package gna;

import java.io.IOException;
import java.util.*;
import libpract.*;

import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestStitcher {
	
	@SuppressWarnings("unused")
	private static final double EPSILON = 0.0001;
	
	
	@After
	public void breakDown() throws IOException {
		int[][] image1 = {{70000, 100, 1}, // Not assignment image.
				  		  {20000, 800, 5},
				          {10000, 100, 8} };
		int[][] image2 = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
		Stitcher stitcher = new Stitcher();
		stitcher.seam(image1, image2);
		stitcher.stitch(image1, image2); // Make sure that mask_output has correct mask.
	}
	
	
	
	@Test
	public void testSeamMultipleCalls() {
		int[][] image1 = {{1, 2, 3}, {1, 2, 3}, {1, 2, 3}};
		int[][] image2 = {{3, 2, 1}, {3, 2, 1}, {3, 2, 1}};
		Stitcher stitcher = new Stitcher();
		List<Position> seamExpected = stitcher.seam(image1, image2);
		for (int i = 0; i < 5; i++) {
			List<Position> seamReturn = stitcher.seam(image1, image2);
			assertSameList(seamExpected, seamReturn);
		}
	}
	
	
	@Test
	public void testStitchMultipleCalls() throws IOException {
		int[][] image1 = {{1, 2, 3}, {1, 2, 3}, {1, 2, 3}};
		int[][] image2 = {{3, 2, 1}, {3, 2, 1}, {3, 2, 1}};
		Stitcher stitcher = new Stitcher();
		Stitch[][] maskExpected  = stitcher.stitch(image1, image2);
		assertTrue(maskExpected[0][0] == Stitch.SEAM);
		assertTrue(maskExpected[maskExpected.length - 1][maskExpected[0].length - 1] == Stitch.SEAM);
		for (int i = 0; i < 5; i++) {
			Stitch[][] maskReturn = stitcher.stitch(image1, image2);
			assertTrue(maskReturn[0][0] == Stitch.SEAM);
			assertTrue(maskReturn[maskReturn.length - 1][maskReturn[0].length - 1] == Stitch.SEAM);
			assertSameArray(maskReturn, maskExpected);
		}
	}
	
	@Test
	public void testStitchSinglePixelImage() throws IOException {
		int[][] image1 = {{1}};
		int[][] image2 = {{2}};
		Stitcher stitcher = new Stitcher();
		Stitch[][] maskReturn = stitcher.stitch(image1, image2);
		Stitch[][] maskExpected = {{Stitch.SEAM}};
		assertTrue(maskExpected[0][0] == Stitch.SEAM);
		assertTrue(maskExpected[maskExpected.length - 1][maskExpected[0].length - 1] == Stitch.SEAM);
		assertTrue(maskReturn[0][0] == Stitch.SEAM);
		assertTrue(maskReturn[maskReturn.length - 1][maskReturn[0].length - 1] == Stitch.SEAM);
		assertSameArray(maskExpected, maskReturn);
	}

	@Test
	public void testStitchSingleWidthImage() throws IOException {
		int[][] image1 = {{1}, {1}, {1}, {1}};
		int[][] image2 = {{2}, {2}, {2}, {2}};
		Stitcher stitcher = new Stitcher();
		Stitch[][] maskReturn = stitcher.stitch(image1, image2);
		Stitch[][] maskExpected = {{Stitch.SEAM}, {Stitch.SEAM}, {Stitch.SEAM}, {Stitch.SEAM}};
		assertTrue(maskExpected[0][0] == Stitch.SEAM);
		assertTrue(maskExpected[maskExpected.length - 1][maskExpected[0].length - 1] == Stitch.SEAM);
		assertTrue(maskReturn[0][0] == Stitch.SEAM);
		assertTrue(maskReturn[maskReturn.length - 1][maskReturn[0].length - 1] == Stitch.SEAM);
		assertSameArray(maskExpected, maskReturn);
	}
	
	@Test
	public void testStitchSingleHeightImage() throws IOException {
		int[][] image1 = {{1, 1, 1, 1}};
		int[][] image2 = {{2, 2, 2, 2}};
		Stitcher stitcher = new Stitcher();
		Stitch[][] maskReturn = stitcher.stitch(image1, image2);
		Stitch[][] maskExpected = {{Stitch.SEAM,Stitch.SEAM,Stitch.SEAM, Stitch.SEAM}};
		assertTrue(maskExpected[0][0] == Stitch.SEAM);
		assertTrue(maskExpected[maskExpected.length - 1][maskExpected[0].length - 1] == Stitch.SEAM);
		assertTrue(maskReturn[0][0] == Stitch.SEAM);
		assertTrue(maskReturn[maskReturn.length - 1][maskReturn[0].length - 1] == Stitch.SEAM);
		assertSameArray(maskExpected, maskReturn);
	}
	
	@Test
	public void testStitchAssignmentImages() throws IOException {
		int[][] image1 = {{70000, 100, 1}, 
						  {20000, 800, 5}, 
						  {10000, 100, 8} };	// Not assignment image.
		int[][] image2 = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
		Stitcher stitcher = new Stitcher();
		Stitch[][] maskReturn = stitcher.stitch(image1, image2);
		assertTrue(maskReturn[0][0] == Stitch.SEAM);
		assertTrue(maskReturn[maskReturn.length - 1][maskReturn[0].length - 1] == Stitch.SEAM);
	}
	
	
	
	public <T> void assertSameArray(T[][] checkResult, T[][] returnResult) {
		for (int i = 0; i < checkResult.length; i++)
			for (int j = 0; j < checkResult[i].length; j++)
				assertTrue(returnResult[i][j] == checkResult[i][j]);
	}

	public void assertSameList(List<Position> checkResult, List<Position> returnResult) {
		for (int i = 0; i < checkResult.size(); i++) {
			assertTrue(checkResult.get(i).getX() == returnResult.get(i).getX());  
			assertTrue(checkResult.get(i).getY() == returnResult.get(i).getY()); 
		}
	}
	
}
