package quests;

import gameObj.GameThing;
import gameObj.Room;
import gameCore.Game;
import gameCore.GameWorld;
import gameExceptions.CorruptedSaveLineException;
import gameExceptions.IllegalGameCodeException;

public class Condition extends UnInteractableThing {
	
	public enum ConditionType{
		// Actions
		Explore, Take, Open, Close, Go, Hit, Eat, Operate,
		// Non actions
		Obtain, Visit, GoTo;
	}
	
	public GameWorld gw;
	
	public ConditionType conditionType;
	public GameThing targetThing;
	public Mission mission;
	public boolean done = false;
	public String code;

	public Condition(GameWorld gw, String code, ConditionType conditionType, GameThing targetThing) throws IllegalGameCodeException {
		this.gw = gw;
		this.conditionType = conditionType;
		this.targetThing = targetThing;
		// Set the Condition code
		if (code == null || code.equals("")) {
			this.code = getNewCode();
		} else if (gw.conditions.containsKey(code)) {
			throw new IllegalGameCodeException(code);
		} else {
			this.code = code;
		}
	}
	
	public Condition(GameWorld gw, String code, ConditionType conditionType, GameThing targetThing,
					Mission mission) throws IllegalGameCodeException {
		this.gw = gw;
		this.conditionType = conditionType;
		this.targetThing = targetThing;
		// Set the Condition code
		if (code == null || code.equals("")) {
			this.code = getNewCode();
		} else if (gw.conditions.containsKey(code)) {
			throw new IllegalGameCodeException(code);
		} else {
			this.code = code;
		}
		this.mission = mission;
	}
	
	public String getNewCode() {
		int cCounter = 1;
		while (gw.conditions.containsKey("C"+Integer.toString(cCounter))) {
			cCounter++;
		}
		return "C"+Integer.toString(cCounter);
	}
	
	public boolean checkCondion() {
		if (done) {
			return true;
		}
		else if (Game.lastTarget == targetThing) {
			// Action conditions
			if (conditionType == ConditionType.Explore) {
				if (Game.lastAction == GameThing.Actions.Explore) {
					this.done = true;
					return true;
				}
			} else if (conditionType == ConditionType.Take) {
				if (Game.lastAction == GameThing.Actions.Take) {
					this.done = true;
					return true;
				}
			} else if (conditionType == ConditionType.Open) {
				if (Game.lastAction == GameThing.Actions.Open) {
					this.done = true;
					return true;
				}
			} else if (conditionType == ConditionType.Close) {
				if (Game.lastAction == GameThing.Actions.Close) {
					this.done = true;
					return true;
				}
			} else if (conditionType == ConditionType.Go) {
				if (Game.lastAction == GameThing.Actions.Go) {
					this.done = true;
					return true;
				}
			} else if (conditionType == ConditionType.Hit) {
				if (Game.lastAction == GameThing.Actions.Hit) {
					this.done = true;
					return true;
				}
			} else if (conditionType == ConditionType.Eat) {
				if (Game.lastAction == GameThing.Actions.Eat) {
					this.done = true;
					return true;
				}
			} else if (conditionType == ConditionType.Operate) {
				if (Game.lastAction == GameThing.Actions.Operate) {
					this.done = true;
					return true;
				}
			}
		}
		
		if (conditionType == ConditionType.Obtain) {
			if (gw.player.inventory.containsKey(targetThing.code)) {
				return true;
			}
		} else if (conditionType == ConditionType.Visit) {
			if ( ( (Room)targetThing ).visited) {
				this.done = true;
				return true;
			}
		} else if (conditionType == ConditionType.GoTo) {
			if ( gw.player.location == targetThing) {
				return true;
			}
		}
		return false;
	}
	
	public String getEditorInfo() {
		return this.code+": "+conditionType.toString()+"-Condition for: "+this.targetThing.code;
	}

	@Override
	public void remove() {
		// TODO not implemented
	}
	
	@Override
	public String getSaveline() {
		/* Condition::::<code>::<type>::<targetThing>::<missionCode> */
		String missionCode = "null";
		if (this.mission != null) {
			missionCode = this.mission.code;
		}
		return "Condition::"+this.code+"::"+this.conditionType+"::"+this.targetThing.code+"::"+missionCode+"::\r";
	}
	
	/** Function for recreating a Quest from the line of text used to save it.*/
	public static Condition loadLine(String saveLine, GameWorld gw) throws CorruptedSaveLineException {
		if (saveLine.startsWith("Condition::")) {
			String[] saveLineComp = saveLine.split("::");
			Condition newCondition;
			try {
				if (saveLineComp[4].equals("null")) {
					newCondition = new Condition(gw, saveLineComp[1], ConditionType.valueOf(saveLineComp[2]),
							gw.gameThings.get(saveLineComp[3]));
				}
				else {
					newCondition = new Condition(gw, saveLineComp[1], ConditionType.valueOf(saveLineComp[2]),
							gw.gameThings.get(saveLineComp[3]), gw.missions.get(saveLineComp[4]));
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new CorruptedSaveLineException(saveLine);
			}
			return newCondition;
		}
		else {
			throw new CorruptedSaveLineException(saveLine);
		}
	}

}
