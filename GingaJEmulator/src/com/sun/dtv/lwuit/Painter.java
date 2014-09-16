package com.sun.dtv.lwuit;

import com.sun.dtv.lwuit.geom.Rectangle;

public interface Painter
{
	/**
	 * Draws inside the given rectangle clipping area.
	 *
	 * 
	 * @param g - the Graphics object
	 * @param rect - the given rectangle clipping area
	 */
	void paint(Graphics g, Rectangle rect);

}
