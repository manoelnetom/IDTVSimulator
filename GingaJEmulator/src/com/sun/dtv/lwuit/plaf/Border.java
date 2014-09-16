package com.sun.dtv.lwuit.plaf;

import java.awt.Color;

import com.sun.dtv.lwuit.Component;
import com.sun.dtv.lwuit.Font;
import com.sun.dtv.lwuit.Graphics;
import com.sun.dtv.lwuit.Image;
import com.sun.dtv.lwuit.geom.Rectangle;
					
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;

public class Border extends Object
{
	private static Border defaultBorder = Border.createEtchedRaised(0x020202, 0xBBBBBB);

	private static final int TYPE_EMPTY = 0;
	private static final int TYPE_LINE = 1;
	private static final int TYPE_ROUNDED = 2;
	private static final int TYPE_ROUNDED_PRESSED = 3;
	private static final int TYPE_ETCHED_LOWERED = 4;
	private static final int TYPE_ETCHED_RAISED = 5;
	private static final int TYPE_BEVEL_RAISED = 6;
	private static final int TYPE_BEVEL_LOWERED = 7;
	private static final int TYPE_IMAGE = 8;
	//Lwuit 
	private static final int TYPE_COMPOUND = 9;
    private static final int TYPE_IMAGE_HORIZONTAL = 10;
    private static final int TYPE_IMAGE_VERTICAL = 11;
    private static final int TYPE_DASHED = 12;
    private static final int TYPE_DOTTED = 13;
    private static final int TYPE_DOUBLE = 14;
    private static final int TYPE_GROOVE = 15;
    private static final int TYPE_RIDGE = 16;
    private static final int TYPE_INSET = 17;
    private static final int TYPE_OUTSET = 18;
	static Border empty;

	/**
	 * Indicates whether theme colors should be used or whether colors are specified
	 * in the border
	 *
	 */
	boolean themeColors;

	Image[] images;
	int type;

	Color colorA;
	Color colorB;
	Color colorC;
	Color colorD;
	int thickness;
	int arcWidth;
	int arcHeight;
	Border pressedBorder;
	Border focusedBorder;
	Border [] compoundBorders;
	String borderTitle; // border title, currently supported only for line borders
    private static final int TITLE_MARGIN = 10;
    private static final int TITLE_SPACE = 5;
    boolean outline = true;
    Border outerBorder; // A border added outside of this border (Used for CSS outline property, but can also be used for other purposes)
    
	/**
	 * Constructor 
	 * 
	 */
	public Border()
	{
		colorA = new Color(0xf0, 0xf0, 0xf0, 0xff);
		colorB = new Color(0xf0, 0x00, 0x00, 0xff);
		colorC = new Color(0x00, 0xf0, 0x00, 0xff);
		colorD = new Color(0x00, 0x00, 0xf0, 0xff);
		type = TYPE_LINE;
		thickness = 2;
		arcWidth = 10;
		arcHeight = 10;
	}
	public int getThickness()
	{
		return this.thickness;
	}

	/**
	 * Returns an empty border, this is mostly useful for overriding components that
	 * have a border by default.
	 *
	 * 
	 * 
	 * @return a border than draws nothing
	 */
	public static Border getEmpty()
	{
		if(empty == null) {
			empty = new Border();
		}

		return empty;
	}

	/**
	 * Creates an empty border, this is useful where we don't want a border for a
	 * component but want a focus border etc...
	 *
	 * 
	 * 
	 * @return a border than draws nothing
	 */
	public static Border createEmpty()
	{
		Border b = new Border();
		b.type = TYPE_EMPTY;
		return b;
	}

	/**
	 * The given images are tiled appropriately across the matching side of the
	 * border and placed as expected in the four corners. The background image
	 * is optional and it will be tiled in the background if necessary.
	 *By default this border does not override background unless a background
	 * image is specified.
	 *
	 * 
	 * @param top - the Image for the top side
	 * @param bottom - the Image for the bottom side
	 * @param left - the Image for the left side
	 * @param right - the Image for the right side
	 * @param topLeft - the Image for the top left corner
	 * @param topRight - the Image for the top right corner
	 * @param bottomLeft - the Image for the bottom left corner
	 * @param bottomRight - the Image for the bottom right corner
	 * @param background - the Image for the background
	 * @return new border instance
	 */
	public static Border createImageBorder( Image top, Image bottom, Image left, Image right, Image topLeft, Image topRight, Image bottomLeft, Image bottomRight, Image background)
	{
		Border b = new Border();
		b.type = TYPE_IMAGE;
		b.images = new Image[] {top, bottom, left, right, topLeft, topRight, bottomLeft, bottomRight, background};
		return b;
	}

	/**
	 * The given images are tiled appropriately across the matching side of the
	 * border, rotated and placed as expected in the four corners. The background
	 * image is optional and it will be tiled in the background if necessary.
	 *By default this border does not override background unless a background
	 * image is specified.
	 *Notice that this version of the method is potentially much more
	 * efficient since images are rotated internally and this might save quite a
	 * bit of memory!
	 *<b>The top and topLeft images must be square!</b> The width and height
	 * of these images must be equal otherwise rotation won't work as you expect.
	 *
	 * 
	 * @param top - the Image for the top side
	 * @param topLeft - the Image for the top left corner
	 * @param background - the Image for the background
	 * @return new border instance
	 */
	public static Border createImageBorder( Image top, Image topLeft, Image background)
	{
		Border b = new Border();
		b.type = TYPE_IMAGE;
		b.images = new Image[] {top, top.rotate(180), top.rotate(270), top.rotate(90), topLeft, topLeft.rotate(90), topLeft.rotate(270), topLeft.rotate(180), background};
		return b;
	}
	 /**
     * This is an image border that can only grow horizontally
     *
     * @param left the image of the left side
     * @param right the image of the right side
     * @param center the image of the center
     * @return new border instance
     */
    public static Border createHorizonalImageBorder(Image left, Image right, Image center) {
        Border b = new Border();
        b.type = TYPE_IMAGE_HORIZONTAL;
        b.images = new Image[] {left, right, center};
        return b;
    }
    /**
     * This is an image border that can only grow vertically
     *
     * @param top the image of the top
     * @param bottom the image of the bottom
     * @param center the image of the center
     * @return new border instance
     */
    public static Border createVerticalImageBorder(Image top, Image bottom, Image center) {
        Border b = new Border();
        b.type = TYPE_IMAGE_VERTICAL;
        b.images = new Image[] {top, bottom, center};
        return b;
    }

