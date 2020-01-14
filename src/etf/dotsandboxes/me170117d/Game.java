package etf.dotsandboxes.me170117d;

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
		switch(gc.bluePlayerType) {
		case HUMAN:
			bluePlayer = gameScreen.getPlayer();
			break;
		case EASY:
			break;
		case MEDIUM:
			break;
		case HARD:
			break;
		}
		bluePlayer.setTurn(GameState.Turn.BLUE);
		switch(gc.redPlayerType) {
		case HUMAN:
			redPlayer = gameScreen.getPlayer();
			break;
		case EASY:
			break;
		case MEDIUM:
			break;
		case HARD:
			break;
		}
		redPlayer.setTurn(GameState.Turn.RED);
		try {
			while(!gameState.gameOver()) {
				Move move;
				if(gameState.getCurrentTurn() == GameState.Turn.BLUE) {
					move = bluePlayer.getMove(gameState);
				}else {
					move = redPlayer.getMove(gameState);
				}
				if(stepByStep) {
					synchronized(this) {
						wait();
					}
				}
				gameState.apply(move);
			}
		}
		catch (InterruptedException e) {
		}
	}
}
