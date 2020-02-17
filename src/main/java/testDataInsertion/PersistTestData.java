package testDataInsertion;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import uniqueIdDataFromDB.DocClassAutomation;
import utilities.Utilities;

public class PersistTestData {

	public static final String metadataPath = Utilities.filePath;
	public static File filePath = new File(Utilities.filePath);
	public static Logger log = Logger.getLogger(PersistTestData.class);

	private static List<String> getMetaFiles(String dir, String fileType) {

		List<String> metaFiles = new ArrayList<String>();
		try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(Paths.get(dir))) {
			for (Path path : dirStream) {
				if (String.valueOf(path.getFileName()).endsWith(fileType))
					metaFiles.add(path.getFileName().toString());
			}
		} catch (IOException ex) {
			log.error("Not able to find file " + ex.getMessage());
		}
		return metaFiles;
	}

	public static void log(TestData tmsg) {
		//System.out.println(tmsg);
		log.info(tmsg);
	}

	static ZipMetadataMap readMetadataFiles(int scernaioId, String zip) {

		List<String> metaFiles = getMetaFiles(metadataPath, ".metadata");
		ZipMetadataMap zipToMdata = new ZipMetadataMap();
		zipToMdata.setScenarioId(scernaioId);
		zipToMdata.setZip(zip);
		zipToMdata.setMetadata(metaFiles);

		return zipToMdata;

	}

	public static boolean persistTestData(int scenarioId, String zipName) {

		boolean insertionFlag = true;
		try {
			ZipMetadataMap obj = readMetadataFiles(scenarioId, zipName);
			List<TestData> dbMapper = new ArrayList<>();
			List<String> fnames = obj.getMetadata();

			List<Map<Integer, String>> testDataList = new ArrayList<Map<Integer, String>>();
			TestData dbObj;

			DocClassTestData tdata = new DocClassTestData();
			Map<Integer, String> map = new HashMap<>();
			tdata.setScenarioId(obj.getScenarioId());
			tdata.setZipFile(obj.getZip());
			int testCaseId = 1;

			Utilities.setupDBConfigValues();

			for (String fname : fnames) {

				map.put(testCaseId, fname);
				tdata.setCaseIdDescMap(map);

				String docClassName = MetaDataXMLReader.readXMLDocClassName(fname,"DocumentClass");
				String docClassID = DBValueReader.getMetaDataDocClassID(docClassName);
				DBValueReader.getMetaDataAttributeMap(docClassID);
				Map<Integer, List<Integer>> classAttribMap = DBValueReader.getDocClassAttribMap();

				List<Integer> classAttribs = classAttribMap.get(Integer.valueOf(docClassID));

				testDataList = DBValueReader.getTestAttVal(filePath + File.separator + fname, classAttribs);

				for (Integer attr : classAttribs) {

					Map<Integer, String> dbObj1 = tdata.getCaseIdDescMap();

					Iterator<Entry<Integer, String>> iter = dbObj1.entrySet().iterator();

					while (iter.hasNext()) {
						dbObj = new TestData();
						Map.Entry<Integer, String> mapElement = (Map.Entry<Integer, String>) iter.next();

						tdata.setDocClassId(Integer.valueOf(docClassID));
						tdata.setDocClassAttr(attr);

						dbObj.setScenarioId(obj.getScenarioId());
						dbObj.setZipFile(obj.getZip());
						dbObj.setTestCaseId(mapElement.getKey());
						dbObj.setTestCaseDesc(mapElement.getValue());
						dbObj.setDocClassId(Integer.valueOf(docClassID));
						dbObj.setDocClassAttr(attr);
						dbObj.setCtrlType("Text");
						dbObj.setCondition("AND");

						for (Map<Integer, String> attrTdataMap : testDataList) {
							dbObj.setTestData(attrTdataMap.get(attr));
							break;
						}

						dbMapper.add(dbObj);
					}

				}

				testCaseId++;
			} // end loop
			
			Set<TestData> unqTestData = new HashSet<>(dbMapper);
			
			for (TestData tdata1: unqTestData) {
				log.info(tdata1);
			}
			DBValueReader.setUpTestDataTable(new ArrayList<>(unqTestData));

			
		} catch (Exception e) {
			log.error("Exception in saving qa data" + (e.getMessage()));
			insertionFlag = false;
		}
		
		return insertionFlag;
	}

	

}
