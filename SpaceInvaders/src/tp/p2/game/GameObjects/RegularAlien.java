package tp.p2.game.GameObjects;

import tp.p2.game.FileContentsVerifier;
import tp.p2.game.Game;

/**
 * @author Fernando M�ndez Torrubiano
 *
 */
public class RegularAlien extends AlienShip {
	//Atributos:
	private String letra;
	private boolean explosive;
	
	//Constructor:
	public RegularAlien(Game game, int fila, int col, int live) {
		super(game, fila, col, live);
		this.letra = "C";
		this.explosive = false;
		numAliens++;
	}
	
	public RegularAlien() {
		super(null, 0, 0, 2);
		this.letra = "C";
		this.explosive = false;
		numAliens++;
	}
	
	public RegularAlien(Game game, int fila, int col, int live, String letra) {
		super(game, fila, col, live);
		this.letra = letra;
		this.explosive = false;
		numAliens++;
	}

	public int getLive() {
		return this.live;
	}
	
	public boolean isDead() {
		return this.getLive() > 0;
	}

	@Override
	public void computerAction() {
		if((!this.explosive) && (this.game.getRandom().nextDouble() < this.level.getTurnExplodeFrequency())) {
			this.explosive = true;
			this.letra = "E";
		}
	}

	@Override
	public void onDelete() {
		if(this.live == 0) {
			if(!this.explosive) { this.isAlive = false; }
				numAliens--;
				if(numAliens == 0) { allDead = true; }
			
			this.game.receivePoints(5);
		}
	}

	@Override
	public String toString() {
		return this.letra + "[" + this.live + "]";
	}
	
	@Override
	public String serializer() {
		String letra = "R";
		if(explosive) {letra = this.letra;}
		return letra + ";"
				+ this.fila + ","
				+ this.columna + ";"
				+ this.live + ";"
				+ this.game.getRestCycles() + ";"
				+ this.direction;
	}
	
	//M�todos si la nave es explosiva:
	public void explodeAttack(GameObject other) {
		other.recibeExplodeAttack(Game.DAMAGE);
	}
	
	public boolean performAttack(GameObject other) {
		boolean attack = false;
		
		if(this.explosive && this.live == 0) {
			if(other.isAlive()) {
				if(other.isOnPosition(fila-1, columna) || other.isOnPosition(fila+1, columna) || other.isOnPosition(fila, columna-1) || other.isOnPosition(fila, columna+1)) { 
					this.explodeAttack(other); //restar vida
					attack = true;
				}
			}
			this.isAlive = false;
		}
		
		return attack;
	}

	@Override
	public GameObject parse(String stringFromFile, Game game2, FileContentsVerifier verifier) {
		String[] words = stringFromFile.split(FileContentsVerifier.separator1);
		String[] coords = words[1].split(FileContentsVerifier.separator2);
		if(words[0].equals("R")) {
			return verifier.verifyRegularShipString(stringFromFile, game2, 2) ? 
						new RegularAlien(game2, Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), Integer.parseInt(words[2])):null;
		}
		else {
			return verifier.verifyExplosiveShipString(stringFromFile, game2, 2) ? 
					new RegularAlien(game2, Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), Integer.parseInt(words[2]), words[0]):null;
		}
	}
}
