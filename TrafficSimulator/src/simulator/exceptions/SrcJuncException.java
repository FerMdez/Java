package simulator.exceptions;

public class SrcJuncException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	
	public SrcJuncException() {
		this.message = "El valor no puede ser nulo.";
	}

	public SrcJuncException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public SrcJuncException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public SrcJuncException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public SrcJuncException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public String getMessage() {
		return message;
	}

}
