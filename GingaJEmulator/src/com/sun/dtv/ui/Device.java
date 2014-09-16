package com.sun.dtv.ui;

public class Device extends Object
{
	private static Device instance = new Device();

	/**
	 * There is no intention to enable users to create a device.
	 * The device instance should be retrieved from the DTV API implementation
	 * on a particular device by calling <A HREF="../../../../com/sun/dtv/ui/Device.html#getInstance()"><CODE>getInstance()</CODE></A>.
	 * 
	 */
	protected Device()
	{
	}

	/**
	 * Retrieving the <A HREF="../../../../com/sun/dtv/ui/Device.html" title="class in com.sun.dtv.ui"><CODE>Device</CODE></A> instance representing the
	 * used TV device from the implementation.
	 * 
	 * @return the device instance representing the currently used TV device
	 */
	public static Device getInstance()
	{
		return instance;
	}

	/**
	 * Returns the default <A HREF="../../../../com/sun/dtv/ui/Screen.html" title="class in com.sun.dtv.ui"><CODE>Screen</CODE></A> for this
	 * device.
	 *
	 * 
	 * 
	 * @return the default Screen for this  device.
	 */
	public Screen getDefaultScreen()
	{
		return Screen.getDefaultScreen();
	}

	/**
	 * Returns a list of all <A HREF="../../../../com/sun/dtv/ui/Screen.html" title="class in com.sun.dtv.ui"><CODE>Screens</CODE></A> associated
	 * with this <A HREF="../../../../com/sun/dtv/ui/Device.html" title="class in com.sun.dtv.ui"><CODE>Device</CODE></A>.
	 *
	 * 
	 * 
	 * @return list of all Screens associated with this Device
	 */
	public Screen[] getScreens()
	{
		return Screen.getAvailableScreens();
	}
}
