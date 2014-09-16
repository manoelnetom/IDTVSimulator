package com.sun.dtv.lwuit;

import com.sun.dtv.lwuit.animations.Transition;

import com.sun.dtv.lwuit.plaf.Border;
import com.sun.dtv.lwuit.plaf.Style;
import com.sun.dtv.lwuit.plaf.UIManager;

import com.sun.dtv.lwuit.events.ActionEvent;

import com.sun.dtv.lwuit.geom.Dimension;

import com.sun.dtv.lwuit.layouts.BorderLayout;
import com.sun.dtv.lwuit.layouts.FlowLayout;

import com.sun.dtv.ui.event.RemoteControlEvent;

import java.awt.Color;

import java.util.Hashtable;
import java.util.Vector;
import com.sun.dtv.ui.ViewOnlyComponent;

public class Dialog extends Form
{
	/**
	 * Constant indicating the type of alert to indicate the sound to play or
	 * icon if none are explicitly set.
	 *
	 * 
	 */
	public static final int TYPE_ALARM = 1;

	/**
	 * Constant indicating the type of alert to indicate the sound to play or
	 * icon if none are explicitly set.
	 *
	 * 
	 */
	public static final int TYPE_CONFIRMATION = 2;

	/**
	 * Constant indicating the type of alert to indicate the sound to play or
	 * icon if none are explicitly set.
	 *
	 *
	 */
	public static final int TYPE_ERROR = 3;

	/**
	 * Constant indicating the type of alert to indicate the sound to play or
	 * icon if none are explicitly set.
	 *
	 * 
	 */
	public static final int TYPE_INFO = 4;

	/**
	 * Constant indicating the type of alert to indicate the sound to play or
	 * icon if none are explicitly set.
	 *
	 * 
	 */
	public static final int TYPE_WARNING = 5;

	private int dialogType;
	private Command lastCommandPressed;
	private Command defaultCommand;
	private boolean modal = true;
	private Style dialogStyle;
	private long time = 0;
	private long timeout = 0;
	private boolean autoDispose = true;
	private static String defaultDialogPosition;
	private boolean menu = false;
	private String dialogUIID;
	private String dialogTitleUIID;
	private int top = -1;
	private int bottom;
	private int left;
	private int right;
	private boolean includeTitle;

	/**
	 * Indicates whether the dialog has been disposed
	 */
	private boolean disposed;

	/**
	 * Constructs a Dialog with a title.
	 *
	 * 
	 * @param title - the title of the dialog
	 */
	public Dialog(String title)
	{
		 this("Dialog", title);
	}

	/**
	 * Constructs a Dialog with a title.
	 *
	 * 
	 */
	public Dialog()
	{
		 this("Dialog", "DialogTitle");
	}

	Dialog(String dialogUIID, String dialogTitleUIID) {
		super();
		setUIID(dialogUIID);
		this.dialogUIID = dialogUIID;
		this.dialogTitleUIID = dialogTitleUIID;
		
		setDialogStyle(UIManager.getInstance().getComponentStyle(dialogUIID));
		setTitleStyle(UIManager.getInstance().getComponentStyle(dialogTitleUIID));
		
		super.getStyle().setBgTransparency(255);
		super.getStyle().setBgImage(null);
	
		setSmoothScrolling(false);
		deregisterAnimated(this);
		
		setSize(new Dimension(350, 450));
		
		Border border = new Border();
		Border focused = Border.createDoubleBorder(5);
		border.setFocusedInstance(focused);
		border.setPressedInstance(border.createPressedVersion());
		super.getStyle().setBorder(border);
	}

	/**
	 * Simple setter to set the Dialog Style.
	 * There is a significant difference between the purpose of this method and the
	 * setStyle method as inherited from <A HREF="../../../../com/sun/dtv/lwuit/Component.html" title="class in com.sun.dtv.lwuit"><CODE>Component</CODE></A>!
	 * While setStyle influences the style of the whole screen, this method
	 * refers to the style of the content pane, where all the elements of the dialog
	 * are visible.
	 *
	 * 
	 * @param style - the style
	 * @see getDialogStyle(),  Component.setStyle(com.sun.dtv.lwuit.plaf.Style)
	 */
	public void setDialogStyle( Style style)
	{
		this.dialogStyle = style;
	}

