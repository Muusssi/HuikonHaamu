package gameCore;

import java.util.HashMap;
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
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		game.startGame(gw);
		
		Scanner sc = new Scanner(System.in);
		boolean running = true;
		String[] command;
		String code = "";
		
		/*
		Set<String> setti = gw.gameThings.keySet();
		Iterator<String> itr = setti.iterator();
		while (itr.hasNext()) {
			System.out.println(itr.next());
		}
		*/
		
		
		while (running) {
			
			Room currentRoom = gw.player.location;
			System.out.println(currentRoom.getName() + ":-----------------------");
			currentRoom.printAvailableGameThings();
			HashMap<String,GameThing> available = gw.player.getAvailableGameThings();
			command = sc.nextLine().split(" ");
			System.out.println("--------------------------------------");
			System.out.print("-->");
			
			if (command.length != 2) {
				System.out.println(HC.SYNTAX_ERROR);
			}
			else if (available.containsKey(command[1])) {
				//action
				code = command[1];
				if (command[0].equals(HC.GO)) {
					gw.player.getAvailableGameThings().get(code).go();
				}
				else if (command[0].equals(HC.OPEN)) {
					gw.player.getAvailableGameThings().get(code).open();
				}
				else if (command[0].equals(HC.CLOSE)) {
					gw.player.getAvailableGameThings().get(code).close();
				}
				else if (command[0].equals(HC.TAKE)) {
					gw.player.getAvailableGameThings().get(code).take();
				}
				else if (command[0].equals(HC.LOOK)) {
					gw.player.getAvailableGameThings().get(code).explore();
				}
				else if (command[0].equals(HC.HIT)) {
					gw.player.getAvailableGameThings().get(code).hit();
				}
				else {
					System.out.println(HC.UNKNOWN_ACTION);
				}
			}
			else {
				System.out.println(HC.UNKNOWN_CODE);
			}
			
			
			
			
			
		}
	}

}
