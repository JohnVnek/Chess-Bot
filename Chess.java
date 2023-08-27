package main;

import java.util.*;

/**
 * Class for playing a game of chess.
 * 
 * @author John Vnek
 */
public class Chess {
	
	private Board board = new Board();
	private List<Move> moveTracker = new LinkedList<>();
	private List<Piece> piecesCaptured = new LinkedList<>();
	private int numCaptured = 0;
	private Player[] players;
	
	/**
	 * Used to play a game of chess.
	 * <p>
	 * Continues the game until the king is captured on either side. 
	 */
	public void play() {
		players = new Player[2];
		players[0] = new HumanPlayer();
		players[1] = new ComputerPlayer(players[0]);

		System.out.println(board.toString());
		
		while (!checkKingCaptured()) {
			for (int i = 0; i < players.length; i++) {
				if (!checkKingCaptured()) {
					System.out.printf("Player %d, make your move", i + 1).println();
					players[i].turn(board, moveTracker, piecesCaptured);
					System.out.println(board.toString());
				}
			}
		}
	}
	
	/**
	 * Keeps track of whether king has been captured.
	 * 
	 * @return true if king captured, false otherwise
	 */
	private boolean checkKingCaptured() {
		Iterator<Piece> iter = piecesCaptured.iterator();
		while(iter.hasNext()) {
			if (iter.next() instanceof King) {
				return true;
			}
		}
		return false;
	}
}