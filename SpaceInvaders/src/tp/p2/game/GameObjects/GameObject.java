package tp.p2.game.GameObjects;

import tp.p2.Level;
import tp.p2.game.FileContentsVerifier;
import tp.p2.game.Game;
import tp.p2.game.GameObjects.Interfaces.IAttack;

/**
 * @author Fernando M�ndez Torrubiano
 *
 */

public abstract class GameObject implements IAttack {
	//Atributos est�ticos:
	protected int fila, columna;
	protected int live;
	protected boolean isAlive;
	protected Game game;
	protected Level level;
	
	//Constrcutor:
	public GameObject(Game game, int fila, int col, /*Coordenadas iniciales*/ int live) {
		this.fila = fila;
		this.columna = col;
		this.game = game;
		this.level = this.game.getLevel();
		this.live = live;
		this.isAlive = true;
	}
	
	//M�todos:
	public int getFila() {
		return this.fila;
	}

	public int getColumna() {
		return this.columna;
	}

	public boolean isAlive() {
		return this.isAlive;
	}

	public int getLive() {
		return this.live;
	}
	
	public boolean isOnPosition(int x, int y) {
		return this.fila == x && this.columna == y;
	}

	public void getDamage (int damage) {
		this.live = damage >= this.live ? 0 : this.live - damage;
	}
	
	public boolean isOut() {
		return !game.isOnBoard(this.fila, this.columna);
	}
	
	public void update() {
		this.move();
		if(this.isAlive != false) { this.onDelete(); }
	}

	public abstract void computerAction();
	public abstract void onDelete();
	public abstract void move();
	public abstract String toString();
	public abstract String serializer();
	public abstract GameObject parse(String stringFromFile, Game game2, FileContentsVerifier verifier);

}

