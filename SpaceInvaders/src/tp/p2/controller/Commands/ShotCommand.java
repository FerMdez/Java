package tp.p2.controller.Commands;

import tp.p2.controller.Exceptions.CommandExecuteException;
import tp.p2.game.Game;

/**
 * @author Fernando M�ndez Torrubiano
 *
 */

public class ShotCommand extends Command {
	//Atributos:
	
	//Constructor:
	public ShotCommand(String name, String shortcut, String details, String help) {
		super(name, shortcut, details, help);
	}
	
	//M�todos:
	public boolean execute(Game game) throws CommandExecuteException {
		boolean exe = true;
	
		if(!game.shootLaser()) {
			exe = false;
			throw new CommandExecuteException("You can't launch two missiles at once.");
		}
		
		return exe;
	}

	public Command parse(String[] commandWords) {
		Command cmd = null;
		
		if(commandWords[0].equals("shot") || commandWords[0].equals("s")) {
			//cmd = new ShotCommand(commandWords[0], "m", "Realiza un disparo", null);
			cmd = this;
		}
		
		return cmd;
	}
}
