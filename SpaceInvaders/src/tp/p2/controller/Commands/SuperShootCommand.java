package tp.p2.controller.Commands;

import tp.p2.controller.Exceptions.CommandExecuteException;
import tp.p2.game.Game;

/**
 * @author Fernando M�ndez Torrubiano
 *
 */

public class SuperShootCommand extends Command {

	public SuperShootCommand(String name, String shortcut, String details, String help) {
		super(name, shortcut, details, help);
	}

	public boolean execute(Game game) throws CommandExecuteException {
		boolean exe = true;
	
		if(!game.shootSuperLaser()) {
			exe = false;
			throw new CommandExecuteException("You don't have super-missiles.");
		}
		
		return exe;
	}

	public Command parse(String[] commandWords) {
		Command cmd = null;
		
		if(commandWords[0].equals("supermissile") || commandWords[0].equals("sm")) {
			cmd = this;
		}
		
		return cmd;
	}

}



