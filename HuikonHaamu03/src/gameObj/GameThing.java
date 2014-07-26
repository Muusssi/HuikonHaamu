package gameObj;

import gameExceptions.*;
import gameCore.GameWorld;

public abstract class GameThing {
	
	protected String name;
	protected String description;
	protected String code;
	protected GameWorld gw;
	
	public GameThing(GameWorld gw, String name, String description, String code) 
			throws IllegalGameCodeException {
		this.gw = gw;
		this.name = name;
		this.giveDescription(description);
		this.giveNewCode(code);
		
	}
	

	
	public abstract void giveDescription(String description);
	
	
	public abstract void giveNewCode(String code) throws IllegalGameCodeException;
	
	public abstract void changeCode(String newCode) throws IllegalGameCodeException;
	
	
	public abstract String genSaveline();
	
	
	/* The operations: */
	
	public void explore() {
		gw.game.actionResponse(this.description);
	}
	
	public void take() {
		gw.game.actionResponse(HC.GAMETHING_TAKE);
	}
	
	public void put(GameThing location) {
		gw.game.actionResponse(HC.GAMETHING_PUT);
	}
	
	public void open() {
		gw.game.actionResponse(HC.GAMETHING_OPEN);
	}
	
	public void close() {
		gw.game.actionResponse(HC.GAMETHING_CLOSE);
	}
	
	public void go() {
		gw.game.actionResponse(HC.GAMETHING_GO);
	}
	
	public void hit() {
		gw.game.actionResponse(HC.GAMETHING_HIT);
	}
	
}
