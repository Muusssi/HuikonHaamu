package gameObj;

import gameCore.GameWorld;
import gameExceptions.IllegalGameCodeException;
import gameExceptions.WorldMakingConflict;

public class GameObject extends GameThing {
	
	public Room location = null;
	public int position;
	
	public GameObject(GameWorld gw, String name, String description, String code, Room location, int position)
			throws IllegalGameCodeException, WorldMakingConflict {
		super(gw, name, description, code);
		if (location != null) {
			location.putObject(this, position);
			this.location = location;
		}
		else {
			gw.thingsInVoid.put(this.code, this);
		}
		this.position = position;
		
	}

	@Override
	public void giveDescription(String description) {
		this.description = HC.OBJECT_DESCRIPTION;
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
		return "o";
	}

}
