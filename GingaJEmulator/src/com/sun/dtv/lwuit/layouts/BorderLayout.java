package com.sun.dtv.lwuit.layouts;

import com.sun.dtv.lwuit.Component;
import com.sun.dtv.lwuit.Container;
import com.sun.dtv.lwuit.geom.Dimension;

public class BorderLayout extends Layout
{
	/**
	 * The north layout constraint (top of container).
	 *
	 * 
	 */
	public static final String NORTH = "North";

	/**
	 * The south layout constraint (bottom of container).
	 *
	 *
	 * 
	 */
	public static final String SOUTH = "South";

	/**
	 * The center layout constraint (middle of container).
	 *
	 *
	 * 
	 */
	public static final String CENTER = "Center";

	/**
	 * The west layout constraint (left of container).
	 *
	 *
	 * 
	 */
	public static final String WEST = "West";

	/**
	 * The east layout constraint (right of container).
	 *
	 * 
	 * 
	 */
	public static final String EAST = "East";

	java.util.Map constraints;
	Component north;
	Component west;
	Component east;
	Component south;
	Component center;
	int hgap;
	int vgap;

	/**
	 * Creates a new instance of BorderLayout.
	 *
	 * 
	 * 
	 */
	public BorderLayout()
	{
		this.hgap = 0;
		this.vgap = 0;

		constraints = new java.util.HashMap();
	}

	/**
	 * <B>Description copied from class: <CODE><A HREF="../../../../../com/sun/dtv/lwuit/layouts/Layout.html#addLayoutComponent(java.lang.Object, com.sun.dtv.lwuit.Component, com.sun.dtv.lwuit.Container)">Layout</A></CODE></B></DD>
	 * Some layouts can optionally track the addition of elements with meta-data
	 * that allows the user to "hint" on object positioning.
	 *
	 * 
	 * @param name - optional meta data information, like alignment orientation
	 * @param comp - the added component to the layout
	 * @param c - the parent container
	 * @see addLayoutComponent in class Layout
	 */
	public void addLayoutComponent( Object constraints, Component comp, Container c)
	{
		Component previous = null;
		
		// synchronized (comp.getTreeLock()) {
			if ((constraints == null) || (constraints instanceof String)) {
				String name = (String) constraints;

				if (name == null) {
					name = CENTER;
				}
				
				// Assign the component to one of the known regions of the layout
				if (CENTER.equals(name)) {
					previous = center;
					center = comp;
				} else if (NORTH.equals(name)) {
					previous = north;
					north = comp;
				} else if (SOUTH.equals(name)) {
					previous = south;
					south = comp;
				} else if (EAST.equals(name)) {
					previous = east;
					east = comp;
				} else if (WEST.equals(name)) {
					previous = west;
					west = comp;
				} else {
					throw new IllegalArgumentException("cannot add to layout: unknown constraint: " + name);
				}
			}

		if (previous != null && previous != comp) {
			this.constraints.remove(comp);
			c.removeComponent(previous);
		} else {
			this.constraints.put(comp, constraints);
		}

		// layout container
		layoutContainer(c);
	}

	/**
	 * <B>Description copied from class: <CODE><A HREF="../../../../../com/sun/dtv/lwuit/layouts/Layout.html#removeLayoutComponent(com.sun.dtv.lwuit.Component)">Layout</A></CODE></B></DD>
	 * Removes the component from the layout this operation is only useful if the
	 * layout maintains references to components within it.
	 *
	 * 
	 * @param comp - the removed component from layout
	 * @see removeLayoutComponent in class Layout
	 */
	public void removeLayoutComponent(Component comp)
	{
		// synchronized (comp.getTreeLock()) {
			if (comp == center) {
				center = null;
			} else if (comp == north) {
				north = null;
			} else if (comp == south) {
				south = null;
			} else if (comp == east) {
				east = null;
			} else if (comp == west) {
				west = null;
			}
		// }
			
		this.constraints.remove(comp);
	}

	/**
	 * <B>Description copied from class: <CODE><A HREF="../../../../../com/sun/dtv/lwuit/layouts/Layout.html#getComponentConstraint(com.sun.dtv.lwuit.Component)">Layout</A></CODE></B></DD>
	 * Returns the component's constraint.
	 *
	 * 
	 * @param comp - the component
	 * @return the component's constraint
	 * @see getComponentConstraint in class Layout
	 */
	public Object getComponentConstraint(Component comp)
	{
		return constraints.get(comp);
	}

	/**
	 * Position the east/west component variables
	 */
	private void positionLeftRight(Component c, int targetWidth, int bottom, int top) {
		Dimension d = c.getPreferredSize();
		c.setWidth(Math.min(targetWidth, d.getWidth()));
		c.setHeight(bottom - top - c.getStyle().getMargin(Component.TOP) - c.getStyle().getMargin(Component.BOTTOM));
		c.setY(top + c.getStyle().getMargin(Component.TOP));
	}

	/**
	 * Position the north/south component variables
	 */
	private void positionTopBottom(Component c, int right, int left, int targetHeight) {
		Dimension d = c.getPreferredSize();
		c.setWidth(right - left - c.getStyle().getMargin(Component.LEFT) - c.getStyle().getMargin(Component.RIGHT));
		c.setHeight(Math.min(targetHeight, d.getHeight())); 
		c.setX(left + c.getStyle().getMargin(Component.LEFT));
	}

