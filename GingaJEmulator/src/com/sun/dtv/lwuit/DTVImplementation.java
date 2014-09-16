package com.sun.dtv.lwuit;

import com.sun.dtv.lwuit.animations.Animation;

import java.awt.GraphicsEnvironment;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Transparency;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import net.beiker.xletview.media.ScreenContainer;
import net.beiker.xletview.ui.XContainer;
import xjava.awt.Toolkit;
import br.org.sbtvd.event.EventManager;
import br.org.sbtvd.config.Settings;

/**
 * Represents a vendor extension mechanizm for LWUIT, <b>WARNING: this class is for internal
 * use only and is subject to change in future API revisions</b>. To replace the way in which
 * LWUIT performs its task this class can be extended and its functionality replaced or
 * enhanced.
 * <p>It is the responsibility of the implementation class to grab and fire all events to the 
 * Display specifically for key, pointer events and screen resolution.
 * 
 * @author Shai Almog
 */
class FrameAdapter extends XContainer { // java.awt.Frame {

    private java.awt.Image buffer;

    public FrameAdapter() {
        int x = Integer.parseInt(Settings.getInstance().getProperty("tv.x"));
        int y = Integer.parseInt(Settings.getInstance().getProperty("tv.y"));
        int width = Integer.parseInt(Settings.getInstance().getProperty("tv.screenwidth"));
        int height = Integer.parseInt(Settings.getInstance().getProperty("tv.screenheight"));

        setBounds(x, y, width, height);

        setLayout(null);
        setBackground(new Color(0x00, 0x00, 0x00, 0x00));
        setVisible(true);

        /*
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gs = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gs.getDefaultConfiguration();
        
        buffer = gc.createCompatibleImage(1280, 720);
         * 
         */

        buffer = new BufferedImage(1280, 720, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = (Graphics2D) buffer.getGraphics();

        g2d.setBackground(new Color(0x00, 0x00, 0x00, 0x00));
        g2d.clearRect(0, 0, 1280, 720);

        add(com.sun.dtv.lwuit.Display.getInstance().getRootContainer());
    }

    public void clearRect() {
        Graphics2D g2d = (Graphics2D) buffer.getGraphics();

        g2d.setBackground(new Color(0x00, 0x00, 0x00, 0x00));
        g2d.clearRect(0, 0, 1280, 720);
    }

    public java.awt.Graphics getBufferGraphics() {
        return buffer.getGraphics();
    }

    public void update(java.awt.Graphics g) {
        paint(g);
    }

    public void paint(java.awt.Graphics g) {
        if (buffer != null) {
            java.awt.Container container = Display.getInstance().getRootContainer();
            int sx = 0,
                    sy = 0;
            int x = container.getX(),
                    y = container.getY(),
                    w = container.getWidth(),
                    h = container.getHeight();

            if (x > getWidth() || y > getHeight()) {
                return;
            }

            com.sun.dtv.lwuit.Graphics lg = Display.getInstance().getImplementation().getNativeGraphics();

            if (lg == null) {
                return;
            }

            int tx = lg.getTranslateX(),
                    ty = lg.getTranslateX();

            if (x < 0) {
                w = w + x;
                x = 0;
                sx = -x;
            }

            if (y < 0) {
                h = h + y;
                y = 0;
                sy = -y;
            }

            if (w < 0 || h < 0) {
                return;
            }

            if ((x + w) > 1280) {
                w = 1280 - x;
            }

            if ((y + h) > 720) {
                h = 720 - y;
            }

            lg.translate(-tx, -ty);

            g.drawImage(buffer, x, y, x + w, y + h, sx, sy, sx + w, sy + h, null);

        }
    }
}

public class DTVImplementation {

