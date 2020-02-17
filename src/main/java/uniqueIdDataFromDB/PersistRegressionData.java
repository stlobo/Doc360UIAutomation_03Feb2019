package uniqueIdDataFromDB;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;

import utilities.Utilities;

public class PersistRegressionData {

	public static Logger log = Logger.getLogger(PersistRegressionData.class);

	static RedissonClient redisson;

	public static void openRedisConnection() throws Exception {
		
		//if user has admin right uncomment this line
		//Runtime.getRuntime().exec(Utilities.redis_server_exe,null,new File(Utilities.redis_server_path));
	
	}

	public static void redisUIDataInsertion(Map<String, String> recordMapsUI, String uiMapName) {
		redisson = Redisson.create();

		RMap<String, String> rmap = redisson.getMap(uiMapName);

		int i = 0;
		for (Entry<String, String> record : recordMapsUI.entrySet()) {

			// String attributesMap

			// RMap<String, String> attributesMap = redisson.getMap("redisAttrMap");
			String attrKey = record.getKey();
			String attrVal = record.getValue();
			// System.out.println(attrKey + " " + attrVal);
			rmap.put(attrKey, attrVal);

		}
	}

	public static void readAndCompareRedisData(Map<String, Map> recordMapsUI) {
		// redisson = Redisson.create();

		Set<Entry<Object, Object>> mi = redisson.getMap("redisMap").entrySet();
		if (recordMapsUI.size() == mi.size()) {

			for (Entry<Object, Object> entry : redisson.getMap("redisMap").entrySet()) {

				Map<String, String> UIMap = recordMapsUI.get(entry.getKey());

				if (recordMapsUI.containsKey(entry.getKey())) {

					for (Entry<String, String> uiMap : UIMap.entrySet()) {
						Map<String, String> dbInnerMap = (Map<String, String>) entry.getValue();
						for (Entry<String, String> dbMap : dbInnerMap.entrySet()) {
							if (uiMap.getKey().equalsIgnoreCase(dbMap.getKey())) {
								if (!dbMap.getValue().equalsIgnoreCase(uiMap.getValue())) {
									// System.out.println("Not Matching" + "valueAfter " + uiMap.getValue()
									// + "::::valueBefore " + dbMap.getValue());
									// Logger.

								} /*
									 * else { System.out.println("Not Matching"); }
									 */
								/*
								 * String valueAfter = uiMap.getValue(); String valueBefore = dbMap.getValue();
								 * 
								 * System.out.println("valueAfter " + valueAfter + "::::valueBefore " +
								 * valueBefore);
								 */
								System.out.println("Matching");
								log.info("Matching");
							
							}

						}

					}
				}

			}
		} else {
			System.out.println("Regression count not matching");
			log.info("Regression count not matching");
		}
		// redisson.getKeys().flushall();
		// redisson.shutdown();

	}

	public static void readAndCompareRedisUIData(Map<String, String> recordMapsUI, String uiMapName)
			throws RedisCompareException {
		redisson = Redisson.create();

		Set<Entry<Object, Object>> mi = redisson.getMap(uiMapName).entrySet();
		boolean dataMatchCheck = true;
		if (recordMapsUI.size() == mi.size()) {
			int i = 0;

			for (Entry<Object, Object> redisEntry : mi) {
				++i;
				String attributeKey = (String) redisEntry.getKey();

				if (recordMapsUI.containsKey(attributeKey)) {
					String valueFromDB = (String) redisEntry.getValue();
					String valueFromUI = recordMapsUI.get(attributeKey);
					if (!valueFromDB.equalsIgnoreCase(valueFromUI) && !attributeKey.equalsIgnoreCase("u_gbl_doc_id")) {
						dataMatchCheck = false;
						System.out.println("Attribute " + attributeKey + " Values does not match in second ingestion");
						log.info("Attribute " + attributeKey + " Values does not match in second ingestion");

					}
					// System.out.println("valueAfter " + valueAfter + "::::valueBefore " +
					// valueBefore);
				} else {
					dataMatchCheck = false;
					System.out.println(attributeKey + " Attribute Not Present in UI map");
					log.info(attributeKey + " Attribute Not Present in UI map");
				}
				if (dataMatchCheck && i == recordMapsUI.size()) {
					System.out.println("UI Data Matched in 2nd Run");
					log.info("UI Data Matched in 2nd Run");
				}
			}

		} else {
			dataMatchCheck = false;
			System.out.println("Regression UI attribute count not matching");
			log.info("Regression UI attribute count not matching");
			throw new RedisCompareException("UI records in first Run and Second Run not matching");
		}
		// redisson.getKeys().flushall();
		// redisson.shutdown();

	}

	public static void redisDataInsertionForMetadata(Map<String, String> metaDataMapRedis, String uiMapName) {
		// redisson = Redisson.create();

		RMap<String, String> rMetaMap = redisson.getMap(uiMapName);

		Set<Entry<String, String>> recordEntries = metaDataMapRedis.entrySet();
		for (Entry<String, String> record : recordEntries) {

			String metaDataKey = record.getKey();
			String metaDataValue = record.getValue();

			rMetaMap.put(metaDataKey, metaDataValue);

		}
	}

