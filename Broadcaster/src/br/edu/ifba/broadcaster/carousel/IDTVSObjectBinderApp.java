
package br.edu.ifba.broadcaster.carousel;

import br.edu.ifba.broadcaster.udpprotocol.BinderAppProtocolPacket;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class IDTVSObjectBinderApp extends IDTVSObject{

    public static final byte AUTOMATIC = 0;
    public static final byte NOTIFY = 1;
    public static final byte LIST = 2;
    
    private byte appRunType;
    private String filePath;
    private String mediaName;
    private ArrayList<Long> ids;
    private BufferedInputStream input;    
    
    public IDTVSObjectBinderApp(long idObject, short typeObject, short versionObject, int bufferSize, byte appRunType, String filePath, String mediaName, ArrayList<Long> ids) throws IOException {
        
        super(idObject, typeObject,versionObject, bufferSize);
        
        this.appRunType = appRunType;
        this.filePath = filePath;
        this.mediaName = mediaName;
        this.ids = ids;
    } 
    
    @Override
    public byte[] read() throws IOException {
        
        
        
        if (this.input == null) {

            BinderAppProtocolPacket packet = new BinderAppProtocolPacket(appRunType,filePath, mediaName, ids);
            
            this.input = new BufferedInputStream(new ByteArrayInputStream(packet.toByteArray()));
        }
        
        byte[] buffer = new byte[this.getBufferSize()];

        int read = this.input.read(buffer);

        if (read == -1) {

            this.setSequence(0);            
            this.input.close();
            BinderAppProtocolPacket packet = new BinderAppProtocolPacket(appRunType, filePath, mediaName, ids);
            this.input = new BufferedInputStream(new ByteArrayInputStream(packet.toByteArray()));
            read = this.input.read(buffer);            
        }
        this.setSequence(this.getSequence() + 1);
        return Arrays.copyOfRange(buffer, 0, read);
    }

    @Override
    public long getPacketQuantity() {
        
        BinderAppProtocolPacket packet = new BinderAppProtocolPacket(appRunType, filePath, mediaName, ids);
        return (long) (Math.ceil((float) packet.toByteArray().length / (float) this.getBufferSize()));
    }
    
     public String getMediaName() {
        return mediaName;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }
}
