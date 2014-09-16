package br.org.sbtvd.event;

import com.sun.dtv.lwuit.Display;
import com.sun.dtv.ui.event.RemoteControlEvent;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;

public class EventManager implements AWTEventListener {

    private static EventManager instance;
    private boolean eventEnabled;
    private Component focusOwner;

    public static EventManager getInstance() {
        if (instance == null) {
            instance = new EventManager();
        }
        return instance;
    }

    private EventManager() {
        // Jeff:: java.awt.Toolkit.getDefaultToolkit().addAWTEventListener(this, AWTEvent.KEY_EVENT_MASK | AWTEvent.FOCUS_EVENT_MASK);
    }

    /**
     * Returns a new key code that is mapped with the incoming or the same as
     * the incoming if it was not mapped.
     *
     * @param incoming The key code to convert
     * @return a key code
     */
    public int convertCode(int incoming) {
        int outgoing;

        switch (incoming) {
            case 112: // F1
            case 61697: // F1
            case 61506: // RED Control
                // outgoing = 403;
                outgoing = RemoteControlEvent.VK_COLORED_KEY_0;
                break;
            case 113: // F1
            case 61698: // F1
            case 61507: // GREEN Control
                // outgoing = 404;
                outgoing = RemoteControlEvent.VK_COLORED_KEY_1;
                break;
            case 114: // F1
            case 61699: // F1
            case 61508: // YELLOW Control
                // outgoing = 405;
                outgoing = RemoteControlEvent.VK_COLORED_KEY_2;
                break;
            case 115: // F1
            case 61700: // F1
            case 61509: // BLUE Control
                // outgoing = 406;
                outgoing = RemoteControlEvent.VK_COLORED_KEY_3;
                break;
            case java.awt.event.KeyEvent.VK_ENTER:
                outgoing = RemoteControlEvent.VK_CONFIRM;
                break;
            case 8: //BACK Control
            case 61512:
                outgoing = RemoteControlEvent.VK_BACK_SPACE;
                break;
            default:
                outgoing = incoming;
        }

        return outgoing;
    }

    /**
     *
     * 1. Passes event to org.dvb.event.EventManager that is in charge of
     * processing all java.awt.KeyEvent and org.dvb.event.UserEvent
     *
     * 2. Fires HFocusEvent
     *
     * 3. Handles emulator specific events from the "remote control" or other
     * input devices such as keyboard or mouse etc.
     */
    public void fireEvents(KeyEvent event) {
        int code = convertCode(event.getKeyCode());

        if (event != null) {
            com.sun.dtv.ui.Device device = com.sun.dtv.ui.Device.getInstance();
            com.sun.dtv.ui.Screen currentScreen = device.getDefaultScreen();
            com.sun.dtv.ui.event.UserInputEventManager manager = com.sun.dtv.ui.event.UserInputEventManager.getUserInputEventManager(currentScreen);

            manager.dispatchUserInputEvent(
                    new com.sun.dtv.ui.event.RemoteControlEvent((com.sun.dtv.lwuit.Component) null,
                    event.getID(), 0, 0, code, (char) event.getKeyChar()));
        }

    }

    public void fireRemoteEvent(KeyEvent event) {
        if (focusOwner != null) {
            fireEvents(new KeyEvent(focusOwner, event.getID(), 0L, 0, event.getKeyCode(), (char) event.getKeyCode()));
        } else {
            fireEvents(event);
        }
    }

    public boolean isChildOf(Container possibleParent, Component comp) {
        boolean result = false;

        if (possibleParent != null && comp != null) {
            Component parent = comp.getParent();
            if (parent == possibleParent) {
                result = true;
            } else if (parent != null) {
                result = isChildOf(possibleParent, parent);
            }
        }
        return result;
    }

    public Component getFocusOwner() {
        return focusOwner;
    }

    public boolean isEventEnabled() {
        return eventEnabled;
    }

    public void setEventEnabled(boolean b) {
        eventEnabled = b;
    }

    public void eventDispatched(AWTEvent e) {
        Object source = e.getSource();

        if (e instanceof KeyEvent) {
            KeyEvent ke = (KeyEvent) e;

            // consume if it's the focus owner
            if (source == focusOwner) {
                ke.consume();
            }

            fireEvents(ke);
        }
    }
}
