package gna;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;
import libpract.*;

/**
 * Implement the methods stitch, seam and floodfill.
 */
public class Stitcher {

	@SuppressWarnings("unused")
	private Helper helper = new Helper();
	private String path = "Enter path to outputfile directory here!";
	private String outputfile = "Enter outputfile name here!";


	/**
	 * Default constructor for this stitcher object,
	 * Initializes the comparator and priority queue.
	 */
	public Stitcher() {
		comparator = new Comparator<Position>() {
			@Override
			public int compare(Position o1, Position o2) {
				if (distTo[o1.getX()][o1.getY()] < distTo[o2.getX()][o2.getY()])
					return -1;
				else if (distTo[o1.getX()][o1.getY()] > distTo[o2.getX()][o2.getY()])
					return 1;
				return 0;
			}
		};
		queue = new PriorityQueue<Position>(comparator);
	}



	private Comparator<Position> comparator;
	private PriorityQueue<Position> queue;
	private int lengthI;
	private int lengthJ;
	private double[][] distTo;
	private Position[][] edgeTo;


	/**
	 * Set the internal lengths to the given lengths.
	 *
	 * @param	lengthI
	 * 			The length to which the internal i length needs to be set.
	 * @param	lengthJ
	 * 			The length to which the internal j length needs to be set.
	 */
	private void setLengths(int lengthI, int lengthJ) {
		this.lengthI = lengthI;
		this.lengthJ = lengthJ;
	}

	/**
	 * Reset distTo and edgeTo so that dijkstra's algorithm can be ran again.
	 */
	private void resetDijkstra() {
		distTo = new double[lengthI][lengthJ]; // Create a new distTo.
		edgeTo = new Position[lengthI][lengthJ];	// Create a new edgeTo.
		for (int i = 0; i < lengthI; i++)
			for (int j = 0; j < lengthJ; j++) {
				distTo[i][j] = Double.POSITIVE_INFINITY;
			}
		distTo[0][0] = 0;	// Initialize 0,0 with the correct values.
		edgeTo[0][0] = null;
	}


	/**
	 * Return the sequence of positions on the seam. The first position in the
	 * sequence is (0, 0) and the last is (width - 1, height - 1). Each position
	 * on the seam must be adjacent to its predecessor and successor (if any).
	 * Positions that are diagonally adjacent are considered adjacent.
	 *
	 * image1 and image2 are both non-null and have equal dimensions.
	 *
	 * Remark: Here we use the default computer graphics coordinate system,
	 *   illustrated in the following image:
	 *
	 *        +-------------> X
	 *        |  +---+---+
	 *        |  | A | B |
	 *        |  +---+---+
	 *        |  | C | D |
	 *        |  +---+---+
	 *      Y v
	 *
	 *   The historical reasons behind using this layout is explained on the following
	 *   website: http://programarcadegames.com/index.php?chapter=introduction_to_graphics
	 *
	 *   Position (y, x) corresponds to the pixels image1[y][x] and image2[y][x]. This
	 *   convention also means that, when an automated test mentioned that it used the array
	 *   {{A,B},{C,D}} as a test image, this corresponds to the image layout as shown in
	 *   the illustration above.
	 */
	public List<Position> seam(int[][] image1, int[][] image2) {
		int[][] oldImage = image2, newImage = image1;	// Initialize the image references.
		setLengths(image1.length, image1[0].length);	// Set the lengths.
		queue.clear();	// Clear the queue so seam can be used again.
		resetDijkstra();	// Reset disTo and edgeTo as specified in Dijkstra's algorithm.

		Position position = new Position(0, 0);
		queue.add(position);	// Add the initial position.
		while ((position.getX() != lengthI - 1) || (position.getY() != lengthJ - 1)) {
			newImage = (newImage == image1) ? image2: image1; // Switch image. Not neccessary anymore.
			oldImage = (newImage == image1) ? image2: image1; // Switch image.
			position = queue.remove();	// Get the next edge.
			relax(oldImage, newImage, position);	// Relax the next edge.
		}

		return getSolution();
	}

