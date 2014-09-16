
package br.edu.ifba.broadcaster.carousel;

import java.io.IOException;

public abstract class IDTVSObject {

    public static final short SINGLE_FILE_TYPE = 1;
    public static final short BINDED_FILE_TYPE = 2;
    public static final short BINDER_DIRECTORY_TYPE = 3;
    public static final short BINDER_APPLICATION_TYPE = 4;
    
    private long idObject;
    private short typeObject;
    private long sequence;
    private int bufferSize;
    private short versionObject;
    
    public IDTVSObject(long idObject, short typeObject, short versionObject, int bufferSize) {
        
        this.bufferSize = bufferSize;
        this.idObject = idObject;
        this.typeObject = typeObject;
        this.versionObject = versionObject;
        this.sequence = 0;        
    }

    public abstract byte[] read() throws IOException;
    public abstract long getPacketQuantity();
    
    public long getIdObject() {
        return idObject;
    }
    
    public short getTypeObject() {
        return typeObject;
    }

    public long getSequence() {
        return sequence;
    }

    public void setSequence(long sequence) {
        this.sequence = sequence;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }     

    public short getVersionObject() {
        return versionObject;
    }
    
}
