
package br.edu.ifba.broadcaster;

import br.edu.ifba.broadcaster.carousel.Carousel;
import br.edu.ifba.broadcaster.carousel.IDTVSObject;
import br.edu.ifba.broadcaster.carousel.IDTVSObjectBinderApp;
import br.edu.ifba.broadcaster.carousel.IDTVSObjectBinderDirectory;
import br.edu.ifba.broadcaster.carousel.IDTVSObjectFile;
import br.edu.ifba.broadcaster.carousel.IDTVSObjectSet;
import br.edu.ifba.broadcaster.udpprotocol.Protocol;
import br.edu.ifba.broadcaster.updconnection.ConnectionSender;
import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Channel {
    
    private ConnectionSender connSender = null;
    private Carousel carousel = null;
    private PlayList playList = null;
    
    private long id = 0;
    
    private ChannelSettings settings;
        
    public Channel( String ip, int mainContentPort, int extraContentPort, String name, int carouselTime ) throws SocketException, UnknownHostException {
                
        this.settings = new ChannelSettings(name, ip, mainContentPort, extraContentPort, carouselTime);
        
        this.connSender = new ConnectionSender(this.settings.getIp(), this.settings.getExtraContentPort());
        this.carousel = new Carousel(this.settings.getCarouselTime(), connSender);
    }
    
    public Channel( ChannelSettings settings ) throws SocketException, UnknownHostException {
        
        this.settings = settings;
        
        this.connSender = new ConnectionSender(this.settings.getIp(), this.settings.getExtraContentPort());
        this.carousel = new Carousel(this.settings.getCarouselTime(), connSender);
    }
    
    private long getNewId() {
        
        return ++id;
    }
    
    public void ResetCarousel(){
        
        this.carousel = new Carousel(50, connSender);
    }
    
    public void SendMainContent( File mainFile ) {
        
        this.playList = new PlayList(this.settings.getIp(), this.settings.getMainContentPort());
        this.playList.addMedia(mainFile);
        this.playList.play();
    }
    
    public IDTVSObjectSet SendExtraContent( File file, Object info  ) throws IOException{
        
        if (file != null) {

            if (file.isDirectory()) {

                ArrayList<Long> ids = new ArrayList<>();
                IDTVSObjectSet objects = new IDTVSObjectSet();                
                this.addNodeAsFolder(file, file.getName(), ids, objects);
                                                
                final short type = IDTVSObject.BINDER_DIRECTORY_TYPE;
                final short version = 0;
                final long objectId =  this.getNewId();
                
                IDTVSObject main = new IDTVSObjectBinderDirectory(objectId, type, version, Protocol.DATA_SIZE, ids);
                objects.add(main);
                objects.setMainObject(main);
                
                carousel.addNodes(objects);
                
                return objects;
                
            } if( file.getName().toUpperCase().endsWith(".CLASS") ) {
                
                File directory;
                                    
                String absolutePath = file.getAbsolutePath();
                String filePath = absolutePath.substring(0,absolutePath.lastIndexOf(File.separator));
                directory = new File(filePath);
                
                ArrayList<Long> ids = new ArrayList<>();
                IDTVSObjectSet objects = new IDTVSObjectSet();
                this.addNodeAsFolder(directory, directory.getName(), ids, objects);
                
                final short type = IDTVSObject.BINDER_APPLICATION_TYPE;
                final short version = 1;
                final long objectId = this.getNewId();
                final byte appRunType = IDTVSObjectBinderApp.AUTOMATIC;
                                                    
                String absolutePathFinal = file.getAbsolutePath();
                String filePathFinal = absolutePathFinal.substring(0,absolutePathFinal.lastIndexOf(File.separator));                    
                filePathFinal = filePathFinal.substring(filePathFinal. lastIndexOf(File.separator) + 1, filePathFinal.length()) + "\\" + file.getName();
                    
                IDTVSObject main = new IDTVSObjectBinderApp(objectId, type, version, Protocol.DATA_SIZE, appRunType, filePathFinal, ((ArrayList<String>)info).get(0), ids);
                objects.add(main);
                objects.setMainObject(main);
                
                carousel.addNodes(objects);
                
                return objects;
                
            } else {

                IDTVSObjectSet objects = new IDTVSObjectSet();
                
                final short type = IDTVSObject.SINGLE_FILE_TYPE;
                final short version = 1;
                long objectId = this.getNewId();

                IDTVSObject main = new IDTVSObjectFile(objectId, type, version, file, Protocol.DATA_SIZE, null);
                objects.add(main);
                objects.setMainObject(main);                
                
                carousel.addNodes(objects);
                
                return objects;
            }
        }
        
        return null;
    }
    
    public IDTVSObjectSet UpdateExtraContent(IDTVSObject obj, File file) {
        
          if (file != null && obj != null) {
              
               IDTVSObjectSet objects = new IDTVSObjectSet();
                               
               final short type = obj.getTypeObject();
               final short version = (short)(obj.getVersionObject() + 1);
               long objectId = obj.getVersionObject();

               IDTVSObject main = new IDTVSObjectFile(objectId, type, version, file, Protocol.DATA_SIZE, null);
               objects.add(main);
               objects.setMainObject(main);                

               carousel.RemoveObject(obj.getIdObject());
               carousel.addNodes(objects);
               
               return objects;
          }
          
          return null;
    }
    
    private void addNodeAsFolder(File rootFolder, String path, ArrayList<Long> ids, IDTVSObjectSet objects) {

        File[] files = rootFolder.listFiles();

        for (File currentFile : files) {

            if (currentFile.isFile()) {
 
                final short type = IDTVSObject.BINDED_FILE_TYPE;
                final short version = 1;
                long objectId = this.getNewId();
                
                ids.add(id);
                objects.add(new IDTVSObjectFile(objectId, type, version, currentFile, Protocol.DATA_SIZE, path + "\\"));

            } else {

                addNodeAsFolder(currentFile, path + "\\" + currentFile.getName(), ids, objects);
            }
        }
    }

    public Carousel getCarousel() {
        return carousel;
    }    
    
    public ChannelSettings getSettings() {
        return settings;
    }
}
