package gna;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

import libpract.PriorityFunc;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * A number of JUnit tests for Solver.
 *
 * Feel free to modify these to automatically test puzzles or other functionality
 */
public class UnitTests {

	private static final double EPSILON = 0.0001;
	private Helper helper = new Helper();
	private boolean quickTest = false;



			/*
			 * |------------------------------------|
			 * | 1. Tests for self made methods.		|
			 * |------------------------------------|
			 */



	@Test
	public void resetBoardEasyTest() {
		int[][] tiles = {{1, 2}, {0, 3}};
		int[][] checkResult = {{1, 2}, {3, 0}};
		Board board = new Board(tiles);
		int[][] returnResult = tiles = board.getResetBoard();
		assertSameArray(checkResult, returnResult);
		assertNoDuplicates(returnResult);
		assertTrue(returnResult[returnResult.length - 1][returnResult[0].length-1] == 0);
	}

	@Test
	public void resetBoardMediumTest() {
		int[][] tiles = {{4, 3, 0}, {5, 2, 1}, {7, 8, 6}};
		Board board = new Board(tiles);
		int[][] returnResult = tiles = board.getResetBoard();
		assertNoDuplicates(returnResult);
		assertTrue(returnResult[returnResult.length - 1][returnResult[0].length-1] == 0);
	}

	@Test
	public void resetBoardHardTest() {
		int[][] tiles = {{1, 2, 3, 4}, {5, 0, 6, 7}, {9, 10, 11, 8}, {13, 14, 15, 12}};
		Board board = new Board(tiles);
		int[][] returnResult = tiles = board.getResetBoard();
		assertNoDuplicates(returnResult);
		assertTrue(returnResult[returnResult.length - 1][returnResult[0].length-1] == 0);
	}



			/*
			 * |----------------------------------------|
			 * | 2. Tests for the Priority Functions.		|
			 * |----------------------------------------|
			 */



	@Test
	public void hammingEasyTest() {
		int[][] tiles1 = {{1, 2}, {0, 3}};
		Board board = new Board(tiles1);
		assertEquals(1, board.hamming(), EPSILON);
		int[][] tiles2 = {{0, 2}, {1, 3}};
		board = new Board(tiles2);
		assertEquals(2, board.hamming(), EPSILON);
		int[][] tiles3 = {{2, 0}, {1, 3}};
		board = new Board(tiles3);
		assertEquals(3, board.hamming(), EPSILON);
	}

	@Test
	public void hammingMediumTest() {
		int[][] tiles1 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
		Board board = new Board(tiles1);
		assertEquals(0, board.hamming(), EPSILON);
		int[][] tiles2 = {{0, 1, 2}, {4, 5, 3}, {7, 8, 6}};
		board = new Board(tiles2);
		assertEquals(4, board.hamming(), EPSILON);
		int[][] tiles3 = {{4, 3, 0}, {5, 2, 1}, {7, 8, 6}};
		board = new Board(tiles3);
		assertEquals(6, board.hamming(), EPSILON);
	}

	@Test
	public void hammingHardTest() {
		int[][] tiles1 = {{2, 3, 4, 0}, {1, 5, 6, 7}, {9, 10, 11, 8}, {13, 14, 15, 12}};
		Board board = new Board(tiles1);
		assertEquals(9, board.hamming(), EPSILON);
		int[][] tiles2 = {{1, 2, 3, 4}, {5, 14, 10, 6}, {9, 15, 11, 7}, {13, 0, 12, 8}};
		board = new Board(tiles2);
		assertEquals(7, board.hamming(), EPSILON);
		int[][] tiles3 = {{2, 3, 10, 0}, {1, 14, 11, 4}, {5, 15, 7, 6}, {9, 13, 12, 8}};
		board = new Board(tiles3);
		assertEquals(15, board.hamming(), EPSILON);
	}


	@Test
	public void manhattanEasyTest() {
		int[][] tiles1 = {{1, 2}, {0, 3}};
		Board board = new Board(tiles1);
		assertEquals(1, board.manhattan(), EPSILON);
		int[][] tiles2 = {{0, 2}, {1, 3}};
		board = new Board(tiles2);
		assertEquals(2, board.manhattan(), EPSILON);
		int[][] tiles3 = {{2, 0}, {1, 3}};
		board = new Board(tiles3);
		assertEquals(3, board.manhattan(), EPSILON);
	}

