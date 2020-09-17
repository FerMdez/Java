package tp.p2.game.GameObjects.Interfaces;

import tp.p2.controller.Exceptions.CommandExecuteException;
/**
 * @author Fernando M�ndez Torrubiano
 *
 */
public interface IPlayerController {
		// PLAYER ACTIONS	
		public boolean move (String dir, int numCells) throws CommandExecuteException;
		public boolean shootLaser();
		public boolean shockWave();
		public boolean shootSuperLaser();
		
		// CALLBACKS
		public void receivePoints(int points);
		public void enableShockWave();
		public void enableMissile();
		public void enableSuperMissile();
}
