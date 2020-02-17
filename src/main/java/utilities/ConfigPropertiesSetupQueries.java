package utilities;

/**
 * @author slobo2
 *
 */

public class ConfigPropertiesSetupQueries {
	
	public final static String BaseURL_query = "select `qa_ config _value` from d3ui02.qa_config_properties\r\n" + 
			"where `qa_ config _key` = 'BaseURL' and `qa_ config _user`=?";
	
	public final static String filePath_query = "select `qa_ config _value` from d3ui02.qa_config_properties\r\n" + 
			"where `qa_ config _key` = 'filePath' and `qa_ config _user`=?";
	
	public final static String zipFilePath_query ="select `qa_ config _value` from d3ui02.qa_config_properties\r\n" + 
			"where `qa_ config _key` = 'zipFilePath' and `qa_ config _user`=?";
	
	
	public final static String webDriverPath_query = "select `qa_ config _value` from d3ui02.qa_config_properties\r\n" + 
			"where `qa_ config _key` = 'webDriverPath' and `qa_ config _user`=?";
	
	public final static String IngestedDocDownload_query = "select `qa_ config _value` from d3ui02.qa_config_properties\r\n" + 
			"where `qa_ config _key` = 'IngestedDocDownload' and `qa_ config _user`=?";
	
	public final static String elasticSearchURL_query ="select `qa_ config _value` from d3ui02.qa_config_properties\r\n" + 
			"where `qa_ config _key` = 'elasticSearchURL' and `qa_ config _user`=?";
	
	
	public final static String searchURL_query = "select `qa_ config _value` from d3ui02.qa_config_properties\r\n" + 
			"where `qa_ config _key` = 'searchURL' and `qa_ config _user`=?";
	
	public final static String testMode_query = "select `qa_ config _value` from d3ui02.qa_config_properties\r\n" + 
			"where `qa_ config _key` = 'testMode' and `qa_ config _user`=?";
	
	public final static String regressionCount_query ="select `qa_ config _value` from d3ui02.qa_config_properties\r\n" + 
			"where `qa_ config _key` = 'regressionCount' and `qa_ config _user`=?";
	
	public final static String redis_server_exe_query = "select `qa_ config _value` from d3ui02.qa_config_properties\r\n" + 
			"where `qa_ config _key` = 'redis_server_exe' and `qa_ config _user`=?";
	
	public final static String redis_server_path_query ="select `qa_ config _value` from d3ui02.qa_config_properties\r\n" + 
			"where `qa_ config _key` = 'redis_server_path' and `qa_ config _user`=?";
	
	public final static String compareUIandES_query = "select `qa_ config _value` from d3ui02.qa_config_properties\r\n" + 
			"where `qa_ config _key` ='compareUIandES' and `qa_ config _user`=?";
	
	public final static String compareESandMetaData_query = "select `qa_ config _value` from d3ui02.qa_config_properties\r\n" + 
			"where `qa_ config _key` ='compareESandMetaData' and `qa_ config _user`=?";
	
	public final static String testDownload_query = "select `qa_ config _value` from d3ui02.qa_config_properties\r\n" + 
			"where `qa_ config _key` ='testDownload' and `qa_ config _user`=?";
	
}
