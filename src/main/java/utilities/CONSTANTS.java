package utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("serial")
public final class CONSTANTS {
	public static final String VALIDATION_SUCCESS = "SUCCESS";
	public static final String VALIDATION_FAILED = "FAILED";
	public static final String COMMA = ",";
	
	public final class ERROR_TYPE {
		public static final String UNZIP_FAILURE = "Unable to unzip batch file";
		public static final String NO_METADATA = "No Metadata";
		public static final String NO_CONTENT = "No Content";
		public static final String MISSING_METADATA = "Partially Missing Metadata";
		public static final String MISSING_CONTENT = "Partially Missing Content";
		public static final String EMPTY_FOLDER = "Empty Folder";
	}
	
	public final class JOB_TYPE {
		public static final String PDFENRICH = "pdfEnrichment-job";
		public static final String TEXTSEPERATEMETADATA = "textSeparateMetadataJob";
		public static final String MULTIFORMATJOB = "multiformatjob";
		public static final String TEXTEMBEDDEDMETADA = "textEmbeddedMetadataJob";
		public static final String AFPPDF = "afpPdfBatch";
		public static final String COSMOSPDF = "cosmosPdfEnrichment-pdf";
		public static final String LCDTOPDF = "lcdsToPdfjob";
		public static final String TIFTOPDF = "tifToPdfjob";
	}

	public static final HashMap<String, String> H01 = new HashMap<String, String>() {
		{
		
					put("DATE", "6:16");
					put("IIROUTEDFROM", "16:19");
					put("IROUTEDFROM", "19:22");
					put("IVROUTEDFROM", "22:25");
					put("IIIROUTEDFROM", "40:43");
					put("IIROUTEDTO", "43:46");
					put("IROUTEDTO", "46:49");
					put("IVROUTEDTO", "49:52");
					put("IIIROUTEDTO", "67:70");
		
				

		}
	};

	public static final String SPACE=" ";
	public final class METADATA {
		public static final String SUFFIX = "_0000001";
		public static final String EXTENSION1 = ".metadata";
		public static final String EXTENSION2 = ".METADATA";
	}

	public final class CONTENT {
		public static final String PROCESSED_EXT = ".processed";
		public static final String EXTENSION = ".txt";
	}

	public final class STATUS {
		public static final String PROCESSING = "Processing";
		public static final String PARTIALCOMPLETE = "Partial";
		public static final String FAILED = "Failed";
		public static final String DUPLICATE = "Duplicate";
		public static final String CORRUPTED = "Corrupted";
		public static final String COMPLETED = "Completed";
		public static final String FATAL = "Fatal";
		public static final String WARNING = "Warning";
	}

	public final class CODE {
		public static final String SYSTEM_FAILURE_NO_ERROR = "1001";
		public static final String SYSTEM_FAILURE = "1002";
		public static final String UNZIP_FAILURE = "1004";
		public static final String NO_METADATA = "1005";
		public static final String NO_CONTENT = "1006";
		public static final String MISSING_CONTENT = "1007";
		public static final String MISSING_METADATA = "1008";
		public static final String RENAME_METADATA_OR_CONTENT_FILE_FAILURE = "1012";
		public static final String COPY_CONTENT_FILE_FAILURE = "1013";
		public static final String DISPATCH_ERROR = "1014";
		public static final String DISPATCH_NO_RESP = "1015";
		public static final String DISPATCH_UNSUCCESSFUL = "1016";
		public static final String EMPTY_FOLDER = "1024";
	}

	public final class FILE_EXTENSION {
		public static final String ZIP = ".zip";
		public static final String METADATA = ".metadata";
		public static final String PROCESSED = ".processed";
		public static final String TEXT = ".txt";
		public static final String TEMPSTRING = "renamed";
		public static final String DOT = ".";
	}

	public final class EXCEPTION_DESC {
		public static final String UNZIP_FAILURE = "Unable to unzip batch file from input folder to temp folder";
		public static final String NO_METADATA = "Zip folder contains no metadata files";
		public static final String NO_CONTENT = "Zip folder contains no content files";
		public static final String MISSING_METADATA = "Content file doesn't have an associated metadata file";
		public static final String MISSING_CONTENT = "Metadata file doesn't have an associated content file";
		public static final String EMPTY_FOLDER = "Unzipped folder contained zero files";
	}

	public final class ENRICHAFPXML {
		private ENRICHAFPXML() {
		}

