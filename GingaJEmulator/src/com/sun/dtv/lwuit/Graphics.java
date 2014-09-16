package com.sun.dtv.lwuit;

import java.awt.Color;
import java.awt.Graphics2D;

import com.sun.dtv.lwuit.geom.Rectangle;

public final class Graphics extends Object {

    private int translateX;
    private int translateY;
    private com.sun.dtv.lwuit.Font font;
    private java.awt.Graphics awtGraphics;

    /**
     * Create a LWUIT Graphics object using a Graphics instance of the underlying
     * Graphics library to be wrapped.
     *
     * 
     * @param g - a Graphics instance of the underlying Graphics library
     */
    public Graphics(java.awt.Graphics2D g) {
        translateX = 0;
        translateY = 0;
        awtGraphics = g;
    }

    /**
     * Translates the X/Y location for drawing on the underlying surface.
     * Translation is incremental so the new value will be added to the current
     * translation and in order to reset translation we have to invoke
     * <code>translate(-getTranslateX(), -getTranslateY()) </code>
     *
     * 
     * @param x - the x coordinate
     * @param y - the y coordinate
     */
    public void translate(int x, int y) {
        translateX += x;
        translateY += y;

        awtGraphics.translate(x, y);
    }

    /**
     * Returns the current x translate value.
     *
     * 
     * 
     * @return an int representing the translate x value
     */
    public int getTranslateX() {
        return this.translateX;
    }

    /**
     * Returns the current y translate value.
     *
     * 
     * 
     * @return an int representing the translate y value
     */
    public int getTranslateY() {
        return this.translateY;
    }

    /**
     * Returns the current color.
     *
     * 
     * 
     * @return the current color
     * @see setColor(java.awt.Color)
     */
    public Color getColor() {
        return awtGraphics.getColor();
    }

    /**
     * Sets the current rgb color while ignoring any potential alpha component within
     * said color value.
     *
     * 
     * @param color - the color to be set.
     * @see getColor()
     */
    public void setColor(Color color) {
        awtGraphics.setColor(color);
    }

    /**
     * Returns the font used with the drawString method calls.
     *
     * 
     * 
     * @return the current font
     * @see setFont(Font)
     */
    public Font getFont() {
        return font;
    }

    /**
     * Sets the font to use with the drawString method calls.
     *
     * 
     * @param font - the font to be set
     * @see getFont()
     */
    public void setFont(Font font) {
        this.font = font;

        awtGraphics.setFont(font.getAWTFont());
    }

    /**
     * Returns the x clipping position.
     *
     * 
     * 
     * @return an int representing the clip x value
     */
    public int getClipX() {
        java.awt.Rectangle rect = awtGraphics.getClipBounds();

        if (rect != null) {
            return (int) rect.x;
        }

        return 0;
    }

    /**
     * Returns the y clipping position.
     *
     * 
     * 
     * @return an int representing the clip y value
     */
    public int getClipY() {
        java.awt.Rectangle rect = awtGraphics.getClipBounds();

        if (rect != null) {
            return (int) rect.y;
        }

        return 0;
    }

    /**
     * Returns the clip width.
     *
     * 
     * 
     * @return an int representing the clip width value
     */
    public int getClipWidth() {
        java.awt.Rectangle rect = awtGraphics.getClipBounds();

        if (rect != null) {
            return (int) rect.width;
        }

        return 1280;
    }

    /**
     * Returns the clip height.
     *
     * 
     * 
     * @return an int representing the clip height value
     */
    public int getClipHeight() {
        java.awt.Rectangle rect = awtGraphics.getClipBounds();

        if (rect != null) {
            return (int) rect.height;
        }

        return 720;
    }

    /**
     * Clips the given rectangle by intersecting with the current clipping region,
     * this method can thus only shrink the clipping region and never increase it.
     *
     * 
     * @param x - the x coordinate of the given rectangle
     * @param y - the y coordinate of the given rectangle
     * @param width - the width of the given rectangle
     * @param height - the height of the given rectangle
     */
    public void clipRect(int x, int y, int width, int height) {
        if (x > getClipWidth() || y >= getClipHeight() || width < getClipX() || height < getClipY()) {
            return;
        }

        if (x < getClipX()) {
            x = getClipX();
        }

        if (y < getClipY()) {
            y = getClipY();
        }

        if ((width - x) > getClipWidth()) {
            width = getClipWidth() - x;
        }

        if ((height - y) > getClipHeight()) {
            height = getClipHeight() - y;
        }

        awtGraphics.clipRect(x, y, width, height);
    }

