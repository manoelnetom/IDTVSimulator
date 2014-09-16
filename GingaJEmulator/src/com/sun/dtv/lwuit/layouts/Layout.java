package com.sun.dtv.lwuit.layouts;

import com.sun.dtv.lwuit.Component;
import com.sun.dtv.lwuit.Container;
import com.sun.dtv.lwuit.geom.Dimension;

public abstract class Layout extends Object
{
	public Layout()
	{
	}

	/**
	 * Layout the given parent container children.
	 *
	 * 
	 * @param parent - the given parent container
	 */
	public abstract void layoutContainer( Container parent);

	/**
	 * Returns the container preferred size.
	 *
	 * 
	 * @param parent - the parent container
	 * @return the container preferred size
	 */
	public abstract Dimension getPreferredSize( Container parent);

	/**
	 * Some layouts can optionally track the addition of elements with meta-data
	 * that allows the user to "hint" on object positioning.
	 *
	 * 
	 * @param value - optional meta data information, like alignment orientation
	 * @param comp - the added component to the layout
	 * @param c - the parent container
	 */
	public void addLayoutComponent( Object value, Component comp, Container c)
	{
		throw new IllegalStateException("Layout doesn't support adding with arguments: " + getClass().getName());
	}

	/**
	 * Removes the component from the layout this operation is only useful if the
	 * layout maintains references to components within it.
	 *
	 * 
	 * @param comp - the removed component from layout
	 */
	public void removeLayoutComponent(Component comp)
	{
	}

	/**
	 * Returns the component's constraint.
	 *
	 * 
	 * @param comp - the component
	 * @return the component's constraint
	 */
	public Object getComponentConstraint( Component comp)
	{
		return null;
	}

	/**
	 * This method returns true if the Layout allows Components to
	 * Overlap.
	 * 
	 * 
	 * @return true if Components may intersect in this layout
	 */
	public boolean isOverlapSupported()
	{
		return false;
	}

}
