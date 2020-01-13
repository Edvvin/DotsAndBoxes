package etf.dotsandboxes.me170117d;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javafx.scene.control.CheckBox;

public class Launcher extends Frame {
	public static final int minDimCnt = 2, maxDimCnt = 10, defaultDimSelection = 3;
	public static final int minDepthCnt = 2, maxDepthCnt = 10, defaultDepthSelection = 4;
	
	public static final int defaultDepth = 5;
	
	Checkbox blueHuman, blueEasy, blueMed, blueHard, redHuman, redEasy, redMed, redHard;
	Choice blueDepthSelector, redDepthSelector, rowCntSelect, colCntSelect;
	Label blueDepthSelectorLabel, redDepthSelectorLabel;
	Button saveFileSelect, startGame;
	CheckboxGroup bluePlayerGroup, redPlayerGroup;
	
	public Launcher() {
		super("Dots And Boxes Launcher");
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				dispose();
				}
			});
		
		Panel playerSelectPanel = new Panel();
		Panel blueSelectPanel = new Panel(new GridLayout(0,1));
		Panel redSelectPanel = new Panel(new GridLayout(0,1));
		
		
		add(playerSelectPanel, BorderLayout.CENTER);
		playerSelectPanel.add(blueSelectPanel, BorderLayout.WEST);
		playerSelectPanel.add(redSelectPanel, BorderLayout.EAST);
		
		Label blueLabel = new Label("Blue");
		blueLabel.setFont(new Font("Lucida",Font.PLAIN,24));
		blueLabel.setForeground(Color.BLUE);
		Label redLabel = new Label("Red");
		redLabel.setFont(new Font("Lucida",Font.PLAIN,24));
		redLabel.setForeground(Color.RED);
		
		blueSelectPanel.add(blueLabel);
		redSelectPanel.add(redLabel);
		
		
		bluePlayerGroup = new CheckboxGroup();
		blueHuman = new Checkbox("Human",bluePlayerGroup,true);
		blueEasy = new Checkbox("Easy Bot",bluePlayerGroup,false);
		blueMed = new Checkbox("Medium Bot",bluePlayerGroup,false);
		blueHard = new Checkbox("Hard Bot",bluePlayerGroup,false);
		
		blueSelectPanel.add(blueHuman);
		blueSelectPanel.add(blueEasy);
		blueSelectPanel.add(blueMed);
		blueSelectPanel.add(blueHard);
		
		redPlayerGroup = new CheckboxGroup();
		redHuman = new Checkbox("Human",redPlayerGroup,true);
		redEasy = new Checkbox("Easy Bot",redPlayerGroup,false);
		redMed = new Checkbox("Medium Bot",redPlayerGroup,false);
		redHard = new Checkbox("Hard Bot",redPlayerGroup,false);
		
		redSelectPanel.add(redHuman);
		redSelectPanel.add(redEasy);
		redSelectPanel.add(redMed);
		redSelectPanel.add(redHard);
		
		
		blueDepthSelector = new Choice();
		redDepthSelector = new Choice();
		for(int i = minDepthCnt; i <= maxDepthCnt; i++) {
			blueDepthSelector.add(""+i);
			redDepthSelector.add(""+i);
		}
		blueDepthSelectorLabel = new Label("Max Depth:");
		redDepthSelectorLabel = new Label("Max Depth:");
		
		blueSelectPanel.add(blueDepthSelectorLabel);
		redSelectPanel.add(redDepthSelectorLabel);
		blueSelectPanel.add(blueDepthSelector);
		redSelectPanel.add(redDepthSelector);
		
		blueDepthSelector.select(defaultDepthSelection);
		redDepthSelector.select(defaultDepthSelection);
		
		Panel remainingOptions = new Panel(new FlowLayout());
		add(remainingOptions, BorderLayout.SOUTH);
		saveFileSelect = new Button("Load Save File");
		
		rowCntSelect = new Choice();
		colCntSelect = new Choice();
		for(int i = minDimCnt; i <= maxDimCnt; i++) {
			rowCntSelect.add(""+i);
			colCntSelect.add(""+i);
		}
		rowCntSelect.select(defaultDimSelection);
		colCntSelect.select(defaultDimSelection);
		startGame = new Button("Start");
		
		remainingOptions.add(saveFileSelect);
		remainingOptions.add(rowCntSelect);
		remainingOptions.add(new Label("x"));
		remainingOptions.add(colCntSelect);
		remainingOptions.add(startGame);
		
		// ACTION LISTENERS // 
		
		
		// Game Starts
		startGame.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});

		// Load a save point
		saveFileSelect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		setSize(320,340);
		setVisible(true);
	}
	
}


