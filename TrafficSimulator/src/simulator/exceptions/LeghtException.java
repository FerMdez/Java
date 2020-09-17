package simulator.exceptions;

public class LeghtException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public LeghtException() {
		this.message = "La longitud de la carretera no puede ser negativo.";
	}

	public LeghtException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public LeghtException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public LeghtException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public LeghtException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public String getMessage() {
		return message;
	}

}