	/**
	 * Creates a line border that uses the color of the component foreground for
	 * drawing.
	 *
	 * 
	 * @param thickness - thickness of the border in pixels
	 * @param themeColors - Indicates whether theme colors should be used or whether colors are specified
	 * in the border
	 * @return new border instance
	 */
	public static Border createLineBorder(int thickness)
	{
		Border b = new Border();
		b.type = TYPE_LINE;
		b.themeColors = false;
		b.thickness = thickness; 
		return b;
	}

	/**
	 * Creates a line border that uses the given color for the component.
	 *
	 * 
	 * @param thickness - thickness of the border in pixels
	 * @param color - the color for the border
	 * @return new border instance
	 */
	public static Border createLineBorder(int thickness, Color color)
	{
		Border b = new Border();
		b.type = TYPE_LINE;
		b.themeColors = false;
		b.thickness = thickness;
		b.colorA = color;
		return b;
	}
	 /**
     * Creates a dotted border with the specified thickness and color
     *
     * @param thickness The border thickness in pixels
     * @param color The border color
     * @return The border
     */
    public static Border createDottedBorder(int thickness,Color color) {
        return createCSSBorder(TYPE_DOTTED, thickness, color);
    }
    /**
     * Creates a dashed border with the specified thickness and color
     *
     * @param thickness The border thickness in pixels
     * @param color The border color
     * @return The border
     */
    public static Border createDashedBorder(int thickness,Color color) {
        return createCSSBorder(TYPE_DASHED, thickness, color);
    }
    /**
     * Creates a double border with the specified thickness and color
     *
     * @param thickness The border thickness in pixels
     * @param color The border color
     * @return The border
     */
    public static Border createDoubleBorder(int thickness, Color color) {
        return createCSSBorder(TYPE_DOUBLE, thickness, color);
    }

    /**
     * Creates a dotted border with the specified thickness and the theme colors
     *
     * @param thickness The border thickness in pixels
     * @param themeColors - Indicates whether theme colors should be used or whether colors are specified in the border
     * @return The border
     */
    public static Border createDottedBorder(int thickness) {
        return createCSSBorder(TYPE_DOTTED, thickness);
    }

    /**
     * Creates a dashed border with the specified thickness and the theme colors
     *
     * @param thickness The border thickness in pixels
     * @param themeColors - Indicates whether theme colors should be used or whether colors are specified in the border
     * @return The border
     */
    public static Border createDashedBorder(int thickness) {
        return createCSSBorder(TYPE_DASHED, thickness);
    }

    /**
     * Creates a double border with the specified thickness and color
     *
     * @param thickness The border thickness in pixels
     * @param themeColors - Indicates whether theme colors should be used or whether colors are specified in the border
     * @return The border
     */
    public static Border createDoubleBorder(int thickness) {
        return createCSSBorder(TYPE_DOUBLE, thickness);
    }
    /**
     * Creates an outset border with the specified thickness and theme colors
     * 
     * @param thickness The border thickness in pixels
     * @param themeColors - Indicates whether theme colors should be used or whether colors are specified in the border
     * @return The border
     */
    public static Border createOutsetBorder(int thickness) {
        return createCSSBorder(TYPE_OUTSET, thickness);
    }

    /**
     * Creates an outset border with the specified thickness and color
     *
     * @param thickness The border thickness in pixels
     * @param color The border color
     * @param themeColors - Indicates whether theme colors should be used or whether colors are specified in the border
     * @return The border
     */
    public static Border createOutsetBorder(int thickness,Color color) {
        return createCSSBorder(TYPE_OUTSET, thickness,color);
    }

    /**
     * Creates an inset border with the specified thickness and theme colors
     *
     * @param thickness The border thickness in pixels
     * @param themeColors - Indicates whether theme colors should be used or whether colors are specified in the border
     * @return The border
     */
    public static Border createInsetBorder(int thickness) {
        return createCSSBorder(TYPE_INSET, thickness);
    }

    /**
     * Creates an inset border with the specified thickness and color
     *
     * @param thickness The border thickness in pixels
     * @param color The border color
     * @param themeColors - Indicates whether theme colors should be used or whether colors are specified in the border
     * @return The border
     */
    public static Border createInsetBorder(int thickness,Color color) {
        return createCSSBorder(TYPE_INSET, thickness,color);
    }

    /**
     * Creates a groove border with the specified thickness and theme colors
     *
     * @param thickness The border thickness in pixels
     * @param themeColors - Indicates whether theme colors should be used or whether colors are specified in the border
     * @return The border
     */
    public static Border createGrooveBorder(int thickness) {
        return createCSSBorder(TYPE_GROOVE, thickness);
    }

