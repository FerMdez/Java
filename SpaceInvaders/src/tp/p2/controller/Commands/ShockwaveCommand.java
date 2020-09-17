package tp.p2.controller.Commands;

import tp.p2.controller.Exceptions.CommandExecuteException;
import tp.p2.game.Game;

/**
 * @author Fernando M�ndez Torrubiano
 *
 */

public class ShockwaveCommand extends Command {
	//Atributos:

	public ShockwaveCommand(String name, String shortcut, String details, String help) {
		super(name, shortcut, details, help);
	}

	public boolean execute(Game game) throws CommandExecuteException {
		boolean exe = true;
		
		if(!game.shockWave()) {
			exe = false;
			throw new CommandExecuteException("El ShockWave no est� disponible.");
		}
		
		return exe;
	}

	public Command parse(String[] commandWords) {
		Command cmd = null;
		
		if(commandWords[0].equals("shockwave") || commandWords[0].equals("w")) {
			cmd = this;
		}
		
		return cmd;
	}

}
