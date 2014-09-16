package com.sun.dtv.lwuit;

import com.sun.dtv.lwuit.events.ActionEvent;
import com.sun.dtv.lwuit.events.ActionListener;
import com.sun.dtv.lwuit.events.DataChangedListener;
import com.sun.dtv.lwuit.plaf.UIManager;
import com.sun.dtv.lwuit.geom.Dimension;
import com.sun.dtv.lwuit.layouts.GridLayout;

import java.awt.Color;
import java.util.Iterator;
import java.util.Vector;
import java.util.Hashtable;
import java.util.ArrayList;

public class TextField extends TextArea
{
	private long releaseTime;
	private boolean longClick;
	private boolean drawCursor = true;
	private boolean pressedAndNotReleased;
	private long cursorBlinkTime = System.currentTimeMillis();
	private boolean overwriteMode;
	private boolean pendingCommit;
	private static String clearText;
	private static String t9Text;
	private int commitTimeout;
	private int pressedKeyCode;
	private int lastKeyCode;
	private int pressCount = 0;
	private long pressTime;
	private static Vector firstUppercaseInputMode = new Vector();
	private static Hashtable inputModes;    
	private String inputMode;
	private String previousText;
	private String[] inputModeOrder;
	private static String[] defaultInputModeOrder;
	private boolean editable;
	private boolean useSoftkeys;
	private boolean replaceMenu;
	private static boolean replaceMenuDefault;
	private static boolean qwertyAutoDetect = false;
	private static boolean qwertyDevice;
	private boolean qwerty = qwertyDevice;
	private int blinkOnTime = 800;
	private int blinkOffTime = 200;
	private java.util.List dataChangeListeners;

	/**
	 * Key to change the input mode on the device
	 */
	private static int defaultChangeInputModeKey = '#';

	private Command selectCommand;

	/**
	 * Set the text that should appear on the clear softkey
	 * 
	 * @param text localized text for the clear softbutton
	 */
	public static void setClearText(String text) {
		clearText = text;
	}

	/**
	 * Set the text that should appear on the T9 softkey
	 * 
	 * @param text text for the T9 softbutton
	 */
	public static void setT9Text(String text) {
		t9Text = text;
	}

	private Command DELETE_COMMAND = new Command(clearText) {
		public void actionPerformed(ActionEvent ev) {
			deleteChar();
		}
	};
	private Command T9_COMMAND = new Command(t9Text) {
		public void actionPerformed(ActionEvent ev) {
			ev.consume();
			editString();
		}
	};

	private static final char[] DEFAULT_SYMBOL_TABLE = new char[] {
		'.', ',', '?', '!', '$', '@', '\'', '-',
			'_', ')', '(', ':', ';', '&', '/', '~',
			'\\', '%', '*', '#', '+', '>', '=', '<',
			'"'
	};

	private static char[] symbolTable = DEFAULT_SYMBOL_TABLE;

	private static final String[] DEFAULT_KEY_CODES = {
		// 0
		" 0",
		// 1
		".,?!'\"1-()@/:_",
		// 2
		"ABC2",
		// 3
		"DEF3",
		// 4
		"GHI4",
		// 5
		"JKL5",
		// 6
		"MNO6",
		// 7
		"PQRS7",
		// 8
		"TUV8",
		// 9
		"WXYZ9",
	};

	/**
	 * Default constructor.
	 *
	 * 
	 */
	public TextField()
	{
		super(1, 20);
		setUIID("TextField");
		getStyle().setBgColor(Color.WHITE);
		getStyle().setBgSelectionColor(Color.WHITE);
		getStyle().setFgColor(Color.BLACK);
		getStyle().setFgSelectionColor(Color.BLACK);
	}

	/**
	 * Construct a text field with space reserved for columns.
	 *
	 * 
	 * @param columns - number of columns
	 */
	public TextField(int columns)
	{
		super(1, columns);
		setUIID("TextField");
		this.setCursorPosition(0);
		getStyle().setBgColor(Color.WHITE);
		getStyle().setBgSelectionColor(Color.WHITE);
		getStyle().setFgColor(Color.BLACK);
		getStyle().setFgSelectionColor(Color.BLACK);
	}

