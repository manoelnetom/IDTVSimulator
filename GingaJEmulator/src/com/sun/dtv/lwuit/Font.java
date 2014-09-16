package com.sun.dtv.lwuit;

import java.util.Map;

public abstract class Font extends Object
{
	// Constant allowing us to author portable system fonts.
	public static final int FACE_SYSTEM = 0;
	// Constant allowing us to author portable system fonts.
	public static final int FACE_MONOSPACE = 32;
	// Constant allowing us to author portable system fonts.
	public static final int FACE_PROPORTIONAL = 64;

	// Constant allowing us to author portable system fonts.
	public static final int SIZE_SMALL = 16;
	// Constant allowing us to author portable system fonts.
	public static final int SIZE_MEDIUM = 20;
	// Constant allowing us to author portable system fonts.
	public static final int SIZE_LARGE = 24;

	// Constant allowing us to author portable system fonts.
	public static final int STYLE_PLAIN = 0;
	// Constant allowing us to author portable system fonts.
	public static final int STYLE_BOLD = 1;
	// Constant allowing us to author portable system fonts.
	public static final int STYLE_ITALIC = 2;
	// Constant allowing us to author portable system fonts.
	public static final int STYLE_UNDERLINED = 4;

	private static Font defaultFont;

	protected java.awt.Font awtFont;
	protected int face;
	protected String charset;

	/**
	 * Constructs a Font object.
	 * 
	 */
	protected Font()
	{
		this("Tiresias", STYLE_PLAIN, SIZE_MEDIUM);
	}

	/**
	 * Constructs a Font object.
	 * 
	 * 
	 * @param name - the font's name
	 * @param style - the font's style
	 * @param size - the font's size
	 */
	public Font(String name, int style, int size)
	{
		awtFont = new java.awt.Font(name, style, size);
	}

	/**
	 * Create a LWUIT Font object using an AWT Font instance to be wrapped.
	 * 
	 * 
	 * @param f - an AWT Font instance to be wrapped
	 */
	public Font(java.awt.Font font)
	{
		awtFont = font;
	}

	/**
	 * Returns a previously loaded bitmap font from cache.
	 * 
	 * 
	 * @param fontName - the font name is the logical name of the font
	 * @return a Font object
	 * @see clearBitmapCache()
	 */
	public static Font getBitmapFont(String fontName)
	{
		System.err.println("com.sun.dtv.lwuit.Font.getBitmapFont(): method not implemented");

		return null;
	}

	/**
	 * Bitmap fonts are cached. This method allows to flush the cache thus
	 * allows to reload a font.
	 * 
	 * 
	 */
	public static void clearBitmapCache()
	{
		System.err.println("com.sun.dtv.lwuit.Font.clearBitmapFont(): method not implemented");
	}

	/**
	 * Increase the contrast of the bitmap font for rendering on top of a
	 * surface whose color is darker. This is useful when drawing anti-aliased
	 * bitmap fonts using a light color (e.g. white) on top of a dark surface
	 * (e.g. black), the font often breaks down if its contrast is not increased
	 * due to the way alpha blending appears to the eye. Notice that this method
	 * only works in one way, contrast cannot be decreased properly in a font
	 * and it should be cleared and reloaded with a Look and Feel switch.
	 * 
	 * 
	 * @param value
	 *            - the value to increase
	 */
	public void addContrast(byte value)
	{
		System.err.println("com.sun.dtv.lwuit.Font.addContrast(): method not implemented");
	}

	/**
	 * Creates a bitmap font with the given arguments and places said font in
	 * the cache.
	 * 
	 * 
	 * @param name - the name for the font in the cache
	 * @param bitmap - a transparency map in red and black that indicates the
	 *            characters
	 * @param cutOffsets - character offsets matching the bitmap pixels and characters
	 *            in the font
	 * @param charWidth - The width of the character when drawing... this should not
	 *            be confused with the number of cutOffset[o + 1] -
	 *            cutOffset[o]. They are completely different since a character
	 *            can be "wider" and "seep" into the next region. This is
	 *            especially true with italic characters all of which "lean"
	 *            outside of their bounds.
	 * @param charsets - the set of characters in the font
	 * @return a font object to draw bitmap fonts
	 */
	public static Font createBitmapFont(String name, Image bitmap,
			int[] cutOffsets, int[] charWidth, String charsets)
	{
		return createBitmapFont(bitmap, cutOffsets, charWidth, charsets);
	}

