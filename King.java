package main;

/**
 * Class for King piece.
 * <p>
 * Ensures that move trying to be made is a valid move for this piece type.
 * 
 * @author John Vnek
 */
public class King extends Piece {

	/**
	 * Constructor for King.
	 * <p>
	 * Value is 25.
	 * 
	 * @param isWhite - true if white, false if not
	 */
	public King(boolean isWhite) {
		super(isWhite, 25);
	}

	/**
	 * Checks if inputted move is valid move for Bishop.
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
		
		if (xDiff == 1 && yDiff == 1 && end.getPiece() != null && end.getPiece().getIsWhite() != this.isWhite) {
			return true;
		}
		
		return xDiff + yDiff == 1;
	}

	/**
	 * Returns string with piece name
	 */
	public String toString() {
		return super.toString() + "King";
	}
}
