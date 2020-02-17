package utilities;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.logging.log4j.Level;

public class CreateLogs {
	static Logger log;
	
	private static Logger getLogger() throws IOException{
	    
		if(log == null){
	        new CreateLogs();
	    }
	    return log;
	}
	public static void log(Priority priority, String msg) throws IOException{
	    getLogger().log(priority, msg);
	    System.out.println(msg);
	}
}
