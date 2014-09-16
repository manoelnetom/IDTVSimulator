package com.sun.dtv.lwuit.events;

import com.sun.dtv.lwuit.Component;

public interface FocusListener
{
	/**
	 * Invoked when component gains focus.
	 *
	 * 
	 * @param cmp - the component that gains focus
	 */
	void focusGained( Component cmp);

	/**
	 * Invoked when component loses focus.
	 *
	 * 
	 * @param cmp - the component that lost focus
	 */
	void focusLost( Component cmp);
}
