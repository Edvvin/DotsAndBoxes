package etf.dotsandboxes.me170117d;

import java.util.*;


public class HardPlayer extends Player {
	
	private static class MinMaxReturn{
		double score;
		Move move;
	}
	private double vcnt = 0, hcnt = 0;
	private int maxDepth;
	private Random r = new Random();
	private boolean tunnelPhase = false;
	public HardPlayer(int maxDepth) {
		this.maxDepth = maxDepth;
	}
	
	
	
	@Override
	public Move getMove(GameState gameState) {
		Move m = findFillable(gameState);
		if(m != null)
			return m;
		GameState localState = gameState.clone();
		Move myMove = minMax(localState, maxDepth, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY).move;
		if(myMove.getOrt() == Move.HORIZONTAL) {
			hcnt++;
		}else {
			vcnt++;
		}
		return myMove;
	}
	
	public static Move findFillable(GameState gameState) {
		int rowCnt = gameState.getConfig().rowCnt, colCnt = gameState.getConfig().colCnt;
		for(int i = 0; i < rowCnt+1; i++) {
			for(int j = 0; j < colCnt; j++) {
				Move m = new Move(Move.HORIZONTAL,i,j);
				if(gameState.getLine(Move.HORIZONTAL, i, j)) {
					continue;
				}
				if(i>0) {
					int row = gameState.getCenterRow(Move.HORIZONTAL, i, j, true);
					int col = gameState.getCenterCol(Move.HORIZONTAL, i, j, true);
					int lineCnt = gameState.linesAround(row, col);
					
					if(lineCnt == 3) {
						return m;
					}
				}
				if(i<rowCnt) {
					int row = gameState.getCenterRow(Move.HORIZONTAL, i, j, false);
					int col = gameState.getCenterCol(Move.HORIZONTAL, i, j, false);
					int lineCnt = gameState.linesAround(row, col);
					
					if(lineCnt == 3) {
						return m;
					}
				}
			}
		}
		
		for(int i = 0; i < rowCnt; i++) {
			for(int j = 0; j < colCnt+1; j++) {
				Move m = new Move(Move.VERTICAL,i,j);
				if(gameState.getLine(Move.VERTICAL, i, j)) {
					continue;
				}
				if(j>0) {
					int row = gameState.getCenterRow(Move.VERTICAL, i, j, true);
					int col = gameState.getCenterCol(Move.VERTICAL, i, j, true);
					int lineCnt = gameState.linesAround(row, col);
					
					if(lineCnt == 3) {
						return m;
					}
				}
				if(j<colCnt) {
					int row = gameState.getCenterRow(Move.VERTICAL, i, j, false);
					int col = gameState.getCenterCol(Move.VERTICAL, i, j, false);
					int lineCnt = gameState.linesAround(row, col);
					
					if(lineCnt == 3) {
						return m;
					}
				}
			}
		}
		return null;
	}
	
