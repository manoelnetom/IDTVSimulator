package com.sun.dtv.lwuit;

import com.sun.dtv.lwuit.geom.Dimension;
import com.sun.dtv.lwuit.events.ActionEvent;
import com.sun.dtv.lwuit.events.ActionListener;
import com.sun.dtv.lwuit.plaf.Border;
import com.sun.dtv.lwuit.plaf.Style;
import com.sun.dtv.lwuit.plaf.UIManager;
import com.sun.dtv.ui.event.RemoteControlEvent;
import com.sun.dtv.ui.ViewOnlyComponent;
import java.util.Iterator;

public class Button extends Label
{

	// Indicates the rollover state of a button which is equivalent to focused for most uses
	public static final int STATE_ROLLOVER = 0;
	// Indicates the pressed state of a button 
	public static final int STATE_PRESSED = 1;
	// Indicates the default state of a button which is neither pressed nor focused
	public static final int STATE_DEFAULT = 2;

	private java.util.List actionListeners;
	private Image pressedIcon;
	private Image rolloverIcon;
	private Command cmd;

	private int state = STATE_DEFAULT;

	/**
	 * Constructs a button with an empty string for its text.
	 *
	 */
	public Button()
	{
		this("", null);
	}

	/**
	 * Constructs a button with the specified text.
	 *
	 * 
	 * @param text - label appearing on the button
	 */
	public Button(String text)
	{
		this(text, null);
	}

	/**
	 * Allows binding a command to a button for ease of use.
	 *
	 * 
	 * @param cmd - command whose text would be used for the button and would  receive action events from the button
	 */
	public Button(Command cmd)
	{
		this(cmd.getCommandName(), cmd.getIcon());
		addActionListener(cmd);
		this.cmd = cmd;
		this.actionListeners = new java.util.ArrayList();
	}

	/**
	 * Constructs a button with the specified image.
	 *
	 * 
	 * @param icon - appearing on the button
	 */
	public Button(Image icon)
	{
		this("", icon);
	}

	/**
	 * Constructor a button with text and image.
	 *
	 * 
	 * @param text - label appearing on the button
	 * @param icon - image appearing on the button
	 */
	public Button(String text, Image icon)
	{
		super(text);
		setFocusable(true);
		setIcon(icon);
		setUIID("Button");
		this.pressedIcon = icon;
		this.rolloverIcon = icon;
		this.actionListeners = new java.util.ArrayList();
		Style s = this.getStyle();
		if(s != null && s.getBorder() == null)
		{
			Border border = new Border();
			Border focused = Border.createDoubleBorder(5);
			border.setFocusedInstance(focused);
			border.setPressedInstance(border.createPressedVersion());
			s.setBorder(border);
		}
		setVerticalAlignment(ViewOnlyComponent.VERTICAL_ALIGN_CENTER);
		setHorizontalAlignment(ViewOnlyComponent.HORIZONTAL_ALIGN_CENTER);
		
	}

	/**
	 * @inheritDoc
	 *
	 */
	void focusGainedInternal() {
		state = STATE_DEFAULT;
		setHandlesInput(true);
	}

	/**
	 * @inheritDoc
	 *
	 */
	void focusLostInternal() {
		state = STATE_DEFAULT;
		setHandlesInput(false);
	}

	/**
	 * Returns the button state.
	 *
	 * 
	 * @return One of STATE_ROLLOVER, STATE_DEAFULT, STATE_PRESSED
	 */
	public int getState()
	{
		return this.state;
	}

	/**
	 * Indicates the icon that is displayed on the button when the button is
	 * in pressed state.
	 *
	 * 
	 * @return icon used
	 * @see setPressedIcon(com.sun.dtv.lwuit.Image),  STATE_PRESSED
	 */
	public Image getPressedIcon()
	{
		return this.pressedIcon;
	}

	/**
	 * Indicates the icon that is displayed on the button when the button is
	 * in rolled over state.
	 *
	 * 
	 * @return icon used
	 * @see setRolloverIcon(com.sun.dtv.lwuit.Image),  STATE_ROLLOVER
	 */
	public Image getRolloverIcon()
	{
		return this.rolloverIcon;
	}

	/**
	 * Indicates the icon that is displayed on the button when the button is
	 * in rolled over state.
	 *
	 * 
	 * @param rolloverIcon - icon to use
	 * @see getRolloverIcon(),  STATE_ROLLOVER
	 */
	public void setRolloverIcon( Image rolloverIcon)
	{
		this.rolloverIcon = rolloverIcon;
		setShouldCalcPreferredSize(true);
		repaint();
	}

	/**
	 * Indicates the icon that is displayed on the button when the button
	 * is in pressed state.
	 *
	 * 
	 * @param pressedIcon - icon used
	 * @see getPressedIcon(),  STATE_PRESSED
	 */
	public void setPressedIcon( Image pressedIcon)
	{
		this.pressedIcon = pressedIcon;
		setShouldCalcPreferredSize(true);
		repaint();
	}

	/**
	 * Adds a listener to the button which will cause an event to dispatch on click.
	 *
	 * 
	 * @param l - implementation of the action listener interface
	 * @see removeActionListener(com.sun.dtv.lwuit.events.ActionListener)
	 */
	public void addActionListener( ActionListener l)
	{
		if (actionListeners.contains((Object)l) == false) {
			actionListeners.add((Object)l);
		}
	}

