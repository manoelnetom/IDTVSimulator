package com.sun.dtv.application;

public interface ApplicationProxy {
	
	public static final int ACTIVE = 0;
	public static final int DESTROYED =	1;
	public static final int LOADED = 2;
	public static final int NOT_LOADED 	= 4;
	public static final int PAUSED =	3;

	/**
	 * Gets the state of the Application. 
	 *
	 */
	public int getState();

	/**
	 * Requests the application be paused. 
	 *
	 */
	public void pause();

	/**
	 * Requests the application be resumed. 
	 *
	 */
	public void resume();

	/**
	 * Request the application be ACTIVE with the specified parameters. The number of parameters may be zero or more. 
	 * The application is requested to be started if it is not already active. If the target application is a Java DTV 
	 * application the arguments MUST be made available as the javax.microedition.xlet.XletContext.ARGS.
	 *
	 * @param parameters the array of parameters to be passed to the application; null MUST have the same behavior as passing a zero length array. 
	 * 
	 * @throws IllegalStateException if the application is already ACTIVE
	 */
	public void start(String[] parameters);

	/**
	 * Requests the application to be stopped. 
	 *
	 */
	public void stop();

	/**
	 * Adds a listener for changes in the application state. 
	 *
	 */
	public void addListener(AppProxyListener listener);

	/**
	 * Remove a listener registered for changes. If the listener was not added, no action is taken. 
	 *
	 */
	public void removeListener(AppProxyListener listener);

}