	/**
	 * Simple getter to get the Dialog Style.
	 * There is a significant difference between the purpose of this method and the
	 * getStyle method as inherited from <A HREF="../../../../com/sun/dtv/lwuit/Component.html" title="class in com.sun.dtv.lwuit"><CODE>Component</CODE></A>!
	 * While getStyle returns the style of the whole screen, this method
	 * refers to the style of the content pane, where all the elements of the dialog
	 * are visible.
	 *
	 * 
	 * 
	 * @return the style
	 * @see setDialogStyle(com.sun.dtv.lwuit.plaf.Style),  Component.getStyle()
	 */
	public Style getDialogStyle()
	{
		return this.dialogStyle;
	}

	/**
	 * This method shows the form as a modal alert allowing us to produce a behavior
	 * of an alert/dialog box. This method will block the calling thread even if the
	 * calling thread is the EDT. Notice that this method will not release the block
	 * until dispose is called even if show() from another form is called!
	 *Modal dialogs Allow the forms "content" to "hang in mid air" this is especially useful for
	 * dialogs where you would want the underlying form to "peek" from behind the
	 * form.
	 *
	 * 
	 * @param top - space in pixels between the top of the screen and the form
	 * @param bottom - space in pixels between the bottom of the screen and the form
	 * @param left - space in pixels between the left of the screen and the form
	 * @param right - space in pixels between the right of the screen and the form
	 * @param includeTitle - whether the title should hang in the top of the screen or be glued onto the content pane
	 * @return the last command pressed by the user if such a command exists
	 */
	public Command show(int top, int bottom, int left, int right, boolean includeTitle)
	{
		return show(top, bottom, left, right, includeTitle, true);
	}

	/**
	 * This method shows the form as a modal alert allowing us to produce a behavior
	 * of an alert/dialog box. This method will block the calling thread even if the
	 * calling thread is the EDT. Notice that this method will not release the block
	 * until dispose is called even if show() from another form is called!
	 *Modal dialogs Allow the forms "content" to "hang in mid air" this is especially useful for
	 * dialogs where you would want the underlying form to "peek" from behind the
	 * form.
	 *
	 * 
	 * @param top - space in pixels between the top of the screen and the form
	 * @param bottom - space in pixels between the bottom of the screen and the form
	 * @param left - space in pixels between the left of the screen and the form
	 * @param right - space in pixels between the right of the screen and the form
	 * @param includeTitle - whether the title should hang in the top of the screen or be glued onto the content pane
	 * @param modal - indicates the dialog should be modal set to false for modeless dialog which is useful for some use cases
	 * @return the last command pressed by the user if such a command exists
	 */
	public Command show(int top, int bottom, int left, int right, boolean includeTitle, boolean modal)
	{
		this.top = top;
		this.bottom = bottom;
		this.left = left;
		this.right = right;
		this.includeTitle = includeTitle;
		
		setDisposed(false);
		
		this.modal = modal;
		super.showModal(top, bottom, left, right, includeTitle, modal);
		
		return lastCommandPressed;
	}

	/**
	 * Indicates that this is a menu preventing getCurrent() from ever returning this class
	 */
	void setMenu(boolean menu) {
		this.menu = menu;
	}

	/**
	 * Indicates the time (in milliseconds) after which the dialog will be disposed
	 * implicitly.
	 *
	 * 
	 * @param time - a milliseconds time used to dispose the dialog
	 */
	public void setTimeout(long timeout)
	{
		this.time = this.timeout = System.currentTimeMillis() + timeout;
	}
	
	public void setDefaultCommand(Command defaultCommand){
		this.defaultCommand = defaultCommand;
	}
	
	/**
	 * Shows a modal prompt dialog with the given title and text.
	 *
	 * 
	 * @param title - The title for the dialog optionally null;
	 * @param text - the text displayed in the dialog
	 * @param type - the type of the alert one of TYPE_WARNING, TYPE_INFO,  TYPE_ERROR, TYPE_CONFIRMATION or TYPE_ALARM
	 * @param icon - the icon for the dialog, can be null
	 * @param okText - the text to appear in the command dismissing the dialog
	 * @param cancelText - optionally null for a text to appear in the cancel command for canceling the dialog
	 * @return true if the ok command was pressed or if cancelText is null. False otherwise.
	 */
	public static boolean show(String title, String text, int type, Image icon, String okText, String cancelText)
	{
		return show(title, text, type, icon, okText, cancelText, 0);
	}

