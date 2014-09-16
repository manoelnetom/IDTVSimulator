package com.sun.dtv.lwuit.plaf;

import java.awt.Color;

import com.sun.dtv.lwuit.Component;
import com.sun.dtv.lwuit.Font;
import com.sun.dtv.lwuit.Image;
import com.sun.dtv.lwuit.Painter;
import com.sun.dtv.lwuit.events.StyleListener;
import java.util.Vector;
import java.util.Enumeration;

public class Style extends Object
{
	private static final short FG_COLOR_MODIFIED = 1;
	private static final short BG_COLOR_MODIFIED = 2;
	private static final short FG_SELECTION_MODIFIED = 4;
	private static final short BG_SELECTION_MODIFIED = 8;
	private static final short FONT_MODIFIED = 16;
	private static final short BG_IMAGE_MODIFIED = 32;
	private static final short SCALE_IMAGE_MODIFIED = 64;
	private static final short TRANSPARENCY_MODIFIED = 128;
	private static final short PADDING_MODIFIED = 256;
	private static final short MARGIN_MODIFIED = 512;
	private static final short BORDER_MODIFIED = 1024;

	/**
	 * Background color attribute name for the theme hashtable.
	 *
	 */
	public static final String BG_COLOR = "bgColor" ;

	/**
	 * Foreground color attribute name for the theme hashtable.
	 *
	 */
	public static final String FG_COLOR  = "fgColor";

	/**
	 * Background image attribute name for the theme hashtable.
	 * 
	 */
	public static final String BG_IMAGE = "bgImage";
	
	/**
	 * Background selection color attribute name for the theme hashtable.
	 *
	 */
	public static final String BG_SELECTION_COLOR = "bgSelectionColor";

	/**
	 * Foreground color attribute name for the theme hashtable.
	 * 
	 */
	public static final String FG_SELECTION_COLOR = "fgSelectionColor";

	/**
	 * Font attribute name for the theme hashtable.
	 * 
	 */
	public static final String FONT = "font";

	/**
	 * Scaled image attribute name for the theme hashtable.
	 *
	 */
	public static final String SCALED_IMAGE = "scaledImage";

	/**
	 * Transparency attribute name for the theme hashtable.
	 *
	 */
	public static final String TRANSPARENCY = "transparency";

	/**
	 * Margin attribute name for the theme hashtable.
	 * 
	 */
	public static final String MARGIN = "margin";

	/**
	 * Border attribute name for the theme hashtable.
	 * 
	 */
	public static final String BORDER = "border";

	/**
	 * Padding attribute name for the theme hashtable.
	 *
	 * 
	 */
	public static final String PADDING = "padding";

	private Color fgColor;
	private Color bgColor;
	private Color bgSelectionColor;
	private Color fgSelectionColor;
	private Font font;
	private Image bgImage;
	private Border border;
	private boolean scaleImage;
	private Painter bgPainter;
	private int[] padding;
	private int[] margin;
	private byte transparency = (byte) 0x7F; //no transparency ==> Int Value: 127
	private short modifiedFlag;
	private Vector listeners;

	/**
	 * Each component when it draw itself uses this Object
	 * to determine in what colors it should use.
	 * When a Component is generated it construct a default Style Object.
	 * The Default values for each Component can be changed by using the UIManager class
	 *
	 * 
	 */
	public Style()
	{
		fgColor = new Color(255, 255, 255, 255);
		bgColor = new Color(91, 89, 87, 255);
		bgSelectionColor = new Color(201, 229, 13, 255);
		fgSelectionColor = new Color(0, 0, 0, 255);
		font = Font.getDefaultFont();
		bgImage = null;
		border = new Border();
		scaleImage = false;
		bgPainter = null;
		padding = new int[4];
		margin = new int[4];
		modifiedFlag = 0;
		transparency = (byte) 0x7F;

		setPadding(3, 3, 3, 3);
		setMargin(2, 2, 2, 2);
	}

