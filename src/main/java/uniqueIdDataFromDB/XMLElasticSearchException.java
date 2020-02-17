package uniqueIdDataFromDB;

import org.apache.log4j.Logger;

public class XMLElasticSearchException extends Exception {
	Logger log = Logger.getLogger(UIElasticSearchException.class);
	String xmlValue;
	String UIAttributeValue;
	
	public XMLElasticSearchException(String message,String xmlValue,String UIAttributeValue) {
        super(message);
        this.xmlValue=xmlValue;
        this.UIAttributeValue=UIAttributeValue;
        
        log.error("Error  :"+message+"XML Value"+"  "+this.xmlValue+"  "+"attributesUI value"+"   "+this.UIAttributeValue);
        
    }
}
