package com.sun.dtv.lwuit.impl;

public class FontImpl extends com.sun.dtv.lwuit.Font {

	public FontImpl(java.awt.Font font) {
		super(font);
	}

	public int getHeight() {
		java.awt.FontMetrics fm = java.awt.Toolkit.getDefaultToolkit().getFontMetrics(awtFont);
		return fm.getHeight() + 1;
	}

	public int charWidth(char ch) {
		return stringWidth("" + (char)ch);
	}

	public void drawChar(com.sun.dtv.lwuit.Graphics g, char character, int x, int y) {
		if (g != null) {
			g.drawString("" + character, x, y);
		}
	}
}

