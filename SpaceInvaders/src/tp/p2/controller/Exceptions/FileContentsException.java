package tp.p2.controller.Exceptions;
/**
 * @author Fernando M�ndez Torrubiano
 *
 */
public class FileContentsException extends Exception {
	public static final String wrongPrefixMsg = "unknown game attribute: ";
	public static final String lineTooLongMsg = "too many words on line commencing: ";
	public static final String lineTooShortMsg = "missing data on line commencing: ";

	private static final long serialVersionUID = 1L;
	
	private String string;
	
	public FileContentsException(String string) {
		this.string = "Contend not found.\n"
				+ "Cause of exception: \n"
				+ this + ": " + string;
	}
	
	public String toString() {
		return this.string;
	}
}
