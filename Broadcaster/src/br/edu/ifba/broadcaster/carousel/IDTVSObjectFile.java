/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifba.broadcaster.carousel;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import br.edu.ifba.broadcaster.udpprotocol.Protocol;

/**
 *
 * @author Thiago
 */
public class IDTVSObjectFile extends IDTVSObject {

    private File file;
    private String filePath;
    private BufferedInputStream input;       

    public IDTVSObjectFile(long idObject, short typeObject, short versionObject, File file, int bufferSize, String filePath) {

        super(idObject, typeObject, versionObject, bufferSize);
        
        this.file = file;
        
        if( filePath == null ) {
            this.filePath = "";
        }else {                                    
            this.filePath = filePath;
        }                
    }

    private byte[] getMetaData() {
        
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[4]);
       
        String filePath = this.filePath + this.file.getName();
        int size = filePath.length();
        byte[] sizeBytes = byteBuffer.putInt(size).array();        
        byte[] filePathBytes = filePath.getBytes();
        
        byte[] metaDataBytes = new byte[sizeBytes.length + filePathBytes.length];
        System.arraycopy(sizeBytes, 0, metaDataBytes, 0, sizeBytes.length);
        System.arraycopy(filePathBytes, 0, metaDataBytes, sizeBytes.length, filePathBytes.length);

        this.setSequence(this.getSequence() + 1);

        return metaDataBytes;
    }

    @Override
    public byte[] read() throws FileNotFoundException, IOException {

        if (this.input == null) {

            this.input = new BufferedInputStream(new FileInputStream(this.file));

        }

        if (this.getSequence() == 0) {
            return this.getMetaData();
        }

        byte[] buffer = new byte[this.getBufferSize()];

        int read = this.input.read(buffer);

        if (read == -1) {

            this.setSequence(0);
            //this.input.reset();
            this.input.close();
            this.input = new BufferedInputStream(new FileInputStream(this.file));
            //read = this.input.read(buffer);
            return this.getMetaData();
        }
        this.setSequence(this.getSequence() + 1);
        return Arrays.copyOfRange(buffer, 0, read);

    }
        
    @Override
    public long getPacketQuantity() {

        return (long) (Math.ceil((float) this.file.length() / (float) this.getBufferSize())) + 1;
    }

    public BufferedInputStream getInput() {
        return input;
    }

    public void setInput(BufferedInputStream input) {
        this.input = input;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File File) {
        this.file = File;
    }
}
