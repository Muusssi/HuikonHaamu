package gameCore;

import javax.swing.JTextArea;

import gameObj.HC;

public class Game {
	
	public static boolean debug = true;
	public static String gameVersion = "0.3.1";
	public static Game game;
	public static boolean guiUsed = true;
	public static JTextArea textArea = null;

	public Game() {
		Game.game = this;
	}
	
	public void actionResponse(String response) {
		System.out.println(response);
		textArea.append(response+ "\n");
		
	}
	
	public void questInfo(String guestInfo) {
		System.out.println(guestInfo);
	}
	
	public void startGame(GameWorld gameWorld) {
		gameWorld.game = this;
		HC.HCinit(gameWorld.language);
		gameWorld.player.location = gameWorld.startingRoom;
	}
}