    /**
     * Updates the clipping region to match the given region exactly.
     *
     * 
     * @param x - the x coordinate of the given rectangle
     * @param y - the y coordinate of the given rectangle
     * @param width - the width of the given rectangle
     * @param height - the height of the given rectangle
     */
    public void setClip(int x, int y, int width, int height) {
        /*if (x > 1280 || y >= 720 || width < 0 || height < 0) {
        return;
        }
        
        if (x < 0) {
        x = 0;
        }
        
        if (y < 0) {
        y = 0;
        }
        
        if ((width-x) > 1280) {
        width = 1280-x;
        }
        
        if ((height-y) > 720) {
        height = 720-y;
        }*/

        awtGraphics.setClip(x, y, width, height);
    }

    /**
     * Draws a line between the 2 X/Y coordinates.
     *
     * 
     * @param x1 - first x position
     * @param y1 - first y position
     * @param x2 - second x position
     * @param y2 - second y position
     */
    public void drawLine(int x1, int y1, int x2, int y2) {
        awtGraphics.drawLine(x1, y1, x2, y2);
    }

    /**
     * Fills the rectangle from the given position according to the width/height
     * minus 1 pixel according to the convention in Java.
     *
     * 
     * @param x - the x coordinate of the given rectangle
     * @param y - the y coordinate of the given rectangle
     * @param width - the width of the given rectangle
     * @param height - the height of the given rectangle
     */
    public void fillRect(int x, int y, int width, int height) {
        awtGraphics.fillRect(x, y, width, height);
    }

    /**
     * Converts the color channel value according to the offest within the distance
     *
     */
    private int calculateGradientChannel(int sourceChannel, int destChannel, int distance, int offset) {
        if (sourceChannel == destChannel) {
            return sourceChannel;
        }
        float ratio = ((float) offset) / ((float) distance);
        int pos = (int) (Math.abs(sourceChannel - destChannel) * ratio);
        if (sourceChannel > destChannel) {
            return sourceChannel - pos;
        } else {
            return sourceChannel + pos;
        }
    }

    private void updateGradientColor(int sourceR, int sourceG, int sourceB, int destR, int destG, int destB, int distance, int offset) {
        int r = calculateGradientChannel(sourceR, destR, distance, offset);
        int g = calculateGradientChannel(sourceG, destG, distance, offset);
        int b = calculateGradientChannel(sourceB, destB, distance, offset);
        int color = 0xff000000 | ((r << 16) & 0xff0000) | ((g << 8) & 0xff00) | (b & 0xff);

        setColor(new Color(color));
    }

    /**
     * Draws a radial gradient in the given coordinates with the given colors,
     * doesn't take alpha into consideration when drawing the gradient.
     * Notice that a radial gradient will result in a circular shape, to create
     * a square use fillRect or draw a larger shape and clip to the appropriate size.
     *
     * 
     * @param startColor - the starting RGB color
     * @param endColor - the ending RGB color
     * @param x - the x coordinate
     * @param y - the y coordinate
     * @param width - the width of the region to be filled
     * @param height - the height of the region to be filled
     */
    public void fillRadialGradient(Color startColor, Color endColor, int x, int y, int width, int height) {
        Color oldColor = getColor();
        int sourceR = startColor.getRed();
        int sourceG = startColor.getGreen();
        int sourceB = startColor.getBlue();
        int destR = endColor.getRed();
        int destG = endColor.getGreen();
        int destB = endColor.getBlue();
        int originalHeight = height;
        while (width > 0 && height > 0) {
            updateGradientColor(sourceR, sourceG, sourceB, destR, destG, destB, originalHeight, height);
            fillArc(x, y, width, height, 0, 360);
            x++;
            y++;
            width -= 2;
            height -= 2;
        }
        setColor(oldColor);
    }

