/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifba.subscriber.datagram;

import java.util.Arrays;

/**
 * Pacote para o protocolo adotado
 * @author Felipe
 */
public class ReceivedDatagramPacket {

    private HeaderDatagramPacket headerDatagramPacket;
    private byte[] data;

    public ReceivedDatagramPacket(byte[] datagramPacket) {
        this.headerDatagramPacket = new HeaderDatagramPacket(datagramPacket);
        this.data = Arrays.copyOfRange(datagramPacket, HeaderDatagramPacket.HEADER_SIZE, this.headerDatagramPacket.getSize() + HeaderDatagramPacket.HEADER_SIZE);
    }

    /**
     * @return the headerDatagramPacket
     */
    public HeaderDatagramPacket getHeaderDatagramPacket() {
        return headerDatagramPacket;
    }

    /**
     * @return the data
     */
    public byte[] getData() {
        return data;
    }
}
