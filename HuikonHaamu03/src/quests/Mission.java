package quests;

import java.util.Iterator;
import java.util.LinkedList;

import gameCore.GameWorld;

public class Mission {
	
	public GameWorld gw;
	
	public String name;
	public String prolog;
	public String epilog;
	
	public LinkedList<Condition> conditions = new LinkedList<Condition>();
	public Quest quest;
	public boolean done = false;


	public Mission(GameWorld gw, String name, String prolog, String epilog, Quest quest) {
		this.gw = gw;
		this.name = name;
		this.prolog = prolog;
		this.epilog = epilog;
		this.quest = quest;
	}
	
	public void begin() {
		gw.game.questInfo(prolog);
		gw.activeMissions.add(this);
	}
	
	public void checkMission() {
		Iterator<Condition> itr = conditions.iterator();
		while (itr.hasNext()) {
			if (!itr.next().checkCondion()) {
				return;
			}
		}
		gw.game.questInfo(epilog);
		done = true;
		gw.activeMissions.remove(this);
		quest.advance();
	}


}