    /**
     * Draws a linear gradient in the given coordinates with the given colors,
     * doesn't take alpha into consideration when drawing the gradient.
     *
     * 
     * @param startColor - the starting RGB color
     * @param endColor - the ending RGB color
     * @param x - the x coordinate
     * @param y - the y coordinate
     * @param width - the width of the region to be filled
     * @param height - the height of the region to be filled
     * @param horizontal - indicating whether it is a horizontal fill or vertical
     */
    public void fillLinearGradient(Color startColor, Color endColor, int x, int y, int width, int height, boolean horizontal) {
        Color oldColor = getColor();
        int sourceR = startColor.getRed();
        int sourceG = startColor.getGreen();
        int sourceB = startColor.getBlue();
        int destR = endColor.getRed();
        int destG = endColor.getGreen();
        int destB = endColor.getBlue();
        if (horizontal) {
            for (int iter = 0; iter < width; iter++) {
                updateGradientColor(sourceR, sourceG, sourceB, destR, destG, destB, width, iter);
                drawLine(x + iter, y, x + iter, y + height);
            }
        } else {
            for (int iter = 0; iter < height; iter++) {
                updateGradientColor(sourceR, sourceG, sourceB, destR, destG, destB, height, iter);
                drawLine(x, y + iter, x + width, y + iter);
            }
        }
        setColor(oldColor);
    }

    /**
     * Draws a rectangle in the given coordinates.
     *
     * 
     * @param x - the x coordinate of the given rectangle
     * @param y - the y coordinate of the given rectangle
     * @param width - the width of the given rectangle
     * @param height - the height of the given rectangle
     */
    public void drawRect(int x, int y, int width, int height) {
        awtGraphics.drawRect(x, y, width, height);
    }

