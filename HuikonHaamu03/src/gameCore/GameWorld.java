package gameCore;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import gameObj.*;

public class GameWorld {
	
	protected String name;
	public String language;
	public Game game = null;
	public Player player = null;
	
	public HashMap<String,GameThing> gameThings = new HashMap<String,GameThing>();
	public HashMap<String,GameThing> thingsInVoid = new HashMap<String,GameThing>();
	
	public HashMap<String,Room> rooms = new HashMap<String,Room>();
	public Room startingRoom = null;
	public HashMap<String,Door> objects = new HashMap<String,Door>();
	
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
	
	/**Creates a text file named <worldname>.hhw that works as save for the current world.*/
	public void saveWorld(String saveName) {
		if (saveName == null) {
			saveName = this.name;
		}
		Room unReachable = this.roomsConnected();
		if (unReachable != null) { //TODO
			System.out.println("WARNING---Room:"+unReachable.name()+", "+unReachable.code()+" can not be reached from starting room.");
		}
		BufferedWriter writer = null;
	    try {
		    writer = new BufferedWriter( new FileWriter(saveName+".hhw" ));
		    //Header data:
		    writer.write("Save file for a HuikonHaamu GameWorld\r");
		    writer.write("HHversion:"+Integer.toString(Game.gameVersion)+"\r");
		    writer.write(this.name+"--------------------------------\r");
		    
		    //Rooms
		    Collection<Room> roomCollection = rooms.values();
		    Iterator<Room> roomItr = roomCollection.iterator();
		    Room currRoom;
		    while (roomItr.hasNext()) {
		    	currRoom = roomItr.next();
		    	System.out.println(currRoom.getSaveline());
		    	writer.write(currRoom.getSaveline());
		    	//Doors in room
		    	
		    }
		    //Player
		    if (this.player != null) {
		    	writer.write(this.player.getSaveline());
		    }
		    
		    //Doors
		    
		    //InvItems
		    
		    
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
		//TODO
		return null;
	}
	
}
