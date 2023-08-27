package main;

/**
 * Class for spot on chess board.
 * 
 * @author John Vnek
 */
public class Spot {

	private int x;
	private int y;
	private Piece piece;
	
	/**
	 * Constructor for Spot.
	 * 
	 * @param x - x coordinate of Spot
	 * @param y - y coordinate of Spot
	 * @param piece - piece at this Spot
	 */
	public Spot(int x, int y, Piece piece) {
		this.x = x;
		this.y = y;
		this.piece = piece;
	}
	
	/**
	 * Getter for x coordinate.
	 * 
	 * @return x
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Getter for y coordinate.
	 *
	 * @return y
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Getter for piece at Spot.
	 * 
	 * @return piece
	 */
	public Piece getPiece() {
		return piece;
	}
	
	/**
	 * Setter for piece at Spot.
	 * 
	 * @param piece - new piece at Spot
	 */
	public void setPiece(Piece piece) {
		this.piece = piece;
	}
	
	/**
	 * Equals method to determine if two Spots are the same.
	 * 
	 * @param other - other Spot Object
	 * @return true if Spots the same, false if not
	 */
	public boolean equals(Spot other) {
		return this.x == other.getX() && this.y == other.getY();
	}
	
	/**
	 * String representation of Spot's coordinates.
	 * 
	 * @return String of coordinates
	 */
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