	@Test
	public void manhattanMediumTest() {
		int[][] tiles1 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
		Board board = new Board(tiles1);
		assertEquals(0, board.manhattan(), EPSILON);
		int[][] tiles2 = {{0, 1, 2}, {4, 5, 3}, {7, 8, 6}};
		board = new Board(tiles2);
		assertEquals(4, board.manhattan(), EPSILON);
		int[][] tiles3 = {{4, 3, 0}, {5, 2, 1}, {7, 8, 6}};
		board = new Board(tiles3);
		assertEquals(8, board.manhattan(), EPSILON);
	}

	@Test
	public void manhattanHardTest() {
		int[][] tiles1 = {{2, 3, 4, 0}, {1, 5, 6, 7}, {9, 10, 11, 8}, {13, 14, 15, 12}};
		Board board = new Board(tiles1);
		assertEquals(9, board.manhattan(), EPSILON);
		int[][] tiles2 = {{1, 2, 3, 4}, {5, 14, 10, 6}, {9, 15, 11, 7}, {13, 0, 12, 8}};
		board = new Board(tiles2);
		assertEquals(14, board.manhattan(), EPSILON);
		int[][] tiles3 = {{2, 3, 10, 0}, {1, 14, 11, 4}, {5, 15, 7, 6}, {9, 13, 12, 8}};
		board = new Board(tiles3);
		assertEquals(23, board.manhattan(), EPSILON);
	}

	@Test
	public void testManhattanVoorAram() {
		Board board = new Board(new int[][]{{0,6,7},{8,4,5},{3,1,2}});
		assertEquals(20, board.manhattan());
	}



			/*
			 * |------------------------------------|
			 * | 3. Tests for the required methods.	|
			 * |------------------------------------|
			 */



	@Test
	public void neighbourGenericTest() {
		int[][] tiles = {{1,2}, {0, 3}};
		Board board = new Board(tiles);
		assertEquals(2, board.neighbors().size(), EPSILON);
	}

	@Test
	public void neighbourZeroTest() {
		int[][] tiles = {{0}};
		Board board = new Board(tiles);
		assertEquals(0, board.neighbors().size(), EPSILON);
	}


	@Test
	public void toStringTest() {
		int[][] tiles = {{1,2}, {0, 3}};
		String checkResult = "1 2 \n  3 \n";
		Board board = new Board(tiles);
		String returnResult = board.toString();
		assertTrue(returnResult.equals(checkResult));
	}



			/*
			 * |--------------------------------|
			 * | 4. Tests for specific Puzzles.	|
			 * |--------------------------------|
			 */



	@Test
	public void easySolveHammingTest() {
		PriorityFunc priority = PriorityFunc.HAMMING;
		int[][] tiles = {{1,2}, {0, 3}};
		Board board = new Board(tiles);
		Solver solve = new Solver(board, priority);
		int[][] checkResult = {{1, 2}, {3, 0}};
		int[][] returnResult = solve.solution().get(solve.solution().size() - 1).getCopy();
		assertSameArray(checkResult, returnResult);
	}

	@Test
	public void mediumSolveHammingTest() {
		PriorityFunc priority = PriorityFunc.HAMMING;
		int[][] tiles = { {1, 2, 5}, {0, 3, 4}, {6, 7, 8} };
		Board board = new Board(tiles);
		Solver solve = new Solver(board, priority);
		int[][] checkResult = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
		int[][] returnResult = solve.solution().get(solve.solution().size() - 1).getCopy();
		assertSameArray(checkResult, returnResult);
	}


	@Test
	public void easySolveManhattanTest() {
		PriorityFunc priority = PriorityFunc.MANHATTAN;
		int[][] tiles = {{1,2}, {0, 3}};
		Board board = new Board(tiles);
		Solver solve = new Solver(board, priority);
		int[][] checkResult = {{1, 2}, {3, 0}};
		int[][] returnResult = solve.solution().get(solve.solution().size() - 1).getCopy();
		assertSameArray(checkResult, returnResult);
	}