	/**
	 * Shows a modal prompt dialog with the given title and text.
	 *
	 * 
	 * @param title - The title for the dialog optionally null;
	 * @param text - the text displayed in the dialog
	 * @param type - the type of the alert one of TYPE_WARNING, TYPE_INFO,  TYPE_ERROR, TYPE_CONFIRMATION or TYPE_ALARM
	 * @param icon - the icon for the dialog, can be null
	 * @param okText - the text to appear in the command dismissing the dialog
	 * @param cancelText - optionally null for a text to appear in the cancel command for canceling the dialog
	 * @param timeout - a timeout after which null would be returned if timeout is 0 infinite time is used
	 * @return true if the ok command was pressed or if cancelText is null. False otherwise.
	 */
	public static boolean show(String title, String text, int type, Image icon, String okText, String cancelText, long timeout)
	{
		Command[] cmds;
		Command okCommand = new Command(okText);
		if (cancelText != null) {
			cmds = new Command[]{new Command(cancelText), okCommand};
		} else {
			cmds = new Command[]{okCommand};
		}
		
		return (show(title, text, okCommand, cmds, type, icon, timeout) == okCommand);
	}

	/**
	 * Shows a modal prompt dialog with the given title and text.
	 *
	 * 
	 * @param title - The title for the dialog optionally null;
	 * @param text - the text displayed in the dialog
	 * @param cmds - commands that are added to the form any click on any command will dispose the form
	 * @param type - the type of the alert one of TYPE_WARNING, TYPE_INFO, TYPE_ERROR, TYPE_CONFIRMATION or TYPE_ALARM
	 * @param icon - the icon for the dialog, can be null
	 * @param timeout - a timeout after which null would be returned if timeout is 0 infinite time is used
	 * @return the command pressed by the user
	 */
	public static Command show(String title, String text, Command[] cmds, int type, Image icon, long timeout)
	{
		return show(title, text, null, cmds, type, icon, timeout);
	}

	/**
	 * Shows a modal prompt dialog with the given title and text.
	 *
	 * 
	 * @param title - The title for the dialog optionally null;
	 * @param text - the text displayed in the dialog
	 * @param defaultCommand - command to be assigned as the default command or null
	 * @param cmds - commands that are added to the form any click on any command will dispose the form
	 * @param type - the type of the alert one of TYPE_WARNING, TYPE_INFO, TYPE_ERROR, TYPE_CONFIRMATION or TYPE_ALARM
	 * @param icon - the icon for the dialog, can be null
	 * @param timeout - a timeout after which null would be returned if timeout is 0 infinite time is used
	 * @return the command pressed by the user
	 */
	public static Command show(String title, String text, Command defaultCommand, Command[] cmds, int type, Image icon, long timeout)
	{
		return show(title, text, defaultCommand, cmds, type, icon, timeout, null);
	}

	/**
	 * Shows a modal prompt dialog with the given title and text.
	 *
	 * 
	 * @param title - The title for the dialog optionally null;
	 * @param text - the text displayed in the dialog
	 * @param cmds - commands that are added to the form any click on any command will dispose the form
	 * @param type - the type of the alert one of TYPE_WARNING, TYPE_INFO, TYPE_ERROR, TYPE_CONFIRMATION or TYPE_ALARM
	 * @param icon - the icon for the dialog, can be null
	 * @param timeout - a timeout after which null would be returned if timeout is 0 infinite time is used
	 * @param transition - the transition installed when the dialog enters/leaves
	 * @return the command pressed by the user
	 */
	public static Command show( String title, String text, Command[] cmds, int type, Image icon, long timeout, Transition transition)
	{
		return show(title, text, null, cmds, type, icon, timeout, transition);
	}