	/**
	 * Construct text field.
	 *
	 * 
	 * @param text - content of textfield
	 */
	public TextField(String text)
	{
		super(text, 1, 20);
		setUIID("TextField");
		this.setCursorPosition(0);
		getStyle().setBgColor(Color.WHITE);
		getStyle().setBgSelectionColor(Color.WHITE);
		getStyle().setFgColor(Color.BLACK);
		getStyle().setFgSelectionColor(Color.BLACK);;
	}

	/**
	 * Construct text field.
	 *
	 * 
	 * @param text - content of text field
	 * @param columns - number of columns
	 */
	public TextField(String text, int columns)
	{
		super(text, 1, columns);
		setUIID("TextField");
		this.setCursorPosition(0);
		getStyle().setBgColor(Color.WHITE);
		getStyle().setBgSelectionColor(Color.WHITE);
		getStyle().setFgColor(Color.BLACK);
		getStyle().setFgSelectionColor(Color.BLACK);
	}

	/**
	 * Returns true if the text field is waiting for a commit on editing.
	 *
	 * 
	 * 
	 * @return true if there is a pending commit, false otherwise
	 */
	public boolean isPendingCommit()
	{
		return pendingCommit;
	}

	/**
	 * The amount of time in milliseconds it will take for a change to get committed into
	 * the field.
	 *
	 * 
	 * @param commitTimeout - indicates the amount of time that should elapse for a commit to automatically occur
	 * @see getCommitTimeout()
	 */
	public void setCommitTimeout(int commitTimeout)
	{
		this.commitTimeout = commitTimeout;
	}

	/**
	 * Indicates whether the key changes the current input mode
	 * 
	 * @param keyCode the code
	 * @return true for the hash (#) key code
	 */
	protected boolean isChangeInputMode(int keyCode) {
		return keyCode == defaultChangeInputModeKey;
	}

	private static void initInputModes() {
		if(inputModes == null) {
			firstUppercaseInputMode.addElement("Abc");
			inputModes = new Hashtable();
			Hashtable upcase = new Hashtable();
			for(int iter = 0 ; iter < DEFAULT_KEY_CODES.length ; iter++) {
				upcase.put(new Integer('0' + iter), DEFAULT_KEY_CODES[iter]);
			}

			inputModes.put("ABC", upcase);

			Hashtable lowcase = new Hashtable();
			for(int iter = 0 ; iter < DEFAULT_KEY_CODES.length ; iter++) {
				lowcase.put(new Integer('0' + iter), DEFAULT_KEY_CODES[iter].toLowerCase());
			}
			inputModes.put("abc", lowcase);

			Hashtable numbers = new Hashtable();
			for(int iter = 0 ; iter < 10 ; iter++) {
				numbers.put(new Integer('0' + iter), "" + iter);
			}
			inputModes.put("123", numbers);
		}
	}
	/**
	 * The amount of time in milliseconds it will take for a change to get committed into
	 * the field.
	 *
	 * 
	 * 
	 * @return the amount of time that should elapse for a commit to automatically occur
	 * @see setCommitTimeout(int)
	 */
	public int getCommitTimeout()
	{
		return this.commitTimeout;
	}

	/**
	 * Sets the current selected input mode matching one of the existing input
	 * modes.
	 *
	 * 
	 * @param mode - the display name of the input mode by default the following modes are supported: Abc, ABC, abc, 123
	 * @see getInputMode()
	 */
	public void setInputMode( String mode)
	{
		this.inputMode = mode;

		repaint();
	}

	/**
	 * Returns the currently selected input mode.
	 *
	 * 
	 * 
	 * @return the display name of the input mode by default the following modes are supported: Abc, ABC, abc, 123
	 * @see setInputMode(java.lang.String)
	 */
	public String getInputMode()
	{
		return this.inputMode;
	}

