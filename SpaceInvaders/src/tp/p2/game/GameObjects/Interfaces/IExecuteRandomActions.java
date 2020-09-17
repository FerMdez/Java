package tp.p2.game.GameObjects.Interfaces;

import tp.p2.game.Game;
/**
 * @author Fernando M�ndez Torrubiano
 *
 */
public interface IExecuteRandomActions {
	
	static boolean canGenerateRandomOvni(Game game){
		return game.getRandom().nextDouble() < game.getLevel().getOvniFrequency();
	}
	
	static boolean canGenerateRandomBomb(Game game){
		return game.getRandom().nextDouble() < game.getLevel().getShootFrequency();	
	}
	
}