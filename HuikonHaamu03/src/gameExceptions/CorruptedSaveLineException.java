package gameExceptions;

public class CorruptedSaveLineException extends Exception {

	private static final long serialVersionUID = 1L;
	public String corruptedLine;
	public boolean notHHfile;
	
	public CorruptedSaveLineException(String corruptedLine, boolean notHHfile) {
		this.corruptedLine = corruptedLine;
		this.notHHfile = notHHfile;
		System.out.println("Line---->"+corruptedLine);
	}
	
	public CorruptedSaveLineException(String corruptedLine) {
		this.corruptedLine = corruptedLine;
		this.notHHfile = false;
		System.out.println("Line---->"+corruptedLine);
	}


}
