/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifba.broadcaster.udpprotocol;

/**
 *
 * @author Thiago
 */
public class ProtocolPacket {

    private IProtocolConverter<ProtocolPacket> converter = null;
    
    private long id;
    private short type;
    private short version;
    private long packetQuantity;
    private long packetSequence;
    private int dataLength;    
    private byte[] data;
    
    public ProtocolPacket() {
        
    }
    
    public ProtocolPacket( long id, short type, short version, long packetQuantity, long packetSequence, int dataLength, byte[] data ) {
        
        this.id = id;
        this.type = type;
        this.version = version;
        this.packetQuantity = packetQuantity;
        this.packetSequence = packetSequence;
        this.dataLength = dataLength;
        this.data = data;
    }
    
    public void toObject(byte[] value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public byte[] toByteArray() {
        
        if(this.converter == null)
            this.converter = new ProtocolConverter();
        
        return this.converter.ObjectToByteArray(this);
    }
    
    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public int getDataLength() {
        return dataLength;
    }

    public void setDataLength(int dataLength) {
        this.dataLength = dataLength;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPacketQuantity() {
        return packetQuantity;
    }

    public void setPacketQuantity(long packetQuantity) {
        this.packetQuantity = packetQuantity;
    }

    public long getPacketSequence() {
        return packetSequence;
    }

    public void setPacketSequence(long packetSequence) {
        this.packetSequence = packetSequence;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public short getVersion() {
        return version;
    }

    public void setVersion(short version) {
        this.version = version;
    }
}
