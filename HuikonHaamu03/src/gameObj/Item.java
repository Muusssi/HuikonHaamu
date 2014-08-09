package gameObj;

import gameCore.GameWorld;
import gameExceptions.CorruptedSaveLineException;
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
		return "I";
	}

	@Override
	public String getSaveline() {
		/* Item::<name>::<code>::<description>::*/
		return "Item::"+this.name+"::"+this.code+"::"+this.description+"\r";
	}
	
	/** Function for recreating an Item from the line of text used to save it.*/
	public static Item loadLine(String saveLine, GameWorld gw) throws CorruptedSaveLineException {
		if (saveLine.startsWith("Item::")) {
			String[] saveLineComp = saveLine.split("::");
			Item newItem;
			try {
				newItem = new Item(gw, saveLineComp[1], saveLineComp[3], saveLineComp[2]);
			} catch (Exception e) {
				e.printStackTrace();
				throw new CorruptedSaveLineException(saveLine);
			}
			return newItem;
		}
		else {
			throw new CorruptedSaveLineException(saveLine);
		}
	}

	@Override
	public String getEditorInfo() {
		// TODO Auto-generated method stub
		return null;
	}

}
