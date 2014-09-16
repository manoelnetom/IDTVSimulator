/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifba.broadcaster.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Thiago
 */
public final class Util {
    
    public static byte[] ListLongToByteArray(ArrayList<Long> list) throws IOException {
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        
        for( Long currentValue : list) {
         
            dos.writeLong(currentValue);
        }
        byte[] bytes = baos.toByteArray();
        return bytes;
    }
    
    public static byte[] IntToByteArray( int value ) throws IOException {
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(value);
        byte[] bytes = baos.toByteArray();
        return bytes;
    }
    
    public static byte[] ShortToByteArray( short value ) throws IOException {
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeShort(value);
        byte[] bytes = baos.toByteArray();
        return bytes;
    }
    
    public static byte[] LongToByteArray( long value ) throws IOException {
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeLong(value);
        byte[] bytes = baos.toByteArray();
        return bytes;
    }
}
