package tp.p2.game;

import tp.p2.util.MyStringUtils;
/**
 * @author Fernando M�ndez Torrubiano
 *
 */
public class BoardPrinter implements GamePrinter {
	private int numRows; 
	private int numCols;
	private String[][] board;
	private final String space = " ";
	
	public BoardPrinter(int maxfil, int maxcol) {
		this.numRows = maxfil;
		this.numCols = maxcol;
	}

	@Override
	public String toString(Game game) {
		encodeGame(game);
		
		int cellSize = 7;
		int marginSize = 2;
		String vDelimiter = "|";
		String hDelimiter = "-";
		
		String rowDelimiter = MyStringUtils.repeat(hDelimiter, (numCols * (cellSize + 1)) - 1); 
		String margin = MyStringUtils.repeat(space, marginSize);
		String lineDelimiter = String.format("%n%s%s%n", margin + space, rowDelimiter);
		
		StringBuilder str = new StringBuilder();
		
		str.append(lineDelimiter);
		
		for(int i=0; i<numRows; i++) {
			str.append(margin).append(vDelimiter);
			for (int j=0; j<numCols; j++) {
				str.append(MyStringUtils.centre(board[i][j], cellSize)).append(vDelimiter);
			}
			str.append(lineDelimiter);
		}
		return str.toString();
	}
	
	private void encodeGame(Game game) {
		this.board = new String[numRows][numCols];
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numCols; j++) {
				board[i][j] =  game.board.toString(i, j);
			}
		}
	}
	
	@Override
	public GamePrinter parse(String name) {
		GamePrinter tablero = null;
		
		if(name.equals("boardprinter") || name.equals("board")) {
			tablero = this;
		}
		
		return tablero;
	}

	@Override
	public String helpText() {
		return "prints the game formatted as a board of dimension: " + Game.MAXFIL + "," + Game.MAXCOL;
	}

	@Override
	public void setGame(Game game) {
		//
	}

}