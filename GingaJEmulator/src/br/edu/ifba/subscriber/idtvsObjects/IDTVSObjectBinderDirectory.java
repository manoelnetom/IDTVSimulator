/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifba.subscriber.idtvsObjects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Felipe
 */
public class IDTVSObjectBinderDirectory extends IDTVSObject {

    private HashMap<Long, Boolean> bindControll = new HashMap<>();
    public int binderType;

    public IDTVSObjectBinderDirectory(long id, short type, File file) {
        super(id, type);
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteBuffer byteBuffer = null;
            
            byte[] idValueByte = new byte[8];
            while (fis.read(idValueByte, 0, 8) != -1) {

                byteBuffer = ByteBuffer.wrap(Arrays.copyOfRange(idValueByte, 0, 8));
                Long idValue = new Long(byteBuffer.getLong());
                this.bindControll.put(idValue, Boolean.FALSE);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(IDTVSObjectBinderDirectory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(IDTVSObjectBinderDirectory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean bind(long idIDTVSObject) {
        if (this.bindControll.containsKey(idIDTVSObject)) {
            this.bindControll.put(idIDTVSObject, Boolean.TRUE);
            return true;
        }
        return false;
    }

    public boolean isBinded() {
        Collection<Boolean> values = bindControll.values();
        for (Boolean currentValue : values) {
            if (!currentValue) {
                return false;
            }
        }
        return true;
    }

    public int getBinderType() {
        return binderType;
    }

    public void setBinderType(int binderType) {
        this.binderType = binderType;
    }
}
