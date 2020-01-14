package etf.dotsandboxes.me170117d;

import java.awt.*;

public class GameBoard extends Panel {
	public static final int HORIZONTAL = 0,VERTICAL = 1;
	public static final int FIELD_SIZE = 15;
	GameConfig gc;
	class Corner extends Panel{
		public Corner(int row, int column) {
			setBackground(Color.BLACK);
			setPreferredSize(new Dimension(FIELD_SIZE,FIELD_SIZE));
		}
	}
	class Center extends Panel{
		public Center(int row, int column) {
			setBackground(Color.BLUE);
			setPreferredSize(new Dimension(4*FIELD_SIZE,4*FIELD_SIZE));
		}
	}
	class Line extends Button{
		public Line( int ort, int row, int column) {
			setBackground(Color.GREEN);
			if(ort==HORIZONTAL) {
				setPreferredSize(new Dimension(4*FIELD_SIZE,4));
			}else {
				setPreferredSize(new Dimension(4,4*FIELD_SIZE));
			}
		}
	}
	public GameBoard(GameConfig gc) {
		super(new GridBagLayout());
		this.gc = gc;
		Corner[][] corners = new Corner[gc.rowCnt+1][gc.colCnt+1];
		Center[][] centers = new Center[gc.rowCnt][gc.colCnt];
		Line[][][] lines = new Line[2][gc.rowCnt+1][gc.colCnt+1];
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		for(int i = 0; i < gc.rowCnt; i++) {
			for(int j = 0; j < gc.colCnt; j++) {
				corners[i][j] = new Corner(i,j);
				gbc.weighty=1;
				gbc.weightx=1;
				gbc.gridy=2*i;
				gbc.gridx=2*j;
				add(corners[i][j],gbc);
				
				centers[i][j] = new Center(i,j);
				gbc.weighty=4;
				gbc.weightx=4;
				gbc.gridy=2*i+1;
				gbc.gridx=2*j+1;
				add(centers[i][j],gbc);
				
				lines[HORIZONTAL][i][j] = new Line(HORIZONTAL, i,j);
				gbc.weighty=1;
				gbc.weightx=4;
				gbc.gridy=2*i;
				gbc.gridx=2*j+1;
				add(lines[HORIZONTAL][i][j],gbc);
				
				lines[VERTICAL][i][j] = new Line(VERTICAL, i,j);
				gbc.weighty=4;
				gbc.weightx=1;
				gbc.gridy=2*i+1;
				gbc.gridx=2*j;
				add(lines[VERTICAL][i][j],gbc);
			}
		}
		for(int i = 0; i < gc.rowCnt; i++) {
			corners[i][gc.colCnt] = new Corner(i,gc.colCnt);
			gbc.weighty=1;
			gbc.weightx=1;
			gbc.gridy=2*i;
			gbc.gridx=2*gc.colCnt;
			add(corners[i][gc.colCnt],gbc);
			
			lines[VERTICAL][i][gc.colCnt] = new Line(VERTICAL, i, gc.colCnt);
			gbc.weighty=4;
			gbc.weightx=1;
			gbc.gridy=2*i+1;
			gbc.gridx=2*gc.colCnt;
			add(lines[VERTICAL][i][gc.colCnt],gbc);
		}
		for(int i = 0; i < gc.colCnt; i++) {
			corners[gc.rowCnt][i] = new Corner(gc.rowCnt, i);
			gbc.weighty=1;
			gbc.weightx=1;
			gbc.gridy=2*gc.rowCnt;
			gbc.gridx=2*i;
			add(corners[gc.rowCnt][i],gbc);
			
			lines[HORIZONTAL][gc.rowCnt][i] = new Line(HORIZONTAL, gc.rowCnt,i);
			gbc.weighty=1;
			gbc.weightx=4;
			gbc.gridy=2*gc.rowCnt;
			gbc.gridx=2*i+1;
			add(lines[HORIZONTAL][gc.rowCnt][i],gbc);
		}
		corners[gc.rowCnt][gc.colCnt] = new Corner(gc.rowCnt, gc.colCnt);
		gbc.weighty=1;
		gbc.weightx=1;
		gbc.gridy=2*gc.rowCnt;
		gbc.gridx=2*gc.colCnt;
		add(corners[gc.rowCnt][gc.colCnt],gbc);
	}
}
