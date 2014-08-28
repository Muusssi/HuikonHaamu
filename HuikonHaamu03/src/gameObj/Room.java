package gameObj;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import gameCore.GameWorld;
import gameExceptions.CorruptedSaveLineException;
import gameExceptions.IllegalGameCodeException;
import gameExceptions.WorldMakingConflict;

	
public class Room extends GameThing {

	public int xdim;
	public int ydim;
	public GameObject[] objectArray;
	public HashMap<String,GameObject> objectMap = new HashMap<String,GameObject>();


	public Room(GameWorld gw, String name, String description, String code, int xdim, int ydim)
				throws IllegalGameCodeException, WorldMakingConflict {
		super(gw, name, description, code);
		if (newDimOutOfBounds(xdim, ydim)) {
			throw new WorldMakingConflict("New room dimensions out of range.");
		}
		this.xdim = xdim;
		this.ydim = ydim;
		objectArray = new GameObject[xdim*ydim];
		
		this.gw.roomMap.put(this.code, this);
		if(gw.startingRoom == null) {
			this.setAsStartingRoom();
		}
	}
	


	/**Tries to add a GameObject to the given position.*/
	public void putObject(GameObject newGameObject, int position) throws WorldMakingConflict {
		if (objectArray[position] == null) {
			objectArray[position] = newGameObject;
			objectMap.put(newGameObject.code, newGameObject);
		}
		else {
			throw new WorldMakingConflict("Object position "+Integer.toString(position)+" already taken.");
		}
	}
	
	/**Sets the room as the GameWorlds starting room */
	public void setAsStartingRoom() {
		gw.startingRoom = this;
		if (gw.player != null) {
			gw.player.location = this;
		}
	}
	
	@Override
	public void giveDescription(String description) {
		if (description == null || description.equals("")) {
			this.description = HC.ROOM_DESCRIPTION;
		}
		else {
			this.description = description;
		}
	}



	@Override
	public void changeCode(String newCode) {
		gw.gameThings.remove(this.code);
		gw.roomMap.remove(this.code);
		this.code = newCode;
		gw.gameThings.put(newCode, this);
		gw.roomMap.put(newCode, this);
	}
	
	/**Tries to change the rooms dimensions. TODO not implemented!*/
	public void changeDimensions(int newXdim, int newYdim) {
		this.xdim = newXdim;
		this.ydim = newYdim;
		this.objectArray = new GameObject[newXdim*newYdim];
	}
	
	@Override
	public String getCodePrefix() {
		return "r";
	}

	@Override
	public String getSaveline() {
				// Room::<name>::<code>::<description>::
				// <xdim>::<ydim>
		return "Room::"+this.name+"::"+this.code+"::"+this.description+"::"
				+Integer.toString(this.xdim)+"::"+Integer.toString(this.ydim)+"::\r";
	}
	
	/** Function for recreating a Room object from the line of text used to save it. */
	public static Room loadLine(String saveLine, GameWorld gw) throws CorruptedSaveLineException {
		if (saveLine.startsWith("Room::")) {
			String[] saveLineComp = saveLine.split("::");
			Room newRoom;
			try {
				newRoom = new Room(gw, saveLineComp[1], saveLineComp[3], saveLineComp[2],
									Integer.parseInt(saveLineComp[4]), Integer.parseInt(saveLineComp[5]));
			} catch (Exception e) {
				e.printStackTrace();
				throw new CorruptedSaveLineException(saveLine);
			}
			return newRoom;
		}
		else {
			throw new CorruptedSaveLineException(saveLine);
		}
	}
	
	/**Returns the iterator for the GameObjects in the room.*/
	public Iterator<GameObject> getRoomThings() {
		Collection<GameObject> collection = objectMap.values();
		return collection.iterator();
	}
	