	/**
	 * Adds a new input mode hashtable with the given name and set of values.
	 *
	 * 
	 * @param name - a unique display name for the input mode e.g. ABC, 123 etc...
	 * @param values - The key for the hashtable is an Integer keyCode and the value is a String containing the characters to toggle between for the given keycode
	 * @param firstUpcase - indicates if this input mode in an input mode used for the special case where the first letter is an upper case letter
	 */
	public static void addInputMode( String name, Hashtable values, boolean firstUpcase)
	{
		initInputModes();
		inputModes.put(name, values);
		if(firstUpcase) {
			firstUppercaseInputMode.addElement(name);
		}
	}

	/**
	 * Returns the order in which input modes are toggled.
	 *
	 * 
	 * 
	 * @return the order in which input modes are toggled
	 * @see setInputModeOrder(java.lang.String[])
	 */
	public String[] getInputModeOrder()
	{
		return this.inputModeOrder;
	}

	/**
	 * Sets the order in which input modes are toggled and allows disabling/hiding
	 * an input mode.
	 *
	 * 
	 * @param order - the order for the input modes in this field
	 * @see getInputModeOrder()
	 */
	public void setInputModeOrder( String[] order)
	{
		inputModeOrder = order;
		inputMode = order[0];
	}

	/**
	 * Returns the order in which input modes are toggled by default.
	 *
	 * 
	 * 
	 * @return the default order for the input modes in this field
	 * @see setDefaultInputModeOrder(java.lang.String[])
	 */
	public static String[] getDefaultInputModeOrder()
	{
		return defaultInputModeOrder;
	}

	/**
	 * Sets the order in which input modes are toggled by default and allows
	 * disabling/hiding an input mode.
	 *
	 * 
	 * @param order - the order for the input modes in all future created fields
	 * @see getDefaultInputModeOrder()
	 */
	public static void setDefaultInputModeOrder( String[] order)
	{
		defaultInputModeOrder = order;
	}

	/**
	 * Set the position of the cursor.
	 *
	 * 
	 * @param pos - the position of the cursor
	 * @see getCursorPosition()
	 */
	public void setCursorPosition(int pos)
	{
		setInternalCursorPosition(pos);
	}

	/**
	 * Returns the position of the cursor.
	 *
	 * 
	 * 
	 * @return the position of the cursor
	 * @see setCursorPosition(int)
	 */
	public int getCursorPosition()
	{
		return getInternalCursorPosition();
	}

	/**
	 * <B>Description copied from class: <CODE><A HREF="../../../../com/sun/dtv/lwuit/TextArea.html#setText(java.lang.String)">TextArea</A></CODE></B></DD>
	 * Sets the text within this text area.
	 *
	 * 
	 * @param text - new value for the text area
	 * @see setText in class TextArea
	 * @see TextArea.getText()
	 */
	public void setText(String text)
	{
		super.setText(removeChar(text, '\n'));

		fireDataChanged(DataChangedListener.CHANGED, -1);
	}

	/**
	 * True is this is a qwerty device or a device that is currently in
	 * qwerty mode.
	 *
	 * 
	 * 
	 * @return currently defaults to false
	 */
	public boolean isQwertyInput()
	{
		return qwerty;
	}

	/**
	 * Set this device to operate in or no longer operate in qwerty mode.
	 *
	 * 
	 * @param qwerty - indicate whether device should be set to qwerty mode or not
	 */
	public void setQwertyInput(boolean qwerty)
	{
		this.qwerty = qwerty;
	}

	/**
	 * <B>Description copied from class: <CODE><A HREF="../../../../com/sun/dtv/lwuit/Component.html#keyReleased(int)">Component</A></CODE></B></DD>
	 * If this Component is focused, the key released event
	 * will call this method.
	 *
	 * 
	 * @param keyCode - the key code value to indicate a physical key.
	 * @see keyReleased in class TextArea
	 */
	public void keyReleased(int keyCode)
	{
		if(!isEditable()) {
			return;
		}
		
		pressedAndNotReleased = false;
		releaseTime = System.currentTimeMillis();
		if(!longClick) {
			if(keyReleaseOrLongClick(keyCode, false)) {
				return;
			}
		}
		longClick = false;
		
		super.keyReleased(keyCode);
	}

