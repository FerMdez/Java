package tp.p2.game;

import java.util.Random;
import tp.p2.Level;
import tp.p2.controller.Exceptions.FileContentsException;
import tp.p2.game.GameObjects.Bomb;
import tp.p2.game.GameObjects.DestroyerAlien;
import tp.p2.game.GameObjects.GameObject;
import tp.p2.game.GameObjects.Ovni;
import tp.p2.game.GameObjects.RegularAlien;
import tp.p2.game.GameObjects.Shockwave;
import tp.p2.game.GameObjects.SuperMissile;
import tp.p2.game.GameObjects.UCMMissile;
import tp.p2.game.GameObjects.UCMShip;
/**
 * @author Fernando M�ndez Torrubiano
 *
 */

public class GameObjectGenerator {
    private static Game otherGame = new Game(Level.EASY, new Random(System.currentTimeMillis()));
    private static GameObject[] availableGameObjects = { 
	    new UCMShip(otherGame, 0, 0), 
	    new Ovni(otherGame, 0, 0, 0), 
	    new RegularAlien(otherGame, 0, 0, 0), 
	    new DestroyerAlien(otherGame, 0, 0, 0), 
	    new Shockwave(otherGame, 0, 0, 0), 
	    new Bomb(otherGame, 0, 0, 0), 
	    new UCMMissile(otherGame, 0, 0, 0), 
	    new SuperMissile(otherGame, 0, 0, 0) 
	};


	public static GameObject parse(String stringFromFile, Game game, FileContentsVerifier verifier) throws FileContentsException { 
		GameObject gameObject = null; 
		for (GameObject go: availableGameObjects) { 
			gameObject = go.parse(stringFromFile, game, verifier); 
			if (gameObject != null) break; 
			} 
		return gameObject; 
	} 
}

/*

*/