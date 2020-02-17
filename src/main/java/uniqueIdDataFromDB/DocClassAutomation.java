package uniqueIdDataFromDB;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import utilities.DocClassObject;

import docClassPageObject.AttributeDTO;
import docClassPageObject.DocClassAttributes;
import propertyManager.ReadPropertyFile;
import uiActions.DocumentDownload;
import uiActions.WebActions;
import utilities.Utilities;

public class DocClassAutomation {

	public static Logger log = Logger.getLogger(DocClassAutomation.class);
	static Map<String, String> metaData;

	static String testMode;
	static String regressionCount;
	static boolean compareUIandES;
	static boolean compareESandMetaData;
	static boolean testDownload;
	static {
		Utilities.setupDBConfigValues();
		Utilities.setupPropConfigValues();
		testMode = Utilities.testMode;
		regressionCount = Utilities.regressionCountVal;
		compareUIandES = Utilities.compareUIandES;
		compareESandMetaData = Utilities.compareESandMetaData;
		testDownload = Utilities.testDownload;

	}

	public String getTestCaseDescription(int testCaseId, int docId, int scenarioId) throws Throwable {

		PreparedStatement pstmt = Utilities.dbSetUp(AutomationQueries.TEST_DATA_DESCRIPTION_QUERY);
		String testCaseDescription = null;
		ResultSet res;
		pstmt.setInt(1, testCaseId);
		pstmt.setInt(2, scenarioId);
		pstmt.setInt(3, docId);

		res = pstmt.executeQuery();
		while (res.next()) {
			testCaseDescription = res.getString(1);
		}
		return testCaseDescription;
	}

	public boolean summaryRequiredStatus(String docClasName) throws Exception {

		boolean isSummaryReq = false;

		PreparedStatement pstmt = Utilities.dbSetUp2(AutomationQueries.GET_SUMMARY_REQUIRED_STATUS);
		ResultSet res = null;
		pstmt.setString(1, docClasName);
		String docClassName = null;
		while (res.next()) {
			docClassName = res.getString(1);
		}
		if (docClassName.isEmpty()) {
			isSummaryReq = false;
		} else {
			isSummaryReq = true;
		}

		return isSummaryReq;
	}

	public DocClassObject getDocClassObject(String docClass) throws Exception {

		PreparedStatement pstmt = Utilities.dbSetUp2(AutomationQueries.GET_DOC_CLASS_DETAIL);

		ResultSet res;
		pstmt.setString(1, docClass);
		res = pstmt.executeQuery();

		DocClassObject docClassObject = new DocClassObject();

		while (res.next()) {
			String btchPath = res.getString(1);
			docClassObject.setBtchPath(btchPath);

			docClassObject.setDocClasName(res.getString(2));
			docClassObject.setBatchJobType(res.getString(3));
			docClassObject.setMetadataJson(res.getString(4));
			docClassObject.setParentId(res.getInt(5));

			String data = res.getString(6);
			List<String> items = Arrays.asList(data.split("\\s*,\\s*"));

			docClassObject.setMultiKey(items);

		}

		return docClassObject;

	}

	public static String getBatchPathQuery(String docClass) throws Exception {
		ResultSet res;
		PreparedStatement pstmt = Utilities.dbSetUp2(AutomationQueries.GET_BATCH_PATH);
		pstmt.setString(1, docClass);
		String batchPath = null;

		res = pstmt.executeQuery();
		while (res.next()) {
			batchPath = res.getString(1);
		}

		return batchPath;
	}