	/**
	 * Creates a full copy of the given style. Notice that if the original
	 * style was modified manually (by invoking setters on it) it would not
	 * change when changing a theme/look and feel,
	 * however this newly created style would change in such a case.
	 *
	 * 
	 * @param style - the style to be copied
	 */
	public Style( Style style)
	{
		this(style.getFgColor(), style.getBgColor(), style.getFgSelectionColor(), style.getBgSelectionColor(), style.getFont(), style.getBgTransparency(), style.getBgImage(), style.isScaleImage());
		
		setPadding(style.getPadding(Component.TOP), style.getPadding(Component.BOTTOM), style.getPadding(Component.LEFT), style.getPadding(Component.RIGHT));		
		setMargin(style.getMargin(Component.TOP), style.getMargin(Component.BOTTOM), style.getMargin(Component.LEFT), style.getMargin(Component.RIGHT));		
		setBorder(style.getBorder());
		
		modifiedFlag = 0;
	}

	/**
	 * Creates a new style with the given attributes.
	 *
	 * 
	 * @param fgColor - foreground color
	 * @param bgColor - background color
	 * @param fgSelectionColor - foreground selection color
	 * @param bgSelectionColor - background selection color
	 * @param f - font
	 * @param transparency - transparency level
	 */
	public Style( Color fgColor, Color bgColor, Color fgSelectionColor, Color bgSelectionColor, Font f, byte transparency)
	{
		this(fgColor, bgColor, fgSelectionColor, bgSelectionColor, f, transparency, null, false);
	}

	/**
	 * Creates a new style with the given attributes.
	 *
	 * 
	 * @param fgColor - foreground color
	 * @param bgColor - background color
	 * @param fgSelectionColor - foreground selection color
	 * @param bgSelectionColor - background selection color
	 * @param f - font
	 * @param transparency - transparency level
	 * @param im - underlying image
	 * @param scaledImage - indicates whether image is scaled
	 */
	public Style( Color fgColor, Color bgColor, Color fgSelectionColor, Color bgSelectionColor, Font f, byte transparency, Image im, boolean scaledImage)
	{
		this();

		this.fgColor = fgColor;
		this.bgColor = bgColor;
		this.font = f;
		this.bgSelectionColor = bgSelectionColor;
		this.fgSelectionColor = fgSelectionColor;
		this.scaleImage = scaledImage;
		this.bgImage = im;
		this.transparency = transparency;
	}

	/**
	 * Merges the new style with the current style without changing the elements
	 * that were modified.
	 *
	 * 
	 * @param style - new values of styles from the current theme
	 */
	public void merge( Style style)
	{
		short tmp = modifiedFlag;

		if ((modifiedFlag & FG_COLOR_MODIFIED) == 0) {
			setFgColor(style.getFgColor());
		}

		if ((modifiedFlag & BG_COLOR_MODIFIED) == 0) {
			setBgColor(style.getBgColor());
		}

		if ((modifiedFlag & BG_IMAGE_MODIFIED) == 0) {
			setBgImage(style.getBgImage());
		}

		if ((modifiedFlag & FONT_MODIFIED) == 0) {
			setFont(style.getFont());
		}

		if ((modifiedFlag & BG_SELECTION_MODIFIED) == 0) {
			setBgSelectionColor(style.getBgSelectionColor());
		}

		if ((modifiedFlag & FG_SELECTION_MODIFIED) == 0) {
			setFgSelectionColor(style.getFgSelectionColor());
		}

		if ((modifiedFlag & SCALE_IMAGE_MODIFIED) == 0) {
			setScaleImage(style.isScaleImage());
		}

		if ((modifiedFlag & TRANSPARENCY_MODIFIED) == 0) {
			setBgTransparency(style.getBgTransparency());
		}

		if ((modifiedFlag & PADDING_MODIFIED) == 0) {
			setPadding(style.getPadding(Component.TOP),
					style.getPadding(Component.BOTTOM),
					style.getPadding(Component.LEFT),
					style.getPadding(Component.RIGHT));
		}

		if ((modifiedFlag & MARGIN_MODIFIED) == 0) {
			setMargin(style.getMargin(Component.TOP),
					style.getMargin(Component.BOTTOM),
					style.getMargin(Component.LEFT),
					style.getMargin(Component.RIGHT));
		}

		if ((modifiedFlag & BORDER_MODIFIED) == 0) {
			setBorder(style.getBorder());
		}

		modifiedFlag = tmp;
	}

	/**
	 * Returns true if the style was modified manually after it was created by the
	 * look and feel. If the style was modified manually (by one of the set methods)
	 * then it should be merged rather than overwritten.
	 *
	 * 
	 * 
	 * @return true if modified, false otherwise
	 */
	public boolean isModified()
	{
		return modifiedFlag != 0;
	}