	/**
	 * Returns the symbol table for the device.
	 *
	 * 
	 * 
	 * @return symbol table of the device
	 * @see setSymbolTable(char[])
	 */
	public static char[] getSymbolTable()
	{
		return symbolTable;
	}

	/**
	 * Sets the symbol table to show when the user clicks the symbol table key.
	 *
	 * 
	 * @param table - the symbol table
	 * @see getSymbolTable()
	 */
	public static void setSymbolTable(char[] table)
	{
		symbolTable = table;
	}

	/**
	 * <B>Description copied from class: <CODE><A HREF="../../../../com/sun/dtv/lwuit/TextArea.html#setEditable(boolean)">TextArea</A></CODE></B></DD>
	 * Sets this text area to be editable or readonly.
	 *
	 * 
	 * @param b - true is text are is editable; otherwise false
	 * @see setEditable in class TextArea
	 */
	public void setEditable(boolean b)
	{
		this.editable = b;
	}

	void appendText(String append) {
		append = removeChar(append, '\n');

		fireDataChanged(DataChangedListener.ADDED, (getCursorPosition()<0)?0:getCursorPosition());

		super.appendText(append);
	}

	/**
	 * <B>Description copied from class: <CODE><A HREF="../../../../com/sun/dtv/lwuit/Component.html#keyPressed(int)">Component</A></CODE></B></DD>
	 * If this Component is focused, the key pressed event
	 * will call this method.
	 *
	 * 
	 * @param keyCode - the key code value to indicate a physical key.
	 * @see keyPressed in class TextArea
	 */
	public void keyPressed(int keyCode) {
		super.keyPressed(keyCode);
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
	 * @see paint in class TextArea
	 */
	public void paint(Graphics g)
	{
		UIManager.getInstance().getLookAndFeel().drawTextField(g, this);
		
		if (drawCursor && hasFocus() && isEditable()) {
			UIManager.getInstance().getLookAndFeel().drawTextFieldCursor(g, this);
		}
	}

	/**
	 * The amount of time in milliseconds in which the cursor is visible.
	 *
	 * 
	 * @param time - time in milliseconds in which the cursor is visible
	 * @see getCursorBlinkTimeOn()
	 */
	public void setCursorBlinkTimeOn(int time)
	{
		blinkOnTime = time;
	}

	/**
	 * The amount of time in milliseconds in which the cursor is invisible.
	 *
	 * 
	 * @param time - time in milliseconds in which the cursor is invisible
	 * @see getCursorBlinkTimeOff()
	 */
	public void setCursorBlinkTimeOff(int time)
	{
		blinkOffTime = time;
	}

	/**
	 * The amount of time in milliseconds in which the cursor is visible.
	 *
	 * 
	 * 
	 * @return time in milliseconds in which the cursor is visible
	 * @see setCursorBlinkTimeOn(int)
	 */
	public int getCursorBlinkTimeOn()
	{
		return blinkOnTime;
	}

	/**
	 * The amount of time in milliseconds in which the cursor is invisible.
	 *
	 * 
	 * 
	 * @return time in milliseconds in which the cursor is invisible
	 * @see setCursorBlinkTimeOff(int)
	 */
	public int getCursorBlinkTimeOff()
	{
		return blinkOffTime;
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
	public boolean animate() {
		boolean ani = super.animate();
		if(hasFocus()) {
			long currentTime = System.currentTimeMillis();
			if (drawCursor) {
				if ((currentTime - cursorBlinkTime) > blinkOnTime) {
					cursorBlinkTime = currentTime;
					drawCursor = false;
					return true;
				}
			} else {
				if ((currentTime - cursorBlinkTime) > blinkOffTime) {
					cursorBlinkTime = currentTime;
					drawCursor = true;
					return true;
				}
			}
			if(pressedAndNotReleased) { 
				if(currentTime - pressTime >= getLongClickDuration()) {
					longClick(pressedKeyCode);
				}
			} else {
				if(pendingCommit && currentTime - releaseTime > commitTimeout) {
					commitChange();
				}
			}
		} else {
			drawCursor = false;
		}
		return ani;
	}

	/**
	 * <B>Description copied from class: <CODE><A HREF="../../../../com/sun/dtv/lwuit/Component.html#pointerReleased(int, int)">Component</A></CODE></B></DD>
	 * If this Component is focused, the pointer released event
	 * will call this method.
	 *
	 * 
	 * @param x - the pointer x coordinate
	 * @param y - the pointer y coordinate
	 * @see pointerReleased in class TextArea
	 */
	public void pointerReleased(int x, int y)
	{
		// unlike text area the text field supports shifting the cursor with the touch screen
		String text = getText();
		int textLength = text.length();
		int position = 0;
		Font f = getStyle().getFont();
		x -= getAbsoluteX();
		for(int iter = 0 ; iter < textLength ; iter++) {
			int width = f.substringWidth(text, 0, iter);
			if(x > width) {
				position = iter;
			} else {
				break;
			}
		}
		if(position == textLength - 1) {
			if(f.stringWidth(text) < x) {
				position = textLength;
			}
		}
		setCursorPosition(position);
		repaint();
	}

	/**
	 * When set to true softkeys are used to enable delete functionality.
	 *
	 * 
	 * 
	 * @return true if softkeys are used to enable delete functionality
	 */
	public boolean isUseSoftkeys()
	{
		return useSoftkeys;
	}

	/**
	 * When set to true softkeys are used to enable delete functionality.
	 *
	 * 
	 * @param useSoftkeys - value indicating whether softkeys are used to enable  delete functionality
	 */
	public void setUseSoftkeys(boolean useSoftkeys)
	{
		this.useSoftkeys = useSoftkeys;
	}

	private void fireDataChanged(int type, int index) {
		if(dataChangeListeners == null){
			dataChangeListeners = new ArrayList();
		}
		
		for (Iterator i=dataChangeListeners.iterator(); i.hasNext(); ) {
			DataChangedListener listener = (DataChangedListener)i.next();

			listener.dataChanged(type, index);
		}
	}
    
	/**
	 * Adds a listener for data change events it will be invoked for every change
	 * made to the text field.
	 *
	 * 
	 * @param d - the DataChangedListener
	 * @see removeDataChangeListener(com.sun.dtv.lwuit.events.DataChangedListener)
	 */
	public void addDataChangeListener(DataChangedListener listener)
	{
		if (dataChangeListeners.contains((Object)listener) == false) {
			dataChangeListeners.add((Object)listener);
		}
	}

	/**
	 * Removes the listener for data change events.
	 *
	 * 
	 * @param d - the DataChangedListener
	 * @see addDataChangeListener(com.sun.dtv.lwuit.events.DataChangedListener)
	 */
	public void removeDataChangeListener( DataChangedListener listener)
	{
		if (dataChangeListeners.contains((Object)listener) == true) {
			dataChangeListeners.remove((Object)listener);
		}
	}

	/**
	 * Indicates whether the menu of the form should be replaced with the T9/Clear
	 * commands for the duration of interactivity with the text field.
	 *
	 * 
	 * 
	 * @return true if the menu of the form should be replaced with the T9/Clear commands for the duration of interactivity with the text field
	 */
	public boolean isReplaceMenu()
	{
		return replaceMenu;
	}

	/**
	 * Indicates whether the menu of the form should be replaced with the T9/Clear
	 * commands for the duration of interactivity with the text field.
	 *
	 * 
	 * @param replaceMenu - true if the menu of the form should be replaced with  the T9/Clear commands for the duration of interactivity with the text field
	 */
	public void setReplaceMenu(boolean replaceMenu)
	{
		this.replaceMenu = replaceMenu;
	}

	/**
	 * Indicates whether the menu of the form should be replaced with the T9/Clear
	 * commands for the duration of interactivity with the text field.
	 *
	 * 
	 * 
	 * @return default value for isReplaceMenu() return value
	 * @see isReplaceMenu(),  setReplaceMenuDefault(boolean)
	 */
	public static boolean isReplaceMenuDefault()
	{
		return replaceMenuDefault;
	}

	/**
	 * Indicates whether the menu of the form should be replaced with the T9/Clear
	 * commands for the duration of interactivity with the text field.
	 *
	 * 
	 * @param replaceMenu - default value for isReplaceMenu() return value
	 * @see isReplaceMenu(),  isReplaceMenuDefault()
	 */
	public static void setReplaceMenuDefault(boolean replaceMenu)
	{
		replaceMenuDefault = replaceMenu;
	}

	/**
	 * Indicates whether the text field should try to auto detect qwerty and
	 * switch the qwerty device flag implicitly.
	 *
	 * 
	 * @param v - value indicating whether the text field should try to auto  detect qwerty and switch the qwerty device flag implicitly
	 */
	public static void setQwertyAutoDetect(boolean v)
	{
		qwertyAutoDetect = v;
	}

	/**
	 * The default value for the qwerty flag so it doesn't need setting for every
	 * text field individually.
	 *
	 * 
	 * @param v - default value for the qwerty flag so it doesn't need setting for every text field individually
	 */
	public static void setQwertyDevice(boolean v)
	{
		qwertyDevice = v;
	}

	/**
	 * Indicates whether the text field should try to auto detect qwerty and
	 * switch the qwerty device flag implicitly.
	 *
	 * 
	 * 
	 * @return value indicating whether the text field should try to auto  detect qwerty and switch the qwerty device flag implicitly
	 */
	public static boolean isQwertyAutoDetect()
	{
		return qwertyAutoDetect;
	}

	/**
	 * The default value for the qwerty flag so it doesn't need setting for every
	 * text field individually.
	 *
	 * 
	 * 
	 * @return default value for the qwerty flag so it doesn't need setting for every text field individually.
	 */
	public static boolean isQwertyDevice()
	{
		return qwertyDevice;
	}

	/**
	 * Performs a backspace operation
	 *
	 */
	protected void deleteChar() {
		fireDataChanged(DataChangedListener.REMOVED, (getCursorPosition()<0)?0:getCursorPosition());

		super.deleteChar();
	}

	private boolean keyReleaseOrLongClick(int keyCode, boolean longClick) {
		// user pressed a different key, autocommit everything
		if(lastKeyCode != keyCode && pendingCommit) {
			commitChange();
		}
		lastKeyCode = keyCode;

		if(isQwertyInput()) {
			if(keyCode > 0) {
				String text;
				if(previousText == null) {
					previousText = getText();
				}
				if(cursorCharPosition < 0) {
					cursorCharPosition = 0;
				}

				insertChars("" + (char)keyCode);
				commitChange();
				fireDataChanged(DataChangedListener.ADDED, cursorCharPosition);
				return true;
			}
		} else {
			char c = getCharPerKeyCode(pressCount, keyCode, longClick);
			cursorCharPosition = Math.max(cursorCharPosition, 0);
			if (c != 0) {
				String text;
				if(previousText == null) {
					previousText = getText();
				}
				if(!pendingCommit) {
					insertChars("" + c);
					pendingCommit = true;
					pressCount++;
				} else {
					text = previousText.substring(0, cursorCharPosition - 1) + c + 
						previousText.substring(cursorCharPosition - 1, previousText.length());
					pendingCommit = true;
					pressCount++;
					super.setText(text);
				}

				if(inputMode.equals("123")) {
					commitChange();
					fireDataChanged(DataChangedListener.ADDED, cursorCharPosition);
				} else {
					if(pressCount == 1) {
						fireDataChanged(DataChangedListener.ADDED, cursorCharPosition);
					} else {
						fireDataChanged(DataChangedListener.CHANGED, cursorCharPosition);
					}
				}
				return true;
			}
		}
		int game = Display.getInstance().getGameAction(keyCode);
		if(game == Display.GAME_RIGHT) {
			cursorCharPosition++;
			if(cursorCharPosition > getText().length()) {
				if(isCursorPositionCycle()) {
					cursorCharPosition = 0;
				} else {
					cursorCharPosition = getText().length();
				}
			}
			repaint();
			return true;
		} else {
			if(game == Display.GAME_LEFT) {
				cursorCharPosition--;
				if(cursorCharPosition < 0) {
					if(isCursorPositionCycle()) {
						cursorCharPosition = getText().length();
					} else {
						cursorCharPosition = 0;
					}
				}
				repaint();
				return true;
			}
		}
		if(isChangeInputMode(keyCode)) {
			for(int iter = 0 ; iter < inputModeOrder.length ; iter++) {
				if(inputModeOrder[iter].equals(inputMode)) {
					iter++;
					if(iter < inputModeOrder.length) {
						setInputMode(inputModeOrder[iter]);
					} else {
						setInputMode(inputModeOrder[0]);
					}
					return true;
				}
			}
			return true;
		}
		if(isClearKey(keyCode)) {
			deleteChar();
			return true;
		}
		if(isSymbolDialogKey(keyCode)) {
			showSymbolDialog();
			return true;
		}
		return false;
	}

	/**
	 * Indicates whether the given key code should be ignored or should trigger
	 * editing, by default fire or any numeric key should trigger editing implicitly.
	 * This method is only called when handles input is false.
	 * 
	 * @param keyCode the keycode passed to the keyPressed method
	 * @return true if this key code should cause a switch to editing mode.
	 */
	protected boolean isEditingTrigger(int keyCode) {
		if(!isEditable()) {
			return false;
		}
		if(isQwertyInput()) {
			return keyCode > 0 || (Display.getInstance().getGameAction(keyCode) == Display.GAME_FIRE);
		}
		
		int gk = Display.getInstance().getGameAction(keyCode);
		
		return (keyCode >= '0' && keyCode <= '9') || isClearKey(keyCode) || (gk == Display.GAME_FIRE) || (gk == Display.GAME_LEFT) || (gk == Display.GAME_RIGHT);
	}

	/**
	 * Indicates whether the given key code should be ignored or should trigger
	 * cause editing to end. By default the fire key, up or down will trigger
	 * the end of editing.
	 * 
	 * @param keyCode the keycode passed to the keyPressed method
	 * @return true if this key code should cause a switch to editing mode.
	 */
	protected boolean isEditingEndTrigger(int keyCode) {
		int k =Display.getInstance().getGameAction(keyCode);
		if(isQwertyInput()) {
			return keyCode < 0 && (k == Display.GAME_FIRE || k == Display.GAME_UP || k == Display.GAME_DOWN);
		}
		return (k == Display.GAME_FIRE || k == Display.GAME_UP || k == Display.GAME_DOWN);
	}

	/**
	 * Returns true if this keycode is the one mapping to the symbol dialog popup
	 * 
	 * @param keyCode the keycode to check
	 * @return true if this is the star symbol *
	 */
	protected boolean isSymbolDialogKey(int keyCode) {
		return keyCode == '*';
	}

	/**
	 * Invoked to show the symbol dialog, this method can be overriden by subclasses to
	 * manipulate the symbol table
	 */
	protected void showSymbolDialog() {
		ActionListener listener = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				String text = ((Button)evt.getSource()).getText();
				insertChars(text);
				// CHANGED:: Display.getInstance().getCurrent().dispose();
			}
		};
		char[] symbolArray = getSymbolTable();
		Container symbols = new Container(new GridLayout(symbolArray.length / 5, 5));
		for(int iter = 0 ; iter < symbolArray.length ; iter++) {
			Button button = new Button("" + symbolArray[iter]);
			button.setAlignment(CENTER);
			button.addActionListener(listener);
			symbols.addComponent(button);
		}
		Command cancel = new Command("Cancel");
		Dialog.show(null, symbols, new Command[] {cancel});        
	}

