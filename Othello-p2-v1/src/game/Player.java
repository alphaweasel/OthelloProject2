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
	String color;
	int rowIn;
	int colIn;
	int xCoord;
	int yCoord;
	String input;
	String gameMode;
	int simNumber;

	public Player(String colorIn) {
		color = colorIn;
	} // end constructor Player(String)

	public void setMode() {
		Scanner userIn = new Scanner(System.in);

		System.out.println("Select mode: ");
		System.out.println("Enter \"1p\" for one player, \"2p\" for two player, or a \n"
				+ "number n to run a simulated game between two bots n times");

		int loop = 1;
		String mode = null;

		while (loop == 1) {
			try {
				mode = userIn.nextLine();
			} catch (Exception ex) {
				System.out.println("Invalid, please try again:");
			} finally {
				userIn.close();
			}
			if (parseMode(mode) != 0)
				loop = 0;
		} // end while loop
		
		switch(parseMode(mode)) {
			case -1:
				gameMode = "1 Player";
				break;
			case -2:
				gameMode = "2 Player";
				break;
			default:
				gameMode = "Simulator";
				simNumber = parseMode(mode);
				break;
		} //end switch statement
		
	} // end method setMode

	public void getMove() {
		System.out.println("");
	}

	/**
	 * Parses the input stringIn and returns a selected mode as an integer
	 * 
	 * 0 indicates an invalid mode, negative numbers indicate the amount of players,
	 * and positive numbers indicate how many times a simulation will be run
	 * 
	 * @param stringIn
	 *            userInput that is parsed for mode information
	 * @return type int that indicates what mode was selected
	 */
	public int parseMode(String stringIn) {
		int type = 0;
		try {
			int hopefullyANumber = Integer.parseInt(stringIn);
			if (hopefullyANumber > 0)
				type = hopefullyANumber;
		} catch (NumberFormatException ex) {
			type = 0;
		}

		if (type == 0) {
			if (stringIn.equals("p1"))
				type = -1;
			else if (stringIn.equals("p2"))
				type = -2;
			else
				type = 0;
		}
		return type;
	}

}
