package com.sun.dtv.lwuit.util;

import java.util.Vector;

import com.sun.dtv.lwuit.Component;
import com.sun.dtv.lwuit.Display;
import com.sun.dtv.lwuit.events.ActionEvent;
import com.sun.dtv.lwuit.events.ActionListener;
import com.sun.dtv.lwuit.events.DataChangedListener;
import com.sun.dtv.lwuit.events.FocusListener;
import com.sun.dtv.lwuit.events.SelectionListener;
import com.sun.dtv.lwuit.events.StyleListener;
import com.sun.dtv.lwuit.plaf.Style;

public class EventDispatcher 
{
	private boolean blocking = false;
    private Vector listeners;   
    private Object[] pending;
    private Object pendingEvent;

    private static boolean fireStyleEventsOnNonEDT = false;

    /**
     * When set to true, style events will be dispatched even from non-EDT threads.
     * When set to false, when in non-EDT threads, style events will not be dispatched at all (And developer has to make sure changes will be reflected by calling revalidate after all the changes)
     *
     * Default is false. Setting this to true results in a performance penalty, and it is better instead to simply aggregate events performed on non-EDT threads and when all are over - call revalidate on the relevant container.
     *
     * @param fire true to fire on non-EDT, false otherwise
     */
    public static void setFireStyleEventsOnNonEDT(boolean fire) {
        fireStyleEventsOnNonEDT = fire;
    }


    class CallbackClass implements Runnable {
        private Object[] iPending;
        private Object iPendingEvent;
        public CallbackClass() {
            if(!blocking) {
                iPendingEvent = pendingEvent;
                iPending = pending;
            }
        }

        /**
         * Do not invoke this method it handles the dispatching internally and serves
         * as an implementation detail
         */
        public final void run() {
            if(!Display.getInstance().isEdt()) {
                throw new IllegalStateException("This method should not be invoked by external code!");
            }

            if(blocking) {
                iPendingEvent = pendingEvent;
                iPending = pending;
            }

            if(iPending instanceof ActionListener[]) {
                fireActionSync((ActionListener[])iPending, (ActionEvent)iPendingEvent);
                return;
            }

            if(pending instanceof FocusListener[]) {
                fireFocusSync((FocusListener[])iPending, (Component)iPendingEvent);
                return;
            }

            if(iPending instanceof DataChangedListener[]) {
                fireDataChangeSync((DataChangedListener[])iPending, ((int[])iPendingEvent)[0], ((int[])iPendingEvent)[1]);
                return;
            }

            if(iPending instanceof SelectionListener[]) {
                fireSelectionSync((SelectionListener[])iPending, ((int[])iPendingEvent)[0], ((int[])iPendingEvent)[1]);
                return;
            }

            if(iPending instanceof StyleListener[]) {
                Object[] p = (Object[])iPendingEvent;
                fireStyleChangeSync((StyleListener[])iPending, (String)p[0], (Style)p[1]);
                pendingEvent = null;
                pending = null;
                return;
            }
        }
    };

    private final Runnable callback = new CallbackClass();
    
    /**
     * Add a listener to the dispatcher that would receive the events when they occurs
     * 
     * @param listener a dispatcher listener to add
     */
    public synchronized void addListener(Object listener) {
        if(listeners == null) {
            listeners = new Vector();
        }
        if(!listeners.contains(listener)){
            listeners.addElement(listener);
        }        
    }
    
    /**
     * Returns the vector of the listeners
     * 
     * @return the vector of listeners attached to the event dispatcher
     */
    public Vector getListenerVector() {
        return listeners;
    }

    /**
     * Remove the listener from the dispatcher
     *
     * @param listener a dispatcher listener to remove
     */
    public synchronized void removeListener(Object listener) {
        if(listeners != null) {
            listeners.removeElement(listener);
        }
    }

    /**
     * Fires the event safely on the EDT without risk of concurrency errors
     * 
     * @param index the index of the event
     * @param type the type of the event
     */
    public void fireDataChangeEvent(int index, int type) {
        if(listeners == null || listeners.size() == 0) {
            return;
        }
        DataChangedListener[] array;
        synchronized(this) {
            array = new DataChangedListener[listeners.size()];
            for(int iter = 0 ; iter < array.length ; iter++) {
                array[iter] = (DataChangedListener)listeners.elementAt(iter);
            }
        }
        // if we already are on the EDT just fire the event
        if(Display.getInstance().isEdt()) {
            fireDataChangeSync(array, type, index);
        } else {
            pending = array;
            pendingEvent = new int[] {type, index};
            if(blocking) {
                Display.getInstance().callSeriallyAndWait(callback);
            } else {
                Display.getInstance().callSerially(new CallbackClass());
            }
            pending = null;
            pendingEvent = null;
        }
    }
    
    /**
     * Fires the style change even to the listeners
     *
     * @param property the property name for the event
     * @param source the style firing the event
     */
    public void fireStyleChangeEvent(String property, Style source) {
        if(listeners == null || listeners.size() == 0) {
            return;
        }
        StyleListener[] array;
        synchronized(this) {
            array = new StyleListener[listeners.size()];
            for(int iter = 0 ; iter < array.length ; iter++) {
                array[iter] = (StyleListener)listeners.elementAt(iter);
            }
        }
        // if we already are on the EDT just fire the event
        if(Display.getInstance().isEdt()) {
            fireStyleChangeSync(array, property, source);
        } else if (fireStyleEventsOnNonEDT) {
            pending = array;
            pendingEvent = new Object[] {property, source};
            Display.getInstance().callSerially(new CallbackClass());
            pending = null;
            pendingEvent = null;
        }
    }

