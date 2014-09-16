/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifba.subscriber.carousel;

import br.edu.ifba.subscriber.idtvsObjects.IDTVSObject;

/**
 *
 * @author Felipe
 */
public interface ICarouselListener {
    
    public abstract void carouselItemCompleted(IDTVSObject iDTVSObject);
    
}
