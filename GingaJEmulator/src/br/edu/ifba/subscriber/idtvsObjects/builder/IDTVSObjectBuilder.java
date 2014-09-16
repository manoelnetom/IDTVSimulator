/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifba.subscriber.idtvsObjects.builder;

import br.edu.ifba.subscriber.datagram.HeaderDatagramPacket;
import br.edu.ifba.subscriber.datagram.ReceivedDatagramPacket;
import br.edu.ifba.subscriber.idtvsObjects.IDTVSObject;
import br.edu.ifba.subscriber.idtvsObjects.IDTVSObjectBinderApplication;
import br.edu.ifba.subscriber.idtvsObjects.IDTVSObjectBinderDirectory;
import br.edu.ifba.subscriber.idtvsObjects.IDTVSObjectFile;
import br.edu.ifba.subscriber.persistence.PacketPersister;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Felipe
 */
public class IDTVSObjectBuilder {

    private PacketPersister packetPersister;
    private HashMap<Long, Boolean> packetControll;
    private ArrayList<IIDTVSObjectBuilderListener> listeners;
    private long id;
    private short type;
    private short version;
    private long numberOfPackets;
    private int size;
    private long numberOfPersistedPackets;

    public IDTVSObjectBuilder() {
        this.listeners = new ArrayList<>();
    }

    public IDTVSObjectBuilder(ReceivedDatagramPacket rdp) {
        this.listeners = new ArrayList<>();
        this.addFirstPacket(rdp);
    }

    public void addFirstPacket(ReceivedDatagramPacket rdp) {
        this.updateIDTVSObjectBuilderProperties(rdp.getHeaderDatagramPacket());

        try {
            this.addPacket(rdp);
        } catch (Exception ex) {
        }
    }

    public void addPacket(ReceivedDatagramPacket sdp) throws Exception {
        if (sdp.getHeaderDatagramPacket().getId() != this.id) {
            throw new Exception("Pacote inserido Não é do Objeto.");
        } else if (sdp.getHeaderDatagramPacket().getVersion() == this.version && isCompleted()) {
            //throw new Exception("O Objeto já esta completo.");
        } else {
            if (sdp.getHeaderDatagramPacket().getVersion() != this.version) {
                this.updateIDTVSObjectBuilderProperties(sdp.getHeaderDatagramPacket());
            }
            if (!this.packetControll.containsKey(sdp.getHeaderDatagramPacket().getPacketNumber())) {
                if (this.writeIDTVSObjectPacket(sdp)) {
                    this.numberOfPersistedPackets++;
                    this.packetControll.put(sdp.getHeaderDatagramPacket().getPacketNumber(), Boolean.TRUE);
                }
            }
            if (this.isCompleted()) {
                this.fireIDTVSObjectBuilderCompleted();
            }
        }
    }

    public void addListener(IIDTVSObjectBuilderListener listener) {
        this.listeners.add(listener);
    }

    private void fireIDTVSObjectBuilderCompleted() {



        IDTVSObject completedIDTVSObject = null;

        if (this.type == IDTVSObject.SINGLE_FILE_TYPE) {
            completedIDTVSObject = new IDTVSObjectFile(id, type, this.packetPersister.getPersitedFile());
        } else if (this.type == IDTVSObject.BINDED_FILE_TYPE) {
            completedIDTVSObject = new IDTVSObjectFile(id, type, this.packetPersister.getPersitedFile());
        } else if (this.type == IDTVSObject.BINDER_DIRECTORY_TYPE) {
            completedIDTVSObject = new IDTVSObjectBinderDirectory(id, type, this.packetPersister.getPersitedFile());
        } else if (this.type == IDTVSObject.BINDER_APPLICATION_TYPE) {
            completedIDTVSObject = new IDTVSObjectBinderApplication(id, type, this.packetPersister.getPersitedFile());
        }


        for (IIDTVSObjectBuilderListener currentIIDTVSObjectBuilderListener : listeners) {
            currentIIDTVSObjectBuilderListener.IDTVSObjectBuilderCompleted(completedIDTVSObject);
        }


    }

    private boolean isCompleted() {
        if (this.numberOfPersistedPackets == this.numberOfPackets) {
            return true;
        }
        return false;
    }

    private void updateIDTVSObjectBuilderProperties(HeaderDatagramPacket hdp) {
        this.packetControll = new HashMap<>();
        this.id = hdp.getId();
        this.type = hdp.getType();
        this.version = hdp.getVersion();
        this.numberOfPackets = hdp.getNumberOfPackets();
        this.size = hdp.getSize();
        this.numberOfPersistedPackets = 0;
    }

    private boolean writeIDTVSObjectPacket(ReceivedDatagramPacket rdp) {

        System.out.println("Id: " + rdp.getHeaderDatagramPacket().getId() + " [" + this.numberOfPersistedPackets + " de " + rdp.getHeaderDatagramPacket().getNumberOfPackets() + "]");

        if (this.numberOfPersistedPackets == 0) {
            packetPersister = new PacketPersister(rdp);
        } else {
            packetPersister.persist(rdp);
        }

        return true;
    }
}
