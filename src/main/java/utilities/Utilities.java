package utilities;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import propertyManager.ReadPropertyFile;
import uniqueIdDataFromDB.DocClassAutomation;

public class Utilities {

	// ReadPropertyFile prop;
	/*
	 * public final static String elasticSearchURL = "elasticSearchURL"; public
	 * final static String searchURL = "searchURL"; public final static String
	 * testMode = "testMode";
	 * 
	 * public static String DB_URL =
	 * ReadPropertyFile.readDBPropertiesFile("db_url1"); public static String
	 * DB_USER = ReadPropertyFile.readDBPropertiesFile("db_user1"); public static
	 * String DB_PASSWORD = ReadPropertyFile.readDBPropertiesFile("db_password1");
	 * public static String DB_URL2 =
	 * ReadPropertyFile.readDBPropertiesFile("db_url2"); public static String
	 * DB_USER2 = ReadPropertyFile.readDBPropertiesFile("db_user2"); public static
	 * String DB_PASSWORD2 = ReadPropertyFile.readDBPropertiesFile("db_password2");
	 * public static String DB_URL3 =
	 * ReadPropertyFile.readDBPropertiesFile("db_url3"); public static String
	 * DB_USER3 = ReadPropertyFile.readDBPropertiesFile("db_user3"); public static
	 * String DB_PASSWORD3 = ReadPropertyFile.readDBPropertiesFile("db_password3");
	 * private static PreparedStatement pstmt = null;
	 */

	// Read from DB properties
	static Connection con = null;
	static Logger log = Logger.getLogger(Utilities.class);
	public static String BaseURL = null;
	public static String filePath = null;
	public static String filePathZip = null;
	public static String webDriverPath = null;
	public static String IngestedDocDownload = null;
	public static String elasticSearchURLSetup = null;
	public static String searchURLSetup = null;
	public static String testMode = null;
	public static String regressionCountVal = null;
	public static String redis_server_exe = null;
	public static String redis_server_path = null;
	public static boolean compareUIandES;
	public static boolean compareESandMetaData;
	public static boolean testDownload;

	public static Utilities seleniumDriver;
	private static WebDriver driver;
	public static WebDriverWait waitDriver;
	public static String user = ReadPropertyFile.readDBPropertiesFile("user");

	public final static int TIMEOUT = 30;
	public final static int PAGE_LOAD_TIMEOUT = 50;

	// Constant for UI Database
	public static String DB_UI_Driver = ReadPropertyFile.readDBPropertiesFile("db_ui_driver");
	public static String DB_UI_URL = ReadPropertyFile.readDBPropertiesFile("db_ui_url");
	public static String DB_UI_USER = ReadPropertyFile.readDBPropertiesFile("db_ui_user");
	public static String DB_UI_PASSWORD = ReadPropertyFile.readDBPropertiesFile("db_ui_password");

	// Constant for PreProcessor Database
	public static String DB_PreProcessor_Driver = null;
	public static String DB_PreProcessor_URL = null;
	public static String DB_PreProcessor_USER = null;
	public static String DB_PreProcessor_PASSWORD = null;

	// Constant for RIO Database
	public static String DB_RIO_Driver = null;
	public static String DB_RIO_URL = null;
	public static String DB_RIO_USER = null;
	public static String DB_RIO_PASSWORD = null;

