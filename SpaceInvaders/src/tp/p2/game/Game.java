package tp.p2.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Random;
import tp.p2.Level;
import tp.p2.controller.Exceptions.CommandExecuteException;
import tp.p2.controller.Exceptions.FileContentsException;
import tp.p2.game.GameObjects.BoardInitializer;
import tp.p2.game.GameObjects.GameObject;
import tp.p2.game.GameObjects.Ovni;
import tp.p2.game.GameObjects.AlienShip;
import tp.p2.game.GameObjects.Shockwave;
import tp.p2.game.GameObjects.SuperMissile;
import tp.p2.game.GameObjects.UCMMissile;
import tp.p2.game.GameObjects.UCMShip;
import tp.p2.game.GameObjects.Interfaces.IPlayerController;
import tp.p2.game.GameObjects.Lists.GameObjectBoard;

/**
 * @author Fernando M�ndez Torrubiano
 *
 */

public class Game implements IPlayerController{
	//Constantes:
	public final static int MAXFIL = 9;
	public final static int MAXCOL = 8;
	public final static int DAMAGE = 1;
	public final static int COSTSUPERMISSILE = 20;
	
	//Atributos
	private int currentCycle;
	private Random rand;
	private Level level;
	private int restCycles;

	GameObjectBoard board;

	private UCMShip player;
	
	private boolean doExit;
	private BoardInitializer initializer;
	
	//Constructor:
	public Game (Level level, Random random){
		this.rand = random;
		this.level = level;
		this.restCycles = this.level.getNumCyclesToMoveOneCell();
		initializer = new BoardInitializer();
		initGame();
	}
	
	public Game (Level level, int restCycles){
		this.rand = new Random(System.currentTimeMillis());
		this.level = level;
		this.restCycles = restCycles;
		this.currentCycle = restCycles;
		initializer = null;
	}
	
	//M�todos:
	public void initGame () {
		currentCycle = 0;
		board = initializer.initialize(this, level);
		player = new UCMShip(this, MAXFIL - 1, MAXCOL / 2);
		board.add(player);
	}

	public Random getRandom() {
		return rand;
	}
	
	public Level getLevel() {
		return level;
	}
	
	public int getCurrentCycle() {
		return this.currentCycle;
	}
	
	public void reset() {
		initGame();
	}
	
	public void addObject(GameObject object) {
		board.add(object);
	}
	
	public String positionToString(int fila, int columna) {
		return board.toString(fila, columna);
	}
	
	public boolean isFinished() {
		return playerWin() || aliensWin() || doExit;
	}
	
	public boolean aliensWin() {
		return !player.isAlive() || AlienShip.haveLanded();
	}
	
	private boolean playerWin () {
		return AlienShip.allDead();
	}
	
	public void update() {
		board.computerAction();
		board.update();
		currentCycle += 1;
		if(this.restCycles == 0) {
			this.restCycles = this.level.getNumCyclesToMoveOneCell();
		}
		else {
			restCycles--;
		}
	}
	
	public boolean isOnBoard(int fila, int columna) {
		return MAXFIL > fila && MAXCOL > columna;
	}
	
	public void exit() {
		doExit = true;
	}
	
	public String infoToString() {
		return "Life: " + this.player.getLive() + "\n" 
				+ "Number of cycles: " + this.currentCycle + "\n"
				+ "Points: " + this.player.getPoints() + "\n"
				+ "Remaining aliens: " + AlienShip.numAliens() + "\n" 
				+ "ShockWave: " + this.player.getShockWave() + "\n"
				+ "Number of SuperMissiles: " + this.player.getNumSuperMissiles() + "\n";
	}
	
	public String toString() {
		//BoardPrinter gp = new BoardPrinter(MAXFIL, MAXCOL);	
		//String tablero = this.infoToString() + gp.toString(this);
		//return tablero;
		
		return this.infoToString();
	}
	
	public String serialize() {
		return "--SPACE INVADERS v2.0--\n\n"
				+ "G;" + this.restCycles + "\n"
				+ "L;" + this.level + "\n"
				+ this.board.serialize() + "\n";			
	}
	
	public String getWinnerMessage () {
		if (playerWin()) return "Player win!";
		else if (aliensWin()) return "Aliens win!";
		else if (doExit) return "Player exits the game";
		else return "This should not happen";
	}
	
	@Override
	public boolean move(String dir, int numCells) throws CommandExecuteException{
		boolean move = false;

		if(this.player.move(dir, numCells)) {
			move = true;
		}
		
		return move;
	}

	@Override
	public boolean shootLaser() {
		boolean shoot = false;
		
		if(this.player.getMisile()) {
			this.enableMissile();
			shoot = true;
		}
		return shoot;
	}

	@Override
	public boolean shockWave() {
		boolean sw = false;
		
		if(this.player.getShockWave()) {
			sw = true;
			this.board.swAttack();
			this.player.deleteSW();
			GameObject ovni = new Ovni(this, 0, Game.MAXCOL, 1);
			this.board.add(ovni);
		}
		
		return sw;
	}
	
	@Override
	public boolean shootSuperLaser() {
		boolean shoot = false;
		
		if(this.player.getSuperMisile() || this.player.getMisile()) {
			this.enableSuperMissile();
			shoot = true;
		}
		
		return shoot;
	}

	@Override
	public void receivePoints(int points) {
		this.player.addPoints(points);
	}

	@Override
	public void enableShockWave() {
		Shockwave sw = new Shockwave(this, this.player.getFila(), this.player.getColumna(), 1);
		this.player.activeSW(sw);
		this.board.add(sw);
	}

	@Override
	public void enableMissile() {
		UCMMissile misil = new UCMMissile(this, this.player.getFila(), this.player.getColumna(), 1);
		this.player.activeMisile(misil);
		this.board.add(misil);
	}
	
	@Override
	public void enableSuperMissile() {
		SuperMissile misil = new SuperMissile(this, this.player.getFila(), this.player.getColumna(), 1);
		this.player.activeSuperMisile(misil);
		this.board.add(misil);
	}

	public boolean buySuperMissile() throws CommandExecuteException {
		boolean buy = false;
		
		if (this.player.getPoints() >= COSTSUPERMISSILE) {
			this.player.subtractPoints(COSTSUPERMISSILE);
			this.player.getSuperMissile();
			buy = true;
		}
		if(!buy) {
			throw new CommandExecuteException("You don't have enough points.");
		}
		
		return buy;
	}

	public int getRestCycles() {
		return this.restCycles;
	}
	
	public void load(BufferedReader inStream) throws IOException, FileContentsException {
		this.board.setCurrentObjects(0);
		String line = inStream.readLine().trim();
		while (line != null && !line.isEmpty()) {
			FileContentsVerifier verifier = new FileContentsVerifier();
			GameObject gameObject = GameObjectGenerator.parse(line, this, verifier);
			if(gameObject == null) {
				throw new FileContentsException("invalid file, unrecognised line prefix");
			}
			board.add(gameObject);
			line = inStream.readLine().trim();
		}
	}

}