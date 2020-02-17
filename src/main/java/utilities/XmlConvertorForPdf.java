package utilities;

import java.io.File;
import java.util.HashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamReader;

import utilities.Documents.Document;

public class XmlConvertorForPdf extends MultiKeyConvert {
	
	/*
	 * public JAXBContext getJaxBContext() throws JAXBException { // TODO
	 * Auto-generated method stub JAXBContext context =
	 * JAXBContext.newInstance(Document.class); return context; }
	 */
	@Override
	public String decideExecution(File xmlFilePath, HashMap addAttributes, HashMap modifyAttributes, String docClass,XMLStreamReader xsr) throws Exception {
		JAXBContext context = JAXBContext.newInstance(Document.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		Document document = (Document) unmarshaller.unmarshal(xsr);
		// Multi-Key Started
	
			String validationResult=processXMLCoversion(document, xmlFilePath, addAttributes, modifyAttributes, docClass,context,0);
	
		return validationResult;
	}

}
