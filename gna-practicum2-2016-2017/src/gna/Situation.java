package gna;

/**
 * A Class of situations of the 8-puzzle problem.
 *
 * @author Mathijs Hubrechtsen
 */
public class Situation {

	/**
	 * Variable containing the board of this situation.
	 */
	private Board board;
	/**
	 * Variable containing the amount of moves of this situation.
	 */
	private double moves;
	/**
	 * Variable containing the previous situation.
	 */
	private Situation prevSituation;


	/**
	 * Construct a situation with a given board, a given amount of moves
	 * and the previous situation.
	 *
	 * @param	board
	 * 				The board of this situation.
	 * @param moves
	 *				The amount of moves to get to this situation.
	 * @param prevSituation
	 *				The previous situation.
	 *
	 * @see implementation
	 */
	public Situation(Board board, double moves, Situation prevSituation) {
		this.board = board;
		this.moves = moves;
		this.prevSituation = prevSituation;
	}


	/**
	 * Return the board of this situation.
	 * @see implementation
	 */
	public Board getBoard() {
		return this.board;
	}

	/**
	 * Return the amount of moves of this situation.
	 * @see implementation
	 */
	public double getMoves() {
		return this.moves;
	}

	/**
	 * Return the previous situation.
	 * @see implementation
	 */
	public Situation getPrevSituation() {
		return this.prevSituation;
	}

}
