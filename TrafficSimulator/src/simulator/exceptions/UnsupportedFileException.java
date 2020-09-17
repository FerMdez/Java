package simulator.exceptions;

public class UnsupportedFileException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public UnsupportedFileException() {
		// TODO Auto-generated constructor stub
		this.message = "El archivo no es compatible.";
	}

	public UnsupportedFileException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public UnsupportedFileException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public UnsupportedFileException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public UnsupportedFileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public String getMessage() {
		return message;
	}

}
