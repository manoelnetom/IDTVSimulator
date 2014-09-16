package com.sun.dtv.lwuit.events;

public interface DataChangedListener {

	// Type value for added data in ListModel.
	public static final int ADDED = 1;
	// Type value for changed data in ListModel.
	public static final int CHANGED = 2;
	// Type value for removed data in ListModel.
	public static final int  REMOVED = 0;
	// Invoked when there was a change in the underlying model.

	public void dataChanged(int type, int index);
}
