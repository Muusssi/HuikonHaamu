package gameExceptions;

public class IllegalGameCodeException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private String illegalCode;
	
	public IllegalGameCodeException(String illegalCode) {
		this.illegalCode = illegalCode;
	}
	public String illegalCode() {
		return this.illegalCode;
	}
}
