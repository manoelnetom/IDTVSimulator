/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifba.broadcaster.carousel;

import br.edu.ifba.broadcaster.udpprotocol.ProtocolPacket;
import br.edu.ifba.broadcaster.updconnection.ConnectionSender;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Thiago
 */
public class Carousel extends Thread {

    private CarouselNode<IDTVSObject> head;
    private CarouselNode<IDTVSObject> last;
    private ArrayList<IDTVSObjectSet> objectsSet;
    private long time;
    private boolean isRunning = false;
    private boolean isStarted = false;
    private ConnectionSender connSender;

    public Carousel(long time, ConnectionSender connSender) {

        this.time = time;
        this.connSender = connSender;
        this.objectsSet = new ArrayList<>();
    }

    @Deprecated
    public void addNode(CarouselNode<IDTVSObject> node) {

        if (this.head == null) {

            this.head = node;
            this.last = node;
            this.head.setNext(node);
            this.head.setPrevious(node);

        } else {

            this.last.setNext(node);
            node.setNext(this.head);
            node.setPrevious(this.last);
            this.last = node;
            this.head.setPrevious(node);
        }
    }

    public void addNodes(IDTVSObjectSet objects) {
        
        this.objectsSet.add(objects);
        
        for( IDTVSObject currentNode : objects) {
            
            this.addNode(new CarouselNode<>(currentNode));
        }
    }
    
    public void RemoveObject( long idBinder ) {
        
        IDTVSObjectSet selectedSet = null;
        
        for(IDTVSObjectSet currentSet: this.objectsSet) {
            
            for( IDTVSObject currentObject : currentSet ) {
                
                if(currentObject.getIdObject() == idBinder) {
                    selectedSet = currentSet;
                    break;
                }
            }
            
            if( selectedSet != null) {
                break;
            }
        }
        
        if( selectedSet != null) {
            
            for( IDTVSObject currentObject : selectedSet){
                
                //REMOVER
                
                boolean selected = false;
                CarouselNode<IDTVSObject> node = this.head;
                
                while( !selected ) {
                    
                    if( node == null) {
                        break;
                    }
                    
                    if( node.getValue().getIdObject() == currentObject.getIdObject() ) {
                        
                        CarouselNode<IDTVSObject> previous = node.getPrevious();
                        CarouselNode<IDTVSObject> next = node.getNext();
                        
                        if( node.equals(previous) && node.equals(next) ) {
                            
                            node.setNext(null);
                            node.setPrevious(null);
                            // Só há um elemento na lista
                            this.head = null;
                            this.last = null;
                            
                        } else {
                            
                            if(node.equals(this.head)) {
                                this.head = next;
                            }
                            
                            if(node.equals(this.last)) {
                                this.last = previous;
                            }
                            
                            previous.setNext(next);                            
                            next.setPrevious(previous);
                        }
                        
                        selected = true;
                        
                    } else {
                    
                        node = node.getNext();
                    }
                }                                
            }
            
            this.objectsSet.remove(selectedSet);
        }
    }
    
    public IDTVSObjectSet getObjectSet(long idBinder) {
                        
        for(IDTVSObjectSet currentSet: this.objectsSet) {
            
            for( IDTVSObject currentObject : currentSet ) {
                
                if(currentObject.getIdObject() == idBinder) {
                    return currentSet;
                }
            }        
        }
        
        return null;
    }
    
    public void UpdateVersion() {
            
    }
    
    public void startCarousel() {

        this.isRunning = true;
        if (!isStarted) {
            isStarted = true;
            this.start();
        }
    }

    public void pauseCarousel() {

        this.isRunning = false;
    }

    public void stopCarousel() {

        this.isStarted = false;
        this.isRunning = false;
    }
    
    @Override
    public void run() {

        this.Begin();
    }

    private void Begin() {

        CarouselNode<IDTVSObject> currentNode = this.head;

        while (isStarted) {

            if (this.isRunning) {

                if (currentNode == null) {

                    try {
                        
                        Thread.sleep(time);

                    } catch (InterruptedException ex) {

                        System.out.println(ex.getMessage());
                    }

                } else {

                    Date startTime = Calendar.getInstance().getTime();
                    Date currentTime = Calendar.getInstance().getTime();

                    System.out.println("---------------------------------------Começou a fatia de tempo do Objeto Id: " + currentNode.getValue().getIdObject());

                    while ((currentTime.getTime() - startTime.getTime()) < this.time) {

                        try {

                            IDTVSObject obj = currentNode.getValue();

                            byte[] read = obj.read();
                            ProtocolPacket packet = new ProtocolPacket(obj.getIdObject(), obj.getTypeObject(), obj.getVersionObject(), obj.getPacketQuantity(), obj.getSequence(), read.length, read);
                            this.connSender.sendPacket(packet);

                            System.out.println("Objeto Id: " + packet.getId() + " | versão: " + packet.getVersion());
                            System.out.println("Pacote enviado: " + packet.getPacketSequence() + "/" + packet.getPacketQuantity());
                            System.out.println("Tamanho do buffer enviado: " + read.length + " bytes");

                        } catch (FileNotFoundException ex) {

                            System.out.println(ex.getMessage());

                        } catch (IOException ex) {

                            System.out.println(ex.getMessage());
                        }

                        currentTime = Calendar.getInstance().getTime();
                    }

                    System.out.println("****************************************Terminou a fatia de tempo do Objeto Id: " + currentNode.getValue().getIdObject());
                }

                if (currentNode == null) {
                    currentNode = this.head;
                } else {
                    currentNode = currentNode.getNext();
                }
            } else {
                
                 try {
                        
                        Thread.sleep(time);


                    } catch (InterruptedException ex) {

                        System.out.println(ex.getMessage());
                    }
            }
        }
    }
}
