package etf.dotsandboxes.me170117d;

import java.awt.*;
import java.awt.event.*;

import javax.swing.BoxLayout;

public class GameScreen extends Frame{
	private GameConfig gc;
	private Game game;
	private HumanPlayer humanPlayer;
	Label turnLabel;
	List turnHistory;
	Button nextTurnBtn, finishBtn;
	GameBoard gameBoard;
	public GameScreen(GameConfig gc, Game game) {
		this.gc = gc;
		this.game = game;
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
		
		if(gc.firstTurn == GameState.Turn.BLUE) {
			turnLabel = new Label("Blue");
			turnLabel.setForeground(Color.BLUE);
		}
		else {
			turnLabel = new Label("Red");
			turnLabel.setForeground(Color.RED);
		}
		turnLabel.setFont(new Font("Lucida",Font.PLAIN,20));
		topPanel.add(turnLabel);
		
		turnHistory = new List();
		movesPanel.add(turnHistory);
		
		nextTurnBtn = new Button("Next Turn");
		finishBtn = new Button("Finish");
		
		bottomPanel.add(nextTurnBtn, BorderLayout.WEST);
		bottomPanel.add(finishBtn, BorderLayout.EAST);
		// Action Listeners
		
		//Next Turn
		nextTurnBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				synchronized(game) {
					game.notify();
				}
				
			}
		});
		
		// Close window
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				dispose();
				}
			});
		setSize(5*gc.colCnt*GameBoard.FIELD_SIZE + 200,5*gc.rowCnt*GameBoard.FIELD_SIZE + 200);
		setVisible(true);
	}
	
	public synchronized Player getPlayer() {
		if(humanPlayer==null)
			humanPlayer = new HumanPlayer();
		return humanPlayer;
	}
	
	public synchronized void setLine(Move move) {
		gameBoard.setLine(move);
	}
	
	public synchronized void resetLine(Move move) {
		gameBoard.resetLine(move);
	}
	
	public synchronized void setCenter(int i, int j, GameState.Turn turn) {
		gameBoard.setCenter(i, j, turn);
	}
	
	public synchronized void resetCenter(int i, int j) {
		gameBoard.resetCenter(i, j);
	}
	
	public synchronized void addMove(Move move) {
		turnHistory.add(move.toString());
	}
	
}

class HumanPlayer extends Player{

	@Override
	public Move getMove(GameState gameState) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