    /**
     * Creates a groove border with the specified thickness and color
     *
     * @param thickness The border thickness in pixels
     * @param color The border color
     * @return The border
     */
    public static Border createGrooveBorder(int thickness,Color color) {
        return createCSSBorder(TYPE_GROOVE, thickness,color);
    }

    /**
     * Creates a ridge border with the specified thickness and theme colors
     *
     * @param thickness The border thickness in pixels
     * @param themeColors - Indicates whether theme colors should be used or whether colors are specified in the border
     * @return The border
     */
    public static Border createRidgeBorder(int thickness) {
        return createCSSBorder(TYPE_RIDGE, thickness);
    }

    /**
     * Creates a ridge border with the specified thickness and color
     *
     * @param thickness The border thickness in pixels
     * @param color The border color
     * @return The border
     */
    public static Border createRidgeBorder(int thickness, Color color) {
        return createCSSBorder(TYPE_RIDGE, thickness,color);
    }

    private static Border createCSSBorder(int type,int thickness) {
        Border b = new Border();
        b.type = type;
        b.themeColors = false;
        b.thickness = thickness;
        b.colorA = new Color(0xf0, 0xf0, 0xf0, 0xff);
        return b;
    }

    private static Border createCSSBorder(int type,int thickness,Color color) {
        Border b = new Border();
        b.type = type;
        b.colorA = color;
        b.thickness = thickness;
        return b;
    }
	/**
	 * Creates a rounded corner border that uses the color of the component
	 * foreground for drawing. Due to technical issues (lack of shaped clipping)
	 * performance and memory overhead of round borders can be low if used with
	 * either a bgImage or translucency!
	 *
	 * This border overrides any painter used on the component and would ignore
	 * such a painter.
	 *
	 * 
	 * @param arcWidth - the horizontal diameter of the arc at the four corners.
	 * @param arcHeight - the vertical diameter of the arc at the four corners.
	 * @return new border instance
	 */
	public static Border createRoundBorder(int arcWidth, int arcHeight)
	{
		Border b = new Border();
		b.type = TYPE_ROUNDED;
		b.themeColors = true;
		b.arcHeight = arcHeight;
		b.arcWidth = arcWidth;
		return b;
	}

	/**
	 * Creates a rounded border that uses the given color for the component.
	 * Due to technical issues (lack of shaped clipping) performance and memory
	 * overhead of round borders can be low if used with either a bgImage or
	 * translucency!
	 *
	 * This border overrides any painter used on the component and would ignore
	 * such a painter.
	 *
	 * 
	 * @param arcWidth - the horizontal diameter of the arc at the four corners.
	 * @param arcHeight - the vertical diameter of the arc at the four corners.
	 * @param color - the color for the border
	 * @return new border instance
	 */
	public static Border createRoundBorder(int arcWidth, int arcHeight, Color color)
	{
		Border b = new Border();
		b.type = TYPE_ROUNDED;
		b.themeColors = false;
		b.colorA = color;
		b.arcHeight = arcHeight;
		b.arcWidth = arcWidth;
		return b;
	}

	/**
	 * Creates a lowered etched border with default colors, highlight is derived
	 * from the component and shadow is a plain dark color.
	 *
	 * 
	 * 
	 * @return new border instance
	 */
	public static Border createEtchedLowered()
	{
		Border b = new Border();
		b.type = TYPE_ETCHED_LOWERED;
		b.themeColors = true;
		return b;
	}

	/**
	 * Creates a raised etched border with the given colors.
	 *
	 * 
	 * @param highlight - color RGB value
	 * @param shadow - color RGB value
	 * @return new border instance
	 */
	public static Border createEtchedLowered(int highlight, int shadow)
	{
		return createEtchedLowered(new Color(highlight), new Color(shadow));
	}
	/**
	 * Creates a raised etched border with the given colors.
	 *
	 * 
	 * @param highlight - color RGB value
	 * @param shadow - color RGB value
	 * @return new border instance
	 */
	public static Border createEtchedLowered(Color highlight, Color shadow)
	{
		Border b = new Border();
		b.type = TYPE_ETCHED_LOWERED;
		b.themeColors = false;
		b.colorA = shadow;
		b.colorB = highlight;
		return b;
	}

	/**
	 * Creates a lowered etched border with default colors, highlight is derived
	 * from the component and shadow is a plain dark color.
	 *
	 * 
	 * 
	 * @return new border instance
	 */
	public static Border createEtchedRaised()
	{
		Border b = new Border();
		b.type = TYPE_ETCHED_RAISED;
		b.themeColors = true;
		return b;
	}

	/**
	 * Creates a raised etched border with the given colors.
	 *
	 * 
	 * @param highlight - color RGB value
	 * @param shadow - color RGB value
	 * @return new border instance
	 */
	public static Border createEtchedRaised(int highlight, int shadow)
	{
		return createEtchedRaised(new Color(highlight),new Color(shadow));
	}
	/**
	 * Creates a raised etched border with the given colors.
	 *
	 * 
	 * @param highlight - color RGB value
	 * @param shadow - color RGB value
	 * @return new border instance
	 */
	public static Border createEtchedRaised(Color highlight, Color shadow)
	{
		Border b = new Border();
		b.type = TYPE_ETCHED_RAISED;
		b.themeColors = false;
		b.colorA = highlight;
		b.colorB = shadow;
		return b;
	}
	/**
	 * Returns true if installing this border will override the painting of the
	 * component background.
	 *
	 * 
	 * 
	 * @return true if it is a background painter, false otherwise
	 */
	public boolean isBackgroundPainter()
	{
		return type == TYPE_ROUNDED || type == TYPE_ROUNDED_PRESSED || (type == TYPE_IMAGE && images[8] != null);
	}

