package etf.dotsandboxes.me170117d;

import java.awt.*;
import java.awt.event.*;

public class GameOver extends Frame {
	private Game game;
	Button okBtn, saveBtn;
	private FileDialog fileDialog = new FileDialog(this, "Save", FileDialog.SAVE);;
	public GameOver(Game game){
		super();
		this.game = game;
		okBtn = new Button("Ok");
		saveBtn = new Button("Save");
		Panel btnPanel = new Panel(new FlowLayout());
		btnPanel.add(okBtn);
		btnPanel.add(saveBtn);
		Label announce = new Label("Blue: " + game.getGameState().getBlueCenters() + " Red: " + game.getGameState().getRedCenters());
		announce.setFont(new Font("Lucida",Font.PLAIN,20));
		add(announce, BorderLayout.CENTER);
		add(btnPanel, BorderLayout.SOUTH);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
		
		okBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		saveBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				fileDialog.setVisible(true);
				game.saveGame(fileDialog.getDirectory() + fileDialog.getFile());
			}
		});
		
		setSize(200,100);
		setVisible(true);
	}
}
