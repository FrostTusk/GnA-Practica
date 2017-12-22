package gna;

import static org.junit.Assert.*;

import java.util.Arrays;

import libpract.SortingAlgorithm;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests provided to the students to prevent technical errors that make grading hard.
 *
 * NOTE THAT THESE TESTS DO NOT ACTUALLY TEST THE ALGORITHM! The student
 * still has to write his/her own tests to test whether his/her algorithm
 * works correctly.
 */
public class TechnicalTests {
	static SortingAlgorithm quickSort;
	static SortingAlgorithm insertionSort;
	static SortingAlgorithm selectionSort;

	@BeforeClass
	public static void beforeClass(){
		quickSort = new QuickSort();
		insertionSort = new InsertionSort();
		selectionSort = new SelectionSort();
	}

	/**
	 * Test whether calling sorter multiple times fails.
	 *
	 * Tests whether calling the given sorter multiple times results
	 * in different 'sorted' arrays or inconsistent number of compares.
	 * @pre	sorter is not null.
	 */
	public void testCallMultipleTimes(SortingAlgorithm sorter){
		Integer[] array = {4, 2, 1, 3, -1};
		Comparable[] arrayClone1 = array.clone();
		Comparable[] arrayClone2 = array.clone();
		long nbCompares1 = sorter.sort(arrayClone1);
		long nbCompares2 = sorter.sort(arrayClone2);

		assertEquals ("Tweemaal de methode sort oproepen op eenzelfde object " +
				"op dezelfde array, zou tweemaal hetzelfde aantal getelde compares moeten returnen " +
				"maar bij jouw implementatie is dat niet zo.",
				nbCompares1, nbCompares2);
		assertTrue("Tweemaal de methode sort oproepen op eenzelfde object " +
				"op dezelfde array, zou tweemaal dezelfde gesorteerde array moeten opleveren, " +
				"maar bij jouw implementatie is dat niet zo.",
				Arrays.equals(arrayClone1, arrayClone2));
	}

	/**
	 * Test whether using InsertionSorter multiple times gives inconsistent results.
	 */
	@Test(timeout = 5000)
	public void testInsertionSortMultipleCalls(){
		testCallMultipleTimes(insertionSort);
	}

	@Test(timeout = 5000)
	public void testQuickSortMultipleCalls(){
		testCallMultipleTimes(quickSort);
	}

	@Test(timeout = 5000)
	public void testSelectionSortMultipleCalls(){
		testCallMultipleTimes(selectionSort);
	}

	/**
	 * A Comparable that is not just an Integer class.
	 */
	private class IntBox implements Comparable<IntBox>{
		int i;

		public IntBox(int i){
			this.i = i;
		}

		@Override
		public int compareTo(IntBox o) {
			Integer self = this.i;
			Integer other = o.i;
			return self.compareTo(other);
		}

	}

	private IntBox[] getBoxes(){
		IntBox[] boxes = {new IntBox(1000), new IntBox(1), new IntBox(10), new IntBox(5)};
		return boxes;
	}

	/**
	 * Test whether the student made the error of casting the Comparable to something else.
	 */
	@Test(timeout = 5000)
	public void testDoNotCast() {
		try {
			quickSort.sort(getBoxes());
			selectionSort.sort(getBoxes());
			insertionSort.sort(getBoxes());
		} catch (ClassCastException e) {
			fail ("Je algoritme heeft een classcast exception. Waarschijnlijk probeer" +
					" je de Comparable to casten naar bijvoorbeeld Integer. Hiermee " +
					" breek je het contract van je functie dat zegt dat het op (alle)" +
					" Comparables werkt.");
		}
	}




}