	/**
	 * Creates a lowered bevel border with default colors, highlight is derived
	 * from the component and shadow is a plain dark color.
	 *
	 * 
	 * 
	 * @return new border instance
	 */
	public static Border createBevelLowered()
	{
		Border b = new Border();
		b.type = TYPE_BEVEL_LOWERED;
		b.themeColors = true;
		return b;
	}

	/**
	 * Creates a raised bevel border with the given colors.
	 *
	 * 
	 * @param highlightOuter - RGB of the outer edge of the highlight area
	 * @param highlightInner - RGB of the inner edge of the highlight area
	 * @param shadowOuter - RGB of the outer edge of the shadow area
	 * @param shadowInner - RGB of the inner edge of the shadow area
	 * @return new border instance
	 */
	public static Border createBevelLowered(int highlightOuter, int highlightInner, int shadowOuter, int shadowInner)
	{
		return createBevelLowered(new Color(highlightOuter), new Color(highlightInner), new Color(shadowOuter), new Color(shadowInner));
		
	}
	public static Border createBevelLowered(Color highlightOuter, Color highlightInner, Color shadowOuter, Color shadowInner)
	{
		Border b = new Border();
		b.type = TYPE_BEVEL_LOWERED;
		b.themeColors = false;
		b.colorA = highlightOuter;
		b.colorB = highlightInner;
		b.colorC = shadowOuter;
		b.colorD = shadowInner;
		return b;
	}
	/**
	 * Creates a lowered bevel border with default colors, highlight is derived
	 * from the component and shadow is a plain dark color.
	 *
	 * 
	 * 
	 * @return new border instance
	 */
	public static Border createBevelRaised()
	{
		Border b = new Border();
		b.type = TYPE_BEVEL_RAISED;
		b.themeColors = true;
		return b;
	}

	/**
	 * Creates a raised bevel border with the given colors.
	 *
	 * 
	 * @param highlightOuter - RGB of the outer edge of the highlight area
	 * @param highlightInner - RGB of the inner edge of the highlight area
	 * @param shadowOuter - RGB of the outer edge of the shadow area
	 * @param shadowInner - RGB of the inner edge of the shadow area
	 * @return new border instance
	 */
	public static Border createBevelRaised(int highlightOuter, int highlightInner, int shadowOuter, int shadowInner)
	{
        return createBevelRaised(new Color(highlightOuter), new Color(highlightInner), new Color(shadowOuter), new Color(shadowInner));
	}
	public static Border createBevelRaised(Color highlightOuter, Color highlightInner, Color shadowOuter, Color shadowInner)
	{
		Border b = new Border();
        b.type = TYPE_BEVEL_RAISED;
        b.themeColors = false;
        b.colorA = highlightOuter;
        b.colorB = highlightInner;
        b.colorC = shadowOuter;
        b.colorD = shadowInner;
        return b;
	}
	public static Border createCompoundBorder(Border top, Border bottom, Border left, Border right) {

        if ((top != null && !top.isRectangleType()) || 
                (bottom != null && !bottom.isRectangleType()) ||
                (left != null && !left.isRectangleType()) ||
                (right != null && !right.isRectangleType())) {
            throw new IllegalArgumentException("Compound Border can be created "
                    + "only from Rectangle types Borders");
        }
        if ((isSame(top, bottom)) && (isSame(top, left)) && (isSame(top, right))) {
            return top; // Borders are all the same, returning one of them instead of creating a compound border which is more resource consuming
        }

        Border b = new Border();
        b.type = TYPE_COMPOUND;
        b.compoundBorders = new Border[4];
        b.compoundBorders[Component.TOP] = top;
        b.compoundBorders[Component.BOTTOM] = bottom;
        b.compoundBorders[Component.LEFT] = left;
        b.compoundBorders[Component.RIGHT] = right;

        // Calculates the thickness of the entire border as the maximum of all 4 sides
        b.thickness=0;
        for(int i=Component.TOP;i<=Component.RIGHT;i++) {
            if (b.compoundBorders[i]!=null) {
                int sideThickness=b.compoundBorders[i].thickness;
                if (sideThickness>b.thickness) {
                    b.thickness=sideThickness;
                }
            }
        }

        return b;
    }
	/**
	 * Allows us to define a border that will act as the pressed version of
	 * this border.
	 *
	 * 
	 * @param pressed - a border that will be returned by the pressed version method
	 * @see getPressedInstance()
	 */
	public void setPressedInstance(Border pressed)
	{
		this.pressedBorder = pressed;
	}

	/**
	 * Allows us to define a border that will act as the focused version of
	 * this border.
	 *
	 * 
	 * @param focused - a border that will be returned by the focused version method
	 * @see getFocusedInstance()
	 */
	public void setFocusedInstance(Border focused)
	{
		this.focusedBorder = focused;
	}

	/**
	 * Returns the focused version of the border if one is installed.
	 *
	 * 
	 * 
	 * @return the focused version of the border if one is installed
	 * @see setFocusedInstance(com.sun.dtv.lwuit.plaf.Border)
	 */
	public Border getFocusedInstance()
	{
		if(focusedBorder != null) {
			return focusedBorder;
		}
		return this;
	}

	/**
	 * Returns the pressed version of the border if one is set by the user.
	 *
	 * 
	 * 
	 * @return the pressed version of the border if one is set by the user
	 * @see setPressedInstance(com.sun.dtv.lwuit.plaf.Border)
	 */
	public Border getPressedInstance()
	{
		if(pressedBorder != null) {
			return pressedBorder;
		}
		return this;
	}

