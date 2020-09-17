package tp.p2.game.GameObjects;

import tp.p2.game.FileContentsVerifier;
import tp.p2.game.Game;

/**
 * @author Fernando M�ndez Torrubiano
 *
 */

public class UCMMissile extends Weapon{
	//Atributos:
	private String letra;
	
	//Constructor:
	public UCMMissile(Game game, int fila, int col, int live) {
		super(game, fila, col, live);
		this.letra = "|";
	}
	
	public UCMMissile() {
		super(null, 0, 0, 1);
		this.letra = "|";
	}

	@Override
	protected void weaponAttack(GameObject other) {
		other.receiveMissileAttack(Game.DAMAGE);
		//this.live--;
		this.getDamage(Game.DAMAGE);
	}
	
	@Override
	public void computerAction() {}

	@Override
	public void onDelete() {
		if(this.live <= 0 || this.fila < 0) {
			this.isAlive = false;
		}
	}

	@Override
	public void move() {
		this.fila--;
	}

	@Override
	public String toString() {
		return this.letra;
	}

	@Override
	public String serializer() {
		return "M;"
				+ this.fila + ","
				+ this.columna;
	}

	@Override
	public GameObject parse(String stringFromFile, Game game2, FileContentsVerifier verifier) {
		String[] words = stringFromFile.split(FileContentsVerifier.separator1);
		String[] coords = words[1].split(FileContentsVerifier.separator2);
		return verifier.verifyWeaponString(stringFromFile, game2) ? 
				new UCMMissile(game2, Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), Integer.parseInt(words[2])):null;
	}
}
