package gna;

import java.util.*;

/**
 * A Class of boards of the 8-puzzle problem.
 *
 * @author Mathijs Hubrechtsen
 */
public class Board {

			/*
			 * |------------------------------------------------------------|
			 * | 1. Methods that handle the Initialization of this board.		|
			 * |------------------------------------------------------------|
			 */



	/**
	 * Variable containing the tiles of this board.
	 */
	private int[][] tiles;

	/**
	 * Construct a board from an N-by-N array of tiles.
	 *
	 * @param	tiles
	 * 			The initial tile configuration
	 * @see implementation
	 */
	public Board( int[][] tiles ) {
//		if (!isNxN(tiles)) throw new IllegalArgumentException();
		this.tiles = deepCopy(tiles);
//		if (!isSolvable()) throw new IllegalArgumentException();
	}


	/**
	 * Checks if a given array is N by N large.
	 *
	 * @param	array
	 * 			The array to check.
	 *
	 * @see implementation
	 */
	public boolean isNxN(int[][] array) {
		if (array == null) return false;
		int length = array[0].length;
		if (length != array.length) return false;
		for (int i = 1; i < array.length; i++)
			if (array[i].length != length) return false;
		return true;
	}



			/*
			 * |--------------------------------------------|
			 * | 2. Methods that handle the Solvability.		|
			 * |--------------------------------------------|
			 */



	/**
	 * Returns an internal representation of board where the zero tile is
	 * in the bottom right corner.
	 * @see implementation
	 */
	public int[][] getResetBoard() {
		int[][] tiles = deepCopy(this.tiles);	// First make a deep copy.
		int[] position = getIJPosition(tiles, 0);	// Get the position of zero.
		// If it's already in the correct spot, return the copy:
		if ( (position[0] == tiles.length - 1) && (position[1] == tiles.length - 1) ) return tiles;

		int displacement = position[0] - convertLineToIJ(getDefaultPosition(0))[0];	// Get the Y displacement.
		if (displacement < 0) tiles = moveDirection(tiles, position[0], position[1], "Y", 1, displacement);	// Move in the positive direction.
		else if (displacement > 0) tiles = moveDirection(tiles, position[0], position[1], "Y", -1, displacement);	// Move in the negative direction.

		position[0] = position[0] - displacement;	// Adjust the Y position.
		displacement = position[1] - convertLineToIJ(getDefaultPosition(0))[1];	// Get the X displacement.
		if (displacement < 0) tiles = moveDirection(tiles, position[0], position[1], "X", 1, displacement);	// Move in the positive direction.
		else if (displacement > 0) tiles = moveDirection(tiles, position[0], position[1], "X", -1, displacement);	// Move in the negative direction.
		return tiles;
	}

	/**
	 * Returns an array in which each index represents a value and each
	 * value is the position in the internal representation.
	 * @param	tiles
	 *				The internal representation to be simulated.
	 *
	 * @see implementation
	 */
	public int[] getTilesMap(int[][] tiles) {
		int[] result = new int[tiles.length*tiles.length];
		int count = 0;
		for (int i = 0; i < tiles.length; i++)
			for (int j = 0; j < tiles.length; j++) {
				count += 1;
				result[tiles[i][j]] = count;
			}
		return result;
	}


	/**
	 * Checks whether the initial board is solvable.
	 * @see implementation
	 */
	public boolean isSolvable()	{
		int[][] tiles = getResetBoard();
		int[] tilesMap = getTilesMap(tiles);
		double numeratorResult = 1, denominatorResult = 1;
		for (int j = 1; j < (tiles.length * tiles.length); j++)
			for (int i = 1; i < j; i++) {
				numeratorResult *= (tilesMap[j] - tilesMap[i]);
				denominatorResult *= (j - i);
			}
		if ( (numeratorResult == Double.POSITIVE_INFINITY) && (denominatorResult == Double.POSITIVE_INFINITY) )
			return true;
		return (numeratorResult / denominatorResult) >= 0;
	}


	/**
	 * Moves the zero element.
	 *
	 * @param	tiles
	 * 				The tile configuration in which the element needs to be moved.
	 * @param	i1
	 *				The initial i position of zero. (Row)
	 * @param	j1
	 *				The initial j position of zero. (Column)
	 * @param XorY
	 * 				Contains X or Y depending on if movement is in the x or y direction.
	 * @param direction
	 * 				The direction to be used (negative or positive).
	 * @param displacement
	 * 				The distance between the element and it's target position.
	 *
	 * @see implementation.
	 */
	private int[][] moveDirection(int[][] tiles, int i1, int j1, String XorY, int direction, int displacement) {
		int i = i1, j = j1;
		while (displacement != 0) {
			if (XorY == "X") {	// In case movement is in the X direction.
				tiles[i][j] = tiles[i][j + direction];
				tiles[i][j + direction] = 0;
				j += direction;
			} else if (XorY == "Y") {	// In case movement is in the Y direction.
				tiles[i][j] = tiles[i + direction][j];
				tiles[i + direction][j] = 0;
				i += direction;
			}
			displacement += direction;	// One displacement finished
		}
		return tiles;
	}



