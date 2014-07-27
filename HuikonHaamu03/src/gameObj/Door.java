package gameObj;

import gameCore.GameWorld;
import gameExceptions.IllegalGameCodeException;

public class Door extends GameThing {
	
	public Room firstRoom;
	public Room secondRoom;
	public boolean closed;
	
	public Door(GameWorld gw, String name, String description, String code, Room firstRoom, Room secondRoom, boolean closed)
			throws IllegalGameCodeException {
		super(gw, name, description, code);
		this.firstRoom = firstRoom;
		this.secondRoom = secondRoom;
		this.closed = closed;
		this.codePrefix = "d";
		// TODO Auto-generated constructor stub
	}
	
	public void open() {
		//TODO
	}
	
	public void close() {
		//TODO
	}
	
	public void go() {
		if (this.closed) {
			gw.game.actionResponse(HC.DOOR_GO_CLOSED);
		}
		else {
			//TODO move player
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
	public void giveNewCode(String code) throws IllegalGameCodeException {
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
	
	public static Room loadLine(String saveline) {
		// TODO implement
		return null;
	}

}
