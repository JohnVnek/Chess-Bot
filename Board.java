package main;

/**
 * Class for creating a chess board
 * 
 * @author John Vnek
 */
public class Board {

	private Spot[][] board;
	private final int NUM_ROWS = 8;
	private final int NUM_COLUMNS = 8;
	
	/**
	 * Constructor for board Object.
	 * <p>
	 * Sets up all the black and white pieces.
	 */
	public Board() {
		board = new Spot[NUM_ROWS][NUM_COLUMNS];
		board[0][0] = new Spot(0, 0, new Rook(true));
		board[0][1] = new Spot(1, 0, new Knight(true));
		board[0][2] = new Spot(2, 0, new Bishop(true));
		board[0][3] = new Spot(3, 0, new Queen(true));
		board[0][4] = new Spot(4, 0, new King(true));
		board[0][5] = new Spot(5, 0, new Bishop(true));
		board[0][6] = new Spot(6, 0, new Knight(true));
		board[0][7] = new Spot(7, 0, new Rook(true));
		
		
		for (int i = 0; i < NUM_COLUMNS; i++) {
			board[1][i] = new Spot(i, 1, new Pawn(true));
		}
		
		
		for (int i = 2; i < NUM_ROWS - 2; i++) {
			for (int j = 0; j < NUM_COLUMNS; j++) {
				board[i][j] = new Spot(j, i, null);
			}
		}
		
		for (int i = 0; i < NUM_ROWS; i++) {
			board[6][i] = new Spot(i, 6, new Pawn(false));
		}
		
		
		board[7][0] = new Spot(0, 7, new Rook(false));
		board[7][1] = new Spot(1, 7, new Knight(false));
		board[7][2] = new Spot(2, 7, new Bishop(false));		
		board[7][3] = new Spot(3, 7, new King(false));
		board[7][4] = new Spot(4, 7, new Queen(false));
		board[7][5] = new Spot(5, 7, new Bishop(false));
		board[7][6] = new Spot(6, 7, new Knight(false));
		board[7][7] = new Spot(7, 7, new Rook(false));
	}
	
	/**
	 * Getter for individual spot on board.
	 * <p>
	 * The col and row parameters are swapped when used on the array of Spot.
	 * Does so because col == x and row == y coordinates.
	 * 
	 * @param col - x coordinate of spot
	 * @param row - y coordinate of spot
	 * @return spot at coordinates (x, y)
	 */
	public Spot getSpot(int col, int row) {
		return board[row][col];
	}
	
	/**
	 * Getter for Spot array.
	 * 
	 * @return board - array of type Spot
	 */
	public Spot[][] getBoardSpots() {
		return board;
	}
	
	/**
	 * Getter for number of rows.
	 * 
	 * @return NUM_ROWS - number of rows
	 */
	public int getNumRows() {
		return NUM_ROWS;
	}
	
	/**
	 * Getter for number of columns.
	 * 
	 * @return NUM_COLUMNS - number of columns
	 */
	public int getNumColumns() {
		return NUM_COLUMNS;
	}
	
	/**
	 * Returns String representation of entire chess board including all pieces
	 * 
	 * @return String of chess board
	 */
	public String toString() {
		String boardStr = "";
		
		for (int i = NUM_ROWS - 1; i > -1 ; i--) {
			boardStr += border();
			boardStr += boxCoords(i);
			boardStr += blankRow();
			boardStr += blankRow();
			boardStr += blankRow();
			boardStr += boxContents(i);
		}
		
		boardStr += border();
		return boardStr;
	}
	
	/**
	 * Used to create empty row of chess board String.
	 * 
	 * @return String of empty row.
	 */
	private String blankRow() {
		String row = "";
		
		row += "|";
		for (int j = 0; j < NUM_COLUMNS; j++) {
			row += "\t\t|";
		}
		row += "\n";
		
		return row;
	}
	
	/**
	 * Used to create String with coordinates of boxes in row.
	 * 
	 * @param row - current row of board
	 * @return String of row with coordinates
	 */
	private String boxCoords(int row) {
		String coords = "| ";
		for (int j = 0; j < NUM_COLUMNS; j++) {
			coords += board[row][j];
			coords += "   ";
			coords += "\t";
			coords += "|";
			coords += " ";
		}
		return coords + "\n";
	}
	
	/**
	 * Used to create String with pieces in boxes in row.
	 * 
	 * @param row - current row of board
	 * @return String of row with pieces in boxes
	 */
	private String boxContents(int row) {
		String box = "| ";
		for (int j = 0; j < NUM_COLUMNS; j++) {
			if (board[row][j].getPiece() != null) {
				box += board[row][j].getPiece();
			} else {
				box += "   ";
			}
			
			box += "   ";
			box += "\t";
			box += "|";
			box += " ";
		}
		
		return box + "\n";
	}
	
	/**
	 * Used to create String of chess board border.
	 * 
	 * @return String of board border
	 */
	private String border() {
		String border = "|";
		for (int i = 0; i < NUM_COLUMNS; i++) {
			border += "---------------|";
		}
		return border + "\n";
	}
}
