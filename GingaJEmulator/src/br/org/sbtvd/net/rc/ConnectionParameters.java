package br.org.sbtvd.net.rc;

/**
 * This class encapsulates the parameters needed to specify the target
 * of a connection.
 */

public class ConnectionParameters {
	/**
	 * Construct a set of connection parameters.
	 *
	 * @param number the target of the connection, e.g. a phone number
	 * @param username the username to use in connection setup
	 * @param password the password to use in connection setup
	 */
	public ConnectionParameters(String number, String username, String password)
	{
	}

	/**
	 * Return the target of this connection for example a phone number
	 *
	 * @return the target of the connection
	 */
	public String getTarget()
	{
		return null;
	}
	/**
	 * Return the username used in establishing this connection
	 *
	 * @return the username used in establishing the connection
	 */
	public String getUsername()
	{
		return null;
	}
	/**
	 * Return the password used in establishing this connection
	 *
	 * @return the password used in establishing this connection
	 */
	public String getPassword()
	{
		return null;
	}
}
