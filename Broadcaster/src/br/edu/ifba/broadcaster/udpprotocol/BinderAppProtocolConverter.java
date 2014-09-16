/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifba.broadcaster.udpprotocol;

import java.nio.ByteBuffer;

/**
 *
 * @author daten3
 */
public class BinderAppProtocolConverter implements IProtocolConverter<BinderAppProtocolPacket> {

    @Override
    public BinderAppProtocolPacket ByteArrayToObject(byte[] value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public byte[] ObjectToByteArray(BinderAppProtocolPacket value) {
        
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[BinderAppProtocol.getPacketHeaderSize() + (value.getIds().size() * BinderAppProtocol.ID_SIZE)]);
                        
        byteBuffer.put(value.getAppRunType());
        
        // Adiciona o tamanho do path
        byteBuffer.put(value.getFilePathLength());
        
        // Adiciona o path do arquivo principal
        ByteBuffer byteBuferFile = ByteBuffer.wrap(new byte[BinderAppProtocol.MAIN_FILE_PATH]);
        byteBuferFile.put(value.getFilePath().getBytes());
        byteBuffer.put(byteBuferFile.array());
        
        // Adiciona o tamanho do nome da mídia
        byteBuffer.put(value.getMediaNameLength());
        
        // Adiciona o nome da mídia
        byteBuferFile = ByteBuffer.wrap(new byte[BinderAppProtocol.MEDIA_NAME]);
        byteBuferFile.put(value.getMediaName().getBytes());
        byteBuffer.put(byteBuferFile.array());
        
        byteBuffer.putInt(value.getIdsQuantity());
        
        // Adiciona Ids;
        for( Long currentId : value.getIds() ) {
            byteBuffer.putLong(currentId);
        }
                
        return byteBuffer.array();
    }
    
    
}
