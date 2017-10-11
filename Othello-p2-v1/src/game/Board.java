package game;

/**
 * Creates the game board, stores the locations of the disks, draws the game
 * board to the screen, and enforces the rules of the game
 * 
 * Project 2; Version 1
 * 
 * @author Garrison Henkle
 * @since 1.8
 *
 */
public class Board {

	// constants
	private final int ROW = 8;
	private final int COLUMN = 8;
	private final String BORDER = "  ||===============================||";
	private final String B = "Black";
	private final String W = "White";

	// variables
	private String turn;
	private int moveNum = 1;
	private int xCurr;
	private int yCurr;

	// data structure to store the locations of the disks
	private Disk[][] gb = new Disk[ROW][COLUMN];

	/**
	 * Constructor with no parameters
	 * 
	 * Creates the board and populates it with all blank disks, then places the 4
	 * initial disks on the board
	 */
	public Board() {
		initialize();
	} // end of constructor Board()

	public static void main(String[] args) {
		Board b = new Board();
		b.draw(b.B);
	}

	/**
	 * Initializes the board
	 * 
	 * Fills the board with blank disks and places the four starting disks
	 */
	private void initialize() {
		// create a blank disk for every space on the board
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				gb[r][c] = new Disk();
			} // end of column for loop
		} // end of row for loop

		// place the 4 initial disks
		gb[3][4].placeDisk(B);
		gb[4][3].placeDisk(B);
		gb[3][3].placeDisk(W);
		gb[4][4].placeDisk(W);

	} // end of method initialize

	/**
	 * Draws the board to the screen
	 * 
	 * Uses the character representation of the disk color
	 * 
	 * @see Disk#getColorChar()
	 * 
	 * @param userTurn
	 *            Used to indicate which player's turn it is at the bottom of the
	 *            board
	 */
	public void draw(String userTurn) {

		// draw the column numbers
		System.out.println("     1   2   3   4   5   6   7   8   ");

		// prints upper border
		System.out.println(BORDER);

		// loops and prints out every row
		for (int r = 0; r < ROW; r++) {
			System.out.printf((r + 1) + " || %c | %c | %c | %c | %c | %c | %c | %c ||%n", gb[r][0].getColorChar(),
					gb[r][1].getColorChar(), gb[r][2].getColorChar(), gb[r][3].getColorChar(), gb[r][4].getColorChar(),
					gb[r][5].getColorChar(), gb[r][6].getColorChar(), gb[r][7].getColorChar());
			// prints a divider, except on the last loop
			if (r != ROW - 1)
				System.out.println("  ||-------------------------------||");
		} // end of row for loop

		// prints lower border
		System.out.println(BORDER);

		// set turn
		setTurn(userTurn);

		// Draw the move counter and turn indicator
		System.out.println("     " + turn + "\'s Turn  | Move: " + moveNum++);

	} // end of method draw

	/**
	 * Sets the turn variable
	 * 
	 * Checks to see if the input is black (B) or white (W)
	 * 
	 * @param userTurn
	 */
	private void setTurn(String userTurn) {
		if (userTurn == B)
			turn = B;
		else
			turn = W;
	} // end of method setTurn

	public boolean isEmpty(int xCoord, int yCoord) {
		if (gb[xCoord][yCoord].isPlaced())
			return false;
		else
			return true;
	} // end method isEmpty

	public boolean onBoard(int xCoord, int yCoord) {
		if (xCoord < 0 || yCoord < 0 || xCoord > 7 || yCoord > 7)
			return false;
		else
			return true;
	} // end method offBoard

	public void flipDisks(int xCoord, int yCoord, String colorIn) {
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (i != 0 && j != 0) {
					if (isValid(xCoord, yCoord, colorIn, i, j))
						flip(xCoord, yCoord, colorIn, xCurr, yCurr, i, j);
				}
			} // end of j loop
		} // end of i loop
	} // end method flipDisks

	private boolean isValid(int x, int y, String colorIn, int dx, int dy) {
		for (; onBoard(x, y); x += dx, y += dy) {
			if (gb[x][y].getColor() == colorIn) {
				xCurr = x;
				yCurr = y;
				return true;
			}
			if (gb[x][y].isPlaced() == false)
				return false;
		}
		return false;
	} // end method isValid

	private void flip(int x, int y, String colorIn, int cX, int cY, int dx, int dy) {
		for(;x<cX && y<cY;x+=dx, y+=dy) {
			gb[x][y].switchColor();
		}
	} // end method flip

} // end class Board