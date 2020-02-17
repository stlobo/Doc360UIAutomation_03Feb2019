package utilities;

import java.util.List;

public class DocClassObject {

	private String btchPath;
	private String docClasName;
	private String batchJobType;
	private String metadataJson;
	private List<String> multiKey;
	private int parentId;
	private boolean isSummaryReq;
	
	public boolean isSummaryReq() {
		return isSummaryReq;
	}

	public void setSummaryReq(boolean isSummaryReq) {
		this.isSummaryReq = isSummaryReq;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getBtchPath() {
		return btchPath;
	}

	public void setBtchPath(String btchPath) {
		this.btchPath = btchPath;
	}

	public String getDocClasName() {
		return docClasName;
	}

	public void setDocClasName(String docClasName) {
		this.docClasName = docClasName;
	}

	public String getBatchJobType() {
		return batchJobType;
	}

	public void setBatchJobType(String batchJobType) {
		this.batchJobType = batchJobType;
	}

	public String getMetadataJson() {
		return metadataJson;
	}

	public void setMetadataJson(String metadataJson) {
		this.metadataJson = metadataJson;
	}

	@Override
	public String toString() {
		return "DocClassObject [btchPath=" + btchPath + ", docClasName=" + docClasName + ", batchJobType="
				+ batchJobType + ", metadataJson=" + metadataJson + ", parentId=" + parentId +"]";
	}

	public List<String> getMultiKey() {
		return multiKey;
	}

	public void setMultiKey(List<String> multiKey) {
		this.multiKey = multiKey;
	}

}
