package etf.dotsandboxes.me170117d;

import java.awt.*;
import java.awt.event.*;

import javax.swing.BoxLayout;

public class GameScreen extends Frame{
	private Game game;
	private GameConfig gc;
	Label turnLabel;
	List turnHistory;
	Button nextTurnBtn, finishBtn;
	GameBoard gameBoard;
	public GameScreen(GameConfig gc, Game game) {
		this.game = game;
		this.gc = gc;
		
		gameBoard = new GameBoard(gc);
		
		Panel centerPanel = new Panel();
		Panel movesPanel = new Panel(new GridLayout(0,1));
		add(centerPanel, BorderLayout.CENTER);
		add(movesPanel, BorderLayout.EAST);
		Panel bottomPanel = new Panel(new FlowLayout());
		Panel topPanel = new Panel();
		
		
		centerPanel.add(topPanel, BorderLayout.NORTH);
		centerPanel.add(gameBoard, BorderLayout.CENTER);
		centerPanel.add(bottomPanel, BorderLayout.SOUTH);
		
		turnLabel = new Label("Blue");
		turnLabel.setForeground(Color.BLUE);
		turnLabel.setFont(new Font("Lucida",Font.PLAIN,20));
		topPanel.add(turnLabel);
		
		turnHistory = new List();
		movesPanel.add(turnHistory);
		
		nextTurnBtn = new Button("Next Turn");
		finishBtn = new Button("Finish");
		
		bottomPanel.add(nextTurnBtn, BorderLayout.WEST);
		bottomPanel.add(finishBtn, BorderLayout.EAST);
		// Action Listeners
		
		// Close window
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				dispose();
				}
			});
		setSize(5*gc.colCnt*GameBoard.FIELD_SIZE + 200,5*gc.rowCnt*GameBoard.FIELD_SIZE + 200);
		setVisible(true);
	}
}
