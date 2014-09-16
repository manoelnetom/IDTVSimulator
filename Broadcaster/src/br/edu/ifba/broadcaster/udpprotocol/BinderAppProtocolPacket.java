package br.edu.ifba.broadcaster.udpprotocol;

import java.util.ArrayList;

public class BinderAppProtocolPacket {
    
    private IProtocolConverter<BinderAppProtocolPacket> converter = null;
        
    private byte appRunType;
    private String filePath;
    private String mediaName;
    private ArrayList<Long> ids;

    public BinderAppProtocolPacket(){
        
    }
    
    public BinderAppProtocolPacket(byte appRunType, String filePath, String mediaName, ArrayList<Long> ids){
        
        this.appRunType = appRunType;
        this.filePath = filePath;        
        this.mediaName = mediaName;
        this.ids = ids;
    }
    
        public void toObject(byte[] value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public byte[] toByteArray() {
        
        if(this.converter == null) {
         
            this.converter = new BinderAppProtocolConverter();
        }
        
        return this.converter.ObjectToByteArray(this);
    }
    
    public byte getFilePathLength() {
        return new Integer(this.filePath.length()).byteValue();
    }
    
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Long> getIds() {
        return ids;
    }

    public void setIds(ArrayList<Long> ids) {
        this.ids = ids;
    }

    public byte getMediaNameLength() {
        return new Integer(this.mediaName.length()).byteValue();
    }
    
    public String getMediaName() {
        return mediaName;
    }
    
    public int getIdsQuantity(){
        
        return this.ids.size();
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }        

    public byte getAppRunType() {
        return appRunType;
    }

    public void setAppRunType(byte appRunType) {
        this.appRunType = appRunType;
    }
    
    
}
