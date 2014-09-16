package com.sun.dtv.lwuit.list;

import com.sun.dtv.lwuit.events.DataChangedListener;
import com.sun.dtv.lwuit.events.SelectionListener;

import java.util.Vector;

public class DefaultListModel implements ListModel
{
	private Vector dataListener;
	private Vector selectionListener;
	private Vector items;

	private int selectedIndex = -1;

	/**
	 * Creates a new instance of DefaultListModel.
	 *
	 * 
	 */
	public DefaultListModel()
	{
		this(new Vector());
	}

	/**
	 * Creates a new instance of DefaultListModel.
	 *
	 * 
	 * @param items - the list items
	 */
	public DefaultListModel(Vector items)
	{
		this.dataListener = new Vector();
		this.items = items;
		this.selectionListener = new Vector();
		if(items.size() > 0){
			selectedIndex = 0;
		}
	}

	/**
	 * Creates a new instance of DefaultListModel.
	 *
	 * 
	 * @param items - the list items
	 */
	public DefaultListModel(Object[] items)
	{
		this(createVector(items));
	}

	private static Vector createVector(Object[] items) {
		if (items == null) {
			items = new Object[] {""};
		}
		
		Vector vec = new Vector(items.length);
		
		for(int iter = 0 ; iter < items.length ; iter++) {
			vec.addElement(items[iter]);
		}

		return vec;
	}

	/**
	 * <B>Description copied from interface: <CODE><A HREF="../../../../../com/sun/dtv/lwuit/list/ListModel.html#getItemAt(int)">ListModel</A></CODE></B></DD>
	 * Returns the item at the given offset.
	 *
	 * 
	 * @param index - an index into this list
	 * @return the item at the specified index
	 * @see getItemAt in interface ListModel
	 */
	public Object getItemAt(int index)
	{
		if(index < getSize() && index >= 0){
			return items.elementAt(index);
		}

		return null;

	}

	/**
	 * <B>Description copied from interface: <CODE><A HREF="../../../../../com/sun/dtv/lwuit/list/ListModel.html#getSize()">ListModel</A></CODE></B></DD>
	 * Returns the number of items in the list.
	 *
	 * 
	 * @return the number of items in the list
	 * @see getSize in interface ListModel
	 */
	public int getSize()
	{
		return items.size();
	}

	/**
	 * <B>Description copied from interface: <CODE><A HREF="../../../../../com/sun/dtv/lwuit/list/ListModel.html#getSelectedIndex()">ListModel</A></CODE></B></DD>
	 * Returns the selected list offset.
	 *
	 * 
	 * @return the selected list index
	 * @see getSelectedIndex in interface ListModel
	 * @see ListModel.setSelectedIndex(int)
	 */
	public int getSelectedIndex()
	{
		return this.selectedIndex;
	}

	/**
	 * <B>Description copied from interface: <CODE><A HREF="../../../../../com/sun/dtv/lwuit/list/ListModel.html#addItem(java.lang.Object)">ListModel</A></CODE></B></DD>
	 * Adds the specified item to the end of this list.
	 * An optional operation for mutable lists, it can throw an unsupported operation
	 * exception if a list model is not mutable.
	 *
	 * 
	 * @param item - the item to be added
	 * @see addItem in interface ListModel
	 * @see ListModel.removeItem(int)
	 */
	public void addItem(Object item)
	{
//		items.addElement(item);
                items.add(0, item);
		fireDataChangedEvent(DataChangedListener.ADDED, items.size());
	}

	/**
	 * Change the item at the given index.
	 *
	 * 
	 * @param index - the index
	 * @param item - the object to be inserted as the new item at the specified  position
	 */
	public void setItem(int index, Object item)
	{
		items.setElementAt(item, index);
		fireDataChangedEvent(DataChangedListener.CHANGED, index);
	}

	/**
	 * Adding an item to list at given index.
	 *
	 * 
	 * @param item - - the item to add
	 * @param index - - the index position in the list
	 */
	public void addItemAtIndex( Object item, int index)
	{
		if (index <= items.size()) {
			items.insertElementAt(item, index);
			fireDataChangedEvent(DataChangedListener.ADDED, index);
		}
	}

