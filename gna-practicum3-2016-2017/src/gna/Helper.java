package gna;

import libpract.Position;
import libpract.Stitch;

public class Helper {
	
	/**
	 * Prints a given array.
	 *
	 * @param	array
	 * 			The array that needs to be printed.
	 * 			Can also be an array of arrays.
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
	 * 			The array that needs to be printed.
	 * 			Can also be a normal array.
	 *
	 * @see implementation
	 */
	public void printArray(int[][] array) {
		for (int i = 0; i < array.length; i++)
			printArray(array[i]);
	}
	
	
	/**
	 * Prints a given array.
	 *
	 * @param	array
	 * 			The array that needs to be printed.
	 * 			Can also be an array of arrays.
	 *
	 * @see implementation
	 */
	public void printArray(Position[] array) {
		System.out.format("[ ");
		for (int i = 0; i < array.length; i++) {
			if (array[i] == null)
				System.out.format("NULL");
			else
				System.out.format("%s", array[i].toString());
			if (i != array.length - 1)
				System.out.format(", ");
		}
		System.out.format(" ]%n");
	}

	/**
	 * Prints a given array.
	 *
	 * @param	array
	 * 			The array that needs to be printed.
	 * 			Can also be a normal array.
	 *
	 * @see implementation
	 */
	public void printArray(Position[][] array) {
		for (int i = 0; i < array.length; i++)
			printArray(array[i]);
	}
	

	/**
	 * Prints a given array.
	 *
	 * @param	array
	 * 			The array that needs to be printed.
	 * 			Can also be an array of arrays.
	 *
	 * @see implementation
	 */
	public void printArray(Stitch[] array) {
		System.out.format("[ ");
		for (int i = 0; i < array.length; i++) {
			if (array[i] == null)
				System.out.format("NULL");
			else
				printEnum(array[i]);
			if (i != array.length - 1)
				System.out.format(", ");
		}
		System.out.format(" ]%n");
	}

	/**
	 * Prints a given array.
	 *
	 * @param	array
	 * 			The array that needs to be printed.
	 * 			Can also be a normal array.
	 *
	 * @see implementation
	 */
	public void printArray(Stitch[][] array) {
		for (int i = 0; i < array.length; i++)
			printArray(array[i]);
	}
	
	
	/**
	 * Print a given stitch value.
	 * 
	 * @param 	value
	 * 			The stitch value to be printed.
	 */
	public void printEnum(Stitch value) {
		switch (value) {
		case EMPTY:
			System.out.format("EMPTY");
			break;
		case IMAGE1:
			System.out.format("IMAGE1");
			break;
		case IMAGE2:
			System.out.format("IMAGE2");
			break;
		case SEAM:
			System.out.format("SEAM");
		default:
			break;
		}
	}

}