	/**
	 * Shows a modal prompt dialog with the given title and text.
	 *
	 * 
	 * @param title - The title for the dialog optionally null;
	 * @param text - the text displayed in the dialog
	 * @param defaultCommand - command to be assigned as the default command or null
	 * @param cmds - commands that are added to the form any click on any command will dispose the form
	 * @param type - the type of the alert one of TYPE_WARNING, TYPE_INFO, TYPE_ERROR, TYPE_CONFIRMATION or TYPE_ALARM
	 * @param icon - the icon for the dialog, can be null
	 * @param timeout - a timeout after which null would be returned if timeout is 0 infinite time is used
	 * @param transition - the transition installed when the dialog enters/leaves
	 * @return the command pressed by the user
	 */
	public static Command show( String title, String text, Command defaultCommand, Command[] cmds, int type, Image icon, long timeout, Transition transition)
	{
		Hashtable h =  UIManager.getInstance().getResourceBundle();
		
		if(h != null && text != null) {
			Object o = h.get(text);
			if(o != null) {
				text = (String)o;
			}
		}
		
		TextArea t = new TextArea(text, 3, 30);
		t.setStyle(UIManager.getInstance().getComponentStyle("DialogBody"));
		t.setEditable(false);
		
		return show(title, t, defaultCommand, cmds, type, icon, timeout, transition);
	}

	/**
	 * Shows a modal prompt dialog with the given title and text.
	 *
	 * 
	 * @param title - The title for the dialog optionally null;
	 * @param text - the text displayed in the dialog
	 * @param okText - the text to appear in the command dismissing the dialog
	 * @param cancelText - optionally null for a text to appear in the cancel command for canceling the dialog
	 * @return true if the ok command was pressed or if cancelText is null. False otherwise.
	 */
	public static boolean show(String title, String text, String okText, String cancelText)
	{
		return show(title, text, TYPE_INFO, null, okText, cancelText);
	}

	/**
	 * Shows a modal dialog with the given component as its "body" placed in the
	 * center.
	 *
	 * 
	 * @param title - title for the dialog
	 * @param body - component placed in the center of the dialog
	 * @param cmds - commands that are added to the form any click on any command will dispose the form
	 * @return the command pressed by the user
	 */
	public static Command show(String title, Component body, Command[] cmds)
	{
		return show(title, body, cmds, TYPE_INFO, null);
	}

	/**
	 * Shows a modal dialog with the given component as its "body" placed in the
	 * center.
	 *
	 * 
	 * @param title - title for the dialog
	 * @param body - component placed in the center of the dialog
	 * @param cmds - commands that are added to the form any click on any command will dispose the form
	 * @param type - the type of the alert one of TYPE_WARNING, TYPE_INFO, TYPE_ERROR, TYPE_CONFIRMATION or TYPE_ALARM
	 * @param icon - the icon for the dialog, can be null
	 * @return the command pressed by the user
	 */
	public static Command show(String title, Component body, Command[] cmds, int type, Image icon)
	{
		return show(title, body, cmds, type, icon, 0);
	}

	/**
	 * Shows a modal dialog with the given component as its "body" placed in the
	 * center.
	 *
	 * 
	 * @param title - title for the dialog
	 * @param body - component placed in the center of the dialog
	 * @param cmds - commands that are added to the form any click on any command will dispose the form
	 * @param type - the type of the alert one of TYPE_WARNING, TYPE_INFO, TYPE_ERROR, TYPE_CONFIRMATION or TYPE_ALARM
	 * @param icon - the icon for the dialog, can be null
	 * @param timeout - a timeout after which null would be returned if timeout is 0 infinite time is used
	 * @return the command pressed by the user
	 */
	public static Command show( String title, Component body, Command[] cmds, int type, Image icon, long timeout)
	{
		return show(title, body, cmds, type, icon, timeout, null);
	}

	/**
	 * Shows a modal dialog with the given component as its "body" placed in the
	 * center.
	 *
	 * 
	 * @param title - title for the dialog
	 * @param body - component placed in the center of the dialog
	 * @param cmds - commands that are added to the form any click on any command will dispose the form
	 * @param type - the type of the alert one of TYPE_WARNING, TYPE_INFO, TYPE_ERROR, TYPE_CONFIRMATION or TYPE_ALARM
	 * @param icon - the icon for the dialog, can be null
	 * @param timeout - a timeout after which null would be returned if timeout is 0 infinite time is used
	 * @param transition - the transition installed when the dialog enters/leaves
	 * @return the command pressed by the user
	 */
	public static Command show( String title, Component body, Command[] cmds, int type, Image icon, long timeout, Transition transition)
	{
		return show(title, body, null, cmds, type, icon, timeout, transition);
	}

