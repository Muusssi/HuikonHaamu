package gameCore;

import gameObj.HC;

public class Game {

	public Game() {
		// TODO Auto-generated constructor stub
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
	}
}
