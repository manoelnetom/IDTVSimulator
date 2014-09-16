package com.sun.dtv.lwuit.list;

import com.sun.dtv.lwuit.events.DataChangedListener;
import com.sun.dtv.lwuit.events.SelectionListener;

public interface ListModel
{
	/**
	 * Returns the item at the given offset.
	 *
	 * 
	 * @param index - an index into this list
	 * @return the item at the specified index
	 */
	Object getItemAt(int index);

	/**
	 * Returns the number of items in the list.
	 *
	 * 
	 * 
	 * @return the number of items in the list
	 */
	int getSize();

	/**
	 * Returns the selected list offset.
	 *
	 * 
	 * 
	 * @return the selected list index
	 * @see setSelectedIndex(int)
	 */
	int getSelectedIndex();

	/**
	 * Sets the selected list offset can be set to -1 to clear selection.
	 *
	 * 
	 * @param index - an index into this list
	 * @see getSelectedIndex()
	 */
	void setSelectedIndex(int index);

	/**
	 * Invoked to indicate interest in future change events.
	 *
	 * 
	 * @param l - a data changed listener
	 * @see removeDataChangedListener(com.sun.dtv.lwuit.events.DataChangedListener)
	 */
	void addDataChangedListener( DataChangedListener l);

	/**
	 * Invoked to indicate no further interest in future change events.
	 *
	 * 
	 * @param l - a data changed listener
	 * @see addDataChangedListener(com.sun.dtv.lwuit.events.DataChangedListener)
	 */
	void removeDataChangedListener( DataChangedListener l);

	/**
	 * Invoked to indicate interest in future selection events.
	 *
	 * 
	 * @param l - a selection listener
	 * @see removeSelectionListener(com.sun.dtv.lwuit.events.SelectionListener)
	 */
	void addSelectionListener( SelectionListener l);

	/**
	 * Invoked to indicate no further interest in future selection events.
	 *
	 * 
	 * @param l - a selection listener
	 * @see addSelectionListener(com.sun.dtv.lwuit.events.SelectionListener)
	 */
	void removeSelectionListener( SelectionListener l);

	/**
	 * Adds the specified item to the end of this list.
	 * An optional operation for mutable lists, it can throw an unsupported operation
	 * exception if a list model is not mutable.
	 *
	 * 
	 * @param item - the item to be added
	 * @see removeItem(int)
	 */
	void addItem( Object item);

	/**
	 * Removes the item at the specified position in this list.
	 *
	 * 
	 * @param index - the index of the item to removed
	 * @see addItem(java.lang.Object)
	 */
	void removeItem(int index);

}
