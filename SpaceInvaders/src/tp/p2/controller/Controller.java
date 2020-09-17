/**
 * 
 */
package tp.p2.controller;

import java.util.Scanner;
import tp.p2.controller.Commands.Command;
import tp.p2.controller.Commands.CommandGenerator;
import tp.p2.controller.Exceptions.CommandExecuteException;
import tp.p2.controller.Exceptions.CommandParseException;
import tp.p2.game.BoardPrinter;
import tp.p2.game.Game;
import tp.p2.game.GamePrinter;

/**
 * @author Fernando M�ndez Torrubiano
 *
 */

public class Controller {	
	//Atributos: 
	Scanner in;
	
	//Constructor:
	public Controller(Game game, Scanner in) {
		this.in = new Scanner(System.in);
	}
	
	//M�todos:
	
	//Ejecuta el bucle del juego hasta que termina:
	public void run(Game game) {
		GamePrinter printer = new BoardPrinter(Game.MAXFIL, Game.MAXCOL);
		System.out.println(game + printer.toString(game));
		
		do {
			System.out.println("Command > ");
			String[] words = in.nextLine().toLowerCase().trim().split("\\s+");
			
			try {
				Command command = CommandGenerator.parseCommand(words);
				if (command != null) {
					if (command.execute(game)) {
						game.update();
						System.out.println(game + printer.toString(game));
					}
				}
				else {
					System.out.format("Command " + words[0] + " not found. \n");
				}
			}
			catch (CommandParseException | CommandExecuteException ex) {
				System.out.format(ex.getMessage() + " %n %n");
			}
		} while (!game.isFinished());
		game.update();
		System.out.println(game + printer.toString(game));
		System.out.println(game.getWinnerMessage());
	}
	
}

//TPpr2v2:
/*
public class Controller {	
//Atributos: 
Scanner in;

//Constructor:
public Controller(Game game, Scanner in) {
	this.in = new Scanner(System.in);
}

//M�todos:

//Ejecuta el bucle del juego hasta que termina:
public void run(Game game) {
	System.out.println(game);
	do {
		System.out.println("Command > ");
		String[] words = in.nextLine().toLowerCase().trim().split("\\s+");
		
		Command command = CommandGenerator.parseCommand(words);
		if (command != null) {
			if (command.execute(game)) {
				game.update();
				System.out.println(game);
			}
		}
		else {
			System.out.format("Command " + words[0] + " not found. \n");
		}
	} while (!game.isFinished());
}

}
*/
