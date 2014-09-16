/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifba.broadcaster.carousel;

/**
 *
 * @author Thiago
 */
public class CarouselNode<T> {
    
    private T value;
    private CarouselNode<T> next;
    private CarouselNode<T> previous;
    
    public CarouselNode( T value, CarouselNode<T> next ) {
    
        this.value = value;
        this.next = next;
    }
    
    public CarouselNode( T value ) {
        
        this.value = value;
        this.next = null;
    }

    public CarouselNode<T> getNext() {
        return next;
    }

    public void setNext(CarouselNode<T> next) {
        this.next = next;
    }

    public CarouselNode<T> getPrevious() {
        return previous;
    }

    public void setPrevious(CarouselNode<T> previous) {
        this.previous = previous;
    }

    
    
    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
    
    
}
