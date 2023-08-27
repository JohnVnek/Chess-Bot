package main;

import java.util.*;

/**
 * Computer capable of playing chess.
 * <p>
 * Analyzes all possible moves it can make in given turn.
 * Assigns score based on advantageousness and safety of each possible move.
 * Randomly selects between safest moves. 
 * 
 * @author John Vnek
 */
public class ComputerPlayer extends Player {
	 
	private List<CompMove> potentialMoves;
	private PriorityQueue<CompMove> safestMoves;
	private Player opponent;
	private Spot[] oppSpots;
	private Spot[] compSpots;
	private int numMoves;
	
	/**
	 * Constructor for ComputerPlayer.
	 * 
	 * @param opponent - player opponent of computer
	 */
	public ComputerPlayer(Player opponent) {
		this.potentialMoves = new LinkedList<>(); 
		this.safestMoves = new PriorityQueue<>(Collections.reverseOrder());
		this.opponent = opponent;
		this.oppSpots = new Spot[16];
		this.compSpots = new Spot[16];
		this.numMoves = 0;
	}
	
	/**
	 * Handles one turn taken by the computer.
	 * 
	 * @param board - chess board Object
	 * @param moveTracker - list of all game's moves
	 * @param piecesCaptured - list of all pieces captured
	 */
	public void turn(Board board, List<Move> moveTracker, List<Piece> piecesCaptured) {
		updateSpots(board);
		checkSpots(board, potentialMoves);
		assessSafety(board, potentialMoves, safestMoves);
		Move move = getSafestMove();
		makeMove(move, moveTracker, piecesCaptured);
		clearData();
	}
	
	/**
	 * Updates lists of spots for black and white pieces.
	 * 
	 * @param board - chess board Object
	 */
	private void updateSpots(Board board) {
		Spot[][] spots = board.getBoardSpots();
		int numWhitePieces = 0;
		int numBlackPieces = 0;
		
		for (int i = 0, yLim = board.getNumRows(); i < yLim; i++) {
			for (int j = 0, xLim = board.getNumColumns(); j < xLim; j++) {	
				Piece piece = spots[i][j].getPiece();
				if (piece != null) {
					if (piece.getIsWhite()) {
						oppSpots[numWhitePieces++] = spots[i][j];
					} else {
						compSpots[numBlackPieces++] = spots[i][j];
					}
				}
			}
		}
	}
	
	/**
	 * Checks all spots with black pieces for potential moves.
	 * 
	 * @param board - chess board Object
	 * @param moveList - list of potential moves
	 */
	private void checkSpots(Board board, List<CompMove> moveList) {
		for (int i = 0; i < compSpots.length; i++) {
			if (compSpots[i] != null) {
				checkPiece(board, moveList, compSpots[i]);
			}
		}
	}
	
	/**
	 * Checks piece type and all possible moves that piece can make.
	 * 
	 * @param board - chess board Object
	 * @param moveList - list of potential moves
	 * @param spot - starting spot position of piece 
	 */
	private void checkPiece(Board board, List<CompMove> moveList, Spot spot) {
		Piece piece = spot.getPiece();
		if (piece != null) {
			if (piece.getIsWhite() == this.whitePieces) {
				int startX = spot.getX();
				int startY = spot.getY();
				
				if (piece instanceof Knight) {
					checkMoves(board, moveList, piece, spot, startX - 2, startY - 1);
					checkMoves(board, moveList, piece, spot, startX - 2, startY + 1);
					
					checkMoves(board, moveList, piece, spot, startX - 1, startY - 2);
					checkMoves(board, moveList, piece, spot, startX - 1, startY + 2);
					
					checkMoves(board, moveList, piece, spot, startX + 1, startY - 2);
					checkMoves(board, moveList, piece, spot, startX + 1 , startY + 2);
					
					checkMoves(board, moveList, piece, spot, startX + 2 , startY - 1);
					checkMoves(board, moveList, piece, spot, startX + 2 , startY + 1);
				} else if (piece instanceof Bishop) {
					checkAcross(board, moveList, piece, spot, startX - 1, startY - 1, -1, -1);
					checkAcross(board, moveList, piece, spot, startX - 1, startY + 1, -1, 1);
					
					checkAcross(board, moveList, piece, spot, startX + 1, startY - 1, 1, -1);
					checkAcross(board, moveList, piece, spot, startX + 1, startY + 1, 1, 1);
				} else if (piece instanceof Rook) {
					checkAcross(board, moveList, piece, spot, startX - 1, startY, -1, 0);
					checkAcross(board, moveList, piece, spot, startX + 1, startY, 1, 0);
					
					checkAcross(board, moveList, piece, spot, startX, startY - 1, 0, -1);
					checkAcross(board, moveList, piece, spot, startX, startY + 1, 0, 1);
				} else if (piece instanceof Queen) {
					checkAcross(board, moveList, piece, spot, startX - 1, startY - 1, -1, -1);
					checkAcross(board, moveList, piece, spot, startX - 1, startY + 1, -1, 1);
					
					checkAcross(board, moveList, piece, spot, startX + 1, startY - 1, 1, -1);
					checkAcross(board, moveList, piece, spot, startX + 1, startY + 1, 1, 1);
					
					checkAcross(board, moveList, piece, spot, startX - 1, startY, -1, 0);
					checkAcross(board, moveList, piece, spot, startX + 1, startY, 1, 0);
					
					checkAcross(board, moveList, piece, spot, startX, startY - 1, 0, -1);
					checkAcross(board, moveList, piece, spot, startX, startY + 1, 0, 1);
				} else {
					for (int i = 0; i < 3; i++) {
						for (int j = 0; j < 3; j++) {
							checkMoves(board, moveList, piece, spot, (startX - 1) + i, (startY - 1) + j);
						}
					}
					checkMoves(board, moveList, piece, spot, startX, startY - 2);
				}
			}
		}
	}
	
