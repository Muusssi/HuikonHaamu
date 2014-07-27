package gameObj;

import gameCore.GameWorld;
import gameExceptions.IllegalGameCodeException;

public class Player extends GameThing {
	
	public Room location;
	
	public Player(GameWorld gw, String name, String description)
			throws IllegalGameCodeException {
		super(gw, name, description, "p0");
		this.location = this.gw.startingRoom;
		gw.player = this;
	}

	@Override
	public void giveDescription(String description) {
		// TODO Auto-generated method stub

	}

	@Override
	public void changeCode(String newCode) throws IllegalGameCodeException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getSaveline() {
		// TODO Auto-generated method stub
		return null;
	}

}
