package com.sun.dtv.lwuit.list;

import com.sun.dtv.lwuit.Component;
import com.sun.dtv.lwuit.List;

public interface ListCellRenderer
{
	/**
	 * Returns a component instance that is already set to render "value".
	 * While it is not a requirement many renderers often derive from a component
	 * (such as a label) and return "this".
	 * Notice that a null value for the value argument might be sent when
	 * refreshing the theme of the list.
	 *
	 * 
	 * @param list - the List object
	 * @param value - an Object
	 * @param index - the index
	 * @param isSelected - indicating whether the Component is selected
	 * @return a Component object
	 */
	Component getListCellRendererComponent( List list, Object value, int index, boolean isSelected);

	/**
	 * Returns a component instance that is painted under the currently focused renderer
	 * and is animated to provide smooth scrolling.
	 * When the selection moves, this component is drawn above/bellow the list items -
	 * it is recommended to give this component some level of transparency (see above code example).
	 * This method is optional an implementation
	 * can choose to return null.
	 *
	 * 
	 * @param list - the list
	 * @return a component instance that is painted under the currently focused renderer and is animated to provide smooth scrolling.
	 * @see Component.setSmoothScrolling(boolean)
	 */
	Component getListFocusComponent( List list);

}