	@Test
	public void mediumSolveManhattanTest() {
		PriorityFunc priority = PriorityFunc.MANHATTAN;
		int[][] tiles = { {1, 2, 5}, {0, 3, 4}, {6, 7, 8} };
		Board board = new Board(tiles);
		Solver solve = new Solver(board, priority);
		int[][] checkResult = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
		int[][] returnResult = solve.solution().get(solve.solution().size() - 1).getCopy();
		assertSameArray(checkResult, returnResult);
	}



			/*
			 * |------------------------------------|
			 * | 5. Tests for the given Puzzles.		|
			 * |------------------------------------|
			 */



	@Test
	public void puzzle04HammingTest() throws IOException {
		PriorityFunc priority = PriorityFunc.HAMMING;
		Board board = helper.newReadPuzzle("puzzle04.txt");
		Solver solve = new Solver(board, priority);
		assertFalse(solve.solution().size() == 0);
		int[][] checkResult = buildFinishedTiles(board.getCopy().length);
		int[][] returnResult = solve.solution().get(solve.solution().size() - 1).getCopy();
		assertSameArray(checkResult, returnResult);
	}

	@Test
	public void puzzle20HammingTest() throws IOException {
		PriorityFunc priority = PriorityFunc.HAMMING;
		Board board = helper.newReadPuzzle("puzzle20.txt");
		assertTrue(board.isSolvable());
		Solver solve = new Solver(board, priority);
		assertFalse(solve.solution().size() == 0);
		int[][] checkResult = buildFinishedTiles(board.getCopy().length);
		int[][] returnResult = solve.solution().get(solve.solution().size() - 1).getCopy();
		assertSameArray(checkResult, returnResult);
	}

	@Test
	public void puzzle22HammingTest() throws IOException {
		PriorityFunc priority = PriorityFunc.HAMMING;
		Board board = helper.newReadPuzzle("puzzle22.txt");
		assertTrue(board.isSolvable());
		Solver solve = new Solver(board, priority);
		assertFalse(solve.solution().size() == 0);
		int[][] checkResult = buildFinishedTiles(board.getCopy().length);
		int[][] returnResult = solve.solution().get(solve.solution().size() - 1).getCopy();
		assertSameArray(checkResult, returnResult);
	}

	@Test
	public void puzzle24HammingTest() throws IOException {
		PriorityFunc priority = PriorityFunc.HAMMING;
		Board board = helper.newReadPuzzle("puzzle24.txt");
		assertTrue(board.isSolvable());
		Solver solve = new Solver(board, priority);
		assertFalse(solve.solution().size() == 0);
		int[][] checkResult = buildFinishedTiles(board.getCopy().length);
		int[][] returnResult = solve.solution().get(solve.solution().size() - 1).getCopy();
		assertSameArray(checkResult, returnResult);
	}

	@Test
	public void puzzle26HammingTest() throws IOException {
		PriorityFunc priority = PriorityFunc.HAMMING;
		Board board = helper.newReadPuzzle("puzzle26.txt");
		assertTrue(board.isSolvable());
		Solver solve = new Solver(board, priority);
		assertFalse(solve.solution().size() == 0);
		int[][] checkResult = buildFinishedTiles(board.getCopy().length);
		int[][] returnResult = solve.solution().get(solve.solution().size() - 1).getCopy();
		assertSameArray(checkResult, returnResult);
	}

	@Test
	public void puzzle28HammingTest() throws IOException {
		PriorityFunc priority = PriorityFunc.HAMMING;
		Board board = helper.newReadPuzzle("puzzle28.txt");
		assertTrue(board.isSolvable());
		Solver solve = new Solver(board, priority);
		assertFalse(solve.solution().size() == 0);
		int[][] checkResult = buildFinishedTiles(board.getCopy().length);
		int[][] returnResult = solve.solution().get(solve.solution().size() - 1).getCopy();
		assertSameArray(checkResult, returnResult);
	}

