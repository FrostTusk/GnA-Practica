package gna;
import java.util.*;

/*
 * Methods Index:
 * 1. Handler Methods (handler = set-up/running of other methods)
 * 2. Methods that handle the actual algorithm
 * 3. Helper Methods
 */
 
/**
 * Performs sort by using the Quick Sort algorithm.
 *
 */
public class QuickSort extends SortingAlgorithm{

			/*
			 * |---------------------------------------------|
			 * | 1. The next methods are "handler" methods.	 |
			 * |---------------------------------------------|
			 */


	/**
	 * Constructor.
	 */
	public QuickSort() {
	}

	/*
	 * Variable that holds the amount comparisons used in this algorithm.
	 */
	private long comparisons = 0;


  /**
	 * Sorts the given array using quick sort.
	 *
	 * @see super
	 */
	public long sort(Comparable[] array) throws IllegalArgumentException {
		if (array == null) {
			throw new IllegalArgumentException("argument 'array' must not be null.");
		}

		this.comparisons = 0;
		QuickSortAlgorithm(array);
		return this.comparisons;
	}


			/*
			 * |---------------------------------------------------|
			 * | 2. The next methods handle the actual algorithm.	|
			 * |---------------------------------------------------|
			 */


  /**
	 * An implementation of the Quick Sort algorithm.
	 * If no "start" and "to" paramaters are given, the method will call itself
	 * with start = 0 and to = array.length - 1.
	 *
	 * @param		array
	 *					The array to be sorted.
	 */
	private void QuickSortAlgorithm(Comparable[] array) {
		QuickSortAlgorithm(array, 0, array.length - 1);
	}

	/**
	 * An implementation of the Quick Sort algorithm.
	 *
	 * @param		array
	 *					The array to be sorted.
	 * @param		start
	 *					Where to start sorting. (Inclusive)
	 * @param 	to
	 *					Where to stop sorting. (Inclusive)
	 */
	private void QuickSortAlgorithm(Comparable[] array, int start, int to) {
		if (start >= to) return;
		int pivot = partion(array, start, to);
		QuickSortAlgorithm(array, start, pivot - 1);
		QuickSortAlgorithm(array, pivot + 1, to);
	}

	/**
	 * A method that partitions an array into 2 halves using a pivot.
	 * The first element of the array is chosen as pivot.
	 *
	 * @param		array
	 *					The array to be partitioned.
	 * @param		start
	 *					Where to start partitioning. (Inclusive)
	 * @param 	to
	 *					Where to stop partitioning. (Inclusive)
	 */
	private int partion(Comparable[] array, int start, int to) {
		int pivot = start; int i = start; int j = to + 1;
		while (i < j) {
			while (less(array[++i], array[pivot]))	if (i >= to) break;
			while (less(array[pivot], array[--j]))	if (j <= start) break;
			if (i < j)	swap(array, i, j);
		}
		swap(array, pivot, j);
		return j;
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
	* @param	value1
	*					The first value to be compared.
	* @param	value2
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
	* @param	i
	*					The position of the first value to be swapped.
	* @param	j
	*					The position of the second value to be swapped.
	*/
	private void swap(Comparable[] a, int i, int j) {
	 Comparable t = a[i];
	 a[i] = a[j];
	 a[j] = t;
	}

}
