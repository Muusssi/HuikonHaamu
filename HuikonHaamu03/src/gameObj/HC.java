package gameObj;

/* HARD CORE!!! 
 * No.. Hard Coded.
 */
public class HC {
	
	public static String lang;
	
	//GameThing
		//Action responses:
		public static String GAMETHING_TAKE;
		public static String GAMETHING_PUT;
		public static String GAMETHING_OPEN;
		public static String GAMETHING_CLOSE;
		public static String GAMETHING_GO;
		public static String GAMETHING_HIT;
	
	//Room
		public static String ROOM_DESCRIPTION;
	
	//Door
		public static String DOOR_DESCRIPTION;
		public static String DOOR_GO;
		public static String DOOR_OPEN_CLOSED;
		public static String DOOR_OPEN_OPENED;
		public static String DOOR_CLOSE_CLOSED;
		public static String DOOR_CLOSE_OPENED;
		public static String DOOR_GO_CLOSED;
		public static String DOOR_GO_OPENED;
	
	public static void HCinit(String lan) {
		lang = lan;
		
		if (lang.equals("eng")) {
			//GameThing responses:
			GAMETHING_TAKE = "You can't take that thing and carry it around.";
			GAMETHING_PUT = "You can't put that there.";
			GAMETHING_OPEN = "You can't open that.";
			GAMETHING_CLOSE = "You can't close that.";
			GAMETHING_GO = "You can't go in there.";
			GAMETHING_HIT = "Don't be so aggressive. You had no reason for hitting that thing.";
			
			// Room
			ROOM_DESCRIPTION = "This just an ordinary room.";
			
			// Door
			DOOR_DESCRIPTION = "This just an ordinary door.";
			DOOR_OPEN_CLOSED = "You open the door.";
			DOOR_OPEN_OPENED = "This door is already open.";
			DOOR_CLOSE_CLOSED = "This door is already closed.";
			DOOR_CLOSE_OPENED = "You close the door.";
			DOOR_GO_CLOSED = "This door is closed.";
			DOOR_GO_OPENED = "You go through the door.";
		}
	}
	
	


}
