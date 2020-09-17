package tp.p2.game.GameObjects;

import tp.p2.game.FileContentsVerifier;
import tp.p2.game.Game;

/**
 * @author Fernando M�ndez Torrubiano
 *
 */

public class Bomb extends Weapon{
	//Atributos:
	private String letra;
	
	//Constructor:
	public Bomb(Game game, int fila, int col, int live) {
		super(game, fila, col, live);
		this.letra = "oo";
	}
	
	public Bomb() {
		super(null, 0, 0, 0);
		this.letra = "oo";
	}

	public void weaponAttack(GameObject other) {
		other.receiveBombAttack(Game.DAMAGE);
		//this.live--;
		this.getDamage(Game.DAMAGE);
	}
	
	public boolean recibeMissileAttack(int damage) {
		//this.live--;
		this.getDamage(damage);
		return true;
	}

	@Override
	public void computerAction() {}

	@Override
	public void onDelete() {
		if(this.getLive() <= 0 || this.getFila() > Game.MAXFIL-1) { //
			this.isAlive = false;
		}
	}

	@Override
	public void move() {
		this.fila++;
	}

	@Override
	public String toString() {
		return this.letra;
	}

	@Override
	public String serializer() {
		return "B;"
				+ this.fila + ","
				+ this.columna + ","
				+ this.live;
	}

	@Override
	public GameObject parse(String stringFromFile, Game game2, FileContentsVerifier verifier) {
		String[] words = stringFromFile.split(FileContentsVerifier.separator1);
		String[] coords = words[1].split(FileContentsVerifier.separator2);
		return verifier.verifyWeaponString(stringFromFile, game2) ? 
				new Bomb(game2, Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), Integer.parseInt(words[2])):null;
	}
	
}
