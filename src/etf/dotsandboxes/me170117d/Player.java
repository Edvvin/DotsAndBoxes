package etf.dotsandboxes.me170117d;

public abstract class Player {
	protected GameState.Turn myTurn;
	public abstract Move getMove(GameState gameState);
	
	public final void setTurn(GameState.Turn myTurn) {
		this.myTurn = myTurn;
	}
}
