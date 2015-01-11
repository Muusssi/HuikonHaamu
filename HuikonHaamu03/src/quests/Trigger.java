package quests;

import java.util.LinkedList;

public class Trigger extends UnInteractableThing {
	
	public LinkedList<Condition> conditions;
	
	public Trigger() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getEditorInfo() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public void remove() {
		// TODO not implemented
		
	}

	@Override
	public String getSaveline() {
		// TODO not implemented
		return null;
	}

}
