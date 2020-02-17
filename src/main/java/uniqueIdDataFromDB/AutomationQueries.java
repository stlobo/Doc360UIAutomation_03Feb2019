package uniqueIdDataFromDB;

/**
 * @author jnaik3
 *
 */
public class AutomationQueries {

	public final static String TEST_DATA_ATTR_QUERY = "select doc_cls_ui_attr_label_nm, doc_cls_ui_attr_phys_nm, qa.qa_test_case_id, qa.qa_fld_ctrl_type, qa.qa_cond_ctrl, qa.qa_test_data \r\n"
			+ "from d3ui02.r_doc_cls_attr attr  \r\n"
			+ "inner join qa_doc_cls_test_data qa on qa.qa_doc_class_atrr_id =  attr.r_doc_cls_attr_id\r\n"
			+ "inner join d3ui02.r_srch_view_ui_attr actv on attr.r_doc_cls_attr_id = actv.r_doc_cls_attr_id \r\n"
			+ "where qa.qa_test_case_id = ? and qa.qa_test_scenario_id=? and qa.qa_doc_class_id=?   and actv.actv_ind = 'Y' \r\n"
			+ "and attr.doc_cls_ui_attr_label_nm not in ('Reset','Search')order by attr.r_doc_cls_id";
	
	public final static String TEST_DATA_DESCRIPTION_QUERY = "select qa_test_case_desc \r\n"
			+ "from qa_doc_cls_test_data   \r\n"
			+ "where qa_test_case_id = ? and qa_test_scenario_id=? and qa_doc_class_id=?   ";
			

	public final static String TEST_DATA_DOC_CLASS_QUERY = "\r\n"
			+ "select distinct qa.qa_doc_class_id,doc.doc_cls_desc, doc.doc_cls_name from qa_doc_cls_test_data qa\r\n"
			+ "inner join r_doc_cls doc\r\n" + "on qa.qa_doc_class_id = doc.r_doc_cls_id";

	public final static String TEST_DATA_SCENARIO_QUERY = "select distinct qa_test_scenario_id from qa_doc_cls_test_data where qa_doc_class_id=?";

	public final static String TEST_DATA_TEST_CASE_QUERY = "select distinct qa_test_case_id from qa_doc_cls_test_data where qa_test_scenario_id=? and qa_doc_class_id=?";

	public final static String TEST_DATA_RESULTSET_QUERY = "select cls.doc_cls_ui_attr_phys_nm,cls.doc_cls_ui_attr_label_nm\r\n"
			+ "FROM d3ui02.r_doc_cls_attr cls\r\n"
			+ "inner join d3ui02.r_rslt_view_ui_attr rls on cls.r_doc_cls_attr_id = rls.r_doc_cls_attr_id\r\n"
			+ "where cls.r_doc_cls_id =? and rls.actv_ind='Y'\r\n" + "order by rls.ui_grid_displ_order_seq";

	public final static String GET_PARENT_BATCH_ID = "Select parent_batch FROM reco01.master_recon where id=?";

	public final static String GET_FILENAME = "Select file_name from doc360_recon.t_batch_files where parent_id=?";

	public final static String TEST_DATA_METADATA_QUERY = "SELECT metadata_json\r\n"
			+ "FROM doc360_recon.r_batchtype_detail\r\n" + "where doc_cls_nm =?";
	
	public final static String GET_DOCCLASS_NAME_QUERY ="\r\n"
			+ "select distinct doc.doc_cls_name from qa_doc_cls_test_data qa\r\n"
			+ "inner join r_doc_cls doc\r\n" + "on qa.qa_doc_class_id = doc.r_doc_cls_id";

	public final static String GET_JOBTYPE_METADATATYPE_QUERY = "select batch_job_type, is_json from doc360_recon.r_batchtype_detail where doc_cls_nm =?";
	
	public final static String GET_PARENTBATCH_ID = "Select parent_id from doc360_recon.t_upstream_master where batch_name like ?";
	
	public final static String GET_BATCH_ID = "Select id FROM reco01.master_recon where data_group = ? and parent_batch=?";
	
	public final static String GET_DOC_CLASS_DETAIL="Select btch_pth, doc_cls_nm, batch_job_type, metadata_json, btch_tp_id, multikey from doc360_recon.r_batchtype_detail where btch_pth = ?";
	
	public final static String GET_SUMMARY_REQUIRED_STATUS="Select doc_cls_name from  doc360_recon.r_summary_config where doc_cls_name = ?";

	public final static String GET_META_DOC_CLASS_ID="SELECT  r_doc_cls_id FROM d3ui02.r_doc_cls\r\n" + 
			"where doc_cls_name = ?";
	
	public final static String GET_QA_TABLE_ATTRIBUTE_ID ="SELECT cls.r_doc_cls_id,dcls.r_doc_cls_attr_id\r\n" + 
			"FROM r_doc_cls_attr dcls\r\n" +                                                                                                                                                                     
			"inner join d3ui02.r_doc_cls cls  on dcls.r_doc_cls_id = cls.r_doc_cls_id\r\n" + 
			"inner join d3ui02.r_srch_view_ui_attr srch on dcls.r_doc_cls_attr_id =  srch.r_doc_cls_attr_id\r\n" + 
			"inner join d3ui02.r_rslt_view_ui_attr rslt on dcls.r_doc_cls_attr_id =  rslt.r_doc_cls_attr_id\r\n" + 
			"where dcls.r_doc_cls_id = ? and srch.actv_ind like 'Y' and rslt.actv_ind like 'Y' and srch.field_cntrl_typ like 'Text' and srch.ui_displ_order_seq >=0 and srch.ui_displ_order_seq <=5\r\n" + 
			"group by cls.r_doc_cls_id,dcls.r_doc_cls_attr_id\r\n" + 
			"order by srch.ui_displ_order_seq\r\n" + 
			"limit 0,2;\r\n";
	
	public final static String GET_ATTRIBUT_NAME_TAG ="select doc_cls_ui_attr_phys_nm\r\n" + 
			"FROM d3ui02.r_doc_cls_attr\r\n" + 
			"where r_doc_cls_attr_id = ?;\r\n";
	
	public final static String INSERT_TESTDATA_TABLE_RECORDS ="INSERT INTO d3ui02.qa_doc_cls_test_data\r\n" + 
			"(qa_test_scenario_id, qa_test_scenario_desc, qa_test_case_id, qa_test_case_desc, qa_doc_class_id, qa_doc_class_atrr_id, qa_fld_ctrl_type, qa_cond_ctrl, qa_test_data)\r\n" + 
			"VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);\r\n";
	
	public final static String DELETE_TESTDATA_TABLE_RECORDS ="DELETE FROM d3ui02.qa_doc_cls_test_data";
	
	public final static String GET_BATCH_PATH="Select btch_pth from doc360_recon.r_batchtype_detail where doc_cls_nm = ?";
	
	
	
	
}
