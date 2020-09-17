package tp.p2.game.GameObjects;

import tp.p2.game.FileContentsVerifier;
import tp.p2.game.Game;

/**
 * @author Fernando M�ndez Torrubiano
 *
 */

public class Shockwave extends Weapon {
	
	public Shockwave(Game game, int fila, int col, int live) {
		super(game, fila, col, live);
	}
	
	public Shockwave() {
		super(null, 0, 0, 1);
	}

	@Override
	protected void weaponAttack(GameObject other) {
		other.receiveShockWaveAttack(Game.DAMAGE);
		this.getDamage(Game.DAMAGE);
	}
	
	@Override
	public void computerAction() {}

	@Override
	public void onDelete() { 
		if(this.live == 0) { 
			this.isAlive = false; 
		}
	}

	@Override
	public void move() {}

	@Override
	public String toString() {
		return "";
	}

	@Override
	public String serializer() {
		return "";
	}

	@Override
	public GameObject parse(String stringFromFile, Game game2, FileContentsVerifier verifier) {
		return verifier.verifyWeaponString(stringFromFile, game2) ? new Shockwave(game2, 0, 0, 1):null;
	}

}
