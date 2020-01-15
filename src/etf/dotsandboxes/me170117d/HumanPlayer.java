package etf.dotsandboxes.me170117d;

public class HumanPlayer extends Player{
	private Move move;
	
	public synchronized void setMove(int ort, int i, int j) {
		move = new Move(ort,i,j);
		notify();
	}
	@Override
	public synchronized Move getMove(GameState gameState) {
		try {
			wait();
		} catch (InterruptedException e) {
			move = null;
		}
		return move;
	}
}