	public MinMaxReturn minMax(GameState gs, int depth, double alpha, double beta) {
		int moveCnt = 0;
		boolean maxPlayer = myTurn == gs.getCurrentTurn();
		if(maxPlayer) {
			do {
				Move m = findFillable(gs);
				if(m == null) {
					break;
				}
				gs.apply(m);
				moveCnt++;
			}while(true);
		}
		
		if(gs.gameOver() || depth == 0) {
			MinMaxReturn ret = new MinMaxReturn();
			if(myTurn == GameState.Turn.BLUE)
				ret.score = gs.getBlueCenters() - gs.getRedCenters();
			else
				ret.score = gs.getRedCenters() - gs.getBlueCenters();
			double tmp = Math.abs(vcnt - hcnt);
			ret.score += 0.5/(1+tmp);
			for(int i = 0; i < moveCnt; i++)
				gs.undo();
			return ret;
		}
		
		MinMaxReturn ret = new MinMaxReturn();
		
		if(maxPlayer) {
			ret.score = Double.NEGATIVE_INFINITY;
		}else {
			ret.score = Double.POSITIVE_INFINITY;
		}
		

		int rowCnt = gs.getConfig().rowCnt, colCnt = gs.getConfig().colCnt;
		//HORIZONTAL
		for(int pass = 0; pass < 2; pass++) {
			
			for(int i = 0; i < rowCnt+1; i++) {
				for(int j = 0; j < colCnt; j++) {
					Move m = new Move(Move.HORIZONTAL,i,j);
					if(gs.getLine(Move.HORIZONTAL, i, j)) {
						continue;
					}
					if(i>0) {
						int row = gs.getCenterRow(Move.HORIZONTAL, i, j, true);
						int col = gs.getCenterCol(Move.HORIZONTAL, i, j, true);
						int lineCnt = gs.linesAround(row, col);
						
						if(lineCnt == 2 && pass != 1) {
							continue;
						}
						
					}
					
					if(i<rowCnt) {
						int row = gs.getCenterRow(Move.HORIZONTAL, i, j, false);
						int col = gs.getCenterCol(Move.HORIZONTAL, i, j, false);
						int lineCnt = gs.linesAround(row, col);
						
						
						if(lineCnt == 2 && pass != 1) {
							continue;
						}
					}
					pass = 2;
					gs.apply(m);
					MinMaxReturn curr = minMax(gs, depth-1, alpha, beta);
					if(maxPlayer) {
						if(curr.score > ret.score || (curr.score == ret.score && r.nextInt(2) == 0)) {
							ret.score = curr.score;
							ret.move = m;
							if(ret.score >= beta) {
								gs.undo();
								for(int k = 0; k < moveCnt; k++)
									gs.undo();
								return ret;
							}
							alpha = (alpha >= ret.score? alpha : ret.score);
						}
					}else {
						if(curr.score < ret.score || (curr.score == ret.score && r.nextInt(2) == 0)) {
							ret.score = curr.score;
							ret.move = m;
							if(ret.score <= alpha) {
								gs.undo();
								for(int k = 0; k < moveCnt; k++)
									gs.undo();
								return ret;
							}
							beta = (beta <= ret.score? beta : ret.score);
						}
					}
					gs.undo();
					
				}
			}
			
			//VERTICAL
			for(int i = 0; i < rowCnt; i++) {
				for(int j = 0; j < colCnt+1; j++) {
					Move m = new Move(Move.VERTICAL,i,j);
					if(gs.getLine(Move.VERTICAL, i, j)) {
						continue;
					}
					if(j>0) {
						int row = gs.getCenterRow(Move.VERTICAL, i, j, true);
						int col = gs.getCenterCol(Move.VERTICAL, i, j, true);
						int lineCnt = gs.linesAround(row, col);
	
						if(lineCnt == 2 && pass != 1) {
							continue;
						}
					}
					if(j<colCnt) {
						int row = gs.getCenterRow(Move.VERTICAL, i, j, false);
						int col = gs.getCenterCol(Move.VERTICAL, i, j, false);
						int lineCnt = gs.linesAround(row, col);
						
						if(lineCnt == 2 && pass != 1) {
							continue;
						}
					}
					pass = 2;
					gs.apply(m);
					MinMaxReturn curr = minMax(gs, depth-1, alpha, beta);
					if(maxPlayer) {
						if(curr.score > ret.score || (curr.score == ret.score && r.nextInt(50) == 0)) {
							ret.score = curr.score;
							ret.move = m;
							if(ret.score >= beta) {
								gs.undo();
								for(int k = 0; k < moveCnt; k++)
									gs.undo();
								return ret;
							}
							alpha = (alpha >= ret.score? alpha : ret.score);
						}
					}else {
						if(curr.score < ret.score || (curr.score == ret.score && r.nextInt(50) == 0)) {
							ret.score = curr.score;
							ret.move = m;
							if(ret.score <= alpha) {
								gs.undo();
								for(int k = 0; k < moveCnt; k++)
									gs.undo();
								return ret;
							}
							beta = (beta <= ret.score? beta : ret.score);
						}
					}
					gs.undo();
				}
			}
		}
		
		return ret;
	}
	
}
