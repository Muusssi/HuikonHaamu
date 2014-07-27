package gameObj;

import gameCore.GameWorld;
import gameExceptions.IllegalGameCodeException;

public class Door extends GameThing {
	
	public String codePrefix = "d";
	
	public Room firstRoom;
	public Room secondRoom;
	public boolean closed;
	
	
	public Door(GameWorld gw, String name, String description, String code, Room firstRoom, int firsRoomPosition,
				Room secondRoom, int secondRoomPosition, boolean closed)
						throws IllegalGameCodeException {
		super(gw, name, description, code);
		this.firstRoom = firstRoom;
		firstRoom.addDoor(this, firsRoomPosition);
		this.secondRoom = secondRoom;
		secondRoom.addDoor(this, secondRoomPosition);
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
		// TODO Auto-generated method stub
		return null;
	}
	
	public static Room loadLine(String saveline) {
		// TODO implement
		return null;
	}

}
