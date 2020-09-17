package tp.p2.game;
/**
 * @author Fernando M�ndez Torrubiano
 *
 */
public interface GamePrinter {
	String toString(Game game);
	public GamePrinter parse(String name);
	public String helpText();
	void setGame(Game game); //A�adido en la versi�n 3.
}
