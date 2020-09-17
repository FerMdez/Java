package tp.p2.controller.Commands;

import tp.p2.controller.Exceptions.CommandExecuteException;
import tp.p2.game.Game;

/**
 * @author Fernando M�ndez Torrubiano
 *
 */

public class BuySuperMissileCommand extends Command {

	public BuySuperMissileCommand(String name, String shortcut, String details, String help) {
		super(name, shortcut, details, help);
	}

	@Override
	public boolean execute(Game game) throws CommandExecuteException {
		boolean exe = true;
		
		if(!game.buySuperMissile()) {
			System.err.println("Error, you can't buy a Super Missile right now.");
			exe = false;
		}
		
		return exe;
	}

	@Override
	public Command parse(String[] commandWords) {
		Command cmd = null;
		
		if(commandWords[0].equals("buy") || commandWords[0].equals("b")) {
			cmd = this;
		}
		
		return cmd;
	}

}