	/**
	 * Creates a bitmap font with the given arguments.
	 * 
	 * 
	 * @param bitmap - a transparency map in red and black that indicates the
	 *            characters
	 * @param cutOffsets - character offsets matching the bitmap pixels and characters
	 *            in the font
	 * @param charWidth - The width of the character when drawing... this should not
	 *            be confused with the number of cutOffset[o + 1] -
	 *            cutOffset[o]. They are completely different since a character
	 *            can be "wider" and "seep" into the next region. This is
	 *            especially true with italic characters all of which "lean"
	 *            outside of their bounds.
	 * @param charsets - the set of characters in the font
	 * @return a font object to draw bitmap fonts
	 */
	public static Font createBitmapFont(Image bitmap, int[] cutOffsets,
			int[] charWidth, String charsets)
	{
		return new CustomFont(bitmap, cutOffsets, charWidth, charsets);
	}

	/**
	 * Creates a system native font in a similar way to common MIDP fonts.
	 * 
	 * 
	 * @param face - One of FACE_SYSTEM, FACE_PROPORTIONAL, FACE_MONOSPACE
	 * @param style - one of STYLE_PLAIN, STYLE_ITALIC, STYLE_BOLD
	 * @param size - One of SIZE_SMALL, SIZE_MEDIUM, SIZE_LARGE
	 * @return A newly created system font instance
	 */
	public static Font createSystemFont(int face, int style, int size) {
		return new com.sun.dtv.lwuit.impl.FontImpl(new java.awt.Font("Tiresias", style, size));
	}

	/**
	 * Return the width of the given characters in this font instance.
	 * 
	 * 
	 * @param ch - array of characters
	 * @param offset - characters offsets
	 * @param length - characters length
	 * @return the width of the given characters in this font instance
	 */
	public int charsWidth(char[] ch, int offset, int length)
	{
		return stringWidth(new String(ch, offset, length));
	}

	/**
	 * Return the width of the given string subset in this font instance.
	 * 
	 * 
	 * @param str - the given string
	 * @param offset - the string offset
	 * @param len - the len od string
	 * @return the width of the given string subset in this font instance
	 */
	public int substringWidth(String str, int offset, int len)
	{
		return stringWidth(str.substring(offset, len));
	}

	/**
	 * Return the width of the given string in this font instance.
	 * 
	 * 
	 * @param str - the given string *
	 * @return the width of the given string in this font instance
	 */
	public int stringWidth(String str)
	{
		java.awt.FontMetrics fm = java.awt.Toolkit.getDefaultToolkit().getFontMetrics(awtFont);

		return fm.stringWidth(str);
	}

	/**
	 * Return the width of the specific character when rendered alone.
	 * 
	 * 
	 * @param ch - the specific character
	 * @return the width of the specific character when rendered alone
	 */
	public abstract int charWidth(char ch);

	/**
	 * Return the total height of the font.
	 * 
	 * 
	 * @return the total height of the font
	 */
	public abstract int getHeight();

	/**
	 * Draw the given char using the current font and color in the x,y
	 * coordinates.
	 * 
	 * 
	 * @param g - the graphics object
	 * @param character - the given character
	 * @param x - the x coordinate to draw the char
	 * @param y - the y coordinate to draw the char
	 */
	public abstract void drawChar(Graphics g, char character, int x, int y);

	/**
	 * Return the global default font instance.
	 * 
	 * 
	 * @return the global default font instance
	 * @see setDefaultFont(com.sun.dtv.lwuit.Font)
	 */
	public static Font getDefaultFont()
	{
		if (defaultFont == null) {
			defaultFont = new com.sun.dtv.lwuit.impl.FontImpl(
					new java.awt.Font("tiresias", java.awt.Font.PLAIN,
							Font.SIZE_MEDIUM));
		}

		return defaultFont;
	}