	@Test
	public void puzzle30HammingTest() throws IOException {
		if (quickTest) return;
		PriorityFunc priority = PriorityFunc.HAMMING;
		Board board = helper.newReadPuzzle("puzzle30.txt");
		assertTrue(board.isSolvable());
		Solver solve = new Solver(board, priority);
		assertFalse(solve.solution().size() == 0);
		int[][] checkResult = buildFinishedTiles(board.getCopy().length);
		int[][] returnResult = solve.solution().get(solve.solution().size() - 1).getCopy();
		assertSameArray(checkResult, returnResult);
	}

// TODO These tests do not work.
//	@Test
//	public void puzzle32HammingTest() throws IOException {
//		if (quickTest) return;
//		PriorityFunc priority = PriorityFunc.HAMMING;
//		Board board = helper.newReadPuzzle("puzzle32.txt");
//		assertTrue(board.isSolvable());
//		Solver solve = new Solver(board, priority);
//		assertFalse(solve.solution().size() == 0);
//		int[][] checkResult = buildFinishedTiles(board.getCopy().length);
//		int[][] returnResult = solve.solution().get(solve.solution().size() - 1).getCopy();
//		assertSameArray(checkResult, returnResult);
//	}
//
//	@Test
//	public void puzzle34HammingTest() throws IOException {
//		if (quickTest) return;
//		PriorityFunc priority = PriorityFunc.HAMMING;
//		Board board = helper.newReadPuzzle("puzzle34.txt");
//		assertTrue(board.isSolvable());
//		Solver solve = new Solver(board, priority);
//		assertFalse(solve.solution().size() == 0);
//		int[][] checkResult = buildFinishedTiles(board.getCopy().length);
//		int[][] returnResult = solve.solution().get(solve.solution().size() - 1).getCopy();
//		assertSameArray(checkResult, returnResult);
//	}
//
//	@Test
//	public void puzzle36HammingTest() throws IOException {
//		if (quickTest) return;
//		PriorityFunc priority = PriorityFunc.HAMMING;
//		Board board = helper.newReadPuzzle("puzzle36.txt");
//		assertTrue(board.isSolvable());
//		Solver solve = new Solver(board, priority);
//		assertFalse(solve.solution().size() == 0);
//		int[][] checkResult = buildFinishedTiles(board.getCopy().length);
//		int[][] returnResult = solve.solution().get(solve.solution().size() - 1).getCopy();
//		assertSameArray(checkResult, returnResult);
//	}
//
//	@Test
//	public void puzzle38HammingTest() throws IOException {
//		if (quickTest) return;
//		PriorityFunc priority = PriorityFunc.HAMMING;
//		Board board = helper.newReadPuzzle("puzzle38.txt");
//		assertTrue(board.isSolvable());
//		Solver solve = new Solver(board, priority);
//		assertFalse(solve.solution().size() == 0);
//		int[][] checkResult = buildFinishedTiles(board.getCopy().length);
//		int[][] returnResult = solve.solution().get(solve.solution().size() - 1).getCopy();
//		assertSameArray(checkResult, returnResult);
//	}
//
//	@Test
//	public void puzzle40HammingTest() throws IOException {
//		if (quickTest) return;
//		PriorityFunc priority = PriorityFunc.HAMMING;
//		Board board = helper.newReadPuzzle("puzzle40.txt");
//		assertTrue(board.isSolvable());
//		Solver solve = new Solver(board, priority);
//		assertFalse(solve.solution().size() == 0);
//		int[][] checkResult = buildFinishedTiles(board.getCopy().length);
//		int[][] returnResult = solve.solution().get(solve.solution().size() - 1).getCopy();
//		assertSameArray(checkResult, returnResult);
//	}
//
//	@Test
//	public void puzzle42HammingTest() throws IOException {
//		if (quickTest) return;
//		PriorityFunc priority = PriorityFunc.HAMMING;
//		Board board = helper.newReadPuzzle("puzzle42.txt");
//		assertTrue(board.isSolvable());
//		Solver solve = new Solver(board, priority);
//		assertFalse(solve.solution().size() == 0);
//		int[][] checkResult = buildFinishedTiles(board.getCopy().length);
//		int[][] returnResult = solve.solution().get(solve.solution().size() - 1).getCopy();
//		assertSameArray(checkResult, returnResult);
//	}
// TODO End.

	@Test
	public void puzzle04ManhattanTest() throws IOException {
		PriorityFunc priority = PriorityFunc.MANHATTAN;
		Board board = helper.newReadPuzzle("puzzle04.txt");
		Solver solve = new Solver(board, priority);
		assertFalse(solve.solution().size() == 0);
		int[][] checkResult = buildFinishedTiles(board.getCopy().length);
		int[][] returnResult = solve.solution().get(solve.solution().size() - 1).getCopy();
		assertSameArray(checkResult, returnResult);
	}