	/**
	 * Returns the background color for the component.
	 *
	 * 
	 * 
	 * @return the background color for the component
	 * @see setBgColor(java.awt.Color)
	 */
	public Color getBgColor()
	{
		return this.bgColor;
	}

	/**
	 * Returns the background image for the component.
	 *
	 * 
	 * 
	 * @return the background image for the component
	 * @see setBgImage(com.sun.dtv.lwuit.Image)
	 */
	public Image getBgImage()
	{
		return this.bgImage;
	}

	/**
	 * Returns the foreground color for the component.
	 *
	 * 
	 * 
	 * @return the foreground color for the component
	 * @see setFgColor(java.awt.Color)
	 */
	public Color getFgColor()
	{
		return this.fgColor;
	}

	/**
	 * Returns the font for the component.
	 *
	 * 
	 * 
	 * @return the font for the component
	 * @see setFont(com.sun.dtv.lwuit.Font)
	 */
	public Font getFont()
	{
		return this.font;
	}

	/**
	 * Sets the background color for the component.
	 *
	 * 
	 * @param bgColor - RRGGBB color that ignores the alpha component
	 * @see getBgColor()
	 */
	public void setBgColor(Color bgColor)
	{
		this.bgColor = bgColor;
	}

	/**
	 * Sets the background image for the component.
	 *
	 * 
	 * @param bgImage - the image
	 * @see getBgImage()
	 */
	public void setBgImage(Image bgImage)
	{
		this.bgImage = bgImage;
	}

	/**
	 * Sets the foreground color for the component.
	 *
	 * 
	 * @param fgColor - the color
	 * @see getFgColor()
	 */
	public void setFgColor(Color fgColor)
	{
		this.fgColor = fgColor;
	}

	/**
	 * Sets the font for the component.
	 *
	 * 
	 * @param font - the font
	 * @see getFont()
	 */
	public void setFont(Font font)
	{
		this.font = font;
	}

	/**
	 * Returns the background selection color for the component.
	 *
	 * 
	 * 
	 * @return the background selection color for the component
	 * @see setBgSelectionColor(java.awt.Color)
	 */
	public Color getBgSelectionColor()
	{
		return this.bgSelectionColor;
	}

	/**
	 * Sets the background selection color for the component.
	 *
	 * 
	 * @param bgSelectionColor - the color
	 * @see getBgSelectionColor()
	 */
	public void setBgSelectionColor( Color bgSelectionColor)
	{
		this.bgSelectionColor = bgSelectionColor;
	}

	/**
	 * Returns the foreground selection color for the component.
	 *
	 * 
	 * 
	 * @return the foreground selection color for the component
	 * @see setFgSelectionColor(java.awt.Color)
	 */
	public Color getFgSelectionColor()
	{
		return this.fgSelectionColor;
	}

	/**
	 * Sets the foreground selection color for the component.
	 *
	 * 
	 * @param fgSelectionColor - the color
	 * @see getFgSelectionColor()
	 */
	public void setFgSelectionColor( Color fgSelectionColor)
	{
		this.fgSelectionColor = fgSelectionColor;
	}

	/**
	 * Indicates whether the image in the background is scaled.
	 *
	 * 
	 * 
	 * @return true if the image in the background is scaled, false if it is tiled
	 * @see setScaleImage(boolean)
	 */
	public boolean isScaleImage()
	{
		return scaleImage;
	}

	/**
	 * Set to true if the image in the background is scaled, false if it is tiled.
	 *
	 * 
	 * @param scaleImage - value indicating whether the image in the background is  scaled
	 * @see isScaleImage()
	 */
	public void setScaleImage(boolean scaleImage)
	{
		this.scaleImage = scaleImage;
	}

	/**
	 * Returns the transparency level of the Component.
	 *
	 * 
	 * 
	 * @return the transparency level of the Component
	 * @see setBgTransparency(byte)
	 */
	public byte getBgTransparency()
	{
		/*
		if(bgImage != null && backgroundType <= BACKGROUND_IMAGE_TILE_BOTH && (bgImage.isAnimation() || bgImage.isOpaque())) {
            return (byte)0xff;
        }
        */
        return transparency;
	}

	/**
	 * Sets the Component transparency level.
	 *
	 * 
	 * @param transparency - transparency level as byte
	 * @see getBgTransparency()
	 */
	public void setBgTransparency(byte transparency)
	{
		setBgTransparency(transparency & 0xFF, false);
	}