		public static final String CONTENT_TYPE_KEY = "a_content_type";
		public static final String CONTENT_TYPE_VALUE = "PDF";
		public static final String FULL_TEXT_KEY = "a_full_text";
		public static final String FULL_TEXT_VALUE = "FALSE";
		public static final String OBJECT_NAME_KEY = "object_name";
		public static final String CONTENT_SIZE_KEY = "r_content_size";
		public static final String PAGE_COUNT_KEY = "r_page_cnt";
		public static final String CREATION_DATE_KEY = "u_orig_creation_date";
		public static final String GLOBAL_DOC_ID_KEY = "u_gbl_doc_id";
		public static final String COMPOUND_SEQ_KEY = "u_compound_seq";
		public static final String COMPOUND_ID_KEY = "u_compound_doc";
		public static final String ZIPCONSTANT = ".zip";
		public static final String UNDERSCORE_VALUE = "_";
		public static final String ONE_CONSTANT = "00001";
		public static final String EXTENSION_METADATA = ".metadata";
		public static final String EXTENSION_PDF = ".pdf";
		public static final String EXTENSION_XML1 = ".xml";
		public static final String EXTENSION_XML2 = ".XML";
		public static final String ENTENSION_AFP1 = ".afp";
		public static final String ENTENSION_AFP2 = ".AFP";
		public static final String DATEFORMAT = "yyyy-MM-dd";
		public static final String DF_MONTHH_DATE_YR = "MM/dd/yyyy";
		public static final String DF_MONTH_DATE_YR = "MMMM dd, yyyy";
		//F299998-US1796073
		public static final String DF_HYPHENATED_MONTH_DAY_YR = "MM-dd-yyyy";
		public static final String DF_HYPHENATED_YR_MONTH_DAY = "yyyy-MM-dd";
		
	}

	
	public static final HashMap<String, Object> cosiMap = new HashMap<String, Object>() {
		{
			put("DIV", "u_div");
			put("MASTERGROUP", "u_master_grp");
			put("INVOICENUMBER", "u_invoice_nbr");
			put("INVOICEDATE", "u_invoice_dt");
		}
	};

	public static final HashMap<String, Object> billMap = new HashMap<String, Object>() {
		{
			put("LEGALENTITY", "u_legal_entity");
			put("DIV", "u_division");
			put("INVOICENUMBER", "u_invoice_nbr");
			put("MASTERGROUP", "u_master_group");
			put("DATE", "u_dt");
		}
	};

/*	public static final HashMap<String, Object> nascMap = new HashMap<String, Object>() {
		{
			put("LETTERID", "u_letter_id");
			put("DATE", "u_dt");
			put("ORSID", "u_ors_id");
			put("GROUPID", "u_group_nbr");
			put("TRACKINGNO", "u_tracking_nbr");
			put("NAME", "u_name");
			put("ENVELOPEID", "u_envelope_id");
			put("EMPLOYEEID", "u_emp_id");

		}
	};

	public static final HashMap<String, Object> multMap = new HashMap<String, Object>() {
		{
			put("LETTERTYPE", "u_letter_type");
			put("PATIENTNAME", "u_patient_fn_ln");
			put("PHYSICIANNAME", "u_physician_provider_nm");
			put("MAILCODE", "u_recip_data_mail_cd");
			put("SUBJECT", "u_subject");
			put("PROVIDERTAXID", "u_physician_provider_tax_id");
			put("POLICYNBR", "u_policy_nbr");
			put("ELGSLETTERGENID", "u_ltr_gen_req_id");
			put("PROCESSDATE", "u_process_ltr_dt");
			put("EMPLOYEEID", "u_emp_id");
			put("EMPLOYEENAME", "u_emp_fn_ln");
			put("ICNNUMBER", "u_icn_nbr");

		}
	};*/

	public static final HashMap<String, Object> coseMap = new HashMap<String, Object>() {
		{
			put("DIV", "u_div");
			put("POLICYNUMBER", "u_policy_nbr");
			put("FLEXPATIENTID", "u_flex_patient_id");
			put("EOBDATE", "u_eob_dt");
			put("POLICY", "u_policy");
			put("FLEXEOBDATE", "u_flex_eob_dt");
			put("FLEXCLAIMNUMBER", "u_flex_claim_nbr");
			put("CLAIMNUMBER", "u_claim_nbr");
			put("ALTPATIENTID", "u_patient_id");

		}
	};

