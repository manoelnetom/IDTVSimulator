package com.sun.dtv.lwuit;

import com.sun.dtv.lwuit.VirtualKeyboard;
import com.sun.dtv.lwuit.events.ActionListener;
import com.sun.dtv.lwuit.geom.Dimension;
import com.sun.dtv.lwuit.geom.Rectangle;
import com.sun.dtv.lwuit.plaf.Border;
import com.sun.dtv.lwuit.plaf.LookAndFeel;
import com.sun.dtv.lwuit.plaf.Style;
import com.sun.dtv.lwuit.plaf.UIManager;

import jregex.Pattern;

import java.awt.Color;
import java.util.Vector;
import java.util.ArrayList;

public class TextArea extends Component
{
	/**
	 * Allows any type of input into a text field, if a constraint is not supported
	 * by an underlying implementation this will be the default.
	 */
	public static final int ANY = 0;

	/**
	 * The user is allowed to enter an e-mail address.
	 * 
	 */
	public static final int EMAILADDR = 1;

	/**
	 * The user is allowed to enter only an integer value.
	 * 
	 */
	public static final int NUMERIC = 2;

	/**
	 * The user is allowed to enter a phone number.
	 * 
	 */
	public static final int PHONENUMBER = 3;

	/**
	 * The user is allowed to enter a URL.
	 *
	 */
	public static final int URL = 4;

	/**
	 * The user is allowed to enter numeric values with optional decimal
	 * fractions, for example "-123", "0.123", or ".5".
	 * 
	 */
	public static final int DECIMAL = 5;

	/**
	 * Indicates that the text entered is confidential data that should be
	 * obscured whenever possible.
	 * 
	 */
	public static final int PASSWORD = 65536;

	/**
	 * Indicates that editing is currently disallowed.
	 * 
	 */
	public static final int UNEDITABLE = 131072;

	/**
	 * Indicates that the text entered is sensitive data that the
	 * implementation must never store into a dictionary or table for use
	 * in predictive, auto-completing, or other accelerated input schemes.
	 * 
	 */
	public static final int SENSITIVE = 262144;

	/**
	 * Indicates that the text entered does not consist of words that are
	 * likely to be found in dictionaries typically used by predictive input
	 * schemes.
	 *
	 */
	public static final int NON_PREDICTIVE = 524288;

	/**
	 * This flag is a hint to the implementation that during text editing,
	 * the initial letter of each word should be capitalized.
	 * 
	 */
	public static final int INITIAL_CAPS_WORD = 1048576;

	/**
	 * This flag is a hint to the implementation that during text editing,
	 * the initial letter of each sentence should be capitalized.
	 *
	 */
	public static final int INITIAL_CAPS_SENTENCE = 2097152;

	/**
	 * Indicates the widest character in the alphabet, this is useful for detecting
	 * linebreaks internally. In CJK languages the widest char is different than W
	 * hence this functionality is exposed to developers.
	 */
	private static char widestChar = 'W';

	private static boolean hadSuccessfulEdit = false;

	private VirtualKeyboard vk;
	private int linesToScroll = 1;
	private int widthForRowCalculations = -1;
	private int constraint;
	private String text;
	private boolean editable = true;
	private int maxSize;
	private int columns = 1;
	private int rows = 1;
	private int lines;
	private int rowsGap = 2;
	private static int defaultMaxSize;
	private boolean growByContent;
	private static boolean autoDegradeMaxSize;
	private String unsupportedChars = "\t\r";
	private java.util.List actionListeners;
	private Vector rowStrings;
	private char currentAccent = 0;
	
	int cursorCharPosition = 0;

	/**
	 * Creates an area with the given rows and columns.
	 *
	 * 
	 * @param rows - the number of rows
	 * @param columns - - the number of columns
	 */
	public TextArea(int rows, int columns)
	{
		this("", defaultMaxSize, rows, columns, ANY);
	}

	/**
	 * Creates an area with the given rows, columns and constraint.
	 *
	 * 
	 * @param rows - the number of rows
	 * @param columns - - the number of columns
	 * @param constraint - one of ANY, EMAILADDR, NUMERIC, PHONENUMBER, URL, DECIMAL it can be bitwise or'd with one of PASSWORD, UNEDITABLE, SENSITIVE, NON_PREDICTIVE, INITIAL_CAPS_SENTENCE, INITIAL_CAPS_WORD. E.g. ANY | PASSWORD.
	 */
	public TextArea(int rows, int columns, int constraint)
	{
		this("", defaultMaxSize, rows, columns, constraint);
	}

