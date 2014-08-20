package gameObj;

import gameExceptions.*;
import gameCore.GameWorld;

public abstract class GameThing {
	
	protected String name;
	protected String description;
	protected String code;
	protected GameWorld gw;
	
	public String codePrefix;
	
	public GameThing(GameWorld gw, String name, String description, String code) 
			throws IllegalGameCodeException {
		this.gw = gw;
		this.name = name;
		if (description == null || description.equals("")) {
			description = null;
		}
		this.giveDescription(description);
		this.giveNewCode(code);
		this.gw.gameThings.put(this.code, this);
	}
	

	/** Sets the gameThings description to the given String. If null sets it as the standard
	 * description of the class.*/
	public abstract void giveDescription(String description);
	
	
	/** If code is null gives the gameThing new suitable gameCode. If code is given checks that the code
	 * isn't already in use if so throws IllegalGameCodeException.
	 * @throws IllegalGameCodeException */
	public void giveNewCode(String code) throws IllegalGameCodeException {
		if (code == null || code.equals("null")) { //New code
			int codeCount = 1;
			while (gw.gameThings.containsKey(this.getCodePrefix()+Integer.toString(codeCount))) {
				codeCount++;
			}
			this.code = this.getCodePrefix()+Integer.toString(codeCount);
		}
		else {
			if (gw.gameThings.containsKey(code)) {
				throw new IllegalGameCodeException(code);
			}
			else {
				this.code = code;
			}
		}
	}
	
	/**Returns the next available gameCode with given prefix.*/
	public static String getNextCode(GameWorld gw, String prefix) {
		int codeCount = 1;
		while (gw.gameThings.containsKey(prefix+Integer.toString(codeCount))) {
			codeCount++;
		}
		return prefix+Integer.toString(codeCount);
	}

	/** Tries to change the GameThings code to the given newCode everywhere it is currently used.
	 * Throws IllegalGameCodeException if unable to do that for some reason.
	 * @throws IllegalGameCodeException */
	public abstract void changeCode(String newCode) throws IllegalGameCodeException;
	
	/**Returns the String line that can be used to save and later recreate the GameThing.*/
	public abstract String getSaveline();
	
	/**This method is called when user wants to delete the object.*/
	public abstract void remove();
	
	/**Returns the appropriate gameCodePrefix for the class that the GameThing is an instance of.*/
	public abstract String getCodePrefix();
	
	/* The player actions: */
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
	
	/**Three Digit Code*/
	public String tdc() {
		if (this.code.length() == 1) {
			return this.code+"  ";
		}
		else if (this.code.length() == 2) {
			return this.code+" ";
		}
		else if (this.code.length() == 3) {
			return this.code;
		}
		else {
			return "   ";
		}
	}
	
	/**Return string that is used to represent the GameThing in the editor.*/
	public abstract String getEditorInfo();

	public String name() { return this.name; }
	public String code() { return this.code; }
}
