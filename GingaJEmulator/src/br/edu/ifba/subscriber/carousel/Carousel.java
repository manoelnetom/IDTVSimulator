/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifba.subscriber.carousel;

import br.edu.ifba.subscriber.idtvsObjects.builder.IDTVSObjectBuilder;
import br.edu.ifba.subscriber.idtvsObjects.IDTVSObject;
import br.edu.ifba.subscriber.idtvsObjects.builder.IIDTVSObjectBuilderListener;
import br.edu.ifba.subscriber.datagram.ReceivedDatagramPacket;
import br.edu.ifba.subscriber.persistence.PacketPersister;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Felipe
 */
public class Carousel extends Thread implements IIDTVSObjectBuilderListener{

    private ArrayList<ICarouselListener> listeners;
    private HashMap<Long, IDTVSObjectBuilder> carouselControll;
    private int portToListener;

    public Carousel(int portToListener) {
        this.listeners = new ArrayList<>();
        this.carouselControll = new HashMap<>();
        this.portToListener = portToListener;
        PacketPersister.deleteRecivedFiles();
    }
    
    @Override
    public void run() {
        this.startSocketListener();
    }    

    @Override
    public void IDTVSObjectBuilderCompleted(IDTVSObject iDTVSObject) {
        this.fireCarouselItemCompleted(iDTVSObject);
    }
    
    public void addListener(ICarouselListener listener){
        this.listeners.add(listener);
    }
    
    private void fireCarouselItemCompleted(IDTVSObject iDTVSObject){
        for (ICarouselListener currentICarouselListener : listeners){
            currentICarouselListener.carouselItemCompleted(iDTVSObject);
            
        }
    }
    
    private void startSocketListener() {
        try {
            DatagramSocket clientSocket = new DatagramSocket(this.portToListener);
//            DatagramSocket clientSocket = new DatagramSocket(12287);
            clientSocket.setBroadcast(true);

            byte[] receiveData = new byte[40000];

            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            while (true) {
                try {
                    clientSocket.receive(receivePacket);

                    byte[] data = receivePacket.getData();
                    
                    this.addPacket(data);

                } catch (Exception e) {
                    System.out.println("TRATAR ISSO while (true)!");
                }
            }

        } catch (SocketException ex) {
            Logger.getLogger(Carousel.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("ERRO NA startSocketListener!");
        }

    }

    private void addPacket(byte[] datagramPacket) {
        ReceivedDatagramPacket sdp = new ReceivedDatagramPacket(datagramPacket);

        Long objectId = sdp.getHeaderDatagramPacket().getId();

        try {

            if (carouselControll.containsKey(objectId)) {
                carouselControll.get(objectId).addPacket(sdp);
            } else {
                IDTVSObjectBuilder builder = new IDTVSObjectBuilder();
                builder.addListener(this);
                builder.addFirstPacket(sdp);
                carouselControll.put(objectId, builder);
            }

        } catch (Exception ex) {
            Logger.getLogger(Carousel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
}