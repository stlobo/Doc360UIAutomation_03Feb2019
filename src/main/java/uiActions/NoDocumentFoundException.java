package uiActions;

public class NoDocumentFoundException extends RuntimeException{
	
	String msg;
	public NoDocumentFoundException(String msg) {
		this.msg = msg;
	}
	

}