    /**
     * Synchronious internal call for common code
     */
    private void fireDataChangeSync(DataChangedListener[] array, int type, int index) {
        for(int iter = 0 ; iter < array.length ; iter++) {
            array[iter].dataChanged(type, index);
        }
    }
    
    /**
     * Synchronious internal call for common code
     */
    private void fireStyleChangeSync(StyleListener[] array, String property, Style source) {
        for(int iter = 0 ; iter < array.length ; iter++) {
            array[iter].styleChanged(property, source);
        }
    }

    /**
     * Synchronious internal call for common code
     */
    private void fireSelectionSync(SelectionListener[] array, int oldSelection, int newSelection) {
        for(int iter = 0 ; iter < array.length ; iter++) {
            array[iter].selectionChanged(oldSelection, newSelection);
        }
    }
    
    /**
     * Fires the event safely on the EDT without risk of concurrency errors
     * 
     * @param ev the ActionEvent to fire to the listeners
     */
    public void fireActionEvent(ActionEvent ev) {
        if(listeners == null || listeners.size() == 0) {
            return;
        }
        ActionListener[] array;
        synchronized(this) {
            array = new ActionListener[listeners.size()];
            for(int iter = 0 ; iter < array.length ; iter++) {
                array[iter] = (ActionListener)listeners.elementAt(iter);
            }
        }
        // if we already are on the EDT just fire the event
        if(Display.getInstance().isEdt()) {
            fireActionSync(array, ev);
        } else {
            pending = array;
            pendingEvent = ev;
            if(blocking) {
                Display.getInstance().callSeriallyAndWait(callback);
            } else {
                Display.getInstance().callSerially(new CallbackClass());
            }
            pending = null;
            pendingEvent = null;
            
        }
    }


    /**
     * Fires the event safely on the EDT without risk of concurrency errors
     * 
     * @param oldSelection old selection
     * @param newSelection new selection
     */
    public void fireSelectionEvent(int oldSelection, int newSelection) {
        if(listeners == null || listeners.size() == 0) {
            return;
        }
        SelectionListener[] array;
        synchronized(this) {
            array = new SelectionListener[listeners.size()];
            for(int iter = 0 ; iter < array.length ; iter++) {
                array[iter] = (SelectionListener)listeners.elementAt(iter);
            }
        }
        // if we already are on the EDT just fire the event
        if(Display.getInstance().isEdt()) {
            fireSelectionSync(array, oldSelection, newSelection);
        } else {
            pending = array;
            pendingEvent = new int[] {oldSelection, newSelection};
            if(blocking) {
                Display.getInstance().callSeriallyAndWait(callback);
            } else {
                Display.getInstance().callSerially(new CallbackClass());
            }
            pending = null;
            pendingEvent = null;            
        }
    }
    
    /**
     * Synchronious internal call for common code
     */
    private void fireActionSync(ActionListener[] array, ActionEvent ev) {
        for(int iter = 0 ; iter < array.length ; iter++) {
            if(!ev.isConsumed()) {
                array[iter].actionPerformed(ev);
            }
        }
    }
    
    /**
     * Fires the event safely on the EDT without risk of concurrency errors
     * 
     * @param c the Component that gets the focus event
     */
    public void fireFocus(Component c) {
        if(listeners == null || listeners.size() == 0) {
            return;
        }
        FocusListener[] array;
        synchronized(this) {
            array = new FocusListener[listeners.size()];
            for(int iter = 0 ; iter < array.length ; iter++) {
                array[iter] = (FocusListener)listeners.elementAt(iter);
            }
        }
        // if we already are on the EDT just fire the event
        if(Display.getInstance().isEdt()) {
            fireFocusSync(array, c);
        } else {
            pending = array;
            pendingEvent = c;
            if(blocking) {
                Display.getInstance().callSeriallyAndWait(callback);
            } else {
                Display.getInstance().callSerially(new CallbackClass());
            }
            pending = null;
            pendingEvent = null;            
        }
    }
    
    /**
     * Synchronious internal call for common code
     */
    private void fireFocusSync(FocusListener[] array, Component c) {
        if(c.hasFocus()) {
            for(int iter = 0 ; iter < array.length ; iter++) {
                array[iter].focusGained(c);
            }
        } else {
            for(int iter = 0 ; iter < array.length ; iter++) {
                array[iter].focusLost(c);
            }
        }
    }

    /**
     * Indicates whether this dispatcher blocks when firing events or not, normally
     * a dispatcher uses callSeriallyAndWait() to be 100% synchronos with event delivery
     * however this method is very slow. By setting blocking to false the callSerially
     * method is used which allows much faster execution for IO heavy operations.
     *
     * @return the blocking state
     */
    public boolean isBlocking() {
        return blocking;
    }

    /**
     * Indicates whether this dispatcher blocks when firing events or not, normally
     * a dispatcher uses callSeriallyAndWait() to be 100% synchronos with event delivery
     * however this method is very slow. By setting blocking to false the callSerially
     * method is used which allows much faster execution for IO heavy operations.
     * 
     * @param blocking the blocking value
     */
    public void setBlocking(boolean blocking) {
        this.blocking = blocking;
    }
}

