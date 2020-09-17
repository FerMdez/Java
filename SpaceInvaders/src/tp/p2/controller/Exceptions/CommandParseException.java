package tp.p2.controller.Exceptions;
/**
 * @author Fernando M�ndez Torrubiano
 *
 */
public class CommandParseException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String message;
	
	public CommandParseException(String string) {
		this.message = "Command can�t be parse.\n"
					+ "Cause of exception: \n"
					+ this + ": " + string;
	}
	
	public String getMessage() {
		return this.message;
	}

}
