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
		// if (moveNum == 20)
		// Integer.parseInt("pasta");
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
	 * Checks to see if the location is empty
	 * 
	 * @param xCoord
	 *            X coordinate of the location
	 * @param yCoord
	 *            Y coordinate of the location
	 * @return true if the location is empty, false if the location has a disk
	 */
	public boolean isEmpty(int xCoord, int yCoord) {
		if (gb[xCoord][yCoord].isPlaced())
			return false;
		else
			return true;
	} // end method isEmpty

	/**
	 * Checks to see if a location is on the gameboard
	 * 
	 * @param xCoord
	 *            X coordinate of the location
	 * @param yCoord
	 *            Y coordinate of the location
	 * @return true if the location is on the board, false if the location is off
	 *         the board
	 */
	public boolean onBoard(int xCoord, int yCoord) {
		if ((0 <= xCoord && xCoord <= 7) && (0 <= yCoord && yCoord <= 7))
			return true;
		else
			return false;
	} // end method offBoard

	/**
	 * Attempts to make a move at the given location, then returns an integer
	 * depending on if the move was successful
	 * 
	 * @param xCoord
	 *            X coordinate of the location
	 * @param yCoord
	 *            Y coordinate of the location
	 * @param colorIn
	 *            color of the actor
	 * @return 1 when the move was successful and 0 if the move was not
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

	// checks to see if a move is valid for player colorIn starting at location
	// (x,y) and moving in direction dx,dy
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

		// check to see if the new location is empty
		if (gb[cX][cY].isPlaced() == false)
			return false;

		// check to see if the new location is the correct (different) color
		if (gb[cX][cY].getColor().equals(colorIn))
			return false;

		// check in a direction
		for (cX += dx, cY += dy; onBoard(cX, cY); cX += dx, cY += dy) {
			if (gb[cX][cY].isPlaced() == false)
				return false;
			if (gb[cX][cY].getColor().equals(colorIn))
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
	} // end method flip

	/**
	 * Checks to see if there is any valid moves for the player colorIn and records
	 * all valid moves
	 * 
	 * Valid moves for white are recorded in possMoveW[][], where possMoveW[0] is an
	 * x value and possMoveW[1] is a y value. The total amount of moves for white is
	 * given by movesW. Black has the same structure, but it instead uses
	 * possMoveB[][] and movesB
	 * 
	 * @param colorIn
	 *            color of the actor
	 * @return true if there is a possible move, false if there is no possible moves
	 */
	public boolean isMove(String colorIn) {
		boolean flag = false;
		possMoveB = new int[2][MAXPOSS];
		possMoveW = new int[2][MAXPOSS];

		// resets the move counter for color colorIn
		if (colorIn.equals(W))
			movesW = 0;
		else
			movesB = 0;

		// loops through all possible moves and checks to see if any are valid
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
	 * Checks to see if an end game condition has been met
	 * 
	 * The game end condition is both players must have no possible moves
	 * 
	 * @param b
	 *            board of the current game
	 * @return true if the game has ended, false if the game continues
	 */
	public boolean isEnd(Board b) {
		if ((!b.isMove(W) && !b.isMove(B)))
			return true;
		else
			return false;
	} // end method endCheck

	/**
	 * Updates the score of both players
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
	} // end method updateScore

	/**
	 * Determines the winner of the game
	 * 
	 * @param b
	 *            board of the current game
	 * @return a string indicating the winner of the game
	 */
	public String getWinner(Board b) {
		if (b.getScore(B) > b.getScore(W))
			return B + " wins with a score of " + b.getScore(B) + "! " + W + " had a score of " + b.getScore(W) + ".";
		else if (b.getScore(B) == b.getScore(W))
			return "Tie.  Both players ended with a score of " + b.getScore(B);
		else
			return W + " wins with a score of " + b.getScore(W) + "! " + B + " had a score of " + b.getScore(B) + ".";
	} // end method getWinner

	/**
	 * Plays Othello
	 * 
	 * Selects between the 3 gamemodes (singleplayer, multiplayer, simulator) based
	 * on the input
	 * 
	 * @param s
	 *            contains the gamemode information
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

	// completes a turn for a (human) player
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

	// completes a turn for the bot
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

		}

		return moved;
	}

	// simulator gamemode
	private void simulator(int simLoops) {
		// variables
		int[] spreads = new int[simLoops];

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
			while (true) {
				if (!b.isMove(B) && !b.isMove(W)) {
					break;
				}
				b.turnB(b1, b);
				b.turnB(b2, b);
				b.updateScore();
			}
			spreads[i] = b.getScore(B) - b.getScore(W);
		}
		processSpreadData(spreads);
	} // end method simulator

	/**
	 * Processes the data within the input array and finds the occurrence of each
	 * spread
	 * 
	 * @param spreads
	 *            array containing the spreads to process
	 */
	public void processSpreadData(int[] spreads) {
		int[] occur = new int[129];
		int count;

		// make an array of spread occurrences, where the index is the spread+64
		for (int i = -64; i < 65; i++) {
			count = 0;
			for (int spread : spreads) {
				if (spread == i)
					count++;
			}
			occur[(i + 64)] = count;
		}

		System.out.println("Spread\tOccurrences");

		int spreadNum;
		for (int i = 0; i < occur.length; i++) {
			spreadNum = i - 64;
			System.out.println(spreadNum + "\t" + occur[i]);
		}
	} // end method processSpreadData

	// singleplayer gamemode
	private void singlePlayer() {
		// objects
		Player player = new Player(B);
		Bot bot = new Bot(W);
		Board board = new Board();

		// run main game loop
		while (true) {
			if (!board.isMove(B) && !board.isMove(W)) {
				break;
			}
			board.turn(player, board);
			board.draw(bot.getColor());
			board.turnB(bot, board);
			board.updateScore();

		}
		getWinner(board);
	} // end method singlePlayer

	// multiplayer gamemode
	private void multiPlayer() {
		// objects
		Player p1 = new Player(B);
		Player p2 = new Player(W);
		Board board = new Board();

		// run main game loop
		while (true) {
			if (!board.isMove(B) && !board.isMove(W)) {
				break;
			}
			board.turn(p1, board);
			board.turn(p2, board);
			board.updateScore();

		}
		getWinner(board);
	} // end method multiPlayer

	/**
	 * Returns the array containing all possible moves for player color
	 * 
	 * @param color
	 *            determines which set of moves to return
	 * @return possMoveB if color is Black, possMoveW if color is White
	 */
	public int[][] getPossMoves(String color) {
		if (color.equals(B))
			return possMoveB;
		else
			return possMoveW;
	} // end method getPossMoves

	/**
	 * Returns the number of possible moves for player color
	 * 
	 * @param color
	 *            determines which total of moves to return
	 * @return movesB if color is Black, movesW if color is White
	 */
	public int getNumMoves(String color) {
		if (color.equals(B))
			return movesB;
		else
			return movesW;
	} // end method getNumMoves

	/**
	 * Returns the score of player color
	 * 
	 * @param color
	 *            determines which score to return
	 * @return scoreB if color is Black, scoreW if color is White
	 */
	public int getScore(String color) {
		if (color.equals(B))
			return scoreB;
		else
			return scoreW;
	} // end method getScore

} // end class Board