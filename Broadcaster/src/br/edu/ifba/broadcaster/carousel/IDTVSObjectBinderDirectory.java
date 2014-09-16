/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifba.broadcaster.carousel;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import br.edu.ifba.broadcaster.util.Util;

/**
 *
 * @author daten3
 */
public class IDTVSObjectBinderDirectory extends IDTVSObject {
    
    private byte[] ids;
    private BufferedInputStream input;    
    
    public IDTVSObjectBinderDirectory(long idObject, short typeObject, short versionObject, int bufferSize, ArrayList<Long> ids) throws IOException {
        
        super(idObject, typeObject,versionObject, bufferSize);
        
        this.ids = Util.ListLongToByteArray(ids);
    }

    @Override
    public byte[] read() throws IOException {
        
        if (this.input == null) {

            this.input = new BufferedInputStream(new ByteArrayInputStream(ids));
        }
        
        byte[] buffer = new byte[this.getBufferSize()];

        int read = this.input.read(buffer);

        if (read == -1) {

            this.setSequence(0);            
            this.input.close();
            this.input = new BufferedInputStream(new ByteArrayInputStream(ids));
            read = this.input.read(buffer);            
        }
        this.setSequence(this.getSequence() + 1);
        return Arrays.copyOfRange(buffer, 0, read);
    }

    @Override
    public long getPacketQuantity() {
        
        return (long) (Math.ceil((float) this.ids.length / (float) this.getBufferSize()));
    }
}