	/**
	 * Recursively checks all moves for pieces that can move across horizontally, vertically, and/or diagonally. 
	 * 
	 * @param board - chess board Object
	 * @param moveList - list of potential moves
	 * @param piece - piece that will move
	 * @param start - starting spot of piece
	 * @param endX - ending x coordinate of piece after move
	 * @param endY - ending y coordinate of piece after move
	 * @param xStep - amount x changes per move
	 * @param yStep - amount y changes per move
	 * @return true if last possible captures piece, false if not
	 */
	private boolean checkAcross(Board board, List<CompMove> moveList, Piece piece, Spot start, int endX, int endY, int xStep, int yStep) {
		if (endX < 0 || endX > 7 || endY < 0 || endY > 7) {
			return false;
		} else {
			Spot end = board.getSpot(endX, endY);
			Piece endPiece;
			if (end.getPiece() != null) {
				endPiece = end.getPiece();
				if (endPiece.getIsWhite() == piece.getIsWhite()) {
					return false;
				} else {
					if (moveList != null) moveList.add(new CompMove(this, start, end, piece));
					return true;
				}
			} else {
				if (moveList != null) moveList.add(new CompMove(this, start, end, piece));
				return checkAcross(board, moveList, piece, start, endX + xStep, endY + yStep, xStep, yStep);
			}
		}
	}
	
	/**
	 * Checks if move can be made
	 * 
	 * @param board - chess board Object
	 * @param moveList - list of potential moves
	 * @param piece - piece that will move
	 * @param start - starting spot of piece
	 * @param endX - ending x coordinate of piece after move
	 * @param endY - ending y coordinate of piece after move
	 * @return true if move can be made, false if not
	 */
	private boolean checkMoves(Board board, List<CompMove> moveList, Piece piece, Spot start, int endX, int endY) {
		if (endX > -1 && endX < 8 && endY > -1 && endY < 8) {
			Spot end = board.getSpot(endX, endY);
			if (piece.checkMove(board, this, start, end)) {
				if (moveList != null) moveList.add(new CompMove(this, start, end, piece));
				return true;
			}		
		}
		return false;
	}
	
	/**
	 * Assigns safety score to each possible move based on various factors
	 * 
	 * @param board - chess board Object
	 * @param moveList - list of potential moves
	 * @param moveQueue - priority queue of potential moves sorted by highest safety score
	 */
	public void assessSafety(Board board, List<CompMove> moveList, PriorityQueue<CompMove> moveQueue) {
		Iterator<CompMove> iter = moveList.iterator();
		int count = 1;	
		
		while(iter.hasNext()) {
			System.out.println(count++);
			CompMove move = iter.next();
			checkCaptured(move);
			moveSafety(board, move, compSpots, oppSpots);
			exposedPieceSafety(board, move, compSpots, oppSpots);
			
			System.out.println(move.getSafetyScore());
			
			moveQueue.offer(move);
		}
	}
	
