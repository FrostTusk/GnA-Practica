package gna;

import java.util.*;
import libpract.PriorityFunc;

/**
 * A Solver Class. Solves 8-puzzle problems.
 *
 * @author Mathijs Hubrechtsen
 */
public class Solver {

			/*
			 * |----------------------------------------------------------------|
			 * | 1. The next method handle the Initialization of this solver.		|
			 * |----------------------------------------------------------------|
			 */



	/**
	 * Variable containing the priority type of this solver.
	 */
	private PriorityFunc priority;
	/**
	 * Variable containing comparator used in the Priority Queue.
	 */
	private Comparator <Situation> comparator = new Comparator<Situation>() {
		@Override
		public int compare(Situation o1, Situation o2) {
	    	if (priority == PriorityFunc.HAMMING) {
	    		if ( ( o1.getBoard().hamming() + o1.getMoves() )
	    		   < ( o2.getBoard().hamming() + o2.getMoves() ) )
	    			return -1;
	    		else if ( ( o1.getBoard().hamming() + o1.getMoves() )
    					> ( o2.getBoard().hamming() + o2.getMoves() ) )
	    			return 1;
	    		else return 0;
	    	} else if (priority == PriorityFunc.MANHATTAN) {
	    		if ( ( o1.getBoard().manhattan() + o1.getMoves() )
	    		   < ( o2.getBoard().manhattan() + o2.getMoves() ) )
	    			return -1;
	    		else if ( ( o1.getBoard().manhattan() + o1.getMoves() )
	    				> ( o2.getBoard().manhattan() + o2.getMoves() ) )
	    			return 1;
	    		else return 0;
	    	} else throw new IllegalArgumentException("Priority function not supported");
		}
	};


	/**
	 * Finds a solution to the initial board.
	 *
	 * @param	initial
	 * 				The initial board state.
	 * @param priority
	 *				Is either PriorityFunc.HAMMING or PriorityFunc.MANHATTAN.

	 * @see implementation
	 */
	public Solver(Board initial, PriorityFunc priority) {
		// Use the given priority function (either PriorityFunc.HAMMING
		// or PriorityFunc.MANHATTAN) to solve the puzzle.
		this.priority = priority;
		buildfinalBoard(initial);
		if (!initial.isSolvable()) {
			System.out.println("Geen mogelijke oplossing. (Solver)");
			return;
		}
		if (priority == PriorityFunc.HAMMING) {
			solve(initial);
		} else if (priority == PriorityFunc.MANHATTAN) {
			solve(initial);
		} else {
			throw new IllegalArgumentException("Priority function not supported");
		}
	}



			/*
			 * |----------------------------------------------------------------|
			 * | 2. The next method handle the Final Board and the Solution.		|
			 * |----------------------------------------------------------------|
			 */



	/**
	 * Variable containing the final board of this solver.
	 */
	private Board finalBoard;
	/**
	 * Variable containing the final situation of this solver.
	 */
	private Situation finalSituation;


	/**
	 * Get the final board state.
	 * @see implementation
	 */
	public Board getFinalBoard() {
		return this.finalBoard;
	}


	/**
	 * Returns a List of board positions as the solution. It should contain the initial
	 * Board as well as the solution (if these are equal only one Board is returned).
	 * @see implementation
	 */
	public List<Board> solution() {
		List<Board> solution = new ArrayList<Board>();
		Stack<Board> stack = new Stack<Board>();
		Situation situation = this.finalSituation;
		while (situation != null) {
			stack.push(situation.getBoard());
			situation = situation.getPrevSituation();
		} while (!stack.isEmpty()) solution.add(stack.pop());
		return solution;
	}


	/**
	 * Builds the final board state.
	 *
	 * @param	initial
	 * 				The initial board of which the final state needs to be built.
	 *
	 * @see implementation
	 */
	private void buildfinalBoard(Board initial) {
		int[][] tiles = initial.getCopy();
		int counter = 0;

		for (int i = 0; i < tiles.length; i++)
			for (int j = 0; j < tiles[i].length; j++) {
				counter += 1;
				if ( (i == tiles.length - 1) && (j == tiles[i].length - 1) )
					tiles[i][j] = 0;
				else tiles[i][j] = counter;
			}

		finalBoard = new Board(tiles);
	}



			/*
			 * |----------------------------------------------------------------|
			 * | 3. The next method handle finding the solution of this Solver.	|
			 * |----------------------------------------------------------------|
			 */



	/**
	 * Solves the initial board state puzzle.
	 *
	 * @param	initial
	 * 				The initial board state.
	 *
	 * @see implementation
	 */
	private void solve(Board initial) {
		PriorityQueue<Situation> queue = new PriorityQueue<Situation>(comparator);
		Situation situation = new Situation(initial, 0, null);
		queue.add(situation);
		while (true) {
			situation = queue.poll();
			if (situation.getBoard().equals(getFinalBoard()))
				break;
			addneighborSituations(situation, queue);
		}
		this.finalSituation = situation;
	}


	/**
	 * Add the neighboring situations of a given situation to a given priority
	 * queue.
	 *
	 * @param	situation
	 * 				The situation of which the neighboring situations need
	 * 				to be added.
	 * @param queue
	 *				The priority queue to which the neighbors will be added.
	 *
	 * @see implementation
	 */
	public void addneighborSituations(Situation situation, PriorityQueue<Situation> queue) {
		Collection<Board> neighbors = situation.getBoard().neighbors();
		for (Board neighbor: neighbors)
			if ( (situation.getPrevSituation() == null ) ||
					(!neighbor.equals(situation.getPrevSituation().getBoard())) )
				queue.add(new Situation(neighbor, situation.getMoves() + 1, situation));
	}

}
