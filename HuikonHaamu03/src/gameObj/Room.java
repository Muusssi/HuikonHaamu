package gameObj;

import java.util.HashMap;

import gameCore.GameWorld;
import gameExceptions.IllegalGameCodeException;

public class Room extends GameThing {
	
	public String codePrefix = "r";
	
	protected Door[] doors;
	public HashMap<String,Item> roomItems = new HashMap<String,Item>();

	public Room(GameWorld gw, String name, String description, String code)
				throws IllegalGameCodeException {
		super(gw, name, description, code);
		if(gw.startingRoom == null) {
			this.setAsStartingRoom();
		}
		
		doors = new Door[28]; // 
		/* Doors:
		 * Seven possible positions on each side starting from left North
		 *    0  1  2  3  4  5  6 
		 * 27                     7
		 * 26                     8
		 * 25                     9
		 * 24                     10
		 * 23                     11
		 * 22                     12
		 * 21                     13
		 *    20 19 18 17 16 15 14
		 */
		
		this.gw.rooms.put(this.code, this);
	}
	
	public void addDoor(Door newDoor, int position) {
		if (doors[position] == null) {
			doors[position] = newDoor;
		}
		else {
			//TODO
		}
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
	public String getCodePrefix() {
		return "r";
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