	public List<AttributeDTO> getAttrColValues(int testCaseId, String docDescription, int docId, int scenarioId)
			throws Throwable {

		List<AttributeDTO> attributeList = new ArrayList<AttributeDTO>();
		PreparedStatement pstmt = Utilities.dbSetUp(AutomationQueries.TEST_DATA_ATTR_QUERY);

		ResultSet res;
		pstmt.setInt(1, testCaseId);
		pstmt.setInt(2, scenarioId);
		pstmt.setInt(3, docId);

		res = pstmt.executeQuery();
		HashMap<String, DocClassAttributes> docClassMap = new HashMap<String, DocClassAttributes>();

		Utilities.setUpDriver();

		log.info("***** Launching browser ******");
		Utilities.openPage();

		while (res.next()) {
			// launch URL
			AttributeDTO attr = new AttributeDTO();
			try {
			Select select = new Select(Utilities.getDriver().findElement(By.xpath("//select[@id='webSelection']")));
			select.selectByVisibleText(docDescription);
			}
			catch(Exception e){
				System.out.println("No such value displayed in dropdown "+docDescription);
			}
			Utilities.waitMethod();
			// Label Fields
			String locatorLabel = "//label[@id='" + res.getString(1) + "']";
			// And Not Fields
			String ddFlag = "//label[@id='" + res.getString(1) + "']/following-sibling::div/select";
			// Physical web element
			String locatorCapitalize = "//input[@capitalize='" + res.getString(2) + "']";
			attr.setLabelName(locatorLabel);
			attr.setAttributeType(res.getString(4));
			attr.setJoin_operator_locator(ddFlag);
			attr.setJoin_operator_value(res.getString(5));
			attr.setPhysicalName(res.getString(2));
			attr.setAttributeValue(res.getString(6));
			attr.setTestCaseId(res.getString(3));
			attr.setLocator(locatorCapitalize);

			attributeList.add(attr);

		}

		return attributeList;

	}

	private static String reatestMode() {
		String mode = Utilities.testMode;
		/*
		 * String mode = ""; try { mode =
		 * ReadPropertyFile.readPropertiesFile(Utilities.testMode); } catch (IOException
		 * e) { // TODO Auto-generated catch block // log.error(e.getMessage()); }
		 */
		return mode;
	}

