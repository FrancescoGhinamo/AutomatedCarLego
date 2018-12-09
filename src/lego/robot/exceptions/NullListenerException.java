package lego.robot.exceptions;

/**
 * Exception thrown when a sensor attempts to invoke a method from a listener is not instanced
 * @author Programming
 *
 */
public class NullListenerException extends Exception {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 759963670855574733L;

	/**
	 * Constructor with default message
	 */
	public NullListenerException() {
		super("The sensor attempted to invoke a method from a listener not instanced");
	}
	
	/**
	 * Constructor with a variable message
	 * @param msg: message for the exception
	 */
	public NullListenerException(String msg) {
		super(msg);
	}

}
