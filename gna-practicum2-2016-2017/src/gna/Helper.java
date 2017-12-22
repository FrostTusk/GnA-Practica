package gna;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

import libpract.StdIn;

/**
 * A Helper Class with methods that handle Input
 * for puzzles.
 *
 * @author Mathijs Hubrechtsen
 */
public class Helper {

	/**
	 * Reads in a given puzzle text file and converts it to a board.
	 * This method uses StdIn.
	 * @param	puzzle
	 *				The puzzle to be read.
	 *
	 * @see implementation
	 */
	public Board readPuzzle(String puzzle) throws IOException {
		String path  = "boards/" + puzzle;
		System.setIn(new FileInputStream(path));
		int N = StdIn.readInt();
		int[][] tiles = new int[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				tiles[i][j] = StdIn.readInt();
		return new Board(tiles);
	}

	/**
	 * Reads in a given puzzle text file and converts it to a board.
	 * This method does NOT use StdIn.

	 * @param	puzzle
	 *				The puzzle to be read.
	 *
	 * @see implementation
	 */
	public Board newReadPuzzle(String puzzle) throws IOException {
		String path  = "boards/" + puzzle;
		BufferedReader br = new BufferedReader(new FileReader(path));
		try {
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();
		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		    String everything = sb.toString();
		    Board board = convertListToBoard(convertStringToList(everything));
			return board;
		} finally {
		    br.close();
		}
	}


	/**
	 * Converts a given read string to a list that can be used to make
	 * a board. Helper Method of newReadPuzzle.
	 *
	 * @param	string
	 *				The string to be converted.
	 *
	 * @see implementation
	 */
	private List<BigInteger> convertStringToList(String string) {
		List<BigInteger> result = new ArrayList<BigInteger>();
		List<String> buffer = new ArrayList<String>();
		for (int i = 0; i < string.length(); i++) {
			int c = Character.getNumericValue(string.charAt(i));
			if (c != -1) buffer.add(Character.toString(string.charAt(i)));
			else
				if (!buffer.isEmpty()) {
					String total = "";
					for (String j: buffer)
						total += j;
					result.add(BigInteger.valueOf(Integer.parseInt(total)));
					buffer.clear();
				}
		}
		return result;
	}

	/**
	 * Converts a given list can be used to make a board to a board.
	 * Helper Method of newReadPuzzle.
	 *
	 * @param	string
	 *				The string to be converted.
	 *
	 * @see implementation
	 */
	private Board convertListToBoard(List<BigInteger> list) {
		int count = 0;
		int N = list.get(0).intValue();
		int[][] tiles = new int[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++) {
				count += 1;
				tiles[i][j] = list.get(count).intValue();
			}
		return new Board(tiles);
	}

}