	/**
	 * Sets the Component transparency level. Valid values should be a
	 * number between 0-255.
	 *
	 * 
	 * @param transparency - int value between 0-255
	 * @see getBgTransparency()
	 */
	public void setBgTransparency(int transparency)
	{
		 setBgTransparency(transparency, false);
	}

	/**
	 * Sets the Style Padding.
	 *
	 * 
	 * @param top - number of pixels to pad
	 * @param bottom - number of pixels to pad
	 * @param left - number of pixels to pad
	 * @param right - number of pixels to pad
	 * @see getPadding(int)
	 */
	public void setPadding(int top, int bottom, int left, int right)
	{
		if (top < 0 || left < 0 || right < 0 || bottom < 0) {
			throw new IllegalArgumentException("padding cannot be negative");
		}

		padding[Component.TOP] = top;
		padding[Component.BOTTOM] = bottom;
		padding[Component.LEFT] = left;
		padding[Component.RIGHT] = right;
		modifiedFlag |= PADDING_MODIFIED;
		
		firePropertyChanged(PADDING);
	}

	/**
	 * Sets the Style Padding.
	 *
	 * 
	 * @param orientation - one of: Component.TOP, Component.BOTTOM, Component.LEFT, Component.RIGHT
	 * @param gap - number of pixels to pad
	 * @see getPadding(int)
	 */
	public void setPadding(int orientation, int gap)
	{
		if (orientation != Component.LEFT && orientation != Component.RIGHT 
			&& orientation != Component.BOTTOM && orientation != Component.TOP) 
		{
			throw new IllegalArgumentException("Orientation can't be set to " + orientation);
		}
		 setPadding(orientation, gap, false);
	}

	/**
	 * Sets the Style Margin.
	 *
	 * 
	 * @param top - number of margin pixels
	 * @param bottom - number of margin pixels
	 * @param left - number of margin pixels
	 * @param right - number of margin pixels
	 * @see getMargin(int)
	 */
	public void setMargin(int top, int bottom, int left, int right)
	{
		if (top < 0 || left < 0 || right < 0 || bottom < 0) {
			throw new IllegalArgumentException("margin cannot be negative");
		}

		margin[Component.TOP] = top;
		margin[Component.BOTTOM] = bottom;
		margin[Component.LEFT] = left;
		margin[Component.RIGHT] = right;
		modifiedFlag |= MARGIN_MODIFIED;
		
		firePropertyChanged(MARGIN);
	}

	/**
	 * Sets the Style Margin.
	 *
	 * 
	 * @param orientation - one of: Component.TOP, Component.BOTTOM, Component.LEFT, Component.RIGHT
	 * @param gap - number of margin pixels
	 * @see getMargin(int)
	 */
	public void setMargin(int orientation, int gap)
	{
		if (orientation < Component.TOP || orientation > Component.RIGHT) 
		{
			throw new IllegalArgumentException("wrong orientation " + orientation);
		}
		setMargin(orientation, gap, false);
	}

	/**
	 * Returns the Padding.
	 *
	 * 
	 * @param orientation - one of: Component.TOP, Component.BOTTOM, Component.LEFT, Component.RIGHT
	 * @return number of padding pixels in the given orientation
	 * @see setPadding(int, int)
	 */
	public int getPadding(int orientation)
	{
		if (orientation < Component.TOP || orientation > Component.RIGHT) {
			throw new IllegalArgumentException("wrong orientation " + orientation);
		}

		return padding[orientation];
	}

	/**
	 * Returns the Margin.
	 *
	 * 
	 * @param orientation - one of: Component.TOP, Component.BOTTOM, Component.LEFT, Component.RIGHT
	 * @return number of margin pixels in the given orientation
	 * @see setMargin(int, int)
	 */
	public int getMargin(int orientation)
	{
		if (orientation < Component.TOP || orientation > Component.RIGHT) {
			throw new IllegalArgumentException("wrong orientation " + orientation);
		}

		return margin[orientation];
	}

	/**
	 * Sets the background color for the component.
	 *
	 * 
	 * @param bgColor - RRGGBB color that ignores the alpha component
	 * @param override - If set to true allows the look and feel/theme to override  the value in this attribute when changing a theme/look and feel
	 * @see getBgColor()
	 */
	public void setBgColor( Color bgColor, boolean override)
	{
		this.bgColor = bgColor;

		if(!override){
			modifiedFlag |= BG_COLOR_MODIFIED;
		}

		firePropertyChanged(BG_COLOR);
	}

