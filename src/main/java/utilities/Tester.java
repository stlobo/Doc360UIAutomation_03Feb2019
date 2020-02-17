package utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.print.attribute.DocAttribute;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.xml.sax.SAXException;

import testDataInsertion.DBValueReader;
import testDataInsertion.MetaDataXMLReader;
import testDataInsertion.PersistTestData;
import uniqueIdDataFromDB.DocClassAutomation;

public class Tester {

	public static Logger log = Logger.getLogger(Tester.class);

	static String filePath;
	static {
		Utilities.setupDBConfigValues();
		Utilities.setupPropConfigValues();
		filePath = Utilities.filePath;
	}

	public static void main(String[] args) throws Throwable {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String d = sdf.format(new Date());
		System.setProperty("current.date", d.replace("/", "_").replace(" ", "_").replace(":", "_"));
		PropertyConfigurator.configure("./src/test/java/Log4j/log4j.properties");
		String filePathPropertyZip = Utilities.filePathZip;
		File folderZip = new File(filePathPropertyZip);
		File[] listOfFilesZip = folderZip.listFiles();

		int scenarioCounter = 0;
		for (File zipFile : listOfFilesZip) {
			++scenarioCounter;
			File dir = new File(Utilities.filePath);
			FileUtils.cleanDirectory(dir);
			UnzipUtility.unzip(zipFile.getAbsolutePath(), Utilities.filePath);
			String batchPath = getBatchPath();

			setUpForAutomation(zipFile.getName(), scenarioCounter, batchPath);

		}

	}

	private static String getBatchPath()
			throws XPathExpressionException, ParserConfigurationException, SAXException, IOException, Exception {
		String docClass = null;
		String batchPath = null;
		String filePathProperty = Utilities.filePath;
		File[] files = new File(filePathProperty).listFiles();
		String fname = "";

		for (File file : files) {
			if (file.isFile() && (getFileExtension(file).equalsIgnoreCase(".metadata")
					|| getFileExtension(file).equalsIgnoreCase(".xml"))) {
				fname = file.getName();

				docClass = MetaDataXMLReader.readXMLDocClassName(fname, "DocumentClass");
				if (docClass.contains("u_")) {
					try {
						batchPath = DocClassAutomation.getBatchPathQuery(docClass);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					String applicationStr = "ApplicationID";
					String appId = MetaDataXMLReader.readXMLDocClassName(fname, applicationStr);
					// System.out.println("ApplicationID : " + appId);
					log.info("ApplicationID : " + appId);

					String docTypeString = "DocumentType";
					String docType = MetaDataXMLReader.readXMLDocClassName(fname, docTypeString);
					// System.out.println("DocumentType : " + docType);
					log.info("DocumentType : " + docType);

					batchPath = appId.concat("_").concat(docType);
				}

			}
		}
		return batchPath;

	}

	private static String getFileExtension(File file) {
		String name = file.getName();
		int lastIndexOf = name.lastIndexOf(".");
		if (lastIndexOf == -1) {
			return ""; // empty extension
		}
		return name.substring(lastIndexOf);
	}

	private static String getFileName() {
		String filePathProperty = Utilities.filePath;
		File[] files = new File(filePathProperty).listFiles();
		String fname = "";

		for (File file : files) {
			if (file.isFile() && (getFileExtension(file).equalsIgnoreCase(".metadata")
					|| getFileExtension(file).equalsIgnoreCase(".xml"))) {
				fname = file.getName();
			}
		}
		return fname;
	}

	private static void setUpForAutomation(String zipName, int scenarioId, String batchPath) throws Throwable {
		MultiKeyConvert mul;
		Boolean isDoumentsMetadata = MetaDataXMLReader.isDoumentsMetadata(getFileName());
		if (isDoumentsMetadata) {
			mul = new XmlConverterForAFP();
		} else {
			mul = new XmlConvertorForPdf();
		}
		// String batchPath = "docmkr_grpdoc5";
		try {
			List<File> listOfFiles = new ArrayList<File>();
			String filePathProperty = Utilities.filePath;
			File[] files = new File(filePathProperty).listFiles();

			for (File file : files) {
				if (file.isFile() && (getFileExtension(file).equalsIgnoreCase(".metadata")
						|| getFileExtension(file).equalsIgnoreCase(".xml"))) {
					// listOfFiles.add(file);
					mul.callMultiKeyConvert(file, batchPath, "", (long) 1, "1", filePathProperty);
				}
			}

			boolean isDataSaved = PersistTestData.persistTestData(scenarioId, zipName);
			log.info("Test data saved " + (isDataSaved));

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		DocClassAutomation.startAutomation(zipName);
	}

}