	// call setUpDriver method to revoke the constructor
	private Utilities() throws IOException, ParseException {

		// System.setProperty("webdriver.chrome.driver",
		// "C:\\ProgramData\\Chrome_driver_78.0.3904.70\\chromedriver.exe");
		// String path = System.getProperty("user.dir");
		// String IngestedDocDownloadPath = path + Utilities.IngestedDocDownload;
		// ReadPropertyFile.readPropertiesFile("IngestedDocDownload");

	
		
		System.setProperty("webdriver.chrome.driver", webDriverPath);
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("useAutomationExtension", false);
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", IngestedDocDownload);
		options.setExperimentalOption("prefs", chromePrefs);
		driver = new ChromeDriver(options);
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	// invoked for driver
	public static WebDriver getDriver() {
		return driver;
	}

	// invoked on setup start
	public static void setUpDriver() throws IOException, ParseException {
		if (seleniumDriver == null) {
			seleniumDriver = new Utilities();
		}
	}

	/*
	 * //invoked on navigating to URL public static void openPage(String url) {
	 * System.out.println(url); System.out.println(driver); driver.get(url); }
	 */

	// invoked for implementing waits
	public static void waitMethod() {
		waitDriver = new WebDriverWait(driver, TIMEOUT);
		driver.manage().timeouts().implicitlyWait(TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);

	}

	/*
	 * //DB Connection public static Statement dbSetUp() throws Exception { try{ //
	 * Make the database connection String dbClass =
	 * ReadPropertyFile.readPropertiesFile("mysqldriver"); // String dbClass =
	 * "com.mysql.cj.jdbc.Driver"; Class.forName(dbClass).newInstance(); // Get
	 * connection to DB Connection con = DriverManager.getConnection(DB_URL,
	 * DB_USER, DB_PASSWORD);
	 * 
	 * // Statement object to send the SQL statement to the Database
	 * 
	 * stmt = con.createStatement(); } catch (Exception e){ e.printStackTrace(); }
	 * return stmt; }
	 */

	public static PreparedStatement dbSetUp(String sql) throws Exception {

		PreparedStatement pstmt1 = null;

		try {
			// Make the database connection
			// String dbClass = ReadPropertyFile.readDBPropertiesFile("mysqldriver");

			String dbClass = ReadPropertyFile.readDBPropertiesFile("db_ui_driver");
			Class.forName(dbClass).newInstance();

			// Get connection to DB
			// Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			con = DriverManager.getConnection(DB_UI_URL, DB_UI_USER, DB_UI_PASSWORD);

			// Statement object to send the SQL statement to the Database
			// pstmt = con.prepareStatement(sql);
			pstmt1 = con.prepareStatement(sql);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return pstmt1;
	}

	public static PreparedStatement dbSetUp2(String sql) throws Exception {
		PreparedStatement pstmt2 = null;
		ResultSet res2 = null;
		try {
			// Make the database connection
			/*
			 * String dbClass = ReadPropertyFile.readDBPropertiesFile("postgresSQLdriver");
			 * Class.forName(dbClass).newInstance();
			 */

			Class.forName(DB_PreProcessor_Driver).newInstance();

			// Get connection to DB
			// Connection con = DriverManager.getConnection(DB_URL2, DB_USER2,
			// DB_PASSWORD2);
			con = DriverManager.getConnection(DB_PreProcessor_URL, DB_PreProcessor_USER, DB_PreProcessor_PASSWORD);

			// Statement object to send the SQL statement to the Database
			pstmt2 = con.prepareStatement(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pstmt2;
	}

	public static PreparedStatement dbSetUp3(String sql) throws Exception {
		PreparedStatement pstmt3 = null;
		try {
			// Make the database connection

			/*
			 * String dbClass = ReadPropertyFile.readDBPropertiesFile("mysqldriver");
			 * Class.forName(dbClass).newInstance();
			 */

			Class.forName(DB_RIO_Driver).newInstance();

			// Get connection to DB
			// Connection con = DriverManager.getConnection(DB_URL3, DB_USER3,
			// DB_PASSWORD3);
			con = DriverManager.getConnection(DB_RIO_URL, DB_RIO_USER, DB_RIO_PASSWORD);

			// Statement object to send the SQL statement to the Database
			pstmt3 = con.prepareStatement(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pstmt3;
	}

	public static void setupDBConfigValues() {

		ResultSet res4 = null, res5 = null, res6 = null, res7 = null, res8 = null, res9 = null, res10 = null,
				res11 = null;
		PreparedStatement pstmt4 = null, pstmt5 = null, pstmt6 = null, pstmt7 = null, pstmt8 = null, pstmt9 = null,
				pstmt10 = null, pstmt11 = null;

		try {
			// Make the database connection

			con = DriverManager.getConnection(DB_UI_URL, DB_UI_USER, DB_UI_PASSWORD);
			Class.forName(DB_UI_Driver).newInstance();

			// Set PreProcessor DBConfig properties
			pstmt4 = con.prepareStatement(DBConfigPropertiesSetupQueries.DB_PreProcessor_Driver_query);
			pstmt4.setString(1, DB_UI_USER);
			res4 = pstmt4.executeQuery();
			res4.next();
			DB_PreProcessor_Driver = res4.getString(1);

			pstmt5 = con.prepareStatement(DBConfigPropertiesSetupQueries.db_pre_processor_url_query);
			pstmt5.setString(1, DB_UI_USER);
			res5 = pstmt5.executeQuery();
			res5.next();
			DB_PreProcessor_URL = res5.getString(1);

			pstmt6 = con.prepareStatement(DBConfigPropertiesSetupQueries.db_pre_processor_user_query);
			pstmt6.setString(1, DB_UI_USER);
			res6 = pstmt6.executeQuery();
			res6.next();
			DB_PreProcessor_USER = res6.getString(1);

			pstmt7 = con.prepareStatement(DBConfigPropertiesSetupQueries.db_pre_processor_password_query);
			pstmt7.setString(1, DB_UI_USER);
			res7 = pstmt7.executeQuery();
			res7.next();
			DB_PreProcessor_PASSWORD = res7.getString(1);

			// Set RIO DBConfig properties
			pstmt8 = con.prepareStatement(DBConfigPropertiesSetupQueries.DB_RIO_Driver_query);
			pstmt8.setString(1, DB_UI_USER);
			res8 = pstmt8.executeQuery();
			res8.next();
			DB_RIO_Driver = res8.getString(1);

			pstmt9 = con.prepareStatement(DBConfigPropertiesSetupQueries.db_rio_url_query);
			pstmt9.setString(1, DB_UI_USER);
			res9 = pstmt9.executeQuery();
			res9.next();
			DB_RIO_URL = res9.getString(1);

			pstmt10 = con.prepareStatement(DBConfigPropertiesSetupQueries.db_rio_user_query);
			pstmt10.setString(1, DB_UI_USER);
			res10 = pstmt10.executeQuery();
			res10.next();
			DB_RIO_USER = res10.getString(1);

			pstmt11 = con.prepareStatement(DBConfigPropertiesSetupQueries.db_rio_password_query);
			pstmt11.setString(1, DB_UI_USER);
			res11 = pstmt11.executeQuery();
			res11.next();
			DB_RIO_PASSWORD = res11.getString(1);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void setupPropConfigValues() {

		ResultSet res4 = null, res5 = null, res6 = null, res7 = null, res8 = null, res9 = null, res10 = null,
				res11 = null, res12 = null, res13 = null, res14 = null, res15 = null, res16 = null, res17 = null;

		PreparedStatement pstmt4 = null, pstmt5 = null, pstmt6 = null, pstmt7 = null, pstmt8 = null, pstmt9 = null,
				pstmt10 = null, pstmt11 = null, pstmt12 = null, pstmt13 = null, pstmt14 = null, pstmt15 = null,
				pstmt16 = null, pstmt17 = null;

		try {
			// Make the database connection

			con = DriverManager.getConnection(DB_UI_URL, DB_UI_USER, DB_UI_PASSWORD);
			Class.forName(DB_UI_Driver).newInstance();

			// Set BaseURL configuration properties
			pstmt4 = con.prepareStatement(ConfigPropertiesSetupQueries.BaseURL_query);
			pstmt4.setString(1, DB_UI_USER);
			res4 = pstmt4.executeQuery();
			res4.next();
			BaseURL = res4.getString(1);

			pstmt5 = con.prepareStatement(ConfigPropertiesSetupQueries.filePath_query);
			pstmt5.setString(1, DB_UI_USER);
			res5 = pstmt5.executeQuery();
			res5.next();
			filePath = res5.getString(1);

			pstmt6 = con.prepareStatement(ConfigPropertiesSetupQueries.zipFilePath_query);
			pstmt6.setString(1, DB_UI_USER);
			res6 = pstmt6.executeQuery();
			res6.next();
			filePathZip = res6.getString(1);

			pstmt7 = con.prepareStatement(ConfigPropertiesSetupQueries.webDriverPath_query);
			pstmt7.setString(1, DB_UI_USER);
			res7 = pstmt7.executeQuery();
			res7.next();
			webDriverPath = res7.getString(1);

			// Set RIO DBConfig properties
			pstmt8 = con.prepareStatement(ConfigPropertiesSetupQueries.IngestedDocDownload_query);
			pstmt8.setString(1, DB_UI_USER);
			res8 = pstmt8.executeQuery();
			res8.next();
			IngestedDocDownload = res8.getString(1);

			pstmt9 = con.prepareStatement(ConfigPropertiesSetupQueries.elasticSearchURL_query);
			pstmt9.setString(1, DB_UI_USER);
			res9 = pstmt9.executeQuery();
			res9.next();
			elasticSearchURLSetup = res9.getString(1);

			pstmt10 = con.prepareStatement(ConfigPropertiesSetupQueries.searchURL_query);
			pstmt10.setString(1, DB_UI_USER);
			res10 = pstmt10.executeQuery();
			res10.next();
			searchURLSetup = res10.getString(1);

			pstmt11 = con.prepareStatement(ConfigPropertiesSetupQueries.testMode_query);
			pstmt11.setString(1, DB_UI_USER);
			res11 = pstmt11.executeQuery();
			res11.next();
			testMode = res11.getString(1);

			pstmt12 = con.prepareStatement(ConfigPropertiesSetupQueries.regressionCount_query);
			pstmt12.setString(1, DB_UI_USER);
			res12 = pstmt12.executeQuery();
			res12.next();
			regressionCountVal = res12.getString(1);

			pstmt13 = con.prepareStatement(ConfigPropertiesSetupQueries.redis_server_path_query);
			pstmt13.setString(1, DB_UI_USER);
			res13 = pstmt13.executeQuery();
			res13.next();
			redis_server_path = res13.getString(1);

			pstmt14 = con.prepareStatement(ConfigPropertiesSetupQueries.redis_server_exe_query);
			pstmt14.setString(1, DB_UI_USER);
			res14 = pstmt14.executeQuery();
			res14.next();
			redis_server_exe = res14.getString(1);

			pstmt15 = con.prepareStatement(ConfigPropertiesSetupQueries.compareUIandES_query);
			pstmt15.setString(1, DB_UI_USER);
			res15 = pstmt15.executeQuery();
			res15.next();
			compareUIandES = Boolean.parseBoolean(res15.getString(1));

			pstmt16 = con.prepareStatement(ConfigPropertiesSetupQueries.compareESandMetaData_query);
			pstmt16.setString(1, DB_UI_USER);
			res16 = pstmt16.executeQuery();
			res16.next();
			compareESandMetaData = Boolean.parseBoolean(res16.getString(1));

			pstmt17 = con.prepareStatement(ConfigPropertiesSetupQueries.testDownload_query);
			pstmt17.setString(1, DB_UI_USER);
			res17 = pstmt17.executeQuery();
			res17.next();
			testDownload = Boolean.parseBoolean(res17.getString(1));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// invoked on navigating to URL
	public static void openPage() throws Throwable {

		/*
		 * String url = ReadPropertyFile.readPropertiesFile("BaseURL");
		 * Utilities.getDriver().get(url);
		 */

		Utilities.getDriver().get(BaseURL);
		Utilities.waitMethod();
		log.info("***** Launching Doc360 Application URL: " + BaseURL + "******");
		/* System.out.println("Launched this\t" + url); */
	}

	// invoked at ending
	public static void tearDown() throws Throwable {
		if (driver != null) {
			driver.close();
			driver.quit();
		}
		seleniumDriver = null;

		if (con != null) {
			con.close();
		}

	}
}
