package main;

/**
 * Class for computer made moves.
 * <p>
 * Unlike standard Move objects, CompMove objects keep track of safety score.
 * Safety score is score of advantageousness and danger of potential move made by computer.
 * 
 * @author John Vnek
 */
public class CompMove extends Move implements Comparable<CompMove>{

	private int safetyScore;
	private final int BASE_SCORE = 5000;
	
	/**
	 * Constructor for CompMove.
	 * 
	 * @param player - the player making move
	 * @param start - the starting location of move
	 * @param end - the ending location of move
	 */
	public CompMove(Player player, Spot start, Spot end) {
		super(player, start, end);
		this.safetyScore = BASE_SCORE;
	}
	
	/**
	 * Constructor for CompMove that requires piece parameter.
	 * 
	 * @param player - the player making move
	 * @param start - the starting location of move
	 * @param end - the ending location of move
	 * @param piece - piece making move
	 */
	public CompMove(Player player, Spot start, Spot end, Piece piece) {
		super(player, start, end, piece);
		this.safetyScore = BASE_SCORE;
	}

	/**
	 * Adds score to move safety score.
	 * 
	 * @param score - amount being added to safety score.
	 */
	public void addToScore(int score) {
		this.safetyScore += score;
	}
	
	/**
	 * Getter for safety score.
	 * 
	 * @return safetyScore field
	 */
	public int getSafetyScore() {
		return this.safetyScore;
	}
	
	/**
	 * Used to compare one CompMove to another.
	 * <p>
	 * Comparison is based on which move has greater safety score.
	 */
	public int compareTo(CompMove o) {
		int otherScore = o.getSafetyScore();
		return this.safetyScore - otherScore;
	}
}
