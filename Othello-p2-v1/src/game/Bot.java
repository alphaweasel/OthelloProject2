package game;

import java.lang.Math;

public class Bot extends Player {

	// variables
	private int moves[][];
	private int numMoves;
	private String color;
	private int xCoord = -1;
	private int yCoord = -1;

	/**
	 * Empty constructor for the Bot class
	 * 
	 * Creates an empty Bot object
	 */
	public Bot() {

	} // end constructor Bot()

	/**
	 * Constructor for the Bot class with a String parameter
	 * 
	 * creates an empty Bot object, then sets color to colorIn
	 * 
	 * @param colorIn
	 *            color of the bot
	 */
	public Bot(String colorIn) {
		color = colorIn;
	} // end constructor Bot(String)

	/**
	 * Loads the list of possible moves from the Board class
	 * 
	 * @param b
	 *            board of the current game
	 * @param colorIn
	 *            determines which set of moves to collect
	 */
	public void loadPossibleMoves(Board b, String colorIn) {
		moves = b.getPossMoves(colorIn);
		numMoves = b.getNumMoves(colorIn);
	} // end method loadPossibleMoves

	/**
	 * Returns a randomly selected move for the bot
	 * 
	 * Chooses randomly from all possible moves
	 */
	public void getMove() {
		int randomMove = (int) (Math.random() * numMoves);

		xCoord = moves[0][randomMove];
		yCoord = moves[1][randomMove];
	} // end method getMoves

	/**
	 * Returns the color of the bot
	 */
	public String getColor() {
		return color;
	} // end method getColor

	/**
	 * Returns the x coordinate of the bot's move
	 */
	public int getXCoord() {
		return xCoord;
	} // end method getXCoord

	/**
	 * Returns the y coordinate of the bot's move
	 */
	public int getYCoord() {
		return yCoord;
	} // end method getYCoord
}