	/**
	 * Shows a modal dialog with the given component as its "body" placed in the
	 * center.
	 *
	 * 
	 * @param title - title for the dialog
	 * @param body - component placed in the center of the dialog
	 * @param defaultCommand - command to be assigned as the default command or null
	 * @param cmds - commands that are added to the form any click on any command will dispose the form
	 * @param type - the type of the alert one of TYPE_WARNING, TYPE_INFO, TYPE_ERROR, TYPE_CONFIRMATION or TYPE_ALARM
	 * @param icon - the icon for the dialog, can be null
	 * @param timeout - a timeout after which null would be returned if timeout is 0 infinite time is used
	 * @param transition - the transition installed when the dialog enters/leaves
	 * @return the command pressed by the user
	 */
	public static Command show( String title, Component body, Command defaultCommand, Command[] cmds, int type, Image icon, long timeout, Transition transition)
	{
		Dialog dialog = new Dialog(title);
		dialog.dialogType = type;
		dialog.setTransitionInAnimator(transition);
		dialog.setTransitionOutAnimator(transition);
		
		Container containerCommandButtons = new Container();
		containerCommandButtons.setLayout(new FlowLayout(FlowLayout.CENTER));
		containerCommandButtons.setVisible(true);
		
		body.setFocusable(false);
		
		dialog.lastCommandPressed = null;
		dialog.setDefaultCommand(defaultCommand);
		if(cmds != null) {
			for(int iter = 0 ; iter < cmds.length ; iter++) {
				dialog.addCommand(cmds[iter]);
				Button button = new Button(cmds[iter]);
				button.addActionListener(cmds[iter]);
				containerCommandButtons.addComponent(button);
			}
			if(cmds.length == 1) {
				dialog.setBackCommand(cmds[0]);
			} else {
				if(cmds.length == 2 && defaultCommand == null) {
					defaultCommand = cmds[0];
					dialog.setBackCommand(cmds[1]);
				}
			}
		}
		
		dialog.setLayout(new BorderLayout());
		dialog.addComponent(BorderLayout.CENTER, body);
		dialog.addComponent(BorderLayout.SOUTH, containerCommandButtons);
		
		if (icon != null) {
			Label i = new Label(icon);
			i.setVerticalAlignment(ViewOnlyComponent.VERTICAL_ALIGN_CENTER);
			i.getStyle().setBgColor(Color.BLACK);
			dialog.addComponent(BorderLayout.WEST, i);
		}
		
		if(body.isScrollable()){
			dialog.setScrollable(false);
		}
		
		if (timeout != 0) {
			dialog.setTimeout(timeout);
		}
		
		dialog.show();
		
		return dialog.lastCommandPressed;
	}

	/**
	 * The default version of show modal shows the dialog occupying the center portion
	 * of the screen.
	 *
	 * 
	 * @see show in class Form
	 */
	public void show()
	{
		setDisposed(false);
		
		if (timeout != 0 && autoDispose) {
			AutoDisposeDialog autoDisposeDialog = new AutoDisposeDialog(this);
			Thread t = new Thread(autoDisposeDialog);
			t.start();
		}
		
		if(top > -1) {
			show(top, bottom, left, right, includeTitle, modal);
		} else {
			if(modal) {
				if(defaultDialogPosition == null) {
					super.showModal();
				} else {
					showPacked(defaultDialogPosition, true);
				}
			} else {
				showModeless();
			}
		}
	}

	/**
	 * Shows a modeless dialog which is useful for some simpler use cases such as
	 * progress indication etc...
	 *
	 * 
	 */
	public void showModeless()
	{
		modal = false;
		setDisposed(false);
		if(top > -1) {
			show(top, bottom, left, right, includeTitle, false);
		} else {
			if(defaultDialogPosition == null) {
				showDialog(false);
			} else {
				showPacked(defaultDialogPosition, false);
			}
		}
	}

