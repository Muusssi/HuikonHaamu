package gameObj;

import gameCore.GameWorld;
import gameExceptions.IllegalGameCodeException;

public class Passage extends GameThing {
	
	public boolean closed;
	public Door door1;
	public Room room1;
	public Door door2;
	public Room room2;
	
	public Passage(GameWorld gw, String name, String description, String code, Door door1, Door door2, boolean closed)
			throws IllegalGameCodeException {
		super(gw, name, description, code);
		this.door1 = door1;
		this.door1.linked = true;
		this.door1.passage = this;
		this.room1 = door1.location;
		
		this.door2 = door2;
		this.door2.linked = true;
		this.door2.passage = this;
		this.room2 = door2.location;
		
		this.closed = closed;
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
	public String getSaveline() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCodePrefix() {
		return "p";
	}

}