	/**
	 * <B>Description copied from interface: <CODE><A HREF="../../../../../com/sun/dtv/lwuit/list/ListModel.html#removeItem(int)">ListModel</A></CODE></B></DD>
	 * Removes the item at the specified position in this list.
	 *
	 * 
	 * @param index - the index of the item to removed
	 * @see removeItem in interface ListModel
	 * @see ListModel.addItem(java.lang.Object)
	 */
	public void removeItem(int index)
	{
		if(index < getSize() && index >= 0){
			items.removeElementAt(index);
			
			if(index != 0){
				setSelectedIndex(index - 1);
			}

			fireDataChangedEvent(DataChangedListener.REMOVED, index);
		}
	}

	/**
	 * Removes all elements from the model.
	 *
	 * 
	 */
	public void removeAll()
	{
		while(getSize() > 0) {
			removeItem(0);
		}
	}

	/**
	 * <B>Description copied from interface: <CODE><A HREF="../../../../../com/sun/dtv/lwuit/list/ListModel.html#setSelectedIndex(int)">ListModel</A></CODE></B></DD>
	 * Sets the selected list offset can be set to -1 to clear selection.
	 *
	 * 
	 * @param index - an index into this list
	 * @see setSelectedIndex in interface ListModel
	 * @see ListModel.getSelectedIndex()
	 */
	public void setSelectedIndex(int index)
	{
		int oldIndex = selectedIndex;
		
		this.selectedIndex = index;

		fireSelectionEvent(oldIndex, selectedIndex);
	}

	/**
	 * <B>Description copied from interface: <CODE><A HREF="../../../../../com/sun/dtv/lwuit/list/ListModel.html#addDataChangedListener(com.sun.dtv.lwuit.events.DataChangedListener)">ListModel</A></CODE></B></DD>
	 * Invoked to indicate interest in future change events.
	 *
	 * 
	 * @param l - a data changed listener
	 * @see addDataChangedListener in interface ListModel
	 * @see ListModel.removeDataChangedListener(com.sun.dtv.lwuit.events.DataChangedListener)
	 */
	public void addDataChangedListener(DataChangedListener l)
	{
		dataListener.add(l);
	}

	/**
	 * <B>Description copied from interface: <CODE><A HREF="../../../../../com/sun/dtv/lwuit/list/ListModel.html#removeDataChangedListener(com.sun.dtv.lwuit.events.DataChangedListener)">ListModel</A></CODE></B></DD>
	 * Invoked to indicate no further interest in future change events.
	 *
	 * 
	 * @param l - a data changed listener
	 * @see removeDataChangedListener in interface ListModel
	 * @see ListModel.addDataChangedListener(com.sun.dtv.lwuit.events.DataChangedListener)
	 */
	public void removeDataChangedListener(DataChangedListener l)
	{
		 dataListener.remove(l);
	}

	/**
	 * <B>Description copied from interface: <CODE><A HREF="../../../../../com/sun/dtv/lwuit/list/ListModel.html#addSelectionListener(com.sun.dtv.lwuit.events.SelectionListener)">ListModel</A></CODE></B></DD>
	 * Invoked to indicate interest in future selection events.
	 *
	 * 
	 * @param l - a selection listener
	 * @see addSelectionListener in interface ListModel
	 * @see ListModel.removeSelectionListener(com.sun.dtv.lwuit.events.SelectionListener)
	 */
	public void addSelectionListener(SelectionListener l)
	{
		 selectionListener.add(l);
	}

	/**
	 * <B>Description copied from interface: <CODE><A HREF="../../../../../com/sun/dtv/lwuit/list/ListModel.html#removeSelectionListener(com.sun.dtv.lwuit.events.SelectionListener)">ListModel</A></CODE></B></DD>
	 * Invoked to indicate no further interest in future selection events.
	 *
	 * 
	 * @param l - a selection listener
	 * @see removeSelectionListener in interface ListModel
	 * @see ListModel.addSelectionListener(com.sun.dtv.lwuit.events.SelectionListener)
	 */
	public void removeSelectionListener(SelectionListener l)
	{
		 selectionListener.remove(l);
	}
	    
	private void fireDataChangedEvent(final int status, final int index){
		for (java.util.Enumeration e=dataListener.elements(); e.hasMoreElements(); ) {
			DataChangedListener l = (DataChangedListener)e.nextElement();

			l.dataChanged(status, index);
		}
	}

	private void fireSelectionEvent(int oldIndex, int newIndex){
		for (java.util.Enumeration e=selectionListener.elements(); e.hasMoreElements(); ) {
			SelectionListener l = (SelectionListener)e.nextElement();

			l.selectionChanged(oldIndex, newIndex);
		}
	}

}