	/**
	 * Removes the given action listener from the button.
	 *
	 * 
	 * @param l - implementation of the action listener interface
	 * @see addActionListener(com.sun.dtv.lwuit.events.ActionListener)
	 */
	public void removeActionListener(ActionListener l)
	{
		if (actionListeners.contains((Object)l) == true) {
			actionListeners.remove((Object)l);
		}
	}

	/**
	 * @inheritDoc
	 *
	 */
	void fireActionEvent(){
		for (Iterator i=actionListeners.iterator(); i.hasNext(); ) {
			ActionListener listener = (ActionListener)i.next();

			if (cmd != null) {
				listener.actionPerformed(new ActionEvent(cmd));
			} else {
				listener.actionPerformed(new ActionEvent(this));
			}
		}
	}
	
	/**
	 * Invoked to change the state of the button to the pressed state
	 *
	 */
	void pressed()
	{
		if(isEnabled()) 
		{
			state=STATE_PRESSED;
			repaint();
			fireActionEvent();
		}
	}

	/**
	 * Invoked to change the state of the button to the released state
	 */
	void released()
	{
		if(isEnabled()) 
		{
			state=STATE_DEFAULT;
			repaint();
			fireActionEvent();
		}
	}

	/**
	 * <B>Description copied from class: <CODE><A HREF="../../../../com/sun/dtv/lwuit/Component.html#keyPressed(int)">Component</A></CODE></B></DD>
	 * If this Component is focused, the key pressed event
	 * will call this method.
	 *
	 * 
	 * @param keyCode - the key code value to indicate a physical key.
	 * @see keyPressed in class Component
	 */
	public void keyPressed(int keyCode)
	{
		if (keyCode == RemoteControlEvent.VK_CONFIRM)
		{
			pressed();
		}
		else
			setHandlesInput(false);
	}

	/**
	 * <B>Description copied from class: <CODE><A HREF="../../../../com/sun/dtv/lwuit/Component.html#keyReleased(int)">Component</A></CODE></B></DD>
	 * If this Component is focused, the key released event
	 * will call this method.
	 *
	 * 
	 * @param keyCode - the key code value to indicate a physical key.
	 * @see keyReleased in class Component
	 */
	public void keyReleased(int keyCode)
	{
		if (keyCode == RemoteControlEvent.VK_CONFIRM)
		{
			released();
		}
	}

	/**
	 * <B>Description copied from class: <CODE><A HREF="../../../../com/sun/dtv/lwuit/Component.html#pointerPressed(int, int)">Component</A></CODE></B></DD>
	 * If this Component is focused, the pointer pressed event
	 * will call this method.
	 *
	 * 
	 * @param x - the pointer x coordinate
	 * @param y - the pointer y coordinate
	 * @see pointerPressed in class Component
	 */
	public void pointerPressed(int x, int y)
	{
		pressed();
	}

	/**
	 * <B>Description copied from class: <CODE><A HREF="../../../../com/sun/dtv/lwuit/Component.html#pointerReleased(int, int)">Component</A></CODE></B></DD>
	 * If this Component is focused, the pointer released event
	 * will call this method.
	 *
	 * 
	 * @param x - the pointer x coordinate
	 * @param y - the pointer y coordinate
	 * @see pointerReleased in class Component
	 */
	public void pointerReleased(int x, int y)
	{
		released();
	}

	/**
	 * <B>Description copied from class: <CODE><A HREF="../../../../com/sun/dtv/lwuit/Component.html#paint(com.sun.dtv.lwuit.Graphics)">Component</A></CODE></B></DD>
	 * This method paints the Component on the screen, it should be overriden
	 * by subclasses to perform custom drawing or invoke the UI API's to let
	 * the PLAF perform the rendering.
	 *
	 * 
	 * @param g - the component graphics
	 * @see paint in interface Animation
	 * @see paint in interface ViewOnlyComponent
	 * @see paint in class Label
	 */
	public void paint(Graphics g)
	{
		UIManager.getInstance().getLookAndFeel().drawButton(g, this);
	}
	
	protected Dimension calcPreferredSize(){
		return UIManager.getInstance().getLookAndFeel().getButtonPreferredSize(this);
	}
    public boolean animate() 
    {
        boolean result = super.animate();
        if(isEnabled())
        {
        	switch(state) 
        	{
        		case STATE_ROLLOVER:
        			result = rolloverIcon != null && rolloverIcon.isAnimation() || result;
        			break;
        		case STATE_PRESSED:
        			result = pressedIcon != null && pressedIcon.isAnimation() || result;
        			break;
        	}
        }
        return result;
    }
    public Image getIconFromState()
	{
		Image icon = null;
		switch (getState()) {
			case Button.STATE_DEFAULT:
				icon = getIcon();
				break;
			case Button.STATE_PRESSED:
				icon = getPressedIcon();
				if (icon == null) {
					icon = getIcon();
				}
				break;
			case Button.STATE_ROLLOVER:
				icon = getRolloverIcon();
				if (icon == null) 
				{
					icon = getIcon();
				}
				break;
		}
		return icon;
	}
    
    Command getCommand(){
    	return this.cmd;
    }
}
