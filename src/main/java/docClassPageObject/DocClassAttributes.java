package docClassPageObject;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;

public class DocClassAttributes {
private List<String> lableName =new ArrayList<String>();
private List<String> physicalName =new ArrayList<String>();
private List<WebElement> labelWebElement =new ArrayList<WebElement>();
private List<WebElement> ddFlagWebElementByLableName =new ArrayList<WebElement>();
private List<WebElement> IdWebElementByPhysicalName =new ArrayList<WebElement>();

public List<String> getLableName() {
	return lableName;
}
public void setLableName(List<String> lableName) {
	this.lableName = lableName;
}
public List<String> getPhysicalName() {
	return physicalName;
}
public void setPhysicalName(List<String> physicalName) {
	this.physicalName = physicalName;
}
public List<WebElement> getLabelWebElement() {
	return labelWebElement;
}
public void setLabelWebElement(List<WebElement> labelWebElement) {
	this.labelWebElement = labelWebElement;
}
public List<WebElement> getDdFlagWebElementByLableName() {
	return ddFlagWebElementByLableName;
}
public void setDdFlagWebElementByLableName(List<WebElement> ddFlagWebElementByLableName) {
	this.ddFlagWebElementByLableName = ddFlagWebElementByLableName;
}
public List<WebElement> getIdWebElementByPhysicalName() {
	return IdWebElementByPhysicalName;
}
public void setIdWebElementByPhysicalName(List<WebElement> idWebElementByPhysicalName) {
	IdWebElementByPhysicalName = idWebElementByPhysicalName;
}


}
