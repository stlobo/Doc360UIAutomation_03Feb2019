package uniqueIdDataFromDB;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import propertyManager.ReadPropertyFile;
import utilities.Utilities;

public class MetaDataXMLReader {
	public static Logger log = Logger.getLogger(MetaDataXMLReader.class);

	public Map<String, Map<String, String>> readXMLMetaData(String fileName) {

		HashMap<String, Map<String, String>> hashMapDataXML = new HashMap<String, Map<String, String>>();

		try {
			// String filePathProperty = ReadPropertyFile.readPropertiesFile("filePath");
			String filePathProperty = Utilities.filePath;
			String filePath = filePathProperty + fileName;

			SAXBuilder builder = new SAXBuilder();
			File xmlFile = new File(filePath);

			Document document;

			document = (Document) builder.build(xmlFile);

			Element rootNode = document.getRootElement();
			// List listDocument = rootNode.getChildren("Document");
			List listDocument = new ArrayList<>();
			listDocument.add(rootNode);

			for (int i = 0; i < listDocument.size(); i++) {

				Element node = (Element) listDocument.get(i);

				List columns = node.getChildren("Indices");

				for (int j = 0; j < columns.size(); j++) {
					HashMap<String, String> hashMapData = new HashMap<String, String>();
					Element column = (Element) columns.get(j);

					List indexFieldList = column.getChildren("IndexField");

					for (int k = 0; k < indexFieldList.size(); k++) {
						Element data = (Element) indexFieldList.get(k);

						String name = data.getChildText("idxName");
						String value = data.getChildText("idxValue");

						hashMapData.put(name, value);

					}

					hashMapDataXML.put(String.valueOf(i + 1), hashMapData);

				}

			}

		} catch (JDOMException ex) {
			System.out.println(ex.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("XML hashMapData : " + hashMapDataXML);
		log.info("XML hashMapData : " + hashMapDataXML);

		return hashMapDataXML;
	}

}
