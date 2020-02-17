package utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import com.google.gson.Gson;

import uniqueIdDataFromDB.DocClassAutomation;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;

import com.google.gson.Gson;
import utilities.DocClassProperties;

import utilities.CONSTANTS;

import utilities.Documents.Document.Indices;

import utilities.Documents.Document;
import utilities.Documents.Document.Indices.IndexField;

public abstract class MultiKeyConvert {

	public String docClass;
	public HashMap modAttributes;
	public String docClassName;
	public List<String> multiKList;

	public String callMultiKeyConvert(File metadataFile, String docClass, String jobName, Long jobExecutionId,
			String parentId, String filePath) throws Exception {

		String pdfPath = metadataFile.getAbsolutePath().replace(".metadata", ".pdf");
		File pdfFile = new File(pdfPath);
		// docClass = "CDC_TESTSBPR";//DOCMKR_GRPDOC5
		this.docClass = docClass;

		/*
		 * if (!pdfFile.exists()) throw new Exception("PDF File does not exists " +
		 * StringUtils.substringAfterLast(pdfPath, File.separator)); PDDocument
		 * document=null; document = PDDocument.load(pdfFile);
		 */

		HashMap<String, Object> addAttributes = new HashMap<String, Object>();
		HashMap<String, Object> modifyAttributes = new HashMap<String, Object>();
		// String inputSource ="docmkr";

		/*
		 * addAttributes.put(CONSTANTS.ENRICHPDFXML.CONTENT_TYPE_KEY,
		 * CONSTANTS.ENRICHPDFXML.CONTENT_TYPE_VALUE);
		 * addAttributes.put(CONSTANTS.ENRICHPDFXML.FULL_TEXT_KEY,
		 * CONSTANTS.ENRICHPDFXML.FULL_TEXT_VALUE);
		 * //addAttributes.put(CONSTANTS.ENRICHPDFXML.GLOBAL_DOC_ID_KEY,
		 * UUID.randomUUID().toString());
		 * addAttributes.put(CONSTANTS.ENRICHPDFXML.OBJECT_NAME_KEY,
		 * metadataFile.getName().replace(".metadata", ".pdf"));
		 * addAttributes.put(CONSTANTS.ENRICHPDFXML.CONTENT_SIZE_KEY,
		 * Long.toString(metadataFile.length()));
		 * addAttributes.put(CONSTANTS.ENRICHPDFXML.CREATION_DATE_KEY,
		 * DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now()));
		 * addAttributes.put(CONSTANTS.ENRICHPDFXML.PAGE_COUNT_KEY,
		 * Integer.toString(document.getNumberOfPages()));
		 */

		DocClassAutomation dca = new DocClassAutomation();
		DocClassObject docObj = dca.getDocClassObject(docClass.toUpperCase());

		modifyAttributes = new Gson().fromJson(docObj.getMetadataJson(), HashMap.class);

		this.modAttributes = modifyAttributes;

		String status = "";
		try {
			status = xmlMerge(metadataFile, addAttributes, modAttributes, docClass);
		} catch (Exception e) {
			String fileName = parseFileName(metadataFile);
			status = CONSTANTS.VALIDATION_FAILED.concat("Incorrect xml format : "
					.concat(fileName.substring(fileName.lastIndexOf(File.separator) + 1, fileName.length())));
		}
		return status;
	}