	/**
	 * Creates an area with the given text, rows and columns.
	 *
	 * 
	 * @param text - the text to be displayed; if text is null, the empty  string "" will be displayed
	 * @param rows - the number of rows
	 * @param columns - - the number of columns
	 */
	public TextArea(String text, int rows, int columns)
	{
		this(text,defaultMaxSize, rows, columns, ANY); //String , maxSize, constraints= 0 (ANY)
	}

	/**
	 * Creates an area with the given text, rows, columns and constraint.
	 *
	 * 
	 * @param text - the text to be displayed; if text is null, the empty  string "" will be displayed
	 * @param rows - the number of rows
	 * @param columns - - the number of columns
	 * @param constraint - one of ANY, EMAILADDR, NUMERIC, PHONENUMBER, URL, DECIMAL it can be bitwise or'd with one of PASSWORD, UNEDITABLE, SENSITIVE, NON_PREDICTIVE, INITIAL_CAPS_SENTENCE, INITIAL_CAPS_WORD. E.g. ANY | PASSWORD.
	 */
	public TextArea(String text, int rows, int columns, int constraint)
	{
		this(text,defaultMaxSize, rows, columns, constraint); 
	}

	/**
	 * Creates an area with the given text and maximum size, this constructor
	 * will create a single line text area similar to a text field!
	 *
	 * 
	 * @param text - the text to be displayed; if text is null, the empty  string "" will be displayed
	 * @param maxSize - text area maximum size
	 */
	public TextArea(String text, int maxSize)
	{
		this(text,maxSize, 1, 1, ANY); 
	}

	/**
	 * Creates an area with the given text, this constructor
	 * will create a single line text area similar to a text field!
	 *
	 * 
	 * @param text - the text to be displayed; if text is null, the empty  string "" will be displayed
	 */
	public TextArea(String text)
	{
		this(text, Math.max(defaultMaxSize, text.length()), 1, 1, ANY); 
	}

	/**
	 * Creates an empty text area, this constructor
	 * will create a single line text area similar to a text field!
	 * 
	 */
	public TextArea()
	{
		this("");
	}

	/**
	 * Creates an area with the given text, maximum size, rows, columns and constraint 
	 * 
	 * @param text the text to be displayed; if text is null, the empty 
	 * string "" will be displayed
	 * @param maxSize text area maximum size
	 * @param rows the number of rows
	 * @param columns - the number of columns
	 * @param constraint one of ANY, EMAILADDR, NUMERIC, PHONENUMBER, URL, DECIMAL
	 * it can be bitwised or'd with one of PASSWORD, UNEDITABLE, SENSITIVE, NON_PREDICTIVE,
	 * INITIAL_CAPS_SENTENCE, INITIAL_CAPS_WORD. E.g. ANY | PASSWORD.
	 */
	private TextArea(String text, int maxSize, int rows, int columns, int constraint){
		actionListeners = new ArrayList();
		this.maxSize = maxSize;
		setText(text);
		setConstraint(constraint);
		this.rows = rows;
		this.columns = columns;
		setUIID("TextArea");
		LookAndFeel laf = UIManager.getInstance().getLookAndFeel();
		setSmoothScrolling(laf.isDefaultSmoothScrolling());
		
		Color selectionBorderColor = getStyle().getBgSelectionColor();
		
		getStyle().setBgColor(Color.WHITE);
		getStyle().setBgSelectionColor(Color.WHITE);
		getStyle().setFgColor(Color.BLACK);
		getStyle().setFgSelectionColor(Color.BLACK);
		Border border = getStyle().getBorder();
		if(border != null){
			border.setFocusedInstance(Border.createDoubleBorder(5, selectionBorderColor));
		}
		
	}

	/**
	 * Sets the constraint.
	 *
	 * 
	 * @param constraint - one of ANY, EMAILADDR, NUMERIC, PHONENUMBER, URL, DECIMAL it can be bitwise or'd with one of PASSWORD, UNEDITABLE, SENSITIVE, NON_PREDICTIVE, INITIAL_CAPS_SENTENCE, INITIAL_CAPS_WORD. E.g. ANY | PASSWORD.
	 * @see getConstraint()
	 */
	public void setConstraint(int constraint)
	{
		this.constraint = constraint;
	}

	/**
	 * Returns the editing constraint value.
	 *
	 * 
	 * 
	 * @return the editing constraint value
	 * @see setConstraint(int)
	 */
	public int getConstraint()
	{
		return this.constraint;
	}

