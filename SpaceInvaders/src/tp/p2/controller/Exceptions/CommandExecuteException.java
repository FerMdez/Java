package tp.p2.controller.Exceptions;
/**
 * @author Fernando M�ndez Torrubiano
 *
 */
public class CommandExecuteException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String message;
	
	public CommandExecuteException(String string) {
		this.message = "Failed to run command.\n" 
				+ "Cause of exception: \n"
				+ this + ": " + string;
	}
	
	public String getMessage() {
		return this.message;
	}
}
