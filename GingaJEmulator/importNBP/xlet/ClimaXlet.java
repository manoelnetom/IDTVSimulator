
import javax.tv.xlet.Xlet;
import javax.tv.xlet.XletContext;
import javax.tv.xlet.XletStateChangeException;

import com.sun.dtv.lwuit.Form;
import com.sun.dtv.lwuit.Label;
import com.sun.dtv.lwuit.geom.Dimension;
import com.sun.dtv.lwuit.layouts.BorderLayout;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;
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

public class ClimaXlet extends Form implements Xlet {

    private XletContext context;
    private Label localBody = new Label();
    private Label netBody = new Label();
    private String localBodyValue;
    private String netBodyValue;
    private Thread thread;
    private String XML_PATH = "";

    @Override
    public void initXlet(XletContext ctx) throws XletStateChangeException {
        this.context = ctx;
        this.setXMLPath();
        this.setTitle("Clima Tempo");
        this.setSize(new Dimension(350, 155));
    }

    private void setXMLPath() {
        XML_PATH = System.getProperty("application.basedirectory");
        String[] path = XML_PATH.split("/");
        XML_PATH = "";
        for (int i = 0; i < path.length; i++) {
            if (i < path.length - 1) {
                XML_PATH += path[i] + "/";
            }
        }
        XML_PATH += "clima.xml";
    }

    private String downloadXML() {
        try {
            URL url = new URL("http://69.64.52.152/felipeteste/clima.xml");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            connection.setUseCaches(false);

            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();

            return response.toString();
        } catch (IOException ex) {
            return null;
        }
    }

    private String readXML(String value) {
        try {
            if (value == null) {
                return "ERRO";
            }

            String xmlAsString = "";
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc;

            if (value.startsWith("<")) {
                doc = docBuilder.parse(new ByteArrayInputStream(value.getBytes()));
            } else {
                doc = docBuilder.parse(new File(value));
            }

            NodeList listOfClima = doc.getElementsByTagName("clima");
            int totalClima = listOfClima.getLength();

            for (int i = 0; i < totalClima; i++) {
                Node cNode = listOfClima.item(i);

                if (cNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) cNode;
                    xmlAsString += element.getElementsByTagName("local").item(0).getTextContent();
                    xmlAsString += ": ";
                    xmlAsString += element.getElementsByTagName("temperatura").item(0).getTextContent();
                    xmlAsString += "; \n";
                }
            }

            return xmlAsString;
        } catch (ParserConfigurationException | SAXException | IOException | DOMException ex) {
            if (ex instanceof FileNotFoundException) {
                System.out.println("**** DEBUG -- XML não encontrado - " + ex.getMessage());
            } else {
                ex.printStackTrace();
            }
            return "ERRO";
        }
    }

    @Override
    public void startXlet() throws XletStateChangeException {
        System.out.println("**** DEBUG -- startXlet");
        this.show();

        this.removeAll();

        Label title = new Label();
        title.setText("Sua aplicação de clima.");
        this.addComponent(BorderLayout.NORTH, title);
        this.addComponent(BorderLayout.CENTER, localBody);
        this.addComponent(BorderLayout.SOUTH, netBody);

        localBody.setText("Aguardando dados do Broadcaster...");
        netBody.setText("Aguardando dados da Internet...");
        this.repaint();

        localBodyValue = this.readXML(XML_PATH);
        netBodyValue = this.readXML(this.downloadXML());


        thread = new Thread() {
            public void run() {
                try {
                    while (true) {
                        try {
                            Thread.sleep(10000);
                            String oldlocalBodyValue = localBody.getText();
                            String oldNetBodyValue = netBody.getText();

                            localBody.setText("Atualizando dados do Broadcaster...");
                            netBody.setText("Atualizando dados da Internet...");

                            repaint();

                            localBodyValue = readXML(XML_PATH);
                            netBodyValue = readXML(downloadXML());

                            if (!localBodyValue.equalsIgnoreCase("ERRO")) {
                                localBody.setText(localBodyValue);
                                repaint();
                            } else {
                                localBody.setText(oldlocalBodyValue);
                                repaint();
                            }

                            if (!netBodyValue.equalsIgnoreCase("ERRO")) {
                                netBody.setText(netBodyValue);
                                repaint();
                            } else {
                                netBody.setText(oldNetBodyValue);
                                repaint();
                            }
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ClimaXlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();
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
            thread.stop();
        }
        this.setVisible(false);
        context.notifyDestroyed();
    }

    @Override
    public void keyPressed(int keyCode) {
        if (keyCode == 27) {
            try {
                this.destroyXlet(true);
            } catch (XletStateChangeException ex) {
                Logger.getLogger(ClimaXlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
