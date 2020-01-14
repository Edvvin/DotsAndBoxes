package etf.dotsandboxes.me170117d;

public class Game extends Thread {
	GameConfig gameConfig;
	private GameScreen gameScreen;
	
	public Game(GameConfig gc) {
		this.gameConfig = gc;
		start();
	}
	
	@Override
	public void run() {
		gameScreen = new GameScreen(gameConfig, this);
	}
}
