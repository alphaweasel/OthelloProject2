package game;

import java.lang.Math;

public class Bot extends Player{
	
	//variables
	private int moves[][];
	private int numMoves;
	private String color;
	private int xCoord = -1;
	private int yCoord = -1;
	
	public Bot() {
		
	}
	
	public Bot(String colorIn) {
		color = colorIn;
	}
	
	public void loadPossibleMoves(Board b, String colorIn){
		moves = b.getPossMoves(colorIn);
		numMoves = b.getNumMoves(colorIn);
	}
	
	public void getMove() {
		int randomMove = (int)(Math.random()*numMoves);
		
		xCoord = moves[0][randomMove];
		yCoord = moves[1][randomMove];
	}
	
	public String getColor() {
		return color;
	}
	
	public int getXCoord() {
		return xCoord;
	}
	
	public int getYCoord(){
		return yCoord;
	}
}
