package utilities;

import java.io.File;
import java.util.HashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamReader;

import utilities.Documents.Document;

public class XmlConverterForAFP extends MultiKeyConvert {

	/*
	 * public JAXBContext getJaxBContext() throws JAXBException { // TODO
	 * Auto-generated method stub JAXBContext context =
	 * JAXBContext.newInstance(Documents.class); return context; }
	 */

	@Override
	public String decideExecution(File xmlFilePath, HashMap addAttributes, HashMap modifyAttributes, String docClass,XMLStreamReader xsr) throws Exception {
		// TODO Auto-generated method stub
		String validationResult="";
		JAXBContext context = JAXBContext.newInstance(Documents.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		Documents documents = (Documents) unmarshaller.unmarshal(xsr);
		// Multi-Key Started
		for (Document document : documents.getDocument()) {
			 validationResult=processXMLCoversion(document, xmlFilePath, addAttributes, modifyAttributes, docClass,context,documents.getDocument().size());
		}
		return validationResult;
	}

}
