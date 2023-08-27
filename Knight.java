package main;

/**
 * Class for Knight piece.
 * <p>
 * Ensures that move trying to be made is a valid move for this piece type.
 * 
 * @author John Vnek
 */
public class Knight extends Piece {

	/**
	 * Constructor for Knight.
	 * <p>
	 * Value is 5.
	 * 
	 * @param isWhite - true if white, false if not
	 */
	public Knight(boolean isWhite) {
		super(isWhite, 4);
	}
	
	/**
	 * Checks if inputted move is valid move for Knight.
	 * 
	 * @param board - chess board Object
	 * @param start - spot where piece starts
	 * @param end - spot where piece will end
	 */
	protected boolean validMove(Board board, Spot start, Spot end) {
		if (end.getPiece() != null && this.isWhite == end.getPiece().getIsWhite()) {
			return false;
		}
		
		int xDiff = Math.abs(end.getX() - start.getX());
		int yDiff = Math.abs(end.getY() - start.getY());
		
		return xDiff * yDiff == 2;
	}

	/**
	 * Returns string with piece name
	 */
	public String toString() {
		return super.toString() + "Knight";
	}
}
