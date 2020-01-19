package etf.dotsandboxes.me170117d;

import java.awt.Dialog;
import java.awt.Label;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import etf.dotsandboxes.me170117d.GameConfig.PlayerType;

public class Game extends Thread {

	private GameConfig gc;
	private GameScreen gameScreen;
	private Player bluePlayer, redPlayer;
	private GameState gameState;
	private boolean stepByStep;
	public Game(GameConfig gc) {
		this.gc = gc;
		stepByStep = (gc.bluePlayerType!= PlayerType.HUMAN) 
				&& (gc.redPlayerType!= PlayerType.HUMAN);
		
		ArrayList<Move> moves = null;
		if(!gc.loadFile.isEmpty())
			 moves = loadFile();
		gameState = new GameState(gc);
		gameScreen = new GameScreen(gc, this);
		gameState.setGameScreen(gameScreen);
		if(!gc.loadFile.isEmpty())
			gameState.setState(moves);
		start();
	}
	
	
	@Override
	public void run() {
		switch(gc.bluePlayerType) {
		case HUMAN:
			bluePlayer = gameScreen.getPlayer();
			break;
		case EASY:
			bluePlayer = new EasyPlayer();
			break;
		case MEDIUM:
			bluePlayer = new MediumPlayer();
			break;
		case HARD:
			bluePlayer = new HardPlayer();
			break;
		}
		bluePlayer.setTurn(GameState.Turn.BLUE);
		switch(gc.redPlayerType) {
		case HUMAN:
			redPlayer = gameScreen.getPlayer();
			break;
		case EASY:
			redPlayer = new EasyPlayer();
			break;
		case MEDIUM:
			redPlayer = new MediumPlayer();
			break;
		case HARD:
			redPlayer = new HardPlayer();
			break;
		}
		redPlayer.setTurn(GameState.Turn.RED);
		try {
			while(!gameState.gameOver() && !isInterrupted()) {
				Move move;
				if(gameState.getCurrentTurn() == GameState.Turn.BLUE) {
					move = bluePlayer.getMove(gameState);
				}else{
					move = redPlayer.getMove(gameState);
				}
				if(move == null) break;
				if(stepByStep) {
					synchronized(this) {
						wait();
					}
				}
				if(isInterrupted())
					break;
				gameState.apply(move);
			}
		}
		catch (InterruptedException e) {
		}
		
		
		// GAME OVER CODE GOES HERE
		
		gameScreen.dispose();
	}


	public void saveGame(String path) {
		gameState.saveGameState(path);
		
	}
	
	public ArrayList<Move> loadFile() {
		if(!gc.loadFile.isEmpty()) {
			File file = new File(gc.loadFile);
			try {
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String temp[] = reader.readLine().split(" ");
				gc.rowCnt = Integer.parseInt(temp[0]);
				gc.colCnt = Integer.parseInt(temp[1]);
				ArrayList<Move> moves = new ArrayList<Move>();
				while(true) {
					String m = reader.readLine();
					if(m==null)
						break;
					int row = 0,col = 0;
					if(Character.isLetter(m.charAt(0))) {
						row = m.charAt(0) - 'A';
						col = Integer.parseInt(""+m.charAt(1));
						moves.add(new Move(Move.VERTICAL,row,col));
					}else if(Character.isLetter(m.charAt(1))) {
						row = Integer.parseInt(""+m.charAt(0));
						col = m.charAt(1) - 'A';
						moves.add(new Move(Move.HORIZONTAL,row,col));
					}else {
						break;
					}
				}
				reader.close();
				return moves;
				
			} catch (IOException e) {
				System.err.print("No file found");
			}
		}
		return null;
	}
	
}