    private FrameAdapter frame;
    private Form currentForm;
    private static Object displayLock;
    private Animation[] paintQueue = new Animation[50];
    private Animation[] paintQueueTemp = new Animation[50];
    private int paintQueueFill = 0;
    private Graphics lwuitGraphics;
    /**
     * Useful since the content of a single element touch event is often recycled
     * and always arrives on 1 thread. Even on multi-tocuh devices a single coordinate
     * touch event should be very efficient
     */
    private int[] xPointerEvent = new int[1];
    /**
     * Useful since the content of a single element touch event is often recycled
     * and always arrives on 1 thread. Even on multi-tocuh devices a single coordinate
     * touch event should be very efficient
     */
    private int[] yPointerEvent = new int[1];

    /**
     * Invoked by the display init method allowing the implementation to "bind"
     * 
     * @param m the object passed to the Display init method
     */
    public void init(Object o) {
        EventManager.getInstance().setEventEnabled(true);

        frame = new FrameAdapter();

        lwuitGraphics = new com.sun.dtv.lwuit.Graphics((java.awt.Graphics2D) frame.getBufferGraphics());
    }

    public XContainer getFrame() {
        return frame;
    }

    public int convertKeyCode(int key) {
        if (key == 13) {
            key = KeyEvent.VK_ENTER;
        } else if (key == 61440) {
            key = KeyEvent.VK_LEFT;
        } else if (key == 61441) {
            key = KeyEvent.VK_RIGHT;
        } else if (key == 61442) {
            key = KeyEvent.VK_UP;
        } else if (key == 61443) {
            key = KeyEvent.VK_DOWN;
        } else if (key == 61559) {
            key = KeyEvent.VK_BACK_SPACE;
        }

        return key;
    }

    public void keyPress(int key) {
        int code = convertKeyCode(key),
                symbol = code;

        System.out.println("InputClientListener -> keyPress: " + code);

        EventManager.getInstance().eventDispatched(new KeyEvent(frame, KeyEvent.KEY_PRESSED, 0L, 0, code, KeyEvent.CHAR_UNDEFINED));
    }

    public void keyRelease(int key) {
        int code = convertKeyCode(key),
                symbol = code;

        System.out.println("InputClientListener -> keyRelease: " + key);

        EventManager.getInstance().eventDispatched(new KeyEvent(frame, KeyEvent.KEY_RELEASED, 0L, 0, code, KeyEvent.CHAR_UNDEFINED));
    }

    public Graphics getNativeGraphics() {
        return lwuitGraphics;
    }

    /**
     * Returns the width dimention of the display controlled by this implementation
     * 
     * @return the width
     */
    public int getDisplayWidth() {
        return frame.getWidth();
    }

    /**
     * Returns the height dimention of the display controlled by this implementation
     * 
     * @return the height
     */
    public int getDisplayHeight() {
        return frame.getHeight();
    }

    /**
     * Invoked when an exception occurs on the EDT, allows the implementation to
     * take control of the device to produce testing information.
     * 
     * @param err the exception that was caught in the EDT loop
     * @return false by default, true if the exception shouldn't be handled further 
     * by the EDT
     */
    public boolean handleEDTException(Throwable err) {
        return false;
    }

    /**
     * Returns true if the implementation still has elements to paint.
     * 
     * @return false by default
     */
    public boolean hasPendingPaints() {
        return paintQueueFill != 0;
    }

    /**
     * Returns the video control for the media player
     * 
     * @param player the media player
     * @return the video control for the media player
     */
    public Object getVideoControl(Object player) {
        return null;
    }

    /**
     * Return the number of alpha levels supported by the implementation.
     * 
     * @return the number of alpha levels supported by the implementation
     */
    public int numAlphaLevels() {
        return 255;
    }

    /**
     * Returns the number of colors applicable on the device, note that the API
     * does not support gray scale devices.
     * 
     * @return the number of colors applicable on the device
     */
    public int numColors() {
        return 65536;
    }

    /**
     * Allows for painting an overlay on top of the implementation for notices during
     * testing etc.
     * 
     * @param g graphics context on which to draw the overlay
     */
    protected void paintOverlay(Graphics g) {
    }