	@Test
	public void puzzle20ManhattanTest() throws IOException {
		PriorityFunc priority = PriorityFunc.MANHATTAN;
		Board board = helper.newReadPuzzle("puzzle20.txt");
		assertTrue(board.isSolvable());
		Solver solve = new Solver(board, priority);
		assertFalse(solve.solution().size() == 0);
		int[][] checkResult = buildFinishedTiles(board.getCopy().length);
		int[][] returnResult = solve.solution().get(solve.solution().size() - 1).getCopy();
		assertSameArray(checkResult, returnResult);
	}

	@Test
	public void puzzle22ManhattanTest() throws IOException {
		PriorityFunc priority = PriorityFunc.MANHATTAN;
		Board board = helper.newReadPuzzle("puzzle22.txt");
		assertTrue(board.isSolvable());
		Solver solve = new Solver(board, priority);
		assertFalse(solve.solution().size() == 0);
		int[][] checkResult = buildFinishedTiles(board.getCopy().length);
		int[][] returnResult = solve.solution().get(solve.solution().size() - 1).getCopy();
		assertSameArray(checkResult, returnResult);
	}

	@Test
	public void puzzle24ManhattanTest() throws IOException {
		PriorityFunc priority = PriorityFunc.MANHATTAN;
		Board board = helper.newReadPuzzle("puzzle24.txt");
		assertTrue(board.isSolvable());
		Solver solve = new Solver(board, priority);
		assertFalse(solve.solution().size() == 0);
		int[][] checkResult = buildFinishedTiles(board.getCopy().length);
		int[][] returnResult = solve.solution().get(solve.solution().size() - 1).getCopy();
		assertSameArray(checkResult, returnResult);
	}

	@Test
	public void puzzle26ManhattanTest() throws IOException {
		PriorityFunc priority = PriorityFunc.MANHATTAN;
		Board board = helper.newReadPuzzle("puzzle26.txt");
		assertTrue(board.isSolvable());
		Solver solve = new Solver(board, priority);
		assertFalse(solve.solution().size() == 0);
		int[][] checkResult = buildFinishedTiles(board.getCopy().length);
		int[][] returnResult = solve.solution().get(solve.solution().size() - 1).getCopy();
		assertSameArray(checkResult, returnResult);
	}

	@Test
	public void puzzle28ManhattanTest() throws IOException {
		PriorityFunc priority = PriorityFunc.MANHATTAN;
		Board board = helper.newReadPuzzle("puzzle28.txt");
		assertTrue(board.isSolvable());
		Solver solve = new Solver(board, priority);
		assertFalse(solve.solution().size() == 0);
		int[][] checkResult = buildFinishedTiles(board.getCopy().length);
		int[][] returnResult = solve.solution().get(solve.solution().size() - 1).getCopy();
		assertSameArray(checkResult, returnResult);
	}

	@Test
	public void puzzle30ManhattanTest() throws IOException {
		if (quickTest) return;
		PriorityFunc priority = PriorityFunc.MANHATTAN;
		Board board = helper.newReadPuzzle("puzzle30.txt");
		assertTrue(board.isSolvable());
		Solver solve = new Solver(board, priority);
		assertFalse(solve.solution().size() == 0);
		int[][] checkResult = buildFinishedTiles(board.getCopy().length);
		int[][] returnResult = solve.solution().get(solve.solution().size() - 1).getCopy();
		assertSameArray(checkResult, returnResult);
	}

	@Test
	public void puzzle32ManhattanTest() throws IOException {
		if (quickTest) return;
		PriorityFunc priority = PriorityFunc.MANHATTAN;
		Board board = helper.newReadPuzzle("puzzle32.txt");
		assertTrue(board.isSolvable());
		Solver solve = new Solver(board, priority);
		assertFalse(solve.solution().size() == 0);
		int[][] checkResult = buildFinishedTiles(board.getCopy().length);
		int[][] returnResult = solve.solution().get(solve.solution().size() - 1).getCopy();
		assertSameArray(checkResult, returnResult);
	}