	private char convertIntToChar(int param) {
		if ((param == '%') ||
				(param == '$') ||
				(param == '&') ||
				(param == '#') ||
				(param == '_') ||
				(param == '{') ||
				(param == '}') ||
				(param == '=') ||
				(param == ',') ||
				(param == '\'') ||
				(param == '[') ||
				(param == '*') ||
				(param == '\\') ||
				(param == '@') ||
				(param == '(') ||
				(param == ':') ||
				(param == ']') ||
				(param == '-') ||
				(param == '<') ||
				(param == '>') ||
				(param == '+') ||
				(param == ')') ||
				(param == ';') ||
				(param == '.') ||
				(param == '!') ||
				(param == '?') ||
				(param == '/') ||
				(param == '|') ||
				(param == ' ') ||
				(param == '\u00C7') ||
				(param == '\u00E7') ||
				(param == '\u00A5') ||
				(param == '\u00A3') ||
				(param == '\u00B1') ||
				(param == '\u00B2') ||
				(param == '\u00B3') ||
				(param == '\u00B5') ||
				(param == '\u00BA') ||
				(param == '\u00BF') ||
				(param == '\u00A2') ||
				(param == '\u00A7') ||
				(param == '\u00A9') ||
				(param == '\u00AA') ||
				(param == '\u00AE') ||
				(param == '\u0060') ||
				(param == '\u005E') ||
				(param == '\u00B4') ||
				(param == '\u007E')) {
			return (char)param;
		} else if (param == '\r' || param == '\n') {
			return (char)'\n';
		} else if (param >= 'A' && param <= 'Z') {
			return (char)param;
		} else if (param >= 'a' && param <= 'z') {
			return (char)param;
		} else if (param >= '0' && param <= '9') {
			return (char)param;
		} 

		return '@';
	}

	void appendText(String append) {
		String text = getText();
		int position = getInternalCursorPosition();
		
		if (position < 0) {
			position = 0;
		}

		setInternalText(text.substring(0, position) + append + text.substring(position));

		if (text.length() != getText().length()) {
			setInternalCursorPosition(position+append.length());
		}
	}

	public void setInternalText(String t) {
		final int mask = 0x7;
		
		String reg = null;
	
		if ((this.getConstraint() & mask) == TextArea.NUMERIC){
			// reg = "[\\+\\-0-9]+";
			reg = "[0-9]+";
		} else if ((this.getConstraint() & mask) == TextArea.DECIMAL){
			reg = "[\\+\\-\\.0-9]+";
		} else if ((this.getConstraint() & mask) == TextArea.EMAILADDR){
			reg = "[A-Za-z0-9\\._\\-@]+";
		} else if ((this.getConstraint() & mask) == TextArea.PHONENUMBER){
			reg = "[0-9\\-#\\+\\* \\(\\)]+";
		} else if ((this.getConstraint() & mask) == TextArea.URL){
			reg = "[A-Za-z0-9\\._\\-/\\?:\\+\\-\\*]+";
		}

		if (t != null && t.length() > 0 && reg != null) {
			Pattern p = new Pattern(reg);

			if (!p.matches(t)) {
				return ;
			}
		}

		this.text = (t != null) ? t : "";
		
		setShouldCalcPreferredSize(true);
		
		if(maxSize < text.length()) {
			maxSize = text.length() + 1;
		}

		// reset scrolling
		setScrollX(0);
		setScrollY(0);

		// special case to make the text field really fast...
		rowStrings = this.getRowStrings();

		if (text == null || text.length() == 0) {
			setInternalCursorPosition(-1);
		} else {
			setInternalCursorPosition(getText().length());
		}

		repaint();
	}

	/**
	 * Sets the text within this text area.
	 *
	 * 
	 * @param t - new value for the text area
	 * @see getText()
	 */
	public void setText(String t) {
		final int mask = 0x7;
		
		String reg = null;
	
		if ((this.getConstraint() & mask) == TextArea.NUMERIC){
			reg = "[\\+-]?[0-9]+";
		} else if ((this.getConstraint() & mask) == TextArea.DECIMAL){
			reg = "(\\.[0-9]*)|([\\+-]?([1-9][0-9]*|[0-9])(\\.[0-9]*)?)";
		} else if ((this.getConstraint() & mask) == TextArea.EMAILADDR){
			reg = "[A-Za-z0-9\\._-]+(@([A-Za-z]+.)*[A-Za-z]+)";
		} else if ((this.getConstraint() & mask) == TextArea.PHONENUMBER){
			// reg = "([0-9]{1,2}-)?[0-9]{3,4}-[0-9]{4}";
			reg = "[0-9\\-#\\+\\* \\(\\)]+";
		} else if ((this.getConstraint() & mask) == TextArea.URL){
			// reg = "([a-z]{3,5}://)?([a-zA-Z0-9\\-])+(\\.[a-zA-Z0-9]+)?\\.?(:[0-9]{1,5})?(/([a-zA-z0-9]*))*";
			// reg = "([a-z]+://)?([a-zA-Z0-9\\-])+(\\.[a-zA-Z0-9]+)?\\.?(:[0-9]+)?(/([a-zA-z0-9]*))*";
			reg = "([A-Za-z]+://)?[A-Za-z0-9\\._\\-/\\?:\\+\\-\\*]+";
		}

		if (t != null && t.length() > 0 && reg != null) {
			Pattern p = new Pattern(reg);

			if (!p.matches(t)) {
				return ;
			}
		}

		this.text = (t != null) ? t : "";
		
		setShouldCalcPreferredSize(true);
		
		if(maxSize < text.length()) {
			maxSize = text.length() + 1;
		}

		// reset scrolling
		setScrollX(0);
		setScrollY(0);

		// special case to make the text field really fast...
		rowStrings = this.getRowStrings();

		if (text == null || text.length() == 0) {
			setInternalCursorPosition(-1);
		} else {
			setInternalCursorPosition(getText().length());
		}

		repaint();
	}