    /**
     * Draws a rounded corner rectangle in the given coordinates with the
     * arcWidth/height matching the last two arguments respectively.
     *
     * 
     * @param x - the x coordinate of the given rectangle
     * @param y - the y coordinate of the given rectangle
     * @param width - the width of the given rectangle
     * @param height - the height of the given rectangle
     * @param arcWidth - the arc width of the given rectangle
     * @param arcHeight - the arc height of the given rectangle
     */
    public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        awtGraphics.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
    }

    /**
     * Fills a rounded rectangle in the same way as drawRoundRect.
     *
     * 
     * @param x - the x coordinate of the given rectangle
     * @param y - the y coordinate of the given rectangle
     * @param width - the width of the given rectangle
     * @param height - the height of the given rectangle
     * @param arcWidth - the arc width of the given rectangle
     * @param arcHeight - the arc height of the given rectangle
     * @see drawRoundRect(int, int, int, int, int, int)
     */
    public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        awtGraphics.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
    }

    /**
     * Fills a rounded rectangle in the same way as drawRoundRect with an
     * optionally translucent fill color.
     *
     * 
     * @param x - the x coordinate of the given rectangle
     * @param y - the y coordinate of the given rectangle
     * @param width - the width of the given rectangle
     * @param height - the height of the given rectangle
     * @param arcWidth - the arc width of the given rectangle
     * @param arcHeight - the arc height of the given rectangle
     * @param alpha - alpha composite value
     * @see drawRoundRect(int, int, int, int, int, int)
     */
    public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight, byte alpha) {
        Color ocolor = getColor(),
                ncolor = new Color(ocolor.getRed(), ocolor.getGreen(), ocolor.getBlue(), alpha + 128);

        awtGraphics.setColor(ncolor);
        awtGraphics.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
        awtGraphics.setColor(ocolor);
    }

    /**
     * Fills a circular or elliptical arc based on the given angles and bounding
     * box.
     *
     * 
     * @param x - the x coordinate of the given arc
     * @param y - the y coordinate of the given arc
     * @param width - the width of the given arc
     * @param height - the height of the given arc
     * @param startAngle - the start angle of the given arc
     * @param arcAngle - the end angle of the given arc
     */
    public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        awtGraphics.fillArc(x, y, width, height, startAngle, arcAngle);
    }

    /**
     * Fills a circular or elliptical arc based on the given angles and bounding
     * box with an optionally translucent fill color.
     *
     * 
     * @param x - the x coordinate of the given arc
     * @param y - the y coordinate of the given arc
     * @param width - the width of the given arc
     * @param height - the height of the given arc
     * @param startAngle - the start angle of the given arc
     * @param arcAngle - the end angle of the given arc
     * @param alpha - alpha composite value
     */
    public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle, byte alpha) {
        Color ocolor = getColor(),
                ncolor = new Color(ocolor.getRed(), ocolor.getGreen(), ocolor.getBlue(), alpha + 128);

        awtGraphics.setColor(ncolor);
        awtGraphics.fillArc(x, y, width, height, startAngle, arcAngle);
        awtGraphics.setColor(ocolor);
    }

    /**
     * Draws a circular or elliptical arc based on the given angles and bounding box.
     *
     * 
     * @param x - the x coordinate of the given arc
     * @param y - the y coordinate of the given arc
     * @param width - the width of the given arc
     * @param height - the height of the given arc
     * @param startAngle - the start angle of the given arc
     * @param arcAngle - the end angle of the given arc
     */
    public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        awtGraphics.drawArc(x, y, width, height, startAngle, arcAngle);
    }

    /**
     * Draw a string using the current font and color in the x,y coordinates.
     * The font is drawn from the top position and not the baseline.
     *
     * 
     * @param str - the string
     * @param x - the x coordinate of the string
     * @param y - the y coordinate of the string
     */
    public void drawString(String str, int x, int y) {
        if (!(this.font instanceof CustomFont)) {
            if (getFont() != null) {
                awtGraphics.drawString(str, x, y + getFont().getSize());
            }
        } else {
            ((CustomFont) this.font).drawChars(this, str.toCharArray(), 0, str.length(), x, y);
        }
    }

    /**
     * Draw the given char using the current font and color in the x,y coordinates.
     * The font is drawn from the top position and not the baseline.
     *
     * 
     * @param character - a char
     * @param x - x coordinate
     * @param y - y coordinate
     */
    public void drawChar(char character, int x, int y) {
        if (getFont() != null) {
            char chars[] = new char[1];

            chars[0] = character;

            awtGraphics.drawChars(chars, 0, chars.length, x, y + getFont().getSize());
        }
    }

    /**
     * Draw the given char array using the current font and color in the x,y
     * coordinates. The font is drawn from the top position and not the baseline.
     *
     * 
     * @param data - the given char array
     * @param offset - offset of the array
     * @param length - length of the array
     * @param x - x coordinate of the position
     * @param y - y coordinate of the position
     */
    public void drawChars(char[] data, int offset, int length, int x, int y) {
        if (getFont() != null) {
            awtGraphics.drawChars(data, offset, length, x, y + getFont().getSize());
        }
    }

    /**
     * Draws the image so its top left coordinate corresponds to x/y.
     *
     * 
     * @param img - an Image object
     * @param x - x coordinate
     * @param y - y coordinate
     */
    public void drawImage(Image img, int x, int y) {
        awtGraphics.drawImage(img.getAWTImage(), x, y, Display.getInstance().getImplementation().getFrame());
    }

    public boolean drawImage(com.sun.dtv.lwuit.Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, java.awt.Color bg, java.awt.image.ImageObserver observer) {
        return awtGraphics.drawImage(img.getAWTImage(), dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, bg, Display.getInstance().getImplementation().getFrame());
    }

    public boolean drawImage(com.sun.dtv.lwuit.Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, java.awt.image.ImageObserver observer) {
        return awtGraphics.drawImage(img.getAWTImage(), dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, Display.getInstance().getImplementation().getFrame());
    }

    public boolean drawImage(com.sun.dtv.lwuit.Image img, int x, int y, int w, int h, java.awt.Color bg, java.awt.image.ImageObserver observer) {
        return awtGraphics.drawImage(img.getAWTImage(), x, y, w, h, bg, Display.getInstance().getImplementation().getFrame());
    }

    public boolean drawImage(com.sun.dtv.lwuit.Image img, int x, int y, int w, int h, java.awt.image.ImageObserver observer) {
        return awtGraphics.drawImage(img.getAWTImage(), x, y, w, h, Display.getInstance().getImplementation().getFrame());
    }

    public boolean drawImage(com.sun.dtv.lwuit.Image img, int x, int y, java.awt.image.ImageObserver observer) {
        return awtGraphics.drawImage(img.getAWTImage(), x, y, Display.getInstance().getImplementation().getFrame());
    }

    public boolean drawImage(com.sun.dtv.lwuit.Image img, int x, int y, java.awt.Color bg, java.awt.image.ImageObserver observer) {
        return awtGraphics.drawImage(img.getAWTImage(), x, y, bg, Display.getInstance().getImplementation().getFrame());
    }

    /**
     * Draws a filled triangle with the given coordinates.
     *
     * 
     * @param x1 - x coordinate of point 1
     * @param y1 - y coordinate of point 1
     * @param x2 - x coordinate of point 2
     * @param y2 - y coordinate of point 2
     * @param x3 - x coordinate of point 3
     * @param y3 - y coordinate of point 3
     */
    public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        int xPoints[] = {x1, x2, x3}, yPoints[] = {y1, y2, y3};

        awtGraphics.fillPolygon(xPoints, yPoints, 3);
    }

    /**
     * Draws a filled triangle with the given coordinates with an optionally
     * translucent fill color.
     *
     * 
     * @param x1 - x coordinate of point 1
     * @param y1 - y coordinate of point 1
     * @param x2 - x coordinate of point 2
     * @param y2 - y coordinate of point 2
     * @param x3 - x coordinate of point 3
     * @param y3 - y coordinate of point 3
     * @param alpha - alpha composite value
     */
    public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3, byte alpha) {
        Color ocolor = getColor(),
                ncolor = new Color(ocolor.getRed(), ocolor.getGreen(), ocolor.getBlue(), alpha + 128);

        awtGraphics.setColor(ncolor);
        fillTriangle(x1, y1, x2, y2, x3, y3);
        awtGraphics.setColor(ocolor);
    }

    /**
     * Fills a rectangle with an optionally translucent fill color.
     *
     * 
     * @param x - x coordinate of upper left corner
     * @param y - y coordinate of upper left corner
     * @param w - the width
     * @param h - the height
     * @param alpha - alpha composite value
     */
    public void fillRect(int x, int y, int w, int h, byte alpha) {
        Color ocolor = getColor(),
                ncolor = new Color(ocolor.getRed(), ocolor.getGreen(), ocolor.getBlue(), alpha + 128);

        awtGraphics.setColor(ncolor);
        awtGraphics.fillRect(x, y, w, h);
        awtGraphics.setColor(ocolor);
    }

    /**
     * Makes the current color slightly lighter, this is useful for many visual effects.
     *
     * 
     * @param factor - the degree of lightening a color per channel a number from 1 to 255
     */
    public void lighterColor(int factor) {
        if (factor < 0 || factor > 255) {
            return;
        }

        Color color = getColor();

        int r = color.getRed() + factor,
                g = color.getGreen() + factor,
                b = color.getBlue() + factor,
                a = color.getAlpha();

        if (r > 255) {
            r = 255;
        }

        if (g > 255) {
            g = 255;
        }

        if (b > 255) {
            b = 255;
        }

        setColor(new Color(r, g, b, a));
    }

    /**
     * Makes the current color slightly darker, this is useful for many visual effects.
     *
     * 
     * @param factor - the degree of lightening a color per channel a number from 1 to 255
     */
    public void darkerColor(int factor) {
        if (factor < 0 || factor > 255) {
            return;
        }

        Color color = getColor();

        int r = color.getRed() - factor,
                g = color.getGreen() - factor,
                b = color.getBlue() - factor,
                a = color.getAlpha();

        if (r < 0) {
            r = 0;
        }

        if (g < 0) {
            g = 0;
        }

        if (b < 0) {
            b = 0;
        }

        setColor(new Color(r, g, b, a));
    }

    /**
     * Obtains the <code>java.awt.Graphics2D</code> object wrapped by this
     * <A HREF="../../../../com/sun/dtv/lwuit/Graphics.html" title="class in com.sun.dtv.lwuit"><CODE>Graphics</CODE></A> instance.
     *
     * 
     * 
     * @return the wrapped java.awt.Graphics2D object
     */
    public Graphics2D getAWTGraphics() {
        return (java.awt.Graphics2D) awtGraphics;
    }

    /**
     * Clears the specified rectangle by filling it with the background color
     * of the current drawing surface.
     * 
     *IMPLEMENTATION NOTE: Delegate to the wrapped AWT Graphics2D
     * object.</p>
     *
     * 
     * @param x - the x coordinate of the given rectangle
     * @param y - the y coordinate of the given rectangle
     * @param width - the width of the given rectangle
     * @param height - the height of the given rectangle
     */
    public void clearRect(int x, int y, int width, int height) {
        awtGraphics.clearRect(x, y, width, height);
    }

    /**
     * Copies an area of the component by a distance specified by the
     * <code>dx</code> and <code>dy</code> parameters.
     * 
     *IMPLEMENTATION NOTE: Delegate to the wrapped AWT Graphics2D
     * object.</p>
     *
     * 
     * @param x - the x coordinate of the given area
     * @param y - the y coordinate of the given area
     * @param width - the width of the given area
     * @param height - the height of the given area
     * @param dx - x coordinate of the distance
     * @param dy - y coordinate of the distance
     */
    public void copyArea(int x, int y, int width, int height, int dx, int dy) {
        awtGraphics.copyArea(x, y, width, height, dx, dy);
    }

    /**
     * Draws a 3D highlighted outline of the specified rectangle.
     * 
     *IMPLEMENTATION NOTE: Delegate to the wrapped AWT Graphics2D
     * object.</p>
     *
     * 
     * @param x - the x coordinate of the given rectangle
     * @param y - the y coordinate of the given rectangle
     * @param width - the width of the given rectangle
     * @param height - the height of the given rectangle
     * @param raised - true if the rectangle appears to be raised above the surface, or false if it appears to be sunk into the surface
     */
    public void draw3DRect(int x, int y, int width, int height, boolean raised) {
        awtGraphics.draw3DRect(x, y, width, height, raised);
    }

    /**
     * Draws the text given by the specified byte array, using this graphics
     * context's current font and color.
     * 
     *IMPLEMENTATION NOTE: Delegate to the wrapped AWT Graphics2D
     * object.</p>
     *
     * 
     * @param data - the specified byte array
     * @param offset - the array's offset
     * @param length - the array's length
     * @param x - the x coordinate of the position
     * @param y - the y coordinate of the position
     */
    public void drawBytes(byte[] data, int offset, int length, int x, int y) {
        awtGraphics.drawBytes(data, offset, length, x, y);
    }

    /**
     * Draws the outline of an oval into the bounds of the given rectangle.
     * 
     *IMPLEMENTATION NOTE: Delegate to the wrapped AWT Graphics2D
     * object.</p>
     *
     * 
     * @param x - the x coordinate of the given rectangle
     * @param y - the y coordinate of the given rectangle
     * @param width - the width of the given rectangle
     * @param height - the height of the given rectangle
     */
    public void drawOval(int x, int y, int width, int height) {
        awtGraphics.drawOval(x, y, width, height);
    }

    /**
     * Draws a closed polygon defined by arrays of x and y coordinates.
     * 
     *IMPLEMENTATION NOTE: Delegate to the wrapped AWT Graphics2D
     * object.</p>
     *
     * 
     * @param xPoints - array of x coordinates
     * @param yPoints - array of y coordinates
     * @param nPoints - number of given points
     */
    public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        awtGraphics.drawPolygon(xPoints, yPoints, nPoints);
    }

    /**
     * Draws a sequence of connected lines defined by arrays of x and y
     * coordinates.
     * 
     *IMPLEMENTATION NOTE: Delegate to the wrapped AWT Graphics2D
     * object.</p>
     *
     * 
     * @param xPoints - array of x coordinates
     * @param yPoints - array of y coordinates
     * @param nPoints - number of given points
     */
    public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) {
        awtGraphics.drawPolyline(xPoints, yPoints, nPoints);
    }

    /**
     * Fills a 3D highlighted rectangle filled with the current color.
     * 
     *IMPLEMENTATION NOTE: Delegate to the wrapped AWT Graphics2D
     * object.</p>
     *
     * 
     * @param x - the x coordinate of the given rectangle
     * @param y - the y coordinate of the given rectangle
     * @param width - the width of the given rectangle
     * @param height - the height of the given rectangle
     * @param raised - true if the rectangle appears to be raised above the surface, or false if it appears to be sunk into the surface
     */
    public void fill3DRect(int x, int y, int width, int height, boolean raised) {
        awtGraphics.fill3DRect(x, y, width, height, raised);
    }

    /**
     * Fills a 3D highlighted rectangle filled with an optionally translucent
     * fill color.
     * 
     *IMPLEMENTATION NOTE: Delegate to the wrapped AWT Graphics2D
     * object.</p>
     *
     * 
     * @param x - the x coordinate of the given rectangle
     * @param y - the y coordinate of the given rectangle
     * @param width - the width of the given rectangle
     * @param height - the height of the given rectangle
     * @param raised - true if the rectangle appears to be raised above the surface, or false if it appears to be sunk into the surface
     * @param alpha - alpha composite value
     */
    public void fill3DRect(int x, int y, int width, int height, boolean raised, byte alpha) {
        Color ocolor = getColor(),
                ncolor = new Color(ocolor.getRed(), ocolor.getGreen(), ocolor.getBlue(), alpha + 128);

        awtGraphics.setColor(ncolor);
        awtGraphics.fill3DRect(x, y, width, height, raised);
        awtGraphics.setColor(ocolor);
    }

    /**
     * Fills an oval bounded by the specified rectangle with the current color.
     * 
     *IMPLEMENTATION NOTE: Delegate to the wrapped AWT Graphics2D
     * object.</p>
     *
     * 
     * @param x - the x coordinate of the given rectangle
     * @param y - the y coordinate of the given rectangle
     * @param width - the width of the given rectangle
     * @param height - the height of the given rectangle
     */
    public void fillOval(int x, int y, int width, int height) {
        awtGraphics.fillOval(x, y, width, height);
    }

    /**
     * Fills an oval bounded by the specified rectangle with an optionally
     * translucent fill color.
     * 
     *IMPLEMENTATION NOTE: Delegate to the wrapped AWT Graphics2D
     * object.</p>
     *
     * 
     * @param x - the x coordinate of the given rectangle
     * @param y - the y coordinate of the given rectangle
     * @param width - the width of the given rectangle
     * @param height - the height of the given rectangle
     * @param alpha - alpha composite value
     */
    public void fillOval(int x, int y, int width, int height, byte alpha) {
        Color ocolor = getColor(),
                ncolor = new Color(ocolor.getRed(), ocolor.getGreen(), ocolor.getBlue(), alpha + 128);

        awtGraphics.setColor(ncolor);
        awtGraphics.fillOval(x, y, width, height);
        awtGraphics.setColor(ocolor);
    }

    /**
     * Fills a closed polygon defined by the specified arrays of x and y
     * coordinates with the graphics context's current color.
     * 
     *IMPLEMENTATION NOTE: Delegate to the wrapped AWT Graphics2D
     * object.</p>
     *
     * 
     * @param xPoints - array of x coordinates
     * @param yPoints - array of y coordinates
     * @param nPoints - number of given points
     */
    public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        awtGraphics.fillPolygon(xPoints, yPoints, nPoints);
    }

    /**
     * Fills a closed polygon defined by the specified arrays of x and y
     * coordinates with an optionally translucent fill color.
     * 
     *IMPLEMENTATION NOTE: Delegate to the wrapped AWT Graphics2D
     * object.</p>
     *
     * 
     * @param xPoints - array of x coordinates
     * @param yPoints - array of y coordinates
     * @param nPoints - number of given points
     * @param alpha - alpha composite value
     */
    public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints, byte alpha) {
        Color ocolor = getColor(),
                ncolor = new Color(ocolor.getRed(), ocolor.getGreen(), ocolor.getBlue(), alpha + 128);

        awtGraphics.setColor(ncolor);
        awtGraphics.fillPolygon(xPoints, yPoints, nPoints);
        awtGraphics.setColor(ocolor);
    }

    /**
     * Returns the bounding rectangle of the current clipping area.
     * 
     *IMPLEMENTATION NOTE: Delegate to the wrapped AWT Graphics2D
     * object.</p>
     *
     * 
     * 
     * @return the bounding rectangle
     */
    public java.awt.Rectangle getClipBounds() {
        return awtGraphics.getClipBounds();
    }

    /**
     * Returns the bounding rectangle of the current clipping area.
     * An existing rectangle is used instead of allocating a new one.
     * 
     *IMPLEMENTATION NOTE: Delegate to the wrapped AWT Graphics2D
     * object.</p>
     *
     * 
     * @param r - the given existing rectangle
     * @return the bounding rectangle
     */
    public Rectangle getClipBounds(Rectangle r) {
        return new com.sun.dtv.lwuit.geom.Rectangle(awtGraphics.getClipBounds(new java.awt.Rectangle(r.getX(), r.getY(), r.getSize().getWidth(), r.getSize().getHeight())));
    }

    /**
     * Returns true if the specified rectangular area might intersect the
     * current clipping area.
     * 
     *IMPLEMENTATION NOTE: Delegate to the wrapped AWT Graphics2D
     * object.</p>
     *
     * 
     * @param x - the x coordinate of the given rectangle
     * @param y - the y coordinate of the given rectangle
     * @param width - the width of the given rectangle
     * @param height - the height of the given rectangle
     * @return true if the specified area intersects with the clipping area, false otherwise
     */
    public boolean hitClip(int x, int y, int width, int height) {
        return awtGraphics.hitClip(x, y, width, height);
    }
}
