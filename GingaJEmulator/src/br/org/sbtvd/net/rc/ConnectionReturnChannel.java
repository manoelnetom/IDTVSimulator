package br.org.sbtvd.net.rc;

/**
 * This interface models a connection based return channel network interface for use in
 * receiving and transmitting IP packets over a return channel.
 * Targets for connections are specified as strings including the number to dial. These
 * strings can only include numbers and are passed direct to the underlying device.
 */

public interface ConnectionReturnChannel extends ReturnChannel{


	/**
	 * Check if this interface is connected. Connected means able to
	 * receive and transmit packets.
	 *
	 * @return true if the interface is connected, otherwise false
	 */
	public abstract boolean isConnected();

	/**
	 * Check if this interface is reserved for a ConnectionRCController.
	 *
	 * @return true if the interface is reserved, otherwise false
	 */
	public abstract boolean isReserved();

	/**
	 * Obtain an estimate of the setup time for a successful connection for this interface in seconds.
	 *
	 * @return an estimate of the setup time for a successful connection for this interface in seconds.
 	 */
	public abstract float getSetupTimeEstimate();



	/**
	 * Get the current target for connections.
	 *
	 * @return the current set of connection target parameters
	 * @throws IncompleteTargetException if the current target is not completely configured
	 */
	public ConnectionParameters getCurrentTarget() throws IncompleteTargetException;

	/**
	 * Return the time an interface has been connected
	 *
	 * @return the time in seconds for which this interface has been connected or
	 * -1 if the device is not connected
	 */
	public int getConnectedTime();

	/**
	 * Add a listener for events related to connections of this interface.
	 *
	 * @param l the listener for the connection related events
	 */
	public void addConnectionListener( ConnectionListener l );

	/**
	 * Remove a listener for events related to connections of this interface.
	 * If the listener specified is not currently receiving these events then
	 * this method has no effect.
	 *
	 * @param l the listener for the connection related events
	 */
	public void removeConnectionListener( ConnectionListener l );

}


