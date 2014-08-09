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
		public static String ROOM_VOID_NAME;

		public static String ROOM_DESCRIPTION;

		public static String ROOM_EDITOR_NAME_LABEL;
		public static String ROOM_EDITOR_NAME_TOOLTIP;
		public static String ROOM_EDITOR_MISSING_NAME;
		
		public static String ROOM_EDITOR_DESCRIPTION_LABEL;
		public static String ROOM_EDITOR_DESCRIPTION_TOOLTIP;
		
		public static String ROOM_EDITOR_CODE_LABEL;
		public static String ROOM_EDITOR_CODE_TOOLTIP;
		public static String ROOM_EDITOR_CODE_TAKEN;
		
		public static String ROOM_EDITOR_XDIM_LABEL;
		public static String ROOM_EDITOR_XDIM_TOOLTIP;
		public static String ROOM_EDITOR_YDIM_LABEL;
		public static String ROOM_EDITOR_YDIM_TOOLTIP;
		public static String ROOM_EDITOR_DIM_OUT_OF_BOUNDS;
		public static String ROOM_EDITOR_DIM_NOT_INT;
	
	//GameObject
		public static String OBJECT_DESCRIPTION;
		
	//Door
		public static String DOOR_DESCRIPTION;
		public static String DOOR_NO_PASSAGE;
		public static String DOOR_GO_NO_PASSAGE;
		public static String DOOR_OPEN_CLOSED;
		public static String DOOR_OPEN_OPENED;
		public static String DOOR_CLOSE_CLOSED;
		public static String DOOR_CLOSE_OPENED;
		public static String DOOR_GO_CLOSED;
		public static String DOOR_GO_OPENED;
	
	
	
	//CommandLineUI
		public static String GO;
		public static String OPEN;
		public static String CLOSE;
		public static String TAKE;
		public static String LOOK;
		public static String HIT;
		
		public static String SYNTAX_ERROR;
		public static String UNKNOWN_CODE;
		public static String UNKNOWN_ACTION;
		
		public static String HELP;
		
	//EDITOR
		public static String EDITOR_ROOMS_LIST_LABEL;
		public static String EDITOR_ROOMS_BUTTON_TEXT;
		public static String EDITOR_ROOMS_BUTTON_TOOLTIP;

		public static String EDITOR_OBJECT_LIST_LABEL;

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
			
			//
			OBJECT_DESCRIPTION = "This is surely interesting object but you can't take it with you.";
			
			// Door
			DOOR_DESCRIPTION = "This just an ordinary door.";

			DOOR_GO_NO_PASSAGE = "This door leads no where.";
			DOOR_OPEN_CLOSED = "You open the door.";
			DOOR_OPEN_OPENED = "This door is already open.";
			DOOR_CLOSE_CLOSED = "This door is already closed.";
			DOOR_CLOSE_OPENED = "You close the door.";
			DOOR_GO_CLOSED = "This door is closed.";
			DOOR_GO_OPENED = "You go through the door.";
			
			//CommandLineUI
			GO = "go";
			OPEN = "open";
			CLOSE = "close";
			TAKE = "take";
			LOOK = "look";
			HIT = "hit";
			
			SYNTAX_ERROR = "Syntax Error.\r <action> <object code> like 'go d1'";
			UNKNOWN_CODE = "Unknown code";
			UNKNOWN_ACTION = "Unknown action. Use "+GO+", "+OPEN+", "+CLOSE+", "+TAKE+", "+LOOK+" or "+HIT;
			
			HELP = "Someone should write some help instructions here."; //TODO
		}
		

		if (lang.equals("fi")) { // TODO SUOMEKSI VAAN
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
