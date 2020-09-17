package tp.p2.game.GameObjects;

import tp.p2.game.FileContentsVerifier;
import tp.p2.game.Game;
import tp.p2.game.GameObjects.Interfaces.IExecuteRandomActions;

/**
 * @author Fernando M�ndez Torrubiano
 *
 */

public class Ovni extends EnemyShip{
	//Atributos:
	private String letra;
	private boolean enabble;
	
	//Constructor:
	public Ovni(Game game, int fila, int col, int live) {
		super(game, fila, col, live);
		this.letra = "O";
		this.enabble = false;
	}
	
	public Ovni() {
		super(null, 0, 0, 1);
		this.letra = "O";
		this.enabble = false;
	}

	//M�todos:
	public String toString() {
		return this.letra + "[" + this.live + "]";
	}

	@Override
	public void computerAction() {
		if(IExecuteRandomActions.canGenerateRandomOvni(game)) {
			this.enabble = true;
		}
	}
	
	public boolean receiveMissileAttack(int damage) {
		this.live -= damage;
		this.game.enableShockWave();
		this.game.receivePoints(25);
		return true;
	}
	
	public boolean receiveShockWaveAttack(int damage) {
		this.live -= damage;
		return true;
	}

	@Override
	public void onDelete() {
		if(this.columna < 0) {
			this.enabble = false;
			this.columna = Game.MAXCOL;
		}
		else if(this.live <= 0) {
			this.isAlive = false;
		}
	}

	@Override
	public void move() {
		if(this.enabble) { //QUITAR LA CONDICI�N DE LA COLUMNA, ES S�LO PARA PRUEBAS. && this.columna >= Game.MAXCOL
			this.columna--;
		}
	}

	@Override
	public String serializer() {
		return "O;"
				+ this.fila + ","
				+ this.columna + ";"
				+ this.live;
	}

	@Override
	public GameObject parse(String stringFromFile, Game game2, FileContentsVerifier verifier) {
		String[] words = stringFromFile.split(FileContentsVerifier.separator1);
		String[] coords = words[1].split(FileContentsVerifier.separator2);
		return verifier.verifyOvniString(stringFromFile, game2, 1) ? 
				new Ovni(game2, Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), Integer.parseInt(words[2])):null;
	}

}