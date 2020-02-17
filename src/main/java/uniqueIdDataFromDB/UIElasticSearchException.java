package uniqueIdDataFromDB;

import org.apache.log4j.Logger;

public class UIElasticSearchException extends Exception {
	Logger log = Logger.getLogger(UIElasticSearchException.class);
	String attributeKey;
	String attributeValue;
	String eOBFromElastic;
	public UIElasticSearchException(String message,String attributeKey,String attributeValue,String eOBFromElastic) {
        super(message);
        this.attributeKey=attributeKey;
        this.attributeValue=attributeValue;
        this.eOBFromElastic=eOBFromElastic;
        //log.error("Error :"+message+"attributesUI key"+"  "+this.attributeKey+"  "+"attributesUI value"+"   "+this.attributeValue+"  "+"ElasticSearchvalue"+"   "+eOBFromElastic);
        
    }
}
