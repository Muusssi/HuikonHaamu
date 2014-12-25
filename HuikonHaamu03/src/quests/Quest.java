package quests;

import gameCore.GameWorld;

import java.util.LinkedList;

public class Quest {
	
	public GameWorld gw;
	public String name;
	public String prolog;
	public String epilog;
	
	public LinkedList<Mission> missions;
	public boolean started = false;
	
	
	

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
		gw.game.questInfo(prolog);
		gw.activeQuests.add(this);
		
	}
	
	public void advance() {
		
	}
	
	

}