    /**
     * Invoked by the EDT to paint the dirty regions
     */
    public void paintDirty() {
        int size = 0;
        synchronized (displayLock) {
            size = paintQueueFill;
            Animation[] array = paintQueue;
            paintQueue = paintQueueTemp;
            paintQueueTemp = array;
            paintQueueFill = 0;
        }

        if (size > 0) {
            System.gc();

            Graphics wrapper = getLWUITGraphics();
            int topX = getDisplayWidth();
            int topY = getDisplayHeight();
            int bottomX = 0;
            int bottomY = 0;

            wrapper.translate(-wrapper.getTranslateX(), -wrapper.getTranslateY());
            wrapper.setClip(0, 0, getDisplayWidth(), getDisplayHeight());

            frame.clearRect();

            // getCurrentForm().update(wrapper);
            getCurrentForm().paint(wrapper);

            for (int iter = 0; iter < size; iter++) {
                paintQueueTemp[iter] = null;
            }

            Popup popup = getCurrentForm().getPopup();
            if (popup != null) {
                Component cmp = popup.getContents();
                com.sun.dtv.lwuit.geom.Rectangle dirty = cmp.getDirtyRegion();
                if (dirty != null) {
                    wrapper.setClip(dirty.getX(), dirty.getY(), dirty.getSize().getWidth(), dirty.getSize().getHeight());
                    cmp.setDirtyRegion(null);
                }
                cmp.update(wrapper);
                cmp.paint(wrapper);

            }

            paintOverlay(wrapper);

            flushGraphics(topX, topY, bottomX - topX, bottomY - topY);
            
            System.gc();
        }
    }

