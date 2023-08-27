package main;

/**
 * Abstract class for chess piece.
 * 
 * @author John Vnek
 */
public abstract class Piece {
	
	protected boolean captured;
	protected boolean isWhite;
	private int value;

	/**
	 * Constructor for Piece Object.
	 * 
	 * @param isWhite - boolean of whether piece is white (true) or black (false)
	 * @param value - value of piece compared to others
	 */
	public Piece(boolean isWhite, int value) {
		this.captured = false;
		this.isWhite = isWhite;
		this.value = value;
	}
	
	/**
	 * Used to check if proposed move is a valid move.
	 * 
	 * @param board - chess board Object
	 * @param player - player making the move
	 * @param start - spot where piece starts
	 * @param end - spot where piece ends up after move
	 * @return true is move valid, false if not.
	 */
	public boolean checkMove(Board board, Player player, Spot start, Spot end) {
		if (player.isWhitePieces() != this.isWhite || !validMove(board, start, end)) {
			//System.out.println("Invalid move");
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Piece type specific method for determining if move confides by movement rules of piece.
	 * 
	 * @param board - chess board Object
	 * @param start - spot where piece starts
	 * @param end - spot where piece ends up after move
	 * @return true if move valid, false if not
	 */
	protected abstract boolean validMove(Board board, Spot start, Spot end);

	/**
	 * Getter for captured field.
	 * 
	 * @return captured - true if captured, false if not
	 */
	public boolean getCaptured() {
		return captured;
	}
	
	/**
	 * Setter for captured field.
	 * 
	 * @param captured - true if captured, false if not
	 */
	public void setCaptured(boolean captured) {
		this.captured = captured;
	}
	
	/**
	 * Getter for isWhite field.
	 * 
	 * @return isWhite - true if white, false if black.
	 */
	public boolean getIsWhite() {
		return isWhite;
	}
	
	/**
	 * Getter for value of piece.
	 * 
	 * @return value
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * String representation of piece's color.
	 * 
	 * return String of piece's color
	 */
	public String toString() {
		if (this.getIsWhite() == true) {
			return "(W) ";
		} else {
			return "(B) ";
		}
	}
}
