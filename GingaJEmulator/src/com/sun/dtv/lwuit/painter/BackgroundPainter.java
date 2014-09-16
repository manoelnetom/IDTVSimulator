package com.sun.dtv.lwuit.painter;

import com.sun.dtv.lwuit.Component;
import com.sun.dtv.lwuit.Graphics;
import com.sun.dtv.lwuit.Painter;
import com.sun.dtv.lwuit.geom.Rectangle;
import com.sun.dtv.lwuit.Image;
import com.sun.dtv.lwuit.plaf.Style;

public class BackgroundPainter implements Painter
{
	private Component parent;

	/**
	 * Construct a background painter for the given component.
	 *
	 * 
	 * @param parent - the given component
	 */
	public BackgroundPainter(Component parent)
	{
		this.parent = parent;
	}

	/**
	 * <B>Description copied from interface: <CODE><A HREF="../../../../../com/sun/dtv/lwuit/Painter.html#paint(com.sun.dtv.lwuit.Graphics, com.sun.dtv.lwuit.geom.Rectangle)">Painter</A></CODE></B></DD>
	 * Draws inside the given rectangle clipping area.
	 *
	 * 
	 * @param g - the Graphics object
	 * @param rect - the given rectangle clipping area
	 * @see paint in interface Painter
	 */
	public void paint(Graphics g, Rectangle rect)
	{
		Style s = parent.getStyle();
		int x = rect.getX();
		int y = rect.getY();
		int width = rect.getSize().getWidth();
		int height = rect.getSize().getHeight();
		
		if (width <= 0 || height <= 0) {
			return;
		}

		Image bgImage = s.getBgImage();

		if (bgImage == null) {
			if (parent.hasFocus() && parent.isFocusPainted()) {
				g.setColor(s.getBgSelectionColor());
				g.fillRect(x, y, width, height, s.getBgTransparency());
			} else {
				g.setColor(s.getBgColor());
				g.fillRect(x, y, width, height, s.getBgTransparency());
			}
		} else {
			if (s.isScaleImage()) {
				if (bgImage.getWidth() != width || bgImage.getHeight() != height) {
					bgImage = bgImage.scaled(width, height);
					s.setBgImage(bgImage, true);
				}
			} else {
				int iW = bgImage.getWidth();
				int iH = bgImage.getHeight();
				
				for(int xPos = 0 ; xPos < width ; xPos += iW) { 
					for(int yPos = 0 ; yPos < height ; yPos += iH) { 
						g.drawImage(s.getBgImage(), x + xPos, y + yPos);
					}
				}
				
				return;
			}

			g.drawImage(s.getBgImage(), x, y);
		}
	}

}
