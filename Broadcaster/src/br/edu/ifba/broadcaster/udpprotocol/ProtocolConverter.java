/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifba.broadcaster.udpprotocol;

import java.nio.ByteBuffer;

/**
 *
 * @author Thiago
 */
public class ProtocolConverter implements IProtocolConverter<ProtocolPacket> {

    @Override
    public ProtocolPacket ByteArrayToObject(byte[] value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public byte[] ObjectToByteArray(ProtocolPacket value) {
        
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[Protocol.getMaxPacketSize()]);
        
        byteBuffer.putLong(value.getId());
        byteBuffer.putShort(value.getType());
        byteBuffer.putShort(value.getVersion());
        byteBuffer.putLong(value.getPacketQuantity());
        byteBuffer.putLong(value.getPacketSequence());
        byteBuffer.putInt(value.getDataLength());        
        byteBuffer.put(value.getData());
        
        return byteBuffer.array();
    }
    
}
