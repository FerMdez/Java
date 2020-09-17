package tp.p2.controller.Commands;

import tp.p2.controller.Exceptions.CommandExecuteException;
import tp.p2.game.Game;
import tp.p2.game.PrinterTypes;

/**
 * @author Fernando M�ndez Torrubiano
 *
 */

public class ListPrintersCommand extends Command {

	public ListPrintersCommand(String name, String shortcut, String details, String help) {
		super(name, shortcut, details, help);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute(Game game) throws CommandExecuteException {
		System.out.println(PrinterTypes.printerHelp(game));
		if(PrinterTypes.printerHelp(game) == "") {
			throw new CommandExecuteException("No printers avaliable.");
		}
		return false;
	}

	@Override
	public Command parse(String[] commandWords) {
		Command cmd = null;
		
		if(commandWords[0].equals("listPrinters") || commandWords[0].equals("lp")) {
			cmd = this;
		}
		
		return cmd;
	}

}