	/**
	 * Convenience method to show a dialog sized to match its content.
	 *
	 * 
	 * @param position - one of the values from the BorderLayout class e.g. BorderLayout.CENTER, BorderLayout.NORTH etc.
	 * @param modal - whether the dialog should be modal
	 */
	public Command showPacked(String position, boolean modal)
	{
		int height = Display.getInstance().getDisplayHeight();
		int width = Display.getInstance().getDisplayWidth();
		Component contentPane = getContentPane();
		Component title = getTitleComponent();
		Style contentPaneStyle = contentPane.getStyle();
		Style titleStyle = title.getStyle();
		Component menuBar = getSoftButton(0).getParent();
		int menuHeight = 0;
		if(menuBar != null) {
			Style menuStyle = menuBar.getStyle();
			menuHeight = menuBar.getPreferredSize().getHeight() + menuStyle.getMargin(TOP) + menuStyle.getMargin(BOTTOM);
		}
		int prefHeight = contentPane.getPreferredSize().getHeight() + contentPaneStyle.getMargin(TOP) + contentPaneStyle.getMargin(BOTTOM) +
			title.getPreferredSize().getHeight() + titleStyle.getMargin(TOP) + titleStyle.getMargin(BOTTOM) +
			menuHeight;
		int prefWidth = contentPane.getPreferredSize().getWidth() + contentPaneStyle.getMargin(LEFT) + contentPaneStyle.getMargin(RIGHT);
		prefWidth = Math.min(prefWidth, width);
		prefHeight = Math.min(prefHeight, height - (title.getPreferredSize().getHeight() + titleStyle.getMargin(TOP) + titleStyle.getMargin(BOTTOM) +
					menuHeight));
		int topBottom = (height - prefHeight) / 2;
		int leftRight = (width - prefWidth) / 2;
		if(position.equals(BorderLayout.CENTER)) {
			show(topBottom, topBottom, leftRight, leftRight, true, modal);
			return lastCommandPressed;
		}
		if(position.equals(BorderLayout.EAST)) {
			show(topBottom, topBottom, width - prefWidth, 0, true, modal);
			return lastCommandPressed;
		}
		if(position.equals(BorderLayout.WEST)) {
			show(topBottom, topBottom, 0, width - prefWidth, true, modal);
			return lastCommandPressed;
		}
		if(position.equals(BorderLayout.NORTH)) {
			show(0, height - prefHeight, leftRight, leftRight, true, modal);
			return lastCommandPressed;
		}
		if(position.equals(BorderLayout.SOUTH)) {
			show(height - prefHeight, 0, leftRight, leftRight, true, modal);
			return lastCommandPressed;
		}
		throw new IllegalArgumentException("Unknown position: " + position);

	}

	/**
	 * Closes the current form and returns to the previous form, releasing the
	 * EDT in the process.
	 *
	 * 
	 */
	public void dispose()
	{
		setDisposed(true);
		
		if(!menu) {
			super.dispose();
		}
	}

	/**
	 * <B>Description copied from class: <CODE><A HREF="../../../../com/sun/dtv/lwuit/Component.html#refreshTheme()">Component</A></CODE></B></DD>
	 * Makes sure the component is up to date with the current style object.
	 *
	 * 
	 * @see refreshTheme in class Form
	 */
	public void refreshTheme()
	{
		Container content = getContentPane();
		content.refreshTheme(dialogUIID);
		Style titleStyle = getTitleStyle();

		if (titleStyle.isModified()) {
			titleStyle.merge(UIManager.getInstance().getComponentStyle(dialogTitleUIID));
		} else {
			setTitleStyle(UIManager.getInstance().getComponentStyle(dialogTitleUIID));
		}

		int size = content.getComponentCount();
		for (int i = 0; i < size; i++) {
			Component cmp = content.getComponentAt(i);
			cmp.refreshTheme();
		}
	}

	/**
	 * Shows a modal dialog and returns the command pressed within the modal dialog.
	 *
	 * 
	 * 
	 * @return last command pressed in the modal dialog
	 */
	public Command showDialog()
	{
		lastCommandPressed = null;
		
		show();
		
		return lastCommandPressed;
	}

