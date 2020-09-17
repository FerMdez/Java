package simulator.exceptions;

public class ItineraryException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public ItineraryException() {
		this.message = "Itinerario no válido.";
	}

	public ItineraryException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ItineraryException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public ItineraryException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ItineraryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public String getMessage() {
		return message;
	}

}
