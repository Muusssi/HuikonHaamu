package gameObj;

import gameCore.GameWorld;
import gameExceptions.IllegalGameCodeException;

public class Room extends GameThing {

	public Room(GameWorld gw, String name, String description, String code)
			throws IllegalGameCodeException {
		super(gw, name, description, code);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void giveDescription(String description) {
		// TODO Auto-generated method stub

	}

	@Override
	public void giveNewCode(String code) throws IllegalGameCodeException {
		// TODO Auto-generated method stub

	}

	@Override
	public void changeCode(String newCode) throws IllegalGameCodeException {
		throw new IllegalGameCodeException(newCode);
	}

	@Override
	public String genSaveline() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static Room loadLine(String saveline) {
		// TODO implement
		return null;
	}

}
