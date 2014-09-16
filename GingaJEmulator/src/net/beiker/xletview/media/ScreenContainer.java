/*

 This file is part of XleTView 
 Copyright (C) 2003 Martin Sveden

 This is free software, and you are 
 welcome to redistribute it under 
 certain conditions;

 See LICENSE document for details.

 */
package net.beiker.xletview.media;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import net.beiker.cake.Log;
import net.beiker.cake.Logger;
// Jeff:: import net.beiker.xletview.event.EventManager;
import br.org.sbtvd.event.EventManager;
import static java.awt.image.ImageObserver.WIDTH;
import net.beiker.xletview.ui.ProgressBar;
import net.beiker.xletview.ui.SafeArea;
import net.beiker.xletview.ui.XContainer;
import net.beiker.xletview.util.Settings;
import net.beiker.xletview.util.Util;
import net.beiker.xletview.window.TvWindow;

/**
 * Has control over the background, video and graphics layer. There is also a
 * fourth layer on top of the others for displaying additional stuff like safe
 * area etc.
 *
 */
public class ScreenContainer extends Container {

    private static final Logger log = Log.getLogger(ScreenContainer.class);
    private static ScreenContainer THE_INSTANCE;
    // index 3 adds it last and therefore in the background
    public final static int BACKGROUND_LAYER = 3;
    public final static int VIDEO_LAYER = 2;
    // index 1 adds it first and therefore in the foreground
    public final static int GRAPHICS_LAYER = 1;
    // layer on top of everything that belongs to the emulator, for safe area marking etc.
    private final static int EMULATOR_LAYER = 0;
    private XContainer[] layers;
    private static int screenX = 0;
    private static int screenY = 0;
    public static int SCREEN_WIDTH = Util.parseInt(Settings.getProperty("tv.screenwidth"));
    public static int SCREEN_HEIGHT = Util.parseInt(Settings.getProperty("tv.screenheight"));
    private boolean eventEnabled;
    private static ProgressBar progressBar;

    /**
     * Gets the instance attribute of the TV class
     *
     * @return The instance value
     */
    public static ScreenContainer getInstance() {
        if (THE_INSTANCE == null) {
            THE_INSTANCE = new ScreenContainer();
        }
        return THE_INSTANCE;
    }

    public Dimension getPreferredSize() {
        return new Dimension(720, 576);
    }

    /**
     * Constructor for the TV object
     */
    private ScreenContainer() {
        Toolkit.getDefaultToolkit().addAWTEventListener(EventManager.getInstance(), AWTEvent.KEY_EVENT_MASK);
        Toolkit.getDefaultToolkit().addAWTEventListener(EventManager.getInstance(), AWTEvent.FOCUS_EVENT_MASK);

        com.sun.dtv.lwuit.Display.getInstance().init();

        // catch all errors
        try {
            setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
            setLayout(null);
            // the layers on screen

            layers = new XContainer[4];

            layers[ScreenContainer.BACKGROUND_LAYER] = BackgroundLayer.getInstance();
            layers[ScreenContainer.VIDEO_LAYER] = VideoLayer.getInstance();
            layers[ScreenContainer.GRAPHICS_LAYER] = com.sun.dtv.lwuit.Display.getInstance().getImplementation().getFrame(); // new XContainer();
            layers[ScreenContainer.EMULATOR_LAYER] = new XContainer();


            for (int i = 0; i < layers.length; i++) {
                if (i == GRAPHICS_LAYER) {
                    continue;
                } else if (i == EMULATOR_LAYER) {
                    layers[i].setBounds(screenX, screenY, 0, 0);
                    add(layers[i]);
                } else {
                    layers[i].setBounds(screenX, screenY, SCREEN_WIDTH, SCREEN_HEIGHT);
                    add(layers[i]);
                }
            }
// progress bar
            progressBar = new ProgressBar(SCREEN_WIDTH, 2, Color.GREEN, Color.BLACK);
            layers[ScreenContainer.EMULATOR_LAYER].add(progressBar);

            try {
                String strX = Settings.getProperty("safearea.x");
                int x = Integer.parseInt(strX);
                String strY = Settings.getProperty("safearea.y");
                int y = Integer.parseInt(strY);
                String strWidth = Settings.getProperty("safearea.width");
                int width = Integer.parseInt(strWidth);
                String strHeight = Settings.getProperty("safearea.height");
                int height = Integer.parseInt(strHeight);
                String hexColor = Settings.getProperty("safearea.color");
//                if (Settings.getProperty("safearea.show").equals("true")) {
//                    layers[ScreenContainer.EMULATOR_LAYER].add(new SafeArea(x, y, width, height, 3, Color.decode(hexColor)));
//                    layers[ScreenContainer.EMULATOR_LAYER].add(new SafeArea(x, y, 350, 160, 3, Color.decode(hexColor)));
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        eventEnabled = true;
    }

    public Container getEmulatorContainer() {
        return layers[ScreenContainer.EMULATOR_LAYER];
    }

    public Container getXletContainer() {
        return layers[ScreenContainer.GRAPHICS_LAYER];
    }

    public Container getBackgroundLayer() {
        return layers[ScreenContainer.BACKGROUND_LAYER];
    }

    public static void showProgressBar() {
        progressBar.setVisible(true);
    }

    public static void hideProgressBar() {
        progressBar.setVisible(false);
    }

    public static void updateProgressBar(int procent) {
        progressBar.update(procent);
    }

    public void update(Graphics g) {
        paint(g);
    }

    public void setEmulatorLayerSize(int width, int height){
        layers[EMULATOR_LAYER].setBounds(screenX, screenY, width, height);
    }
    
    public void setEmulatorLayerVisible(boolean isVisible) {
        //TODO: Felipe: Aqui muda o tamanho de exibição do xlet
        if (isVisible) {
            layers[EMULATOR_LAYER].setBounds(screenX, screenY, 350, 160);
//            layers[EMULATOR_LAYER].setBounds(screenX, screenY, 800, 600);
        } else {
            layers[EMULATOR_LAYER].setBounds(screenX, screenY, 0, 0);
        }
    }

    public void paint(Graphics g) {
        java.awt.Image buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = (Graphics2D) buffer.getGraphics();

        layers[ScreenContainer.BACKGROUND_LAYER].paint(g2d);
        layers[ScreenContainer.VIDEO_LAYER].paint(g2d);
        layers[ScreenContainer.GRAPHICS_LAYER].paint(g2d);
        layers[ScreenContainer.EMULATOR_LAYER].paint(g2d);
        g.drawImage(buffer, 0, 0, null);

        g2d.dispose();
    }
}