	/**
	 * Returns true if this is the clear key on the device, many devices don't contain
	 * a clear key and even in those that contain it this might be an issue
	 * 
	 * @param keyCode the key code that might be the clear key
	 * @return true if this is the clear key.
	 */
	protected boolean isClearKey(int keyCode) {
		return keyCode == Form.clearSK || keyCode == Form.backspaceSK;
	}

	/**
	 * Returns true if the cursor should cycle to the beginning of the text when the
	 * user navigates beyond the edge of the text and visa versa.
	 * @return true by default
	 */
	protected boolean isCursorPositionCycle() {
		return true;
	}

	/**
	 * This method is responsible for adding a character into the field and is 
	 * the focal point for all input. It can be overriden to prevent a particular
	 * char from insertion or provide a different behavior for char insertion.
	 * It is the responsibility of this method to shift the cursor and invoke
	 * setText...
	 * <p>This method accepts a string for the more elaborate cases such as multi-char
	 * input and paste.
	 * 
	 * @param c character for insertion
	 */
	protected void insertChars(String c) {
		String currentText = getText();
		cursorCharPosition++;
		if(overwriteMode) {
			setText(currentText.substring(0, cursorCharPosition - 1) + c + 
					currentText.substring(cursorCharPosition, currentText.length()));
		} else {
			setText(currentText.substring(0, cursorCharPosition - 1) + c + 
					currentText.substring(cursorCharPosition - 1, currentText.length()));
		}
	}

