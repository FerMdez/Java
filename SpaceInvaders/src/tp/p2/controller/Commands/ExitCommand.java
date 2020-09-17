package tp.p2.controller.Commands;

import tp.p2.game.Game;

/**
 * @author Fernando M�ndez Torrubiano
 *
 */

public class ExitCommand extends Command {

	public ExitCommand(String name, String shortcut, String details, String help) {
		super(name, shortcut, details, help);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute(Game game) {
		game.exit();
		return true;
	}

	@Override
	public Command parse(String[] commandWords) {
		Command cmd = null;
		
		if(commandWords[0].equals("exit") || commandWords[0].equals("e")) {
			cmd = this;
		}
		return cmd;
	}

}
