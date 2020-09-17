package simulator.exceptions;

public class ContClassException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public ContClassException() {
		this.message = "El valor de contaminación debe ser positivo.";
	}

	public ContClassException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ContClassException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public ContClassException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ContClassException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public String getMessage() {
		return message;
	}

}
