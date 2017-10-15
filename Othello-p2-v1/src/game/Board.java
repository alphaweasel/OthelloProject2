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
	private int scoreW = 2;
	private int scoreB = 2;

	// move lists and current lengths
	int possMoveB[][] = new int[2][64];
	int movesB;
	int possMoveW[][] = new int[2][64];
	int movesW;

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

	public int move(int xCoord, int yCoord, String colorIn) {
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (!(i == 0 && j == 0)) {
					if (isValid(xCoord, yCoord, colorIn, i, j)) {
						System.out.println(i + ", " + j + " is valid");
						if (gb[xCoord][yCoord].isPlaced() == false)
							gb[xCoord][yCoord].placeDisk(colorIn);
						flip(xCoord, yCoord, colorIn, i, j);
					}
				}
			} // end of j loop
		} // end of i loop
			// returns 1 if a valid move was made, otherwise it returns 0
		if (gb[xCoord][yCoord].isPlaced())
			return 1;
		else
			return 0;
	} // end method move

	private boolean isValid(int x, int y, String colorIn, int dx, int dy) {
		int currentX = x + dx;
		int currentY = y + dy;

		if (onBoard(currentX, currentY) && gb[currentX][currentY].isPlaced())
			if (gb[currentX][currentY].getColor().equals(colorIn))
				return false;

		for (; onBoard(currentX, currentY); currentX += dx, currentY += dy) {
			if (gb[currentX][currentY].getColor() == colorIn) {
				return true;
			}
			if (gb[currentX][currentY].isPlaced() == false)
				return false;
		}
		return false;
	} // end method isValid

	private void flip(int x, int y, String colorIn, int dx, int dy) {
		for (x += dx, y += dy; onBoard(x, y); x += dx, y += dy)
			if (gb[x][y].getColor() == colorIn)
				break;
			else {
				gb[x][y].switchColor();
				if (colorIn.equals(B)) {
					scoreB++;
					scoreW--;
				} else {
					scoreW++;
					scoreB--;
				}
			}
	} // end method flip

	public boolean isMove(String colorIn) {
		boolean flag = false;

		if (colorIn.equals(W))
			movesW = 0;
		else
			movesB = 0;

		for (int r = 0; r < ROW; r++) {
			for (int c = 0; c < COLUMN; c++) {
				for (int i = -1; i < 2; i++) {
					for (int j = -1; j < 2; j++) {
						if (!(i == 0 && j == 0)) {
							if (isValid(c, r, colorIn, i, j)) {
								flag = true;
								if (colorIn.equals(B)) {
									possMoveB[0][movesB] = r;
									possMoveB[1][movesB] = c;
									movesB++;
								} else {
									possMoveW[0][movesW] = r;
									possMoveW[1][movesW] = c;
									movesW++;
								}
							}
						}
					}
				}
			}
		}
		return flag;
	} // end method isMove

	public boolean endCheck() {
		if (isMove(B) && isMove(W))
			return false;
		else
			return true;
	} // end method endCheck

	public String getWinner() {
		if (scoreB > scoreW)
			return B + " wins with a score of " + scoreB + "! " + W + " had a score of " + scoreW + ".";
		else
			return W + " wins with a score of " + scoreW + "! " + B + " had a score of " + scoreB + ".";
	} // end method getWinner

	public void play(String s) {
		// variables
		char gameType;
		int modifier;

		// start the specified game mode with the modifier as the parameter
		gameType = s.charAt(0);
		modifier = Integer.parseInt(s.substring(1));

		if (gameType == 'p')
			if (modifier == 1)
				singlePlayer();
			else
				multiPlayer();
		else
			simulator(modifier);
	}

	public void simulator(int loops) {
		for (int i = 0; i < loops; i++) {

		}
	}

	public void singlePlayer() {

	}

	public void multiPlayer() {

		// objects
		Player p1 = new Player(B);
		Player p2 = new Player(W);
		Board b = new Board();

		// main game loop
		while (!endCheck()) {
			turn(p1, b);
			turn(p2, b);
		}
	} // end method multiPlayer

	public void turn(Player p, Board b) {
		// variables
		int moved = 0;

		b.draw(p.getColor());

		if (b.isMove(p.getColor())) {
			do {
				p.getMove();
				moved = b.move(p.getXCoord(), p.getYCoord(), p.getColor());

				if (moved == 1)
					break;
				else
					System.out.println("Invalid move location.");
			} while (true);
		}

	} // end method turn

} // end class Board