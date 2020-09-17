package simulator.exceptions;

public class WeatherException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	
	public WeatherException() {
		this.message = "El tiempo atmosférico no pueder ser nulo.";
	}

	public WeatherException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public WeatherException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public WeatherException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public WeatherException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public String getMessage() {
		return message;
	}

}
