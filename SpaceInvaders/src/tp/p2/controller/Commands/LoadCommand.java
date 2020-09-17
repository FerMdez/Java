package tp.p2.controller.Commands;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import tp.p2.Level;
import tp.p2.controller.Exceptions.CommandExecuteException;
import tp.p2.controller.Exceptions.CommandParseException;
import tp.p2.controller.Exceptions.FileContentsException;
import tp.p2.game.FileContentsVerifier;
import tp.p2.game.Game;
import tp.p2.game.GameObjects.AlienShip;

/**
 * @author Fernando M�ndez Torrubiano
 *
 */

public class LoadCommand extends Command {
	private Scanner in;

	public LoadCommand(String name, String shortcut, String details, String help) {
		super(name, shortcut, details, help);
		this.in = new Scanner(System.in);
	}

	@Override
	public boolean execute(Game game) throws CommandExecuteException {
		BufferedReader inStream = null;
		FileContentsVerifier verifier = new FileContentsVerifier();
		String gameString = "";
		int restCycles = 0;
		
		System.out.println("File name > ");
		String fileName = in.nextLine() + ".dat";
		
		try {
			inStream = new BufferedReader(
						new InputStreamReader(
							new FileInputStream(fileName), "UTF-8"));
			try {
				//Read from the archive until we find the game line:
				do {
					gameString = inStream.readLine().trim();
				} while(!verifier.verifyGameString(gameString));
				String[] words = gameString.split(FileContentsVerifier.separator1);
				restCycles = Integer.parseInt(words[1]);
				
				//Read the level:
				String levelString = inStream.readLine().trim();
				verifier.verifyLevelString(levelString);
				Level level = Level.parse(levelString);
				
				//Load the game information:
				game.load(inStream);
				AlienShip.setNumAliens(level.getNumRegularAliens()+level.getNumDestroyerAliens());
				game = new Game(level, restCycles);
				System.out.println("Game successfully loaded from file " + fileName + "\n");
			}
			catch (FileContentsException e) {
				e.printStackTrace();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
			finally { 
				try {
					inStream.close();
				} 
				catch (IOException e) {
					e.printStackTrace();
				}  
			}
		}
		catch (FileNotFoundException e) {	
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParseException {
		Command cmd = null;
		
		if(commandWords[0].equals("load") || commandWords[0].equals("c")) {
			cmd = this;
		}
		
		return cmd;
	}

}
