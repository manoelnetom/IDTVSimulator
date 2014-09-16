/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifba.broadcaster.udpprotocol;

/**
 *
 * @author Thiago
 */
public interface IProtocolConverter<T> {
    
    T ByteArrayToObject(byte[] value);
    byte[] ObjectToByteArray(T value);
}
