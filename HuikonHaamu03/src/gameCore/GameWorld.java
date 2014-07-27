package gameCore;

import java.util.HashMap;

import gameObj.*;

public class GameWorld {
	
	protected String name;
	public String language;
	public Game game = null;
	public Player player;
	
	public HashMap<String,GameThing> gameThings = new HashMap<String,GameThing>();
	public HashMap<String,GameThing> thingsInVoid = new HashMap<String,GameThing>();
	
	public HashMap<String,Room> rooms = new HashMap<String,Room>();
	public Room startingRoom = null;
	
	
	public GameWorld(String name, String language) {
		this.name = name;
		this.language = language;
	}
	
	
	public boolean roomsConnected() {
		//TODO Check that every room can be accessed from the starting room.
		return true;
	}
	
}