	/**
	 * Returns the text in the text area.
	 *
	 * 
	 * 
	 * @return the text in the text area
	 * @see setText(java.lang.String)
	 */
	public String getText()
	{
		return this.text;
	}

	/**
	 * Returns true if this area is editable.
	 *
	 * 
	 * 
	 * @return true if this area is editable
	 */
	public boolean isEditable()
	{
		return editable;
	}

	/**
	 * Sets this text area to be editable or readonly.
	 *
	 * 
	 * @param b - true is text are is editable; otherwise false
	 */
	public void setEditable(boolean b)
	{
		this.editable = b;
	}

	/**
	 * Returns the maximum size for the text area.
	 *
	 * 
	 * 
	 * @return the maximum size for the text area
	 * @see setMaxSize(int)
	 */
	public int getMaxSize()
	{
		return this.maxSize;
	}

	/**
	 * Sets the maximum size of the text area.
	 *
	 * 
	 * @param maxSize - the maximum size of the text area
	 * @see getMaxSize()
	 */
	public void setMaxSize(int maxSize)
	{
		this.maxSize = maxSize;
	}

	/**
	 * Performs a backspace operation
	 *
	 */
	protected void deleteChar() {
		String text = getText();
		int position = getInternalCursorPosition();
		int lines = getLines();

		if (position > 0) {
			setInternalText(text.substring(0, position-1) + text.substring(position));
			setInternalCursorPosition(position-1);
		} else {
			if (text.length() > 0) {
				setInternalText(text.substring(1));
			}

			setInternalCursorPosition(0);
		}
	}

