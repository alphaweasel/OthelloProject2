package game;

import java.util.Scanner;

/**
 * Contains all of the user interface for the game, such as input and player
 * actions
 * 
 * Input is split into getAction(), which allows for the user to enter
 * coordinates while allowing for the user to pass or quit, and move(), which
 * tests to see if a move is valid then places a disk and flips all legal pieces
 * 
 * Project 2; Version 1
 * 
 * @author Garrison Henkle
 * @since 1.8
 *
 */
public class Player {

	// constants
	private final String B = "Black";
	private final String W = "White";

	// variables
	private int rowIn = -1;
	private int colIn = -1;
	private int xCoord = -1;
	private int yCoord = -1;
	private String color;

	/**
	 * Empty constructor for the class Player
	 * 
	 * Creates an empty object for the class
	 */
	public Player() {

	} // end constructor Player()

	/**
	 * Constructor for the class Player with a String parameter
	 * 
	 * Creates a Player object and sets color to colorIn
	 * 
	 * @param colorIn
	 *            color of the Player
	 */
	public Player(String colorIn) {
		color = colorIn;
	} // end constructor Player(String)

	/**
	 * Prompts the player to make a move and validates the input
	 * 
	 * Sets the coordinates to numbers in [0,7]
	 */
	public void getMove() {
		// variables
		String move;

		// prints prompt
		System.out.println("Please enter a move in the form \"row,column\":");

		// loop for valid input
		do {
			move = getInput();

			// checks the input to see if a valid move was entered
			if (move.length() == 3) {

				// check column and set x if column is valid
				colIn = getInt(move.substring(0, 1));
				System.out.println("colIn is " + colIn);

				if (colIn != -1 && (1 <= colIn && colIn <= 8))
					xCoord = colIn - 1;
				else {
					System.out.println("Invalid column, please try again:");
					continue;
				}

				// check to make sure there is a comma
				if (move.charAt(1) != ',') {
					System.out.println("These are not coordinates, please try again:");
					continue;
				}

				// check row and set y if row is valid, then break the loop if y is set.
				rowIn = getInt(move.substring(2));
				System.out.println("rowIn is " + rowIn);

				if (rowIn != -1 && (1 <= rowIn && rowIn <= 8)) {
					yCoord = rowIn - 1;
					break;
				} else {
					System.out.println("Invalid row, please try again: ");
					continue;
				}
			} else {
				System.out.println("Invalid coordinates, please try again:");
			}
		} while (true);
	} // end method getMove

	/**
	 * Prompts the player to choose a gamemode
	 * 
	 * Possible choices are singleplayer, multiplayer, and simulation
	 * 
	 * Output has one letter to indicate simulator or player, then a modifier that
	 * sets the amount of players for the the letter 'p' and sets the amount of
	 * loops for the letter 's'
	 * 
	 * @return "p1", "p2", "sn" where n is a number > 0
	 */
	public String getMode() {
		// variables
		String gameMode = null;
		String mode;
		boolean loop = true;
		int players = 0;
		int simLoops = 0;

		// prints prompt
		System.out.println("Enter \"play\" to start a game or \"sim\" to start a simulated game between two bots:");

		// loop for valid input
		do {
			mode = getInput();

			// checks the input to see what mode was selected. If the input is not valid,
			// print invalid.
			if (mode.equals("play")) {

				System.out.println("Enter the amount of players (1 or 2): ");

				// loop for valid input
				do {
					mode = getInput();

					// if the input is "1" or "2", set the amount of players and exit the loop.
					// Otherwise, print invalid.
					if (getInt(mode) == 1 || getInt(mode) == 2) {
						players = getInt(mode);
						loop = false;
						break;
					} else {
						System.out.println("Invalid input, please enter either 1 or 2:");
					}
				} while (true);

			} else if (mode.equals("sim")) {
				System.out.println("Enter the amount of times to run the simulation: ");

				// loop for valid input
				do {
					mode = getInput();

					// if the input is an integer greater than 0, set the amount of players and exit
					// the loop. Otherwise, print invalid.
					if (getInt(mode) > 0) {
						simLoops = getInt(mode);
						loop = false;
						break;
					} else {
						System.out.println("Invalid input, please enter a number greater than 0:");
					}
				} while (true);
			} else {
				System.out.println("Invalid input, please try again:");
			}

		} while (loop);

		// sets the gameMode variable depending on what value was changed
		if (players != 0)
			gameMode = "p" + String.valueOf(players);
		else if (simLoops != 0)
			gameMode = "s" + String.valueOf(simLoops);

		return gameMode;
	} // end getMode

	/**
	 * Gets input from the user
	 * 
	 * @return in the input of the user as a String
	 */
	public String getInput() {
		// variables
		@SuppressWarnings("resource")
		Scanner userIn = new Scanner(System.in);
		String in = null;

		// loop until a valid output is received
		do {
			if (userIn.hasNextLine()) {
				in = userIn.nextLine();
				break;
			}
		} while (true);

		return in;
	} // end method getInput

	/**
	 * Parses integers from Strings
	 * 
	 * If the input is not a number or a negative number, the output will be -1. If
	 * the input is a positive, the output will be the number as an int
	 * 
	 * @param in
	 *            the String to parse
	 * @return out if out is > 0, -1 if out < 0
	 */
	public int getInt(String in) {
		int out;
		try {
			out = Integer.parseInt(in);
		} catch (NumberFormatException ex) {
			return -1;
		}
		if (out > 0)
			return out;
		else
			return -1;
	} // end method getInt

	/**
	 * Returns the x coordinate of the Player's move
	 * 
	 * @return xCoord
	 */
	public int getXCoord() {
		return xCoord;
	} // end method getXCoord

	/**
	 * Returns the y coordinate of the Player's move
	 * 
	 * @return yCoord
	 */
	public int getYCoord() {
		return yCoord;
	} // end method getYCoord

	/**
	 * Sets the color of the Player
	 * 
	 * @param s
	 *            color to set the Player
	 */
	public void setColor(String s) {
		if (s.equals(W) || s.equals(B))
			color = s;
	} // end method setColor

	/**
	 * Returns the color of the Player
	 * 
	 * @return color
	 */
	public String getColor() {
		return color;
	} // end method getColor
} // end class Player