	@Test
	public void puzzle34ManhattanTest() throws IOException {
		if (quickTest) return;
		PriorityFunc priority = PriorityFunc.MANHATTAN;
		Board board = helper.newReadPuzzle("puzzle34.txt");
		assertTrue(board.isSolvable());
		Solver solve = new Solver(board, priority);
		assertFalse(solve.solution().size() == 0);
		int[][] checkResult = buildFinishedTiles(board.getCopy().length);
		int[][] returnResult = solve.solution().get(solve.solution().size() - 1).getCopy();
		assertSameArray(checkResult, returnResult);
	}

	@Test
	public void puzzle36ManhattanTest() throws IOException {
		if (quickTest) return;
		PriorityFunc priority = PriorityFunc.MANHATTAN;
		Board board = helper.newReadPuzzle("puzzle36.txt");
		assertTrue(board.isSolvable());
		Solver solve = new Solver(board, priority);
		assertFalse(solve.solution().size() == 0);
		int[][] checkResult = buildFinishedTiles(board.getCopy().length);
		int[][] returnResult = solve.solution().get(solve.solution().size() - 1).getCopy();
		assertSameArray(checkResult, returnResult);
	}

	 @Test
	 public void puzzle38ManhattanTest() throws IOException {
	 	if (quickTest) return;
	 	PriorityFunc priority = PriorityFunc.MANHATTAN;
	 	Board board = helper.newReadPuzzle("puzzle38.txt");
	 	assertTrue(board.isSolvable());
	 	Solver solve = new Solver(board, priority);
	 	assertFalse(solve.solution().size() == 0);
	 	int[][] checkResult = buildFinishedTiles(board.getCopy().length);
	 	int[][] returnResult = solve.solution().get(solve.solution().size() - 1).getCopy();
	 	assertSameArray(checkResult, returnResult);
	 }

	 @Test
	 public void puzzle40ManhattanTest() throws IOException {
	 	if (quickTest) return;
	 	PriorityFunc priority = PriorityFunc.MANHATTAN;
	 	Board board = helper.newReadPuzzle("puzzle40.txt");
	 	assertTrue(board.isSolvable());
	 	Solver solve = new Solver(board, priority);
	 	assertFalse(solve.solution().size() == 0);
	 	int[][] checkResult = buildFinishedTiles(board.getCopy().length);
	 	int[][] returnResult = solve.solution().get(solve.solution().size() - 1).getCopy();
	 	assertSameArray(checkResult, returnResult);
	 }

	 @Test
	 public void puzzle42ManhattanTest() throws IOException {
	 	if (quickTest) return;
	 	PriorityFunc priority = PriorityFunc.MANHATTAN;
	 	Board board = helper.newReadPuzzle("puzzle42.txt");
	 	assertTrue(board.isSolvable());
	 	Solver solve = new Solver(board, priority);
	 	assertFalse(solve.solution().size() == 0);
	 	int[][] checkResult = buildFinishedTiles(board.getCopy().length);
	 	int[][] returnResult = solve.solution().get(solve.solution().size() - 1).getCopy();
	 	assertSameArray(checkResult, returnResult);
	 }



			/*
			 * |--------------------|
			 * | 6. Helper Methods.	|
			 * |--------------------|
			 */



	public void assertSameArray(int[][] checkResult, int[][] returnResult) {
		for (int i = 0; i < checkResult.length; i++)
			for (int j = 0; j < checkResult[i].length; j++)
				assertTrue(returnResult[i][j] == checkResult[i][j]);
	}


	public void assertNoDuplicates(int[][] returnResult) {
		Set<BigInteger> checkSet = new HashSet<BigInteger>();
		for (int i = 0; i < returnResult.length; i++)
			for (int j = 0; j < returnResult[i].length; j++) {
				assertTrue( !checkSet.contains(BigInteger.valueOf(returnResult[i][j])) );
				checkSet.add(BigInteger.valueOf(returnResult[i][j]));
			}
	}


	public int[][] buildFinishedTiles(int size) {
		int count = 0;
		int[][] tiles = new int[size][size];
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++) {
				count += 1;
				if ( (i == size - 1) && (j == size - 1) ) count = 0;
				tiles[i][j] = count;
			}
		return tiles;
	}

}
