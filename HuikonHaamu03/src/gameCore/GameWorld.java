package gameCore;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.ListModel;

import gameExceptions.CorruptedSaveLineException;
import gameExceptions.IllegalGameCodeException;
import gameExceptions.WorldMakingConflict;
import gameObj.*;

public class GameWorld {
	
	public String name;
	public String language;
	public Game game = null;
	public Player player = null;
	
	public HashMap<String,GameThing> gameThings = new HashMap<String,GameThing>();
	public HashMap<String,GameThing> thingsInVoid = new HashMap<String,GameThing>();
	
	public HashMap<String,Room> roomMap = new HashMap<String,Room>();
	public Room startingRoom = null;
	public HashMap<String,GameObject> objectMap = new HashMap<String,GameObject>();
	public HashMap<String,Passage> passageMap = new HashMap<String,Passage>();
	
	public GameWorld(String name, String language) {
		this.name = name;
		this.language = language;
		HC.HCinit(language);
	}
	
	
	
	/** Method for checking that every room can be accessed from the starting room.*/
	public Room roomsConnected() {
		//TODO Check that every room can be accessed from the starting room.
		return null;
	}
	
	public void remove(GameThing thing) {
		thing.remove();
	}
	
	/**Creates a text file named <saveName>.hhw that works as a save file for the current world.
	 * If saveName is null then file name is <worldName>.hhw*/
	public void saveWorld(String saveName) {
		if (saveName == null) {
			saveName = this.name;
		}
		Room unReachable = this.roomsConnected();
		if (unReachable != null) { //TODO roomsConnected()
			System.out.println("WARNING---Room:"+unReachable.name+", "+unReachable.code+" can not be reached from starting room.");
		}
		BufferedWriter writer = null;
	    try {
		    writer = new BufferedWriter( new FileWriter(saveName+".hhw" ));
		    //Header data:
		    writer.write("Save file for a HuikonHaamu GameWorld\r");
		    writer.write("HHversion:"+Game.gameVersion+"\r");
		    writer.write(this.language+"\r");
		    writer.write(this.name+":--------------------------------\r");
		    
		    //Rooms
		    Collection<Room> roomCollection = roomMap.values();
		    Iterator<Room> roomItr = roomCollection.iterator();
		    Room currRoom;
		    while (roomItr.hasNext()) {
		    	currRoom = roomItr.next();
		    	writer.write(currRoom.getSaveline());
		    }
		    //Player
		    if (this.player != null) {
		    	writer.write(this.player.getSaveline());
		    }
		    //GameObjects
		    Collection<GameObject> objectCollection = this.objectMap.values();
		    Iterator<GameObject> objItr = objectCollection.iterator();
		    GameObject currObj;
		    while (objItr.hasNext()) {
		    	currObj = objItr.next();
		    	writer.write(currObj.getSaveline());
		    }
		    //Passages
		    Collection<Passage> passageCollection = this.passageMap.values();
		    Iterator<Passage> passageItr = passageCollection.iterator();
		    Passage currPassage;
		    while (passageItr.hasNext()) {
		    	currPassage = passageItr.next();
		    	writer.write(currPassage.getSaveline());
		    }
		    
	    } catch (Exception e) {
	      throw new RuntimeException(e);
	    } finally {
	      if (writer != null) {
	        try {
	        	writer.close();
	        } catch (IOException e) {
	          // Ignore issues during closing
	        }
	      }
	    }	
	}
	
	/**This function recreates a GameWorld from a HHsave file.*/
	public static GameWorld loadWorld(String fileName) {
		BufferedReader reader = null;
		GameWorld newWorld;
	    try {
		    reader = new BufferedReader( new FileReader(fileName));
		    //Header data:
		    reader.readLine();//Header
		    String line = reader.readLine(); //version info
		    String saveVersion;
		    if (line.startsWith("HHversion:")) {
		    	saveVersion = line.split(":")[1];
		    }
		    else {
		    	throw new CorruptedSaveLineException(line);
		    }
		    String lang = reader.readLine();// lang
		    line = reader.readLine();//WorldName:-------
		    newWorld = new GameWorld(line.split(":")[0],lang);
		    
		    line = reader.readLine();
		    while (line != null) {
		    	if (line.startsWith("Room::")) {
		    		Room.loadLine(line, newWorld);
		    	}
		    	else if (line.startsWith("Object::")) {
		    		GameObject.loadLine(line, newWorld);
		    	}
				else if (line.startsWith("Door::")) {
					Door.loadLine(line, newWorld);
		    	}
				else if (line.startsWith("Item::")) {
					Item.loadLine(line, newWorld);
				}
				else if (line.startsWith("Passage::")) {
					Passage.loadLine(line, newWorld);
				}
				else if (line.startsWith("Player::")) {
					Player.loadLine(line, newWorld);
				}
				else {
					throw new CorruptedSaveLineException(line);
				}
		    	line = reader.readLine();
		    }
	    } catch (Exception e) {
	    	e.printStackTrace();
	      throw new RuntimeException(e);
	    } finally {
	      if (reader != null) {
	        try {
	        	reader.close();
	        } catch (IOException e) {
	          // Ignore issues during closing
	        }
	      }
	    }	
		return newWorld;
	}


	public String[] getThingArray() {
		Iterator<GameThing> itr = this.gameThings.values().iterator();
		String[] thingArray = new String[this.gameThings.size()];
		int i = 0;
		while (itr.hasNext()) {
			thingArray[i] = itr.next().getEditorInfo();
			i++;
		}

		return thingArray;
	}



	public String[] getRoomArrayForEditor() {
		Iterator<Room> itr = this.roomMap.values().iterator();
		String[] roomArray = new String[this.roomMap.size()];
		int i = 0;
		Room current;
		while (itr.hasNext()) {
			current = itr.next();
			roomArray[i] = current.getEditorInfo();
			i++;
		}
		return roomArray;
	}
	
	public String[] getVoidObjectArrayForEditor() {
		Iterator<GameThing> itr = this.thingsInVoid.values().iterator();
		String[] objectArray = new String[this.thingsInVoid.size()];
		int i = 0;
		GameThing current;
		while (itr.hasNext()) {
			current = itr.next();
			objectArray[i] = current.getEditorInfo();
			i++;
		}
		return objectArray;
	}
}
