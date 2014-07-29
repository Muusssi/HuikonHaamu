package gameObj;

import gameCore.GameWorld;
import gameExceptions.CorruptedSaveLineException;
import gameExceptions.IllegalGameCodeException;
import gameExceptions.WorldMakingConflict;


public class Door extends GameThing {
	
	public Room firstRoom;
	private int firstRoomPosition;
	public Room secondRoom;
	private int secondRoomPosition;
	public boolean closed;

	/**Door is a passage from one room to another.*/
	public Door(GameWorld gw, String name, String description, String code, Room firstRoom, int firstRoomPosition,
				Room secondRoom, int secondRoomPosition, boolean closed)
						throws IllegalGameCodeException, WorldMakingConflict {
		super(gw, name, description, code);
		this.firstRoom = firstRoom;
		firstRoom.addDoor(this, firstRoomPosition);
		this.firstRoomPosition =  firstRoomPosition;
		this.secondRoom = secondRoom;
		secondRoom.addDoor(this, secondRoomPosition);
		this.secondRoomPosition =  secondRoomPosition;
		this.closed = closed;
		
	}
	
	public void open() {
		if (this.closed) {
			this.closed = false;
			gw.game.actionResponse(HC.DOOR_OPEN_CLOSED);
		}
		else {
			gw.game.actionResponse(HC.DOOR_OPEN_OPENED);
		}
	}
	
	public void close() {
		if (this.closed) {
			gw.game.actionResponse(HC.DOOR_CLOSE_CLOSED);
		}
		else {
			this.closed = true;
			gw.game.actionResponse(HC.DOOR_CLOSE_OPENED);
		}
	}
	
	public void go() {
		if (this.closed) {
			gw.game.actionResponse(HC.DOOR_GO_CLOSED);
		}
		else {
			if (gw.player.location == this.firstRoom) {
				gw.player.location = this.secondRoom;
			}
			else if (gw.player.location == this.secondRoom) {
				gw.player.location = this.firstRoom;
			}
			gw.game.actionResponse(HC.DOOR_GO_OPENED);
		}
	}

	@Override
	public void giveDescription(String description) {
		if (description == null) {
			this.description = HC.DOOR_DESCRIPTION;
		}
		else {
			this.description = description;
		}
	}



	@Override
	public void changeCode(String newCode) throws IllegalGameCodeException {
		// TODO Auto-generated method stub

	}
	
	@Override
	public String getCodePrefix() {
		return "d";
	}
	
	@Override
	public String getSaveline() {
		/* Door::<name>::<code>::<description>::
		 * <firstRoomCode>::<positionInFirstRoom>::
		 * <secondRoomCode>::<positionInSecondRoom>::
		 * <closed>*/
		return "Door::"+this.name+"::"+this.code+"::"+this.description+"::"
				+this.firstRoom.code+"::"+Integer.toString(this.firstRoomPosition)+"::"
				+this.secondRoom.code+"::"+Integer.toString(this.secondRoomPosition)+"::"+
				this.closed+"\r";
	}
	
	/** Function for recreating a Door object from the line of text used to save it.*/
	public static Door loadLine(String saveLine, GameWorld gw) throws CorruptedSaveLineException {
		if (saveLine.startsWith("Door::")) {
			String[] saveLineComp = saveLine.split("::");
			Door newDoor;
			try {
				newDoor = new Door(gw, saveLineComp[1], saveLineComp[3], saveLineComp[2],
						gw.rooms.get(saveLineComp[4]), Integer.parseInt(saveLineComp[5]),
						gw.rooms.get(saveLineComp[6]), Integer.parseInt(saveLineComp[7]),
						Boolean.getBoolean(saveLineComp[8]));
			} catch (Exception e) {
				e.printStackTrace();
				throw new CorruptedSaveLineException(saveLine);
			}
			return newDoor;
		}
		else {
			throw new CorruptedSaveLineException(saveLine);
		}
	}

}
