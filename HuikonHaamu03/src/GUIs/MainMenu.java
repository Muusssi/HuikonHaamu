package GUIs;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import gameCore.Game;
import gameCore.GameWorld;
import gameExceptions.CorruptedSaveLineException;
import gameExceptions.IllegalGameCodeException;
import gameObj.HC;
import gameObj.Player;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import GUIs.WorldEditor.Load;

@SuppressWarnings("serial")
public class MainMenu extends JFrame {
	public static MainMenu menu;
	
	JPanel menuPanel;
	
	public MainMenu() {
		menu = this;
		setTitle("Huikon Haamu Game Engine - v"+Game.gameVersion);
		setSize(400,200);
		setLocationRelativeTo(null);
		
		menuPanel = new JPanel();
		
		JButton playButton = new JButton("Play");
		menuPanel.add(playButton);
		playButton.addActionListener(new Play());
		playButton.setToolTipText("Start playing a game.");
		
		JButton editorButton = new JButton("Editor");
		menuPanel.add(editorButton);
		editorButton.addActionListener(new Editor());
		editorButton.setToolTipText("Open the editor.");
		
		JButton settingsButton = new JButton("Settings");
		//menuPanel.add(settingsButton);
		settingsButton.addActionListener(new Editor()); //TODO
		settingsButton.setToolTipText("Change settings like language...");
		
		// Setting up exit listener
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		add(menuPanel);
		setVisible(true);
	}
	
	class Play implements ActionListener {
		String saveName;
		String path;
		GameWorld gameWorld;
		public void actionPerformed(ActionEvent e) {
			
			setVisible(false);
			JFileChooser fileChooser = new JFileChooser();
			int rVal = fileChooser.showOpenDialog(MainMenu.this);
			if (rVal == JFileChooser.APPROVE_OPTION) {
				saveName = fileChooser.getSelectedFile().getName();
				path = fileChooser.getSelectedFile().getAbsolutePath();
				

				try {
					gameWorld = GameWorld.loadWorld(path);
					new Player(gameWorld, "Tomppa", "Pelaaja ite"); // TODO player fixed?
					Game game = new Game();
					game.startGame(gameWorld);
					new GameUI(gameWorld);
					
				} catch (CorruptedSaveLineException e1) {
					if (e1.notHHfile) {
						JOptionPane.showMessageDialog(null, "Error: '"+saveName+"' is not a HuikonHaamu file.");
					}
					else if (e1.corruptedLine != null) {
						JOptionPane.showMessageDialog(null, "Error: '"+saveName+"' is somehow corrupted and can not be opened." +
															"Error occured on line: "+e1.corruptedLine);
					}
					else {
						JOptionPane.showMessageDialog(null, "Un unexpected error while loading file '"+saveName+"'.");
					}
				} catch (IllegalGameCodeException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
			else {
				setVisible(true);
			}
			
		}
	}
	
	class Editor implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setVisible(false);
			new WorldEditor("eng");
		}
	}
	
	public static void main(String[] args) {
		new MainMenu();
	}
}
