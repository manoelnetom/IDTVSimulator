/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifba.subscriber.idtvsObjects.builder;

import br.edu.ifba.subscriber.idtvsObjects.IDTVSObject;

/**
 *
 * @author Felipe
 */
public interface IIDTVSObjectBuilderListener {
    
    public abstract void IDTVSObjectBuilderCompleted(IDTVSObject iDTVSObject);
    
}
