package tp.p2.game.GameObjects;

import tp.p2.game.Game;

/**
 * @author Fernando M�ndez Torrubiano
 *
 */

public abstract class EnemyShip extends Ship {
	
	//Constructor:
	public EnemyShip(Game game, int fila, int col, int live) {
		super(game, fila, col, live);
		// TODO Auto-generated constructor stub
	}
	
	//M�todos:
	public abstract void computerAction();
	public abstract void onDelete();
	public abstract void move();
	public abstract String toString();
}