			/*
			 * |------------------------------------------------|
			 * | 3. Methods that handle the Priority Functions.	|
			 * |------------------------------------------------|
			 */



	/**
	 * Return number of blocks out of place.
	 */
	public int hamming() {
		int counter = 0, result = 0;
		for (int i = 0; i < this.tiles.length; i++)
			for (int j = 0; j < this.tiles.length; j++) {
				counter += 1;
				if ( (this.tiles[i][j] != 0) && (this.tiles[i][j] != counter) )
					result += 1;
			}
		return result;
	}

	/**
	 * Return sum of Manhattan distances between blocks and goal.
	 */
	public int manhattan() {
		int result = 0;
		for (int i = 0; i < this.tiles.length; i++)
			for (int j = 0; j < this.tiles.length; j++)
				if (this.tiles[i][j] != 0)
					result += getDistance(tiles[i][j], i, j);
		return result;
	}



			/*
			 * |------------------------------------------------|
			 * | 4. Methods that handle the A* implementation.	|
			 * |------------------------------------------------|
			 */



	/**
	 * Return a Collection of all neighboring board positions
	 */
	public Collection<Board> neighbors() {
		int[] position = getIJPosition(this.tiles, 0);
		Collection<Board> result = new ArrayList<Board>();

		if (isInBounds(position[0] + 1, position[1]) &&
				isAdjacentToEmpty(position[0] + 1, position[1]))
			result.add(moveToEmpty(position[0] + 1, position[1], position));
		if (isInBounds(position[0] - 1, position[1]) &&
				isAdjacentToEmpty(position[0] - 1, position[1]))
			result.add(moveToEmpty(position[0] - 1, position[1], position));
		if (isInBounds(position[0], position[1] + 1) &&
				isAdjacentToEmpty(position[0], position[1] + 1))
			result.add(moveToEmpty(position[0], position[1] + 1, position));
		if (isInBounds(position[0], position[1] - 1) &&
				isAdjacentToEmpty(position[0], position[1] - 1))
			result.add(moveToEmpty(position[0], position[1] - 1, position));

		return result;
	}


	/**
	 * Checks whether the given position is next to an empty tile.
	 *
	 * @param	i
	 * 				The i position. (Row)
	 * @param	j
	 * 				The j position. (Column)
	 *
	 * @see implementation
	 */
	public boolean isAdjacentToEmpty(int i, int j) {
		if (isInBounds(i + 1, j)) if (tiles[i + 1][j] == 0) return true;
		if (isInBounds(i - 1, j)) if (tiles[i - 1][j] == 0) return true;
		if (isInBounds(i, j + 1)) if (tiles[i][j + 1] == 0) return true;
		if (isInBounds(i, j - 1)) if (tiles[i][j - 1] == 0) return true;
		return false;
	}

	/**
	 * Checks whether the given position is in bounds of this board's tiles.
	 *
	 * @param	i
	 * 				The i position. (Row)
	 * @param	j
	 * 				The j position. (Column)
	 *
	 * @see implementation
	 */
	public boolean isInBounds(int i, int j) {
		return (i >= 0) && (i < tiles.length) && (j >= 0) && (j < tiles.length);
	}


	/**
	 * Moves a given position to it's adjacent empty square (if it has one).
	 * If this position is not next to an empty square or this position
	 * is not in bounds of this board's tiles, this board is returned.
	 * Otherwise the board in which the position is moved is returned.
	 *
	 * @param	i
	 * 				The i position. (Row)
	 * @param	j
	 * 				The j position. (Column)
	 *
	 * @see implementation
	 */
	public Board moveToEmpty(int i, int j, int[] position) {
		int[][] newTiles = deepCopy(tiles);
		// int[] position = getIJPosition(tiles, 0);
		newTiles[position[0]][position[1]] = newTiles[i][j];
		newTiles[i][j] = 0;
		return new Board(newTiles);
	}



			/*
			 * |----------------------------------------|
			 * | 5. Methods that handle the position.		|
			 * |----------------------------------------|
			 */



	/**
	 * Returns the line position of a certain value in given tiles.
	 *
	 * @param	tiles
	 * 				The tiles in which should be looked.
	 * @param	value
	 * 				The value that needs to be used.
	 *
	 * @see implementation
	 */
	public int getLinePosition(int[][] tiles, int value) {
		int counter = 0;
		for (int i = 0; i < tiles.length; i++)
			for (int j = 0; j < tiles.length; j++) {
				counter += 1;
				if (tiles[i][j] == value)
					return counter;
			}
		return -1;
	}

