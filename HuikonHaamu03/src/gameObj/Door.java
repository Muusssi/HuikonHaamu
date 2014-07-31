package gameObj;

import gameCore.GameWorld;
import gameExceptions.CorruptedSaveLineException;
import gameExceptions.IllegalGameCodeException;
import gameExceptions.WorldMakingConflict;


public class Door extends GameObject {
	
	public Passage passage;
	public boolean linked = false;

	/**Door is a link to a passage from one room to another.*/
	public Door(GameWorld gw, String name, String description, String code,
				Room location, int position)
						throws IllegalGameCodeException, WorldMakingConflict {
		super(gw, name, description, code, location, position);
		
	}
	
	public void linkTo(Door otherDoor, boolean closed) throws WorldMakingConflict {
		if (this.linked || otherDoor.linked) {
			throw new WorldMakingConflict("Door already linked.");
		}
		else {
			try {
				new Passage(gw, "passage", null, null, this, otherDoor, closed);
			} catch (IllegalGameCodeException e) {
				e.printStackTrace();
				//This shouldn't happen...
			}
		}
	}
	
	public void open() {
		if (this.passage.closed) {
			this.passage.closed = false;
			gw.game.actionResponse(HC.DOOR_OPEN_CLOSED);
		}
		else {
			gw.game.actionResponse(HC.DOOR_OPEN_OPENED);
		}
	}
	
	public void close() {
		if (this.passage.closed) {
			gw.game.actionResponse(HC.DOOR_CLOSE_CLOSED);
		}
		else {
			this.passage.closed = true;
			gw.game.actionResponse(HC.DOOR_CLOSE_OPENED);
		}
	}
	
	public void go() {
		if (this.passage.closed) {
			gw.game.actionResponse(HC.DOOR_GO_CLOSED);
		}
		else {
			if (gw.player.location == this.passage.room1) {
				gw.player.location = this.passage.room2;
			}
			else if (gw.player.location == this.passage.room2) {
				gw.player.location = this.passage.room1;
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
	public String getSaveline() { //TODO
		/* Door::<name>::<code>::<description>::
		 * <firstRoomCode>::<positionInFirstRoom>::
		 * <secondRoomCode>::<positionInSecondRoom>::
		 * <closed>*/
		
		/*
		return "Door::"+this.name+"::"+this.code+"::"+this.description+"::"
				+this.firstRoom.code+"::"+Integer.toString(this.firstRoomPosition)+"::"
				+this.secondRoom.code+"::"+Integer.toString(this.secondRoomPosition)+"::"+
				this.closed+"\r";
		*/
		return null;
	}
	
	/** Function for recreating a Door object from the line of text used to save it.*/
	public static Door loadLine(String saveLine, GameWorld gw) throws CorruptedSaveLineException {
		if (saveLine.startsWith("Door::")) {
			String[] saveLineComp = saveLine.split("::");
			Door newDoor;
			try {
				//TODO
			} catch (Exception e) {
				e.printStackTrace();
				throw new CorruptedSaveLineException(saveLine);
			}
			return null;
		}
		else {
			throw new CorruptedSaveLineException(saveLine);
		}
	}

}