	void internalKeyPressed(int keyCode) {
		String newText = null;

		if (keyCode == 127) {
			deleteChar();
		} else if (keyCode == 8) {
			deleteChar();
		} else {
			char code = convertIntToChar(keyCode);
			
			if (code == '\u00b4' || code == '\u0060' || code == '\u007e' || code == '\u005e') {
				if (currentAccent == 0) {
					currentAccent = code;
				} else {
					appendText("" + currentAccent);
					appendText("" + code);

					currentAccent = 0;
				}
			} else {
				if (currentAccent != 0) {
					if (currentAccent == '\u00b4') {
						if (code == 'A') {
							code = '\u00C1';
						} else if (code == 'E') {
							code = '\u00C9';
						} else if (code == 'I') {
							code = '\u00CD';
						} else if (code == 'O') {
							code = '\u00D3';
						} else if (code == 'U') {
							code = '\u00DA';
						} else if (code == 'a') {
							code = '\u00E1';
						} else if (code == 'e') {
							code = '\u00E9';
						} else if (code == 'i') {
							code = '\u00ED';
						} else if (code == 'o') {
							code = '\u00F3';
						} else if (code == 'u') {
							code = '\u00FA';
						} else {
							currentAccent = 0;
							appendText("" + currentAccent);
							return;
						}
					} else if (currentAccent == '\u0060') {
						if (code == 'A') {
							code = '\u00C0';
						} else if (code == 'E') {
							code = '\u00C8';
						} else if (code == 'I') {
							code = '\u00CC';
						} else if (code == 'O') {
							code = '\u00D2';
						} else if (code == 'U') {
							code = '\u00D9';
						} else if (code == 'a') {
							code = '\u00E0';
						} else if (code == 'e') {
							code = '\u00E8';
						} else if (code == 'i') {
							code = '\u00EC';
						} else if (code == 'o') {
							code = '\u00F2';
						} else if (code == 'u') {
							code = '\u00F9';
						} else {
							currentAccent = 0;
							appendText("" + currentAccent);
							return;
						}
					} else if (currentAccent == '\u007e') {
						if (code == 'A') {
							code = '\u00C3';
						} else if (code == 'E') {
							code = '\u00CB';
						} else if (code == 'I') {
							code = '\u00CF';
						} else if (code == 'O') {
							code = '\u00D5';
						} else if (code == 'U') {
							code = '\u00DC';
						} else if (code == 'a') {
							code = '\u00E3';
						} else if (code == 'e') {
							code = '\u00EB';
						} else if (code == 'i') {
							code = '\u00EF';
						} else if (code == 'o') {
							code = '\u00F5';
						} else if (code == 'u') {
							code = '\u00FC';
						} else {
							currentAccent = 0;
							appendText("" + currentAccent);
							return;
						}
					} else if (currentAccent == '\u005e') {
						if (code == 'A') {
							code = '\u00C2';
						} else if (code == 'E') {
							code = '\u00CA';
						} else if (code == 'I') {
							code = '\u00CE';
						} else if (code == 'O') {
							code = '\u00D4';
						} else if (code == 'U') {
							code = '\u00DB';
						} else if (code == 'a') {
							code = '\u00E2';
						} else if (code == 'e') {
							code = '\u00EA';
						} else if (code == 'i') {
							code = '\u00EE';
						} else if (code == 'o') {
							code = '\u00F4';
						} else if (code == 'u') {
							code = '\u00FB';
						} else {
							currentAccent = 0;
							appendText("" + currentAccent);
							return;
						}
					}

					currentAccent = 0;

					appendText("" + code);
				} else {
					appendText("" + (char)code);
				}
			}
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
	public void keyPressed(int keyCode) {
		super.keyPressed(keyCode);

		Rectangle rect = new Rectangle(getScrollX(), getScrollY(), getWidth(), getHeight());

		int action = com.sun.dtv.lwuit.Display.getInstance().getGameAction(keyCode);

		setHandlesInput(true);

		if (action == Display.GAME_LEFT) {
			if (this instanceof com.sun.dtv.lwuit.TextField) {
				int position = getInternalCursorPosition();

				setInternalCursorPosition(getInternalCursorPosition()-1);
				
				if (position == getInternalCursorPosition() || getInternalCursorPosition() < 0) {
					setHandlesInput(false);
				}
			}
		} else if (action == Display.GAME_RIGHT) {
			if (this instanceof com.sun.dtv.lwuit.TextField) {
				int position = getInternalCursorPosition();
				
				setInternalCursorPosition(getInternalCursorPosition()+1);
				
				if (position == getInternalCursorPosition()) {
					setHandlesInput(false);
				}
			}
		} else 	if (action == Display.GAME_DOWN) {
			Font textFont = getStyle().getFont();

			if((getScrollY() + getHeight()) <(rowsGap + getStyle().getFont().getHeight()) * getLines()) {
				rect.setY(rect.getY() + (textFont.getHeight() + rowsGap) * linesToScroll);
				scrollRectToVisible(rect, this);
			} else {
				setHandlesInput(false);
			}
		} else if (action == Display.GAME_UP) {
			Font textFont = getStyle().getFont();

			if(getScrollY() > 0) {
				rect.setY(Math.max(0, rect.getY() - (textFont.getHeight() + rowsGap) * linesToScroll));
				scrollRectToVisible(rect, this);
			} else {
				setHandlesInput(false);
			}
		} else if (isEditable()) {
			if (action == Display.GAME_FIRE) {
				final int mask = 0x7;

				if ((this.getConstraint() & mask) == TextArea.NUMERIC){
					vk = new VirtualKeyboard(VirtualKeyboard.NUMERIC_MODE, this);
				} else if ((this.getConstraint() & mask) == TextArea.DECIMAL){
					vk = new VirtualKeyboard(VirtualKeyboard.NUMERIC_MODE, this);
				} else if ((this.getConstraint() & mask) == TextArea.EMAILADDR){
					vk = new VirtualKeyboard(VirtualKeyboard.QWERTY_MODE, this);
				} else if ((this.getConstraint() & mask) == TextArea.PHONENUMBER){
					vk = new VirtualKeyboard(VirtualKeyboard.PHONE_MODE, this);
					// vk = new VirtualKeyboard(VirtualKeyboard.NUMERIC_MODE, this);
				} else if ((this.getConstraint() & mask) == TextArea.URL){
					vk = new VirtualKeyboard(VirtualKeyboard.INTERNET_MODE, this);
				} else {
					vk = new VirtualKeyboard(VirtualKeyboard.QWERTY_MODE, this);
				}

				vk.show();
			} else {
				internalKeyPressed(keyCode);
			}
		}

		if (handlesInput()) {
			repaint();
		}
	}

	public void setInternalCursorPosition(int pos)
	{
		if (pos < -1 || this.text == null || this.text.length() == 0) {
			pos = 0;
		} else if (pos > this.text.length()) {
			pos = this.text.length();
		}

		cursorCharPosition = pos;
	}

	public int getInternalCursorPosition()
	{
		if (getText() == null) {
			return 0;
		}

		return Math.min(getText().length(), cursorCharPosition);
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
		int action = com.sun.dtv.lwuit.Display.getInstance().getGameAction(keyCode);

		if(isEditable()){
			if (action == Display.GAME_FIRE) {
				onClick();
				return;
			}
		}
	}

	/**
	 * <B>Description copied from class: <CODE><A HREF="../../../../com/sun/dtv/lwuit/Component.html#isScrollableY()">Component</A></CODE></B></DD>
	 * Indicates whether the component should/could scroll on the Y axis.
	 *
	 * 
	 * @return whether the component is scrollable on the X axis
	 * @see isScrollableY in class Component
	 */
	public boolean isScrollableY()
	{
		return (rowsGap + getStyle().getFont().getHeight()) * getLines() > getHeight();
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
		// prevent a drag operation from going into edit mode
		if(isDragActivated()) {
			super.pointerReleased(x, y);
		} else {
			super.pointerReleased(x, y);
			if(isEditable()){
				onClick();
			}
		}
	}

	/**
	 * Returns the number of columns in the text area.
	 *
	 * 
	 * 
	 * @return the number of columns in the text area
	 * @see setColumns(int)
	 */
	public int getColumns()
	{
		return this.columns;
	}

	/**
	 * Returns the number of rows in the text area.
	 *
	 * 
	 * 
	 * @return the number of rows in the text area
	 * @see setRows(int)
	 */
	public int getRows()
	{
		return this.rows;
	}

	/**
	 * Sets the number of columns in the text area.
	 *
	 * 
	 * @param columns - number of columns
	 * @see getColumns()
	 */
	public void setColumns(int columns)
	{
		setShouldCalcPreferredSize(true);
		this.columns = columns;
	}

	/**
	 * Sets the number of rows in the text area.
	 *
	 * 
	 * @param rows - number of rows
	 * @see getRows()
	 */
	public void setRows(int rows)
	{
		setShouldCalcPreferredSize(true);

		this.rows = rows;
	}

	/**
	 * Returns the number of text lines in the TextArea.
	 *
	 * 
	 * 
	 * @return the number of text lines in the TextArea
	 */
	public int getLines()
	{
		return getRowStrings().size();
	}

	/**
	 * Returns the text at the given row of the text box.
	 *
	 * 
	 * @param line - the line number in the text box
	 * @return the text at the given row
	 */
	public String getTextAt(int line)
	{
        Vector rowsV = getRowStrings();
        int size = rowsV.size();
        if(size == 0){
            return "";
        }
        if(line >= size){
            return (String)rowsV.elementAt(size-1);        
        }            
        return (String)rowsV.elementAt(line);
	}

	/**
	 * Gets the num of pixels gap between the rows.
	 *
	 * 
	 * 
	 * @return the gap between rows in pixels
	 * @see setRowsGap(int)
	 */
	public int getRowsGap()
	{
		return this.rowsGap;
	}

	/**
	 * The gap in pixels between rows.
	 *
	 * 
	 * @param rowsGap - num of pixels to gap between rows
	 * @see getRowsGap()
	 */
	public void setRowsGap(int rowsGap)
	{
		this.rowsGap = rowsGap;
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
	 * @see paint in class Component
	 */
	public void paint(Graphics g)
	{
		UIManager.getInstance().getLookAndFeel().drawTextArea(g, this);
	}

	/**
	 * Add an action listener which is invoked when the text area was modified
	 * not during modification. A text <b>field</b> might never fire an action
	 * event if it is edited in place and the user never leaves the text field!
	 *
	 * 
	 * @param a - actionListener
	 * @see removeActionListener(com.sun.dtv.lwuit.events.ActionListener)
	 */
	public void addActionListener(ActionListener listener)
	{
		if (actionListeners.contains((Object)listener) == false) {
			actionListeners.add((Object)listener);
		}
	}

	/**
	 * Removes an action listener.
	 *
	 * 
	 * @param a - actionListener
	 * @see addActionListener(com.sun.dtv.lwuit.events.ActionListener)
	 */
	public void removeActionListener(ActionListener listener)
	{
		if (actionListeners.contains((Object)listener) == true) {
			actionListeners.remove((Object)listener);
		}
	}

	/**
	 * Sets the default limit for the native text box size.
	 *
	 * 
	 * @param value - default value for the size of the native text box
	 */
	public static void setDefaultMaxSize(int value)
	{
		defaultMaxSize = value;
	}

	/**
	 * Indicates that the text area should "grow" in height based on the content beyond the
	 * limits indicate by the rows variable.
	 *
	 * 
	 * 
	 * @return true if the text component should grow and false otherwise
	 */
	public boolean isGrowByContent()
	{
		return growByContent;
	}

	/**
	 * Indicates that the text area should "grow" in height based on the content beyond the
	 * limits indicate by the rows variable.
	 *
	 * 
	 * @param growByContent - true if the text component should grow and false otherwise
	 */
	public void setGrowByContent(boolean growByContent)
	{
		this.growByContent = growByContent;
	}

	/**
	 * Indicates whether a high value for default maxSize will be reduced to a lower
	 * value if the underlying platform throws an exception.
	 *
	 * 
	 * @param value - if true then the platform will auto degrade.
	 */
	public static void setAutoDegradeMaxSize(boolean value)
	{
		autoDegradeMaxSize = value;
	}

	/**
	 * Indicates whether a high value for default maxSize will be reduced to a lower
	 * value if the underlying platform throws an exception.
	 *
	 * 
	 * 
	 * @return the current setting for auto degrade.
	 */
	public static boolean isAutoDegradeMaxSize()
	{
		return autoDegradeMaxSize;
	}

	/**
	 * Unsupported characters is a string that contains characters that cause issues
	 * when rendering on some problematic fonts. The rendering engine can thus remove them
	 * when drawing.
	 *
	 * 
	 * 
	 * @return String containing the unsupported characters
	 * @see setUnsupportedChars(java.lang.String)
	 */
	public String getUnsupportedChars()
	{
		return this.unsupportedChars;
	}

	/**
	 * Returns the number of actual rows in the text area taking into consideration
	 * growsByContent
	 * 
	 * @return the number of rows in the text area
	 */
	public int getActualRows() {
		if(growByContent) {
			return Math.max(rows, getLines());
		}
		return rows;
	}

	/**
	 * Unsupported characters is a string that contains characters that cause issues
	 * when rendering on some problematic fonts. The rendering engine can thus remove them
	 * when drawing.
	 *
	 * 
	 * @param unsupportedChars - String containing the unsupported characters
	 * @see getUnsupportedChars()
	 */
	public void setUnsupportedChars(String unsupportedChars)
	{
		this.unsupportedChars = unsupportedChars;
	}

	private Vector getRowStrings() {
		initRowString();
		setShouldCalcPreferredSize(true);

		return rowStrings;
	}

	private void onClick(){
		if(isEditable()) {
			editString();
		}
	}

	public static String removeChar(String s, char c) {
		String r = "";

		for (int i = 0; i < s.length(); i ++) {
			if (s.charAt(i) != c) r += s.charAt(i);
		}

		return r;
	}

	private void initRowString() {
		Style style = getStyle();
		rowStrings= new Vector();
		widthForRowCalculations = getWidth() - style.getPadding(Component.RIGHT) - style.getPadding(Component.LEFT);
		// single line text area is essentially a text field, we call the method to allow subclasses to override it
		if ((this.rows == 1) || (widthForRowCalculations<=0)) {
			rowStrings.addElement(removeChar(getText(), '\n'));
			return;
		}
		if(text == null || text.equals("")){
			return;
		}
		char[] text = preprocess(getText()+"_");
		int rows = this.rows;
		if(growByContent) {
			rows = Math.max(rows, getLines());
		}
		Font font = style.getFont();
		int charWidth = font.charWidth(widestChar);
		Style selectedStyle = getStyle();
		if(selectedStyle.getFont() != style.getFont()) {
			int cw = selectedStyle.getFont().charWidth(widestChar);
			if(cw > charWidth) {
				charWidth = cw;
				font = selectedStyle.getFont();
			}
		}
		style = getStyle();
		int tPadding = style.getPadding(Component.RIGHT) + style.getPadding(Component.LEFT);
		int textAreaWidth = getWidth() - tPadding;
		int minCharactersInRow = Math.max(1, textAreaWidth / charWidth);
		int rowIndex=0;
		int from=0;
		int to=from+minCharactersInRow;
		int textLength=text.length;
		String rowText;
		int i,spaceIndex;

		// if there is any possibility of a scrollbar we need to reduce the textArea width to accommodate it
		if(textLength / minCharactersInRow > Math.max(2, rows)) {
			textAreaWidth -= UIManager.getInstance().getLookAndFeel().getVerticalScrollWidth();
			textAreaWidth -= charWidth/2;
		}
		String unsupported = getUnsupportedChars();

		/*
			 iteration over the string using indexes, from - the beginning of the row , to - end of a row
			 for each row we will try to search for a "space" character at the end of the row ( row is text area available width)
			 indorder to improve the efficiency we do not search an entire row but we start from minCharactersInRow which indicates
			 what is the minimum amount of characters that can feet in the text area width.
			 if we dont find we will go backwards and search for the first space available,
			 if there is no space in the entire row we will cut the line inorder to fit in.
			 */

		//Don't rely on the fact that short text has no newline character. we always have to parse the text.
		to = Math.max( Math.min(textLength-1,to), 0 );
		while(to<textLength) {
			if(to>textLength){
				to=textLength;
			}

			spaceIndex=-1;
			rowText="";
			int maxLength = to;

			String currentRow = "";

			// search for "space" character at close as possible to the end of the row
			for( i=to; i < textLength && fastCharWidthCheck(text, from, i - from + 1, textAreaWidth, charWidth, font) ; i++){
				char c = text[i];
				currentRow+=c;
				if(font.stringWidth(currentRow) >= textAreaWidth) {
					break;
				}
				if(unsupported.indexOf(c) > -1) {
					text[i] = ' ';
					c = ' ';
				}
				if(c == ' ' || c == '\n') {
					spaceIndex=i;
					// newline has been found. We can end the loop here as the line cannot grow more
					if (c == '\n')
						break;
				}
				maxLength++;
			}

			// if we got to the end of the text use the entire row, also if space is next character (in the next row) we can cut the line
			if(i == textLength || text[i] == ' ' || text[i] == '\n') {
				spaceIndex=i;
			}

			// if we found space in the limit width of the row (searched only from minCharactersInRow)
			if(spaceIndex!=-1){
				// make sure that if we have a newline character before the end of the line we should break there instead
				int newLine = indexOf(text, '\n', from, spaceIndex - from);
				if(newLine > -1 && newLine < spaceIndex) {
					spaceIndex = newLine;
				}

				rowText = new String(text, from, spaceIndex - from);
				from=spaceIndex+1;

			} // if there is no space from minCharactersInRow to limit need to search backwards
			else{
				for( i=to; spaceIndex==-1 && i>=from ; i--){
					char chr = text[i];
					if(chr == ' ' || chr == '\n' || chr == '\t') {
						spaceIndex=i;

						// don't forget to search for line breaks in the remaining part. otherwise we overlook possible line breaks!
						int newLine = indexOf(text, '\n', from, i - from);
						if(newLine > -1 && newLine < spaceIndex) {
							spaceIndex = newLine;
						}
						rowText = new String(text, from, spaceIndex - from);
						from=spaceIndex+1;
					}

				}
				if(spaceIndex==-1) {
					// from = to + 1;
					if(maxLength <= 0) {
						maxLength = 1;
					}
					spaceIndex = maxLength;
					rowText = new String(text, from, spaceIndex - from);
					from = spaceIndex;
				}
			}

			rowStrings.addElement(removeChar(rowText, '\n'));
			
			//adding minCharactersInRow doesn't work if what is left is less then minCharactersInRow
			
			to=from;
			rowIndex++;
		}
	}

	void editString() {
		if(autoDegradeMaxSize && (!hadSuccessfulEdit) && (maxSize > 1024)) {
			try {
				Display.getInstance().editString(this, getMaxSize(), getConstraint(), getText());
			} catch(IllegalArgumentException err) {
				maxSize -= 1024;
				setDefaultMaxSize(maxSize);
				editString();
			}
		} else {
			Display.getInstance().editString(this, getMaxSize(), getConstraint(), getText());
		}
	}

	/**
	 * Override this to modify the text for rendering in cases of invalid characters 
	 * for display, this method allows the developer to replace such characters e.g.:
	 * replace "\\t" with 4 spaces
	 * 
	 * @param text the text to process
	 * @return the given string as a processed char array ready for rendering
	 */
	protected char[] preprocess(String text) {
		return text.toCharArray();
	}

	private int indexOf(char[] t, char c, int offset, int length) {
		for(int iter = offset ; iter < t.length && iter < offset+length; iter++) {
			if(t[iter] == c) {
				return iter;
			}
		}
		return -1;
	}

	private boolean fastCharWidthCheck(char[] chrs, int off, int length, int width, int charWidth, Font f) {
		if(length * charWidth < width) {
			return true;
		}
		return f.charsWidth(chrs, off, length) < width;
	}
	
	protected Dimension calcPreferredSize(){
		return UIManager.getInstance().getLookAndFeel().getTextAreaPreferredSize(this);
		
	}

}
