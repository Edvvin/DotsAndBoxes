package etf.dotsandboxes.me170117d;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class GameState {
	enum Turn{
		BLUE, RED, GRAY
	}
	private Turn currentTurn;
	private GameScreen gameScreen;
	private ArrayList<Move> moveList;
	private boolean[][][] lines;
	private Turn centers[][];
	private GameConfig gc;
	private int progressCnt = 0;
	private boolean gameOver = false;
	public GameState(GameConfig gc) {
		this.gc = gc;
		currentTurn = gc.firstTurn;
		moveList = new ArrayList<Move>();
		lines = new boolean[2][gc.rowCnt+1][gc.colCnt+1];
		centers = new Turn[gc.rowCnt][gc.colCnt];

	}

	public boolean gameOver() {
		return gameOver;
	}

	public Turn getCurrentTurn() {
		return currentTurn;
	}

	public void apply(Move move) {
		moveList.add(move);
		lines[move.getOrt()][move.getRow()][move.getCol()] = true;
		gameScreen.setLine(move);
		gameScreen.addMove(move);
		boolean changeTurn = true;

		if(move.getOrt() == Move.HORIZONTAL) {
			if(move.getRow()>0) {
				int row = getCenterRow(move.getOrt(), move.getRow(), move.getCol(), true);
				int col = getCenterCol(move.getOrt(), move.getRow(), move.getCol(), true);
				if(linesAround(row, col) == 4) {
					centers[row][col]=currentTurn;
					gameScreen.setCenter(row, col, currentTurn);
					changeTurn = false;
					progressCnt++;
					if(progressCnt == gc.colCnt*gc.rowCnt)
						gameOver = true;
				}
			}
			if(move.getRow()<gc.rowCnt) {
				int row = getCenterRow(move.getOrt(), move.getRow(), move.getCol(), false);
				int col = getCenterCol(move.getOrt(), move.getRow(), move.getCol(), false);
				if(linesAround(row, col) == 4) {
					centers[row][col]=currentTurn;
					gameScreen.setCenter(row, col, currentTurn);
					changeTurn = false;
					progressCnt++;
					if(progressCnt == gc.colCnt*gc.rowCnt)
						gameOver = true;
				}
			}
		}
		else {
			if(move.getCol()>0) {
				int row = getCenterRow(move.getOrt(), move.getRow(), move.getCol(), true);
				int col = getCenterCol(move.getOrt(), move.getRow(), move.getCol(), true);
				if(linesAround(row, col) == 4) {
					centers[row][col]=currentTurn;
					gameScreen.setCenter(row, col, currentTurn);
					changeTurn = false;
					progressCnt++;
					if(progressCnt == gc.colCnt*gc.rowCnt)
						gameOver = true;
				}
			}
			if(move.getCol()<gc.colCnt) {
				int row = getCenterRow(move.getOrt(), move.getRow(), move.getCol(), false);
				int col = getCenterCol(move.getOrt(), move.getRow(), move.getCol(), false);
				if(linesAround(row, col) == 4) {
					
					centers[row][col]=currentTurn;
					gameScreen.setCenter(row, col, currentTurn);
					changeTurn = false;
					progressCnt++;
					if(progressCnt == gc.colCnt*gc.rowCnt)
						gameOver = true;
				}
			}
		}

		if(changeTurn) {
			currentTurn = (currentTurn==Turn.BLUE)?Turn.RED:Turn.BLUE;
			gameScreen.setTurn(currentTurn);
		}
	}
	
	public static int getCenterRow(int ort, int i, int j, boolean upOrLeft) {
		if(ort == Move.HORIZONTAL && upOrLeft)
			return i-1;
		return i;
	}
	public static int getCenterCol(int ort, int i, int j, boolean upOrLeft) {
		if(ort == Move.VERTICAL && upOrLeft)
			return j-1;
		return j;
	}
	
	public int linesAround(int i, int j) {
		return (lines[Move.HORIZONTAL][i][j]?1:0) +
		(lines[Move.HORIZONTAL][i+1][j]?1:0) +
		(lines[Move.VERTICAL][i][j]?1:0) +
		(lines[Move.VERTICAL][i][j+1]?1:0);
	}

	public void setGameScreen(GameScreen gameScreen) {
		this.gameScreen = gameScreen;
		
	}
	public void loadFile() {
		if(!gc.loadFile.isEmpty()) {
			File file = new File(gc.loadFile);
			try {
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String temp[] = reader.readLine().split(" ");
				gc.rowCnt = Integer.parseInt(temp[0]);
				gc.colCnt = Integer.parseInt(temp[1]);
				while(true) {
					String m = reader.readLine();
					if(m==null)
						break;
					int row = 0,col = 0;
					if(Character.isLetter(m.charAt(0))) {
						row = m.charAt(0) - 'A';
						col = Integer.parseInt(""+m.charAt(1));
						apply(new Move(Move.VERTICAL,row,col));
					}else if(Character.isLetter(m.charAt(1))) {
						row = Integer.parseInt(""+m.charAt(0));
						col = m.charAt(1) - 'A';
						apply(new Move(Move.HORIZONTAL,row,col));
					}else {
						break;
					}
				}
				reader.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void saveGameState(String path) {
		File file = new File(path);
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			String head = "" + gc.rowCnt + " " + gc.colCnt;
			writer.write(head);
			writer.newLine();
			for(Move m : moveList) {
				writer.write(m.toString());
				if(moveList.get(moveList.size()-1) != m)
					writer.newLine();
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