	public String parseFileName(File file) {
		String extension = ".".concat(getFileExtension(file));
		String fileName = file.toString();
		if (fileName.contains(File.separator)) {
			fileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1, fileName.length());
		}
		fileName = StringUtils.substringBeforeLast(fileName, "_");
		return fileName.concat(extension);
	}

	public String getFileExtension(File givenFile) {
		return FilenameUtils.getExtension(givenFile.getName());
	}

	List<Document> docList = new ArrayList<>();
	private String xmlMerge(File xmlFilePath, HashMap addAttributes, HashMap modifyAttributes, String docClass)
			throws Exception {

		docList = new ArrayList<>();
		
		//JAXBContext context =  getJaxBContext();
		XMLInputFactory xif = XMLInputFactory.newFactory();
		xif.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
		// xif.setProperty(XMLInputFactory.SUPPORT_DTD, false);
		XMLStreamReader xsr = xif.createXMLStreamReader(new StreamSource(xmlFilePath));
		
		String validationResult=decideExecution(xmlFilePath,  addAttributes,  modifyAttributes, docClass,xsr);

		// Document document = (Document) unmarshaller.unmarshal(xsr);
		
		FileUtils.deleteQuietly(xmlFilePath);
		return validationResult;

	}
	public abstract String decideExecution(File xmlFilePath, HashMap addAttributes, HashMap modifyAttributes, String docClass,XMLStreamReader xsr) throws Exception;
	//public abstract JAXBContext  getJaxBContext() throws JAXBException;
	
	public String processXMLCoversion(Document document,File xmlFilePath, HashMap addAttributes, HashMap modifyAttributes, String docClass,JAXBContext context,int documentsSize) throws Exception {
		String validationResult = "";
		//List<Document> docList = new ArrayList<>();
		List<IndexField> multiKeyList = new ArrayList<>();

		Indices indices = document.getIndices();
		List<IndexField> indexList = indices.getIndexField();

		Iterator<IndexField> iterator = indexList.iterator();

		IndexField idxField = null;
		DocClassAutomation dca = new DocClassAutomation();
		multiKList = new ArrayList<String>(dca.getDocClassObject(docClass.toUpperCase()).getMultiKey());
		dca.getDocClassObject(docClass.toUpperCase());
		DocClassObject dc = dca.getDocClassObject(docClass.toUpperCase());
		docClassName = dc.getDocClasName();
		// multiKList=dc.getMultiKey();
		// DocClassObject docObj = dca.getDocClassObject(docClass.toUpperCase());
		// multiKList = docObj.getMultiKey();

		// List<String> multiKList = multiKeyListData;

		if (!multiKList.isEmpty()) {
			try {
				multiKList.removeAll(convertSingleToMultiKey(indexList, multiKList));
			} catch (Exception e) {
				System.out.println(e.getMessage().concat(", file : ").concat(xmlFilePath.getName()));

				return CONSTANTS.VALIDATION_FAILED
						.concat(e.getMessage().concat(", file : ").concat(xmlFilePath.getName()));
			}
		}

		while (iterator.hasNext()) {
			idxField = iterator.next();

			if (!multiKList.isEmpty() && isFieldPresent(idxField)) {
				multiKeyList.add(idxField);
				iterator.remove();
			}

		}

		if (!multiKList.isEmpty()) {
			String compoundIdValue = UUID.randomUUID().toString().replace("-", "");
			addAttributes.put(CONSTANTS.ENRICHPDFXML.COMPOUND_ID_KEY, compoundIdValue);
			if (multiKeyList.isEmpty()) {
				addAttributes.put(CONSTANTS.ENRICHPDFXML.COMPOUND_SEQ_KEY, "1");
			}
		}

		if (multiKeyList.isEmpty() && documentsSize == 1) {
			int metadataIndex = xmlFilePath.getAbsolutePath().indexOf(".metadata");
			if (metadataIndex <= 0) {
				metadataIndex = xmlFilePath.getAbsolutePath().toLowerCase().indexOf(".xml");
			}
			String renamedName = xmlFilePath.getAbsolutePath().substring(0, metadataIndex).concat("_0000001")
					.concat(".metadata");
			File renamedFile = new File(renamedName);
			boolean renamed = xmlFilePath.renameTo(renamedFile);
			if (!renamed) {
				throw new Exception("Exception while renaming the file :" + xmlFilePath.getName());
			}
			docList.add(addModifyElements(document, addAttributes, modifyAttributes,
					dca.getDocClassObject(docClass.toUpperCase()).getDocClasName(), context));

		} else {
			docList.addAll(
					convertMultiKey(document, multiKeyList, multiKList, xmlFilePath.getAbsolutePath(), context));
		}
		int sequenceNum = 0;
		for (Document marsDoc : docList) {
			sequenceNum++;
			String pdfName = getStrictFileNameWithoutBaseDirectory(xmlFilePath.getAbsolutePath());
			String sequence = String.format("%07d", sequenceNum);
			String xmlName = pdfName + CONSTANTS.ENRICHAFPXML.UNDERSCORE_VALUE + sequence
					+ CONSTANTS.ENRICHAFPXML.EXTENSION_METADATA;
			Path outputXmlPath = Paths.get(getFileParentPath(xmlFilePath).toString(), xmlName);
			try (FileOutputStream fos = new FileOutputStream(outputXmlPath.toFile())) {
				Marshaller marshaller = context.createMarshaller();
				marshaller.marshal(marsDoc, fos);
			}
			///////////////////// Commeted ////////////
			// validationResult = pdfMetadataValidator.validate(docClass, jobName,
			///////////////////// jobExecutionId, parentId, filePath, marsDoc, xmlName);
			///////////////////////////////////////////////////
			// we are modifying doc in validation, re marshling the XML
			try (FileOutputStream fos = new FileOutputStream(outputXmlPath.toFile())) {
				Marshaller marshaller = context.createMarshaller();
				marshaller.marshal(marsDoc, fos);
			}
		}
		return validationResult;
	}

	public String getFileParentPath(File givenFile) {
		return givenFile.getParentFile().getAbsolutePath();
	}

	private boolean isFieldPresent(IndexField idxField) throws Exception {
		DocClassAutomation dca = new DocClassAutomation();
		List<String> indexKeyArray = new ArrayList<String>(dca.getDocClassObject(docClass.toUpperCase()).getMultiKey());
		// List<String> indexKeyArray =
		// DocClassProperties.docClassMapping.get(docClass).getMultiKey();
		for (String key : indexKeyArray) {
			if (idxField.getIdxName().matches(key.concat("\\d+"))) {
				return true;
			}
		}
		return false;
	}

	private List<Document> convertMultiKey(Document doc, List<IndexField> multiKeyList, List<String> multiKListForDoc,
			String xmlFilePath, JAXBContext context) throws JAXBException {
		List<Document> documentsList = new ArrayList<Document>();
		int sequenceNum = 0;
		String sequence;
		String pdfName = getStrictFileNameWithoutBaseDirectory(xmlFilePath);
		String validationResult = "";
		Document enrinchedDoc;
		HashMap addAttributes = new HashMap<String, String>();
		do {
			sequenceNum++;
			sequence = String.format("%07d", sequenceNum);
			addAttributes.put("u_compound_seq", sequence);

			if (!multiKeyList.isEmpty()) {
				if (sequenceNum - 2 >= 0) {
					doc.getIndices().getIndexField()

							.removeAll(getMatchingFieldsForContentGroup(doc.getIndices().getIndexField(),
									multiKListForDoc));

				}
				doc.getIndices().getIndexField()
						.addAll(getMatchingFieldsFromList(multiKeyList, Integer.toString(sequenceNum)));
			}

			removeNumbersFromIndexKey(doc);

			enrinchedDoc = addModifyElements(doc, addAttributes, modAttributes, docClassName, context);

			// enrinchedDoc = ;
			documentsList.add(enrinchedDoc);

		} while (!multiKeyList.isEmpty());

		return documentsList;
	}

	public static String getStrictFileNameWithoutBaseDirectory(String xmlFilePath) {
		return FilenameUtils.removeExtension(FilenameUtils.getName(xmlFilePath));
	}

	private List<IndexField> getMatchingFieldsForContentGroup(List<IndexField> document,
			List<String> multiKListForDoc) {

		List<IndexField> matchedFields = new ArrayList<IndexField>();

		for (int i = 0; i < document.size(); i++) {
			for (String key : multiKListForDoc) {
				if (document.get(i).getIdxName().matches(key)) {
					matchedFields.add(document.get(i));
				}
			}
		}
		return matchedFields;
	}

	private List<IndexField> getMatchingFieldsFromList(List<IndexField> document, String regex) {
		List<IndexField> matchedFields = new ArrayList<IndexField>();
		for (int i = 0; i < document.size(); i++) {
			if (document.get(i).getIdxName().replaceAll("[^0-9]", "").equals(regex)) {
				matchedFields.add(document.get(i));
			}
		}
		document.removeAll(matchedFields);
		return matchedFields;
	}

	private Document removeNumbersFromIndexKey(Document doc) {

		List<String> indexKeyArray = multiKList;

		if (indexKeyArray != null) {
			Indices indices = doc.getIndices();
			List<IndexField> indexList = indices.getIndexField();
			Iterator<IndexField> iterator = indexList.iterator();
			IndexField idxField = null;

			while (iterator.hasNext()) {
				idxField = iterator.next();
				for (String key : indexKeyArray) {
					if (idxField.getIdxName().matches(key.concat("\\d+"))) {
						idxField.setIdxName(key);
						break;
					}
				}
			}
		}
		return doc;
	}

	public Document addModifyElements(Document doc, HashMap addIndexs, HashMap modifyIndexs, String docClass,
			JAXBContext context) throws JAXBException {
		Document document = SerializationUtils.clone(doc);
		addIndexs.put(CONSTANTS.ENRICHPDFXML.GLOBAL_DOC_ID_KEY, UUID.randomUUID().toString());
		Indices indices = document.getIndices();
		document.setDocumentClass(docClass);
		// document.setDatagroup(null);
		// document.setFileFormat(null);
		List<IndexField> indexList = indices.getIndexField();

		Set<String> modifyIdx = modifyIndexs.keySet();
		// Modified for sonar.Added curly braces and indentation
		modifyIdx.forEach(indx -> {
			indexList.forEach(index -> {
				if (index.getIdxName().equals(indx)) {
					index.setIdxName((String) modifyIndexs.get(indx));
					if (index.getIdxValue() != null) {
						if (index.getIdxName().equals("u_subject") && docClass.equals("u_mli_elgs")) {
							index.setIdxValue(index.getIdxValue().toUpperCase());
						}
						index.setIdxValue(index.getIdxValue().trim());
					}
					if (((String) modifyIndexs.get(indx)).contains("_dt")
							|| ((String) modifyIndexs.get(indx)).contains("_date")) {
						if (index.getIdxValue() != null) {
							index.setIdxValue(convertDateFromString(index.getIdxValue()));
						}
					}
				}
			});
		});

		for (IndexField index : indexList) {
			if (index.getIdxName().equals(CONSTANTS.ENRICHPDFXML.CREATION_DATE_KEY)) {
				addIndexs.remove(CONSTANTS.ENRICHPDFXML.CREATION_DATE_KEY);
				if (convertDateFromString(index.getIdxValue()).equals("")) {
					index.setIdxValue(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now()));
				}
			}
		}

		Set<String> addIdx = addIndexs.keySet();

		for (String addIndx : addIdx) {
			IndexField field = new IndexField();
			field.setIdxName(addIndx);
			field.setIdxValue((String) addIndexs.get(addIndx));

			indexList.add(field);
		}

		return document;
	}

	public String convertDateFromString(String date) {

		try {
			Date convertedDate;
			String formattedDate = "";
			if (date.contains(":")) {
				int index = date.indexOf(":") - 3;
				String date2 = date.substring(0, index).trim();
				String time = date.substring(index);
				if (date2.matches("\\d{4}\\-\\d{2}\\-\\d{2}")) {
					// convertedDate = new SimpleDateFormat("MM/dd/yyyy").parse(date);
					formattedDate = date2;
				} else if (date2.matches("\\d{5}")) {
					convertedDate = new SimpleDateFormat("yyDDD", java.util.Locale.getDefault()).parse(date2);
					// Added for sonar issue fix
					formattedDate = new SimpleDateFormat(CONSTANTS.ENRICHAFPXML.DATEFORMAT,
							java.util.Locale.getDefault()).format(convertedDate);
				} else if (date2.matches("\\d{2}\\/\\d{2}\\/\\d{4}")) {
					convertedDate = new SimpleDateFormat(CONSTANTS.ENRICHAFPXML.DF_MONTHH_DATE_YR,
							java.util.Locale.getDefault()).parse(date2);
					formattedDate = new SimpleDateFormat(CONSTANTS.ENRICHAFPXML.DATEFORMAT,
							java.util.Locale.getDefault()).format(convertedDate);
					// logger.info("Converting MM/dd/YYYY {} to {}", date, formattedDate);
				} else if (date2.matches("\\d{6}")) {
					convertedDate = new SimpleDateFormat("yyMMdd", java.util.Locale.getDefault()).parse(date2);
					formattedDate = new SimpleDateFormat(CONSTANTS.ENRICHAFPXML.DATEFORMAT,
							java.util.Locale.getDefault()).format(convertedDate);
					// logger.info("Converting yyMMdd {} to {}", date, formattedDate);

				}
				// F299998-US1796073
				else if (date2.matches("\\d{4}-\\d{2}-\\d{2}")) { /* yyyy-MM-dd */
					convertedDate = new SimpleDateFormat(CONSTANTS.ENRICHAFPXML.DF_HYPHENATED_YR_MONTH_DAY,
							java.util.Locale.getDefault()).parse(date2);
					formattedDate = new SimpleDateFormat(CONSTANTS.ENRICHAFPXML.DATEFORMAT,
							java.util.Locale.getDefault()).format(convertedDate);
				} else if (date2.matches("\\d{2}-\\d{2}-\\d{4}")) {/* MM-dd-yyyy */
					convertedDate = new SimpleDateFormat(CONSTANTS.ENRICHAFPXML.DF_HYPHENATED_MONTH_DAY_YR,
							java.util.Locale.getDefault()).parse(date2);
					formattedDate = new SimpleDateFormat(CONSTANTS.ENRICHAFPXML.DATEFORMAT,
							java.util.Locale.getDefault()).format(convertedDate);
				} else if (date2.matches(
						"(JAN(UARY)?|FEB(RUARY)?|MAR(CH)?|APR(IL)?|MAY|JUN(E)?|JUL(Y)?|AUG(UST)?|SEP(TEMBER)?|OCT(OBER)?|NOV(EMBER)?|DEC(EMBER)?)\\s+\\d{1,2},\\s+\\d{4}")) {

					convertedDate = new SimpleDateFormat(CONSTANTS.ENRICHAFPXML.DF_MONTH_DATE_YR,
							java.util.Locale.getDefault()).parse(date2);
					formattedDate = new SimpleDateFormat(CONSTANTS.ENRICHAFPXML.DATEFORMAT,
							java.util.Locale.getDefault()).format(convertedDate);

				}
				formattedDate = formattedDate + time;
			} else {
				if (date.matches("\\d{4}\\-\\d{2}\\-\\d{2}")) {
					// convertedDate = new SimpleDateFormat("MM/dd/yyyy").parse(date);
					formattedDate = date;
				} else if (date.matches("\\d{5}")) {
					convertedDate = new SimpleDateFormat("yyDDD", java.util.Locale.getDefault()).parse(date);
					// Added for sonar issue fix
					formattedDate = new SimpleDateFormat(CONSTANTS.ENRICHAFPXML.DATEFORMAT,
							java.util.Locale.getDefault()).format(convertedDate);
				} else if (date.matches("\\d{2}\\/\\d{2}\\/\\d{4}")) {
					convertedDate = new SimpleDateFormat(CONSTANTS.ENRICHAFPXML.DF_MONTHH_DATE_YR,
							java.util.Locale.getDefault()).parse(date);
					formattedDate = new SimpleDateFormat(CONSTANTS.ENRICHAFPXML.DATEFORMAT,
							java.util.Locale.getDefault()).format(convertedDate);
					// logger.info("Converting MM/dd/YYYY {} to {}", date, formattedDate);
				} else if (date.matches("\\d{6}")) {
					convertedDate = new SimpleDateFormat("yyMMdd", java.util.Locale.getDefault()).parse(date);
					formattedDate = new SimpleDateFormat(CONSTANTS.ENRICHAFPXML.DATEFORMAT,
							java.util.Locale.getDefault()).format(convertedDate);
					// logger.info("Converting yyMMdd {} to {}", date, formattedDate);

				}
				// F299998-US1796073
				else if (date.matches("\\d{4}-\\d{2}-\\d{2}")) { /* yyyy-MM-dd */
					convertedDate = new SimpleDateFormat(CONSTANTS.ENRICHAFPXML.DF_HYPHENATED_YR_MONTH_DAY,
							java.util.Locale.getDefault()).parse(date);
					formattedDate = new SimpleDateFormat(CONSTANTS.ENRICHAFPXML.DATEFORMAT,
							java.util.Locale.getDefault()).format(convertedDate);
				} else if (date.matches("\\d{2}-\\d{2}-\\d{4}")) {/* MM-dd-yyyy */
					convertedDate = new SimpleDateFormat(CONSTANTS.ENRICHAFPXML.DF_HYPHENATED_MONTH_DAY_YR,
							java.util.Locale.getDefault()).parse(date);
					formattedDate = new SimpleDateFormat(CONSTANTS.ENRICHAFPXML.DATEFORMAT,
							java.util.Locale.getDefault()).format(convertedDate);
				} else if (date.matches(
						"(JAN(UARY)?|FEB(RUARY)?|MAR(CH)?|APR(IL)?|MAY|JUN(E)?|JUL(Y)?|AUG(UST)?|SEP(TEMBER)?|OCT(OBER)?|NOV(EMBER)?|DEC(EMBER)?)\\s+\\d{1,2},\\s+\\d{4}")) {

					convertedDate = new SimpleDateFormat(CONSTANTS.ENRICHAFPXML.DF_MONTH_DATE_YR,
							java.util.Locale.getDefault()).parse(date);
					formattedDate = new SimpleDateFormat(CONSTANTS.ENRICHAFPXML.DATEFORMAT,
							java.util.Locale.getDefault()).format(convertedDate);

				}
			}
			return formattedDate;
		} catch (Exception e) {
			System.out.println("Exception occur while converting julian date" + e);
			return "";
		}

	}

	private List<String> convertSingleToMultiKey(List<IndexField> indexList, List<String> multiKList) throws Exception {
		Set<IndexField> dupSet = indexList.stream().filter(idx -> Collections.frequency(indexList, idx) > 1)
				.collect(Collectors.toSet());
		List<String> removeMultiKey = new ArrayList<>();
		if (dupSet.size() > 0) {
			throw new Exception("Single key attributes appeared more than once :".concat(dupSet.toString()));
		}
		indexList.forEach(indexName -> {
			multiKList.forEach(indx -> {
				if (indx.equals(indexName.getIdxName())) {
					removeMultiKey.add(indx);
				}
			});
		});

		return removeMultiKey;
	}

}
