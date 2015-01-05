package GUIs;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import gameCore.Game;
import gameCore.GameWorld;
import gameExceptions.IllegalGameCodeException;
import gameObj.Player;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import quests.Mission;
import quests.Quest;

public class GameUI extends JFrame {
	
	GameWorld gw;
	
	JPanel gamePanel;
	JPanel chartPane;
	
	JTextArea textArea;
	
	
	private void setChartPanel() {
		chartPane = new JPanel();
		chartPane.setBounds(530, 30, 230, 250);
		chartPane.setLayout(null);
		chartPane.setVisible(true);
		gamePanel.add(chartPane);
	}
	
	public void updateGame() {
		//this.updateRoomList();
		//this.updateObjectList(editedRoom);
		//this.updateVoidList();
		gw.checkActiveMissions();
		RoomChart.setRoomChart(chartPane, gw.player.location, this);
	}

	public GameUI(GameWorld gameWorld) {
		gw = gameWorld;
		
		
		setTitle("Huikon Haamu - "+gameWorld.name);
		setSize(1000,600);
		setLocationRelativeTo(null);

		gamePanel = new JPanel();
	    getContentPane().add(gamePanel);
	    gamePanel.setLayout(null);
	    
	   
	 	
	 	textArea = new JTextArea(5,20);
	 	JScrollPane scrollPane = new JScrollPane(textArea);
	 	scrollPane.setBounds(50, 300, 700, 200);
	 	textArea.setEditable(false);
	 	gamePanel.add(scrollPane);
	 	Game.textArea = textArea;
	 	
	 	// Setting up exit listener
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				MainMenu.menu.setVisible(true);
			}
		});
		
		
	 	setChartPanel();
	 	updateGame();
 		setVisible(true);
	}
	
	
	/*
	public static void main(String[] args) {
		try {
			GameWorld gameWorld = GameWorld.loadWorld("guitest.hhw");
			new Player(gameWorld, "Tomppa", "Pelaaja ite");
			Game game = new Game();
			game.startGame(gameWorld);
			new GameUI(gameWorld);
		} catch (IllegalGameCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	*/

}
