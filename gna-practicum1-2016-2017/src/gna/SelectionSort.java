package gna;
import java.util.*;

/*
 * Methods Index:
 * 1. Handler Methods (handler = set-up/running of other methods)
 * 2. Methods that handle the actual algorithm
 * 3. Helper Methods
 */

/**
 * Performs sort by using the Selection Sort algorithm.
 *
 */
public class SelectionSort extends SortingAlgorithm {

			/*
			 * |---------------------------------------------|
			 * | 1. The next methods are "handler" methods.  |
			 * |---------------------------------------------|
			 */


	/**
	 * Constructor.
	 */
	public SelectionSort() {
	}

	/*
	 * Variable that holds the amount comparisons used in this algorithm.
	 */
	private long comparisons = 0;


	/**
	 * Sorts the given array using selection sort.
	 *
	 * @see super
	 */
	public long sort(Comparable[] array) throws IllegalArgumentException {
		if (array == null) {
			throw new IllegalArgumentException("argument 'array' must not be null.");
		}

		this.comparisons = 0;
		SelectionSortAlgorithm(array);
		return this.comparisons;
	}


			/*
			 * |---------------------------------------------------|
			 * | 2. The next methods handle the actual algorithm.	 |
			 * |---------------------------------------------------|
			 */


	/**
	 * An implementation of the Selection Sort algorithm.
	 *
	 * @param		array
	 *					The array to be sorted.
	 */
	private void SelectionSortAlgorithm(Comparable[] array) {
		for (int i = 0; i < array.length - 1; i++) {
			int temp = i;
			for (int j = i + 1; j < array.length; j++)
				if (less(array[j], array[temp]))	temp = j;
			swap(array, i, temp);
		}
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

}