	/**
	 * Indicates that this is a menu preventing getCurrent() from ever returning this class
	 */
	boolean isMenu() {
		return menu;
	}
	
	/**
	 * <B>Description copied from interface: <CODE><A HREF="../../../../com/sun/dtv/lwuit/animations/Animation.html#animate()">Animation</A></CODE></B></DD>
	 * Allows the animation to reduce "repaint" calls when it returns false. It is
	 * called once for every frame.
	 *
	 * 
	 * @return true if a repaint is desired or false if no repaint is necessary
	 * @see animate in interface Animation
	 * @see animate in class Component
	 */
	public boolean animate()
	{
		if (this.time != 0 && System.currentTimeMillis() >= this.time) {
			this.time = 0;
			dispose();
		}

		return false;
	}

	/**
	 * Determines whether the execution of a command on this dialog implicitly
	 * disposes the dialog. This defaults to true which is a sensible default for
	 * simple dialogs.
	 *
	 * 
	 * 
	 * @return whether the execution of a command on this dialog implicitly  disposes the dialog
	 */
	public boolean isAutoDispose()
	{
		return autoDispose;
	}

	/**
	 * Determines whether the execution of a command on this dialog implicitly
	 * disposes the dialog. This defaults to true which is a sensible default
	 * for simple dialogs.
	 *
	 * 
	 * @param autoDispose - auto disposed state
	 */
	public void setAutoDispose(boolean autoDispose)
	{
		this.autoDispose = autoDispose;
	}

	/**
	 * Default screen orientation position for the upcoming dialog. By default
	 * the dialog will be shown at hardcoded coordinates, this method allows us
	 * to pack the dialog appropriately in one of the border layout based locations
	 * see BorderLayout for further details.
	 *
	 * 
	 * @param p - default dialog position
	 * @see getDefaultDialogPosition()
	 */
	public static void setDefaultDialogPosition( String p)
	{
		defaultDialogPosition = p;
	}

	/**
	 * Default screen orientation position for the upcoming dialog. By default
	 * the dialog will be shown at hardcoded coordinates, this method allows us
	 * to pack the dialog appropriately in one of the border layout based locations
	 * see BorderLayout for further details.
	 *
	 * 
	 * 
	 * @return default dialog position
	 * @see setDefaultDialogPosition(java.lang.String)
	 */
	public static String getDefaultDialogPosition()
	{
		return defaultDialogPosition;
	}

	/**
	 * Allows us to indicate disposed state for dialogs
	 *
	 */
	boolean isDisposed() {
		return disposed;
	}

	/**
	 * Allows us to indicate disposed state for dialogs
	 *
	 */
	void setDisposed(boolean disposed) {
		this.disposed = disposed;
	}
	
	public void keyPressed(int keyCode) {
		if (focused != null) {
			if(focused.isEnabled())
			{
				focused.keyPressed(keyCode);
			}
			if(focused.handlesInput() && focused instanceof Button)
			{
				Vector commands = getCommandList();

				/* Update lastCommandPressed */
				for(int i = 0; i < commands.size(); i++){
					Command tmp = ((Button)focused).getCommand();
					if(tmp == ((Command)commands.get(i)) ){
						lastCommandPressed = tmp;
						break;
					}
				}
				
				dispose();
				return;
			} else {
				if (keyCode == RemoteControlEvent.VK_LEFT
					||
					keyCode == RemoteControlEvent.VK_RIGHT
					||
					keyCode == RemoteControlEvent.VK_UP
					||
					keyCode == RemoteControlEvent.VK_DOWN) {
					this.updateFocus(keyCode);
				}
			}
		}
	}
	
	class AutoDisposeDialog implements Runnable{
		private Dialog d;
		public AutoDisposeDialog(Dialog d){
			this.d = d;
		}
		public void run() {
			while(System.currentTimeMillis() < d.timeout && !d.isDisposed()){
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			if(!d.isDisposed()){
				if(d.defaultCommand != null){
					d.defaultCommand.actionPerformed(new ActionEvent(this));
					d.lastCommandPressed = d.defaultCommand;
				}
				d.dispose();
			}
		}
	}
	
}
