package br.org.sbtvd.net.rc;

/**
 * ConnectionTerminatedEvent - An event generated after a connected
 * <code>ConnectionReturnChannel</code> is disconnected.
 */

public class ConnectionTerminatedEvent extends ConnectionRCEvent {
	/**
	 * Construct an event.
	 *
	 * @param source the <code>ConnectionReturnChannel</code> whose status changed
	 */
	public ConnectionTerminatedEvent(Object source){
		super(source);
	}
}