	public static final HashMap<String, Object> cospMap = new HashMap<String, Object>() {
		{
			put("DIV", "u_div");
			put("PROVIDERNUMBER", "u_provider_nbr");
			put("CHECKNUMBER", "u_check_nbr");
			put("CLAIMNUMBER", "u_claim_nbr");
			put("TIN", "u_tin");
			put("CHECKDATE", "u_check_dt");
			put("PTRS", "u_ptrs");

		}
	};

	public static final HashMap<String, String> docClass = new HashMap<String, String>() {
		{
			put("bill", "u_grp_bil_invc");
			put("cosi", "u_cosmos_aso_invc");
			//put("mult", "u_mli_elgs");
			//put("nasc", "u_nasc_salsa_ltr");
			put("cose", "u_cosmos_eob");
			put("cosp", "u_cosmos_pra");
			//put("prov", "u_eni_prov_vld_ltr");
			//put("sbpr", "u_sb_dental_pra");
			//put("sbme", "u_sb_dental_member_eob");
			//put("sbmc", "u_sb_miss_clm_info_ltr");
		}
	};

	public static final HashMap<String, List<String>> multiKey = new HashMap<String, List<String>>() {
		{
			ArrayList<String> cosp = new ArrayList();
			cosp.add("CLAIMNUMBER");
			cosp.add("DIV");
			ArrayList<String> cose = new ArrayList<>();
			cose.add("CLAIMNUMBER");
			cose.add("FLEXCLAIMNUMBER");
			put("u_cosmos_pra", cosp);
			put("u_cosmos_eob", cose);

		}
	};

	public final class ENRICHPDFXML {

		private ENRICHPDFXML() {
		}

		public static final String CONTENT_TYPE_KEY = "a_content_type";
		public static final String CONTENT_TYPE_VALUE = "PDF";
		public static final String FULL_TEXT_KEY = "a_full_text";
		public static final String FULL_TEXT_VALUE = "FALSE";
		public static final String OBJECT_NAME_KEY = "object_name";
		public static final String CONTENT_SIZE_KEY = "r_content_size";
		public static final String PAGE_COUNT_KEY = "r_page_cnt";
		public static final String CREATION_DATE_KEY = "u_orig_creation_date";
		public static final String GLOBAL_DOC_ID_KEY = "u_gbl_doc_id";
		public static final String COMPOUND_SEQ_KEY = "u_compound_seq";
		public static final String COMPOUND_ID_KEY = "u_compound_doc";
		public static final String EXTENSION_PDF1 = ".pdf";
		public static final String EXTENSION_PDF2 = ".PDF";
		public static final String EXTENSION_Metadata = ".metadata";
		public static final String EXTENSION_METADATA = ".METADATA";

	}
	
	public final class ENRICHMULTIFORMATXML {

		private ENRICHMULTIFORMATXML() {
		}

		public static final String CONTENT_TYPE_KEY = "a_content_type";
		public static final String FULL_TEXT_KEY = "a_full_text";
		public static final String FULL_TEXT_VALUE = "FALSE";
		public static final String OBJECT_NAME_KEY = "object_name";
		public static final String CONTENT_SIZE_KEY = "r_content_size";
		public static final String PAGE_COUNT_KEY = "r_page_cnt";
		public static final String CREATION_DATE_KEY = "u_orig_creation_date";
		public static final String GLOBAL_DOC_ID_KEY = "u_gbl_doc_id";
		public static final String COMPOUND_SEQ_KEY = "u_compound_seq";
		public static final String COMPOUND_ID_KEY = "u_compound_doc";
		public static final String EXTENSION_Metadata = ".metadata";
		public static final String EXTENSION_METADATA = ".METADATA";
		public static final String EXTENSION_DOCX = ".docx";
		public static final String EXTENSION_DOC = ".doc";
		public static final String EXTENSION_DOT = ".dot";
		public static final String EXTENSION_SUMMARY = ".summary";
		public static final String EXTENSION_PDF = "pdf";
	}

	public final class RRDJ {
		private RRDJ() {
		}

		public static final int PAGE_NO_INDEX = 34;
		public static final int PAGE_NO_LENGTH = 10;
		public static final String SUMMARY_TAG = "38-3";
		public static final int COUNT_START_INDEX = 161;
		public static final int COUNT_END_INDEX = 171;
		public static final int SUMM_START_INDEX = 24;

	}

