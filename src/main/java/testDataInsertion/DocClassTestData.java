package testDataInsertion;
import java.util.Map;

public class DocClassTestData {
	
	private int scenarioId;
	private String zipFile;
	private Map<Integer, String> caseIdDescMap;
	private int docClassId;
	private int docClassAttr;
	
	
	public DocClassTestData() {
		
	}
	
	
	
	public DocClassTestData(int scenarioId, String zipFile) {
		this.scenarioId = scenarioId;
		this.zipFile = zipFile;
	}

	public void setScenarioId(int scenarioId) {
		this.scenarioId = scenarioId;
	}



	public void setZipFile(String zipFile) {
		this.zipFile = zipFile;
	}



	


	public int getScenarioId() {
		return scenarioId;
	}

	public String getZipFile() {
		return zipFile;
	}

	public Map<Integer, String> getCaseIdDescMap() {
		return caseIdDescMap;
	}



	public void setCaseIdDescMap(Map<Integer, String> caseIdDescMap) {
		this.caseIdDescMap = caseIdDescMap;
	}
	
	



	public int getDocClassId() {
		return docClassId;
	}



	public void setDocClassId(int docClassId) {
		this.docClassId = docClassId;
	}



	public int getDocClassAttr() {
		return docClassAttr;
	}



	public void setDocClassAttr(int docClassAttr) {
		this.docClassAttr = docClassAttr;
	}



	@Override
	public String toString() {
		return "DocClassTestData [scenarioId=" + scenarioId + ", zipFile=" + zipFile + ", caseIdDescMap="
				+ caseIdDescMap + ", docClassId=" + docClassId + ", docClassAttr=" + docClassAttr + "]";
	}



	

	
}
