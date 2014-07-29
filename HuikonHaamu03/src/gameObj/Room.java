package gameObj;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import gameCore.GameWorld;
import gameExceptions.CorruptedSaveLineException;
import gameExceptions.IllegalGameCodeException;
import gameExceptions.WorldMakingConflict;

public class Room extends GameThing {
	
	protected Door[] doors;
	public HashMap<String,Door> doorMap = new HashMap<String,Door>();
	public HashMap<String,GameThing> roomItems = new HashMap<String,GameThing>();

	public Room(GameWorld gw, String name, String description, String code)
				throws IllegalGameCodeException {
		super(gw, name, description, code);
		roomItems.put(this.code, this);
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
	
	/**Tries to add a Door to the given position.*/
	public void addDoor(Door newDoor, int position) throws WorldMakingConflict {
		if (doors[position] == null) {
			doors[position] = newDoor;
			doorMap.put(newDoor.code, newDoor);
		}
		else {
			throw new WorldMakingConflict("Passage position taken");
		}
	}
	
	/**Sets the room as the GameWorlds starting room */
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
		// Room::<name>::<code>::<description>
		return "Room::"+this.name+"::"+this.code+"::"+this.description+"\r";
	}
	
	/** Function for recreating a Room object from the line of text used to save it.*/
	public static Room loadLine(String saveLine, GameWorld gw) throws CorruptedSaveLineException {
		if (saveLine.startsWith("Room::")) {
			String[] saveLineComp = saveLine.split("::");
			Room newRoom;
			try {
				newRoom = new Room(gw, saveLineComp[1], saveLineComp[3], saveLineComp[2]);
			} catch (IllegalGameCodeException e) {
				e.printStackTrace();
				throw new CorruptedSaveLineException(saveLine);
				
			}
			return newRoom;
		}
		else {
			throw new CorruptedSaveLineException(saveLine);
		}
	}
	
	/**Returns the iterator for the GameObjects in the room.
	 * Currently: excluding doors*/
	public Iterator<GameThing> getRoomThings() {
		Collection<GameThing> collection = roomItems.values();
		return collection.iterator();
	}
	
	/**Method for cmdLineUIs. Prints the doors of the room according to directions and
	 * lists all GameThings in the room.*/
	public void printAvailableGameThings() {
		int doorIndex = 0;
		while (doorIndex < 28) {
			//Directions
			if (doorIndex == 0) {
				System.out.print("North: ");
			}
			else if (doorIndex == 7) {
				System.out.println();
				System.out.print("East: ");
			}
			else if (doorIndex == 14) {
				System.out.println();
				System.out.print("South: ");
			}
			else if (doorIndex == 21) {
				System.out.println();
				System.out.print("West: ");
			}
			//Doors
			if (this.doors[doorIndex] != null) {
				System.out.print(this.doors[doorIndex].code +":" +this.doors[doorIndex].name+", " );
			}
			doorIndex++;
		}
		System.out.println();
		
		//Other things
		System.out.print("   Items:\r");
		Iterator<GameThing> itr = this.getRoomThings();
		GameThing currentThing;
		while (itr.hasNext()) {
			currentThing = itr.next();
			System.out.print(currentThing.code+":"+currentThing.name+", ");
		}
		System.out.println();
	}
	
}
