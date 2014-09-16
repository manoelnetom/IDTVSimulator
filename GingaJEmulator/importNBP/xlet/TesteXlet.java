
import br.edu.ifba.VLCSPlayer;
import com.sun.dtv.lwuit.Form;
import com.sun.dtv.lwuit.geom.Dimension;
import com.sun.dtv.lwuit.layouts.BorderLayout;
import java.io.File;
import java.util.Vector;
import javax.swing.JDialog;
import javax.tv.xlet.Xlet;
import javax.tv.xlet.XletContext;
import javax.tv.xlet.XletStateChangeException;
import net.beiker.xletview.window.ConsoleWindow;
import net.beiker.xletview.window.TvWindow;
import com.sun.dtv.lwuit.List;
import com.sun.dtv.lwuit.events.ActionEvent;
import com.sun.dtv.lwuit.events.ActionListener;
import com.sun.dtv.lwuit.events.SelectionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class TesteXlet extends Form implements Xlet, ActionListener, SelectionListener, ComponentListener {

    private XletContext context;
    private VLCSPlayer player;
    private JDialog videoDialog;
    private List actionList;
    private HashMap<Integer, String> videosPath;
    private Thread thread;

    @Override
    public void initXlet(XletContext ctx) throws XletStateChangeException {
        this.context = ctx;
        this.videosPath = new HashMap<>();
        TvWindow.getInstance().addComponentListener(this);

        this.actionList = new List();
        this.actionList.addItem("Sair do Xlet");
        this.actionList.addActionListener(this);
        this.actionList.addSelectionListener(this);
        this.actionList.setNumericKeyActions(false);

        this.addComponent(BorderLayout.CENTER, actionList);
    }

    private void moveVideoDialog() {
        if (videoDialog != null) {
            int x = TvWindow.getInstance().getX() + 8 + 350;
            int y = TvWindow.getInstance().getY() + 52;
            videoDialog.setLocation(x, y);
        }
    }

    private void playReplay(String url) {
        if (player != null) {
            player.stop();
            player = null;
        }

        player = new VLCSPlayer(url);

        if (videoDialog != null) {
            videoDialog.setVisible(false);
            videoDialog.dispose();
        }

        videoDialog = new JDialog(ConsoleWindow.getInstance());
        videoDialog.setUndecorated(true);
        videoDialog.add(player.getVisualComponent());
        videoDialog.setSize(350, 160);
        videoDialog.setAlwaysOnTop(true);
        videoDialog.setVisible(true);

        moveVideoDialog();

        player.start();

        TvWindow.getInstance().toFront();
    }

    private Replay fillReplayFromXml(File xml, Replay replay) {
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(xml);

            NodeList listOfClima = doc.getElementsByTagName("replay");
            int totalClima = listOfClima.getLength();

            for (int i = 0; i < totalClima; i++) {
                Node cNode = listOfClima.item(i);

                if (cNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) cNode;
                    replay.title = element.getElementsByTagName("title").item(0).getTextContent();
                    replay.description = element.getElementsByTagName("description").item(0).getTextContent();
                    replay.sequence = Integer.parseInt(element.getElementsByTagName("sequence").item(0).getTextContent());
                }
            }

        } catch (ParserConfigurationException | SAXException | IOException | DOMException ex) {
            if (ex instanceof FileNotFoundException) {
                System.out.println("**** DEBUG -- XML não encontrado - " + ex.getMessage());
            } else {
                ex.printStackTrace();
            }
            return null;
        }
        return replay;
    }

    private Replay getReplayObject(File replayFolder) {

        Replay replay = new Replay();
        File[] subFiles = replayFolder.listFiles();

        if (subFiles[0].getName().endsWith(".xml")) {
            replay = this.fillReplayFromXml(subFiles[0], replay);
            replay.replay = subFiles[1];
        } else {
            replay = this.fillReplayFromXml(subFiles[1], replay);
            replay.replay = subFiles[0];
        }

        return replay;

    }

    private ArrayList<Replay> orderReplays(ArrayList<Replay> replays) {

        if (replays != null && replays.size() > 2) {
            for (int i = 0; i < replays.size(); i++) {
                for (int j = 0; j < replays.size(); j++) {
                    if (replays.get(i).sequence < replays.get(j).sequence) {
                        Replay r = replays.get(i);
                        replays.set(i, replays.get(j));
                        replays.set(j, r);
                    }
                }
            }
        }
        return replays;


    }

    private ArrayList<Replay> getReplayVideos() {

        ArrayList<Replay> replays = new ArrayList<>();
        
        String pathName = System.getProperty("application.basedirectory");
        pathName = pathName.substring(0, pathName.lastIndexOf("/"));
        System.out.println("----------------->  " + pathName);
        
        File root = new File(pathName);

        if (root.isDirectory()) {
            File[] subFiles = root.listFiles();
            for (File cFile : subFiles) {
                if (cFile.isDirectory()) {
                    if (cFile.listFiles().length == 2) {
                        Replay r = this.getReplayObject(cFile);
                        if (r != null) {
                            replays.add(r);
                        }
                    }
                }
            }
        }

        replays = this.orderReplays(replays);

        return replays;
    }

    @Override
    public void startXlet() throws XletStateChangeException {

        this.setSize(new Dimension(350, 160));


        thread = new Thread() {
            public void run() {
                try {
                    while (true) {

                        ArrayList<Replay> replays = getReplayVideos();

                        for(Replay r : replays){
                            if (!videosPath.containsKey(r.sequence)) {
                                videosPath.put(r.sequence, r.replay.getPath());
                                actionList.addItem(r.title);
                            }
                        }
                        
                        actionList.repaint();

                        Thread.sleep(5000);
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(TesteXlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };

        thread.start();
        this.show();
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        int newSelected = this.actionList.getSelectedIndex();

        if (newSelected == this.videosPath.size()) {
            try {
                this.destroyXlet(true);
            } catch (XletStateChangeException ex) {
            }
        } else {
            
            int total = this.videosPath.size() + 1;
            
            this.playReplay(this.videosPath.get(total - newSelected - 2));
        }
    }

    @Override
    public void selectionChanged(int oldSelected, int newSelected) {
        System.out.println("Old: " + oldSelected + "  New: " + newSelected);
    }

    @Override
    public void pauseXlet() {
    }

    @Override
    public void destroyXlet(boolean unconditional) throws XletStateChangeException {
        if (!unconditional) {
            throw new XletStateChangeException();
        }

        if (this.thread != null) {
            this.thread.stop();
            this.thread = null;
        }

        if (this.player != null) {
            this.player.stop();
            this.player.deallocate();
        }

        if (this.videoDialog != null) {
            this.videoDialog.setVisible(false);
            this.videoDialog.dispose();
        }


        this.setVisible(false);
        this.dispose();
        context.notifyDestroyed();
    }

    @Override
    public void componentResized(ComponentEvent e) {
        this.moveVideoDialog();
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        this.moveVideoDialog();
    }

    @Override
    public void componentShown(ComponentEvent e) {
        if (this.videoDialog != null) {
            this.videoDialog.setVisible(true);
        }
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        if (this.videoDialog != null) {
            this.videoDialog.setVisible(false);
        }
    }

    private class Replay {

        public String title;
        public String description;
        public int sequence;
        public File replay;

        public Replay() {
        }

        public Replay(String title, String description, int sequence, File replay) {
            this.title = title;
            this.description = description;
            this.sequence = sequence;
            this.replay = replay;
        }
    }
}
