package testDataInsertion;

public class TestData {
	
	private int scenarioId;
	private String zipFile;
	private int testCaseId;
	private String testCaseDesc;
	private int docClassId;
	private int docClassAttr;
	private String ctrlType;
	private String condition;
	private String testData;
	
	
	public TestData() {
		
	}


	public int getScenarioId() {
		return scenarioId;
	}


	public void setScenarioId(int scenarioId) {
		this.scenarioId = scenarioId;
	}


	public String getZipFile() {
		return zipFile;
	}


	public void setZipFile(String zipFile) {
		this.zipFile = zipFile;
	}


	public int getTestCaseId() {
		return testCaseId;
	}


	public void setTestCaseId(int testCaseId) {
		this.testCaseId = testCaseId;
	}


	public String getTestCaseDesc() {
		return testCaseDesc;
	}


	public void setTestCaseDesc(String testCaseDesc) {
		this.testCaseDesc = testCaseDesc;
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


	public String getCtrlType() {
		return ctrlType;
	}


	public void setCtrlType(String ctrlType) {
		this.ctrlType = ctrlType;
	}


	public String getCondition() {
		return condition;
	}


	public void setCondition(String condition) {
		this.condition = condition;
	}


	public String getTestData() {
		return testData;
	}


	public void setTestData(String testData) {
		this.testData = testData;
	}
	
	
	


	@Override
	public int hashCode() {
		final int hash = 31;
		int hashCode = 1;
		hashCode = hash * hashCode + docClassAttr;
		hashCode = hash * hashCode + docClassId;
		hashCode = hash * hashCode + scenarioId;
		hashCode = hash * hashCode + testCaseId;
		return hashCode;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TestData other = (TestData) obj;
		if (docClassAttr != other.docClassAttr)
			return false;
		if (docClassId != other.docClassId)
			return false;
		if (scenarioId != other.scenarioId)
			return false;
		if (testCaseId != other.testCaseId)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "TestData [scenarioId=" + scenarioId + ", zipFile=" + zipFile + ", testCaseId=" + testCaseId
				+ ", testCaseDesc=" + testCaseDesc + ", docClassId=" + docClassId + ", docClassAttr=" + docClassAttr
				+ ", ctrlType=" + ctrlType + ", condition=" + condition + ", testData=" + testData + "]";
	}
	
	
		
	
}
