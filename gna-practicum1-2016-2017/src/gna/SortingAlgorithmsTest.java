package gna;

import libpract.SortingAlgorithm;
import java.util.*;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

/*
 * Methods Index:
 * 1. Methods that handle set-up of the test suite
 * 2. Basic Tests
 * 3. advanced Tests
 * 4. Helper Methods
 */

/**
 * Tests that test whether the implementations of the sorting algorithms do sort.
 */
public class SortingAlgorithmsTest {

			/*
			 * |---------------------------------------------|
			 * | 1. The next methods set-up the test suite.	 |
			 * |---------------------------------------------|
			 */


	static SortingAlgorithm selectionSort;
	static SortingAlgorithm insertionSort;
	static SortingAlgorithm quickSort;
	private SortingAlgorithm[] algorithms = {selectionSort, insertionSort, quickSort};

	@BeforeClass
	public static void beforeClass(){
		selectionSort = new SelectionSort();
		insertionSort = new InsertionSort();
		quickSort = new QuickSort();
	}


		  /*
			 * |-------------------------------------|
			 * | 2. The next test are basic tests.	 |
	 	   * |-------------------------------------|
			 */


	@Test
	public void basicTests() {
		for (int i = 0; i < algorithms.length; i++) {
			// System.out.format("Algorithm = %d%n", i);
			SortingAlgorithm algorithm = algorithms[i];

			Integer[] array1 = {4, 2, 1, 3, -1};
			algorithm.sort(array1);
			// System.out.println(Arrays.toString(array1));
			isSorted(array1);
		}
	}

	@Test
	public void basicDoubleSizeTests() {
		for (int i = 0; i < algorithms.length; i++) {
			// System.out.format("Algorithm = %d%n", i);
			SortingAlgorithm algorithm = algorithms[i];

			Integer[] array = {4, 2, 1, 3, -1, -4, 5, 6, 8, 9};
			algorithm.sort(array);
			isSorted(array);
		}
	}

	@Test
	public void allPosTest() {
		for (int i = 0; i < algorithms.length; i++) {
			SortingAlgorithm algorithm = algorithms[i];
			Integer[] array = {6, 8, 7, 9, 5};
			algorithm.sort(array);
			isSorted(array);
		}
	}

	@Test
	public void allNegTest() {
		for (int i = 0; i < algorithms.length; i++) {
			SortingAlgorithm algorithm = algorithms[i];
			Integer[] array = {-6, -8, -7, -9, -5};
			algorithm.sort(array);
			isSorted(array);
		}
	}

	@Test
	public void allZero() {
		for (int i = 0; i < algorithms.length; i++) {
			SortingAlgorithm algorithm = algorithms[i];
			Integer[] array = {0, 0, 0, 0, 0};
			algorithm.sort(array);
			isSorted(array);
		}
	}

	@Test
	public void allEqualTestsPos() {
		for (int i = 0; i < algorithms.length; i++) {
			SortingAlgorithm algorithm = algorithms[i];
			Integer[] array = {5, 5, 5, 5, 5};
			algorithm.sort(array);
			isSorted(array);
		}
	}

	@Test
	public void allEqualTestsNeg() {
		for (int i = 0; i < algorithms.length; i++) {
			SortingAlgorithm algorithm = algorithms[i];
			Integer[] array = {-5, -5, -5, -5, -5};
			algorithm.sort(array);
			isSorted(array);
		}
	}


		 /*
		  * |----------------------------------------|
		  * | 3. The next test are advanced tests.	 |
		  * |----------------------------------------|
		  */


  @Test
	public void alreadySortedTest() {
		for (int i = 0; i < algorithms.length; i++) {
			SortingAlgorithm algorithm = algorithms[i];
			Integer[] array = {0, 1, 2, 3, 4};
			algorithm.sort(array);
			isSorted(array);
		}
	}

	@Test
	public void alreadySortedInverseTest() {
		for (int i = 0; i < algorithms.length; i++) {
			SortingAlgorithm algorithm = algorithms[i];
			Integer[] array = {4, 3, 2, 1, 0};
			algorithm.sort(array);
			isSorted(array);
		}
	}

  @Test(expected = IllegalArgumentException.class)
 	public void arrayIsNullTest() {
 	for (int i = 0; i < algorithms.length; i++) {
 			SortingAlgorithm algorithm = algorithms[i];
 			Integer[] array = null;
 			algorithm.sort(array);
 		}
 	}

	@Test
 	public void stringTest() {
 		for (int i = 0; i < algorithms.length; i++) {
 			SortingAlgorithm algorithm = algorithms[i];
 			String[] array = {"c", "a", "r", "o", "l", "i", "n", "e"};
 			algorithm.sort(array);
 			// System.out.println(Arrays.toString(array));
 			isSorted(array);
 		}
 	}


		  /*
			 * |-------------------------------------------|
			 * | 4. The next methods are helper methods.	 |
			 * |-------------------------------------------|
			 */


	public void isSorted(Comparable[] array) {
		for (int i = 1; i < array.length; i++)
			assertTrue(lessOrEqual(array[i-1], array[i]));
	}

	public static boolean lessOrEqual(Comparable v, Comparable w)
	{ return v.compareTo(w) <= 0; }

}
