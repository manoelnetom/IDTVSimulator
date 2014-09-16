/*

 This file is part of XleTView 
 Copyright (C) 2003 Martin Sveden
 
 This is free software, and you are 
 welcome to redistribute it under 
 certain conditions;

 See LICENSE document for details.

 */
package net.beiker.xletview.window;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

public class AboutWindow extends JDialog {

    private static final net.beiker.cake.Logger log = net.beiker.cake.Log.getLogger(AboutWindow.class);

    public AboutWindow(Frame owner) {
        super(owner, false);

        setTitle("GingaJ Test Tool");
        
        JTextArea area = new JTextArea("Ferramenta desenvolvida pelo LAVID");
        area.setEnabled(false);
        
        add(area);
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                doClose();
            }
        });

        // pack();
        
        setSize(320, 240);
        GraphicsConfiguration gc = this.getGraphicsConfiguration();
        Rectangle bounds = gc.getBounds();
        int x = (int) (bounds.getWidth() - this.getWidth()) / 2;
        int y = (int) (bounds.getHeight() - this.getHeight()) / 2;
        setLocation(x, y);
        
        show();
    }

    private void doClose() {
        this.dispose();
    }
}
