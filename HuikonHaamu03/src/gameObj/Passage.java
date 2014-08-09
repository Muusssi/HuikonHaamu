package gameObj;

import gameCore.GameWorld;
import gameExceptions.CorruptedSaveLineException;
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
		this.door1.passage = this;
		this.room1 = door1.location;
		
		this.door2 = door2;
		this.door2.passage = this;
		this.room2 = door2.location;
		
		this.closed = closed;
		this.gw.passageMap.put(this.code, this);
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
				// Passage::<name>::<code>::<description>::
				// <door1code>::<door2code>::<closed>
		return "Passage::"+this.name+"::"+this.code+"::"+this.description+"::"
				+this.door1.code+"::"+this.door2.code+"::"+this.closed+"::\r";
	}
	
	/** Function for recreating a Passage from the line of text used to save it. */
	public static Passage loadLine(String saveLine, GameWorld gw) throws CorruptedSaveLineException {
		if (saveLine.startsWith("Passage::")) {
			String[] saveLineComp = saveLine.split("::");
			Passage newPassage;
			try {
				boolean closed;
				if (saveLineComp[6].equals("false")) {
					closed = false;
				}
				else if (saveLineComp[6].equals("true")) {
					closed = true;
				}
				else {
					throw new CorruptedSaveLineException(saveLine);
				}
				newPassage = new Passage(gw, saveLineComp[1], saveLineComp[3], saveLineComp[2],
								(Door)gw.objectMap.get(saveLineComp[4]), (Door)gw.objectMap.get(saveLineComp[5]),
								closed);
			} catch (Exception e) {
				e.printStackTrace();
				throw new CorruptedSaveLineException(saveLine);
			}
			return newPassage;
		}
		else {
			throw new CorruptedSaveLineException(saveLine);
		}
	}
	
	
	@Override
	public String getCodePrefix() {
		return "p";
	}

	@Override
	public String getEditorInfo() {
		// TODO Auto-generated method stub
		return null;
	}

}
