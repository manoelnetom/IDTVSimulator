package com.sun.dtv.lwuit;

class RunnableWrapper implements Runnable {
    private boolean done = false;
    private Runnable internal;
    private int type;
    private RuntimeException err;
    private Form parentForm;
    private Painter paint;

    public RunnableWrapper(Form parentForm, Painter paint) {
        this.parentForm = parentForm;
        this.paint = paint;
    }
    
    public RunnableWrapper(Runnable internal, int type) {
        this.internal = internal;
        this.type = type;
    }

    public RuntimeException getErr() {
        return err;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
    
    public boolean isDone() {
        return done;
    }

    public void run() {
        if(parentForm != null) {
            // set current form uses this portion to make sure all set current operations
            // occur on the EDT
            if(paint == null) {
                Display.getInstance().setCurrent(parentForm);
                return;
            }
            
            Dialog dlg = (Dialog)parentForm;
            while (!dlg.isDisposed()) {
                try {
                    synchronized(Display.lockDialog) {
                        Display.lockDialog.wait();
                    }
                } catch (InterruptedException ex) {
                }
            }
            parentForm.getStyle().setBgPainter(paint);
        } else {
            switch(type) {
                case 0: 
                    internal.run();
                    done = true;
                    synchronized(Display.lockDialog) {
                        Display.lockDialog.notify();
                    }
                    break;
                case 1:
                    try {
                        //internal.run();
                    } catch(RuntimeException ex) {
                        this.err = ex;
                    }
                    break;
                case 2:
                    while(!done) {
                        synchronized(Display.lockDialog) {
                            try {
                                Display.lockDialog.wait();
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                    break;
            }
        }
    }
}