	/**
	 * <B>Description copied from class: <CODE><A HREF="../../../../../com/sun/dtv/lwuit/layouts/Layout.html#layoutContainer(com.sun.dtv.lwuit.Container)">Layout</A></CODE></B></DD>
	 * Layout the given parent container children.
	 *
	 * 
	 * @param target - the given parent container
	 * @see layoutContainer in class Layout
	 */
	public void layoutContainer(Container target)
	{
		// synchronized (target.getTreeLock()) {
			int top = target.getStyle().getPadding(Component.TOP);
			int bottom = target.getLayoutHeight() - target.getBottomGap() - target.getStyle().getPadding(Component.BOTTOM);
			int left = target.getStyle().getPadding(Component.LEFT);
			int right = target.getLayoutWidth() - target.getSideGap() - target.getStyle().getPadding(Component.RIGHT);
			int targetWidth = target.getWidth();
			int targetHeight = target.getHeight();
			
			if (north != null) {
				Component c = north;
				positionTopBottom(c, right, left, targetHeight);
				c.setY(top + c.getStyle().getMargin(Component.TOP));
				top += (c.getHeight() + c.getStyle().getMargin(Component.TOP) + c.getStyle().getMargin(Component.BOTTOM));
			}

			if (south != null) {
				Component c = south;
				positionTopBottom(c, right, left, targetHeight);
				c.setY(bottom - c.getHeight() - c.getStyle().getMargin(Component.TOP));
				bottom -= (c.getHeight() + c.getStyle().getMargin(Component.TOP) + c.getStyle().getMargin(Component.BOTTOM));
			}

			if (east != null) {
				Component c = east;
				positionLeftRight(east, targetWidth, bottom, top);
				c.setX(right - c.getWidth() - c.getStyle().getMargin(Component.LEFT));
				right -= (c.getWidth() + c.getStyle().getMargin(Component.LEFT) + c.getStyle().getMargin(Component.RIGHT));
			}

			if (west != null) {
				Component c = west;
				positionLeftRight(west, targetWidth, bottom, top);
				c.setX(left + c.getStyle().getMargin(Component.LEFT));
				left += (c.getWidth() + c.getStyle().getMargin(Component.LEFT) + c.getStyle().getMargin(Component.RIGHT));
			}

			if (center != null) {
				Component c = center;
				c.setWidth(right - left - c.getStyle().getMargin(Component.LEFT) - c.getStyle().getMargin(Component.RIGHT));
				c.setHeight(bottom - top - c.getStyle().getMargin(Component.TOP) - c.getStyle().getMargin(Component.BOTTOM)); //verify I want to use the remaining size
				c.setX(left + c.getStyle().getMargin(Component.LEFT));
				c.setY(top + c.getStyle().getMargin(Component.TOP));
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
	public Dimension getPreferredSize(Container parent)
	{
		// synchronized (target.getTreeLock()) {
			Dimension dim = new Dimension(0, 0);

			if (east != null) {
				Dimension d = east.getPreferredSize();
				dim.setWidth(d.getWidth() + east.getStyle().getMargin(Component.LEFT) + east.getStyle().getMargin(Component.RIGHT));
				dim.setHeight(Math.max(d.getHeight() + east.getStyle().getMargin(Component.TOP) + east.getStyle().getMargin(Component.BOTTOM), dim.getHeight()));
			}

			if (west != null) {
				Dimension d = west.getPreferredSize();
				dim.setWidth(dim.getWidth() + d.getWidth() + west.getStyle().getMargin(Component.LEFT) + west.getStyle().getMargin(Component.RIGHT));
				dim.setHeight(Math.max(d.getHeight() + west.getStyle().getMargin(Component.TOP) + west.getStyle().getMargin(Component.BOTTOM), dim.getHeight()));
			}

			if (center != null) {
				Dimension d = center.getPreferredSize();
				dim.setWidth(dim.getWidth() + d.getWidth() + center.getStyle().getMargin(Component.LEFT) + center.getStyle().getMargin(Component.RIGHT));
				dim.setHeight(Math.max(d.getHeight() + center.getStyle().getMargin(Component.TOP) + center.getStyle().getMargin(Component.BOTTOM), dim.getHeight()));
			}

			if (north != null) {
				Dimension d = north.getPreferredSize();
				dim.setWidth(Math.max(d.getWidth() + north.getStyle().getMargin(Component.LEFT) + north.getStyle().getMargin(Component.RIGHT), dim.getWidth()));
				dim.setHeight(dim.getHeight() + d.getHeight() + north.getStyle().getMargin(Component.TOP) + north.getStyle().getMargin(Component.BOTTOM));
			}

			if (south != null) {
				Dimension d = south.getPreferredSize();
				dim.setWidth(Math.max(d.getWidth() + south.getStyle().getMargin(Component.LEFT) + south.getStyle().getMargin(Component.RIGHT), dim.getWidth()));
				dim.setHeight(dim.getHeight() + d.getHeight() + south.getStyle().getMargin(Component.TOP) + south.getStyle().getMargin(Component.BOTTOM));
			}

			dim.setWidth(dim.getWidth() + parent.getStyle().getPadding(Component.LEFT) + parent.getStyle().getPadding(Component.RIGHT));
			dim.setHeight(dim.getHeight() + parent.getStyle().getPadding(Component.TOP) + parent.getStyle().getPadding(Component.BOTTOM));

			return dim;
		// }
	}

}
