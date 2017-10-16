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
	// Maximum amount of moves possible, as some will overlap (8 directions, so 8*64
	// = 512)
	private final int MAXPOSS = 512;

	// variables
	private String turn;
	private int moveNum = 1;
	private int scoreW = 2;
	private int scoreB = 2;

	// move lists and current lengths
	int possMoveB[][] = new int[2][MAXPOSS];
	int movesB;
	int possMoveW[][] = new int[2][MAXPOSS];
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
		//if (moveNum == 20)
			//Integer.parseInt("pasta");
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

	/**
	 * 
	 * @param xCoord
	 * @param yCoord
	 * @return
	 */
	public boolean isEmpty(int xCoord, int yCoord) {
		if (gb[xCoord][yCoord].isPlaced())
			return false;
		else
			return true;
	} // end method isEmpty

	/**
	 * 
	 * @param xCoord
	 * @param yCoord
	 * @return
	 */
	public boolean onBoard(int xCoord, int yCoord) {
		if ((0 <= xCoord && xCoord <= 7) && (0 <= yCoord && yCoord <= 7))
			return true;
		else
			return false;
	} // end method offBoard

	/**
	 * 
	 * @param xCoord
	 * @param yCoord
	 * @param colorIn
	 * @return
	 */
	public int move(int xCoord, int yCoord, String colorIn) {
		int flag = 0;

		if (gb[xCoord][yCoord].isPlaced())
			return 0;

		if (moveNum == 8) {
			@SuppressWarnings("unused")
			int pasta = 0;
		}

		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (!(i == 0 && j == 0)) {
					if (isValid(xCoord, yCoord, colorIn, i, j)) {
						flip(xCoord, yCoord, colorIn, i, j);
						flag = 1;
					}
				}
			} // end of j loop
		} // end of i loop
			// returns 1 if a valid move was made, otherwise it returns 0
		if (flag == 1) {
			gb[xCoord][yCoord].placeDisk(colorIn);
			return 1;
		} else
			return 0;
	} // end method move

	private boolean isValid1(int x, int y, String colorIn, int dx, int dy) {

		// current x (cX) and current y (cY)
		int cX = x + dx;
		int cY = y + dy;

		// checks if the location is on the board
		if (onBoard(x, y) == false)
			return false;
		if (gb[x][y].isPlaced() == true)
			return false;

		// checks to see if the color of the adjacent disk is the same as the disk being
		// placed and checks to see if there is a disk adjacent to the disk
		if (onBoard(cX, cY) == false)
			return false;
		if (gb[cX][cY].isPlaced() == false)
			return false;
		if (gb[cX][cY].getColor().equals(colorIn))
			return false;

		// increments the current x and current y values to determine if there is
		// another disk of the same color (so that a flip can be made) and checks to
		// make sure there is no empty spaces
		for (cX += dx, cY += dy; onBoard(cX, cY); cX += dx, cY += dy) {
			if (gb[cX][cY].isPlaced()) {
				if (gb[cX][cY].getColor().equals(colorIn)) {
					return true;
				}
			} else {
				return false;
			}
		}
		// if all the tests return false complete somehow without a return, return false
		return false;
	} // end method isValid1

	private boolean isValid(int x, int y, String colorIn, int dx, int dy) {
		// check to see if the spot is empty
		if (gb[x][y].isPlaced() == true)
			return false;

		// create current x and current y variables and increment by dx/dy
		int cX = x + dx;
		int cY = y + dy;

		// check to see if the cX and xY variables are on the board
		if (onBoard(cX, cY) == false)
			return false;

		//check to see if the new location is empty
		if(gb[cX][cY].isPlaced() == false)
			return false;
		
		// check to see if the new location is the correct (different) color
		if(gb[cX][cY].getColor().equals(colorIn))
			return false;

		//check in a direction
		for(cX += dx, cY += dy;onBoard(cX,cY);cX += dx, cY += dy) {
			if(gb[cX][cY].isPlaced() == false)
				return false;
			if(gb[cX][cY].getColor().equals(colorIn))
				return true;
		}
		
		return false;
	} // end method isValid

	// checks along a specific direction (given by dx and dy) for flips. Flips any
	// disk of the other color until it hits a disk of the same color, then the loop
	// is broken
	private void flip(int x, int y, String colorIn, int dx, int dy) {
		for (x += dx, y += dy; onBoard(x, y); x += dx, y += dy) {
			if (gb[x][y].getColor().equals(colorIn))
				break;
			else
				gb[x][y].switchColor();
		}
	}

	/**
	 * 
	 * @param colorIn
	 * @param mode
	 * @return
	 */
	public boolean isMove(String colorIn) {
		boolean flag = false;
		possMoveB = new int[2][MAXPOSS];
		possMoveW = new int[2][MAXPOSS];

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
									possMoveB[0][movesB] = c;
									possMoveB[1][movesB] = r;
									movesB++;
								} else {
									possMoveW[0][movesW] = c;
									possMoveW[1][movesW] = r;
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

	/**
	 * 
	 * @param b
	 * @return
	 */
	public boolean isEnd(Board b) {
		if ((!b.isMove(W) && !b.isMove(B)))
			return true;
		else
			return false;
	} // end method endCheck

	/**
	 * 
	 */
	public void updateScore() {
		scoreB = 0;
		scoreW = 0;
		for (int r = 0; r < ROW; r++) {
			for (int c = 0; c < COLUMN; c++) {
				if (gb[r][c].isPlaced()) {
					if (gb[r][c].getColor().equals(B))
						scoreB++;
					if (gb[r][c].getColor().equals(W))
						scoreW++;
				}
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	public String getWinner() {
		if (scoreB > scoreW)
			return B + " wins with a score of " + scoreB + "! " + W + " had a score of " + scoreW + ".";
		else if (scoreB == scoreW)
			return "Tie.  Both players ended with a score of " + scoreB;
		else
			return W + " wins with a score of " + scoreW + "! " + B + " had a score of " + scoreB + ".";
	} // end method getWinner

	/**
	 * 
	 * @param s
	 */
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
	} // end method play

	private int turn(Player player, Board board) {
		// variables
		int moved = 0;
		String color = player.getColor();

		// draw the board
		board.draw(color);

		// if there is possible moves, ask for a move. Otherwise skip.
		if (board.isMove(color)) {
			int isAMove = 0;
			do {
				// get a move and try to move
				player.getMove();
				moved = board.move(player.getXCoord(), player.getYCoord(), color);

				// if the move was successful, break the loop. Otherwise continue the loop
				if (moved == 1)
					isAMove = 1;
				else
					System.out.println("Invalid move location.");
			} while (isAMove == 0);
		} else {
			System.out.println(color + " skips. No possible moves.");
		}

		return moved;
	}

	private int turnB(Bot bot, Board board) {
		// variables
		int moved = 0;
		String color = bot.getColor();

		if (board.isMove(color)) {
			// load moves and randomly select one
			bot.loadPossibleMoves(board, color);
			bot.getMove();

			// move
			board.move(bot.getXCoord(), bot.getYCoord(), color);

			moved = 1;
		} else {
			System.out.println(color + " skips. No possible moves.");
		}

		return moved;
	}

	private void simulator(int simLoops) {
		// objects
		Bot b1;
		Bot b2;
		Board b;

		// run simLoops game(s) between the two bots b1 and b2
		for (int i = 0; i < simLoops; i++) {
			// reset the objects for every game
			b1 = new Bot(B);
			b2 = new Bot(W);
			b = new Board();
			b.draw(B);
			while (true) {
				b.isMove(B);
				if (!b.isMove(B) && !b.isMove(W))
					break;
				b.turnB(b1, b);
				b.draw(W);
				b.turnB(b2, b);
				b.draw(B);
				b.updateScore();
			}
			System.out.println((i + 1) + "," + (scoreB - scoreW));
			b.getWinner();
		}
	}

	private void singlePlayer() {

	}

	private void multiPlayer() {

	}

	/**
	 * 
	 * @param color
	 * @return
	 */
	public int[][] getPossMoves(String color) {
		if (color.equals(B))
			return possMoveB;
		else
			return possMoveW;
	} // end method getPossMoves

	/**
	 * 
	 * @param color
	 * @return
	 */
	public int getNumMoves(String color) {
		if (color.equals(B))
			return movesB;
		else
			return movesW;
	} // end method getNumMoves

} // end class Board