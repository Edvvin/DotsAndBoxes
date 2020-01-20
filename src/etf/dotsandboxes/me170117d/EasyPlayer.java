package etf.dotsandboxes.me170117d;

import java.util.ArrayList;
import java.util.Random;

public class EasyPlayer extends Player {
	private Random r = new Random();
	@Override
	public Move getMove(GameState gameState) {
		int rowCnt = gameState.getConfig().rowCnt, colCnt = gameState.getConfig().colCnt;
		ArrayList<Move> riskFree = new ArrayList<Move>();
		ArrayList<Move> free = new ArrayList<Move>();
		
		//HORIZONTAL
		for(int i = 0; i < rowCnt+1; i++) {
			for(int j = 0; j < colCnt; j++) {
				Move m = new Move(Move.HORIZONTAL,i,j);
				if(!gameState.getLine(Move.HORIZONTAL, i, j)) {
					if(riskFree.size() == 0)
						free.add(m);
				}
				else {
					continue;
				}
				boolean noRisk = true;
				if(i>0) {
					int row = gameState.getCenterRow(Move.HORIZONTAL, i, j, true);
					int col = gameState.getCenterCol(Move.HORIZONTAL, i, j, true);
					int lineCnt = gameState.linesAround(row, col);
					
					//remove here
					//if(lineCnt == 2) {
					//	noRisk = false;
					//}
					
					if(lineCnt == 3) {
						return m;
					}
				}
				if(i<rowCnt) {
					int row = gameState.getCenterRow(Move.HORIZONTAL, i, j, false);
					int col = gameState.getCenterCol(Move.HORIZONTAL, i, j, false);
					int lineCnt = gameState.linesAround(row, col);
					
					//remove here
					//if(lineCnt == 2) {
					//	noRisk = false;
					//}
					
					if(lineCnt == 3) {
						return m;
					}
				}
				if(noRisk) {
					riskFree.add(m);
				}
			}
		}
		
		//VERTICAL
		for(int i = 0; i < rowCnt; i++) {
			for(int j = 0; j < colCnt+1; j++) {
				Move m = new Move(Move.VERTICAL,i,j);
				if(!gameState.getLine(Move.VERTICAL, i, j)) {
					if(riskFree.size() == 0)
						free.add(m);
				}
				else {
					continue;
				}
				boolean noRisk = true;
				if(j>0) {
					int row = gameState.getCenterRow(Move.VERTICAL, i, j, true);
					int col = gameState.getCenterCol(Move.VERTICAL, i, j, true);
					int lineCnt = gameState.linesAround(row, col);
					
					//remove here
					//if(lineCnt == 2) {
					//	noRisk = false;
					//}
					
					if(lineCnt == 3) {
						return m;
					}
				}
				if(j<colCnt) {
					int row = gameState.getCenterRow(Move.VERTICAL, i, j, false);
					int col = gameState.getCenterCol(Move.VERTICAL, i, j, false);
					int lineCnt = gameState.linesAround(row, col);
					
					//remove here
					//if(lineCnt == 2) {
					//	noRisk = false;
					//}
					
					if(lineCnt == 3) {
						return m;
					}
				}
				if(noRisk) {
					riskFree.add(m);
				}
			}
		}
		
		if(riskFree.size() > 0) {
			int ind = r.nextInt(riskFree.size());
			return riskFree.get(ind);
		}
		int ind = r.nextInt(free.size());
		return free.get(ind);
	}

}
