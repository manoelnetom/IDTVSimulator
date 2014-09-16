package br.org.sbtvd.net.rc;

/**
 * ConnectionFailedEvent - An event generated after an attempt to setup a connection for a
 * <code>ConnectionReturnChannel</code> fails.
 */

public class ConnectionFailedEvent extends ConnectionRCEvent {
	/**
	 * Construct an event.
	 *
	 * @param source the <code>ConnectionReturnChannel</code> whose connection attempt failed
	 */
	public ConnectionFailedEvent(Object source){
		super(source);
	}
}
