package gameObj;

import java.util.HashMap;

import gameCore.GameWorld;
import gameExceptions.CorruptedSaveLineException;
import gameExceptions.IllegalGameCodeException;

public class Player extends GameThing {
	
	public Room location;
	public HashMap<String,Item> inventory = new HashMap<String,Item>();
	
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
				// Player::<name>::<code>::<description>::
				// <xdim>::<ydim>
		return "Player::"+this.name+"::"+this.code+"::"+this.description+"::"+"\r";
	}
	
	/** Function for recreating a Player from the line of text used to save it. */
	public static Player loadLine(String saveLine, GameWorld gw) throws CorruptedSaveLineException {
		if (saveLine.startsWith("Player::")) {
			String[] saveLineComp = saveLine.split("::");
			Player newPlayer;
			try {
				newPlayer = new Player(gw, saveLineComp[1], saveLineComp[3]);
			} catch (Exception e) {
				e.printStackTrace();
				throw new CorruptedSaveLineException(saveLine);
			}
			return newPlayer;
		}
		else {
			throw new CorruptedSaveLineException(saveLine);
		}
	}
	
	
	/** Returns a HashMap of the GameThings that the player can interact with.*/
	public HashMap<String,GameThing> getAvailableGameThings() {
		HashMap<String,GameThing> interactables = new HashMap<String,GameThing>();
		interactables.putAll(this.inventory);
		interactables.putAll(this.location.objectMap);
		return interactables;
	}
	
}
