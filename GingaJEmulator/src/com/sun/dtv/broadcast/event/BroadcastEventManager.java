/*
 * Copyright (c) 2009 LAViD
 *
 *  Copyright (C) 2009 Jefferson Ferreira <jefferson@lavid.ufpb.br>
 */
package com.sun.dtv.broadcast.event;

import java.io.IOException;

import javax.tv.locator.InvalidLocatorException;
import javax.tv.locator.Locator;

/**
 * 
 */
public class BroadcastEventManager
{
	//following variables are implicitely defined by getter- or setter-methods:
	private Locator locator;

	/**
	 * Creates a <code>BroadcastEventManager</code> instance that represents the events
	 * referenced by the given <code>Locator</code>.
	 *
	 * 
	 * This constructor throws <code>java.io.IOException</code> if it
	 * determines immediately that the requested broadcast events cannot
	 * be accessed.  Since this constructor may complete its work
	 * asynchronously, absence of an <code>IOException</code> is not a
	 * guarantee that the requested broadcast events are accessible.
	 *
	 * 
	 * @param locator - A Locator referencing the source of the BroadcastEvent.
	 * @throws InvalidLocatorException - If locator does not refer to broadcast events.
	 * @throws IOException - If the requested broadcast events cannot be accessed.
	 * @throws UnsupportedOperationException - If broadcast filesystem is not supported.
	 */
	public BroadcastEventManager( Locator locator) throws InvalidLocatorException, IOException
	{
		//TODO implement BroadcastEventManager
	}

	/**
	 * Creates a <code>BroadcastEventManager</code> instance that represents the
	 * events whose path name in the broadcast is the given path argument.
	 *
	 * 
	 * This constructor throws <code>java.io.IOException</code> if it
	 * determines immediately that the requested broadcast events cannot
	 * be accessed.  Since this constructor may complete its work
	 * asynchronously, absence of an <code>IOException</code> is not a
	 * guarantee that the requested broadcast events are accessible.
	 *
	 * 
	 * @param url - The stream path name.
	 * @throws IOException - If the requested broadcast events cannot be accessed.
	 * @throws UnsupportedOperationException - If broadcast filesystem is not supported.
	 */
	public BroadcastEventManager( String url) throws IOException
	{
		//TODO implement BroadcastEventManager
	}

	/**
	 * Subscribes a <code>BroadcastEventListener</code> to receive
	 * notifications of received <code>BroadcastReceivedEvent</code>. If
	 * the specified listener is currently subscribed then no action is
	 * performed.
	 *
	 * 
	 * @param listener - The BroadcastEventListener to be notified.
	 * @throws IOException - If there are insufficient resources to support this listener.
	 * @throws SecurityException - If a security manager exists and its SecurityManager.checkRead(java.lang.String) method denies read access to the file.
	 * @see removeListener(com.sun.dtv.broadcast.event.BroadcastEventListener)
	 */
	public void addListener( BroadcastEventListener listener) throws IOException, SecurityException
	{
		//TODO implement addListener
	}

	/**
	 * Unsubscribes a <code>BroadcastEventListener</code> from receiving
	 * notifications of received <code>BroadcastEvent</code>. If
	 * the given <code>BroadcastFileListener</code> is not currently
	 * subscribed for notification then no action is performed.
	 *
	 * 
	 * @param listener - A currently registered BroadcastEventListener.
	 * @see addListener(com.sun.dtv.broadcast.event.BroadcastEventListener)
	 */
	public void removeListener( BroadcastEventListener listener)
	{
		//TODO implement removeListener
	}

	/**
	 * Returns a <code>Locator</code> identifying this
	 * <code>BroadcastEvent</code>.
	 *
	 * 
	 * 
	 * @return A Locator identifying this BroadcastEvent.
	 */
	public Locator getLocator()
	{
		return this.locator;
	}

}