	/**
	 * When applied to buttons borders produce a version that reverses the effects
	 * of the border providing a pressed feel.
	 *
	 * 
	 * 
	 * @return a border that will make the button feel pressed
	 */
	public Border createPressedVersion()
	{
		 if(pressedBorder != null) {
	            return pressedBorder;
	        }
	        switch(type) {
	            case TYPE_LINE:
	                return createLineBorder(thickness + 1, colorA);
	            case TYPE_ETCHED_LOWERED: {
	                Border b = createEtchedRaised(colorA, colorB);
	                b.themeColors = themeColors;
	                return b;
	            }
	            case TYPE_ETCHED_RAISED: {
	                Border b = createEtchedLowered(colorA, colorB);
	                b.themeColors = themeColors;
	                return b;
	            }
	            case TYPE_BEVEL_RAISED: {
	                Border b = createBevelLowered(colorA, colorB, colorC, colorD);
	                b.themeColors = themeColors;
	                return b;
	            }
	            case TYPE_BEVEL_LOWERED: {
	                Border b = createBevelRaised(colorA, colorB, colorC, colorD);
	                b.themeColors = themeColors;
	                return b;
	            }
	            case TYPE_ROUNDED: {
	                Border b = createRoundBorder(arcWidth, arcHeight, colorA);
	                b.themeColors = themeColors;
	                b.type = TYPE_ROUNDED_PRESSED;
	                return b;
	            }
	            case TYPE_ROUNDED_PRESSED: {
	                Border b = createRoundBorder(arcWidth, arcHeight, colorA);
	                b.themeColors = themeColors;
	                return b;
	            }
	        }
	        return this;
	}

	/**
	 * Has effect when the border demands responsibility for background painting
	 * normally the painter will perform this work but in this case the border might
	 * do it instead.
	 *
	 * 
	 * @param g - graphics context to draw onto
	 * @param c - component whose border should be drawn
	 */
	public void paintBorderBackground( Graphics g, Component c)
	{
		Color originalColor = g.getColor();
		int x = c.getX();
		int y = c.getY();
		int width = c.getWidth();
		int height = c.getHeight();
		switch(type) {
			case TYPE_ROUNDED_PRESSED:
				x++;
				y++;
				width -= 2;
				height -= 2;
			case TYPE_ROUNDED:
				width--;
				height--;
				// rounded is also responsible for drawing the background
				Style s = c.getStyle();
				if(s.getBgImage() != null) {
					// we need to draw a background image!
					Image i = Image.createImage(width, height);
					Graphics imageG = i.getGraphics();
					imageG.setColor(new Color(0x00, 0x00, 0x00, 0xff));
					imageG.fillRoundRect(0, 0, width, height, arcWidth, arcHeight);
					int[] rgb = i.getRGB();
					int transColor = rgb[0];
					int[] imageRGB = s.getBgImage().scaled(width, height).getRGB();
					for(int iter = 0 ; iter < rgb.length ; iter++) {
						if(rgb[iter] == transColor) {
							imageRGB[iter] = 0;
						}
					}
					
					g.drawImage(new Image(Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(width, height, imageRGB, 0, width))), x, y);
				} else {
					Color foreground = g.getColor();
					if(c.hasFocus()) {
						g.setColor(s.getBgSelectionColor());
					} else {
						g.setColor(s.getBgColor());
					}

					// Its opaque much easier job!
					if(s.getBgTransparency() == ((byte)0xff)) {
						g.fillRoundRect(x, y , width, height, arcWidth, arcHeight);
					} else {
						/* TODO:: verificar se isso eh necessario
						if(g.isAlphaSupported()) {
							int alpha = g.getAlpha();
							g.setAlpha(s.getBgTransparency() & 0xff);
							g.fillRoundRect(x, y , width, height, arcWidth, arcHeight);
							g.setAlpha(alpha);
						} else {
						*/
							// if its transparent we don't need to do anything, if its
							// translucent... well....
							if(s.getBgTransparency() != 0) {
								Image i = Image.createImage(width, height);
								int[] imageRgb;
								if(g.getColor().getRGB() != 0xffffff) {
									Graphics imageG = i.getGraphics();
									imageG.setColor(g.getColor());
									imageG.fillRoundRect(0, 0 , width, height, arcWidth, arcHeight);
									imageRgb = i.getRGB();
								} else {
									// background color is white we need to remove a different color
									// black is the only other "reliable" color on the device
									Graphics imageG = i.getGraphics();
									imageG.setColor(new Color(0x00, 0x00, 0x00, 0xff));
									imageG.fillRect(0, 0, width, height);
									imageG.setColor(g.getColor());
									imageG.fillRoundRect(0, 0 , width, height, arcWidth, arcHeight);
									imageRgb = i.getRGB();
								}
								int removeColor = imageRgb[0];
								int size = width * height;
								int alphaInt = ((s.getBgTransparency() & 0xff) << 24) & 0xff000000;
								for(int iter = 0 ; iter < size ; iter++) {
									if(removeColor == imageRgb[iter]) {
										imageRgb[iter] = 0;
										continue;
									}
									if((imageRgb[iter] & 0xff000000) != 0) {
										imageRgb[iter] = (imageRgb[iter] & 0xffffff) | alphaInt;
									}   
								}
								g.drawImage(new Image(Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(width, height, imageRgb, 0, width))), x, y);
							}
						// }
					}

					g.setColor(foreground);
				}
				break;
			case TYPE_IMAGE:
				int clipX = g.getClipX();
				int clipY = g.getClipY();
				int clipWidth = g.getClipWidth();
				int clipHeight = g.getClipHeight();
				Image topLeft = images[4]; 
				Image topRight = images[5];
				Image bottomLeft = images[6];
				Image center = images[8];
				x += topLeft.getWidth();
				y += topLeft.getHeight();
				height -= (topLeft.getHeight() + bottomLeft.getHeight());
				width -= (topLeft.getWidth() + topRight.getWidth());
				g.clipRect(x, y, width, height);
				if(center != null){
					int centerWidth = center.getWidth();
					int centerHeight = center.getHeight();
					for(int xCount = x ; xCount < x + width ; xCount += centerWidth) {
						for(int yCount = y ; yCount < y + height ; yCount += centerHeight) {
							g.drawImage(center, xCount, yCount);
						}
					}
				}
				Image top = images[0];  Image bottom = images[1];
				Image left = images[2]; Image right = images[3];
				Image bottomRight = images[7];

