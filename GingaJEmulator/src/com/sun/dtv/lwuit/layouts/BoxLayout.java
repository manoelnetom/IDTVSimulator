package com.sun.dtv.lwuit.layouts;

import com.sun.dtv.lwuit.Component;
import com.sun.dtv.lwuit.Container;
import com.sun.dtv.lwuit.geom.Dimension;

public class BoxLayout extends Layout
{
	/**
	 * Horizontal layout where components are arranged from left to right.
	 *
	 *
	 * 
	 */
	public static final int X_AXIS = 1;

	/**
	 * Vertical layout where components are arranged from top to bottom.
	 *
	 *
	 * 
	 */
	public static final int Y_AXIS = 2;

	private int axis;

	/**
	 * Creates a new instance of BoxLayout.
	 *
	 * 
	 * @param axis - the axis to lay out components along.  Can be: BoxLayout.X_AXIS or BoxLayout.Y_AXIS.
	 */
	public BoxLayout(int axis)
	{
		this.axis = axis;
	}

	/**
	 * <B>Description copied from class: <CODE><A HREF="../../../../../com/sun/dtv/lwuit/layouts/Layout.html#layoutContainer(com.sun.dtv.lwuit.Container)">Layout</A></CODE></B></DD>
	 * Layout the given parent container children.
	 *
	 * 
	 * @param parent - the given parent container
	 * @see layoutContainer in class Layout
	 */
	public void layoutContainer( Container parent)
	{
		int width = parent.getLayoutWidth() - parent.getSideGap() - parent.getStyle().getPadding(Component.RIGHT) - parent.getStyle().getPadding(Component.LEFT);
		int height = parent.getLayoutHeight() - parent.getBottomGap() - parent.getStyle().getPadding(Component.BOTTOM) - parent.getStyle().getPadding(Component.TOP);
		int x = parent.getStyle().getPadding(Component.LEFT);
		int y = parent.getStyle().getPadding(Component.TOP);
		int numOfcomponents = parent.getComponentCount();

		for(int i=0; i< numOfcomponents; i++){
			Component cmp = parent.getComponentAt(i);
			
			if(axis == Y_AXIS){
				Dimension d = cmp.getPreferredSize();
				int cmpBottom = height;
				int cmpH = d.getHeight();
				y += cmp.getStyle().getMargin(Component.TOP);
				if(y >= cmpBottom){
					cmpH = 0;
				}else if(y + cmpH > cmpBottom){
					cmpH = cmpBottom - y - cmp.getStyle().getMargin(Component.BOTTOM);
				}
				if(cmpH > parent.getHeight()) {
					cmpH = parent.getHeight();
				}
				cmp.setWidth(width - cmp.getStyle().getMargin(Component.LEFT) - cmp.getStyle().getMargin(Component.RIGHT));
				cmp.setHeight(cmpH);
				cmp.setX(x + cmp.getStyle().getMargin(Component.LEFT));
				cmp.setY(y);
				y += cmp.getHeight() + cmp.getStyle().getMargin(Component.BOTTOM);
			} else {
				Dimension d = cmp.getPreferredSize();
				int cmpRight = width;
				int cmpW = d.getWidth();
				x += cmp.getStyle().getMargin(Component.LEFT);
				if(x >= cmpRight){
					cmpW = 0;
				}else if(x + cmpW > cmpRight){
					cmpW = cmpRight - x - cmp.getStyle().getMargin(Component.RIGHT);
				}
				if(cmpW > parent.getWidth()) {
					cmpW = parent.getWidth();
				}
				cmp.setWidth(cmpW);
				cmp.setHeight(height- cmp.getStyle().getMargin(Component.TOP) - cmp.getStyle().getMargin(Component.BOTTOM));
				cmp.setX(x);
				cmp.setY(y + cmp.getStyle().getMargin(Component.TOP));
				x += cmp.getWidth() + cmp.getStyle().getMargin(Component.RIGHT);
			}
		}
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
		int width = 0;
		int height = 0;
		int numOfcomponents = parent.getComponentCount();
		
		for(int i=0; i< numOfcomponents; i++){
			Component cmp = parent.getComponentAt(i);
			if(axis == Y_AXIS){
				Dimension d = cmp.getPreferredSize();
				height += d.getHeight() + cmp.getStyle().getMargin(Component.TOP) + cmp.getStyle().getMargin(Component.BOTTOM);
				width = Math.max(width , d.getWidth()+ cmp.getStyle().getMargin(Component.LEFT) + cmp.getStyle().getMargin(Component.RIGHT));
			}else{
				Dimension d = cmp.getPreferredSize();
				height = Math.max(height, d.getHeight() + cmp.getStyle().getMargin(Component.TOP) + cmp.getStyle().getMargin(Component.BOTTOM));
				width += d.getWidth() + cmp.getStyle().getMargin(Component.LEFT) + cmp.getStyle().getMargin(Component.RIGHT);
			}
		}
		
		return new Dimension(width + parent.getStyle().getPadding(Component.LEFT)+ parent.getStyle().getPadding(Component.RIGHT), height + parent.getStyle().getPadding(Component.TOP)+ parent.getStyle().getPadding(Component.BOTTOM));

	}

}
