package tp.p2.controller.Commands;

import tp.p2.game.Game;

/**
 * @author Fernando M�ndez Torrubiano
 *
 */
public class ResetCommand extends Command {

	public ResetCommand(String name, String shortcut, String details, String help) {
		super(name, shortcut, details, help);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute(Game game) {
		game.reset();
		return true;
	}

	@Override
	public Command parse(String[] commandWords) {
		Command cmd = null;
		
		if(commandWords[0].equals("reset") || commandWords[0].equals("r")) {
			cmd = this;
		}
		
		return cmd;
	}

}
