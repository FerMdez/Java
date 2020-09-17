package tp.p2.game.GameObjects.Interfaces;

import tp.p2.game.GameObjects.GameObject;
/**
 * @author Fernando M�ndez Torrubiano
 *
 */
public interface IAttack {
	default boolean performAttack(GameObject other) {return false;};

	default boolean receiveMissileAttack(int damage) {return false;};
	default boolean receiveBombAttack(int damage) {return false;};
	default boolean receiveShockWaveAttack(int damage) {return false;};
	default boolean recibeExplodeAttack(int damage) {return false;};
}

