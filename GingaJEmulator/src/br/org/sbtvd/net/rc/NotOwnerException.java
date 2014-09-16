
package br.org.sbtvd.net.rc;

/** This exception is raised when the application calls
  * a method and has no
  * control over the corresponding ConnectionReturnChannel.
  */
public class NotOwnerException extends ReturnChannelException {

  /** Default constructor for the exception
    */
  public NotOwnerException() {
  }

  /** Constructor for the exception with a specified reason
    * @param reason the reason why the exception was raised
    */
  public NotOwnerException(String reason) {}

}