	/**
	 * Sets the background image for the component.
	 *
	 * 
	 * @param bgImage - the image
	 * @param override - If set to true allows the look and feel/theme to override  the value in this attribute when changing a theme/look and feel
	 * @see getBgImage()
	 */
	public void setBgImage( Image bgImage, boolean override)
	{
		this.bgImage = bgImage;

		if(!override){
			modifiedFlag |= BG_IMAGE_MODIFIED;
		}

		firePropertyChanged(BG_IMAGE);
	}

	/**
	 * Sets the foreground color for the component.
	 *
	 * 
	 * @param fgColor - the color
	 * @param override - If set to true allows the look and feel/theme to override  the value in this attribute when changing a theme/look and feel
	 * @see getFgColor()
	 */
	public void setFgColor( Color fgColor, boolean override)
	{
		this.fgColor = fgColor;

		if(!override){
			modifiedFlag |= FG_COLOR_MODIFIED;
		}

		firePropertyChanged(FG_COLOR);
	}

	/**
	 * Sets the font for the component.
	 *
	 * 
	 * @param font - the font
	 * @param override - If set to true allows the look and feel/theme to override  the value in this attribute when changing a theme/look and feel
	 * @see getFont()
	 */
	public void setFont( Font font, boolean override)
	{
		this.font = font;

		if(!override){
			modifiedFlag |= FONT_MODIFIED;
		}

		firePropertyChanged(FONT);
	}

	/**
	 * Sets the background selection color for the component.
	 *
	 * 
	 * @param bgSelectionColor - the color
	 * @param override - If set to true allows the look and feel/theme to override  the value in this attribute when changing a theme/look and feel
	 * @see getBgSelectionColor()
	 */
	public void setBgSelectionColor( Color bgSelectionColor, boolean override)
	{
		this.bgSelectionColor = bgSelectionColor;

		if(!override){
			modifiedFlag |= BG_SELECTION_MODIFIED;
		}

		firePropertyChanged(BG_SELECTION_COLOR);
	}

	/**
	 * Sets the foreground selection color for the component.
	 *
	 * 
	 * @param fgSelectionColor - the color
	 * @param override - If set to true allows the look and feel/theme to override  the value in this attribute when changing a theme/look and feel
	 * @see getFgSelectionColor()
	 */
	public void setFgSelectionColor( Color fgSelectionColor, boolean override)
	{
		this.fgSelectionColor = fgSelectionColor;

		if(!override){
			modifiedFlag |= FG_SELECTION_MODIFIED;
		}

		firePropertyChanged(FG_SELECTION_COLOR);
	}

	/**
	 * Set to true if the image in the background is scaled, false if it is tiled.
	 *
	 * 
	 * @param scaleImage - value indicating whether the image in the background is scaled
	 * @param override - If set to true allows the look and feel/theme to override  the value in this attribute when changing a theme/look and feel
	 * @see isScaleImage()
	 */
	public void setScaleImage(boolean scaleImage, boolean override)
	{
		this.scaleImage = scaleImage;

		if(!override){
			modifiedFlag |= SCALE_IMAGE_MODIFIED;
		}

		firePropertyChanged(SCALED_IMAGE);
	}

	/**
	 * Sets the Component transparency level. Valid values should be a
	 * number between 0-255.
	 *
	 * 
	 * @param transparency - the transparency level
	 * @param override - If set to true allows the look and feel/theme to override  
	 * 					the value in this attribute when changing a theme/look and feel
	 * @see getBgTransparency()
	 */
	public void setBgTransparency(int transparency, boolean override)
	{
		/*if (transparency < 0 || transparency > 255) 
		{
			throw new IllegalArgumentException("valid values are between 0-255");
		}

		setBgTransparency(transparency);

		if(!override)
		{
			modifiedFlag |= TRANSPARENCY_MODIFIED;
		}

		firePropertyChanged(TRANSPARENCY);*/
		
		if (transparency < 0 || transparency > 255) {
            throw new IllegalArgumentException("valid values are between 0-255");
        }
		
        if (this.transparency != (byte) transparency) {
            this.transparency = (byte) (transparency-128);

            if (!override) {
                modifiedFlag |= TRANSPARENCY_MODIFIED;
            }
            firePropertyChanged(TRANSPARENCY);
        }
	}

