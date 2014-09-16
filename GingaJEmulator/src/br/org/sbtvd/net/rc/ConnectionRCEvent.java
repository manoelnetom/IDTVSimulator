package br.org.sbtvd.net.rc;

/**
 * ConnectionRCEvent - the base class for events related to
 * ConnectionReturnChannel objects.
 */

public class ConnectionRCEvent extends java.util.EventObject {
	/**
	 * Construct an event
	 *
	 * @param source the <code>ConnectionReturnChannel</code>
	 * for which the event was generated.
	 */
	public ConnectionRCEvent(Object source){
		super(source);
	}
}
