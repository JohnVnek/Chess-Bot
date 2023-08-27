package main;

/**
 * Class for Pawn piece.
 * <p>
 * Ensures that move trying to be made is a valid move for this piece type.
 * 
 * @author John Vnek
 */
public class Pawn extends Piece {

	/**
	 * Constructor for Pawn.
	 * <p>
	 * Value is 1.
	 * 
	 * @param isWhite - true if white, false if not
	 */
	public Pawn(boolean isWhite) {
		super(isWhite, 1);
	}

	/**
	 * Checks if inputted move is valid move for Pawn.
	 * 
	 * @param board - chess board Object
	 * @param start - spot where piece starts
	 * @param end - spot where piece will end
	 */
	protected boolean validMove(Board board, Spot start, Spot end) {
		if (end.getPiece() != null && this.isWhite == end.getPiece().getIsWhite()) {
			return false;
		}
		
		int xDiff = end.getX() - start.getX();
		int absXDiff = Math.abs(xDiff);
		
		int yDiff = end.getY() - start.getY();
		int absYDiff = Math.abs(yDiff);
		
		
		if(yDiff < 0 && this.isWhite == true || yDiff > 0 && this.isWhite == false) {
			return false;
		}
	
		if ( (absXDiff == 1 && absYDiff != 1) || (absXDiff == 1 && absYDiff == 1 && end.getPiece() == null)) {
			return false;
		}
		
		Spot front;
		if (this.isWhite) {
			front = board.getSpot(start.getX(), start.getY() + 1); // change to not create bound issue
		} else {
			front = board.getSpot(start.getX(), start.getY() - 1);
		}
				
		if ((front.getPiece() != null || end.getPiece() != null) && (absYDiff > 0 && absXDiff == 0)) {
			return false;
		}
		
		return absYDiff > 0 && absYDiff < 3 && absXDiff < 2;
	}
	
	/**
	 * Returns string with piece name
	 */
	public String toString() {
		return super.toString() + "Pawn";
	}
}
