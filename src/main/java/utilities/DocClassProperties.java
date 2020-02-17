package utilities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import uniqueIdDataFromDB.DocClassAutomation;

public class DocClassProperties{

	
	private List<String> docClassList;

	private String inputStream;
	
	DocClassAutomation dca = new DocClassAutomation();


	public static Map<String, DocClassObject> docClassMapping = new HashMap<>();

	
	public void afterPropertiesSet() throws Exception {

		DocClassObject docClassObject = null;

		for (String docClassName : docClassList) {
			String btch_pth = inputStream + "_" + docClassName;
			docClassObject = dca.getDocClassObject(btch_pth);
			if (docClassObject == null || docClassObject.getBatchJobType() == null || docClassObject.getBatchJobType().trim().isEmpty()) {
				throw new Exception("Required configuration not found for doc class : " + btch_pth
						+ ", configuration : " + docClassObject);
			}
			docClassObject.setSummaryReq(dca.summaryRequiredStatus(docClassObject.getDocClasName()));
			docClassMapping.put(btch_pth, docClassObject);
		}

	}

	public DocClassObject getDocClassObject(String name) {
		return docClassMapping.get(inputStream + "_" + name);
	}

}
