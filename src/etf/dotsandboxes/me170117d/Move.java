package etf.dotsandboxes.me170117d;

public class Move {
	public static final int HORIZONTAL = 0, VERTICAL = 1;
	private int ort, row, col;
	public Move(int ort, int row, int col) {
		this.ort = ort;
		this.row = row;
		this.col = col;
	}
	int getOrt() {
		return ort;
	}
	public int getCol() {
		return col;
	}

	public int getRow() {
		return row;
	}
	
	@Override
	public String toString() {
		if(ort == HORIZONTAL) {
			return ("" + row) + ((int)'A' + col);
		}else {
			return ("" + ((int)'A' + row)) + col;
		}
	}
}
