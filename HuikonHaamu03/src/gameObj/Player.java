package gameObj;

import java.util.HashMap;

import gameCore.GameWorld;
import gameExceptions.IllegalGameCodeException;

public class Player extends GameThing {
	
	public Room location;
	public HashMap<String,InventoryItem> inventory = new HashMap<String,InventoryItem>();
	
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
	public String getCodePrefix() {
		return "player";
	}

	@Override
	public String getSaveline() {
		// TODO Auto-generated method stub
		return null;
	}
	/** Returns a HashMap of the GameThings that the player can interact with.*/
	public HashMap<String,GameThing> getAvailableGameThings() {
		HashMap<String,GameThing> interactables = new HashMap<String,GameThing>();
		interactables.putAll(this.inventory);
		interactables.putAll(this.location.roomItems);
		interactables.putAll(this.location.doorMap);
		return interactables;
	}
	
}
