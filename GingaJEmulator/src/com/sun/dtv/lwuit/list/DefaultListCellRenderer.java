package com.sun.dtv.lwuit.list;

import com.sun.dtv.lwuit.Component;
import com.sun.dtv.lwuit.Label;
import com.sun.dtv.lwuit.List;
import com.sun.dtv.lwuit.Command;
import com.sun.dtv.lwuit.plaf.UIManager;

import java.util.Hashtable;
import com.sun.dtv.ui.ViewOnlyComponent;
public class DefaultListCellRenderer extends Label implements ListCellRenderer
{
	private boolean showNumbers;
	private int selectionTransparency;

	/**
	 * Creates a new instance of DefaultCellRenderer.
	 *
	 * 
	 */
	public DefaultListCellRenderer()
	{
		super("");
		setUIID("DefaultListCellRenderer");
		this.selectionTransparency = 100;

		setCellRenderer(true);
		setEndsWith3Points(false);
		showNumbers = true;
		setVerticalAlignment(ViewOnlyComponent.VERTICAL_ALIGN_CENTER);

	}

	/**
	 * Creates a new instance of DefaultCellRenderer.
	 *
	 * 
	 * @param showNumbers - indicates whether numbers are shown
	 */
	public DefaultListCellRenderer(boolean showNumbers)
	{
		this();

		this.showNumbers = showNumbers;
	}

	/**
	 * <B>Description copied from interface: <CODE><A HREF="../../../../../com/sun/dtv/lwuit/list/ListCellRenderer.html#getListCellRendererComponent(com.sun.dtv.lwuit.List, java.lang.Object, int, boolean)">ListCellRenderer</A></CODE></B></DD>
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
	 * @see getListCellRendererComponent in interface ListCellRenderer
	 */
	public Component getListCellRendererComponent(List list, Object value, int index, boolean isSelected)
	{
		if(showNumbers) {
			String text = "" + value;
			Hashtable t =  UIManager.getInstance().getResourceBundle();
			if(t != null && value != null) {
				Object o = t.get(value.toString());
				if(o != null) {
					text = (String)o;
				}
			} 
			setText("" + (index + 1) + ". " + text);
		} else {
			if(value != null) {
				setText(value.toString());
			} else {
				setText("null");
			}
		}

		if (isSelected) {
			setFocus(true);
			getStyle().setBgTransparency(selectionTransparency);
		} else {
			setFocus(false);
			getStyle().setBgTransparency(0xff);
		}

		if(value instanceof Command) {
			setIcon(((Command)value).getIcon());
		}

		return this;
	}

	/**
	 * <B>Description copied from interface: <CODE><A HREF="../../../../../com/sun/dtv/lwuit/list/ListCellRenderer.html#getListFocusComponent(com.sun.dtv.lwuit.List)">ListCellRenderer</A></CODE></B></DD>
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
	 * @see getListFocusComponent in interface ListCellRenderer
	 * @see Component.setSmoothScrolling(boolean)
	 */
	public Component getListFocusComponent( List list)
	{
		setText("");
		setIcon(null);
		setFocus(true);
		
		getStyle().setBgTransparency(selectionTransparency);

		return this;
	}

	/**
	 * Overriden to do nothing and remove a performance issue where renderer changes
	 * perform needless repaint calls.
	 *
	 * 
	 * @see repaint in class Component
	 */
	public void repaint()
	{
	}

	/**
	 * Indicate whether numbering should exist for the default cell renderer.
	 *
	 * 
	 * 
	 * @return true if numbering should exist for the default cell renderer
	 */
	public boolean isShowNumbers()
	{
		return showNumbers;
	}

	/**
	 * Indicate whether numbering should exist for the default cell renderer.
	 *
	 * 
	 * @param showNumbers - indicates if numbering should exist for the default  cell renderer
	 */
	public void setShowNumbers(boolean showNumbers)
	{
		this.showNumbers = showNumbers;
	}

	/**
	 * The background transparency factor to apply to the selection focus.
	 *
	 * 
	 * 
	 * @return the background transparency factor
	 * @see setSelectionTransparency(int)
	 */
	public int getSelectionTransparency()
	{
		return this.selectionTransparency;
	}

	/**
	 * The background transparency factor to apply to the selection focus.
	 *
	 * 
	 * @param selectionTransparency - the background transparency factor
	 * @see getSelectionTransparency()
	 */
	public void setSelectionTransparency(int selectionTransparency)
	{
		this.selectionTransparency = selectionTransparency;
	}

}
