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

		public static String ROOM_EDITOR_NAME_TOOLTIP;
		public static String ROOM_EDITOR_MISSING_NAME;
		
		public static String ROOM_EDITOR_DESCRIPTION_TOOLTIP;
		
		public static String ROOM_EDITOR_XDIM_LABEL;
		public static String ROOM_EDITOR_XDIM_TOOLTIP;
		public static String ROOM_EDITOR_YDIM_LABEL;
		public static String ROOM_EDITOR_YDIM_TOOLTIP;
		public static String ROOM_EDITOR_DIM_OUT_OF_BOUNDS;
		public static String ROOM_EDITOR_DIM_NOT_INT;
	
	//GameObject
		public static String OBJECT_DESCRIPTION;
		
		public static String OBJECT_EDITOR_NAME_TOOLTIP;
		public static String OBJECT_EDITOR_MISSING_NAME;
		
		public static String OBJECT_EDITOR_DESCRIPTION_TOOLTIP;
		
		
		public static String OBJECT_EDITOR_POSITION_LABEL;
		public static String OBJECT_EDITOR_POSITION_TOOLTIP;
		
		public static String OBJECT_EDITOR_POSITION_ILLEGAL;
		public static String EDITOR_OBJECT_NO_ROOMS_ADDING;
		
		public static String OBJECT_EDITOR_TOVOID_LABEL = "Put to Void:";
		public static String OBJECT_EDITOR_TOVOID_TOOLTIP = "If you check this check box the object will be added to void and to the selected room.";
		
		public static String OBJECT_EDITOR_DOOR_LABEL = "A door:";
		public static String OBJECT_EDITOR_DOOR_TOOLTIP = "If you check this check box the new object will become a door.";
		
	//Door
		public static String DOOR_DESCRIPTION;
		
		public static String DOOR_NO_PASSAGE = "This door goes nowhere.";
		public static String DOOR_GO_NO_PASSAGE;
		public static String DOOR_OPEN_CLOSED;
		public static String DOOR_OPEN_OPENED;
		public static String DOOR_CLOSE_CLOSED;
		public static String DOOR_CLOSE_OPENED;
		public static String DOOR_GO_CLOSED;
		public static String DOOR_GO_OPENED;
		
		//public static String 
	
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
		public static String EDITOR_ROOM_BUTTON_TEXT;
		public static String EDITOR_ROOM_BUTTON_TOOLTIP;
		public static String EDITOR_NEW_ROOM_TTILE;
		public static String EDITOR_EDIT_ROOM_TITLE = "Edit room: ";
		public static String EDITOR_NOT_EMPTY_ROOM_DIM_CHG = "You are trying to change dimensions of the room. In order to do that the room has to be empty. Do you want to put everything to void?";

		public static String EDITOR_OBJECT_LIST_LABEL;
		public static String EDITOR_OBJECT_BUTTON_TEXT;
		public static String EDITOR_OBJECT_BUTTON_TOOLTIP;
		public static String EDITOR_OBJECT_POSITION_SETTER_TITLE;
		public static String EDITOR_OBJECT_ROOM_FULL = "The room is already full. You have to remove some object to make room for the new object.";
		
		public static String EDITOR_DOOR_BUTTON_TEXT = "Door";
		public static String EDITOR_DOOR_BUTTON_TOOLTIP = "Add a new door. Doors are objects that can be linked to other doors.";
		
		public static String EDITOR_VOID_LIST_LABEL = "Void:";

		public static String EDITOR_NAME_LABEL;
		
		public static String EDITOR_DESCRIPTION_LABEL;
		public static String EDITOR_DESCRIPTION_TOOLTIP;
		
		public static String EDITOR_CODE_LABEL;
		public static String EDITOR_CODE_TOOLTIP;
		public static String EDITOR_CODE_TAKEN;
		
		public static String EDITOR_SAVENAME_LABEL;
		public static String EDITOR_SAVENAME_TITLE = "Save name";
		public static String EDITOR_SAVENAME_TOOLTIP = "Name of the file used to save this world.";
		public static String EDITOR_SAVENAME_MISSING = "Can't make a file with no name.";
		public static String EDITOR_SAVE_BUTTON_TEXT;
		public static String EDITOR_SAVE_BUTTON_TOOLTIP = "Save the wolrd you have created.";
		
		public static String EDITOR_LOAD_FILE_MISSING = "File with given name wasn't found.";
		public static String EDITOR_LOAD_BUTTON_TEXT = "Load";
		public static String EDITOR_LOAD_BUTTON_TOOLTIP = "Start working with a previously saved world.";
		
		public static String EDITOR_WORLD_NAME_LABEL = "World Name:";
		public static String EDITOR_WORLD_NAME_TOOLTIP = "Give name for the game world you are making.";
		public static String EDITOR_WORLD_NAME_TITLE = "New world name:";
		
		public static String EDITOR_POPUP_DELETE = "Delete";
		public static String EDITOR_POPUP_EDIT = "Edit";
		public static String EDITOR_POPUP_MOVE  = "Move";
		public static String EDITOR_POPUP_MOVE_TO_VOID = "Move to void";
		
		public static String EDITOR_MOVE_CHOOSE_ROOM_TITLE = "Choose the new room:";
		
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
			ROOM_VOID_NAME = "void";
			
			ROOM_DESCRIPTION = "This just an ordinary room.";
			
			ROOM_EDITOR_NAME_TOOLTIP = "Give a name for the room.";
			ROOM_EDITOR_MISSING_NAME = "The new room has to have a name.";
			
			ROOM_EDITOR_DESCRIPTION_TOOLTIP = "Description is what the player gets when he/she looks the room.";
			
			ROOM_EDITOR_XDIM_LABEL = "Width";
			ROOM_EDITOR_XDIM_TOOLTIP = "The size of the room from West to East. Between 2 and 7.";
			ROOM_EDITOR_YDIM_LABEL = "Length";
			ROOM_EDITOR_YDIM_TOOLTIP = "The size of the room from North to South. Between 2 and 7.";
			ROOM_EDITOR_DIM_OUT_OF_BOUNDS = "The dimensions of the room are out of bounds. Must be between 2 and 7.";
			ROOM_EDITOR_DIM_NOT_INT = "Give the measures of the room as integers.";
		
			
			//Objects
			OBJECT_DESCRIPTION = "This is surely interesting object but you can't take it with you.";
			
			OBJECT_EDITOR_NAME_TOOLTIP = "Give a name for the new object";
			OBJECT_EDITOR_MISSING_NAME = "You have to give a name to the new object.";
			
			OBJECT_EDITOR_DESCRIPTION_TOOLTIP = "Description is what the player gets when he/she looks the object.";
			
			OBJECT_EDITOR_POSITION_LABEL = "Position:";
			OBJECT_EDITOR_POSITION_TOOLTIP = "The place of the object in the room. Integer starting from 0. Can be left empty.";
			
			OBJECT_EDITOR_POSITION_ILLEGAL = "The given position isn't allowed.";
			EDITOR_OBJECT_NO_ROOMS_ADDING = "You first need to create rooms to put objects in.";
			
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
			
			//EDITOR
			EDITOR_ROOMS_LIST_LABEL = "Rooms:";
			EDITOR_ROOM_BUTTON_TEXT = "Room";
			EDITOR_ROOM_BUTTON_TOOLTIP = "Add a new room to this game world.";
			EDITOR_NEW_ROOM_TTILE = "New room";

			EDITOR_OBJECT_LIST_LABEL = "Objects:";
			EDITOR_OBJECT_BUTTON_TEXT = "Object";
			EDITOR_OBJECT_BUTTON_TOOLTIP = "Add a new object to the chosen room.";
			EDITOR_OBJECT_POSITION_SETTER_TITLE = "Choose position of the object:";
			

			EDITOR_NAME_LABEL = "Name:";
			
			EDITOR_DESCRIPTION_LABEL = "Description:";
			EDITOR_DESCRIPTION_TOOLTIP = "Description is what the player receives when he/she looks the this.";
			
			EDITOR_CODE_LABEL = "Code:";
			EDITOR_CODE_TOOLTIP = "Code is used to distinguish each different thing in the game world.";
			EDITOR_CODE_TAKEN = "The given code is already in use.";
			
			EDITOR_SAVENAME_LABEL = "Save Name";
			EDITOR_SAVE_BUTTON_TEXT = "Save";
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
