/*
 * Copyright (c) 2009 LAViD
 *
 *  Copyright (C) 2009 Jefferson Ferreira <jefferson@lavid.ufpb.br>
 */
package com.sun.dtv.broadcast.event;

import java.util.EventListener;

/**
 * 
 */
public interface BroadcastEventListener extends EventListener
{
	/**
	 * Notifies the <code>BroadcastEventListener</code> that the
	 * <code>BroadcastEvent</code> has been received in the broadcast.
	 * 
	 * No guarantees are provided concerning the ability of the
	 * receiver to receive all <code>BroadcastEvent</code> in the broadcast stream, nor
	 * the latency of event notification after reception of the event in the stream.
	 *
	 * 
	 * @param event - Event indicating BroadcastEvent  has been received.
	 */
	void received( BroadcastReceivedEvent event);

}
