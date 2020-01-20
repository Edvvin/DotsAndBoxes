package etf.dotsandboxes.me170117d;

import java.awt.GridBagConstraints;
import java.util.ArrayList;

enum Turn{
	MAX, MIN
}

public class MediumPlayer extends Player {

	private static class MinMaxReturn{
		double score;
		Move move;
	}
	private int maxDepth;
	
	
	public MediumPlayer(int maxDepth) {
		this.maxDepth = maxDepth;
	}
	
	
	@Override
	public Move getMove(GameState gameState) {
		Move m = findFillable(gameState);
		if(m != null)
			return m;
		GameState localState = gameState.clone();
		return minMax(localState, maxDepth, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY).move;
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
		boolean maxPlayer = myTurn == gs.getCurrentTurn();
		
		if(gs.gameOver() || depth == 0) {
			MinMaxReturn ret = new MinMaxReturn();
			if(myTurn == GameState.Turn.BLUE)
				ret.score = gs.getBlueCenters() - gs.getRedCenters();
			else
				ret.score = gs.getRedCenters() - gs.getBlueCenters();
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
					GameState newgs = gs.clone();
					newgs.apply(m);
					MinMaxReturn curr = minMax(newgs, depth-1, alpha, beta);
					if(maxPlayer) {
						if(curr.score > ret.score) {
							ret.score = curr.score;
							ret.move = m;
							if(ret.score >= beta) {
								return ret;
							}
							alpha = (alpha >= ret.score? alpha : ret.score);
						}
					}else {
						if(curr.score < ret.score) {
							ret.score = curr.score;
							ret.move = m;
							if(ret.score <= alpha) {
								return ret;
							}
							beta = (beta <= ret.score? beta : ret.score);
						}
					}
					
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
					GameState newgs = gs.clone();
					newgs.apply(m);
					MinMaxReturn curr = minMax(newgs, depth-1, alpha, beta);
					if(maxPlayer) {
						if(curr.score > ret.score) {
							ret.score = curr.score;
							ret.move = m;
							if(ret.score >= beta) {
								return ret;
							}
							alpha = (alpha >= ret.score? alpha : ret.score);
						}
					}else {
						if(curr.score < ret.score) {
							ret.score = curr.score;
							ret.move = m;
							if(ret.score <= alpha) {
								return ret;
							}
							beta = (beta <= ret.score? beta : ret.score);
						}
					}
				}
			}
		}
		
		return ret;
	}
	


}
