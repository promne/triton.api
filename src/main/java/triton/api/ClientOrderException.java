package triton.api;

/**
 * Generic exception for anything wrong during communication with server.
 * 
 * @author georgeh
 *
 */
public class ClientOrderException extends Exception {

	private static final long serialVersionUID = 1L;

	public ClientOrderException() {
		super();
	}

	public ClientOrderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ClientOrderException(String message, Throwable cause) {
		super(message, cause);
	}

	public ClientOrderException(String message) {
		super(message);
	}

	public ClientOrderException(Throwable cause) {
		super(cause);
	}

}
