package gameCore;

import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import gameExceptions.IllegalGameCodeException;
import gameObj.*;

public class CmdlineUI {

	public CmdlineUI() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Game game = new Game();
		GameWorld gw = new GameWorld("maailma", "eng");
		

		try {
			Room h1 = new Room(gw, "huone1", null, null);
			Room h2 = new Room(gw, "huone2", null, null);
			Room h3 = new Room(gw, "huone3", null, null);
			
			Door d1 = new Door(gw, "ovi1", null, null, h1, 1, h2, 15, false);
			Door d2 = new Door(gw, "ovi2", null, null, h2, 10, h3, 24, false);
			Door d3 = new Door(gw, "ovi3", null, null, h1, 5, h3, 19, false);
			
			Player pl = new Player(gw, "ovi1", null);
			
		} catch (IllegalGameCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		game.startGame(gw);
		
		Scanner sc = new Scanner(System.in);
		boolean running = true;
		String command = "";
		String code = "";
		
		Set<String> setti = gw.gameThings.keySet();
		Iterator<String> itr = setti.iterator();
		while (itr.hasNext()) {
			System.out.println(itr.next());
		}
		System.out.println("------------");
		
		
		while (running) {
			command = sc.nextLine();
			//command
			if (command.startsWith("go ")) {
				code = command.substring(3);
				gw.gameThings.get(code).go();
				
			}
			
			
		}
	}

}
