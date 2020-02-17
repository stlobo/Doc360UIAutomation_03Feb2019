package uniqueIdDataFromDB;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import propertyManager.ReadPropertyFile;

public class MetaDataJsonReader {
	public static Logger log = Logger.getLogger(MetaDataJsonReader.class);

	public Map<String, Map<String, String>> readJsonMetaData(String fileName) {
		
		JSONParser parser = new JSONParser();
		
		HashMap<String, Map<String, String>> hashMapDataList = new HashMap<String, Map<String, String>>();
		try {

			String filePathProperty = ReadPropertyFile.readPropertiesFile("filePath");
			Object obj = parser.parse(new FileReader(filePathProperty + fileName));

			JSONObject jsonObject = (JSONObject) obj;
			JSONArray fileListArr = null;

			if (jsonObject.get("FileList") != null) {
				fileListArr = (JSONArray) jsonObject.get("FileList");
			} else if (jsonObject.get("u_fl_list") != null) {
				fileListArr = (JSONArray) jsonObject.get("u_fl_list");
			}

			Iterator it = fileListArr.iterator();

			while (it.hasNext()) {
				JSONObject type = (JSONObject) it.next();
				JSONArray fileListArr1 = null;

				if ((JSONArray) type.get("PublicationList") != null) {
					fileListArr1 = (JSONArray) type.get("PublicationList");
				} else if ((JSONArray) type.get("u_publ_list") != null) {
					fileListArr1 = (JSONArray) type.get("u_publ_list");
				}

				System.out.println("fileListArr1 : " + fileListArr1);
				log.info("fileListArr1 : " + fileListArr1);
				Iterator it1 = fileListArr1.iterator();
				int listCounter = 0;
				while (it1.hasNext()) {
					HashMap<String, String> hashMapData = new HashMap<String, String>();
					++listCounter;
					JSONObject type1 = (JSONObject) it1.next();
					JSONArray metaDataArr = (JSONArray) type1.get("MetaDataList");
					for (int i = 0; i < metaDataArr.size(); i++) {
						JSONObject fieldData = (JSONObject) metaDataArr.get(i);
						List<String> list = (List<String>) fieldData.get("FieldValues");
						hashMapData.put(fieldData.get("FieldName").toString(), list.get(0));

					}
					hashMapDataList.put(String.valueOf(listCounter), hashMapData);

				}
				System.out.println("JSON file Mappings are: " + hashMapDataList);
				log.info("JSON file Mappings are: " + hashMapDataList);
				
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return hashMapDataList;
	}

}
