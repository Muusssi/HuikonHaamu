package quests;

import java.util.Iterator;
import java.util.LinkedList;

import gameCore.GameWorld;

public class Mission {
	
	public GameWorld gw;
	
	public String name;
	public String prolog;
	public String epilog;
	
	public LinkedList<Condition> conditions;
	public Quest quest;
	public boolean done = false;


	public Mission(GameWorld gw, String name, String prolog, String epilog, Quest quest) {
		this.gw = gw;
		this.name = name;
		this.prolog = prolog;
		this.epilog = epilog;
		this.quest = quest;
	}
	
	public void checkMission() {
		Iterator<Condition> itr = conditions.iterator();
		while (itr.hasNext()) {
			if (!itr.next().checkCondion()) {
				return;
			}
		}
		done = true;
		quest.advance();
	}


}
