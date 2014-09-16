package br.org.sbtvd.net.rc;

/**
 * ConnectionEstablishedEvent - An event generated after a connection is established
 * for a <code>ConnectionReturnChannel</code>.
 */

public class ConnectionEstablishedEvent extends ConnectionRCEvent {
	/**
	 * Construct an event.
	 *
	 * @param source the <code>ConnectionReturnCahnnel</code> whose connection was established
	 */
	public ConnectionEstablishedEvent(Object source){
		super(source);
	}
}
