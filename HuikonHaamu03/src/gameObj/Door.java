package gameObj;

import gameCore.GameWorld;
import gameExceptions.CorruptedSaveLineException;
import gameExceptions.IllegalGameCodeException;
import gameExceptions.WorldMakingConflict;


public class Door extends GameObject {
	
	public Passage passage = null;

	/**Door is a link to a passage from one room to another.*/
	public Door(GameWorld gw, String name, String description, String code,
				Room location, int position)
						throws IllegalGameCodeException, WorldMakingConflict {
		super(gw, name, description, code, location, position);
		gw.unlinkedDoorMap.put(this.code, this);
	}
	
	public void linkTo(Door otherDoor, boolean closed) throws WorldMakingConflict {
		if (this.passage != null || otherDoor.passage != null) {
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
		if (this.passage == null) {
			gw.game.actionResponse(HC.DOOR_NO_PASSAGE);
		}
		else if (this.passage.closed) {
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
	public String getCodePrefix() {
		return "d";
	}
	
	@Override
	public String getSaveline() { //TODO
		/* Door::<name>::<code>::<description>::
		 * <roomCode>::<position>::*/
		String roomString;
		if (this.location == null) {
			roomString = "null";
		}
		else {
			roomString = this.location.code;
		}
		return "Door::"+this.name+"::"+this.code+"::"+this.description+"::"+
				roomString+"::"+Integer.toString(this.position)+"::\r";

	}
	
	/** Function for recreating a Door object from the line of text used to save it.*/
	public static Door loadLine(String saveLine, GameWorld gw) throws CorruptedSaveLineException {
		if (saveLine.startsWith("Door::")) {
			String[] saveLineComp = saveLine.split("::");
			Door newDoor;
			try {
				if (saveLineComp[4].equals(null)) {
					newDoor = new Door(gw, saveLineComp[1], saveLineComp[3], saveLineComp[2],
							null, Integer.parseInt(saveLineComp[5]));
				}
				else {
					newDoor = new Door(gw, saveLineComp[1], saveLineComp[3], saveLineComp[2],
							gw.roomMap.get(saveLineComp[4]), Integer.parseInt(saveLineComp[5]));
				}
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

	@Override
	public String getEditorInfo() {
		if (this.passage == null) {
			return this.code+": "+this.name+" -unlinked Door";
		}
		else {
			if (this.passage.door1 == this) {
				return this.code+": "+this.name+" -Door linked to "
						+this.passage.door2.code;
			}
			else {
				return this.code+": "+this.name+" -Door linked to "
						+this.passage.door1.code;
			}
			
		}

	}
	
	@Override
	public void remove() {
		gw.gameThings.remove(this.code);
		gw.objectMap.remove(this.code);
		if (gw.unlinkedDoorMap.containsKey(this.code)) {
			gw.unlinkedDoorMap.remove(this.code);
		}
		if (this.location != null) {
			this.location.objectMap.remove(this.code);
			this.location.objectArray[this.position] = null;
		}
		else {
			gw.thingsInVoid.remove(this.code);
		}
	}
	
}
