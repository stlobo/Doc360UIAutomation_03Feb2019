package testDataInsertion;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import propertyManager.ReadPropertyFile;
import uniqueIdDataFromDB.AutomationQueries;
import utilities.DBConfigPropertiesSetupQueries;

public class DBValueReader {

	public static Logger log = Logger.getLogger(DBValueReader.class);
	public static String user = ReadPropertyFile.readDBPropertiesFile("user");
	static Connection con = null;
	

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

	private static Map<Integer, List<Integer>> testDocClassAttribMap = new HashMap<>();


	public static String getMetaDataDocClassID(String DocClassName) {
		String batchID = null;
		ResultSet res1 = null;
		PreparedStatement pstmt1 = null;

		try {
			// Make the database connection
			// Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(DB_UI_URL, DB_UI_USER, DB_UI_PASSWORD);
			Class.forName(DB_UI_Driver).newInstance();
			// Set PreProcessor DBConfig properties
			pstmt1 = con.prepareStatement(AutomationQueries.GET_META_DOC_CLASS_ID);
			pstmt1.setString(1, DocClassName);
			res1 = pstmt1.executeQuery();
			res1.next();
			batchID = res1.getString(1);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return batchID;
	}

	public static void getMetaDataAttributeMap(String DocClassID) {

		ResultSet res1 = null;
		PreparedStatement pstmt1 = null;
		Map<String, List<String>> ReadMetaDataAttributeIDMap = new HashMap<String, List<String>>();
		int docClassId, docClassAttrID;
		String cntType;

		try {
			// Make the database connection
			// Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(DB_UI_URL, DB_UI_USER, DB_UI_PASSWORD);
			Class.forName(DB_UI_Driver).newInstance();
			// Set PreProcessor DBConfig properties
			pstmt1 = con.prepareStatement(AutomationQueries.GET_QA_TABLE_ATTRIBUTE_ID);
			pstmt1.setString(1, DocClassID);
			res1 = pstmt1.executeQuery();
			List<Integer> attribLst = new ArrayList<>();
			Integer tmpdocClassId = 0;

			while (res1.next()) {
				docClassId = res1.getInt(1);
				docClassAttrID = res1.getInt(2);

				attribLst.add(docClassAttrID);

				tmpdocClassId = docClassId;
			}

			testDocClassAttribMap.put(tmpdocClassId, attribLst);

		} catch (Exception e) {
			e.printStackTrace();
		}

		// Map<String, List<String>>
		// return ReadMetaDataAttributeIDMap;
	}

	public static Map<Integer, List<Integer>> getDocClassAttribMap() {
		return testDocClassAttribMap;
	}

	public static List<Map<Integer, String>> getTestAttVal(String filename, List<Integer> classAttrList)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		// PersistTestData.log("DB FILE: "+ filename);
		MetaDataXMLReader obj = new MetaDataXMLReader();
		Map<String, String> metaMap = obj.readXMLMetaDataIndxVal(filename);
		Map<Integer, String> attrIdAttrNAmeMap = new HashMap<Integer, String>();
		List<Map<Integer, String>> testDataList = new ArrayList<Map<Integer, String>>();
		String tempAtName = null, cnt = null, testDatVal = null;
		ResultSet res1 = null;
		PreparedStatement pstmt1 = null;

		// execute query to fetch attName add to list
		for (Integer at : classAttrList) {
			con = DriverManager.getConnection(DB_UI_URL, DB_UI_USER, DB_UI_PASSWORD);
			Class.forName(DB_UI_Driver).newInstance();
			cnt = at.toString();

			pstmt1 = con.prepareStatement(AutomationQueries.GET_ATTRIBUT_NAME_TAG);
			pstmt1.setString(1, cnt);
			res1 = pstmt1.executeQuery();
			res1.next();

			tempAtName = res1.getString(1);
			testDatVal = metaMap.get(tempAtName);

			attrIdAttrNAmeMap.put(at.intValue(), testDatVal);
		}
		testDataList.add(attrIdAttrNAmeMap);
		return testDataList;
	}

	public static void setUpTestDataTable(List<TestData> testDataLst) {

		PreparedStatement pstmt1 = null, pstmt2 = null;

		try {
			con = DriverManager.getConnection(DB_UI_URL, DB_UI_USER, DB_UI_PASSWORD);
			Class.forName(DB_UI_Driver).newInstance();
			// Set PreProcessor DBConfig properties

			pstmt2 = con.prepareStatement(AutomationQueries.DELETE_TESTDATA_TABLE_RECORDS);
			pstmt2.executeUpdate();

			log.info("Deleted existing records in QA_DOC_CLS_TEST_DATA table successfully");
			//log.info("-----------------------------------------------------------------------");

			/*
			 * for (TestData dbMap: testDataLst) { PersistTestData.log(dbMap); }
			 */
			for (TestData dbTestData : testDataLst) {
				pstmt1 = con.prepareStatement(AutomationQueries.INSERT_TESTDATA_TABLE_RECORDS);
				pstmt1.setString(1, String.valueOf(dbTestData.getScenarioId()));
				pstmt1.setString(2, dbTestData.getZipFile());
				pstmt1.setString(3, String.valueOf(dbTestData.getTestCaseId()));
				pstmt1.setString(4, dbTestData.getTestCaseDesc());
				pstmt1.setString(5, String.valueOf(dbTestData.getDocClassId()));
				pstmt1.setString(6, String.valueOf(dbTestData.getDocClassAttr()));
				pstmt1.setString(7, dbTestData.getCtrlType());
				pstmt1.setString(8, dbTestData.getCondition());
				pstmt1.setString(9, dbTestData.getTestData());
				
				pstmt1.executeUpdate();
			}

			
			log.info("Saved test details in QA_DOC_CLS_TEST_DATA table successfully");
			//log.info("-----------------------------------------------------------------------");

		} catch (Exception e) {
			log.error("Error occured in data insertion" + e.getMessage());
			e.printStackTrace();
		}
	}

}