	/**
	 * Sets the global default font instance.
	 * 
	 * 
	 * @param f - the global default font instance
	 * @see getDefaultFont()
	 */
	public static void setDefaultFont(Font f)
	{
		defaultFont = f;
	}

	/**
	 * Return Optional operation returning the font face for system fonts.
	 * 
	 * 
	 * @return Optional operation returning the font face for system fonts
	 */
	public int getFace()
	{
		return face;
	}

	/**
	 * Return Optional operation returning the font size for system fonts.
	 * 
	 * 
	 * @return Optional operation returning the font size for system fonts
	 */
	public int getSize()
	{
		return awtFont.getSize();
	}

	/**
	 * Return Optional operation returning the font style for system fonts.
	 * 
	 * 
	 * @return Optional operation returning the font style for system fonts
	 */
	public int getStyle()
	{
		return awtFont.getStyle();
	}

	/**
	 * Returns a string containing all the characters supported by this font.
	 * Will return null for system fonts.
	 * 
	 * 
	 * @return String containing the characters supported by a bitmap font or
	 *         null otherwise.
	 */
	public String getCharset()
	{
		return charset;
	}

	/**
	 * Obtains the <code>java.awt.Font</code> object wrapped by this <A* HREF="../../../../com/sun/dtv/lwuit/Font.html" * title="class in com.sun.dtv.lwuit"><CODE>Font</CODE></A> instance.
	 * 
	 * 
	 * @return the wrapped java.awt.Font object
	 */
	public java.awt.Font getAWTFont()
	{
		return awtFont;
	}

	/**
	 * Returns the <A HREF="../../../../com/sun/dtv/lwuit/Font.html"
	 * title="class in com.sun.dtv.lwuit"><CODE>Font</CODE></A> described by the
	 * argument.
	 * 
	 * IMPLEMENTATION NOTE: Delegate to the wrapped AWT font object.</p>
	 * 
	 * @param str
	 *            - the description
	 * @return the Font to be found
	 */
	public static Font decode(String str)
	{
		return new com.sun.dtv.lwuit.impl.FontImpl(java.awt.Font.decode(str));
	}

	/**
	 * Compares this <A HREF="../../../../com/sun/dtv/lwuit/Font.html"
	 * title="class in com.sun.dtv.lwuit"><CODE>Font</CODE></A> object to the
	 * specified.
	 * 
	 * IMPLEMENTATION NOTE: Delegate to the wrapped AWT font object.</p>
	 * 
	 * @param obj
	 *            - the object to compare with.
	 * @return true if equals, false otherwise.
	 * @see equals in class Object
	 */
	public boolean equals(Object obj)
	{
		Font o = (Font) obj;

		return awtFont.equals(o.getAWTFont());
	}

	/**
	 * Returns a map of attributes available in this <A
	 * HREF="../../../../com/sun/dtv/lwuit/Font.html"
	 * title="class in com.sun.dtv.lwuit"><CODE>Font</CODE></A>.
	 * 
	 * IMPLEMENTATION NOTE: Delegate to the wrapped AWT font object.</p>
	 * 
	 * @return the attribute map
	 */
	public Map getAttributes()
	{
		return awtFont.getAttributes();
	}

	/**
	 * Returns the family name of this <A
	 * HREF="../../../../com/sun/dtv/lwuit/Font.html"
	 * title="class in com.sun.dtv.lwuit"><CODE>Font</CODE></A>.
	 * 
	 * IMPLEMENTATION NOTE: Delegate to the wrapped AWT font object.</p>
	 * 
	 * @return the family name
	 */
	public String getFamily()
	{
		return awtFont.getFamily();
	}

	/**
	 * Returns a <A HREF="../../../../com/sun/dtv/lwuit/Font.html"
	 * title="class in com.sun.dtv.lwuit"><CODE>Font</CODE></A> appropriate to
	 * this attribute set.
	 * 
	 * IMPLEMENTATION NOTE: Delegate to the wrapped AWT font object.</p>
	 * 
	 * @param attributes
	 *            - the attribute set
	 * @return the appropriate Font
	 */
	public static Font getFont(Map attributes)
	{
		return new com.sun.dtv.lwuit.impl.FontImpl(java.awt.Font.getFont(attributes));
	}

