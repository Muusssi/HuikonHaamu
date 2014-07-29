package gameExceptions;

public class CorruptedSaveLineException extends Exception {

	private static final long serialVersionUID = 1L;
	public String corruptedLine;
	
	public CorruptedSaveLineException(String corruptedLine) {
		this.corruptedLine = corruptedLine;
	}


}
