package com.sun.dtv.lwuit.events;

public interface SelectionListener
{
	/**
	 * Indicates the selection changed in the underlying list model.
	 *
	 * 
	 * @param oldSelected - old selected index in list model
	 * @param newSelected - new selected index in list model
	 */
	void selectionChanged(int oldSelected, int newSelected);
}
