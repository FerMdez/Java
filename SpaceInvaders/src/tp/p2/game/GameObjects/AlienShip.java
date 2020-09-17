package tp.p2.game.GameObjects;

import tp.p2.game.Game;

/**
 * @author Fernando M�ndez Torrubiano
 *
 */

public abstract class AlienShip extends EnemyShip {
	//Atributos est�ticos:
	protected static boolean down = false; //Variable est�tica booleana para indicar si los Aliens tienen que bajar o no.
	protected static String direction = "left"; //Variable est�tica que indica la direcci�n en la que se mueve los aliens.
	protected static boolean isLanded = false;
	protected static boolean allDead = false;
	protected static int numAliens = 0; //Variable est�tica que indica el n�mero de Aliens que hay en el tablero actualmente.
	protected static int contAliens = 0; //Variable est�tica que ind�ca (si down == true), cu�ntos aliens han bajado.
	
	
	//Constructor:
	public AlienShip(Game game, int fila, int col, int live) {
		super(game, fila, col, live);
	}

	//M�todos:
	public static boolean haveLanded() {
		return isLanded;
	}

	public static boolean allDead() {
		return allDead;
	}
	
	public static int numAliens() {
		return numAliens;
	}
	
	public static void setNumAliens(int aliens) {
		numAliens = aliens;
	}
	
	public void move() {
		if(this.game.getRestCycles() == 0) {
			if(direction == "left") {
				this.columna--;
				if(this.columna == 0) {
					down = true;
				}
			}
			else if(direction == "right") {
				this.columna++;
				if(this.columna == Game.MAXCOL-1) {
					down = true;
				}
			}
		}
		if(down && this.game.getRestCycles() == this.level.getNumCyclesToMoveOneCell()) {	
			this.fila++;
			if(this.fila == Game.MAXFIL-1) { isLanded = true; }
			contAliens++;
			if(contAliens >= numAliens) {
				contAliens = 0;
				down = false;
				if(direction == "left") {
					direction = "right";
				}
				else {
					direction = "left";
				}
			}
		}
	}
	
	public boolean receiveMissileAttack(int damage) {
		this.getDamage(damage);
		return true;
	}
	
	public boolean receiveShockWaveAttack(int damage) {
		this.getDamage(damage);
		return true;
	}
	
	public boolean recibeExplodeAttack(int damage) { //Para recibir da�o de una nave explosiva.
		this.getDamage(damage);
		return true;
	}	
	
	public abstract void computerAction();
	public abstract void onDelete();
	public abstract String toString();
}



/*
public void move() {
		if(this.game.getCurrentCycle() % this.level.getNumCyclesToMoveOneCell() == 0) {
			if(direction == "left") {
				this.columna--;
				if(this.columna == 0) {
					down = true;
				}
			}
			else if(direction == "right") {
				this.columna++;
				if(this.columna == Game.MAXCOL-1) {
					down = true;
				}
			}
		}
		if(down == true && this.game.getCurrentCycle() % this.level.getNumCyclesToMoveOneCell() != 0) {	
			this.fila++;
			if(this.fila == Game.MAXFIL-1) { isLanded = true; }
			contAliens++;
			if(contAliens == numAliens) {
				contAliens = 0;
				down = false;
				if(direction == "left") {
					direction = "right";
				}
				else {
					direction = "left";
				}
			}
		}
	}
*/