    public void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception e) {
        }
    }
    
    /**
     * Flush the currently painted drawing onto the screen if using a double buffer
     * 
     * @param x position of the dirty region
     * @param y position of the dirty region
     * @param width width of the dirty region
     * @param height height of the dirty region
     */
    public void flushGraphics(int x, int y, int width, int height) {
        ScreenContainer.getInstance().repaint();
        
        Toolkit.getDefaultToolkit().sync();
        
        delay(20);        
    }

    /**
     * Flush the currently painted drawing onto the screen if using a double buffer
     */
    public void flushGraphics() {
        ScreenContainer.getInstance().repaint();
    }

    /**
     * Returns a graphics object for use by the painting
     * 
     * @return a graphics object, either recycled or new, this object will be 
     * used on the EDT
     */
    protected Graphics getLWUITGraphics() {
        return getNativeGraphics();
    }

    /**
     * Installs the LWUIT graphics object into the implementation
     * 
     * @param g graphics object for use by the implementation
     */
    public void setLWUITGraphics(Graphics g) {
        lwuitGraphics = g;
    }

    /**
     * Installs the display lock allowing implementors to synchronize against the 
     * Display mutex, this method is invoked internally and should not be used.
     * 
     * @param lock the mutex from display
     */
    public void setDisplayLock(Object lock) {
        displayLock = lock;
    }

    /**
     * Returns a lock object which can be synchrnoized against, this lock is used
     * by the EDT.
     * 
     * @return a lock object
     */
    public Object getDisplayLock() {
        return displayLock;
    }

    /**
     * Invoked to add an element to the paintQueue
     * 
     * @param cmp component or animation to push into the paint queue
     */
    public void repaint(Animation cmp) {
        synchronized (displayLock) {
            for (int iter = 0; iter < paintQueueFill; iter++) {
                Animation ani = paintQueue[iter];
                if (ani == cmp) {
                    return;
                }
            }
            // overcrowding the queue don't try to grow the array!
            if (paintQueueFill >= paintQueue.length) {
                System.out.println("Warning paint queue size exceeded, please watch the amount of repaint calls");
                return;
            }

            paintQueue[paintQueueFill] = cmp;
            paintQueueFill++;
            displayLock.notify();
        }
    }

    /**
     * Returns the number of softkeys on the device
     * 
     * @return the number of softkey buttons on the device
     */
    public int getSoftkeyCount() {
        return 0;
    }

    /**
     * Returns the softkey keycode for the given softkey index
     * 
     * @param index the index of the softkey
     * @return the set of keycodes which can indicate the softkey, multiple keycodes
     * might apply to the same functionality
     */
    public int[] getSoftkeyCode(int index) {
        // TODO::
        return null;
    }

    /**
     * Returns the keycode for the clear key
     * 
     * @return the system key code for this device
     */
    public int getClearKeyCode() {
        // TODO::
        return 0;
    }

    /**
     * Returns the keycode for the backspace key
     * 
     * @return the system key code for this device
     */
    public int getBackspaceKeyCode() {
        // TODO::
        return 0;
    }

    /**
     * Returns the keycode for the back key
     * 
     * @return the system key code for this device
     */
    public int getBackKeyCode() {
        // TODO::
        return 0;
    }

    /**
     * Returns the display game action for the given keyCode if applicable to match
     * the contrct of LWUIT for the game action behavior
     * 
     * @param keyCode the device keycode
     * @return a game action or 0
     */
    public int getGameAction(int keyCode) {
        if (keyCode == com.sun.dtv.ui.event.RemoteControlEvent.VK_CONFIRM) {
            return Display.GAME_FIRE;
        } else if (keyCode == com.sun.dtv.ui.event.RemoteControlEvent.VK_LEFT) {
            return Display.GAME_LEFT;
        } else if (keyCode == com.sun.dtv.ui.event.RemoteControlEvent.VK_RIGHT) {
            return Display.GAME_RIGHT;
        } else if (keyCode == com.sun.dtv.ui.event.RemoteControlEvent.VK_UP) {
            return Display.GAME_UP;
        } else if (keyCode == com.sun.dtv.ui.event.RemoteControlEvent.VK_DOWN) {
            return Display.GAME_DOWN;
        }

        return -1;
    }

    /**
     * Returns a keycode which can be sent to getGameAction
     * 
     * @param gameAction the game action
     * @return key code matching the given game action
     */
    public int getKeyCode(int gameAction) {
        if (gameAction == Display.GAME_FIRE) {
            return com.sun.dtv.ui.event.RemoteControlEvent.VK_CONFIRM;
        } else if (gameAction == Display.GAME_LEFT) {
            return com.sun.dtv.ui.event.RemoteControlEvent.VK_LEFT;
        } else if (gameAction == Display.GAME_RIGHT) {
            return com.sun.dtv.ui.event.RemoteControlEvent.VK_RIGHT;
        } else if (gameAction == Display.GAME_UP) {
            return com.sun.dtv.ui.event.RemoteControlEvent.VK_UP;
        } else if (gameAction == Display.GAME_DOWN) {
            return com.sun.dtv.ui.event.RemoteControlEvent.VK_DOWN;
        }

        return -1;
    }

    /**
     * This method is used internally to determine the actual current form
     * it doesn't perform the logic of transitions etc. and shouldn't be invoked
     * by developers
     * 
     * @param f the current form
     */
    public void setCurrentForm(Form f) {
        currentForm = f;
    }

    /**
     * Callback method allowing the implementation to confirm that it controls the
     * view just before a new form is installed.
     */
    public void confirmControlView() {
    }

    /**
     * Returns the current form, this method is for internal use only and does not
     * take transitions/menus into consideration
     * 
     * @return The internal current form
     */
    public Form getCurrentForm() {
        return currentForm;
    }

    /**
     * LWUIT can translate all coordinates and never requires a call to translate
     * this works well for some devices which have hairy issues with translate.
     * However for some platforms where translate can be leveraged with affine transforms
     * this can be a problem. These platforms can choose to translate on their own
     * 
     * @return true if the implementation is interested in receiving translate calls
     * and handling them.
     */
    public boolean isTranslationSupported() {
        return false;
    }

    /**
     * Translates the X/Y location for drawing on the underlying surface. Translation
     * is incremental so the new value will be added to the current translation and
     * in order to reset translation we have to invoke 
     * {@code translate(-getTranslateX(), -getTranslateY()) }
     * 
     * @param graphics the graphics context
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public void translate(Object graphics, int x, int y) {
    }

    /**
     * Returns the current x translate value 
     * 
     * @param graphics the graphics context
     * @return the current x translate value 
     */
    public int getTranslateX(Object graphics) {
        return 0;
    }

    /**
     * Returns the current y translate value 
     * 
     * @param graphics the graphics context
     * @return the current y translate value 
     */
    public int getTranslateY(Object graphics) {
        return 0;
    }

    /**
     * Displays the virtual keyboard on devices that support manually poping up
     * the vitual keyboard
     * 
     * @param show toggles the virtual keyboards visibility
     */
    public void setShowVirtualKeyboard(boolean show) {
    }

    /**
     * Indicates whether showing a virtual keyboard programmatically is supported 
     * 
     * @return false by default
     */
    public boolean isVirtualKeyboardShowingSupported() {
        return false;
    }

    /**
     * Returns the type of the input device one of:
     * KEYBOARD_TYPE_UNKNOWN, KEYBOARD_TYPE_NUMERIC, KEYBOARD_TYPE_QWERTY, 
     * KEYBOARD_TYPE_VIRTUAL, KEYBOARD_TYPE_HALF_QWERTY
     * 
     * @return KEYBOARD_TYPE_UNKNOWN
     */
    public int getKeyboadType() {
        return Display.KEYBOARD_TYPE_UNKNOWN;
    }

    /**
     * Indicates whether the device supports native in place editing in which case
     * lightweight input logic shouldn't be used for input.
     * 
     * @return false by default
     */
    public boolean isNativeInputSupported() {
        return false;
    }

    /**
     * Encapsulates the editing code which is specific to the platform, some platforms
     * would allow "in place editing" MIDP does not.
     * 
     * @param cmp the {@link TextArea} component
     * @param maxSize the maximum size from the text area
     * @param constraint the constraints of the text area
     * @param text the string to edit
     */
    public void editString(Component cmp, int maxSize, int constraint, String text) {
        /*
        UIManager m = UIManager.getInstance();
        CONFIRM_COMMAND =
        new Command(m.localize("ok", "OK"), Command.OK, 1);
        CANCEL_COMMAND =
        new Command(m.localize("cancel", "Cancel"), Command.CANCEL, 2);
        currentTextBox =
        new TextBox("", "", maxSize, TextArea.ANY);
        currentTextBox.setCommandListener(new CommandListener() {
        
        public void commandAction(Command c, Displayable d) {
        if (d == currentTextBox) {
        if (c == CONFIRM_COMMAND) {
        // confirm
        String text = currentTextBox.getString();
        Display.getInstance().onEditingComplete(currentTextComponent, text);
        }
        
        currentTextBox = null;
        waitForEdit.setDone(true);
        
        // we must return to the LWUIT thread otherwise there is a risk of the MIDP
        // thread blocking on dialog.show calls essentially breaking text editing in dialogs
        Display.getInstance().callSerially(new Runnable() {
        
        public void run() {
        if (currentTextComponent.getComponentForm() == Display.getInstance().getCurrent()) {
        currentTextComponent.getComponentForm().show();
        }
        
        }
        });
        }
        }
        });
        currentTextBox.addCommand(CONFIRM_COMMAND);
        currentTextBox.addCommand(CANCEL_COMMAND);
        currentTextComponent = cmp;
        currentTextBox.setMaxSize(maxSize);
        currentTextBox.setString(text);
        currentTextBox.setConstraints(constraint);
        display.setCurrent(currentTextBox);
        waitForEdit = new WaitForEdit();
        waitForEdit.setDone(false);
        Display.getInstance().invokeAndBlock(waitForEdit);
         */
    }

    /**
     * @inheritDoc
     *
     */
    public void saveTextEditingState() {
        /*
        String text = currentTextBox.getString();
        Display.getInstance().onEditingComplete(currentTextComponent, text);
        currentTextBox = null;
        waitForEdit.setDone(true);
         */
    }
}