	/**
	 * Returns the default line position of a certain value in this board's tiles.
	 *
	 * @param	value
	 * 			The value that needs to be used.
	 *
	 * @see implementation
	 */
	public int getDefaultPosition(int value) {
		if (value == 0) return (this.tiles.length * this.tiles.length);
		return value;
	}

	/**
	 * Returns the IJ position of a certain value in given tiles.
	 *
	 * @param	tiles
	 * 				The tiles in which should be looked.
	 * @param	value
	 * 				The value that needs to be used.
	 *
	 * @see implementation
	 */
	public int[] getIJPosition(int[][] tiles, int value) {
		int[] result = {-1, -1};
		for (int i = 0; i < this.tiles.length; i++)
			for (int j = 0; j < this.tiles.length; j++)
				if (tiles[i][j] == value) {
					result[0] = i;
					result[1] = j;
					return result;
				}
		return result;
	}


	/**
	 * Calculates the position between a given element and it's default position.
	 *
	 * @param value
	 *				The value of the element.
	 * @param	i
	 * 				The i position. (Row)
	 * @param	j
	 * 				The j position. (Column)
	 *
	 * @see implementation
	 */
	public int getDistance(int value, int i, int j) {
		int[] defaultPosition = convertLineToIJ(getDefaultPosition(value));
		return Math.abs(i - defaultPosition[0]) + Math.abs(j - defaultPosition[1]);
	}



			/*
			 * |------------------------|
			 * | 6. New Helper Methods.	|
			 * |------------------------|
			 */



	/**
	 * Converts a given line position to it's position in i,j format.
	 *
	 * @param	position
	 * 				The position in line format.
	 *
	 * @see implementation
	 */
	public int[] convertLineToIJ(int position) {
		int[] result = {-1, -1};
		result[0] = (position - 1)/this.tiles.length;
		result[1] = (position - 1)%this.tiles.length;
		return result;
	}


	/**
	 * Return a copy of this board's tiles.
	 * @see implementation
	 */
	public int[][] getCopy() {
		return deepCopy(this.tiles);
	}


	/**
	 * Deep Copies a given array.
	 *
	 * @param	array
	 * 				The array that needs to be deep copied.
	 *
	 * @see implementation
	 */
	public int[][] deepCopy(int[][] array) {
		int[][] result = new int[array.length][array[0].length];
		for (int i = 0; i < array.length; i++)
			for (int j = 0; j < array.length; j++)
				result[i][j] = array[i][j];
		return result;
	}


	/**
	 * Prints a given array.
	 *
	 * @param	array
	 * 				The array that needs to be printed.
	 * 				Can also be an array of arrays.
	 *
	 * @see implementation
	 */
	public void printArray(int[] array) {
		System.out.format("[ ");
		for (int i = 0; i < array.length; i++) {
			System.out.format("%d", array[i]);
			if (i != array.length - 1)
				System.out.format(", ", array[i]);
		}
		System.out.format(" ]%n");
	}

	/**
	 * Prints a given array.
	 *
	 * @param	array
	 * 				The array that needs to be printed.
	 * 				Can also be a normal array.
	 *
	 * @see implementation
	 */
	public void printArray(int[][] array) {
		for (int i = 0; i < array.length; i++)
			printArray(array[i]);
	}



			/*
			 * |----------------------------|
			 * | 7. Given Helper Methods.		|
			 * |----------------------------|
			 */



	// Does this board equal y. Two boards are equal when they both were constructed
	// using tiles[][] arrays that contained the same values.
	@Override
	public boolean equals(Object y) {
		if ( !(y instanceof Board) )
			return false;

		Board other = (Board)y;
		return Arrays.deepEquals(tiles, other.tiles);
	}

	// Since we override equals(), we must also override hashCode(). When two objects are
	// equal according to equals() they must return the same hashCode. More info:
	// - http://stackoverflow.com/questions/27581/what-issues-should-be-considered-when-overriding-equals-and-hashcode-in-java/27609#27609
	// - http://www.ibm.com/developerworks/library/j-jtp05273/
    @Override
	public int hashCode()	{
		return Arrays.deepHashCode(tiles);
	}


	/**
	 * Return a string representation of the board.
	 */
	public String toString() {
		String result = "";
		for (int i = 0; i < this.tiles.length; i++) {
			for (int j = 0; j < this.tiles[i].length; j++) {
				if (this.tiles[i][j] == 0)
					result += "  ";
				else
					result += Integer.toString(this.tiles[i][j]) + " ";
			} result += "\n";
		} return result;
	}

}
