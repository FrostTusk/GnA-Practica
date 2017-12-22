package gna;
import java.util.*;

/*
 * Methods Index:
 * 1. Handler Methods (handler = set-up/running of other methods)
 * 2. Methods that handle the actual algorithm
 * 3. Helper Methods
 */

/**
 * Performs sort by using the Insertion Sort algorithm.
 *
 */
public class InsertionSort extends SortingAlgorithm {

			/*
			* |---------------------------------------------|
			* | 1. The next methods are "handler" methods.	|
			* |---------------------------------------------|
			*/

	/**
	 * Constructor.
	 */
	public InsertionSort() {
	}

	/*
	 * Variable that holds the amount comparisons used in this algorithm.
	 */
	private long comparisons = 0;


	/**
	 * Sorts the given array using insertion sort.
	 *
	 * @see super
	 */
	public long sort(Comparable[] array) throws IllegalArgumentException {
		if (array == null) {
			throw new IllegalArgumentException("argument 'array' must not be null.");
		}

		this.comparisons = 0;
		InsertionSortAlgorithm(array);
		return this.comparisons;
	}


			/*
			 * |---------------------------------------------------|
			 * | 2. The next methods handle the actual algorithm.	 |
			 * |---------------------------------------------------|
			 */


	/**
	 * An implementation of the Insertion Sort algorithm.
	 *
	 * @param		array
	 *					The array to be sorted.
	 */
	private long InsertionSortAlgorithm(Comparable[] array) {
		for (int i = 1; i < array.length; i++) {
			for (int j = 0; j < i; j++) {
				if (less(array[i], array[j])) {
					insert(array, i, j);
					break;
				}
			}
		}
		return comparisons;
	}


			/*
			 * |-------------------------------------------|
			 * | 3. The next methods are helper methods.	 |
			 * |-------------------------------------------|
			 */


	/**
	 * Compares 2 comparable values using the compareTo method
	 * and returns whether the first one is "smaller" than the second.
	 * Also keeps track of the amount of times this method is called.
	 *
	 * @param		value1
	 *					The first value to be compared.
	 * @param		value2
	 *					The second value to be compared.
	 *
	 * @return	true if value1 is "smaller" than value2, false if not.
	 */
	private boolean less(Comparable value1, Comparable value2) {
		this.comparisons += 1;
		return value1.compareTo(value2) < 0;
	}

	/**
	 * Swaps the values at 2 given positions of an array.
	 *
	 * @param		i
	 *					The position of the first value to be swapped.
	 * @param		j
	 *					The position of the second value to be swapped.
	 */
	private void swap(Comparable[] a, int i, int j) {
		Comparable t = a[i];
		a[i] = a[j];
		a[j] = t;
	}

	/**
	 * Inserts a value at a given in front of another given position.
	 *
	 * @param		from
	 *					The position of the value to be inserted.
 	 * @param		dest
	 *					The destination of the value to be inserted.
	 */
	private void insert(Comparable[] array, int from, int dest) {
		for (int i = dest; i < from; i++)
			swap(array, i, from);
	}

}
