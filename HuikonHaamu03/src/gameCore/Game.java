package gameCore;

import gameObj.HC;

public class Game {
	
	public static boolean debug = true;
	public static String gameVersion = "0.3.1";
	public static Game game;

	public Game() {
		Game.game = this;
	}
	
	public void actionResponse(String response) {
		System.out.println(response);
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
