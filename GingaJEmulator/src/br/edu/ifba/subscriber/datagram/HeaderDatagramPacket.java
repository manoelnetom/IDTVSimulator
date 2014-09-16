/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifba.subscriber.datagram;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Cabeçalho do protocolo adotado.
 * @author Felipe
 */
public class HeaderDatagramPacket {
    
    public static final int HEADER_SIZE = 32;
    
    private long id;
    private short type;
    private short version;
    private long numberOfPackets;
    private long packetNumber;
    private int size;
    
    public HeaderDatagramPacket(byte[] packetHeader){
                        
        ByteBuffer byteBuffer; 
        
        //8 Bytes: ID
        byteBuffer = ByteBuffer.wrap(Arrays.copyOfRange(packetHeader, 0,8));
        this.id = byteBuffer.getLong();
        
        //2 Bytes: Tipo
        byteBuffer = ByteBuffer.wrap(Arrays.copyOfRange(packetHeader, 8,10));
        this.type = byteBuffer.getShort();
        
        //2 Bytes: Versão
        byteBuffer = ByteBuffer.wrap(Arrays.copyOfRange(packetHeader, 10,12));
        this.version = byteBuffer.getShort();
        
        //8 Bytes: Numero de pacotes
        byteBuffer = ByteBuffer.wrap(Arrays.copyOfRange(packetHeader, 12,20));
        this.numberOfPackets = byteBuffer.getLong();
        
        //8 Bytes: Numero do pacote
        byteBuffer = ByteBuffer.wrap(Arrays.copyOfRange(packetHeader, 20,28));
        this.packetNumber = byteBuffer.getLong();
        
        //4 Bytes: Tamanho da parte de dados (61440 bytes)
        byteBuffer = ByteBuffer.wrap(Arrays.copyOfRange(packetHeader, 28,32));
        this.size = byteBuffer.getInt();
        
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the type
     */
    public short getType() {
        return type;
    }

    /**
     * @return the version
     */
    public short getVersion() {
        return version;
    }

    /**
     * @return the numberOfPackets
     */
    public long getNumberOfPackets() {
        return numberOfPackets;
    }

    /**
     * @return the packetNumber
     */
    public long getPacketNumber() {
        return packetNumber;
    }

    /**
     * @return the size
     */
    public int getSize() {
        return size;
    }
    
}
