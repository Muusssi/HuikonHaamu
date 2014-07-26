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
			
			//Room
			ROOM_DESCRIPTION = "This just an ordinary room.";
			
		}
	}
	
	


}
