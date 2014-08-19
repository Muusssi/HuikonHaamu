package gameObj;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import gameCore.GameWorld;
import gameExceptions.CorruptedSaveLineException;
import gameExceptions.IllegalGameCodeException;
import gameExceptions.WorldMakingConflict;

	
public class Room extends GameThing {

	/*Positions (0-76): if dim=7x7
	 * Seven possible positions on each side starting from left North
	 *    0  1  2  3  4  5  6 
	 * 27 28 29 30 31 32 33 34 7
	 * 26 35 36 37 38 39 40 41 8
	 * 25 42 43 44 45 46 47 48 9
	 * 24 49 50 51 52 53 54 55 10
	 * 23 56 57 58 59 60 61 62 11
	 * 22 63 64 65 66 67 68 69 12
	 * 21 70 71 72 73 74 75 76 13
	 *    20 19 18 17 16 15 14 
	 *    */

	public int xdim;
	public int ydim;
	public GameObject[] objectArray;
	public HashMap<String,GameObject> objectMap = new HashMap<String,GameObject>();


	public Room(GameWorld gw, String name, String description, String code, int xdim, int ydim)
				throws IllegalGameCodeException, WorldMakingConflict {
		super(gw, name, description, code);
		if ((3 > xdim && xdim > 9) && (3 > ydim && ydim > 9)) {
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
	
	protected Room(GameWorld gw)
			throws IllegalGameCodeException, WorldMakingConflict {
		super(gw, HC.ROOM_VOID_NAME, "", "void");
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
	public void changeCode(String newCode) throws IllegalGameCodeException {
		if (code == null || gw.gameThings.containsKey(newCode)) {
			throw new IllegalGameCodeException(newCode);
		}
		else {
			gw.gameThings.remove(this.code);
			gw.roomMap.remove(this.code);
			this.code = newCode;
			gw.gameThings.put(newCode, this);
			gw.roomMap.put(newCode, this);
		}
	}
	
	/**Tries to change the rooms dimensions. TODO not implemented!*/
	public void changeDimensions(int newXdim, int newYdim) {
		
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
		return this.code+": "+this.name+" - "+this.xdim+"x"+this.ydim;
	}

	@Override
	public void remove() {
		
	}

}