	/**
	 * Relax a given position.
	 *
	 * @param 	oldImage
	 * 			The image used in the last call of relax.
	 * 			Used for rgb calculation.
	 * @param 	newImage
	 * 			The image to be used in this call of relax.
	 * 			Used for rgb calculation.
	 * @param 	position
	 * 			The position to be relaxed.
	 */
	private void relax(int[][] oldImage, int[][] newImage, Position position) {
		for (Position neighbor: getNeighborsSeam(position)) { // Iterate over all the neighbors.
			int rgbvalue = ImageCompositor.pixelSqDistance(oldImage[neighbor.getX()][neighbor.getY()], // Calculate the rgb value.
														   newImage[neighbor.getX()][neighbor.getY()]);
			if (distTo[neighbor.getX()][neighbor.getY()] > distTo[position.getX()][position.getY()] + rgbvalue) {
				distTo[neighbor.getX()][neighbor.getY()] = distTo[position.getX()][position.getY()] + rgbvalue;
				edgeTo[neighbor.getX()][neighbor.getY()] = position;	// Make this the incident edge in edgeTo for this neighbor.
				if (queue.contains(neighbor))	// Update the position in the queue if necessary.
					queue.remove(neighbor);
				queue.add(neighbor);	// Add to queue.
			}
		}
	}


	/**
	 * Checks if a given position is in range of the internal lengths.
	 *
	 * @param 	position
	 * 			The position to be checked.
	 */
	public boolean isInRange(Position position) {
		return (position.getX() >= 0) && (position.getY() >= 0) &&
			   (position.getX() < lengthI) && (position.getY() < lengthJ);
	}


	/**
	 * Get the neighbors of a given position as specified in the seam algorithm.
	 *
	 * @param 	position
	 * 			The position of which the neighbors need to be returned.
	 */
	public List<Position> getNeighborsSeam(Position position) {
		List<Position> result =  new ArrayList<>();
		int[] offset = {1, -1, 0};	// Create the offset array.
		for (int i = 0; i < offset.length; i++)
			for (int j = 0; j < offset.length; j++) {
				if ( (offset[i] == 0) && (offset[j] == 0) ) // 0,0 is not a valid offset.
					continue;
				Position neighborPosition = new Position(position.getX() + offset[i], position.getY() + offset[j]);
				if (isInRange(neighborPosition)) // If the neighbor is valid, add it to the result.
					result.add(neighborPosition);
			}
		return result;
	}


	/**
	 * Get the solution of the last call of seam.
	 * Uses the internal arrays (edgeTo/distTo) to find the solution.
	 */
	public List<Position> getSolution() {
		Stack<Position> stack = new Stack<Position>();
		Position current = new Position(lengthI - 1, lengthJ -1);

		stack.add(current);	// Add the initial position to the stack.
		while (edgeTo[current.getX()][current.getY()] != null) { // If the next position is not null,
			current = edgeTo[current.getX()][current.getY()];	// add the next position to the stack.
			stack.add(current);
		}

		List<Position> result = new ArrayList<Position>();
		while (!stack.isEmpty())	// Simple stack conversion.
			result.add(stack.pop());
		return result;
	}



	/**
	 * Apply the floodfill algorithm described in the assignment to mask. You can assume the mask
	 * contains a seam from the upper left corner to the bottom right corner. The seam is represented
	 * using Stitch.SEAM and all other positions contain the default value Stitch.EMPTY. So your
	 * algorithm must replace all Stitch.EMPTY values with either Stitch.IMAGE1 or Stitch.IMAGE2.
	 *
	 * Positions left to the seam should contain Stitch.IMAGE1, and those right to the seam
	 * should contain Stitch.IMAGE2. You can run `ant test` for a basic (but not complete) test
	 * to check whether your implementation does this properly.
	 * @throws FileNotFoundException
	 */
	public void floodfill(Stitch[][] mask) throws IOException {
		Stack<Position> stack = new Stack<Position>();
		Position start = getStart(mask, true, 0, mask.length);
		if (start == null)
			start = getStart(mask, false, mask.length - 1, mask[0].length);
//		Position start = new Position(mask.length - 1, 0);
		if (start != null) {
			stack.push(start);
			flood(mask, Stitch.IMAGE1, stack);
		}

		start = getStart(mask, false, 0, mask[0].length);
		if (start == null)
			start = getStart(mask, true, mask[0].length - 1, mask.length);
//		start = new Position(0, mask[0].length - 1);
		if (start != null) {
			stack.push(start);
			flood(mask, Stitch.IMAGE2, stack);
		}

		// System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream(path + outputfile)), true));
		// helper.printArray(mask);
	}

