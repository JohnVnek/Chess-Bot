package main;

/**
 * Class for keeping track of player moves.
 * 
 * @author John Vnek
 */
public class Move {

	private Player player;
	private Spot start;
	private Spot end;
	private Piece pieceMoved;
	private Piece pieceCaptured;
	
	/**
	 * Constructor for Move.
	 * 
	 * @param player - the player making move
	 * @param start - the starting location of move
	 * @param end - the ending location of move
	 */
	public Move(Player player, Spot start, Spot end) {
		this.player = player;
		this.start = start;
		this.end = end;
		this.pieceMoved = start.getPiece();
		this.pieceCaptured = end.getPiece();
	}
	
	/**
	 * Constructor for Move that requires piece parameter.
	 * 
	 * @param player - the player making move
	 * @param start - the starting location of move
	 * @param end - the ending location of move
	 * @param piece - piece making move
	 */
	public Move(Player player, Spot start, Spot end, Piece pieceMoved) {
		this.player = player;
		this.start = start;
		this.end = end;
		this.pieceMoved = pieceMoved;
		this.pieceCaptured = end.getPiece();
	}
	
	/**
	 * Setter for pieceCaptured field.
	 * 
	 * @param piece - piece that was captured
	 */
	public void setPieceCaptured(Piece piece) {
		this.pieceCaptured = piece;
	}
	
	/**
	 * Getter for pieceCaptured field.
	 * 
	 * @return pieceCaptured
	 */
	public Piece getPieceCaptured() {
		return this.pieceCaptured;
	}
	
	/**
	 * Getter for starting Spot.
	 * 
	 * @return starting Spot
	 */
	public Spot getStart() {
		return start;
	}
	
	/**
	 * Getter for ending Spot.
	 * 
	 * @return ending Spot
	 */
	public Spot getEnd() {
		return end;
	}
	
	/**
	 * Getter for piece moved.
	 * 
	 * @return pieceMoved
	 */
	public Piece getPieceMoved() {
		return pieceMoved;
	}
	
	/**
	 * Returns String representation of piece that moved and where. 
	 * 
	 * @return String representation of Move
	 */
	public String toString() {
		return "" + pieceMoved + " " + start + " " + end;
	}
}
