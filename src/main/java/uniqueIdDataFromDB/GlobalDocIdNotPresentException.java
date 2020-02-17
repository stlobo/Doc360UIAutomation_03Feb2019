package uniqueIdDataFromDB;

import org.apache.log4j.Logger;

public class GlobalDocIdNotPresentException extends Exception {
	
	
	 /**
	 * 
	 */
  private static final long serialVersionUID = 7718828512143293558L;
	
  public static Logger log = Logger.getLogger(GlobalDocIdNotPresentException.class);

	public GlobalDocIdNotPresentException() {
	        System.out.println("Global Doc ID not found");
	        log.info("Global Doc ID not found");
	    }

    public GlobalDocIdNotPresentException(Throwable cause) {
        super (cause);
    }

    public GlobalDocIdNotPresentException(String message, Throwable cause) {
        super (message, cause);
    }
    
    public GlobalDocIdNotPresentException(String message) {
		super(message);
	}
}
