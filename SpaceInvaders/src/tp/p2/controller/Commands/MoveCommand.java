package tp.p2.controller.Commands;

import tp.p2.controller.Exceptions.CommandExecuteException;
import tp.p2.controller.Exceptions.CommandParseException;
import tp.p2.game.Game;

/**
 * @author Fernando M�ndez Torrubiano
 *
 */

public class MoveCommand extends Command{
	//Atributos:
	private String moveDir;
	private int nCasillas;
	
	//Constructor:
	public MoveCommand(String name, String shortcut, String details, String help) {
		super(name, shortcut, details, help);
	}

	//M�todos:
	public boolean execute(Game game) throws CommandExecuteException{
		boolean exe = false;
	
		if(game.move(this.moveDir, this.nCasillas)) {
			exe = true;
		}
		
		return exe;
	}

	public Command parse(String[] commandWords) throws CommandParseException { 
		Command cmd = null;
		
		if(commandWords.length > 3) {
			throw new CommandParseException(Command.incorrectNumArgsMsg);
		}
		if(commandWords[0].equals("move") || commandWords[0].equals("m")) {
			cmd = this;
			if(commandWords[1].equals("left") || commandWords[1].equals("right")) {
				this.moveDir = commandWords[1];
				if(Integer.parseInt(commandWords[2]) > 0 && Integer.parseInt(commandWords[2]) <= 2) {
					this.nCasillas = Integer.parseInt(commandWords[2]);
				}
				else {
					throw new CommandParseException("The number of cells is not correct.");
				}
			}
			else {
				throw new CommandParseException("Error reading the direction.");
			}
		}
		
		
		return cmd;
	}

}
