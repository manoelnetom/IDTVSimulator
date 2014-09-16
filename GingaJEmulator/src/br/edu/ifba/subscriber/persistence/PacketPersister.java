/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifba.subscriber.persistence;

import br.edu.ifba.subscriber.idtvsObjects.IDTVSObject;
import br.edu.ifba.subscriber.datagram.HeaderDatagramPacket;
import br.edu.ifba.subscriber.datagram.ReceivedDatagramPacket;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Felipe
 */
public class PacketPersister {

    //public static final String PATH = "c:" + File.separator + "Subscriber";
    public static final String PATH = "." + File.separator + "Subscriber";
    private File fileFolder;
    private HeaderDatagramPacket header;
    private int persistedPackets = 0;
    private File persistedFile = null;

    public PacketPersister(ReceivedDatagramPacket rdp) {
        this.updatePacketPersisterProperties(rdp.getHeaderDatagramPacket());
        this.persist(rdp);
    }

    public File getPersitedFile() {
        if (this.persistedPackets == this.header.getNumberOfPackets()) {
            return this.persistedFile;
        }
        return null;
    }

    public void persist(ReceivedDatagramPacket rdp) {

        if (rdp.getHeaderDatagramPacket().getVersion() != this.header.getVersion()) {
            this.updatePacketPersisterProperties(rdp.getHeaderDatagramPacket());
        }

        File filePacket = new File(PATH + File.separator + rdp.getHeaderDatagramPacket().getId() + File.separator + rdp.getHeaderDatagramPacket().getPacketNumber() + ".pkt");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(filePacket);
            fos.write(rdp.getData());
            fos.close();
            this.persistedPackets++;
            this.tryMakeFile();
        } catch (Exception ex) {
            Logger.getLogger(PacketPersister.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void updatePacketPersisterProperties(HeaderDatagramPacket newHeader) {
        this.header = newHeader;
        this.createFolder(newHeader);
    }

    private void tryMakeFile() {
        if (this.persistedPackets == this.header.getNumberOfPackets()) {
            File fullFile = null;
            FileOutputStream fos = null;
            try {
                for (long i = 1; i <= this.header.getNumberOfPackets(); i++) {
                    File filePacket = new File(PATH + File.separator + this.header.getId() + File.separator + i + ".pkt");
                    FileInputStream fis = new FileInputStream(filePacket);

                    if (i == 1 && (this.header.getType() == IDTVSObject.BINDER_DIRECTORY_TYPE || this.header.getType() == IDTVSObject.BINDER_APPLICATION_TYPE) )   {
                        fullFile = new File(PATH + File.separator + this.header.getId() + ".bnd");
                        fos = new FileOutputStream(fullFile);
                    }

                    if (i == 1 && (this.header.getType() == IDTVSObject.SINGLE_FILE_TYPE || this.header.getType() == IDTVSObject.BINDED_FILE_TYPE)) {
                        ByteBuffer byteBuffer;
                        byte[] sizeMetaDataBytes = new byte[4];
                        fis.read(sizeMetaDataBytes, 0, 4);
                        byteBuffer = ByteBuffer.wrap(Arrays.copyOfRange(sizeMetaDataBytes, 0, 4));
                        int sizeMetaData = byteBuffer.getInt();

                        byte[] metaDataBytes = new byte[sizeMetaData];
                        fis.read(metaDataBytes);
                        String metaDataString = new String(metaDataBytes);

                        String[] fileTree = metaDataString.split("\\\\");

                        String folderName = PATH;
                        for (int j = 0; j < fileTree.length; j++) {
                            if (j == (fileTree.length - 1)) {
                                fullFile = new File(folderName + File.separator + fileTree[j]);
                                fos = new FileOutputStream(fullFile);
                            } else {
                                folderName += File.separator + fileTree[j];
                                File folder = new File(folderName);
                                if (!folder.exists()) {
                                    folder.mkdir();
                                }
                            }
                        }
                    } else {
                        byte[] fileBytes = new byte[(int) filePacket.length()];
                        fis.read(fileBytes);
                        fos.write(fileBytes);
                    }
                    fis.close();
                }

                this.persistedFile = fullFile;
                fos.close();

                this.deletePacketsFiles();

            } catch (Exception ex) {
                Logger.getLogger(PacketPersister.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void createFolder(HeaderDatagramPacket hdp) {
        this.fileFolder = new File(PATH + File.separator + hdp.getId());
        if (fileFolder.exists()) {
            if (fileFolder.isDirectory()) {
                String[] children = fileFolder.list();
                for (int i = 0; i < children.length; i++) {
                    (new File(fileFolder, children[i])).delete();
                }
            }
        }
        fileFolder.mkdirs();
    }

    private void deletePacketsFiles() {
        for (long i = 1; i <= this.header.getNumberOfPackets(); i++) {
            File filePacket = new File(PATH + File.separator + this.header.getId() + File.separator + i + ".pkt");
            filePacket.delete();
        }
        File idFileFolder = new File(PATH + File.separator + this.header.getId());
        idFileFolder.delete();
    }

    public static void deleteRecivedFiles() {
        deleteFolder(PATH);
    }
    
    private static void deleteFolder(String path) {
        File folder = new File(path);
        if (folder.isDirectory()) {
            for (File currentFile : folder.listFiles()) {
                if (currentFile.isDirectory()) {
                    deleteFolder(currentFile.getAbsolutePath());
                } else {
                    currentFile.delete();
                }
            }
        }
        folder.delete();
    }
}