	/**
	 * Checks if move results in piece capturing another piece.
	 * If it does capture piece, add points to safety score.
	 * 
	 * @param move - computer move being made
	 */
	public void checkCaptured(CompMove move) {
		Piece piece = move.getPieceCaptured();
		
		if (piece != null && piece.getIsWhite() != this.whitePieces) {
			move.addToScore(((125) * piece.getValue()));
		}
	}
	
	/**
	 * Checks if move can cause piece to be captured by any of opponents pieces.
	 * <p>
	 * If piece can be captured prior to moving, add points to move score.
	 * <p>
	 * If piece can be captured after move, remove points from score.
	 * Or if piece can be captured but opponent piece put at risk as a result,
	 * considered sacrifice, add points instead.
	 * <p>
	 * If piece can be captured prior to moving, add points to score.
	 * <p>
	 * If piece cannot be captured by opponent after move,
	 * check next possible moves to see if first move gives advantage.
	 * 
	 * @param board - chess board Object
	 * @param move - computer move being made
	 * @param compSpots - array of all spots with computer (black) pieces
	 * @param oppSpots - array of all spot with opponent (white) pieces
	 */
	private void moveSafety(Board board, CompMove move, Spot[] compSpots, Spot[] oppSpots) {
		Spot start = move.getStart();
		Piece pieceMoved = move.getPieceMoved();
		int pieceValue = pieceMoved.getValue();
		
		Spot end = move.getEnd();
		Piece endPiece = end.getPiece();
		
		boolean canBeCaptured = false;
		System.out.println(pieceMoved + " " + end);
		
		for (int i = 0; i < oppSpots.length; i++) {
			if (oppSpots[i] != null) {
				Piece oppPiece = oppSpots[i].getPiece();
				
				if (oppPiece.checkMove(board, opponent, oppSpots[i], start)) {
					move.addToScore(25 * pieceValue);
				}
				
				end.setPiece(pieceMoved);
				if (oppPiece.checkMove(board, opponent, oppSpots[i], end)) {
					move.addToScore(((-50) * pieceValue));
					
					if (sacrifice(board, move, compSpots, end, oppPiece, oppSpots[i], oppPiece)) {
						System.out.println("Entered4");
						
						move.addToScore(25 * oppPiece.getValue());
					}
					canBeCaptured = true;
				} 
				end.setPiece(endPiece);
			}
		}
		
		if (!canBeCaptured) {
			end.setPiece(pieceMoved);
			assessAdvantage(board, move, end, pieceMoved);
			end.setPiece(endPiece);
		}
	}
	
	/**
	 * Checks if piece being captured can set up computer to capture more valuable opponent piece.
	 * <p>
	 * First checks if opponent piece captured is at least twice as valuable as computer piece.
	 * If true, checks if other computer piece can captured opponent piece.
	 * 
	 * @param board - chess board Object
	 * @param move - computer move being made
	 * @param compSpots - array of all spots with black pieces
	 * @param moveEnd - spot that piece moves to
	 * @param pieceMoved - computer piece that moves
	 * @param oppStart - spot where opponent piece starts
	 * @param oppPiece - opponent piece
	 * @return true if more valuable opponent piece can be captured, false if not
	 */
	private boolean sacrifice(Board board, CompMove move, Spot[] compSpots, Spot moveEnd, Piece pieceMoved, Spot oppStart, Piece oppPiece) {
		moveEnd.setPiece(oppPiece);
		oppStart.setPiece(null);
		
		int movedValue = pieceMoved.getValue();
		int oppValue = oppPiece.getValue();
		
		boolean shouldSac = false;
		if ((movedValue * 2) <= oppValue) {
			for (int i = 0; i < compSpots.length; i++) {
				if (compSpots[i] != null) {
					Piece black = compSpots[i].getPiece();
					if (black != null && black != pieceMoved && black.checkMove(board, this, compSpots[i], moveEnd)) {
						shouldSac = true;
					}
				}
			}
		}
		moveEnd.setPiece(pieceMoved);
		oppStart.setPiece(oppPiece);
		return shouldSac;
	}
	
