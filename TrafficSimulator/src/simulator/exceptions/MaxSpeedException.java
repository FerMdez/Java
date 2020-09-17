package simulator.exceptions;

public class MaxSpeedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public MaxSpeedException() {
		this.message = "La velocidad no puede ser negativa.";
	}

	public MaxSpeedException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public MaxSpeedException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public MaxSpeedException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public MaxSpeedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public String getMessage() {
		return message;
	}

}
