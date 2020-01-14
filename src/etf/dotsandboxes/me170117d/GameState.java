package etf.dotsandboxes.me170117d;

public class GameState {
	private Turn currentTurn;
	private GameScreen gameScreen;
	enum Turn{
		BLUE, RED
	}
	public GameState(GameConfig gc) {
		currentTurn = gc.firstTurn;
	}

	public boolean gameOver() {
		return true;
	}

	public Turn getCurrentTurn() {
		return null;
	}

	public void apply(Move move) {
		
		
	}

	public void setGameScreen(GameScreen gameScreen) {
		this.gameScreen = gameScreen;
		
	}
}