	/**
	 * Commit the changes made to the text field as a complete edit operation. This
	 * is used in a numeric keypad to allow the user to repeatedly press a number
	 * to change values.
	 */
	protected void commitChange() {
		pendingCommit = false;
		previousText = null;
		pressCount = 0;
	}

	/**
	 * Invoked on a long click by the user
	 */
	private void longClick(int keyCode) {
		longClick = true;
		keyReleaseOrLongClick(keyCode, true);
	}

	/**
	 * The amount of time considered as a "long click" causing the long click method
	 * to be invoked.
	 * 
	 * @return currently defaults to 800
	 */
	protected int getLongClickDuration() {
		return 800;
	}

	/**
	 * Returns the character matching the given key code after the given amount
	 * of user presses
	 * 
	 * @param pressCount number of times this keycode was pressed
	 * @param keyCode the actual keycode input by the user
	 * @param longClick does this click constitute a long click
	 * @return the char mapping to this key or 0 if no appropriate char was found 
	 * (navigation, input mode change etc...).
	 */
	protected char getCharPerKeyCode(int pressCount, int keyCode, boolean longClick) {
		initInputModes();
		String input = inputMode;

		// if this is a first letter uppercase input mode then we need to pick either
		// the upper case mode or the lower case mode...
		if(longClick) {
			input = getLongClickInputMode();
		} else {
			if(firstUppercaseInputMode.contains(input)) {
				input = pickLowerOrUpper(input);
			}
		}

		Hashtable mode = (Hashtable)inputModes.get(input);
		String s = (String)mode.get(new Integer(keyCode));
		if(s != null) {
			pressCount = pressCount % s.length();
			return s.charAt(pressCount);
		}
		return 0;
	}

	/**
	 * Returns the input mode for the ong click mode
	 * 
	 * @return returns 123 by default
	 */
	protected String getLongClickInputMode() {
		return "123";
	}

	/**
	 * Used for the case of first sentence character should be upper case
	 */
	private String pickLowerOrUpper(String inputMode) {
		// check the character before the cursor..
		int pos = cursorCharPosition - 1;

		// we have input which has moved the cursor position further
		if(pendingCommit) {
			pos--;
		}
		String text = getText();
		if(pos >= text.length()) {
			pos = text.length() - 1;
		}
		while(pos > -1) {
			if(text.charAt(pos) == '.') {
				return inputMode.toUpperCase();
			}
			if(text.charAt(pos) != ' ') {
				return inputMode.toLowerCase();
			}
			pos--;
		}
		return inputMode.toUpperCase();
	}
	
	protected Dimension calcPreferredSize(){
		return UIManager.getInstance().getLookAndFeel().getTextFieldPreferredSize(this);
		
	}

}
