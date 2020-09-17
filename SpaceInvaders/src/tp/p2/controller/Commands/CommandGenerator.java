package tp.p2.controller.Commands;

import tp.p2.controller.Exceptions.CommandParseException;

/**
 * @author Fernando M�ndez Torrubiano
 *
 */

public class CommandGenerator {
	//Atributos:
	private static final int MAXCOMMANDS = 14;
	private static Command[] avaliableCommands = {
		new MoveCommand("move", "m", "Move <left|right><1|2>", "Moves UCM-Ship to the indicated direction."),
		new ShotCommand("shoot", "s", "Shoot", "UCM-Ship launches a missile."),
		new ShockwaveCommand("shockwave", "w", "ShockWave", "UCM-Ship releases a shock wave."),
		new ListCommand("list", "l", "List", "Prints the list of available ships."),
		new ResetCommand("reset", "r", "Reset", "Starts a new game."),
		new HelpCommand("help", "h", "Help", "Prints this help message."),
		new ExitCommand("exit", "e", "Exit", "Terminates the program."),
		new UpdateCommand("none", "", "[none]", "Skips one cycle."),
		new BuySuperMissileCommand("buy", "b", "Buy", "Buy a Super Missile."),
		new StringifyCommand("stringify", "sf", "Stringify", "Serialize the board."),
		new ListPrintersCommand("listPrinters", "lp", "ListPrinters", "Prints the list of available printers."),
		new SuperShootCommand("supermissile", "sm", "SuperMissile", "UCM-Ship launches a super-missile."),
		new SaveCommand("save", "g", "Save", "Save the game on a file."),
		new LoadCommand("load", "c", "Load", "Load the game of a file.")
	};
	
	//M�todos:
	
	//Invoca al m�todo "parse" de cada subclase de Command.
	public static Command parseCommand(String[] commandWords) throws CommandParseException {
		boolean encontrado = false;
		int i = 0;
		Command cmd = null;
		
		while(!encontrado && i < MAXCOMMANDS) {
			if(avaliableCommands[i].parse(commandWords) != null) {
				cmd = avaliableCommands[i];
				encontrado = true;
			}
			else {
				i++;
			}
		}
		if(cmd == null) { throw new CommandParseException(Command.incorrectArgsMSg); }
		
		return cmd;
	}
	
	//Invoca al m�todo helpText() de cada subclase de Command.
	//Este m�todo es invocado por el m�todo "execute" de la clase HelpCommand.
	public static String commandHelp(){
		String help = "";
		
		for (int i = 0; i < avaliableCommands.length; i++) {
			help += avaliableCommands[i].helpText();
		}
		
		return help;
	}
}
