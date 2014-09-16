package com.sun.dtv.lwuit;

import com.sun.dtv.lwuit.geom.Rectangle;
import com.sun.dtv.lwuit.plaf.Style;
import com.sun.dtv.lwuit.geom.Dimension;

import com.sun.dtv.lwuit.layouts.BorderLayout;
import com.sun.dtv.lwuit.layouts.GridLayout;
import com.sun.dtv.lwuit.layouts.FlowLayout;

import com.sun.dtv.ui.event.RemoteControlEvent;

import com.sun.dtv.lwuit.events.ActionEvent;
import com.sun.dtv.lwuit.events.ActionListener;

import javax.tv.xlet.XletContext;

import java.awt.Color;

import java.util.ArrayList;
import java.util.Iterator;
import com.sun.dtv.ui.ViewOnlyComponent;
public class VirtualKeyboard extends Dialog {

	// This keymap represents qwerty keyboard
	public static final String[][] QWERTY_MAP = new String[][]{
		{"q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "\u00B4"},
		{"a", "s", "d", "f", "g", "h", "j", "k", "l", "\u00E7", "\u007E"},
		{"$Shift$", "z", "x", "c", "v", "b", "n", "m", "$Enter$"},
		{"$Mode$", "$Space$", "$Delete$"}
	};

	// This keymap represents numbers keyboard
	public static final String[][] PHONE_MAP = new String[][]{
		{"1", "2", "3", "+"},
		{"4", "5", "6", "-"},
		{"7", "8", "9", "("},
		{"*", "0", "#", ")"},
		{"$Delete$", "$Enter$"}
	};

	// This keymap represents numbers keyboard
	public static final String[][] NUMERIC_MAP = new String[][]{
		{"1", "2", "3", "+"},
		{"4", "5", "6", "-"},
		{"7", "8", "9", "*"},
		{".", "0", "#", "/"},
		{"$Delete$", "$Enter$"}
	};

	// This keymap represents numbers and symbols keyboard
	public static final String[][] NUMERIC_SYMBOL_MAP = new String[][]{
		{"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"}, 
		{"@", "(", ")", "*", "-", "<", ">", "+"},
		{"$Shift$", ":", ";", ".", "!", "?", "/", "$Enter$"}, 
		{"$Mode$", "$Space$", "$Delete$"}
	};

	// This keymap represents symbols keyboard
	public static final String[][] SYMBOL_MAP = new String[][]{
		{"\u00B2", "@", "(", ")", "*", "-", "<", ">", "+", "\u00B3", "\u00B5"},
		{"\u00B1", "\u00BA", ":", ";", ".", "!", "?", "/", "\u00A3", "\u00BF"},
		{"$Shift$", "\\o/", ";)", ":)", ":(", ":P", ":D", "$Enter$"},
		{"$Mode$", "$Space$", "$Delete$"}
	};

	// This keymap represents internet keyboard
	public static final String[][] INTERNET_MAP = new String[][]{
		{"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "+"}, 
		{"q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "%"},
		{"a", "s", "d", "f", "g", "h", "j", "k", "l", "-", "/"},
		{"z", "x", "c", "v", "b", "n", "m", ".", ":", "?"},
		{"$http://$", "$Shift$", "$Space$", "$Delete$", "$Enter$", "$.com$"}
	};

	// The String that represent the qwerty mode.
	public static final int QWERTY_MODE = 1; // "ABC";
	// The String that represent the phone mode.
	public static final int PHONE_MODE = 2; // "123";
	// The String that represent the numbers mode.
	public static final int NUMERIC_MODE = 3; // "123";
	// The String that represent the numbers sybols mode.
	public static final int NUMERIC_SYMBOL_MODE = 4; // ".,123";
	// The String that represent the symbols mode.
	public static final int SYMBOL_MODE = 5; // ".,?";
	// The String that represent the symbols mode.
	public static final int INTERNET_MODE = 6; // ".,?";

	private InputField display;
	private String currentMode;
	private java.util.List keys;
	private KeyButton shiftButton;
	private boolean shiftPressed;
	private boolean isPassword;
	private int mode;
	private Style style1,
					style2,
					style3;

	public VirtualKeyboard() {
		this(QWERTY_MODE, new TextArea());
	}

	public VirtualKeyboard(int mode, TextArea externalDisplay) {
		super("Virtual Keyboard");

		this.currentMode = "ABC";
		this.mode = mode;
		this.shiftPressed = false;
		this.isPassword = false;
		this.shiftButton = null;

		this.display = new InputField(externalDisplay);
		
		style1 = new Style();
		style2 = new Style();
		style3 = new Style();

		style1.setBgColor(new Color(0x65, 0x75, 0x85));
		style1.setFgColor(new Color(0xd0, 0xd0, 0xd0));

		style2.setBgColor(new Color(0x65, 0x75, 0x85));
		style2.setFgColor(new Color(0xd0, 0xd0, 0xd0));

		style3.setBgColor(new Color(0xa0, 0xa0, 0xa0));
		style3.setFgColor(new Color(0x20, 0x20, 0x20));

		if (mode == QWERTY_MODE) {
			this.currentMode = "123";
		} else if (mode == PHONE_MODE) {
			this.currentMode = "ABC";
		} else if (mode == NUMERIC_MODE) {
			this.currentMode = "ABC";
		} else if (mode == SYMBOL_MODE) {
			this.currentMode = "ABC";
		} else if (mode == NUMERIC_SYMBOL_MODE) {
			this.currentMode = "ABC";
		} else if (mode == INTERNET_MODE) {
			this.currentMode = "ABC";
		}

		buildKeyboard(mode);
		
		setX((1280-640)/2);
		setY((720-320)/2);

		setSize(new Dimension(640, 320));
	}

	void switchKeys() {
		if (keys != null) {
			for (Iterator i=keys.iterator(); i.hasNext(); ) {
				java.util.List list = (java.util.List)i.next();

				for (Iterator j=list.iterator(); j.hasNext(); ) {
					KeyButton button = (KeyButton)j.next();
					String key = switchKey(button.getLabel());

					if (key != null) {
						button.setLabel(switchKey(button.getLabel()));
					}
				}
			}
		}
	}

	String switchKey(String key) {
		if (key.equals("a")) {
			return "A";
		} else if (key.equals("b")) {
			return "B";
		} else if (key.equals("c")) {
			return "C";
		} else if (key.equals("d")) {
			return "D";
		} else if (key.equals("e")) {
			return "E";
		} else if (key.equals("f")) {
			return "F";
		} else if (key.equals("g")) {
			return "G";
		} else if (key.equals("h")) {
			return "H";
		} else if (key.equals("i")) {
			return "I";
		} else if (key.equals("j")) {
			return "J";
		} else if (key.equals("k")) {
			return "K";
		} else if (key.equals("l")) {
			return "L";
		} else if (key.equals("m")) {
			return "M";
		} else if (key.equals("n")) {
			return "N";
		} else if (key.equals("o")) {
			return "O";
		} else if (key.equals("p")) {
			return "P";
		} else if (key.equals("q")) {
			return "Q";
		} else if (key.equals("r")) {
			return "R";
		} else if (key.equals("s")) {
			return "S";
		} else if (key.equals("t")) {
			return "T";
		} else if (key.equals("u")) {
			return "U";
		} else if (key.equals("v")) {
			return "V";
		} else if (key.equals("w")) {
			return "W";
		} else if (key.equals("x")) {
			return "X";
		} else if (key.equals("y")) {
			return "Y";
		} else if (key.equals("z")) {
			return "Z";
		} else if (key.equals("A")) {
			return "a";
		} else if (key.equals("B")) {
			return "b";
		} else if (key.equals("C")) {
			return "c";
		} else if (key.equals("D")) {
			return "d";
		} else if (key.equals("E")) {
			return "e";
		} else if (key.equals("F")) {
			return "f";
		} else if (key.equals("G")) {
			return "g";
		} else if (key.equals("H")) {
			return "h";
		} else if (key.equals("I")) {
			return "i";
		} else if (key.equals("J")) {
			return "j";
		} else if (key.equals("K")) {
			return "k";
		} else if (key.equals("L")) {
			return "l";
		} else if (key.equals("M")) {
			return "m";
		} else if (key.equals("N")) {
			return "n";
		} else if (key.equals("O")) {
			return "o";
		} else if (key.equals("P")) {
			return "p";
		} else if (key.equals("Q")) {
			return "q";
		} else if (key.equals("R")) {
			return "r";
		} else if (key.equals("S")) {
			return "s";
		} else if (key.equals("T")) {
			return "t";
		} else if (key.equals("U")) {
			return "u";
		} else if (key.equals("V")) {
			return "v";
		} else if (key.equals("W")) {
			return "w";
		} else if (key.equals("X")) {
			return "x";
		} else if (key.equals("Y")) {
			return "y";
		} else if (key.equals("Z")) {
			return "z";
		} else if (key.equals("@")) {
			return "%";
		} else if (key.equals("(")) {
			return "$";
		} else if (key.equals(":")) {
			return "&";
		} else if (key.equals("]")) {
			return "#";
		} else if (key.equals("-")) {
			return "_";
		} else if (key.equals("<")) {
			return "{";
		} else if (key.equals(">")) {
			return "}";
		} else if (key.equals("+")) {
			return "=";
		} else if (key.equals(")")) {
			return ",";
		} else if (key.equals(";")) {
			return "'";
		} else if (key.equals(".")) {
			return "\"";
		} else if (key.equals("!")) {
			return "[";
		} else if (key.equals("?")) {
			return "*";
		} else if (key.equals("/")) {
			return "\\";
		} else if (key.equals("%")) {
			return "@";
		} else if (key.equals("$")) {
			return "(";
		} else if (key.equals("&")) {
			return ":";
		} else if (key.equals("#")) {
			return "]";
		} else if (key.equals("_")) {
			return "-";
		} else if (key.equals("{")) {
			return "<";
		} else if (key.equals("}")) {
			return ">";
		} else if (key.equals("=")) {
			return "+";
		} else if (key.equals(",")) {
			return ")";
		} else if (key.equals("'")) {
			return ";";
		} else if (key.equals("\"")) {
			return ".";
		} else if (key.equals("[")) {
			return "!";
		} else if (key.equals("*")) {
			return "?";
		} else if (key.equals("\\")) {
			return "/";
		} else if (key.equals("\u00E7")) {
			return "\u00C7";
		} else if (key.equals("\u00C7")) {
			return "\u00E7";
		} else if (key.equals("\u00A3")) {
			return "\u00A5";
		} else if (key.equals("\u00A5")) {
			return "\u00A3";
		} else if (key.equals("\u00A2")) {
			return "\u00B1";
		} else if (key.equals("|")) {
			return "\u00B2";
		} else if (key.equals("\u00A7")) {
			return "\u00B3";
		} else if (key.equals("\u00A9")) {
			return "\u00B5";
		} else if (key.equals("\u00AA")) {
			return "\u00BA";
		} else if (key.equals("\u00AE")) {
			return "\u00BF";
		} else if (key.equals("\u00B1")) {
			return "\u00A2";
		} else if (key.equals("\u00B2")) {
			return "|";
		} else if (key.equals("\u00B3")) {
			return "\u00A7";
		} else if (key.equals("\u00B5")) {
			return "\u00A9";
		} else if (key.equals("\u00BA")) {
			return "\u00AA";
		} else if (key.equals("\u00BF")) {
			return "\u00AE";
		} else if (key.equals("\u00B4")) {
			return "\u0060";
		} else if (key.equals("\u007E")) {
			return "\u005E";
		} else if (key.equals("\u0060")) {
			return "\u00B4";
		} else if (key.equals("\u005E")) {
			return "\u007E";
		} else if (key.equals("\\o/")) {
			return ":^";
		} else if (key.equals(";)")) {
			return ":*";
		} else if (key.equals(":)")) {
			return ":))";
		} else if (key.equals(":(")) {
			return "8)";
		} else if (key.equals(":P")) {
			return ":0";
		} else if (key.equals(":D")) {
			return ":/";
		} else if (key.equals(":^")) {
			return "\\o/";
		} else if (key.equals(":*")) {
			return ";)";
		} else if (key.equals(":))")) {
			return ":)";
		} else if (key.equals("8)")) {
			return ":(";
		} else if (key.equals(":0")) {
			return ":P";
		} else if (key.equals(":/")) {
			return ":D";
		}

		return null;
	}

	void buildKeyboard(int mode) {
		String map[][] = null;

		shiftPressed = false;
		shiftButton = null;

		if (mode == QWERTY_MODE) {
			map = QWERTY_MAP;
		} else if (mode == PHONE_MODE) {
			map = PHONE_MAP;
		} else if (mode == NUMERIC_MODE) {
			map = NUMERIC_MAP;
		} else if (mode == SYMBOL_MODE) {
			map = SYMBOL_MAP;
		} else if (mode == NUMERIC_SYMBOL_MODE) {
			map = NUMERIC_SYMBOL_MAP;
		} else if (mode == INTERNET_MODE) {
			map = INTERNET_MAP;
		}

		setLayout(new BorderLayout());

		removeAll();
		
		if (this.display != null) {
			addComponent(BorderLayout.NORTH, display);
		}

		Container grid = new Container();

		grid.setLayout(new GridLayout(map.length, 1));
		grid.setVisible(true);
		grid.setPreferredSize(new Dimension(100,100));

		Container lines[] = new Container[map.length];

		for (int i=0; i<map.length; i++) {
			lines[i] = new Container();
		
			lines[i].setVisible(true);
			lines[i].setPreferredSize(new Dimension(100,100));
		}

		keys = new ArrayList();

		KeyButton focus = null;

		for (int i=0; i<lines.length; i++) {
			lines[i].setLayout(new FlowLayout());

			Component first = null,
								last = null;

			java.util.List list = new java.util.ArrayList();

			for (int j=0; j<map[i].length; j++) {
				KeyButton button = createButton(map[i][j], "uiid");

				lines[i].addComponent(button);
				list.add(button);

				if (focus == null || button.getName().equalsIgnoreCase("mode")) {
					focus = button;
				}

				if (first == null) {
					first = button;
				}

				last = button;
			}

			keys.add(list);

			first.setNextFocusLeft(last);
			last.setNextFocusRight(first);

			grid.addComponent(lines[i]);
		}

		addComponent(BorderLayout.CENTER, grid);

		if (focus != null) {
			focus.requestFocus();
		}

		doLayout();
		repaint();
	}

	void shiftPressed() {
		this.switchKeys();

		if (shiftButton != null) {
			if (shiftPressed == true) {
				shiftButton.setStyle(style3);
			} else {
				shiftButton.setStyle(style2);
			}
		}
	}

	private KeyButton createButton(String name, String uiid) {
		KeyButton button = null;

		if (name.length() > 2 && name.startsWith("$") && name.endsWith("$")) {
			String label = name.substring(1, name.length()-1);

			button = new KeyButton(80, 80);

			button.setName(label);
			button.setLabel(label);
			button.setStyle(style2);

			if (label.equalsIgnoreCase("mode")) {
				button.setLabel(currentMode);
			} else if (label.equalsIgnoreCase("shift")) {
				shiftButton = button;
			}

			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					VirtualKeyboard keyboard = VirtualKeyboard.this;
					KeyButton button = (KeyButton)event.getSource();
					String name = button.getName();

					if (name.equalsIgnoreCase("mode")) {
						if (currentMode.equalsIgnoreCase("ABC")) {
							if (keyboard.mode == SYMBOL_MODE) {
								currentMode = "SYM";
							} else {
								currentMode = "123";
							}
							
							keyboard.buildKeyboard(keyboard.QWERTY_MODE);
						} else if (currentMode.equalsIgnoreCase("123")) {
							currentMode = "ABC";

							keyboard.buildKeyboard(keyboard.NUMERIC_SYMBOL_MODE);
						} else if (currentMode.equalsIgnoreCase("SYM")) {
							currentMode = "ABC";

							keyboard.buildKeyboard(keyboard.SYMBOL_MODE);
						}
					} else if (name.equalsIgnoreCase("shift")) {
						if (shiftPressed == true) {
							shiftPressed = false;
						} else {
							shiftPressed = true;
						}
						
						keyboard.shiftPressed();
					} else if (name.equalsIgnoreCase("space")) {
						keyboard.getInternalField().internalAppendText(" ");
					} else if (name.equalsIgnoreCase("enter")) {
						keyboard.getInternalField().internalAppendText("\n");
					} else if (name.equalsIgnoreCase("delete")) {
						keyboard.getInternalField().deleteChar();
					} else {
						keyboard.getInternalField().internalAppendText(name);
					}
				}
			});
		} else {
			button = new KeyButton(40, 80);

			button.setName(name);
			button.setLabel(name);
			button.setStyle(style1);

			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					VirtualKeyboard keyboard = VirtualKeyboard.this;
					KeyButton button = (KeyButton)event.getSource();

					keyboard.getInternalField().internalAppendText("" + button.getLabel());

					if (shiftPressed == true) {
						shiftPressed = false;

						keyboard.shiftPressed();
					}
				}
			});
		}

		return button;
	}

	public String getText() {
		if (this.display == null) {
			return "";
		}

		return display.getText();
	}

	private InputField getInternalField() {
		return this.display;
	}

	public void show() {
		super.show();
		
		// INFO:: setting navigation up/down to closest button
		for (int i=0; i<keys.size(); i++) {
			java.util.List list = (java.util.List)keys.get(i);

			for (int j=0; j<list.size(); j++) {
				KeyButton button = (KeyButton)list.get(j);

				button.setNextFocusUp(button.closestButton((java.util.List)keys.get((i+keys.size()-1)%keys.size())));
				button.setNextFocusDown(button.closestButton((java.util.List)keys.get((i+keys.size()+1)%keys.size())));
			}
		}
	}

	public void keyPressed(int keyCode) {
		if (focused != null) {
			if(focused.isEnabled())
			{
				focused.keyPressed(keyCode);
			}
		
			if (keyCode == RemoteControlEvent.VK_LEFT ||
					keyCode == RemoteControlEvent.VK_RIGHT ||
					keyCode == RemoteControlEvent.VK_UP ||
					keyCode == RemoteControlEvent.VK_DOWN) {
				this.updateFocus(keyCode);
			} else if (keyCode == RemoteControlEvent.VK_BACK || 
					keyCode == RemoteControlEvent.VK_ESCAPE) {
				dispose();
			}
		}
	}

	class KeyButton extends Button {

		private String name;
		private int minimumWidth;
		private int minimumHeight;

		public KeyButton(int width, int height) {
			minimumWidth = width;
			minimumHeight = height;

			setEndsWith3Points(false);
			setAlignment(ViewOnlyComponent.HORIZONTAL_ALIGN_CENTER);
		}

		public Dimension getPreferredSize() {
			return new Dimension(minimumWidth, minimumHeight);
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getLabel() {
			return super.getText();
		}

		public void setLabel(String label) {
			super.setText(label);
		}

		public KeyButton closestButton(java.util.List list) {
			KeyButton button = null;
			int best = 9999;

			for (Iterator i=list.iterator(); i.hasNext(); ) {
				KeyButton next = (KeyButton)i.next();

				int d = Math.abs(getX()-next.getX());

				if (d < best) {
					best = d;

					button = next;
				}
			}

			return button;
		}

	}

	class InputField extends TextArea {
	
		private TextArea field;
		private Style style;

		InputField(TextArea field) {
			this.field = field;

			if (field != null) {
				int cursorPosition = field.getInternalCursorPosition();

				setConstraint(field.getConstraint());
				setMaxSize(field.getMaxSize());
				setText(field.getText());
				setRows(field.getRows());
				setInternalCursorPosition(cursorPosition);
			}

			Font textFont = getStyle().getFont();

			if (getHeight() < (textFont.getHeight() + getRowsGap()) * getLines()) {
				int dh = getHeight() - (getStyle().getPadding(Component.TOP) + getStyle().getPadding(Component.BOTTOM)) + getRowsGap();

				scrollRectToVisible(new Rectangle(0, (textFont.getHeight() + getRowsGap()) * getLines() - dh, getWidth(), getHeight()), this);
			}
		}

		public boolean hasFocus() {
			return true;
		}

		public String getUIID() {
			return "VKBTextInput";
		}

		public void deleteChar() {
			if(field != null) {
				if (field instanceof TextField) {
					// field.setInternalCursorPosition(getInternalCursorPosition());
				}
			}

			super.deleteChar();
			
			if (field != null) {
				field.deleteChar();
			}
		}

		public void internalAppendText(String t) {
			if(field != null) {
				if (field instanceof TextField) {
					// field.setInternalCursorPosition(getInternalCursorPosition());
				}
			}

			char []array = t.toCharArray();

			for (int i=0; i<array.length; i++) {
				internalKeyPressed(array[i]);
				field.internalKeyPressed(array[i]);
			}
			
			Font textFont = getStyle().getFont();

			if (getHeight() < (textFont.getHeight() + getRowsGap()) * getLines()) {
				int dh = getHeight() - (getStyle().getPadding(Component.TOP) + getStyle().getPadding(Component.BOTTOM));

				scrollRectToVisible(new Rectangle(0, (textFont.getHeight() + getRowsGap()) * getLines() - dh, getWidth(), getHeight()), this);
			}
		}

		public Dimension getPreferredSize() {
			return new Dimension(120, 120);
		}

	}

}

