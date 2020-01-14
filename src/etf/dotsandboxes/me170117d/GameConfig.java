package etf.dotsandboxes.me170117d;

public class GameConfig {
	public enum PlayerType{
		HUMAN, EASY, MEDIUM, HARD
	}
	int rowCnt;
	int colCnt;
	PlayerType bluePlayerType, redPlayerType;
	int maxTreeDepth;
	GameState.Turn firstTurn;
	String loadFile = "";
}
