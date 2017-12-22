package gna;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import libpract.SortingAlgorithm;


/*
 * Methods Index:
 * 1. Handler Methods (handler = set-up/running of other methods)
 * 2. Methods that handle printing and incrementing n
 * 3. Methods that handle the tests
 * 4. Helper Methods
 */

public class Main {

			/*
			 * |-------------------------------------------------------------|
			 * | 1. The next methods are handler methods of the main class.	 |
			 * |-------------------------------------------------------------|
			 */



	private static Scanner scanner;
	private static SortingAlgorithm selectionSort;
	private static SortingAlgorithm insertionSort;
	private static SortingAlgorithm quickSort;

	/**
	 * main function. Sets up the private variables and calls the testHandler method.
	 * To change how testHandler will behave the local variables at the start of main
	 * need to be changed.
	 */
	public static void main(String[] args) {
		/*
		 * Count: The amount of tests to be ran.
		 * Low:	The lowest random int value of the arrays to be tested.
		 * High: The highest random int value of the arrays to be tested.
		 * maxN: The largest possibe size of the arrays to be tested.
		 * Mode: The mode in which testHandler will be called.
		 *			 0 = Don't run any tests.
		 * 			 1 = Print N increment (normal), 2 = Print Raw Tests (normal), 3 = Print Average Tests (normal)
		 *			 4 = Print N increment (doubling, C(N)), 5 = Print Raw Tests (doubling, C(N)), 6 = Print Average Tests (doubling, C(N))
		 *			 7 = Print Raw Tests (doubling, T(N))
		 */
		int count = 15, low = 0, high = 99, maxN = 65000, mode = 7;
		scanner = new Scanner(System.in);
		selectionSort = new SelectionSort();
		insertionSort = new InsertionSort();
		quickSort = new QuickSort();
		if (! (mode == 0))	testHandler(maxN, count, low, high, mode);
		System.out.println("Done...");
		if ( (mode != 0) && (mode != 3) && (mode != 4) )	scanner.next();
		// Otherwise in cases with a lot of output, not everything will be printed.
	}

	/**
	 * Handler method for this class. Directs all inputs to their desired methods.
	 * Runs tests for all the implemented algorithms, will print out all
	 * n values or resulting data depending upon mode.
	 *
	 * @param		maxN
	 *					The largest possibe size of the arrays to be tested.
	 * @param		count
	 *					The amount of tests to be ran.
	 * @param 	low
	 *					The lowest random int value of the arrays to be tested.
	 * @param		high
	 *					The highest random int value of the arrays to be tested.
	 * @param		mode
	 *					The mode in which methods will be called.
	 */
	public static void testHandler(int maxN, int count, int low, int high, int mode) {
		SortingAlgorithm[] algorithms = {selectionSort, insertionSort, quickSort};
		for (int i = 0; i < algorithms.length/*algorithms.length*/; i++) {
			SortingAlgorithm algorithm = algorithms[i];
			System.out.format("Algorithm: ");
			System.out.println(algorithm);

			if ( (mode == 1) || (mode == 4) )	printIncrement(maxN, count, mode);
			else	printTests(algorithm, maxN, count, low, high, mode);
		}
	}


			/*
			 * |-----------------------------------------------------------|
			 * | 2. The next methods handle printing and incrementing n.	 |
			 * |-----------------------------------------------------------|
			 */



	/**
	 * Prints out all the n values up to a maximum n, in a given amount
	 * of tests. Increments with the method incrementN.
	 *
	 * @param		maxN
	 *					The largest possibe size of the arrays to be tested.
	 * @param		count
	 *					The amount of tests to be ran.
	 * @param		mode
	 *					The mode in which methods will be called.
	 */
	public static void printIncrement(int maxN, int count, int mode) {
		int n = 1;
		if (mode == 4)	n = 250;
		System.out.println("Printing Column N: ");
		while (n <= maxN) {
			for (int i = 0; i < count; i++)	System.out.println(n);
			if (mode == 1) n = incrementN(n);
			if (mode == 4) n = doublingRatio(n);
		}
	}


