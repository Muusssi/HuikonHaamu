package gameObj;

import gameCore.GameWorld;
import gameExceptions.IllegalGameCodeException;

public class Item extends GameThing {

	public Item(GameWorld gw, String name, String description, String code)
			throws IllegalGameCodeException {
		super(gw, name, description, code);
		// TODO Auto-generated constructor stub
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
	public String getCodePrefix() {
		return "i";
	}

	@Override
	public String getSaveline() {
		// TODO Auto-generated method stub
		return null;
	}

}
