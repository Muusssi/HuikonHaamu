package quests;

import gameCore.GameWorld;

import java.util.LinkedList;

public class Quest {
	
	public GameWorld gw;
	public String name;
	public String prolog;
	public String epilog;
	
	public LinkedList<Mission> missions = new LinkedList<Mission>();
	public int currentMission = 0;
	public boolean started = false;
	public boolean finished = false;
	

	public Quest(GameWorld gw, String name, String prolog, String epilog) {
		this.gw = gw;
		this.name = name;
		this.prolog = prolog;
		this.epilog = epilog;
	}
	
	public void addMission(Mission mission) {
		missions.add(mission);
	}
	
	public void startQuest() {
		gw.game.questInfo("   New Quest   ");
		gw.game.questInfo("   "+name);
		gw.game.questInfo(prolog);
		gw.activeQuests.add(this);
		started = true;
		advance();
	}
	
	public void advance() {
		if (missions.size() == currentMission) {
			endQuest();
		}
		else {
			missions.get(currentMission).begin();
			currentMission++;
		}
	}
		
	public void endQuest() {
		gw.game.questInfo(epilog);
		gw.game.questInfo("Quest finished.");
		gw.activeQuests.remove(this);
		finished = true;
	}
	
	

}
