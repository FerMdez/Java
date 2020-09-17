package tp.p2.game.GameObjects;

import tp.p2.game.Game;

/**
 * @author Fernando M�ndez Torrubiano
 *
 */

public abstract class Weapon extends GameObject {

	public Weapon(Game game, int fila, int col, int live) {
		super(game, fila, col, live);
		// TODO Auto-generated constructor stub
	}
	
	public boolean performAttack(GameObject other) {
		boolean attack = false;
		
		if(isAlive() && other.isAlive()) {
			if(other.isOnPosition(this.fila, this.columna)) { //x, y de weapon
				this.weaponAttack(other); //restar vida
				attack = true;
			}
		}
		
		return attack;
	}
	
	protected abstract void weaponAttack(GameObject other);
	public abstract void computerAction();
	public abstract void onDelete();
	public abstract void move();
	public abstract String toString();

}
