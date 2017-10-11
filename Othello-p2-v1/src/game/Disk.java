package game;

/**
 * Contains the object Disk and its constructors and methods
 * 
 * Disk consists of 2 main fields: color and placed. Every other field is
 * derived from color or placed.
 * 
 * Project 2; Version 1
 * 
 * @author Garrison Henkle
 * @since 1.8
 * 
 */
public class Disk {

	// constants
	private final String B = "Black";
	private final String W = "White";

	// variables
	private String color;
	private boolean placed;

	/**
	 * Constructor with no parameters
	 * 
	 * Creates a disk with no color that is not placed
	 */
	public Disk() {
		color = null;
		placed = false;
	} // end constructor Disk()

	/**
	 * Constructor with a string parameter
	 * 
	 * Creates a disk with the color, colorIn, that is placed
	 * 
	 * @param colorIn
	 *            color that is assigned to the disk when it is created
	 */
	public Disk(String colorIn) {
		color = colorIn;
		placed = true;
	} // end constructor Disk(string)

	/**
	 * Used to make a blank, unplaced disk into a a placed disk of a certain color
	 * 
	 * @param colorIn
	 *            the color that the disk is set to
	 */
	public void placeDisk(String colorIn) {
		if (colorIn == B || colorIn == W) {
			color = colorIn;
			placed = true;
		}
	}// end method placeDisk

	/**
	 * Used to switch the color of a disk from black to white or vice versa
	 */
	public void switchColor() {
		if (placed == true) {
			if (color == W)
				color = B;
			else
				color = W;
		}
	}// end method switchColor

	/**
	 * Used to get the color of a disk
	 * 
	 * @return returns the color of the disk as a string
	 */
	public String getColor() {
		if (placed == true)
			return color;
		else
			return null;
	}/// end method getColor

	/**
	 * Gives the character representation of a disk's color
	 * 
	 * 'W' represents "White", 'B' represents "Black", and ' ' represents null a
	 * Useful for representing the color of the disks in the board class
	 * 
	 * @see Board#clear()
	 * 
	 * @return returns the character representing the color of a disk
	 */
	public char getColorChar() {
		if (color == B)
			return 'B';
		else if (color == W)
			return 'W';
		else
			return ' ';
	}// end method getColorChar

	/**
	 * Checks to see if a disk is placed
	 * 
	 * @return returns the character 'Y' if the disk is placed and 'N' if the disk
	 *         is not placed
	 */
	public boolean isPlaced() {
		return placed;
	}// end method isPlaced
}// end class Disk