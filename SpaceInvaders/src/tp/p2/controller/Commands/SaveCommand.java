package tp.p2.controller.Commands;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import tp.p2.controller.Exceptions.CommandExecuteException;
import tp.p2.controller.Exceptions.CommandParseException;
import tp.p2.game.Game;
import tp.p2.game.GamePrinter;
import tp.p2.game.Stringifier;

/**
 * @author Fernando M�ndez Torrubiano
 *
 */
public class SaveCommand extends Command {
	
	private Scanner in;
	
	public SaveCommand(String name, String shortcut, String details, String help) {
		super(name, shortcut, details, help);
		this.in = new Scanner(System.in);
	}

	@Override
	public boolean execute(Game game) throws CommandExecuteException {
		GamePrinter printer = new Stringifier();
		
		System.out.println("File name > ");
		String fileName = in.nextLine() + ".dat";
		
		BufferedWriter outChars = null;         
		
		try {
			outChars = new BufferedWriter(new FileWriter(fileName));
			outChars.write(printer.toString(game));
			outChars.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		finally {
			if(outChars == null) { throw new CommandExecuteException("No se pudo guardar el juego."); }
			else { System.out.println("Game successfully saved in file " + fileName + ". Use the 'load' command to reload it.\n"); }
		}
	
		return false;
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParseException {
		Command cmd = null;
		
		if(commandWords[0].equals("save") || commandWords[0].equals("g")) {
			cmd = this;
		}
		
		return cmd;
	}

}

