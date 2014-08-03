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
		
		
		//Creating a test world:
		GameWorld gw = null;
		try {
			/*
			 * 
			Room h1 = new Room(gw, "huone1", null, null,3,4);
			GameObject o1 = new GameObject(gw, "pöytä", "siinä voi vaikkapa istua", null, h1, 16);
			Room h2 = new Room(gw, "huone2", null, null,2,7);
			GameObject o2 = new GameObject(gw, "tuoli", "siinä voi vaikkapa istua", null, h1, 15);
			Door d1 = new Door(gw, "ovi", null, null, h1, 3);
			Door d2 = new Door(gw, "ovi", null, null, h2, 8);
			d1.linkTo(d2, false);
			Player pl = new Player(gw, "Pelaaja", null);
			*/
			gw = new GameWorld("maailma", "eng");
			gw.loadWorld("maailma.hhw");
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		game.startGame(gw);
		
		
		
		//The actual UI code
		Scanner sc = new Scanner(System.in);
		boolean running = true;
		String[] command;
		String code = "";
		
		
		
		while (running) {
			
			Room currentRoom = gw.player.location;
			System.out.println(currentRoom.name() + ":-----------------------");
			currentRoom.printRoom();
			HashMap<String,GameThing> available = gw.player.getAvailableGameThings();
			command = sc.nextLine().split(" ");
			System.out.println("--------------------------------------");
			System.out.print("-->");
			
			if (command.length != 2) {
				if (command[0].equals("save")) {
					gw.saveWorld(null);
					System.out.println("Gamesaved");
				}
				else if (command[0].equals("quit")) {
					System.out.println("Bye!");
					break;
				}
				else {
					System.out.println(HC.SYNTAX_ERROR);
				}
				
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
