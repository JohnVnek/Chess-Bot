package main;

import java.util.*;

/**
 * Class for human chess player.
 * 
 * @author John Vnek
 */
public class HumanPlayer extends Player {

	/**
	 * Constructor for HumanPlayer Object.
	 * <p>
	 * Make the human player the white pieces.
	 */
	public HumanPlayer() {
		this.whitePieces = true;
		this.humanPlayer = true;
	}
	
	/**
	 * Handles one turn taken by human player.
	 * <p>
	 * Prompts user for move until valid move inputted.
	 * 
	 * @param board - chess board Object
	 * @param moveTracker - list of all game's moves
	 * @param piecesCaptured - list of all pieces captured
	 */
	public void turn(Board board, List<Move> moveTracker, List<Piece> piecesCaptured) {
		boolean turnTaken = false;
		Scanner input = new Scanner(System.in);
		
		while (!turnTaken) {
			System.out.println("Starting Spot");
			System.out.print("X: ");
			int startX = input.nextInt();
			System.out.print("Y: ");
			int startY = input.nextInt();
			
			System.out.println("Ending Spot");
			System.out.print("X: ");
			int endX = input.nextInt();
			System.out.print("Y: ");
			int endY = input.nextInt();
			
			turnTaken = playerMove(board, startX, startY, endX, endY, piecesCaptured);
			
			if (turnTaken == false) {
				System.out.println("\nInvalid move, try again\n");
			} else {
				moveTracker.add(new Move(this, board.getSpot(startX, startY), board.getSpot(endX, endY)));
			}
		}
	}
	
	/**
	 * Checks if move is valid, and makes that move if so.
	 * 
	 * @param board - chess board Object
	 * @param col1 - x coordinate of first spot
	 * @param row1 - y coordinate of first spot
	 * @param col2 - x coordinate of second spot
	 * @param row2 - y coordinate of second spot
	 * @param piecesCaptured - list of all pieces captured
	 * @return true if valid move can be made, false if not
	 */
	private boolean playerMove(Board board, int col1, int row1, int col2, int row2, List<Piece> piecesCaptured) {
		
		if(!checkBounds(col1, row1, col2, row2)) {
			return false;
		}
	
		Spot start = board.getSpot(col1, row1);
		Spot end = board.getSpot(col2, row2);
		
		Piece startPiece = start.getPiece();
		Piece endPiece = end.getPiece();
		
		if (!startPiece.checkMove(board, this, start, end)) {
			return false;
		} else {
			Piece temp = start.getPiece();
			start.setPiece(null);
			if (end.getPiece() != null) {
				end.getPiece().setCaptured(true);
			}
			end.setPiece(temp);
			
			if (endPiece != null && endPiece.getCaptured() == true) {
				piecesCaptured.add(endPiece);
			}
			return true;
		}
	}
	
	/**
	 * Checks if inputted x and y values are within bounds of board.
	 * 
	 * @param col1 - x coordinate of first spot
	 * @param row1 - y coordinate of first spot
	 * @param col2 - x coordinate of second spot
	 * @param row2 - y coordinate of second spot
	 * @return true if within bounds, false if not
	 */
	private boolean checkBounds(int col1, int row1, int col2, int row2) {
		if ( (col1 < 0 || col1 > 7 || row1 < 0 || row1 > 7) ||
				(col2 < 0 || col2 > 7 || row2 < 0 || row2 > 7) ) {
			System.out.println("Coordinates not within bounds");
			return false;
		}
		return true;
	}
	
}
