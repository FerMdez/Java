package tp.p2.controller.Commands;

import tp.p2.controller.Exceptions.CommandExecuteException;
import tp.p2.controller.Exceptions.CommandParseException;
import tp.p2.game.Game;

/**
 * @author Fernando M�ndez Torrubiano
 *
 */

public abstract class Command {
	//Atributos:
	protected final String name;
	protected final String shortcut;
	private final String details;
	private final String help;
	protected static final String incorrectNumArgsMsg = "Incorrect number of arguments";
	protected static final String incorrectArgsMSg = "Incorrect argument format";
	
	//Constructor:
	public Command(String name, String shortcut, String details, String help) {
		this.name = name;
		this.shortcut = shortcut;
		this.details = details;
		this.help = help;
	}
	
	//M�todos:
	public abstract boolean execute(Game game) throws CommandExecuteException;
	public abstract Command parse(String[] commandWords) throws CommandParseException;
	
	protected boolean matchCommandName(String name) {
		return this.shortcut.equalsIgnoreCase(name) || this.name.equalsIgnoreCase(name);
	}
	
	public String helpText() {
		return this.details + ": " + this.help + "\n";
	}
}
