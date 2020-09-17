/**
 * @author Fernando M�ndez Torrubiano
 *
 */
package tp.p2;

import java.util.Random;
import java.util.Scanner;
import java.lang.NumberFormatException;
import tp.p2.Level;
import tp.p2.controller.Controller;
import tp.p2.game.Game;

/**
 * @author Fernando M�ndez Torrubiano; DNI: 02723009Q
 * @clase TP1, GRUPO D.
 *
 */

public class Main {
	
	public static void main(String[] args) {
		Game game;
		Level level = null;
		Scanner in = null;
		int seed = 0;
		Random rnd = null;
		
		//Selecci�n de nivel:
		if(args.length >= 1) {
			level = Level.parse(args[0]);
		}
		else {
			level = Level.EASY;
		}
		
		//Selecci�n de semilla:
		if(args.length == 2) {
			try {
				seed = Integer.parseInt(args[1]);
			}
			catch (NumberFormatException ex){
				System.err.format(ex.getMessage() + " %n %n");
			}
			finally {
				rnd = new Random(seed);
			}
		}
		else {
			rnd = new Random(System.currentTimeMillis());
		}
		
		game = new Game(level, rnd);
		Controller controlador = new Controller(game, in); 
		controlador.run(game);
	}
	
}

/*
//Selecci�n de nivel:
		if(args[0].contentEquals("EASY")) {
			nivel = Level.EASY;
		} else if(args[0].contentEquals("HARD")){
			nivel = Level.HARD;
		} else if(args[0].contentEquals("INSANE")) {
			nivel = Level.INSANE;
		}else {
			nivel = Level.EASY;
		}
*/