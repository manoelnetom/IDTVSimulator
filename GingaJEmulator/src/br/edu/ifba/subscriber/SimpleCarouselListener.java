/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifba.subscriber;

import br.edu.ifba.subscriber.carousel.ICarouselListener;
import br.edu.ifba.subscriber.idtvsObjects.AppRunType;
import br.edu.ifba.subscriber.idtvsObjects.IDTVSObject;
import br.edu.ifba.subscriber.idtvsObjects.IDTVSObjectBinderApplication;
import br.edu.ifba.subscriber.idtvsObjects.IDTVSObjectBinderDirectory;
import br.edu.ifba.subscriber.idtvsObjects.IDTVSObjectFile;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.beiker.xletview.app.App;
import net.beiker.xletview.xlet.XletManager;

/**
 *
 * @author Felipe
 */
public class SimpleCarouselListener implements ICarouselListener {

    private HashMap<Long, IDTVSObjectFile> iDTVSObjectFileControll = new HashMap<>();
    private HashMap<Long, IDTVSObjectBinderDirectory> iDTVSObjectBinderDirectoryControll = new HashMap<>();
    private HashMap<Long, IDTVSObjectBinderApplication> iDTVSObjectBinderApplicationControll = new HashMap<>();

    @Override
    public void carouselItemCompleted(IDTVSObject iDTVSObject) {
        if (iDTVSObject.getType() == IDTVSObject.SINGLE_FILE_TYPE) {
            this.completeSingleFileType(iDTVSObject);
        } else if (iDTVSObject.getType() == IDTVSObject.BINDED_FILE_TYPE) {
            this.completeBindedFileType(iDTVSObject);
        } else if (iDTVSObject.getType() == IDTVSObject.BINDER_DIRECTORY_TYPE) {
            this.completeBinderDirectoryType(iDTVSObject);
        } else if (iDTVSObject.getType() == IDTVSObject.BINDER_APPLICATION_TYPE) {
            this.completeBinderApplicationType(iDTVSObject);
        }
    }

    private void runApp(IDTVSObjectBinderApplication binder) {
        if (binder.getAppRunType() == AppRunType.AUTOMATIC) {
            synchronized (binder) {
                
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(SimpleCarouselListener.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                App app = binder.getAplication();
                XletManager.getInstance().destroyActiveXlet();
                XletManager.getInstance().setXlet(app.getPath(), app.getXletName());
            }
//            try {
//                //URL url = new URL("file:" + app.getPath());
//                
//            } catch (MalformedURLException ex) {
//                Logger.getLogger(SimpleCarouselListener.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }
    }

    private void completeSingleFileType(IDTVSObject iDTVSObject) {
        System.out.println("COMPLETOU OBJETO (SINGLE_FILE_TYPE) DO ID: " + iDTVSObject.getId());
    }

    private void completeBindedFileType(IDTVSObject iDTVSObject) {
        this.iDTVSObjectFileControll.put(iDTVSObject.getId(), (IDTVSObjectFile) iDTVSObject);

        for (IDTVSObjectBinderDirectory currentIDTVSObjectBinder : this.iDTVSObjectBinderDirectoryControll.values()) {
            boolean isBindTrue = currentIDTVSObjectBinder.bind(iDTVSObject.getId());
            if (isBindTrue) {
                if (currentIDTVSObjectBinder.isBinded()) {
                    System.out.println("COMPLETOU UM OBJETO BinderDirectory");
                    this.iDTVSObjectBinderDirectoryControll.remove(currentIDTVSObjectBinder.getId());
                }
            }
        }

        for (IDTVSObjectBinderApplication currentIDTVSObjectBinderApplication : this.iDTVSObjectBinderApplicationControll.values()) {
            boolean isBindTrue = currentIDTVSObjectBinderApplication.bind(iDTVSObject.getId());
            if (isBindTrue) {
                if (currentIDTVSObjectBinderApplication.isBinded()) {
                    System.out.println("COMPLETOU UM OBJETO  completeBindedFileType BinderApplication");
                    this.runApp(currentIDTVSObjectBinderApplication);
                    this.iDTVSObjectBinderApplicationControll.remove(currentIDTVSObjectBinderApplication.getId());
                }
            }
        }
    }

    private void completeBinderDirectoryType(IDTVSObject iDTVSObject) {
        IDTVSObjectBinderDirectory iDTVSObjectBinder = (IDTVSObjectBinderDirectory) iDTVSObject;

        for (IDTVSObjectFile currentIDTVSObjectFile : this.iDTVSObjectFileControll.values()) {
            iDTVSObjectBinder.bind(currentIDTVSObjectFile.getId());
        }

        if (iDTVSObjectBinder.isBinded()) {
            System.out.println("COMPLETOU UM OBJETO BinderDirectory");
        } else {
            this.iDTVSObjectBinderDirectoryControll.put(iDTVSObject.getId(), (IDTVSObjectBinderDirectory) iDTVSObjectBinder);
        }
    }

    private void completeBinderApplicationType(IDTVSObject iDTVSObject) {
        IDTVSObjectBinderApplication iDTVSObjectBinderApplication = (IDTVSObjectBinderApplication) iDTVSObject;

        for (IDTVSObjectFile currentIDTVSObjectFile : this.iDTVSObjectFileControll.values()) {
            iDTVSObjectBinderApplication.bind(currentIDTVSObjectFile.getId());
        }

        if (iDTVSObjectBinderApplication.isBinded()) {
            System.out.println("COMPLETOU UM OBJETO completeBinderApplicationType BinderApplication");
            runApp(iDTVSObjectBinderApplication);
        } else {
            this.iDTVSObjectBinderApplicationControll.put(iDTVSObject.getId(), (IDTVSObjectBinderApplication) iDTVSObjectBinderApplication);
        }
    }
}