	/**
	 * Checks the next possible moves after each move computer can make.
	 * <p>
	 * From end location of first computer move, record next possible moves.
	 * If one of the next possible moves puts computer in position to capture valuable piece,
	 * add points to move score of initial move.
	 * 
	 * @param board - chess board Object
	 * @param move - computer move being made
	 * @param start - starting spot for next possible moves (ending spot from first move)
	 * @param pieceMoved - computer piece being moved
	 */
	private void assessAdvantage(Board board, CompMove move, Spot start, Piece pieceMoved) {
		List<CompMove> nextMoves = new LinkedList<>();
		PriorityQueue<CompMove> moveQueue = new PriorityQueue<>(Collections.reverseOrder());
		checkPiece(board, nextMoves, start);
		int movedValue = pieceMoved.getValue();
		
		Iterator<CompMove> iter = nextMoves.iterator();
		while(iter.hasNext()) {
			CompMove nextMove = iter.next();
			Spot end = nextMove.getEnd();
			System.out.println(end);
			
			checkCaptured(nextMove);
			nextMoveDanger(board, nextMove, movedValue, end);
			
			moveQueue.offer(nextMove);
		}
		
		if (!moveQueue.isEmpty()) {
			CompMove bestMove = moveQueue.poll();
			if (bestMove.getSafetyScore() > 5000) {
				Piece captured = bestMove.getPieceCaptured();

				if (captured != null) {
					move.addToScore(50 * captured.getValue());
				}
			} 
		}
	}
	
	/**
	 * Assesses danger of next moves computer can make.
	 * 
	 * @param board - chess board Object
	 * @param nextMove - next move computer can make
	 * @param compValue - value of computer piece
	 * @param end - end location of next move
	 */
	private void nextMoveDanger(Board board, CompMove nextMove, int compValue, Spot end) {
		for (int i = 0; i < oppSpots.length; i++) {
			if (oppSpots[i] != null) {
				Piece white = oppSpots[i].getPiece();
				
				if (white.checkMove(board, opponent, oppSpots[i], end)) {
					nextMove.addToScore(((-100) * compValue));
				}
			}
		}
	}	
	
