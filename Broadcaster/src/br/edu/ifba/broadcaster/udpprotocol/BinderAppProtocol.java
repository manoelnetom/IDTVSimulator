
package br.edu.ifba.broadcaster.udpprotocol;

public class BinderAppProtocol {
    
    public static final int APP_RUN_TYPE = 1;
    public static final int MAIN_FILE_PATH_SIZE = 1;
    public static final int MAIN_FILE_PATH = 250;
    public static final int MEDIA_NAME_SIZE = 1;
    public static final int MEDIA_NAME = 32;
    public static final int ID_QUANTITY = 4;
    public static final int ID_SIZE = 8;
            
    public static int getPacketHeaderSize(){
        
        return APP_RUN_TYPE + MAIN_FILE_PATH_SIZE + MAIN_FILE_PATH + MEDIA_NAME_SIZE + MEDIA_NAME + ID_QUANTITY;
    }
    
    public static int getMaxPacketSize(){

        return getPacketHeaderSize() + ID_SIZE;
    }
}
