package uniqueIdDataFromDB;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import docClassPageObject.AttributeDTO;
import propertyManager.ReadPropertyFile;
import testDataInsertion.PersistTestData;
import utilities.Utilities;

public class ElasticSearchConsumer {

	/**
	 * @param args
	 * 
	 */
	Logger log = Logger.getLogger(ElasticSearchConsumer.class);

	@SuppressWarnings("deprecation")
	public Map<String, JSONObject> getAttributeMapFromElasticSearch(String batchId, String docClassName,
			String gblDocId) {
		Map<String, JSONObject> recordsMap = new HashMap<String, JSONObject>();

		System.out.println("---------------------Start reading ES details--------------------------------------------");
		System.out.println("docClassName : " + docClassName);

		log.info("---------------------Start reading ES details--------------------------------------------");
		log.info("docClassName : " + docClassName);

		String elasticSearchURL = Utilities.elasticSearchURLSetup;
		String searchURL = Utilities.searchURLSetup;
		/*
		 * String elasticSearchURL = ""; String searchURL = ""; try { //elasticSearchURL
		 * = ReadPropertyFile.readPropertiesFile(Utilities.elasticSearchURL);
		 * //searchURL = ReadPropertyFile.readPropertiesFile(Utilities.searchURL);
		 * 
		 * } catch (IOException e1) {
		 * 
		 * log.error(e1.getMessage()); }
		 */

		try {
			HttpClient httpClient = new DefaultHttpClient();
			StringBuffer url = new StringBuffer();
			// url.append("http://doc360-meta-stg-elr.optum.com/u_ei_ltr_ntfy*/Document/_search?");

			url.append(elasticSearchURL + docClassName + searchURL);
			// url.append(elasticSearchURL);

			if (batchId == null) {
				url.append("q=u_gbl_doc_id:" + gblDocId);
			} else {
				url.append("q=batch_id:" + batchId);
			}

			HttpGet httpGet = new HttpGet(url.toString());
			System.out.println("Elastic Search URL : " + url.toString());
			log.info("Elastic Search URL : " + url.toString());

			httpGet.addHeader(
					BasicScheme.authenticate(new UsernamePasswordCredentials("elastic", "changeme"), "UTF-8", false));

			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity responseEntity = httpResponse.getEntity();
			String content = EntityUtils.toString(responseEntity);

			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(content);// Convert String to JSON Object

			JSONObject catanns = (JSONObject) json.get("hits");

			JSONArray attributeArr = (JSONArray) catanns.get("hits");

			for (int i = 0; i <= attributeArr.size() - 1; i++) {
				JSONObject attributeSet = (JSONObject) attributeArr.get(i);

				JSONObject attr_Map = (JSONObject) attributeSet.get("_source");
				// System.out.println("attr_Map : " + attr_Map);
				if (attr_Map.containsKey("u_gbl_doc_id")) {
					String globalDocId = (String) attr_Map.get("u_gbl_doc_id");

					// System.out.println(globalDocId);
					recordsMap.put(globalDocId, attr_Map);
				} else {
					throw new GlobalDocIdNotPresentException();
				}

			}

		} catch (Exception e) {
			// System.out.println("Exception while fetching data from elastic search");
			e.printStackTrace();
		}

		System.out.println("Elastic Search data : " + recordsMap);
		System.out.println("--------------------------------------------------------------------");
		log.info("Elastic Search data : " + recordsMap);
		log.info("--------------------------------------------------------------------");
		return recordsMap;

	}

	public boolean compareElasticSearchAndUIData() {

		return true;
	}

}