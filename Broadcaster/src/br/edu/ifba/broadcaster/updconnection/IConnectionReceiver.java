/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifba.broadcaster.updconnection;

import java.io.IOException;

/**
 *
 * @author Thiago
 */
public interface IConnectionReceiver<T> {
    
    T receivePacket(byte[] buffer) throws IOException;
}