	/**
	 * Sets the Style Padding.
	 *
	 * 
	 * @param orientation - one of: Component.TOP, Component.BOTTOM, Component.LEFT, Component.RIGHT
	 * @param gap - number of pixels to pad
	 * @param override - If set to true allows the look and feel/theme to override  the value in this attribute when changing a theme/look and feel
	 * @see getPadding(int)
	 */
	public void setPadding(int orientation, int gap, boolean override)
	{
		if (orientation < Component.TOP || orientation > Component.RIGHT) {
			throw new IllegalArgumentException("wrong orientation " + orientation);
		}

		if (gap < 0) {
			throw new IllegalArgumentException("padding cannot be negative");
		}

		padding[orientation] = gap;

		if(!override){
			modifiedFlag |= PADDING_MODIFIED;
		}

		firePropertyChanged(PADDING);
	}

	/**
	 * Sets the Style Margin.
	 *
	 * 
	 * @param orientation - one of: Component.TOP, Component.BOTTOM, Component.LEFT, Component.RIGHT
	 * @param gap - number of margin pixels
	 * @param override - If set to true allows the look and feel/theme to override  the value in this attribute when changing a theme/look and feel
	 * @see getMargin(int)
	 */
	public void setMargin(int orientation, int gap, boolean override)
	{
		if (orientation < Component.TOP || orientation > Component.RIGHT) {
			throw new IllegalArgumentException("wrong orientation " + orientation);
		}

		if (gap < 0) {
			throw new IllegalArgumentException("margin cannot be negative");
		}

		margin[orientation] = gap;
		
		if(!override){
			modifiedFlag |= MARGIN_MODIFIED;
		}

		firePropertyChanged(MARGIN);
	}

	private void firePropertyChanged(String propertName) {
		if (listeners == null) {
			return;
		}

		Enumeration enums = listeners.elements();

		while (enums.hasMoreElements()) {
			StyleListener l = (StyleListener) enums.nextElement();

			l.styleChanged(propertName, this);
		}
	}

	/**
	 * Adds a Style Listener to the Style Object.
	 *
	 * 
	 * @param l - a style listener
	 * @see removeStyleListener(com.sun.dtv.lwuit.events.StyleListener)
	 */
	public void addStyleListener( StyleListener l)
	{
		if (listeners == null) {
			listeners = new Vector();
		}

		listeners.addElement(l);
	}

	/**
	 * Removes a Style Listener from the Style Object.
	 *
	 * 
	 * @param l - a style listener
	 * @see addStyleListener(com.sun.dtv.lwuit.events.StyleListener)
	 */
	public void removeStyleListener( StyleListener l)
	{
		if (listeners != null) {
			listeners.removeElement(l);
		}
	}

	/**
	 * Sets the border for the style.
	 *
	 * 
	 * @param border - new border object for the component
	 * @see getBorder()
	 */
	public void setBorder( Border border)
	{
		this.border = border;
		modifiedFlag |= BORDER_MODIFIED;
		
		firePropertyChanged(BORDER);
	}

	/**
	 * Sets the border for the style.
	 *
	 * 
	 * @param border - new border object for the component
	 * @param override - If set to true allows the look and feel/theme to override  the value in this attribute when changing a theme/look and feel
	 * @see getBorder()
	 */
	public void setBorder( Border border, boolean override)
	{
		this.border = border;
		
		if(!override){
			modifiedFlag |= BORDER_MODIFIED;
		}
		
		firePropertyChanged(BORDER);
	}

	/**
	 * Returns the border for the style.
	 *
	 * 
	 * 
	 * @return the border
	 * @see setBorder(com.sun.dtv.lwuit.plaf.Border)
	 */
	public Border getBorder()
	{
		return this.border;
	}

	/**
	 * Return the background painter for this style, normally this would be
	 * the internal image/color painter but can be user defined.
	 *
	 * 
	 * 
	 * @return the painter
	 * @see setBgPainter(com.sun.dtv.lwuit.Painter)
	 */
	public Painter getBgPainter()
	{
		return this.bgPainter;
	}

	/**
	 * Defines the background painter for this style, normally this would be
	 * the internal image/color painter but can be user defined.
	 *
	 * 
	 * @param bgPainter - new painter to install into the style
	 * @see getBgPainter()
	 */
	public void setBgPainter( Painter bgPainter)
	{
		this.bgPainter = bgPainter;
	}

	void resetModifiedFlag() {
		modifiedFlag = 0;
	}

}
