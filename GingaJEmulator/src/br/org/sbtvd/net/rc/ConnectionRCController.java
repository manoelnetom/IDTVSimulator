package br.org.sbtvd.net.rc;

import java.io.IOException;

/** ConnectionRCController represents a controller that
  * can be used for connect a return channel interface.
  * Applications may create a connection return channel controller
  * object and use it to attempt to reserve the capability
  * to connect a return channel interface.
  */

public class ConnectionRCController{


  /** Creates a ConnectionRCController
    */
  public ConnectionRCController(){
  }

  	/**
	 * Connects asynchronously to the given target (specified
     * by a ConnectionParameters).
	 * If this controlles does not have the underlying resource reserved
	 * then a NotOwnerException will be thrown.
	 * Where the underlying resource is reserved but at the time
	 * the method is called, it is known that connection is impossible then
	 * an IOException will be thrown.
	 * Apart from this, this method is asynchronous
	 * and completion or failure is reported through the event listener on this
	 * interface.
	 * If a connection is already established when this method is called then
	 * the method call shall have no effect.
	 * @exception NotOwnerException raised if no connection return channel is
     * reserved at the moment.
	 * @throws IncompleteTargetException if the current target is not completely configured.
	 * @throws IOException if connection is known to be impossible at the time
	 * when the method is called.
	 */
	public void connect(ConnectionParameters target) throws IOException, ReturnChannelException
	{
	}

	/**
	 * Disconnect this return channel from the current target. This method is
	 * asynchronous and completion is reported through the event listener on this
	 * interface.
	 * If no connection is established then this method shall have no effect.
	 *
	 * @throws NotOwmerException if this application does not own the resource
	 */
	public void disconnect() throws NotOwnerException
	{
	}

  /** Tries to reserve exclusively the control
    * over the specified connection return channel. <p>
    * If this ConnectionRCController has currently reserved another ConnectionReturnChannel, then it
    * will either release that ConnectionReturnChannel and reserve an appropriate one.
    * If the givem ConnectionReturnChannel is currently reserved by this ConnectionRCController,
    * then this method does nothing.
    *
    * @param rcr Connection Return Channel to be reserved
    * @exception NoFreeInterfaceException raised if the requested connection return channel interface
  	*  can not be reserved.
    */
  public synchronized void reserve(ConnectionReturnChannel rcr)
    throws NoFreeInterfaceException {
    }

  /** Release the right to control the return channel interface. <p>
    *
    * @exception NotOwnerException raised if the controller does not
    * currently have a connection return channel interface reserved.
    */
  public synchronized void release()
    throws NotOwnerException {
    }

  /** Returns the connection return channel interface associated with this
    * controller.
    * @return the connection return channel associated with this controller or
    * null if no connection return channel has been reserved.
    */
  public ConnectionReturnChannel getConnectionReturnChannel(){
    	return null;
  }
}
