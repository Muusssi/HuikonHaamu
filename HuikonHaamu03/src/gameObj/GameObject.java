package gameObj;

import gameCore.GameWorld;
import gameExceptions.CorruptedSaveLineException;
import gameExceptions.IllegalGameCodeException;
import gameExceptions.WorldMakingConflict;

public class GameObject extends GameThing {
	
	public Room location = null;
	public int position;
	
	public GameObject(GameWorld gw, String name, String description, String code, Room location, int position)
			throws IllegalGameCodeException, WorldMakingConflict {
		super(gw, name, description, code);
		if (location != null && !location.equals("null")) {
			location.putObject(this, position);
			this.location = location;
		}
		else {
			gw.thingsInVoid.put(this.code, this);
		}
		this.position = position;
		gw.objectMap.put(this.code, this);
	}
	
	public void putToVoid() {
		this.location.objectMap.remove(this.code);
		this.location.objectArray[this.position] = null;
		gw.thingsInVoid.put(this.code, this);
	}

	@Override
	public void giveDescription(String description) {
		if (description == null || description.equals("null")) {
			this.description = HC.OBJECT_DESCRIPTION;
		}
		else {
			this.description = description;
		}
	}

	@Override
	public void changeCode(String newCode) throws IllegalGameCodeException {
		// TODO Auto-generated method stub
	}

	@Override
	public String getSaveline() {
		/* Object::<name>::<code>::<description>::
		 * <roomCode>::<position>
		 * (room = null if in void)
		 */
		String roomString;
		if (this.location == null) {
			roomString = "null";
		}
		else {
			roomString = this.location.code;
		}
		return "Object::"+this.name+"::"+this.code+"::"+this.description+"::"+
				roomString+"::"+Integer.toString(this.position)+"::\r";
	}
	
	/** Function for recreating a GameObject object from the line of text used to save it.*/
	public static GameObject loadLine(String saveLine, GameWorld gw) throws CorruptedSaveLineException {
		if (saveLine.startsWith("Object::")) {
			String[] saveLineComp = saveLine.split("::");
			GameObject newGameObject;
			try {
				if (saveLineComp[4].equals(null)) {
					newGameObject = new GameObject(gw, saveLineComp[1], saveLineComp[3], saveLineComp[2],
							null, Integer.parseInt(saveLineComp[5]));
				}
				else {
					newGameObject = new GameObject(gw, saveLineComp[1], saveLineComp[3], saveLineComp[2],
							gw.roomMap.get(saveLineComp[4]), Integer.parseInt(saveLineComp[5]));
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new CorruptedSaveLineException(saveLine);
			}
			return newGameObject;
		}
		else {
			throw new CorruptedSaveLineException(saveLine);
		}
	}
	
	
	@Override
	public String getCodePrefix() {
		return "o";
	}

	@Override
	public String getEditorInfo() {
		return this.code+": "+this.name+" -Object";
	}

	@Override
	public void remove() {
		gw.gameThings.remove(this.code);
		gw.objectMap.remove(this.code);
		this.location.objectMap.remove(this.code);
		this.location.objectArray[this.position] = null;
	}

}
