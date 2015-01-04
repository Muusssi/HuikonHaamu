package quests;

import gameObj.GameThing;
import gameObj.Room;
import gameCore.Game;
import gameCore.GameWorld;

public class Condition {
	
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

	public Condition(GameWorld gw, ConditionType conditionType, GameThing targetThing, Mission mission) {
		this.gw = gw;
		this.conditionType = conditionType;
		this.targetThing = targetThing;
		this.mission = mission;
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
		} else {
			System.out.println("This condition type is not implemented!");
		}
		return false;
	}

}
