package gameObj;

import gameCore.GameWorld;
import gameExceptions.IllegalGameCodeException;

public class Room extends GameThing {
	
	protected Door[] doors;

	public Room(GameWorld gw, String name, String description, String code)
				throws IllegalGameCodeException {
		super(gw, name, description, code);
		if(gw.startingRoom == null) {
			this.setAsStartingRoom();
		}
		
		doors = new Door[32]; // Eight possible on each side starting from left North
		
		this.codePrefix = "r";
		this.gw.rooms.put(this.code, this);
	}
	
	public void setAsStartingRoom() {
		gw.startingRoom = this;
		if (gw.player != null) {
			gw.player.location = this;
		}
	}
	
	@Override
	public void giveDescription(String description) {
		if (description == null) {
			this.description = HC.ROOM_DESCRIPTION;
		}
		else {
			this.description = description;
		}
	}



	@Override
	public void changeCode(String newCode) throws IllegalGameCodeException {
		if (code == null || gw.gameThings.containsKey(newCode)) {
			throw new IllegalGameCodeException(newCode);
		}
		else {
			gw.gameThings.remove(this.code);
			gw.rooms.remove(this.code);
			this.code = newCode;
			gw.gameThings.put(newCode, this);
			gw.rooms.put(newCode, this);
		}
		
	}

	@Override
	public String getSaveline() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static Room loadLine(String saveline) {
		// TODO implement
		return null;
	}

}
