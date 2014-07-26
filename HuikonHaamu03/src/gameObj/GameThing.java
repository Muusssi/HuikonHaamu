package gameObj;

import gameExceptions.*;

public abstract class GameThing {
	
	private String name;
	private String description;
	private String code;
	
	public GameThing(String name, String description, String code) 
			throws IllegalGameCodeException {
		this.name = name;
		this.giveDescription(description);
		this.giveNewCode(code);
	}
	
	public String name() {
		return this.name;
	}
	public String code() {
		return this.code;
	}
	public String description() {
		return this.description;
	}
	
	public abstract void giveDescription(String description);
	
	public abstract void giveNewCode(String code) throws IllegalGameCodeException;
	
	public void changeCode(String newCode) throws IllegalGameCodeException {
		
	}
	
	
	public abstract String genSaveline();
	
	
	/* The operations: */
	
	public void explore() {
		
	}
	
	public void take() {
		
	}
	
	public void put(GameThing location) {
		
	}
	
	public void open() {
		
	}
	
	public void close() {
		
	}
	
	public void go() {
		
	}
	
	public void hit() {
		
	}
	
}
