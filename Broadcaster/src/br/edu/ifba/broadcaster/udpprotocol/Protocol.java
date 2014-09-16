/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifba.broadcaster.udpprotocol;

/**
 *
 * @author Thiago
 */
public final class Protocol {
 
    // Tamanho do Id em bytes
    public static int ID_SIZE = 8;
    // Tamanho do tipo em bytes
    public static int TYPE_SIZE = 2;
    // Tamanho da versão em bytes
    public static int VERSION_SIZE = 2;
    // Tamanho da quantidade de pacotes em bytes
    public static int PACKET_QUANTITY_SIZE = 8;
    // Tamanho da sequencia do pacote em bytes
    public static int PACKET_SEQUENCE_SIZE = 8;
    // Tamanho do comprimento dos dados em bytes
    public static int DATA_LENGTH_SIZE = 4;
    // Tamanho máximo dos dados em bytes
    //public static int DATA_SIZE = 61440;
    public static int DATA_SIZE = 30720;    
    
    public static int getPacketHeaderSize() {
        
        return ID_SIZE + TYPE_SIZE + VERSION_SIZE + PACKET_QUANTITY_SIZE + PACKET_SEQUENCE_SIZE + DATA_LENGTH_SIZE;
    }
    
    public static int getMaxPacketSize() {
        
        return getPacketHeaderSize() + DATA_SIZE;
    }    
}
