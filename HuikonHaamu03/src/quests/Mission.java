package quests;

import java.util.Iterator;
import java.util.LinkedList;

import gameCore.GameWorld;
import gameExceptions.IllegalGameCodeException;

public class Mission {
	
	public GameWorld gw;
	
	public String name;
	public String prolog;
	public String epilog;
	public String code;
	
	public LinkedList<Condition> conditions = new LinkedList<Condition>();
	public Quest quest;
	public boolean done = false;


	public Mission(GameWorld gw, String name, String prolog, String epilog, Quest quest)
			throws IllegalGameCodeException {
		this.gw = gw;
		this.name = name;
		this.prolog = prolog;
		this.epilog = epilog;
		this.quest = quest;
		// Set the quest code
		if (code == null || code.equals("")) {
			this.code = getNewMissionCode();
		} else if (gw.quests.containsKey(code)) {
			throw new IllegalGameCodeException(code);
		} else {
			this.code = code;
		}
	}
	
	public String getNewMissionCode() { //TODO Fix mission numbering
		int qCounter = 1;
		while (gw.quests.containsKey("M"+Integer.toString(qCounter))) {
			qCounter++;
		}
		return "M"+Integer.toString(qCounter);
	}
	
	public void begin() {
		gw.game.questInfo(prolog);
		gw.addNewMission(this);
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
		gw.addFinishedMission(this);
		quest.advance();
	}


}
