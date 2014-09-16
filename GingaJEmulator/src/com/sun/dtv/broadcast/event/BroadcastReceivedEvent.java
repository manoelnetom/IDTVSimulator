/*
 * Copyright (c) 2009 LAViD
 *
 *  Copyright (C) 2009 Jefferson Ferreira <jefferson@lavid.ufpb.br>
 */
package com.sun.dtv.broadcast.event;

import java.util.EventObject;

/**
 * 
 */
public class BroadcastReceivedEvent extends EventObject
{
	//following variables are implicitely defined by getter- or setter-methods:
	private byte[] data;
	private long identifier;
	private String name;
	private long time;
	private long duration;

	/**
	 * Creates a <code>BroadcastFileEvent</code> indicating that
	 * the specified <code>BroadcastFile</code> has changed.
	 *
	 * 
	 * @param source - The BroadcastFile whose contents have changed.
	 */
	public BroadcastReceivedEvent( BroadcastEventManager source)
	{
		super(source);
	}

	/**
	 * Retrieves the received data block of the <code>BroadcastReceivedEvent</code>.
	 *
	 * 
	 * 
	 * @return the data
	 */
	public byte[] getData()
	{
		return this.data;
	}

	/**
	 * Returns the identifier this <code>BroadcastReceivedEvent</code>.
	 *
	 * 
	 * 
	 * @return the identifier of this BroadcastReceivedEvent. Returns -1 if unknown.
	 */
	public long getIdentifier()
	{
		return this.identifier;
	}

	/**
	 * Returns the name this <code>BroadcastReceivedEvent</code>.
	 *
	 * 
	 * 
	 * @return the name of this BroadcastReceivedEvent. Returns null if unknown.
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * Returns the time of this <code>BroadcastReceivedEvent</code>.
	 *
	 * 
	 * 
	 * @return the time of this BroadcastReceivedEvent in microseconds. Returns -1 if unknown.
	 */
	public long getTime()
	{
		return this.time;
	}

	/**
	 * Returns the duration of this <code>BroadcastReceivedEvent</code>.
	 *
	 * 
	 * 
	 * @return the duration of this broadcast event in microseconds. Returns -1 if unknown.
	 */
	public long getDuration()
	{
		return this.duration;
	}

}
