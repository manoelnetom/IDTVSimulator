package com.sun.dtv.lwuit.layouts;

import com.sun.dtv.lwuit.Component;
import com.sun.dtv.lwuit.Container;
import com.sun.dtv.lwuit.geom.Dimension;
import com.sun.dtv.lwuit.plaf.Style;

public class FlowLayout extends Layout
{
	/**
	 * This value indicates that each row of components
	 * should be left-justified.
	 * @since   JDK1.0
	 */
	public static final int LEFT = 0;
	/**
	 * This value indicates that each row of components
	 * should be centered.
	 * @since   JDK1.0
	 */
	public static final int CENTER = 1;
	/**
	 * This value indicates that each row of components
	 * should be right-justified.
	 * @since   JDK1.0
	 */
	public static final int RIGHT = 2;

	int hgap;
	int vgap;
	int align;

	/**
	 * Creates a new instance of FlowLayout with left alignment.
	 *
	 * 
	 */
	public FlowLayout()
	{
		this.align = CENTER;
		this.hgap = 4;
		this.vgap = 4;
	}

	/**
	 * Creates a new instance of FlowLayout with the given orientation one of
	 * LEFT, RIGHT or CENTER.
	 *
	 * 
	 * @param orientation - the orientation value
	 */
	public FlowLayout(int orientation)
	{
		this.align = orientation;
		this.hgap = 4;
		this.vgap = 4;
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
		int x = parent.getStyle().getPadding(Component.LEFT);
		int width = parent.getLayoutWidth() - parent.getSideGap() - parent.getStyle().getPadding(Component.RIGHT) - x;
		int y = parent.getStyle().getPadding(Component.TOP);
		int rowH=0;
		int start=0;
		int maxComponentWidth = width - parent.getStyle().getPadding(Component.LEFT);
		int maxComponentHeight = parent.getLayoutHeight() - y - parent.getStyle().getPadding(Component.BOTTOM);
		
		if(maxComponentHeight > parent.getHeight()) {
			maxComponentHeight = parent.getHeight();
		}
		
		int numOfcomponents = parent.getComponentCount();
		
		for(int i=0; i< numOfcomponents; i++){
			Component cmp = parent.getComponentAt(i);
			Dimension d = cmp.getPreferredSize();
			Style style = cmp.getStyle();
			int marginY = style.getMargin(Component.TOP) + style.getMargin(Component.BOTTOM);
			int marginX = style.getMargin(Component.LEFT) + style.getMargin(Component.RIGHT);
			cmp.setWidth(Math.min(maxComponentWidth - marginX, d.getWidth()));
			cmp.setHeight(Math.min(d.getHeight(), maxComponentHeight - marginY));

			if((x == parent.getStyle().getPadding(Component.LEFT)) || ( x + d.getWidth() < width) ) {
				cmp.setX(x + cmp.getStyle().getMargin(Component.LEFT));
				cmp.setY(y + cmp.getStyle().getMargin(Component.TOP));
				x += d.getWidth() + cmp.getStyle().getMargin(Component.RIGHT); 
				rowH = Math.max(rowH, d.getHeight() + cmp.getStyle().getMargin(Component.TOP)+ cmp.getStyle().getMargin(Component.BOTTOM)); 
			} else {
				moveComponents(parent, parent.getStyle().getPadding(Component.LEFT), y, width - x, rowH, start, i);
				x = parent.getStyle().getPadding(Component.LEFT);
				y += rowH;
				cmp.setX(x + cmp.getStyle().getMargin(Component.LEFT));
				cmp.setY(y + cmp.getStyle().getMargin(Component.TOP));
				rowH = d.getHeight()+ cmp.getStyle().getMargin(Component.TOP)+ cmp.getStyle().getMargin(Component.BOTTOM); 
				x += d.getWidth()+ cmp.getStyle().getMargin(Component.RIGHT); 
				start = i;
			}
		}

		moveComponents(parent, parent.getStyle().getPadding(Component.LEFT), y, width - x, rowH, start, numOfcomponents);
	}

	/**
	 * Centers the elements in the specified row, if there is any slack.
	 * @param target the component which needs to be moved
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param width the width dimensions
	 * @param height the height dimensions
	 * @param rowStart the beginning of the row
	 * @param rowEnd the the ending of the row
	 */
	private void moveComponents(Container target, int x, int y, int width, int height, int rowStart, int rowEnd) {
		// synchronized (target.getTreeLock()) {
			switch (align) {
				case LEFT:
					break;
				case CENTER:
					x += width / 2;
					break;
				case RIGHT:
					x += width;
					break;
			}

			for (int i = rowStart; i < rowEnd; i++) {
				Component m = target.getComponentAt(i);
				Dimension d = m.getPreferredSize();

				m.setX(m.getX()+ x + m.getStyle().getMargin(Component.LEFT));
				m.setY(y + m.getStyle().getMargin(Component.TOP) + (height - d.getHeight()) / 2);
			}
		// }
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
			Dimension d = cmp.getPreferredSize();
			height = Math.max(height, d.getHeight() + cmp.getStyle().getMargin(Component.TOP)+ cmp.getStyle().getMargin(Component.BOTTOM));
			width += d.getWidth()+ cmp.getStyle().getMargin(Component.RIGHT)+ cmp.getStyle().getMargin(Component.LEFT);
		}
		
		return new Dimension(width + parent.getStyle().getPadding(Component.LEFT)+ parent.getStyle().getPadding(Component.RIGHT), height + parent.getStyle().getPadding(Component.TOP)+ parent.getStyle().getPadding(Component.BOTTOM));
	}

}
