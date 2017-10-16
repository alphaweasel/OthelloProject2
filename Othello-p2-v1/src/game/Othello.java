package game;

public class Othello {
	/**
	 * Main method of the class Othello
	 * 
	 * Prompts the user to input a gamemode then starts that gamemode
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		// create a board and a player to select the game mode
		Board b = new Board();
		Player p = new Player();

		// getMode returns the game mode and the game starts that mode
		b.play(p.getMode());
	}
}
