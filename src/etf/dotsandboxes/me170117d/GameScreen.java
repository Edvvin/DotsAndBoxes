package etf.dotsandboxes.me170117d;

import java.awt.*;
import java.awt.event.*;

import javax.swing.BoxLayout;

import etf.dotsandboxes.me170117d.GameConfig.PlayerType;
import etf.dotsandboxes.me170117d.GameState.Turn;

public class GameScreen extends Frame{
	private GameConfig gc;
	private Game game;
	private HumanPlayer humanPlayer;
	Label turnLabel;
	List turnHistory;
	Button nextTurnBtn, finishBtn;
	GameBoard gameBoard;
	FileDialog fileDialog;
	public GameScreen(GameConfig gc, Game game) {
		this.gc = gc;
		this.game = game;
		gameBoard = new GameBoard(gc, this);
		
		
		MenuBar bar = new MenuBar();
		setMenuBar(bar);
		Menu filesMenu = new Menu("Files");
		bar.add(filesMenu);
		
		MenuItem saveGame = new MenuItem("Save Game");
		filesMenu.add(saveGame);
		
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
		else if(gc.firstTurn == GameState.Turn.RED){
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
		fileDialog = new FileDialog(this, "Save", FileDialog.SAVE);
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
		
		//Finish
		finishBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				synchronized(game) {
					game.resetStepByStep();
					game.notify();
				}
			}
		});
		
		// Close window
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				game.interrupt();
			}
		});
		setSize(5*gc.colCnt*GameBoard.FIELD_SIZE + 200,5*gc.rowCnt*GameBoard.FIELD_SIZE + 200);
		setVisible(true);
		
		// Save progress
		saveGame.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				fileDialog.setVisible(true);
				game.saveGame(fileDialog.getDirectory() + fileDialog.getFile());
			}
		});
		
		
	}
	
	public synchronized HumanPlayer getPlayer() {
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
	
	public synchronized void addMove(Move move) {
		turnHistory.add(move.toString());
	}
	
	public synchronized void removeMove() {
		turnHistory.remove(turnHistory.getItemCount()-1);
	}
	
	public synchronized void setTurn(GameState.Turn turn) {
		if(turn == GameState.Turn.BLUE) {
			turnLabel.setText("Blue");
			turnLabel.setForeground(Color.BLUE);
			turnLabel.repaint();
		}else {
			turnLabel.setText("Red");
			turnLabel.setForeground(Color.RED);
			turnLabel.repaint();
		}
	}
}
