package main;

import java.util.*;

/**
 * Abstract class for chess player.
 * 
 * @author John
 */
public abstract class Player {

	protected boolean whitePieces;
	protected boolean humanPlayer;
	
	/**
	 * Abstract method for player turn.
	 * 
	 * @param board - chess board Object
	 * @param moveTracker - list of all game's moves
	 * @param piecesCaptured - list of all pieces captured
	 */
	public abstract void turn(Board board, List<Move> moveTracker, List<Piece> piecesCaptured);
	
	/**
	 * Getter for whitePieces (boolean of whether player is using white pieces)
	 * 
	 * @return whitePieces
	 */
	public boolean isWhitePieces() {
		return whitePieces;
	}
	
	/**
	 * Getter for humanPlayer (boolean of whether player is human)
	 * 
	 * @return humanPlayer
	 */
	public boolean isHumanPlayer() {
		return humanPlayer;
	}
}