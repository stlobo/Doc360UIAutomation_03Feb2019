package uniqueIdDataFromDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import uiActions.WebActions;
import utilities.Utilities;

public class UIDataProcessor {
	
	public static Logger log = Logger.getLogger(UIDataProcessor.class);

	// maps physical and label names from DB
	public HashMap<String, String> getPhyscalLabelNameMap(int docId) {
		HashMap<String, String> resultSetMap = new HashMap<String, String>();

		try {
			ResultSet res;
			PreparedStatement pstmt;
			pstmt = Utilities.dbSetUp(AutomationQueries.TEST_DATA_RESULTSET_QUERY);
			pstmt.setInt(1, docId);
			res = pstmt.executeQuery();
			while (res.next()) {
				String physicalName = res.getString(1);
				String labelName = res.getString(2);
				resultSetMap.put(labelName, physicalName);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultSetMap;
	}

	public Map<String, Map> getRowRecord(int docId) throws Throwable {

		ArrayList<String> gridHeaderLabel = new ArrayList<String>();
		Map<String, Map> record = new HashMap<String, Map>();
		// HashMap<String, String> gridMap = new HashMap<String, String>();
		JavascriptExecutor js = (JavascriptExecutor) Utilities.getDriver();

		if (WebActions.searchResultDisplay()) {

			// list of header label row

			js.executeScript("document.body.style.zoom='20%';");
			//System.out.println("Thread Sleep On" + new Date());
			Thread.sleep(5000);
			//System.out.println("Thread Sleep off" + new Date());
			ArrayList<WebElement> headerWebElements = (ArrayList<WebElement>) Utilities.getDriver()
					.findElements(By.xpath("//div[@role='columnheader']/div/span[contains(@id,'header-text')]"));

			for (WebElement el : headerWebElements) {
				//System.out.println("header Element " + headerWebElements.size());
				//String LabelName = el.getText();
				String LabelName = el.getAttribute("innerHTML");
				
				// System.out.println("LabelName : " + LabelName);
				if (el.getText().isEmpty()) {
					// System.out.println("Inside scroll");
					WebActions.scrollGrid();
					//LabelName = el.getText();
					LabelName = el.getAttribute("innerHTML");
					
				}
				Map<String, String> physicalLabelNameMap = getPhyscalLabelNameMap(docId);
				String physicalName = physicalLabelNameMap.get(LabelName);
				gridHeaderLabel.add(physicalName);
				// System.out.print("\tLabel\t" + LabelName);
			}

			//js.executeScript("document.body.style.zoom='100%';");
			//Thread.sleep(5000);
			//WebActions.scrollToHeight();
			//Thread.sleep(5000);

			// gets total rows in results grid without header
			
			WebActions.scrollToHeight();
			List<WebElement> row = Utilities.getDriver().findElements(By.xpath("//div[@class='ui-grid-row ng-scope']"));
			
			js.executeScript("document.body.style.zoom='100%';");
			//WebActions.scrollToHeight();
			js.executeScript("window.scrollBy(0,1000)");
			Thread.sleep(5000);

			for (int i = 0; i < row.size(); i++) {
				HashMap<String, String> gridlabelValueMap = new HashMap<String, String>();

				// gets the columns of the result grid without header
				List<WebElement> col = Utilities.getDriver().findElements(
						By.xpath("//div[@class='ui-grid-row ng-scope']/div/div[@role='gridcell' and contains(@id,'-" + i
								+ "-uiGrid-')]"));
				int labelIndx = 0;

				for (int j = 0; j < col.size(); j++) {
					//System.out.println("Coulmn Size" + col.size());
					//System.out.println("Grid Size " + gridHeaderLabel.size());

					// get dynamic index id of cell
					String getCellID = col.get(j).getAttribute("id");
					String getCellIndex = StringUtils.substringBetween(getCellID, "-uiGrid-", "-cell");
					Thread.sleep(100);
					// get cell value using row and dynamic id
					String cellValue = Utilities.getDriver()
							.findElement(By.xpath("//div[@class='ui-grid-row ng-scope']/div/div[contains(@id,'-" + i
									+ "-uiGrid-" + getCellIndex + "-cell')]")).getText();

					// System.out.println("\n***Cell Value***" + cellValue);

					// skip second column
					if (j != 1) {
						/////////////////////////////////// Commented for Testing
						/////////////////////////////////// //////////////////////////////////////////////////
						if (gridHeaderLabel.get(labelIndx) != null) {
							/* System.out.println("Test : " + gridHeaderLabel.get(labelIndx)); */
							if (gridHeaderLabel.get(labelIndx).equals("u_gbl_doc_id")) {
								String GlobalDocIdElasticFormat = modifyGlobalIDInElasticFormat(cellValue);
								gridlabelValueMap.put(gridHeaderLabel.get(labelIndx), GlobalDocIdElasticFormat);
								record.put(GlobalDocIdElasticFormat, gridlabelValueMap);
							} else {
								gridlabelValueMap.put(gridHeaderLabel.get(labelIndx), cellValue);
							}
							
						}
						labelIndx++;
					}

				}
				/*
				 * String globalDocId = gridlabelValueMap.get("u_gbl_doc_id"); //String
				 * globalDocId = "6f3f7a66-a5fa-4ac1-8710-4b80aad9a6f4";
				 * //System.out.println("globalDocId" + globalDocId); String
				 * formattedGlobalDocId = modifyGlobalIDInElasticFormat(globalDocId);
				 * record.put(formattedGlobalDocId, gridlabelValueMap);
				 */
				
			}
		} else {

			System.out.println("No Records in Result for the entered search criteria");
			log.info("No Records in Result for the entered search criteria");
			
		}
		return record;

	}

	public String modifyGlobalIDInElasticFormat(String globalIdUI) {
		//System.out.println("globalIdUI : " + globalIdUI);
		String[] modifiedGlobalDocId = globalIdUI.split("\\|");
		//System.out.println("modifiedGlobalDocId : " + modifiedGlobalDocId[0]);
		return modifiedGlobalDocId[0];
	}
}
