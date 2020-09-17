package tp.p2.game.GameObjects.Lists;

import tp.p2.game.Game;
//import tp.p2.game.Game;
import tp.p2.game.GameObjects.GameObject;
/**
 * @author Fernando M�ndez Torrubiano
 *
 */
public class GameObjectBoard {
	//Atributos:
	private GameObject[] objects;
	private int currentObjects;
	
	//Constructor:
	public GameObjectBoard (int width, int height) {
		this.objects = new GameObject[width * height];
		this.currentObjects = 0;
	}
	
	//M�todos:
	private int getCurrentObjects() {
		return this.currentObjects;
	}
	
	public void setCurrentObjects(int currentObjects) {
		this.currentObjects = currentObjects;
	}
	
	public void add (GameObject object) {
		this.objects[this.currentObjects] = object;
		this.currentObjects++;
	}
	
	/*
	private GameObject getObjectInPosition(int fila, int columna) {
		int pos = getIndex(fila, columna);
		return this.objects[pos];
	}
	*/
	
	private int getIndex(int fila, int columna) {
		boolean encontrado = false;
		int i = this.getCurrentObjects()-1;
		
		while(!encontrado && i >= 0) {
			if(this.objects[i].getFila() == fila && this.objects[i].getColumna() == columna) {
				encontrado = true;
			}
			else {
				i--;
			}
		}
		
		return i;
	}

	private void remove(GameObject object) {
		int pos = getIndex(object.getFila(), object.getColumna());
		
		this.objects[pos] = null;
		for (int i = pos; i < this.getCurrentObjects(); i++) {
			this.objects[i] = this.objects[i+1];
		}
		this.currentObjects--;
	}
	
	public void update() {
		for (int i = 0; i < this.getCurrentObjects(); i++) {
			this.objects[i].update();
			this.checkAttacks(this.objects[i]);
		}
		this.removeDead();
	}
	
	private void checkAttacks(GameObject object) {
		for (int i = 0; i < this.getCurrentObjects(); i++) {
			if (object != this.objects[i]) {
				if(object.performAttack(this.objects[i])) {
					object.onDelete();
					this.objects[i].onDelete();
				}
			}
		}
	}
	
	public void computerAction() {
		for (int i = 0; i < this.getCurrentObjects(); i++) {
			this.objects[i].computerAction();
		}
	}
	
	private void removeDead() {
		for (int i = 0; i < this.getCurrentObjects(); i++) {
			if(!this.objects[i].isAlive()) {
				remove(this.objects[i]);
			}
		}
	}
	
	public void swAttack() {
		for (int i = 0; i < this.getCurrentObjects(); i++) {
			this.objects[i].receiveShockWaveAttack(Game.DAMAGE);
		}
	}

	public String toString(int fila, int columna) {
		String print = "";
		
		for (int i = 0; i < this.getCurrentObjects(); i++) {
			if(this.objects[i].getFila() == fila && this.objects[i].getColumna() == columna) {
				print += this.objects[i].toString();
			}
		}
		
		return print;
	}

	public String serialize() {
		String serialize = "";
		
		for (int i = 0; i < this.getCurrentObjects(); i++) {
			serialize += this.objects[i].serializer() + "\n";
		}
		
		return serialize;
	}

}