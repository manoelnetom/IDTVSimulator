package br.org.sbtvd.net.rc;

/**
 * This interface models a return channel network interface for use in receiving
 * and transmitting IP packets over a logical return channel.
 * This class does not model any concept of connection.A non-connection oriented
 * interface really means a permanently connected return channel.
 */

public interface ReturnChannel {
	/**
	 * Constant to indicate a PSTN return channel.
	 */
	public static final int TYPE_PSTN=1;

	/**
	 * Constant to indicate an ISDN return channel.
	 */
	public static final int TYPE_ISDN=2;

	/**
	 * Constant to indicate a DECT return channel.
	 */
	public static final int TYPE_DECT=3;

	/**
	 * Constant to indicate a CATV return channel.
	 */
	public static final int TYPE_CATV=4;

	/**
	 * Constant to indicate a LMDS return channel.
	 */
	public static final int TYPE_LMDS=5;

	/**
	 * Constant to indicate a MATV return channel.
	 */
	public static final int TYPE_MATV=6;


	/**
	 * Return the type of return channel represented by this object.
     * Note, applications wishing to discover whether a return channel interface is
     * connection oriented or not are recommended to test whether an object is an
     * instance of <code>ConnectionReturnChannel</code> or not.
	 *
	 * @return the type of return channel represented by this object encoded
	 * as one of the constants defined in this interface
	 */

	public abstract int getType();


	/**
	 * Return the maximum data rate of the connection over the immediate access
	 * network to which this network interface is connected.
	 * For connection oriented interfaces which are not currently connected,
	 * the value returned shall be that of the last connection established where
	 * that information is available. Where that information is not available,
	 * (e.g. where no connection has been established since an terminal was
 	 * power cycled), -1 shall be returned.
	 *
	 * @return a data rate in KBaud or -1 where this is not available
	 */
	public abstract int getDataRate();
}

