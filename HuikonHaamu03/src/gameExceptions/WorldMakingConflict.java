package gameExceptions;

public class WorldMakingConflict extends Exception {
	
	
	public String conflictType;
	
	public WorldMakingConflict(String conflictType) {
		this.conflictType = conflictType;
	}
	
}
