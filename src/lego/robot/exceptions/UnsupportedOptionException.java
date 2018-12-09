package lego.robot.exceptions;

/**
 * Exception for switch - case choices
 * @author Francesco
 *
 */
public class UnsupportedOptionException extends Exception {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 2635280281893199242L;

	/**
	 * Default constructor
	 */
	public UnsupportedOptionException() {
		super("The chosen option is not supported");
	}
	
	/**
	 * Constructor with a given message
	 * @param msg: message to initialize the exception
	 */
	public UnsupportedOptionException(String msg) {
		super(msg);
	}
}
