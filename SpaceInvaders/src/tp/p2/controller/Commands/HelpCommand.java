package tp.p2.controller.Commands;

import tp.p2.controller.Exceptions.CommandExecuteException;
import tp.p2.game.Game;

/**
 * @author Fernando M�ndez Torrubiano
 *
 */

public class HelpCommand extends Command {

	public HelpCommand(String name, String shortcut, String details, String help) {
		super(name, shortcut, details, help);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute(Game game) throws CommandExecuteException {
		String help = CommandGenerator.commandHelp();
		
		if(help != "") {
			System.out.println(help);
		}
		else {
			throw new CommandExecuteException("No description available.");
		}
		
		return false;
	}

	@Override
	public Command parse(String[] commandWords) {
		Command cmd = null;
		
		if(commandWords[0].equals("help") || commandWords[0].equals("h")) {
			cmd = this;
		}
		
		return cmd;
	}

}
