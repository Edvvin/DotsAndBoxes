package etf.dotsandboxes.me170117d;

import java.awt.Dialog;
import java.awt.Label;

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
		start();
	}
	
	
	@Override
	public void run() {
		gameState = new GameState(gc);
		gameScreen = new GameScreen(gc, this);
		gameState.setGameScreen(gameScreen);
		if(!gc.loadFile.isEmpty())
			gameState.loadFile();
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
}
