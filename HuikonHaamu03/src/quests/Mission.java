package quests;

import java.util.Iterator;
import java.util.LinkedList;

import gameCore.GameWorld;
import gameExceptions.CorruptedSaveLineException;
import gameExceptions.IllegalGameCodeException;

public class Mission extends UnInteractableThing{
	
	public GameWorld gw;
	
	public String name;
	public String prolog;
	public String epilog;
	public String code;
	
	public LinkedList<Condition> conditions = new LinkedList<Condition>();
	public Quest quest;
	public boolean done = false;


	public Mission(GameWorld gw, String name, String prolog, String epilog, String code, Quest quest)
			throws IllegalGameCodeException {
		this.gw = gw;
		this.name = name;
		this.prolog = prolog;
		this.epilog = epilog;
		this.quest = quest;
		quest.addMission(this);
		// Set the Mission code
		if (code == null || code.equals("")) {
			this.code = getNewCode();
		} else if (gw.missions.containsKey(code)) {
			throw new IllegalGameCodeException(code);
		} else {
			this.code = code;
		}
	}
	
	public String getNewCode() {
		int mCounter = 1;
		while (gw.missions.containsKey(quest.code+"M"+Integer.toString(mCounter))) {
			mCounter++;
		}
		return quest.code+"M"+Integer.toString(mCounter);
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
	
	public String getEditorInfo() {
		return this.code+": "+this.name+" -Mission in "+quest.code;
	}

	@Override
	public void remove() {
		// TODO not implemented
		
	}
	
	@Override
	public String getSaveline() {
		/* Mission::<name>::<code>::<prolog>::<epilog>
		 * ::<quest> */
		return "Mission::"+this.name+"::"+this.code+"::"+this.prolog+"::"+this.epilog+"::"+
				this.quest.code+"::\r";
	}
	
	/** Function for recreating a Quest from the line of text used to save it.*/
	public static Mission loadLine(String saveLine, GameWorld gw) throws CorruptedSaveLineException {
		if (saveLine.startsWith("Mission::")) {
			String[] saveLineComp = saveLine.split("::");
			Mission newMission;
			try {
				newMission = new Mission(gw, saveLineComp[1], saveLineComp[3], saveLineComp[4], saveLineComp[2],
								gw.quests.get(saveLineComp[5]));
			} catch (Exception e) {
				e.printStackTrace();
				throw new CorruptedSaveLineException(saveLine);
			}
			return newMission;
		}
		else {
			throw new CorruptedSaveLineException(saveLine);
		}
	}

}
