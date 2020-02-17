package testDataInsertion;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
//import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import utilities.Utilities;

public class MetaDataXMLReader {

	public Map<String, String> readXMLMetaDataIndxVal(String fileName) {
		// HashMap<String, Map<String, String>> hashMapDataXML = new HashMap<String,
		// Map<String, String>>();
		HashMap<String, String> hashMapData = new HashMap<String, String>();
		try {
			// String filePathProperty = ReadPropertyFile.readPropertiesFile("filePath");
			/*
			 * String filePathProperty = Utilities.filePath; String filePath =
			 * filePathProperty + fileName;
			 */
			SAXBuilder builder = new SAXBuilder();
			File xmlFile = new File(fileName);

			Document document;

			document = (Document) builder.build(xmlFile);

			Element rootNode = document.getRootElement();

			List listDocument = new ArrayList<Object>();
			listDocument.add(rootNode);

			for (int i = 0; i < listDocument.size(); i++) {

				Element node = (Element) listDocument.get(i);

				List columns = node.getChildren("Indices");

				for (int j = 0; j < columns.size(); j++) {
					// HashMap<String, String> hashMapData = new HashMap<String, String>();
					Element column = (Element) columns.get(j);

					List indexFieldList = column.getChildren("IndexField");

					for (int k = 0; k < indexFieldList.size(); k++) {
						Element data = (Element) indexFieldList.get(k);

						String name = data.getChildText("idxName");
						String value = data.getChildText("idxValue");

						hashMapData.put(name, value);

					}

					// hashMapDataXML.put(String.valueOf(i + 1), hashMapData);

				}
			}

		} catch (JDOMException ex) {
			System.out.println(ex.getMessage());
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		return hashMapData;
	}
	
	
	public static org.w3c.dom.Document  getDoc(String fName) throws Exception{
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true); // never forget this!
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		File unZipPath = new File(Utilities.filePath);
		String filename = unZipPath + File.separator + fName;
		 //"steffi_test_pdf_40_0000001.metadata";
		
		 org.w3c.dom.Document doc = builder.parse(filename);
		 return doc;
	}
	
	public static boolean isDoumentsMetadata(String fName) throws Exception {
		boolean isDoumentsMetadata=false;

		org.w3c.dom.Document doc=getDoc(fName);
		
		
		if(doc.getFirstChild().getNodeName().equalsIgnoreCase("Documents")) {
			isDoumentsMetadata=true;
		}
		return isDoumentsMetadata;
	}
	

	public static String readXMLDocClassName(String fName, String valueToGet) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException,Exception {
		String xmlData;
		XPathFactory xpf = XPathFactory.newInstance();
		XPath xpath = xpf.newXPath();
		org.w3c.dom.Document doc= getDoc(fName);
		//System.out.println("doc : " + doc.getFirstChild().getNodeName());
		if(isDoumentsMetadata(fName)) {
			 xmlData = (String) xpath.evaluate("Documents/Document/"+valueToGet, doc, XPathConstants.STRING);
		}else {
			xmlData = (String) xpath.evaluate("Document/"+valueToGet, doc, XPathConstants.STRING);
		}
		//String docClassName = (String) xpath.evaluate("Documents/Document/"+valueToGet, doc, XPathConstants.STRING);
		

		return xmlData;
	}
	
	

}
