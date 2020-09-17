package tp.p2.game;

import tp.p2.controller.Exceptions.CommandExecuteException;
/**
 * @author Fernando M�ndez Torrubiano
 *
 */
public enum PrinterTypes {
	
	BOARDPRINTER("boardprinter",
			"prints the game formatted as a board of dimension: ",
			new BoardPrinter(Game.MAXFIL, Game.MAXCOL)),
	STRINGIFIER("stringifier",
			"prints the game as plain text",
			new Stringifier());
	
	private String printerName;
	private String helpText;
	private GamePrinter printerObject;
	
	private PrinterTypes(String name, String text, GamePrinter printer) {
		printerName = name;
		helpText = text;
		printerObject = printer;
	}
	
	public static String printerHelp(Game game) throws CommandExecuteException {
		String helpString = "";
		for (PrinterTypes printer : PrinterTypes.values())
			helpString += String.format("%s : %s%s%n", printer.printerName, printer.helpText,
				(printer == BOARDPRINTER ? Game.MAXFIL + " x " + Game.MAXCOL : "") );
		
		if(helpString == "") {throw new CommandExecuteException("No hay tableros disponibles.");}
		return helpString;
	}
	
	// Assumes a max of one object of each printer type is needed (otherwise return copy)
	public GamePrinter getObject(Game game) {
		printerObject.setGame(game);
		return printerObject;
	}


}