				g.setClip(clipX, clipY, clipWidth, clipHeight);

				x = c.getX();
				y = c.getY();
				width = c.getWidth();
				height = c.getHeight();

				g.drawImage(topLeft, x, y);
				g.drawImage(bottomLeft, x, y + height - bottomLeft.getHeight());
				g.drawImage(topRight, x + width - topRight.getWidth(), y);
				g.drawImage(bottomRight, x + width - bottomRight.getWidth(), y + height - bottomRight.getHeight());

				g.setClip(clipX, clipY, clipWidth, clipHeight);
				drawImageBorderLine(g, topLeft, topRight, top, x, y, width);
				g.setClip(clipX, clipY, clipWidth, clipHeight);
				drawImageBorderLine(g, bottomLeft, bottomRight, bottom, x, y + height - bottom.getHeight(), width);
				g.setClip(clipX, clipY, clipWidth, clipHeight);
				drawImageBorderColumn(g, topLeft, bottomLeft, left, x, y, height);
				g.setClip(clipX, clipY, clipWidth, clipHeight);
				drawImageBorderColumn(g, topRight, bottomRight, right, x + width - left.getWidth(), y, height);

				g.setClip(clipX, clipY, clipWidth, clipHeight);
				break;
		}
		g.setColor(originalColor);
	}

	private void drawImageBorderLine(Graphics g, Image left, Image right, Image center, int x, int y, int width) {
		int currentWidth = width - right.getWidth();
		if(currentWidth > 0) {
			x += left.getWidth();
			int destX = x + currentWidth;
			g.clipRect(x, y, currentWidth - left.getWidth(), center.getHeight());
			int centerWidth = center.getWidth();
			for(; x < destX ; x += centerWidth) {
				g.drawImage(center, x, y);
			}
		}
	}

	private void drawImageBorderColumn(Graphics g, Image top, Image bottom, Image center, int x, int y, int height) {
		int currentHeight = height - bottom.getHeight();
		if(currentHeight > 0) {
			y += top.getHeight();
			int destY = y + currentHeight;
			g.clipRect(x, y, center.getWidth(), currentHeight - top.getHeight());
			int centerHeight = center.getHeight();
			for(; y < destY ; y += centerHeight) {
				g.drawImage(center, x, y);
			}
		}
	}
	/**
     * Draws the border for the given component, this method is called before a call to
     * background painting is made.
     * 
     * @param g graphics context to draw onto
     * @param c component whose border should be drawn
     */
    public void paint(Graphics g, Component c) {
        int x = c.getX();
        int y = c.getY();
        int width = c.getWidth();
        int height = c.getHeight();
        if (outerBorder!=null) {
            paint(g, x+thickness, y+thickness, width-thickness*2, height-thickness*2, c);
            outerBorder.paint(g, x, y, width, height, c);
        } else {
            paint(g, x, y, width, height, c);
        }
    }
    
	/**
	 * Draws the border for the given component, this method is called before a call to
	 * background painting is made.
	 *
	 * 
	 * @param g - graphics context to draw onto
	 * @param c - component whose border should be drawn
	 */
	public void paint( Graphics g, int x,int y,int width,int height,Component c)
	{

		int originalColor = g.getColor().getRGB();
        if(!themeColors) {
            g.setColor(colorA);
        } 
        switch(type) {
            case TYPE_LINE:
                if (borderTitle==null) {
                    width--;
                    height--;
                    for(int iter = 0 ; iter < thickness ; iter++) {
                        g.drawRect(x + iter, y + iter, width, height);
                        width -= 2; height -= 2;
                    }
                } else {
                    Font f=c.getStyle().getFont();
                    int titleW=f.stringWidth(borderTitle);
                    int topPad=c.getStyle().getPadding(Component.TOP);
                    int topY=y+(topPad-thickness)/2;
                    g.fillRect(x, topY, TITLE_MARGIN , thickness); //top (segment before the title)
                    g.fillRect(x+TITLE_MARGIN +titleW+TITLE_SPACE*2, topY, width-(TITLE_MARGIN +titleW+TITLE_SPACE*2), thickness); //top (segment after the title)
                    g.drawString(borderTitle, x+TITLE_MARGIN+TITLE_SPACE, y+(topPad-f.getHeight())/2);
                    

                    g.fillRect(x, y+height-thickness, width, thickness); //bottom
                    g.fillRect(x, topY, thickness, height); //left
                    g.fillRect(x+width-thickness, topY, thickness, height); //right
                    
                }
                break;
            case TYPE_DASHED:
            case TYPE_DOTTED:
                int segWidth=thickness;
                if (type==TYPE_DASHED) {
                    segWidth=thickness*3;
                }
                int ix=x;
                for (;ix<x+width;ix+=segWidth*2) {
                    g.fillRect(ix, y, segWidth, thickness);
                    g.fillRect(ix, y+height-thickness, segWidth, thickness);
                }
                if (ix-segWidth<x+width) { //fill in the gap if any
                    g.fillRect(ix-segWidth, y, x+width-ix+segWidth, thickness);
                    g.fillRect(ix-segWidth, y+height-thickness, x+width-ix+segWidth, thickness);
                }

                int iy=y;
                for (;iy<y+height;iy+=segWidth*2) {
                    g.fillRect(x, iy, thickness, segWidth);
                    g.fillRect(x+width-thickness, iy, thickness, segWidth);
                }
                if (iy-segWidth<y+height) { //fill in the gap if any
                    g.fillRect(x, iy-segWidth, thickness, y+height-iy+segWidth);
                    g.fillRect(x+width-thickness, iy-segWidth, thickness, y+height-iy+segWidth);
                }


                break;
            case TYPE_DOUBLE:
                    width--;
                    height--;
                    for(int iter = 0 ; iter < thickness ; iter++) 
                    {
                        if ((iter*100/thickness <= 33) || (iter*100/thickness >= 66)) 
                        {
                            g.drawRect(x + iter, y + iter, width, height);
                        }
                        width -= 2; height -= 2;
                   }
                    break;
            case TYPE_INSET:
            case TYPE_OUTSET:
                for(int i=0;i<thickness;i++) {
                    g.drawLine(x+i, y+i, x+i, y+height-i);
                    g.drawLine(x+i, y+i, x+width-i, y+i);
                }

                if (type==TYPE_INSET) {
                    g.lighterColor(50);
                } else {
                    g.darkerColor(50);
                }
                for(int i=0;i<thickness;i++) {
                    g.drawLine(x+i, y+height-i, x+width-i, y+height-i);
                    g.drawLine(x+width-i, y+i, x+width-i, y+height-i);
                }
                break;
            case TYPE_GROOVE:
            case TYPE_RIDGE:
                for(int i=0;i<thickness/2;i++) {
                    g.drawLine(x+i, y+i, x+i, y+height-i);
                    g.drawLine(x+i, y+i, x+width-i, y+i);
                }
                for(int i=thickness/2;i<thickness;i++) {
                    g.drawLine(x+i, y+height-i, x+width-i, y+height-i);
                    g.drawLine(x+width-i, y+i, x+width-i, y+height-i);
                }

                if (type==TYPE_GROOVE) {
                    g.lighterColor(50);
                } else {
                    g.darkerColor(50);
                }
                for(int i=0;i<thickness/2;i++) {
                    g.drawLine(x+i, y+height-i, x+width-i, y+height-i);
                    g.drawLine(x+width-i, y+i, x+width-i, y+height-i);
                }
                for(int i=thickness/2;i<thickness;i++) {
                    g.drawLine(x+i, y+i, x+i, y+height-i);
                    g.drawLine(x+i, y+i, x+width-i, y+i);
                }
                break;
            case TYPE_ROUNDED_PRESSED:
                x++;
                y++;
                width -= 2;
                height -= 2;
            case TYPE_ROUNDED:
                width--;
                height--;

                if(outline) {
                    g.drawRoundRect(x, y , width, height, arcWidth, arcHeight);
                }
                break;
            case TYPE_ETCHED_LOWERED:
            case TYPE_ETCHED_RAISED:
                g.drawRect(x, y, width - 2, height - 2);

                if(themeColors) {
                    if(type == TYPE_ETCHED_LOWERED) {
                        g.lighterColor(40);
                    } else {
                        g.darkerColor(40);
                    }
                } else {
                    g.setColor(colorB);
                }
                g.drawLine(x + 1, y + height - 3, x + 1, y +1);
                g.drawLine(x + 1, y + 1, x + width - 3, y + 1);

                g.drawLine(x, y + height - 1, x + width - 1, y + height - 1);
                g.drawLine(x + width - 1, y + height - 1, x + width - 1, y);
                break;
            case TYPE_BEVEL_RAISED:
                if(themeColors) {
                    g.setColor(getBackgroundColor(c));
                    g.lighterColor(50);
                } else {
                    g.setColor(colorA);
                }
                g.drawLine(x, y, x, y + height - 2);
                g.drawLine(x + 1, y, x + width - 2, y);

                if(themeColors) {
                    g.darkerColor(25);
                } else {
                    g.setColor(colorB);
                }
                g.drawLine(x + 1, y + 1, x + 1, y + height - 3);
                g.drawLine(x + 2, y + 1, x + width - 3, y + 1);

                if(themeColors) {
                    g.darkerColor(50);
                } else {
                    g.setColor(colorC);
                }
                g.drawLine(x, y + height - 1, x + width - 1, y + height - 1);
                g.drawLine(x + width - 1, y, x + width - 1, y + height - 2);

                if(themeColors) {
                    g.darkerColor(25);
                } else {
                    g.setColor(colorD);
                }
                g.drawLine(x + 1, y + height - 2, x + width - 2, y + height - 2);
                g.drawLine(x + width - 2, y + 1, x + width - 2, y + height - 3);
                break;
            case TYPE_BEVEL_LOWERED: 
                if(themeColors) {
                    g.setColor(getBackgroundColor(c));
                    g.darkerColor(50);
                } else {
                    g.setColor(colorD);
                }
                g.drawLine(x, y, x, y + height - 1);
                g.drawLine(x + 1, y, x + width - 1, y);

                if(themeColors) {
                    g.lighterColor(25);
                } else {
                    g.setColor(colorC);
                }
                g.drawLine(x + 1, y + 1, x + 1, y + height - 2);
                g.drawLine(x + 2, y + 1, x + width - 2, y + 1);

                if(themeColors) {
                    g.lighterColor(50);
                } else {
                    g.setColor(colorC);
                }
                g.drawLine(x + 1, y + height - 1, x + width - 1, y + height - 1);
                g.drawLine(x + width - 1, y + 1, x + width - 1, y + height - 2);

                if(themeColors) {
                    g.lighterColor(25);
                } else {
                    g.setColor(colorA);
                }
                g.drawLine(x + 2, y + height - 2, x + width - 2, y + height - 2);
                g.drawLine(x + width - 2, y + 2, x + width - 2, y + height - 3);
                break;
            case TYPE_COMPOUND:
                Style style = c.getStyle();
                boolean drawLeft = true;
                boolean drawRight = true;

                Border top = compoundBorders[Component.TOP];
                Border bottom = compoundBorders[Component.BOTTOM];
                Border left = compoundBorders[Component.LEFT];
                Border right = compoundBorders[Component.RIGHT];
                int topThickness = 0;
                int bottomThickness = 0;

                if (top != null) {
                    Rectangle clip = saveClip(g);
                    topThickness = top.thickness;
                    g.clipRect(x, y, width, topThickness);
                    top.paint(g, x, y, width, height, c); //top.paint(g, c);
                    restoreClip(g, clip);
                }

                if (bottom != null) {
                    Rectangle clip = saveClip(g);
                    bottomThickness = bottom.thickness;
                    g.clipRect(x, y + height - bottomThickness, width, bottomThickness);
                    bottom.paint(g, x, y, width, height, c); //bottom.paint(g, c);
                    restoreClip(g, clip);
                }

                if ((drawLeft) && (left != null)) {
                    Rectangle clip = saveClip(g);
                    g.clipRect(x, y + topThickness,
                            left.thickness,
                            height - topThickness - bottomThickness);
                    left.paint(g, x, y, width, height, c); //left.paint(g, c);
                    restoreClip(g, clip);
                }
                if ((drawRight) && (right != null)) {
                    Rectangle clip = saveClip(g);
                    g.clipRect(x + width - right.thickness,
                            y + topThickness,
                            right.thickness,
                            height - topThickness - bottomThickness);
                    right.paint(g, x, y, width, height, c); //right.paint(g, c);
                    restoreClip(g, clip);
                }
                break;
            case TYPE_IMAGE:
            case TYPE_IMAGE_HORIZONTAL:
            case TYPE_IMAGE_VERTICAL:
                break;
        }
        g.setColor(new Color(originalColor));
	}

	private Color getBackgroundColor(Component c) {
		if(c.hasFocus()) {
			return c.getStyle().getBgSelectionColor();
		}
		return c.getStyle().getBgColor();
	}
    
	/**
	 * Sets the default border to the given value.
	 *
	 * 
	 * @param border - new border value
	 * @see getDefaultBorder()
	 */
	public static void setDefaultBorder( Border border)
	{
		defaultBorder = border;
	}

	/**
	 * Gets the default border to the given value.
	 *
	 * 
	 * 
	 * @return the default border to the given value
	 * @see setDefaultBorder(com.sun.dtv.lwuit.plaf.Border)
	 */
	public static Border getDefaultBorder()
	{
		return defaultBorder;
	}
	 /**
     * Returns true if this border type is a rectangle border.
     *
     * @return true if this border is rectangle
     */
    public boolean isRectangleType() {
        return type == TYPE_LINE || type == TYPE_BEVEL_LOWERED ||
                type == TYPE_BEVEL_RAISED || type == TYPE_ETCHED_LOWERED ||
                type == TYPE_ETCHED_RAISED || type == TYPE_COMPOUND 
                || type == TYPE_EMPTY || type==TYPE_DOTTED || type==TYPE_DASHED || type==TYPE_DOUBLE
                || type == TYPE_OUTSET || type == TYPE_INSET || type == TYPE_GROOVE || type == TYPE_RIDGE;
    }
    /**
     * Compares two object including the scenario one of them is null (thus avoiding equals pitfalls)
     * 
     * @param obj1 The first object to compare
     * @param obj2 The second object to compare
     * @return true if the two object are equal (or both null), false otherwise
     */
    private static boolean isSame(Object obj1,Object obj2) {
        if (obj1==null) {
            return (obj2==null);
        } else if (obj2==null) {
            return (obj1==null);
        }
        return obj1.equals(obj2);
    }
    /**
     * Utility method used to save the current clip area
     *
     * @param g The graphics to obtain the clip area from
     * @return A Rectangle object representing the current clip area
     */
    private Rectangle saveClip(Graphics g) {
        return new Rectangle(g.getClipX(), g.getClipY(), g.getClipWidth(), g.getClipHeight());
    }

    /**
     * Utility method used to restore a previously saved clip area
     *
     * @param g The graphics to apply the clip area on
     * @param rect A Rectangle representing the saved clip area
     */
    private void restoreClip(Graphics g,Rectangle rect) {
        g.setClip(rect.getX(), rect.getY(), rect.getSize().getWidth(), rect.getSize().getHeight());
    }
    /**
     * This method returns the Compound Borders array.
     * The array size is 4 and the borders arranged as follows :
     * Border[] borders = getCompoundBorders();
     * Border top = borders[Component.TOP];
     * Border bottom = borders[Component.BOTTOM];
     * Border left = borders[Component.LEFT];
     * Border right = borders[Component.RIGHT];
     *
     * @return the borders array or null if this is not a Compound Border
     */
    public Border[] getCompoundBorders() {
        return compoundBorders;
    }

}