	private String[] getObjectListForCmdLineUI() {
		String[] objectDataArr;
		if (this.ydim*2 + 2 < this.objectMap.size()) {
			objectDataArr = new String[this.objectMap.size()];
		}
		else {
			objectDataArr = new String[this.ydim*2 + 2];
		}
		Iterator<GameObject> itr = this.objectMap.values().iterator();
		int i=0;
		while (itr.hasNext()) {
			GameObject currentObj = itr.next();
			objectDataArr[i] = currentObj.tdc()+": "+currentObj.name;
			i++;
		}
		for (int j=i;j<objectDataArr.length;j++) {
			objectDataArr[j] = "";
		}
		return objectDataArr;
	}
	
	
	
	/**Method for cmdLineUIs. Prints the view of the room in text based manner.
	 * This is magic!*/
	public void printRoom() { //TODO depricated
		
		int objectListOffset = (this.xdim+2)*4;
		int objectListCount = 0;
		String[] objectList = this.getObjectListForCmdLineUI();
		/* RoomName---------------------
		 * 
		 * #----------------------#
		 * |                      |
		 * |                      |
		 * |                      |
		 * |                      |
		 * |         5x5          |
		 * |                      |
		 * |                      |
		 * |                      |
		 * |                      |
		 * |                      |
		 * #----------------------#
		 * 
		 * */
		System.out.println();
		System.out.print("#---");
		for (int i=0;i<this.objectArray.length;i++) {
			if (i < this.xdim) {
				if (this.objectArray[i] != null) {
					System.out.print(this.objectArray[i].tdc()+"-");
				}
				else {
					System.out.print("----");
				}
				if (i == xdim-1) {
					System.out.print("#   ");
					System.out.println(objectList[objectListCount]);
					objectListCount++;
				}
			}
			else if (i < this.objectArray.length - this.xdim) {
				if (this.objectArray[i] != null) {
					System.out.print(this.objectArray[i].tdc()+" ");
				}
				else if ((i+2)%(xdim+2) == 0) {
					System.out.print("|   ");
					for (int j=0;j<xdim;j++) {
						System.out.print("    ");
					}
					System.out.print("|   ");
					System.out.println(objectList[objectListCount]);
					objectListCount++;
					
					System.out.print("|   ");
				}
				else if ((i+3)%(xdim+2) == 0) {
					System.out.print("|   ");
					System.out.println(objectList[objectListCount]);
					objectListCount++;
				}
				else {
					System.out.print("    ");
				}
			}
			else {
				if (i == this.objectArray.length - this.xdim) {
					System.out.print("#---");
				}
				if (this.objectArray[i] != null) {
					System.out.print(this.objectArray[i].tdc()+"-");
				}
				else {
					System.out.print("----");
				}
			}
		}
		System.out.print("#   ");
		System.out.println(objectList[objectListCount]);
		objectListCount++;
		System.out.println();
		
		
	}
	
	public String[] getObjectArrayForEditor() {
		Iterator<GameObject> itr = this.objectMap.values().iterator();
		String[] objectArray = new String[this.objectMap.size()];
		int i = 0;
		GameObject current;
		while (itr.hasNext()) {
			current = itr.next();
			objectArray[i] = current.getEditorInfo();
			i++;
		}
		return objectArray;
	}

	@Override
	public String getEditorInfo() {
		return this.code+": Room: "+this.name+" - "+this.xdim+"x"+this.ydim;
	}

	@Override
	public void remove() {
		for (int position=0; position<this.objectArray.length; position++) {
			if (objectArray[position] != null) {
				objectArray[position].putToVoid();
			}
		}
		gw.gameThings.remove(this.code);
		gw.roomMap.remove(this.code);
		if (gw.startingRoom == this) {
			gw.startingRoom = null;
		}
	}
	
	public boolean isFull() {
		for (int pos=0; pos<this.objectArray.length; pos++) {
			if (this.objectArray[pos] == null) {
				return false;
			}
		}
		return true;
	}
	
	public boolean newDimOutOfBounds(int newXdim, int newYdim) {
		if (newXdim<2 || newXdim >9) {
			return true;
		}
		if (newYdim<2 || newYdim >9) {
			return true;
		}
		return false;
	}
	
	public void objectsToVoid() {
		for (int pos=0;pos<this.objectArray.length;pos++) {
			if (this.objectArray[pos] != null) {
				this.objectArray[pos].putToVoid();
			}
		}
	}

}
