package br.org.sbtvd.net.rc;

/**
 * This interface should be implemented by objects wishing to be notified
 * about the connection status of a <code>ConnectionReturnChannel</code>.
 *
 */

public interface ConnectionListener {
	/**
	 * This method is called to report events related to the setup
	 * and termination of return channel interface connections.
	 *
	 * @param e the event which happened
	 */
	public abstract void connectionChanged(ConnectionRCEvent e);
}