	public final class RREJ {
		private RREJ() {
		}

		public static final int PAGE_NO_INDEX = 36;
		public static final int PAGE_NO_LENGTH = 10;
		public static final String SUMMARY_TAG = "SMRY";
		public static final int COUNT_START_INDEX = 163;
		public static final int COUNT_END_INDEX = 173;
		public static final int SUMM_START_INDEX = 26;
	}

	public final class RACR {
		private RACR() {
		}

		public static final int PAGE_NO_INDEX = 113;
		public static final int PAGE_NO_LENGTH = 10;
		public static final int COUNT_START_INDEX = 240;
		public static final int COUNT_END_INDEX = 250;
	}

	public final class RRTM {
		private RRTM() {
		}

		public static final int PAGE_NO_INDEX = 57;
		public static final int PAGE_NO_LENGTH = 10;
		public static final int COUNT_START_INDEX = 184;
		public static final int COUNT_END_INDEX = 194;
	}

	public final class REIC {
		private REIC() {
		}

		public static final int PAGE_NO_INDEX = 43;
		public static final int PAGE_NO_LENGTH = 10;
		public static final int COUNT_START_INDEX = 170;
		public static final int COUNT_END_INDEX = 180;
	}

	public final class RAHP {
		private RAHP() {
		}

		public static final int PAGE_NO_INDEX = 10;
		public static final int PAGE_NO_LENGTH = 10;
		public static final int COUNT_START_INDEX = 137;
		public static final int COUNT_END_INDEX = 147;
	}

	public final class LCDS {
		private LCDS() {
		}

		public static final String LCDSATTRIBUTECOMP = "LCD";
		public static final String DIRECTORYCONST = "_dir/";
		public static final String PDFEXTENSION = ".pdf";
		public static final String METADATAEXTN = ".metadata";
		public static final String PDFSTRING = "PDF";
		public static final String FALSE = "FALSE";
		public static final String UNDESCORESTRING = "_";

		// LCDS Page related constants
		public static final String PAGECONST = "page";
		public static final String BLANKCONST = "";
		public static final String STRINGONECONST = "1";
		public static final String APPLICATIONID = "Doc360";
		public static final String PHSIDOCCLASS = "u_phs_invoice";
		public static final String CUSTNOSTRING = "customer no:";
		public static final String CUSTNOTAG = "u_customer_nbr";
		public static final String INVOICENOSTRING = "invoice no:";
		public static final String INVOICENOTAG = "u_invoice_nbr";
		public static final String ACCOUNTNOSTRING = "account no:";
		public static final String ACCOUNTNOTAG = "u_accountt_nbr";
		public static final String DUEDATESTRING = "coverage pd:";
		public static final String DUEDATETAG = "u_coverage_period";
		public static final String JOBIDTAG = "u_job_id";
		public static final String CUSTOMERNAMETAG = "u_customer_name";
		public static final String INVOICEDETAILSTRING = "Invoice Detail";
		public static final String GROUPHASHSTRING = "Group #";
		public static final String TOTALSTRING = "Total";
		public static final String CONTENTTYPETAG = "a_content_type";
		public static final String FULLTEXTTAG = "a_full_text";
		public static final String OBJECTNAMETAGE = "object_name";
		public static final String CONTENTSIZETAG = "r_content_size";
		public static final String PAGECOUNTTAG = "r_page_cnt";
		public static final String CREATIONDATETAG = "u_orig_creation_date";
		public static final String GLOBALDOCIDTAG = "u_gbl_doc_id";
		public static final String COMPOUNDDOCTAG = "u_compound_doc";
		public static final String COMPOUNDSEQTAG = "u_compound_seq";
		public static final String GROUPNOTAG = "u_group_nbr";
		public static final String DATEFORMAT = "yyyy-MM-dd";

		// SRSI Page related constants
		public static final String SHEETSTRING = "sheet:";
		public static final String ONEOFSTRING = "1 of";
		public static final String SRSIDOCCLASS = "u_srs_invc";
		public static final String INVOICESTRING = "invoice:";
		public static final String ACCOUNTSTRING = "account:";
		public static final String ACCOUNTTAG = "u_acct_nbr";
		public static final String DATESTRING = "date:";
		public static final String DATETAG = "u_dt";
		public static final String COMSEQCONST = "0000001";

	}

public final class TIFF_CONSTANTS {
	public static final String EXTENSION_TIF1 = ".tif";
}
}
