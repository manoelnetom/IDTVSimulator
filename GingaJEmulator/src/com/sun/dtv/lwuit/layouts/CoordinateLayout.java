package com.sun.dtv.lwuit.layouts;

import com.sun.dtv.lwuit.Component;
import com.sun.dtv.lwuit.Container;
import com.sun.dtv.lwuit.geom.Dimension;

public class CoordinateLayout extends Layout
{
	private int width;
	private int height;

	/**
	 * This constructor accepts the relative width and height used to define the
	 * aspect ratio of the Container.
	 *
	 * 
	 * @param width - the width
	 * @param height - the height
	 */
	public CoordinateLayout(int width, int height)
	{
		this.width = width;
		this.height = height;
	}

	/**
	 * This constructor accepts a <A HREF="../../../../../com/sun/dtv/lwuit/geom/Dimension.html" title="class in com.sun.dtv.lwuit.geom"><CODE>Dimension</CODE></A> object
	 * in order to define the aspect ratio of the Container.
	 *
	 * 
	 * @param d - the aspect ratio of the Container as a Dimension object
	 */
	public CoordinateLayout(Dimension d)
	{
		this.width = d.getWidth();
		this.height = d.getHeight();
	}

	/**
	 * <B>Description copied from class: <CODE><A HREF="../../../../../com/sun/dtv/lwuit/layouts/Layout.html#layoutContainer(com.sun.dtv.lwuit.Container)">Layout</A></CODE></B></DD>
	 * Layout the given parent container children.
	 *
	 * 
	 * @param parent - the given parent container
	 * @see layoutContainer in class Layout
	 */
	public void layoutContainer(Container parent)
	{
		int numOfcomponents = parent.getComponentCount();
		int parentW = parent.getWidth();
		int parentH = parent.getHeight();
		
		for(int i=0; i< numOfcomponents; i++){
			Component cmp = parent.getComponentAt(i);
			Dimension d = cmp.getPreferredSize();

			if (width > 0 && height > 0) {
				if (cmp.getWidth() <= 0) {
					cmp.setWidth(d.getWidth());
				}

				if (cmp.getHeight() <= 0) {
					cmp.setHeight(d.getHeight());
				}

				int x = (cmp.getX() * parentW) / width;
				int y = (cmp.getY() * parentH) / height;
				int w = (cmp.getWidth());
				int h = (cmp.getHeight());
				
				cmp.setX(x);
				cmp.setY(y);
				cmp.setWidth(w);
				cmp.setHeight(h);
			}
		}
		
		width = parentW;
		height = parentH;
	}

	/**
	 * <B>Description copied from class: <CODE><A HREF="../../../../../com/sun/dtv/lwuit/layouts/Layout.html#getPreferredSize(com.sun.dtv.lwuit.Container)">Layout</A></CODE></B></DD>
	 * Returns the container preferred size.
	 *
	 * 
	 * @param parent - the parent container
	 * @return the container preferred size
	 * @see getPreferredSize in class Layout
	 */
	public Dimension getPreferredSize( Container parent)
	{
		Dimension retVal = new Dimension();
		int numOfcomponents = parent.getComponentCount();
		
		for(int i=0; i< numOfcomponents; i++){
			Component cmp = parent.getComponentAt(i);
			Dimension d = cmp.getPreferredSize();
			retVal.setWidth(Math.max(retVal.getWidth(), cmp.getX() + d.getWidth()));
			retVal.setHeight(Math.max(retVal.getHeight(), cmp.getY() + d.getHeight()));
		}
		
		return retVal;
	}

	/**
	 * <B>Description copied from class: <CODE><A HREF="../../../../../com/sun/dtv/lwuit/layouts/Layout.html#isOverlapSupported()">Layout</A></CODE></B></DD>
	 * This method returns true if the Layout allows Components to
	 * Overlap.
	 *
	 * 
	 * @return true if Components may intersect in this layout
	 * @see isOverlapSupported in class Layout
	 */
	public boolean isOverlapSupported()
	{
		return true;
	}

}
