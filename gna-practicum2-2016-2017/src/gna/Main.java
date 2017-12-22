package gna;

import java.io.*;
import java.util.*;
import libpract.PriorityFunc;
import libpract.StdIn;

class Main {

	private static Helper helper = new Helper();


	public static void main( String[] args ) throws IOException {
		boolean fileInput = true,	// Run via File Input
				runHamming = true,	// Run Hamming Tests.
				runManhattan = true;	// Run Manhattan Tests.
		int amount = 25;	// Amount of tests.
		String outputFile = "output.txt";

		if (fileInput) {
			runFilePuzzles(amount, runHamming, runManhattan, outputFile);
			return;
		}

		int N = StdIn.readInt();
		int[][] tiles = new int[N][N];

		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				tiles[i][j] = StdIn.readInt();

		Board initial = new Board(tiles);
		if (!initial.isSolvable()) {
			System.out.println("No solution possible");
		}
		else {
			Solver solver = new Solver(initial, PriorityFunc.HAMMING);

			for (Board board : solver.solution())
				System.out.println(board);

			System.out.println("Minimum number of moves = " + Integer.toString(solver.solution().size() - 1));
		}
	}


	private static void addPaths(List<String> paths) {
		paths.add("puzzle28.txt");
		paths.add("puzzle30.txt");
		paths.add("puzzle32.txt");
		paths.add("puzzle34.txt");
		paths.add("puzzle36.txt");
		paths.add("puzzle38.txt");
		paths.add("puzzle40.txt");
		paths.add("puzzle42.txt");
	}


	public static void runFilePuzzles(int amount, boolean runHamming, boolean runManhattan, String outputFile) throws IOException {
		String outputPath = ""
				+ outputFile;
		System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream(outputPath)), true));
		List<String> inputPaths = new ArrayList<String>();
		addPaths(inputPaths);
		if (runHamming) runFilePuzzlesHamming(inputPaths, amount);
		if (runManhattan) runFilePuzzlesManhattan(inputPaths, amount);

	}

	private static void runFilePuzzlesHamming(List<String> paths, int amount)
			throws IOException {
		System.out.println("***********************\n***********************\nPRINTING HAMMING TESTS:\n"
				+ "***********************\n***********************");
		PriorityFunc priority = PriorityFunc.HAMMING;
		for (String path: paths) {
			System.out.println("******************************\nPrinting path: "
					+ path + "\n******************************");
			Board board = helper.newReadPuzzle(path);
			int count = 0;
			while (count < amount) {
				long startTime = System.currentTimeMillis();
				Solver solver = new Solver(board, priority);
				long endTime = System.currentTimeMillis();
				System.out.println("Running Time = " + Long.toString((endTime - startTime)/1000));
				for (Board boardStep : solver.solution()) System.out.println(boardStep);
				System.out.println("Minimum number of moves = " + Integer.toString(solver.solution().size() - 1));
				count += 1;
			}
		}
	}

	private static void runFilePuzzlesManhattan(List<String> paths, int amount)
			throws IOException {
		System.out.println("*************************\n*************************\nPRINTING MANHATTAN TESTS:\n"
				+ "*************************\n*************************");
		PriorityFunc priority = PriorityFunc.MANHATTAN;
		for (String path: paths) {
			System.out.println("******************************\nPrinting path: "
					+ path + "\n******************************");
			Board board = helper.newReadPuzzle(path);
			int count = 0;
			while (count < amount) {
				long startTime = System.currentTimeMillis();
				Solver solver = new Solver(board, priority);
				long endTime = System.currentTimeMillis();
				System.out.println("Running Time = " + Long.toString((endTime - startTime)/1000));
				for (Board boardStep : solver.solution()) System.out.println(boardStep);
				System.out.println("Minimum number of moves = " + Integer.toString(solver.solution().size() - 1));
				count += 1;
			}
		}
	}

}
