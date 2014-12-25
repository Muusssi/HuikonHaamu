package quests;

import gameObj.GameThing;
import gameObj.Room;
import gameCore.GameWorld;

public class Condition {
	
	public enum ConditionType{
		Explore, Take, Put, Open, Close, Go, Hit, Eat,
		Obtain, Visit;
	}
	
	public GameWorld gw;
	
	public ConditionType conditionType;
	public GameThing targetThing;
	public Mission mission;
	public boolean done = false;

	public Condition(GameWorld gw, ConditionType conditionType, GameThing targetThing, Mission mission) {
		this.gw = gw;
		this.conditionType = conditionType;
		this.targetThing = targetThing;
		this.mission = mission;
	}
	
	public boolean checkCondion() {
		if (done) {
			return true;
		} else if (conditionType == ConditionType.Obtain) {
			if (gw.player.inventory.containsKey(targetThing.code)) {
				return true;
			}
		} else if (conditionType == ConditionType.Visit) {
			if ( ( (Room)targetThing ).visited) {
				this.done = true;
				return true;
			}
		} else {
			System.out.println("This condition type is not implemented!");
		}
		return false;
	}

}