	/**
	 * Checks if other computer pieces are in danger.
	 * 
	 * @param board - chess board Object
	 * @param move - move being made
	 * @param compSpots - array of all spots with computer (black) pieces
	 * @param oppSpots - array of all spot with opponent (white) pieces
	 */
	private void exposedPieceSafety(Board board, CompMove move, Spot[] compSpots, Spot[] oppSpots) {
		Piece pieceMoved = move.getPieceMoved();
		int movedValue = pieceMoved.getValue();
		Spot start = move.getStart();
		Spot end = move.getEnd();
		
		for (int i = 0; i < compSpots.length; i++) {
			if (compSpots[i] != null) {
				Piece black = compSpots[i].getPiece();
				int compValue = black.getValue();
				
				int pieceValue = black.getValue();
				
				for (int j = 0; j < oppSpots.length; j++) {
					if (oppSpots[j] != null) {
						Piece white = oppSpots[j].getPiece();
						
						if (white.checkMove(board, opponent, oppSpots[j], compSpots[i])) {
							if (!start.equals(compSpots[j])) {
								if (end.equals(oppSpots[j])) {
									move.addToScore(50 * white.getValue());
								} else if (checkProtection(board, move, compSpots[i], oppSpots[j], white)) {
									if (movedValue < compValue) {
										System.out.println("Entered5");
										
										move.addToScore(75 * compValue);
									}
								} else {
									move.addToScore(((-25) * pieceValue));
								}
							} else {
								checkVulnerable(board, move, start, pieceMoved, oppSpots[j], white);
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * Checks if moving piece ends up protecting a piece behind it.
	 * 
	 * @param board - chess board Object
	 * @param move - move being made
	 * @param compSpot - spot with computer piece
	 * @param oppSpot - spot with opponent piece
	 * @param oppPiece - opponent piece
	 * @return true if moving piece protects piece behind it, false if not
	 */
	private boolean checkProtection(Board board, CompMove move, Spot compSpot, Spot oppSpot, Piece oppPiece) {
		Spot moveEnd = move.getEnd();
		boolean canProtect = false;
		
		if (oppPiece.checkMove(board, opponent, oppSpot, compSpot)) {
			moveEnd.setPiece(move.getPieceMoved());
			
			if (!oppPiece.checkMove(board, opponent, oppSpot, compSpot)) {				
				canProtect = true;
			}
			moveEnd.setPiece(move.getPieceCaptured());
		}
		
		return canProtect;
	}
	
	/**
	 * Checks if the piece behind the moving piece gets put in danger.
	 * 
	 * @param board - chess board Object
	 * @param compStart - spot where computer piece starts
	 * @param oppStart - spot where opponent piece starts
	 * @param white - opponent piece
	 */
	private void checkVulnerable(Board board, CompMove move, Spot compStart, Piece moved, Spot oppStart, Piece white) {
		System.out.println("Entered 1");
		
		if (white.checkMove(board, opponent, oppStart, compStart)) {
			if (white instanceof Bishop || white instanceof Rook || white instanceof Queen) {
				System.out.println("Entered 2");
				compStart.setPiece(null);
				
				
				int xStep = compStart.getX() - oppStart.getX();
				xStep /= Math.abs(xStep);
				
				int yStep = compStart.getY() - oppStart.getY();
				yStep /= Math.abs(yStep);
				
				System.out.println(xStep + " " + yStep);
				
				LinkedList<CompMove> intermediates = new LinkedList<>();
				if (checkAcross(board, intermediates, white, oppStart, compStart.getX() + xStep, compStart.getY() + yStep, xStep, yStep)) {
					System.out.println("Entered 3");
					
					compStart.setPiece(moved);
					Piece captured = intermediates.getLast().getEnd().getPiece();
					int capValue = captured.getValue();
					if (capValue > compStart.getPiece().getValue()) {
						System.out.println("Entered 4");
						
						move.addToScore((-25) * capValue);
					}
				} else {
					compStart.setPiece(moved);
				}
			} 
		}
	}
	
	/**
	 * Randomly selected between the safest two moves of all possible moves.
	 * <p>
	 * Fills array of moves to select from with the safest move at least 10 times.
	 * Therefore odds of selecting safest move is 10+/15.
	 * The other elements comprising array are the second safest move.
	 * <p>
	 * Having computer randomly choose between two safest moves prevents situations from playing out the same.
	 * Also makes computer more capable of making mistakes.
	 * 
	 * @return safest move of all possible moves
	 */
	private Move getSafestMove() {
		Object[] moves;
		int options;
		
		if (numMoves++ == 0) {
			moves = safestMoves.toArray();
			options = moves.length;
		} else {
			moves = new CompMove[15];
			
			int index = 0;
			CompMove safest = safestMoves.poll();
			while (index < moves.length && !safestMoves.isEmpty()) {
				CompMove nextSafest = safestMoves.poll();
				
				double quotient = Math.abs(((double) safest.getSafetyScore()) / (nextSafest.getSafetyScore()));
				int numToInclude = round(quotient);
				for (int i = 0; i < numToInclude; i++) {
					if (index < moves.length) {
						moves[index++] = safest;
					}
				}
				safest = nextSafest;
			}
			options = index;
		}
		
		Random rand = new Random();
		int index = rand.nextInt(options);
		
		return (Move) moves[index];
	}
	
	/**
	 * Rounds number of times to include move in array.
	 * 
	 * @param quotient - quotient between the current safest move score and next most safe score
	 * @return the number of times to include move in array
	 */
	private int round(double quotient) {
		quotient *= 100;
		
		int numToInclude = (int) quotient / 10;
		if (quotient % 100 > 5) {
			numToInclude++;
		}
		return numToInclude;
	}

	/**
	 * Finalizes move by changing piece locations, adding move to game moveTracker list, and updating piecesCaptured list.
	 * 
	 * @param move - move being made
	 * @param moveTracker - list of all game's moves
	 * @param piecesCaptured - list of all pieces captured
	 */
	private void makeMove(Move move, List<Move> moveTracker, List<Piece> piecesCaptured) {
		Spot start = move.getStart();
		Spot end = move.getEnd();
		Piece temp = move.getPieceMoved();
		Piece endPiece = end.getPiece();
		
		start.setPiece(null);
		if (end.getPiece() != null) {
			end.getPiece().setCaptured(true);
		}
		end.setPiece(temp);
		
		if (endPiece != null && endPiece.getCaptured() == true) {
			piecesCaptured.add(endPiece);
		}
		moveTracker.add(move);
	}
	
	/**
	 * Clears computer and opponent spots, potential move list, and safest move priority queue.
	 */
	private void clearData() {
		for (int i = 0; i < oppSpots.length; i++) {
			oppSpots[i] = null;
			compSpots[i] = null;
		}
		potentialMoves.clear();
		safestMoves.clear();
	}
}