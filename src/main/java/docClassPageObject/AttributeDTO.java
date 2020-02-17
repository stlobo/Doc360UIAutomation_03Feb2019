package docClassPageObject;

/**
 * @author jnaik3
 *
 */
public class AttributeDTO {
	
	
	String labelName;
	String join_operator_locator;
	String physicalName;
	String attributeType;
	String attributeValue;
	String testCaseId;
	String join_operator_value;
	String locator;
	
	public String getLocator() {
		return locator;
	}
	public void setLocator(String locator) {
		this.locator = locator;
	}
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	
	public String getJoin_operator_locator() {
		return join_operator_locator;
	}
	public void setJoin_operator_locator(String join_operator_locator) {
		this.join_operator_locator = join_operator_locator;
	}
	public String getJoin_operator_value() {
		return join_operator_value;
	}
	public void setJoin_operator_value(String join_operator_value) {
		this.join_operator_value = join_operator_value;
	}
	public String getPhysicalName() {
		return physicalName;
	}
	public void setPhysicalName(String physicalName) {
		this.physicalName = physicalName;
	}
	public String getAttributeType() {
		return attributeType;
	}
	public void setAttributeType(String attributeType) {
		this.attributeType = attributeType;
	}
	public String getAttributeValue() {
		return attributeValue;
	}
	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}
	public String getTestCaseId() {
		return testCaseId;
	}
	public void setTestCaseId(String testCaseId) {
		this.testCaseId = testCaseId;
	}

	

}
