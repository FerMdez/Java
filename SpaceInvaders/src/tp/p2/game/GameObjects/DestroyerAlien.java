package tp.p2.game.GameObjects;

import tp.p2.game.FileContentsVerifier;
import tp.p2.game.Game;
import tp.p2.game.GameObjects.Interfaces.IExecuteRandomActions;

/**
 * @author Fernando M�ndez Torrubiano
 *
 */

public class DestroyerAlien extends AlienShip implements IExecuteRandomActions{
	//Atributos:
	private Bomb bomb;
	private String letra;
	
	//Constructor:
	public DestroyerAlien(Game game, int fila, int col, int live) {
		super(game, fila, col, live);
		this.bomb = null;
		this.letra = "D";
		numAliens++;
	}
	
	public DestroyerAlien() {
		super(null, 0, 0, 1);
		this.bomb = null;
		this.letra = "D";
		numAliens++;
	}

	//M�todos:
	public void deleteBomb() {
		this.bomb = null;
	}
	
	public boolean getBomb() {
		return this.bomb != null ? true : false;
	}

	@Override
	public void computerAction() {
		if(this.bomb == null && IExecuteRandomActions.canGenerateRandomBomb(game)) {
			this.bomb = new Bomb(game, this.fila, this.columna, 1);
			this.game.addObject(this.bomb);
		}
	}

	@Override
	public void onDelete() {
		if(this.bomb != null && !this.bomb.isAlive) {
			this.deleteBomb();
		}
		if(this.live <= 0) {
			this.isAlive = false;
			numAliens--;
			this.game.receivePoints(10);
			if(numAliens == 0) { allDead = true; }
		}
	}
	
	@Override
	public String toString() {
		return this.letra + "[" + this.live + "]";
	}

	@Override
	public String serializer() {
		return "D;"
				+ this.fila + ","
				+ this.columna + ";"
				+ this.live + ";"
				//+ this.getBomb() + ";"
				+ this.game.getRestCycles() + ";"
				+ this.direction;
	}

	@Override
	public GameObject parse(String stringFromFile, Game game2, FileContentsVerifier verifier) {
		String[] words = stringFromFile.split(FileContentsVerifier.separator1);
		String[] coords = words[1].split(FileContentsVerifier.separator2);
		return verifier.verifyDestroyerShipString(stringFromFile, game2, 2) ? 
				new DestroyerAlien(game2, Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), Integer.parseInt(words[2])):null;
	}

}
