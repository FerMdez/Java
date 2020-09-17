package tp.p2.game;
/**
 * @author Fernando M�ndez Torrubiano
 *
 */
public class Stringifier implements GamePrinter{

	@Override
	public String toString(Game game) {
		return game.serialize();
	}

	@Override
	public GamePrinter parse(String name) {
		GamePrinter tablero = null;
		
		if(name.equals("serializer") || name.equals("stringifier")) {
			tablero = this;
		}
		
		return tablero;
	}

	@Override
	public String helpText() {
		return "prints the game as plain text";
	}

	@Override
	public void setGame(Game game) {
		// TODO Auto-generated method stub
		
	}
}
