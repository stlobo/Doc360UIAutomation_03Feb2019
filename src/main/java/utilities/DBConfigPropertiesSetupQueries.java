package utilities;

/**
 * @author slobo2
 *
 */
public class DBConfigPropertiesSetupQueries {

	public final static String DB_RIO_Driver_query = "select `qa_ config _value` from d3ui02.qa_config_properties\r\n"
			+"where `qa_ config _key` = 'mysqldriver' and `qa_ config _user`=?";
	
	public final static String DB_PreProcessor_Driver_query = "select `qa_ config _value` from d3ui02.qa_config_properties\r\n" + 
			"where `qa_ config _key` = 'postgresSQLdriver' and `qa_ config _user`=?";
	
	
	public final static String db_pre_processor_url_query = "select `qa_ config _value` from d3ui02.qa_config_properties\r\n" + 
			"where `qa_ config _key` = 'db_pre_processor_url' and `qa_ config _user`=?";
	
	public final static String db_pre_processor_user_query = "select `qa_ config _value` from d3ui02.qa_config_properties\r\n" + 
			"where `qa_ config _key` = 'db_pre_processor_user' and `qa_ config _user`=?";
	
	public final static String db_pre_processor_password_query ="select `qa_ config _value` from d3ui02.qa_config_properties\r\n" + 
			"where `qa_ config _key` = 'db_pre_processor_password' and `qa_ config _user`=?";
	
	
	public final static String db_rio_url_query = "select `qa_ config _value` from d3ui02.qa_config_properties\r\n" + 
			"where `qa_ config _key` = 'db_rio_url' and `qa_ config _user`=?";
	
	public final static String db_rio_user_query = "select `qa_ config _value` from d3ui02.qa_config_properties\r\n" + 
			"where `qa_ config _key` = 'db_rio_user' and `qa_ config _user`=?";
	
	public final static String db_rio_password_query ="select `qa_ config _value` from d3ui02.qa_config_properties\r\n" + 
			"where `qa_ config _key` = 'db_rio_password' and `qa_ config _user`=?";
	
	
}
