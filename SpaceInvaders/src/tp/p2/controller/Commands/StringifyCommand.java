package tp.p2.controller.Commands;

import tp.p2.controller.Commands.Command;
import tp.p2.controller.Exceptions.CommandExecuteException;
import tp.p2.game.Game;
import tp.p2.game.GamePrinter;
import tp.p2.game.Stringifier;

/**
 * @author Fernando M�ndez Torrubiano
 *
 */

public class StringifyCommand extends Command{

	public StringifyCommand(String name, String shortcut, String details, String help) {
		super(name, shortcut, details, help);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute(Game game) throws CommandExecuteException {
		GamePrinter printer = new Stringifier();
		
		if(printer != null) {
			System.out.println(printer.toString(game));
		}
		/*
		else {
			throw new CommandExecuteException("The board could not be serializable.");
		}*/
		
		return false;
	}

	@Override
	public Command parse(String[] commandWords) {
		Command cmd = null;
		
		if (commandWords[0].equals("stringify") || commandWords[0].equals("sf")) {
			cmd = this;
		}
		
		return cmd;
	}
	
}