	/**
	 * Auto-Increments a given n value.
	 *
	 * @param		n
	 *					The n value to be incremented.
	 */
	public static int incrementN(int n) {
		return n + 1;
	}

	/**
	 * Increments a given n value using a doubling ratio.
	 *
	 * @param		n
	 *					The n value to be incremented.
	 */
	public static int doublingRatio(int n) {
		return n * 2;
	}



			/*
			 * |-----------------------------------------------|
			 * |  3. The next methods handle the actual tests. |
			 * |-----------------------------------------------|
			 */



	/**
	 * Handler method for the data tests. Directs all inputs to their desired methods.
	 * Prints out the raw data or average data depending upon the mode.
	 *
	 * @param 	algorithm
	 						The algorithm to be used.
	 * @param		maxN
	 *					The largest possibe size of the arrays to be tested.
	 * @param		count
	 *					The amount of tests to be ran.
	 * @param 	low
	 *					The lowest random int value of the arrays to be tested.
	 * @param		high
	 *					The highest random int value of the arrays to be tested.
	 * @param		mode
	 *					The mode in which methods will be called.
	 */
	public static void printTests(SortingAlgorithm algorithm, int maxN, int count, int low, int high, int mode) {
		int n = 1;
		if ( (mode == 5) || (mode == 6) || (mode == 7) )	n = 250;
		System.out.println("Printing Tests: ");
		while (n <= maxN) {
			singleSizeTest(algorithm, n, count, low, high, mode);
			if ( (mode == 2) || (mode == 3) )	n = incrementN(n);
			if ( (mode == 5) || (mode == 6) || (mode == 7))	n = doublingRatio(n);
		}
	}


	/**
	 * Runs a single size test of a given algorithm. Prints out all the raw data
	 * or the average data depending upon the mode.
	 *
	 * @param 	algorithm
	 						The algorithm to be used.
	 * @param		n
	 *					The size of the array.
	 * @param		count
	 *					The amount of tests to be ran.
	 * @param 	low
	 *					The lowest random int value of the arrays to be tested.
	 * @param		high
	 *					The highest random int value of the arrays to be tested.
	 * @param		mode
	 *					The mode in which methods will be called.
	 */
	public static void singleSizeTest(SortingAlgorithm algorithm, int n, int count, int low, int high, int mode) {
		long total = 0, startTime, endTime;
		for (int i = 0; i < count; i++) {
			Comparable[] array = buildArray(n, low, high);
			startTime = System.currentTimeMillis();
			long amount = algorithm.sort(array);
			endTime = System.currentTimeMillis();
			if ( (mode == 2) || (mode == 5) )	System.out.println(amount);
			if (mode == 7) System.out.println(endTime - startTime);
			else total += amount;
		}

		if ( (mode == 3) || (mode == 6) ) {
			total = total / count;
			System.out.println(total);
		}
	}



			/*
			 * |-------------------------------------------|
			 * | 4. The next methods are helper methods.	 |
			 * |-------------------------------------------|
			 */



	/**
	 * Returns a single random number between 2 given values.
	 *
	 * @param 	low
	 *					The lowest random int value. (Inclusive)
	 * @param		high
	 *					The highest random int value. (Inclusive)
	 */
	public static int getRandomNumber(int low, int high) {
	 	int randInt = ThreadLocalRandom.current().nextInt(low, high + 1);
		return randInt;
	}


	/**
	 * Returns an array of a given length built up by integers between
	 * 2 given value.
	 *
	 * @param		n
	 *					The size of the array.
	 * @param 	low
	 *					The lowest random int value.
	 * @param		high
	 *					The highest random int value.
	 */
	public static Comparable[] buildArray(int n, int low, int high) {
		Comparable[] array = new Comparable[n];
		for (int i = 0; i < n; i++) {
			int randInt = getRandomNumber(low, high);
			array[i] = randInt;
		}
		return array;
	}

}
