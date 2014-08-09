package gameObj;

import gameCore.GameWorld;
import gameExceptions.IllegalGameCodeException;
import gameExceptions.WorldMakingConflict;

public class VoidRoom extends Room {

	public VoidRoom(GameWorld gw) throws IllegalGameCodeException, WorldMakingConflict {
		super(gw);
		this.xdim = 0;
		this.ydim = 0;
		this.gw.voidRoom = this;
		this.gw.roomMap.put(this.code, this);
		this.objectArray = null;
	}
//TODO needs to be implemented
}
