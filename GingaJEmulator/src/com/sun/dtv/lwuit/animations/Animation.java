package com.sun.dtv.lwuit.animations;

import com.sun.dtv.lwuit.Graphics;

public interface Animation
{
	/**
	 * Allows the animation to reduce "repaint" calls when it returns false. It is
	 * called once for every frame.
	 *
	 * 
	 * 
	 * @return true if a repaint is desired or false if no repaint is necessary
	 */
	boolean animate();

	/**
	 * Draws the animation, within a component the standard paint method would be
	 * invoked since it bares the exact same signature.
	 *
	 * 
	 */
	void paint( Graphics g);

}
