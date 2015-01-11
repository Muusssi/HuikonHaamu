package quests;

import gameCore.GameWorld;
import gameExceptions.CorruptedSaveLineException;
import gameExceptions.IllegalGameCodeException;
import gameObj.GameObject;

import java.util.LinkedList;

public class Quest extends UnInteractableThing{
	
	public GameWorld gw;
	public String name;
	public String prolog;
	public String epilog;
	public String code;
	
	public LinkedList<Mission> missions = new LinkedList<Mission>();
	public int currentMission = 0;
	public boolean started = false;
	public boolean finished = false;
	

	public Quest(GameWorld gw, String name, String prolog, String epilog, String code)
			throws IllegalGameCodeException {
		this.gw = gw;
		this.name = name;
		this.prolog = prolog;
		this.epilog = epilog;
		// Set the quest code
		if (code == null || code.equals("")) {
			this.code = getNewCode();
		} else if (gw.quests.containsKey(code)) {
			throw new IllegalGameCodeException(code);
		} else {
			this.code = code;
		}
	}

	
	public String getNewCode() {
		int qCounter = 1;
		while (gw.quests.containsKey("Q"+Integer.toString(qCounter))) {
			qCounter++;
		}
		return "Q"+Integer.toString(qCounter);
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
	
	public String getEditorInfo() {
		return this.code+": "+this.name+" -Quest";
	}


	@Override
	public void remove() {
		gw.quests.remove(this);
		
	}


	@Override
	public String getSaveline() {
		/* Quest::<name>::<code>::<prolog>::<epilog> */
		return "Quest::"+this.name+"::"+this.code+"::"+this.prolog+"::"+this.epilog+"::\r";
	}
	
	/** Function for recreating a Quest from the line of text used to save it.*/
	public static Quest loadLine(String saveLine, GameWorld gw) throws CorruptedSaveLineException {
		if (saveLine.startsWith("Quest::")) {
			String[] saveLineComp = saveLine.split("::");
			Quest newQuest;
			try {
				newQuest = new Quest(gw, saveLineComp[1], saveLineComp[3], saveLineComp[4], saveLineComp[2]);
			} catch (Exception e) {
				e.printStackTrace();
				throw new CorruptedSaveLineException(saveLine);
			}
			return newQuest;
		}
		else {
			throw new CorruptedSaveLineException(saveLine);
		}
	}

}
