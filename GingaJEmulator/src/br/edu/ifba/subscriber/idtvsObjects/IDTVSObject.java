/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifba.subscriber.idtvsObjects;

import java.io.File;

/**
 *
 * @author Felipe
 */
public class IDTVSObject {

    private long id;
    private short type;
    
    public static final short SINGLE_FILE_TYPE = 1;
    public static final short BINDED_FILE_TYPE = 2;
    public static final short BINDER_DIRECTORY_TYPE = 3;
    public static final short BINDER_APPLICATION_TYPE = 4;

    public IDTVSObject(long id, short type) {
        this.id = id;
        this.type = type;
    }

    public long getId() {
        return this.id;
    }

    public short getType() {
        return this.type;
    }
}
