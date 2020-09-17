package tp.p2.controller.Commands;

import tp.p2.game.Game;

/**
 * @author Fernando M�ndez Torrubiano
 *
 */
public class UpdateCommand extends Command {
	public UpdateCommand(String name, String shortcut, String details, String help) {
		super(name, shortcut, details, help);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute(Game game) {
		return true;
	}

	@Override
	public Command parse(String[] commandWords) {
		Command cmd = null;
		
		if(commandWords[0].equals("none") || commandWords[0].equals("n") || commandWords[0].equals("")) {
			cmd = this;
		}
		
		return cmd;
	}

}