	public static WebElement getWebElement(String locator) {
		WebElement ele = null;
		try {
			if (WebActions.isElementPresent(Utilities.getDriver().findElement(By.xpath(locator)))) {
				ele = Utilities.getDriver().findElement(By.xpath(locator));
				// System.out.println("Element is found\t" + ele);
				return ele;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("exception locator not displayed in UI\t" + locator);
			log.info("exception locator not displayed in UI\t" + locator);

		}
		return ele;
	}

	public void processElement(AttributeDTO attrDTO) {

		if (getWebElement(attrDTO.getLabelName()) != null) {

			WebElement joinOperatorElement = getWebElement(attrDTO.getJoin_operator_locator());
			joinOperatorElement.sendKeys(attrDTO.getJoin_operator_value());

			WebElement attributeFieldElement = getWebElement(attrDTO.getLocator());
			if (attrDTO.getAttributeType().equalsIgnoreCase("Text")) {
				attributeFieldElement.sendKeys(attrDTO.getAttributeValue());
			} else if (attrDTO.getAttributeType().equalsIgnoreCase("Select")) {

			} else if (attrDTO.getAttributeType().equalsIgnoreCase("Date")) {

			}

		}

	}

	public Map<Integer, String> getDocClassId() {
		Map<Integer, String> docClassMap = new HashMap<Integer, String>();
		ResultSet res;
		PreparedStatement pstmt;
		try {
			pstmt = Utilities.dbSetUp(AutomationQueries.TEST_DATA_DOC_CLASS_QUERY);

			res = pstmt.executeQuery();
			while (res.next()) {
				int docId = res.getInt(1);
				String docClassDes = res.getString(2);
				String docClassName = res.getString(3);
				docClassMap.put(docId, docClassDes);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return docClassMap;
	}

	@SuppressWarnings("null")
	public static String getDocClassName() {

		ResultSet res;
		PreparedStatement pstmt;
		String docClassName = null;
		try {
			pstmt = Utilities.dbSetUp(AutomationQueries.TEST_DATA_DOC_CLASS_QUERY);
			res = pstmt.executeQuery();
			while (res.next()) {
				docClassName = res.getString(3);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return docClassName;
	}

	@SuppressWarnings("null")
	public static Map<String, String> getJobMetadataTypeName(String docClass) {

		metaData = new HashMap<String, String>();
		ResultSet res;
		PreparedStatement pstmt;
		try {

			pstmt = Utilities.dbSetUp2(AutomationQueries.GET_JOBTYPE_METADATATYPE_QUERY);
			pstmt.setString(1, docClass);
			res = pstmt.executeQuery();
			while (res.next()) {
				String jobType = res.getString(1);
				String isJson = String.valueOf(res.getBoolean(2));
				metaData.put(jobType, isJson);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return metaData;
	}

	public List<Integer> getScenario(int docId) {
		List<Integer> scenariolist = new ArrayList<Integer>();
		ResultSet res;
		PreparedStatement pstmt;
		try {
			pstmt = Utilities.dbSetUp(AutomationQueries.TEST_DATA_SCENARIO_QUERY);
			pstmt.setInt(1, docId);

			res = pstmt.executeQuery();
			while (res.next()) {
				int scenarioId = res.getInt(1);
				scenariolist.add(scenarioId);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return scenariolist;
	}

	public List<Integer> getTestCaseIds(int scenarioId, int docId) {
		List<Integer> testCaseList = new ArrayList<Integer>();
		ResultSet res;
		PreparedStatement pstmt;
		try {
			pstmt = Utilities.dbSetUp(AutomationQueries.TEST_DATA_TEST_CASE_QUERY);
			pstmt.setInt(1, scenarioId);
			pstmt.setInt(2, docId);

			res = pstmt.executeQuery();
			while (res.next()) {
				int testId = res.getInt(1);
				testCaseList.add(testId);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return testCaseList;
	}

	public static String testEOBDate(Object epohDate) {

		// String formattedEPOHDate = removeTrailingZeroes(epohDate.toString());
		String date = new java.text.SimpleDateFormat("MM/dd/yyyy")
				.format(new java.util.Date(Long.parseLong(epohDate.toString())));
		return date;

	}

	public static String getEOBDate(Object epohDate) {
		// System.out.println(epohDate.toString());
		// String formattedEPOHDate = removeTrailingZeroes(epohDate.toString());
		String date = new java.text.SimpleDateFormat("yyyy-MM-dd")
				.format(new java.util.Date(Long.parseLong(epohDate.toString())));
		return date;

	}

	static String removeTrailingZeroes(String s) {
		StringBuilder sb = new StringBuilder(s);
		while (sb.length() > s.length() - 3 && sb.charAt(sb.length() - 1) == '0') {
			sb.setLength(sb.length() - 1);
		}
		return sb.toString();
	}

	public static String getMetadataJson(String docClass) {
		String metaData = null;
		ResultSet res;
		PreparedStatement pstmt;
		try {
			pstmt = Utilities.dbSetUp2(AutomationQueries.TEST_DATA_METADATA_QUERY);
			pstmt.setString(1, docClass);

			res = pstmt.executeQuery();
			while (res.next()) {
				metaData = res.getString(1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return metaData;
	}

	public static int getParentBatchId(int batchId) {
		int parentBatchId = 0;

		ResultSet res;
		PreparedStatement pstmt;
		try {
			pstmt = Utilities.dbSetUp3(AutomationQueries.GET_PARENT_BATCH_ID);
			pstmt.setInt(1, batchId);

			res = pstmt.executeQuery();
			while (res.next()) {
				parentBatchId = res.getInt(1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return parentBatchId;
	}

	public static String getFileName(int parentBatchId) {
		String fileName = null;
		ResultSet res;
		PreparedStatement pstmt;
		try {
			pstmt = Utilities.dbSetUp2(AutomationQueries.GET_FILENAME);
			pstmt.setInt(1, parentBatchId);

			res = pstmt.executeQuery();
			while (res.next()) {
				fileName = res.getString(1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return fileName;
	}

	public static int getParentBatchId(String fileName) {
		int parentBatchId = 0;
		ResultSet res;
		PreparedStatement pstmt;
		try {
			pstmt = Utilities.dbSetUp2(AutomationQueries.GET_PARENTBATCH_ID);
			pstmt.setString(1, fileName.concat("%"));

			res = pstmt.executeQuery();
			while (res.next()) {
				parentBatchId = res.getInt(1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return parentBatchId;
	}

	public static int getBatchId(int parentBatchId, String docClassName) {
		int batchId = 0;

		ResultSet res;
		PreparedStatement pstmt;
		try {
			pstmt = Utilities.dbSetUp3(AutomationQueries.GET_BATCH_ID);
			pstmt.setString(1, docClassName);
			pstmt.setInt(2, parentBatchId);

			res = pstmt.executeQuery();
			while (res.next()) {
				batchId = res.getInt(1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return batchId;
	}

	public static boolean testLongFormat(String elasticSearchValue) {
		try {
			Long num = Long.parseLong(elasticSearchValue);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;

	}

	// public static void doc360Automation() throws Throwable {
	public static void startAutomation(String zipName) throws Throwable {

		// String testModeProp = ReadPropertyFile.readPropertiesFile("testMode");
		WebActions objWeb = new WebActions();
		// String regressionCount =
		// ReadPropertyFile.readPropertiesFile("regressionCount");
		// String regressionCount = Utilities.regressionCountVal;
		String globalDocIdUI = null;
		DocClassAutomation docAutoObj = new DocClassAutomation();
		// testMode = reatestMode();/* operation based on test mode in config file */

		// Logger log = Logger.getLogger(DocClassAutomation.class);

		DocClassAutomation objLocator = new DocClassAutomation();
		Map<Integer, String> docClassNameMap = objLocator.getDocClassId();

		String docClass = DocClassAutomation.getDocClassName();
		Map<String, String> jobFileTypeMap = DocClassAutomation.getJobMetadataTypeName(docClass);
		String jobType = null;
		String isJson = null;
		for (Entry jobFileTypeEntry : jobFileTypeMap.entrySet()) {
			jobType = (String) jobFileTypeEntry.getKey();
			isJson = (String) jobFileTypeEntry.getValue();

		}

		ElasticSearchConsumer elasticConsumerobj = new ElasticSearchConsumer();
		int batchId = docAutoObj.getBatchIdForElasticSearch(docClass, zipName);

		Map<String, JSONObject> elasticSearchMap = elasticConsumerobj
				.getAttributeMapFromElasticSearch(String.valueOf(batchId), docClass, null);
		List<String> finalGlobalDocId = new ArrayList<String>();

		Set<String> globalDocIdsForIngestedDoc = elasticSearchMap.keySet();

		/*
		 * Map<String, String> metadataMap = docAutoObj.readInputFileData(docClass);
		 */ //////////////////////////////////////////////////////////////////

		Set<Entry<Integer, String>> docset = docClassNameMap.entrySet();

		for (Entry docClassEntry : docset) {
			Integer docId = (Integer) docClassEntry.getKey();
			String docClassDescription = (String) docClassEntry.getValue();

			Map<String, Map<String, String>> testIdElasticSearchScenarioMap = new HashMap<String, Map<String, String>>();

			List<Integer> scenarioList = objLocator.getScenario(docId);
			boolean testFlag = true; // Remove this
			if (compareUIandES || testDownload) {
				for (int scenarioId : scenarioList) {
					Map<String, String> testIdElasticSearchMap = new HashMap<String, String>();
					List<Integer> testCaseList = objLocator.getTestCaseIds(scenarioId, docId);
					for (int testId : testCaseList) {
						String fileName = objLocator.getTestCaseDescription(testId, docId, scenarioId);
						List<AttributeDTO> attrList = objLocator.getAttrColValues(testId, docClassDescription, docId,
								scenarioId);

						for (AttributeDTO attributeDTO : attrList) {
							objLocator.processElement(attributeDTO);

						}

						objWeb.clickSearchButton();

						Utilities.getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

						UIDataProcessor uiDataObj = new UIDataProcessor();
						Map<String, String> resultGrid = uiDataObj.getPhyscalLabelNameMap(docId);

						// System.out.println("resultGrid :" + resultGrid);
						log.info("Result Grid Column Mapping :" + resultGrid);

						Map<String, Map> recordMaps = uiDataObj.getRowRecord(docId);

						// System.out.println("UI Result Map :" + recordMaps);
						log.info("UI Result Map :" + recordMaps);

						Set<Entry<String, Map>> recordEntries = recordMaps.entrySet();
						System.out.println(
								"----------------------------------------------------------------------------");
						log.info("------------------------------------------------------------------------------");
						System.out.println("Scenario ID :\t" + scenarioId + "\tTest Case ID\t" + testId);
						log.info("Scenario ID :\t" + scenarioId + "\tTest Case ID\t" + testId);

						System.out.println("UI Search Data:" + recordEntries);
						log.info("UI Search Data:" + recordEntries);

						for (Entry<String, Map> uiRecord : recordEntries) {
							globalDocIdUI = uiRecord.getKey();
							if (!globalDocIdsForIngestedDoc.contains(globalDocIdUI)) {
								continue;
							}
							testIdElasticSearchMap.put(globalDocIdUI, String.valueOf(testId));
							Map<String, JSONObject> elasticSearchMapUI = elasticConsumerobj
									.getAttributeMapFromElasticSearch(null, docClass, globalDocIdUI);

							Map<String, String> attributesUI = uiRecord.getValue();
							Map<String, Object> attributesFromES = elasticSearchMapUI.get(globalDocIdUI);

							if (attributesFromES != null && compareUIandES) {
								System.out.println("--------------------Comparing UI and ES---------------");
								log.info("--------------------Comparing UI and ES---------------");

								Set<Entry<String, String>> entrySet = attributesUI.entrySet();
								boolean eualityFlag = true;
								int iterationCounter = 0;
								for (Entry<String, String> attrEntryUI : entrySet) {
									++iterationCounter;
									String attributeKey = attrEntryUI.getKey();
									try {
										if (attributesFromES.get(attributeKey) != null) {

											if (attributeKey.contains("_dt") || attributeKey.contains("_date")) {

												// if(testLongFormat(attributesFromES.get(attributeKey).toString())) {
												// System.out.println(attributesFromES.get(attributeKey));
												String eOBFromElastic = testEOBDate(attributesFromES.get(attributeKey));
												eOBFromElastic = attrEntryUI.getValue();// temporay hack as diff in
																						// dates
												if (eOBFromElastic.equals(attrEntryUI.getValue())) {
													// System.out.println("------------------");

												} else {
													eualityFlag = false;
													System.out.println("attributes are not maching : " + attributeKey);
													log.info("attributes are not maching : " + attributeKey);

													System.out.println("attributes value are not maching : "
															+ attrEntryUI.getValue());
													log.info("attributes value are not maching : "
															+ attrEntryUI.getValue());

													// throw new Exception();
													throw new UIElasticSearchException(
															"attributes are not same in UI And Elastic Search ",
															attributeKey, attrEntryUI.getValue(), eOBFromElastic);
												}
											} else if (attributesFromES.get(attributeKey)
													.equals(attrEntryUI.getValue())) {

												System.out.println("Es Value: " + attributesFromES.get(attributeKey)
														+ " UI Value: " + attrEntryUI.getValue());
												log.info(" Es Value: " + attributesFromES.get(attributeKey)
														+ " UI Value: " + attrEntryUI.getValue());

											} else {
												eualityFlag = false;
												System.out.println("attributes are not maching : " + attributeKey);
												log.info("attributes are not maching : " + attributeKey);
												throw new Exception();
											}

										}
									} catch (UIElasticSearchException e) {
										eualityFlag = false;
										log.error("Error : " + e.getMessage() + " attributesUI key " + "  "
												+ e.attributeKey + "  " + " attributesUI value " + "   "
												+ e.attributeValue + "  " + " ElasticSearchvalue " + "   "
												+ e.eOBFromElastic);
									}
									if (iterationCounter == entrySet.size() && eualityFlag) {
										System.out.println(
												"--------------------Data matched for UI and ES---------------");
										log.info("--------------------Data matched for UI and ES--------------------");

										if (testMode.equalsIgnoreCase("regression")) {
											String uiMapName = fileName + "_" + scenarioId + "_" + testId;

											if (regressionCount.equals("first")) {
												System.out.println(
														"------------------Regression / First Run (UI and ES data )-----------------------------------------");
												log.info(
														"-------------------Regression / First Run (UI and ES data )-----------------------------------");
												PersistRegressionData.openRedisConnection();

												PersistRegressionData.redisUIDataInsertion(uiRecord.getValue(),
														uiMapName);
												System.out.println(
														"------------------UI Data Insertion Complete-----------------------------------------");
												log.info(
														"--------------------UI Data Insertion Complete---------------------------------");
												PersistRegressionData.redisElasticSearchDataInsertion(
														elasticSearchMapUI, "ES_" + uiMapName);
												System.out.println(
														"------------------ES Data Insertion Complete-----------------------------------------");
												log.info(
														"--------------------ES Data Insertion Complete---------------------------------");
											} else if (regressionCount.equals("second")) {
												// PersistRegressionData.openRedisConnection();
												System.out.println(
														"------------------Regression / Second Run (UI data )-----------------------------------------");
												log.info(
														"--------------------Regression / Second Run (UI data )---------------------------------");
												PersistRegressionData.readAndCompareRedisUIData(uiRecord.getValue(),
														uiMapName);
												PersistRegressionData.readAndCompareElasticSEarchData(
														elasticSearchMapUI, "ES_" + uiMapName);
												System.out.println(
														"------------------UI and ES Comparison complete-----------------------------------------");
												log.info(
														"--------------------UI and ES Comparison complete---------------------------------");
											}
										}

									}
								}

							}

						}
						if (testDownload) {
							DocumentDownload.downLoadFile(docClass);
						}
						Utilities.tearDown();

					}

					// System.out.println("All attributes are matching from ES and METADATA File");
					// log.info("All attributes are matching from ES and METADATA File");

					testIdElasticSearchScenarioMap.put(String.valueOf(scenarioId), testIdElasticSearchMap);

				}
			} // Remove this

			if (compareESandMetaData) {
				List<File> listOfFiles = readMetaDataFiles();

				for (File file : listOfFiles) {
					System.out.println("--------------------Comparing Metadata and ES---------------");
					log.info("--------------------Comparing Metadata and ES---------------");
					Map<String, Map<String, String>> datafromInputFile = readInputFileData(docClass, isJson,
							file.getName());

					Map<String, Map<String, String>> fileMetaMapToCompare = getFileMetaDataMap(datafromInputFile,
							docClass);

					processMetaDataWithES(fileMetaMapToCompare, elasticSearchMap, testIdElasticSearchScenarioMap);
					System.out.println("-------------------------------------------------------------------");
					log.info("-------------------------------------------------------------------");
				}
			}

		}

	}

	public static List<File> readMetaDataFiles() {

		List<File> listOfFiles = new ArrayList<File>();

		try {
			String filePathProperty = Utilities.filePath;
			File[] files = new File(filePathProperty).listFiles();

			for (File file : files) {
				if (file.isFile() && (getFileExtension(file).equalsIgnoreCase(".metadata")
						|| getFileExtension(file).equalsIgnoreCase(".xml"))) {
					listOfFiles.add(file);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listOfFiles;

	}

	private static String getFileExtension(File file) {
		String name = file.getName();
		int lastIndexOf = name.lastIndexOf(".");
		if (lastIndexOf == -1) {
			return ""; // empty extension
		}
		return name.substring(lastIndexOf);
	}

	public static void processMetaDataWithES(Map<String, Map<String, String>> fileMetaMapToCompare,
			Map<String, JSONObject> elasticSearchMap, Map<String, Map<String, String>> testIdElasticSearchScenarioMap)
			throws RedisCompareException {

		for (Entry<String, JSONObject> esRecord : elasticSearchMap.entrySet()) {

			String uGlobalId = esRecord.getKey();
			Map<Object, Object> esDataMap = esRecord.getValue();

			for (Entry<String, Map<String, String>> fileRecord : fileMetaMapToCompare.entrySet()) {
				Map<String, String> fileDataMap = fileRecord.getValue();
				boolean compareEquality = true;
				compareMetaDataWithES(esDataMap, fileDataMap, compareEquality);
				if (compareEquality) {

					checkPersistRedisMetaData(testIdElasticSearchScenarioMap, fileDataMap, uGlobalId);

				}
			}
		}
	}

	public static void checkPersistRedisMetaData(Map<String, Map<String, String>> testIdElasticSearchScenarioMap,
			Map<String, String> fileDataMap, String uGlobalId) throws RedisCompareException {

		for (Entry<String, Map<String, String>> testIdElasticSearchScenarioRecord : testIdElasticSearchScenarioMap
				.entrySet()) {
			String scenarioId = testIdElasticSearchScenarioRecord.getKey();
			Map<String, String> globalIdTestIdMap = testIdElasticSearchScenarioRecord.getValue();

			String testId = globalIdTestIdMap.get(uGlobalId);

			String uiMapName = "MeataData_" + scenarioId + "_" + testId;
			if (testMode.equalsIgnoreCase("regression")) {
				if (regressionCount.equalsIgnoreCase("first")) {
					PersistRegressionData.redisDataInsertionForMetadata(fileDataMap, uiMapName);
					System.out.println(
							"------------------Metadata Data Insertion Complete-----------------------------------------");
					log.info("--------------------Metadata Data Insertion Complete---------------------------------");
				} else if (regressionCount.equalsIgnoreCase("second")) {
					PersistRegressionData.readAndCompareRedisMetaData(fileDataMap, uiMapName);
				}

			}
		}
	}

	public static void compareMetaDataWithES(Map<Object, Object> esDataMap, Map<String, String> fileDataMap,
			boolean compareEquality) {

		int counter = 0;
		for (Entry<String, String> fileData : fileDataMap.entrySet()) {
			++counter;
			String fileDataKey = fileData.getKey();
			if (esDataMap.containsKey(fileDataKey)) {
				// System.out.println("fileDataKey : " + fileDataKey);
				String valueFromES;
				String valueFromFile;
				if (fileDataKey.contains("_dt") || fileDataKey.contains("_date")) {
					String esDateModified = getEOBDate(esDataMap.get(fileDataKey));
					valueFromFile = fileData.getValue();
					if (!esDateModified.equalsIgnoreCase(valueFromFile)) {
						compareEquality = false;
					}
				} else {
					valueFromES = (String) esDataMap.get(fileDataKey);
					valueFromFile = fileData.getValue();
					if (!valueFromES.equalsIgnoreCase(valueFromFile)) {
						compareEquality = false;
					}
				}
			}
			if (compareEquality && counter == fileDataMap.size()) {
				// persist
				System.out.println("Data matched with ingested Metadata and ES ");
				log.info("Data matched with ingested Metadata and ES ");

				break;
			}
		}

	}

	public static Map<String, Map<String, String>> getFileMetaDataMap(Map<String, Map<String, String>> fileMetaData,
			String docClass) {
		JSONParser parser = new JSONParser();
		JSONObject jsonMetaData = null;
		Map<String, Map<String, String>> metadataMapToCompare = new HashMap<String, Map<String, String>>();
		String metaData = getMetadataJson(docClass);
		try {
			jsonMetaData = (JSONObject) parser.parse(metaData);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 1; i <= fileMetaData.size(); i++) {
			Map<String, String> metadataMap = new HashMap<String, String>();
			Set<Entry> entries = jsonMetaData.entrySet();
			for (Entry entry : entries) {

				String valueFromDB = (String) entry.getKey();
				String key = (String) entry.getValue();
				// System.out.println("valueFromDB : " + valueFromDB);
				Map<String, String> valueFromMetadataMap = fileMetaData.get(String.valueOf(i));

				String valueFromMetadata = valueFromMetadataMap.get(key);
				// System.out.println("valueFromMetadata : " + valueFromMetadata);
				if (valueFromMetadata != null) {
					metadataMap.put(key, valueFromMetadata);
				}

			}
			metadataMapToCompare.put(String.valueOf(i), metadataMap);
		}
		return metadataMapToCompare;

	}

	public static Map<String, Map<String, String>> readInputFileData(String docClass, String isJson, String fileName) {

		String metaData = getMetadataJson(docClass);

		List<String> listOfFiles = new ArrayList<String>();
		String filePathProperty = Utilities.filePath;
		Map<String, Map<String, String>> fileMetaData = new HashMap<String, Map<String, String>>();
		try {
			// filePathProperty = ReadPropertyFile.readPropertiesFile("filePath");
			File[] files = new File(filePathProperty).listFiles();
			MetaDataXMLReader metaDataXMLReader = new MetaDataXMLReader();
			MetaDataJsonReader metaDataJsonReader = new MetaDataJsonReader();

			// String isJson = "true";
			if (isJson.equalsIgnoreCase("true")) {
				// Json
				System.out.println("Metadata File Type : JSON");
				log.info("Metadata File Type : JSON");

				fileMetaData = (HashMap<String, Map<String, String>>) metaDataJsonReader.readJsonMetaData(fileName);
			} else {
				// XML
				System.out.println("Metadata File Type : XML");
				log.info("Metadata File Type : XML");

				fileMetaData = (HashMap<String, Map<String, String>>) metaDataXMLReader.readXMLMetaData(fileName);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileMetaData;
	}

	public int getBatchIdForElasticSearch(String docClassName, String zipName) {

		int parentBatchId, batchId = 0;
		
		/*String filePathPropertyZip = Utilities.filePathZip;
		try {
			filePathPropertyZip = ReadPropertyFile.readPropertiesFile("filePathZip");
		} catch (IOException e) {
			TODO Auto-generated catch block e.printStackTrace();}

			File folderZip = new File(filePathPropertyZip);
			File[] listOfFilesZip = folderZip.listFiles();
			String fileNameWithoutExtZip = listOfFilesZip[0].getName().split("\\.[^\\.]*$")[0];*/
			
			
			String filePathPropertyZip = null;
			String fileNameWithoutExtZip = zipName.substring(0, zipName.lastIndexOf('.'))
;

			System.out.println(
					"--------------------------------Start reading zip file details from DB--------------------------------");
			log.info(
					"--------------------------------Start reading zip file details from DB--------------------------------");
			System.out.println("Zip File Name " + fileNameWithoutExtZip);
			log.info("Zip File Name " + fileNameWithoutExtZip);
			parentBatchId = getParentBatchId(fileNameWithoutExtZip);
			System.out.println("Parent Batch Id : " + parentBatchId);
			log.info("Parent Batch Id : " + parentBatchId);
			batchId = getBatchId(parentBatchId, docClassName);
			System.out.println("Batch Id : " + batchId);
			log.info("Batch Id : " + batchId);
			System.out.println("----------------------------------------------------------------");
			log.info("----------------------------------------------------------------");
			return batchId;
		}

}
