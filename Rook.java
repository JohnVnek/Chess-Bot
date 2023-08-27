package main;

/**
 * Class for Rook piece.
 * <p>
 * Ensures that move trying to be made is a valid move for this piece type.
 * 
 * @author John Vnek
 */
public class Rook extends Piece {

	/**
	 * Constructor for Rook.
	 * <p>
	 * Value is 5.
	 * 
	 * @param isWhite - true if white, false if not
	 */
	public Rook(boolean isWhite) {
		super(isWhite, 5);
	}

	/**
	 * Checks if inputted move is valid move for Queen.
	 * 
	 * @param board - chess board Object
	 * @param start - spot where piece starts
	 * @param end - spot where piece will end
	 */
	protected boolean validMove(Board board, Spot start, Spot end) {
		int xDiff = end.getX() - start.getX();
		int absXDiff = Math.abs(xDiff);
		
		int yDiff = end.getY() - start.getY();
		int absYDiff = Math.abs(yDiff);
		
		if (xDiff + yDiff != xDiff && yDiff + xDiff != yDiff) {
			return false;
		}
		
		int xStep = 0;
		if (xDiff != 0) {
			xStep = xDiff / absXDiff;
		}
		
		int yStep = 0;
		if (yDiff != 0) {
			yStep = yDiff / absYDiff;
		}
		
		if (end.getPiece() != null && this.isWhite == end.getPiece().getIsWhite()) {
			return false;
		} else {
			return validMove(xStep, yStep, board, board.getSpot(start.getX() + xStep, start.getY() + yStep), end);
		}
	}
	
	/**
	 * Recursively checks spots between original start and end.
	 * <p>
	 * Start coordinates change by xStep and yStep until start equals end.
	 * If obstacle (piece) found between original start and end, return false.
	 * 
	 * @param xStep - amount x changes per move
	 * @param yStep - amount y changes per move
	 * @param board - chess board Object
	 * @param start - starting spot of piece
	 * @param end - ending spot of piece
	 * @return true if start is same as end, false if not
	 */
	private boolean validMove(int xStep, int yStep, Board board, Spot start, Spot end) {
		int x = start.getX();
		int y = start.getY();
		
		if (x == end.getX() && y == end.getY()) {
			return true;
		} else if (start.getPiece() != null) {
			return false;
		} else {
			return validMove (xStep, yStep, board, board.getSpot(x + xStep, y + yStep), end);
		}
	}

	/**
	 * Returns string with piece name
	 */
	public String toString() {
		return super.toString() + "Rook";
	}
}