	/**
	 * Returns a <A HREF="../../../../com/sun/dtv/lwuit/Font.html"
	 * title="class in com.sun.dtv.lwuit"><CODE>Font</CODE></A> object from the
	 * system properties list.
	 * 
	 * IMPLEMENTATION NOTE: Delegate to the wrapped AWT font object.</p>
	 * 
	 * @param nm
	 *            - the property name
	 * @return the appropriate Font
	 */
	public static Font getFont(String nm)
	{
		return new com.sun.dtv.lwuit.impl.FontImpl(java.awt.Font.getFont(nm));
	}

	/**
	 * Gets the specified <A HREF="../../../../com/sun/dtv/lwuit/Font.html"
	 * title="class in com.sun.dtv.lwuit"><CODE>Font</CODE></A> from the system.
	 * 
	 * IMPLEMENTATION NOTE: Delegate to the wrapped AWT font object.</p>
	 * 
	 * @param nm
	 *            - the property name
	 * @param font
	 *            - a default Font to be returned if the property name does not
	 *            exist
	 * @return the Font found
	 */
	public static Font getFont(String nm, Font font)
	{
		return new com.sun.dtv.lwuit.impl.FontImpl(java.awt.Font.getFont(nm,
				font.getAWTFont()));
	}

	/**
	 * Returns the logical name of this <A
	 * HREF="../../../../com/sun/dtv/lwuit/Font.html"
	 * title="class in com.sun.dtv.lwuit"><CODE>Font</CODE></A>.
	 * 
	 * IMPLEMENTATION NOTE: Delegate to the wrapped AWT font object.</p>
	 * 
	 * @return the logical name
	 */
	public String getName()
	{
		return awtFont.getName();
	}

	/**
	 * Returns a hash code for this <A
	 * HREF="../../../../com/sun/dtv/lwuit/Font.html"
	 * title="class in com.sun.dtv.lwuit"><CODE>Font</CODE></A>.
	 * 
	 * IMPLEMENTATION NOTE: Delegate to the wrapped AWT font object.</p>
	 * 
	 * @return the hashcode
	 * @see hashCode in class Object
	 */
	public int hashCode()
	{
		return awtFont.hashCode();
	}

	/**
	 * Indicates whether this <A HREF="../../../../com/sun/dtv/lwuit/Font.html"
	 * title="class in com.sun.dtv.lwuit"><CODE>Font</CODE></A> is BOLD.
	 * 
	 * IMPLEMENTATION NOTE: Delegate to the wrapped AWT font object.</p>
	 * 
	 * @return true if Font is BOLD, false otherwise
	 */
	public boolean isBold()
	{
		return awtFont.isBold();
	}

	/**
	 * Indicates whether this <A HREF="../../../../com/sun/dtv/lwuit/Font.html"
	 * title="class in com.sun.dtv.lwuit"><CODE>Font</CODE></A> is ITALIC.
	 * 
	 * IMPLEMENTATION NOTE: Delegate to the wrapped AWT font object.</p>
	 * 
	 * @return true if Font is ITALIC, false otherwise
	 */
	public boolean isItalic()
	{
		return awtFont.isItalic();
	}

	/**
	 * Indicates whether this <A HREF="../../../../com/sun/dtv/lwuit/Font.html"
	 * title="class in com.sun.dtv.lwuit"><CODE>Font</CODE></A> is PLAIN.
	 * 
	 * IMPLEMENTATION NOTE: Delegate to the wrapped AWT font object.</p>
	 * 
	 * @return true if Font is PLAIN, false otherwise
	 */
	public boolean isPlain()
	{
		return awtFont.isPlain();
	}

	/**
	 * Converts this <A HREF="../../../../com/sun/dtv/lwuit/Font.html"
	 * title="class in com.sun.dtv.lwuit"><CODE>Font</CODE></A> object into a
	 * String.
	 * 
	 * IMPLEMENTATION NOTE: Delegate to the wrapped AWT font object.</p>
	 * 
	 * @return a String representation of this Font
	 * @see toString in class Object
	 */
	public String toString()
	{
		return awtFont.toString();
	}

}
