package br.org.sbtvd.net.rc;

import java.net.*;

/**
 * This class is the factory and manager for all return channel interfaces in the system.
 * The methods on this class which return instances of the <code>ReturnChannel</code>
 */

public class ReturnChannelManager{
	/**
	 * package scope constructor to stop javadoc generating one
	 */
	ReturnChannelManager()
	{
	}

	/**
	 * Factory method to obtain a manager. From the point of view of any
	 * individual application, this is a singleton.
	 *
	 * @return an instance of an ReturnChannelManager
	 */
	public static ReturnChannelManager getInstance()
	{
		return null;
	}
	/**
	 * Factory method to return a list of all return channel interfaces
	 * visible to this application. The number of entries in the array will
	 * exactly match the number of return channel interfaces visible to
	 * the application. Null is returned if no interfaces are visible to
	 * this application.
	 *
	 * @return an array of available return channel interfaces
 	 */
	public ReturnChannel[] getInterfaces()
	{
		return null;
	}
	/**
	 * Return the interface which will be used when connecting to a particular host.
	 * Null is returned if this is not known when the method is called.
	 *
	 * @param addr the IP address of the host to connect to
	 * @return the interface which will be used or null if this is not known
	 */
	public ReturnChannel getInterface(InetAddress addr)
	{
		return null;
	}
	/**
	 * Return the interface which is used for a particular socket.
	 *
	 * @param s the socket to use
	 * @return the interface which is used or null if the socket isn't connected
	 */
	public ReturnChannel getInterface(Socket s)
	{
		return null;
	}
	/**
	 * Return the interface which is used for a particular URLConnection
	 *
	 * @param u the URLConnection to use
	 * @return the interface which is used or null if the URLConnection isn't connected
	 */
	public ReturnChannel getInterface(URLConnection u)
	{
		return null;
	}

}

