package com.sun.dtv.lwuit.painter;

import com.sun.dtv.lwuit.Form;
import com.sun.dtv.lwuit.Graphics;
import com.sun.dtv.lwuit.Painter;
import com.sun.dtv.lwuit.geom.Rectangle;

public class PainterChain extends Object implements Painter
{
	private Painter[] chain;

	/**
	 * Create a new painter chain which will paint all of the elements in the chain
	 * in sequence from 0 to the last element.
	 *
	 * 
	 * @param chain - the chain elements
	 */
	public PainterChain( Painter[] chain)
	{
		this.chain = chain;
	}

	/**
	 * Creates a new chain based on the existing chain with the new element added
	 * at the end.
	 *
	 * 
	 * @param p - new painter
	 * @return new chain element
	 */
	public PainterChain addPainter( Painter p)
	{
		if(chain.length != 0) {
			Painter[] newChain = new Painter[chain.length + 1];
			System.arraycopy(chain, 0, newChain, 0, chain.length);
			newChain[chain.length] = p;
		
			return new PainterChain(newChain);
		}

		return new PainterChain(new Painter[] {p});
	}

	/**
	 * Creates a new chain based on the existing chain with the new element added
	 * at the beginning.
	 *
	 * 
	 * @param p - new painter
	 * @return new chain element
	 */
	public PainterChain prependPainter( Painter p)
	{
		Painter[] newChain = new Painter[chain.length + 1];
		System.arraycopy(chain, 1, newChain, 0, chain.length);
		newChain[0] = p;

		return new PainterChain(newChain);
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
	public void paint( Graphics g, Rectangle rect)
	{
		for(int iter = 0 ; iter < chain.length ;  iter++) {
			chain[iter].paint(g, rect);
		}
	}

	/**
	 * Installs a glass pane on the given form making sure to make it a painter
	 * chain only if required by existing painter.
	 *
	 * 
	 * @param f - form on which to install the chain
	 * @param p - painter to install
	 */
	public static void installGlassPane( Form f, Painter p)
	{
		Painter existing = f.getGlassPane();
		
		if(existing == null) {
			f.setGlassPane(p);
			return;
		}
		
		if(existing instanceof PainterChain) {
			((PainterChain)existing).addPainter(p);
		} else {
			PainterChain pc = new PainterChain(new Painter[] {existing, p});
			f.setGlassPane(pc);
		}
	}

}