	/**
	 * Flood using a given stack. Will pop the top of the stack
	 * and apply the flood pseudo-code specified in the assignment to it.
	 * This is done in a while loop.
	 *
	 * @param 	mask
	 * 			The mask to be used.
	 * @param 	fill
	 * 			The fill to be used.
	 * @param 	stack
	 * 			The stack to be used.
	 */
	private void flood(Stitch[][] mask, Stitch fill, Stack<Position> stack) {
		while (!stack.isEmpty()) {
			Position current = stack.pop();
			if (mask[current.getX()][current.getY()] != Stitch.EMPTY)
				continue;
			mask[current.getX()][current.getY()] = fill;
			for (Position neighbor: getNeighborsFlood(current))
				stack.push(neighbor);
		}
	}


	/**
	 * Get the start of the given mask.
	 *
	 * @param 	mask
	 * 			The mask to be used.
	 * @param 	jStatic
	 * 			true if the j iteration does not change, false if the i
	 * 			iteration doesn't change.
	 * @param 	length
	 * 			The length over which needs to be iterated.
	 */
	private Position getStart(Stitch[][] mask, boolean jStasis, int stasis, int length) {
		for (int i = 0; i < length; i++) {
			if ( (jStasis) && (mask[i][stasis] == Stitch.EMPTY) )
				return new Position(i, stasis);
			else if ( (!jStasis) && (mask[stasis][i] == Stitch.EMPTY) )
				return new Position(stasis, i);
		}
		return null;
	}


	/**
	 * Get the neighbors of a given position as specified in the floodfill algorithm.
	 *
	 * @param 	position
	 * 			The position of which the neighbors need to be returned.
	 */
	private List<Position> getNeighborsFlood(Position position) {
		List<Position> result = new ArrayList<Position>();
		Position newPosition = new Position(position.getX() - 1, position.getY());
		if (isInRange(newPosition))
			result.add(newPosition);
		newPosition = new Position(position.getX() + 1, position.getY());
		if (isInRange(newPosition))
			result.add(newPosition);
		newPosition = new Position(position.getX(), position.getY() - 1);
		if (isInRange(newPosition))
			result.add(newPosition);
		newPosition = new Position(position.getX(), position.getY() + 1);
		if (isInRange(newPosition))
			result.add(newPosition);
		return result;
	}



	/**
	 * Return the mask to stitch two images together. The seam runs from the upper
	 * left to the lower right corner, where in general the rightmost part comes from
	 * the second image (but remember that the seam can be complex, see the spiral example
	 * in the assignment). A pixel in the mask is Stitch.IMAGE1 on the places where
	 * image1 should be used, and Stitch.IMAGE2 where image2 should be used. On the seam
	 * record a value of Stitch.SEAM.
	 *
	 * ImageCompositor will only call this method (not seam and floodfill) to
	 * stitch two images.
	 *
	 * image1 and image2 are both non-null and have equal dimensions.
	 * @throws FileNotFoundException
	 */
	public Stitch[][] stitch(int[][] image1, int[][] image2) throws IOException {
		// use seam and floodfill to implement this method
		setLengths(image1.length, image1[0].length);
		Stitch[][] result = new Stitch[lengthI][lengthJ];
		for (int i = 0; i < lengthI; i++)	// Create an empty stitch array.
			for (int j = 0; j < lengthJ; j++)
				result[i][j] = Stitch.EMPTY;

		for (Position seamPosition: seam(image1, image2)) // Create the seam.
			result[seamPosition.getX()][seamPosition.getY()] = Stitch.SEAM;

		floodfill(result);	// Floodfill the stitch array.
		return result;
	}

}
