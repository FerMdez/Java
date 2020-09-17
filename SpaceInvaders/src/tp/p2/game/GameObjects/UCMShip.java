package tp.p2.game.GameObjects;

import tp.p2.controller.Exceptions.CommandExecuteException;
import tp.p2.game.FileContentsVerifier;
//import tp.p2.controller.ShockwaveCommand;
import tp.p2.game.Game;
//import tp.p2.util.MyStringUtils;

/**
 * @author Fernando M�ndez Torrubiano
 *
 */

public class UCMShip extends Ship {
	//Atributos:
	private Weapon misil;
	private Shockwave shockwave;
	private String letra;
	private int points;
	private int numSuperMissiles;
	
	//Constructor:
	public UCMShip(Game game, int fila, int col) {
		super(game, fila, col, 3);
		this.misil = null;
		this.shockwave = null;
		this.letra = "^__^";
		this.points = 0;
		this.numSuperMissiles = 0;
	}

	public UCMShip() {
		super(null, Game.MAXFIL-1, 4, 3);
		this.misil = null;
		this.shockwave = null;
		this.letra = "^__^";
		this.points = 0;
		this.numSuperMissiles = 0;
	}

	public void activeMisile(UCMMissile misil) {
		this.misil = misil;
	}
	
	public void activeSW(Shockwave sw) {
		this.shockwave = sw;
	}
	
	public void activeSuperMisile(SuperMissile supermisil) {
		if(this.numSuperMissiles > 0) { this.misil = supermisil; }
	}
	
	public void deleteMisil() {
		if(this.getSuperMisile()) { this.numSuperMissiles--; }
		this.misil = null;
	}
	
	public void deleteSW() {
		this.shockwave = null;
	}
	
	public boolean receiveBombAttack(int damage) {
		this.live -= damage;
		return true;
	}

	@Override
	public void computerAction() {}

	@Override
	public void onDelete() {
		if((this.misil != null) && (!this.misil.isAlive)) { //(this.misil.getLive() == 0 || this.misil.getFila() < 0)
			this.deleteMisil();
		}
		
		if(this.live <= 0) {
			this.isAlive = false;
		}
	}
	public boolean move(String dir, int nCasillas) throws CommandExecuteException{
		boolean move = false;
		
		switch(dir) {
			case "right":
				if(this.columna + nCasillas < Game.MAXCOL) {
					switch(nCasillas) {
						case 1: this.columna++; move = true; break;
						case 2: this.columna += 2; move = true; break;
						default: break;
					}
				}
			break;
			case "left":
				if(this.columna - nCasillas >= 0) {
					switch(nCasillas) {
						case 1: this.columna--; move = true; break;
						case 2: this.columna -= 2; move = true; break;
						default: this.columna += 0; move = false; break;
					}
				}
			break;
		}
		if(!move) {
			throw new CommandExecuteException("You'd come out of the limits of the board.");
		}
		
		return move;
	}
	
	public void addPoints(int points) {
		this.points += points;
	}
	
	public void subtractPoints(int points) {
		this.points -= points;
	}
	
	public int getPoints() {
		return this.points;
	}
	
	public boolean getMisile() {
		boolean misilEnable = false;
		
		if(this.misil == null) {
			misilEnable = true;
		}
		
		return misilEnable;
	}
	
	public boolean getShockWave() {
		boolean sw = false;
		
		if(this.shockwave != null) {
			sw = true;
		}
		
		return sw;
	}
	
	public boolean getSuperMisile() {
		boolean smEnable = false;
		
		if(this.numSuperMissiles > 0) {
			smEnable = true;
		}
		
		return smEnable;
	}
	
	public void getSuperMissile() {
		this.numSuperMissiles++;
	}
	
	public int getNumSuperMissiles() {
		return this.numSuperMissiles;
	}
	
	@Override
	public void move() {}

	@Override
	public String toString() {
		return this.letra /*+ "[" + this.live + "]"*/;
	}

	@Override
	public String serializer() {
		return "P;" 
				+ this.fila + "," 
				+ this.columna + ";" 
				+ this.live + ";" 
				+ this.points +  ";"
				+ this.getShockWave() + ";"
				+ this.numSuperMissiles;
	}

	@Override
	public GameObject parse(String stringFromFile, Game game2, FileContentsVerifier verifier) {
		String[] words = stringFromFile.split(FileContentsVerifier.separator1);
		String[] coords = words[1].split(FileContentsVerifier.separator2);
		return verifier.verifyPlayerString(stringFromFile, game2, 3) ? 
				new UCMShip(game2, Integer.parseInt(coords[0]), Integer.parseInt(coords[1])):null;
	}
}