package etf.dotsandboxes.me170117d;

import java.awt.*;
import java.awt.event.*;

public class Launcher extends Frame {
	public static final int minDimCnt = 2, maxDimCnt = 10;
	
	public Launcher() {
		super("Dots And Boxes Launcher");
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				dispose();
				}
			});
		
		Panel playerSelectPanel = new Panel();
		Panel blueSelectPanel = new Panel(new GridLayout());
		Panel redSelectPanel = new Panel(new GridLayout());
		
		
		
		
		
		add(playerSelectPanel, BorderLayout.CENTER);
		playerSelectPanel.add(blueSelectPanel, BorderLayout.WEST);
		playerSelectPanel.add(redSelectPanel, BorderLayout.EAST);
		
		
		
		
		
		
		
		Panel remainingOptions = new Panel(new FlowLayout());
		add(remainingOptions, BorderLayout.SOUTH);
		Button saveFileSelect = new Button("Load Save File");
		
		Choice rowCntSelect = new Choice();
		Choice colCntSelect = new Choice();
		for(int i = minDimCnt; i <= maxDimCnt; i++) {
			rowCntSelect.add(""+i);
			colCntSelect.add(""+i);
		}
		Button startGame = new Button("Start");
		
		remainingOptions.add(saveFileSelect);
		remainingOptions.add(rowCntSelect);
		remainingOptions.add(new Label("x"));
		remainingOptions.add(colCntSelect);
		remainingOptions.add(startGame);
		
		setSize(320,600);
		setVisible(true);
	}
	
}
