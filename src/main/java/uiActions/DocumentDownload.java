package uiActions;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import propertyManager.ReadPropertyFile;
import uniqueIdDataFromDB.AutomationQueries;
import uniqueIdDataFromDB.DocClassAutomation;
import utilities.Utilities;

public class DocumentDownload {

	public static Logger log = Logger.getLogger(DocumentDownload.class);
	public static DocumentDownload obj = new DocumentDownload();
	// static String path = System.getProperty("user.dir");
	// public static String IngestedDocDownloadPath = path +
	// Utilities.IngestedDocDownload;
	public static File dir = new File(Utilities.IngestedDocDownload);

	public File getLatestFilefromDir() throws Throwable {

		/*
		 * String IngestedDocDownload = path +
		 * ReadPropertyFile.readPropertiesFile("IngestedDocDownload"); File dir = new
		 * File(IngestedDocDownload);
		 */

		File[] files = dir.listFiles();
		if (files == null || files.length == 0) {
			return null;
		}

		File lastModifiedFile = files[0];
		for (int i = 1; i < files.length; i++) {
			if (lastModifiedFile.lastModified() < files[i].lastModified()) {
				lastModifiedFile = files[i];
			}
		}
		return lastModifiedFile;

	}

	public void cleanDirectory() throws Throwable {
		/*
		 * String IngestedDocDownload = path +
		 * ReadPropertyFile.readPropertiesFile("IngestedDocDownload"); File dir = new
		 * File(IngestedDocDownload);
		 */

		try {
			FileUtils.cleanDirectory(dir);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
		// System.out.println("Directory Cleaned");
	}

	public void getPDFDoc() throws Throwable {
		try {

			Utilities.getDriver().findElement(By.xpath("//button[@id='btnDownload']")).click();
			// System.out.println("PDF download");
			log.info("PDF download");
			Thread.sleep(1000);

			for (String winHandle : Utilities.getDriver().getWindowHandles()) {
				Utilities.getDriver().switchTo().window(winHandle);
				// System.out.println("Inside Windows handle");
			}

			Utilities.getDriver().findElement(By.xpath("//input[contains(@value,'downloadAllPages')]")).click();
			System.out.println("Selected Download All Pages Radio button");
			log.info("Selected Download All Pages Radio button");
			Thread.sleep(1000);

			Utilities.getDriver().findElement(By.xpath(
					"//ng-form[@name='downloadForm']//button[@class='btn btn-primary'][contains(text(),'Download')]"))
					.click();
			Thread.sleep(5000);
			// System.out.println("Selected download pages button in the Pop up");

			File ddFile = obj.getLatestFilefromDir();
			if (ddFile != null) {

				System.out.println(
						"File \t" + ddFile.getName() + "\tSize\t" + ddFile.length() + "\tis downloading successfully");
				log.info("File \t" + ddFile.getName() + "\tSize\t" + ddFile.length() + "\tis downloading successfully");
			} else {
				System.out.println("No files downloaded");
				log.info("No files downloaded");
				throw new NoDocumentFoundException("No files downloaded");
			}
		} catch (Exception e) {

			System.out.println("No files visible for download");
			log.info("No files visible for download");

		}
	}

	public void getDoc() throws Throwable {

		File ddDocFile = obj.getLatestFilefromDir();
		if (ddDocFile != null) {
			Thread.sleep(2000);
			System.out.println(
					"File \t" + ddDocFile.getName() + "\tSize\t" + ddDocFile.length() + "\tis downloading successfully");
			log.info("File \t" + ddDocFile.getName() + "\tSize\t" + ddDocFile.length() + "\tis downloading successfully");
		} else {

			obj.getPDFDoc();
		}

	}

	public static void downLoadFile(String doc_cls_nm) throws Throwable {

		PreparedStatement pstmt = Utilities.dbSetUp(AutomationQueries.TEST_DATA_ATTR_QUERY);
		ResultSet res;
		String jobType = null;
		pstmt = Utilities.dbSetUp2(AutomationQueries.GET_JOBTYPE_METADATATYPE_QUERY);
		pstmt.setString(1, doc_cls_nm);
		res = pstmt.executeQuery();
		while (res.next()) {
			jobType = res.getString(1);
		}
		obj.cleanDirectory();
		Thread.sleep(5000);
		int ddFlag = 2;
		try {

			/*
			 * JavascriptExecutor js = (JavascriptExecutor)Utilities.getDriver();
			 * js.executeScript("document.body.style.zoom='100%';"); Thread.sleep(5000);
			 * WebActions.scrollToHeight(); Thread.sleep(5000);
			 */

			Utilities.getDriver().findElement(By.xpath("//span[@class='glyphicon glyphicon-picture']")).click();
			System.out.println(
					"--------------------------------Started Document Download--------------------------------");
			log.info(
					"--------------------------------Started Document Download--------------------------------");
			// System.out.println("Selected download on search page");
			Thread.sleep(2000);

			if (jobType.equalsIgnoreCase("pdfEnrichment-job") || jobType.equalsIgnoreCase("afpPdfBatch")
					|| jobType.equalsIgnoreCase("tifToPdfjob")) {
				ddFlag = 1;
			}

			if (jobType.equalsIgnoreCase("multiformatjob")) {
				ddFlag = 2;
			}

			switch (ddFlag) {

			case 1: {
				obj.getPDFDoc();
				break;

			}

			case 2: {
				obj.getDoc();
				break;

			}

			default: {
				System.out.println(
						"Incorrect job type: Select from the below job types \n1.pdfEnrichment-job \n2.afpPdfBatch \n3.tifToPdfjob \n4.multiformatjob");
				log.info(
						"Incorrect job type: Select from the below job types \n1.pdfEnrichment-job \n2.afpPdfBatch \n3.tifToPdfjob \n4.multiformatjob");
			}

			}
		} catch (Exception e) {
			System.out.println("No content availble to download");
			log.info("No content availble to download");

		}

		System.out.println("----------------------------------------------------------------");
		log.info("----------------------------------------------------------------");
	}

}
