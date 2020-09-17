package tp.p2.game.GameObjects;

import tp.p2.Level;
import tp.p2.game.Game;
import tp.p2.game.GameObjects.Lists.GameObjectBoard;

/**
 * @author Fernando M�ndez Torrubiano
 *
 */

public class BoardInitializer {
	
	private Level level;
	private GameObjectBoard board;
	private Game game;
	
	public GameObjectBoard initialize(Game game, Level level) {
		this.level = level;
		this.game = game;
		board = new GameObjectBoard(Game.MAXFIL, Game.MAXCOL);
		
		initializeOvni();
		initializeRegularAliens();
		initializeDestroyerAliens();
		return board;
	}
	
	private void initializeOvni () {
		GameObject ovni = new Ovni(game, 0, Game.MAXCOL, 1);
		board.add(ovni);
	}

	private void initializeRegularAliens () {
		int fila=2, col=3;
		for (int i = 0; i < this.level.getNumRowsOfRegularAliens(); i++) {
			for (int j = 0; j < this.level.getNumRegularAliensPerRow(); j++) {
				GameObject regular = new RegularAlien(this.game, fila, col, 2);
				board.add(regular);
				col++;
			}
			fila++;
			col = 3;
		}
	}
	
	private void initializeDestroyerAliens() {
		int fila, col;
		switch (level) {
			case EASY: fila = 3; col = 4; break;
			case HARD: fila = 4; col = 3; break;
			default: fila = 5; col = 3; break;
		}
		for (int i = 0; i < this.level.getNumDestroyerAliensPerRow(); i++) {
			GameObject destroyer = new DestroyerAlien(this.game, fila, col, 1);
			board.add(destroyer);
			col++;
		}
	}
}