	public static void readAndCompareRedisMetaData(Map<String, String> metadataMapCurrent, String uiMapName)
			throws RedisCompareException {
		// redisson = Redisson.create();

		Set<Entry<Object, Object>> metaDataMapRedis = redisson.getMap(uiMapName).entrySet();
		if (metadataMapCurrent.size() == metaDataMapRedis.size()) {
			for (Entry<Object, Object> metaDataRedis : metaDataMapRedis) {
				String redisMetaKey = (String) metaDataRedis.getKey();
				String redisMetaValue = (String) metaDataRedis.getValue();
				if (metadataMapCurrent.containsKey(redisMetaKey)
						&& redisMetaValue.equalsIgnoreCase(metadataMapCurrent.get(redisMetaKey))) {
					System.out.println("MetaData records in first Run and Second Run matching\tKey "+redisMetaKey+" Value "+redisMetaValue);
					log.info("MetaData records in first Run and Second Run matching\tKey "+redisMetaKey+" Value "+redisMetaValue);

				} else {
					//System.out.println("Metadata attribute " + redisMetaKey + " In second Run and First Run Not Matching ");
					//log.info("Metadata attribute " + redisMetaKey + " In second Run and First Run Not Matching ");
				}

			}

		} else {
			//System.out.println("Metadata records in first Run and Second Run not matching");
		}

		redisson.getKeys().flushall();
	}

	public static void redisElasticSearchDataInsertion(Map<String, JSONObject> elasticSearchCurrentMap,
			String esMapName) {

		// redisson = Redisson.create();

		RMap<String, RMap<String, String>> redisOuterElasticMap = redisson.getMap(esMapName);
		Set<Entry<String, JSONObject>> elasticOuterEntries = elasticSearchCurrentMap.entrySet();
		int i = 0;
		for (Entry<String, JSONObject> elasticEntry : elasticOuterEntries) {
			++i;
			String globalDocId = elasticEntry.getKey();
			JSONObject elasticJson = elasticEntry.getValue();
			RMap<String, String> redisInnerElasticMap = redisson.getMap(esMapName + "_" + i);
			Set<Entry<String, String>> elasticInnerEntries = elasticJson.entrySet();
			for (Entry<String, String> elasticInnerEntry : elasticInnerEntries) {
				String key = null;
				String value = null;
				key = elasticInnerEntry.getKey().toString();

				if (elasticInnerEntry.getKey().contains("_dt")
						|| elasticInnerEntry.getKey().contains("_date")) {

					value = DocClassAutomation.getEOBDate(elasticInnerEntry.getValue());

					redisInnerElasticMap.put(key, value);
				} else if (elasticInnerEntry.getKey().equalsIgnoreCase("group_names")) {

				} else {

					value = elasticInnerEntry.getValue().toString();
					redisInnerElasticMap.put(key, value);

				}

			}

			redisOuterElasticMap.put(globalDocId, redisInnerElasticMap);
		}

	}

	public static void readAndCompareElasticSEarchData(Map<String, JSONObject> elasticSearchCurrentMap,
			String esMapName) {

		// redisson = Redisson.create();
		System.out.println("Started comparing regression data for ES");
		log.info("Started comparing regression data for ES");

		Set<Entry<Object, Object>> redissDBMap = redisson.getMap(esMapName).entrySet();

		if (elasticSearchCurrentMap.size() == redissDBMap.size()) {
			int i = 0;
			boolean equalityCheck = true;
			for (Entry<Object, Object> entry : redissDBMap) {
				++i;
				Map<String, String> currentInnerMap = elasticSearchCurrentMap.get(entry.getKey());

				if (elasticSearchCurrentMap.containsKey(entry.getKey())) {
					Map<String, String> redisDbInnerElasticMap = (Map<String, String>) entry.getValue();

					for (Entry<String, String> dbMapEntry : redisDbInnerElasticMap.entrySet()) {

						if (currentInnerMap.containsKey(dbMapEntry.getKey())) {
							String valueFromCurrentRun = null;
							String valueFromDB = redisDbInnerElasticMap.get(dbMapEntry.getKey());

							if (dbMapEntry.getKey().contains("_dt")
									|| dbMapEntry.getKey().contains("_date")) {
								valueFromCurrentRun = DocClassAutomation
										.getEOBDate(currentInnerMap.get(dbMapEntry.getKey()));

							} else if (dbMapEntry.getKey().equalsIgnoreCase("group_names")) {

							} else {
								valueFromCurrentRun = currentInnerMap.get(dbMapEntry.getKey());
							}
							if (!valueFromDB.equalsIgnoreCase(valueFromCurrentRun)
									&& !dbMapEntry.getKey().equalsIgnoreCase("u_gbl_doc_id")) {
								equalityCheck = false;
								System.out.println(
										"Attribute " + dbMapEntry.getKey() + " Values does not match in 2nd Run");

							}

						} else {
							System.out.println("Attribute" + entry.getKey() + "Not available in 2nd Run");
						}

					}

				} else {
					System.out.println("GlobalDocId" + entry.getKey() + "Not available in 2nd Run");
				}
				if (equalityCheck && i == redissDBMap.size()) {
					System.out.println("Elastic Search records in first Run and Second Run  matching");
					log.info("Elastic Search records in first Run and Second Run  matching");
				}
			}
		} else {
			System.out.println("Elastic Search records in first Run and Second Run not matching");

		}
		// redisson.getKeys().flushall();
	}

}
