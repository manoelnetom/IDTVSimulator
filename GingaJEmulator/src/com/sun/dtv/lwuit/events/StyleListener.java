package com.sun.dtv.lwuit.events;

import com.sun.dtv.lwuit.plaf.Style;

public interface StyleListener
{
	/**
	 * Invoked to indicate a change in a propertyName of a Style.
	 *
	 * 
	 * @param propertyName - the property name that was changed
	 * @param source - The changed Style object
	 */
	void styleChanged( String propertyName, Style source);
}
