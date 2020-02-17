package uiActions;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import propertyManager.ReadPropertyFile;
import utilities.Utilities;

public class WebActions {
	
	public static void selectDropDown() throws IOException {
		
		Select select = new Select(Utilities.getDriver().findElement(By.xpath("//select[@id='webSelection']")));
		select.selectByVisibleText(ReadPropertyFile.readPropertiesFile("docClass"));
		Utilities.waitMethod();
		//System.out.println("--------After  Entered DropDown Method\t" +DocGroup+"\tDocGroup Name is Selected---------");
		
	}

	public static boolean isElementPresent (WebElement isEle){
	
			if(isEle.isDisplayed()) {
			return true;}
		
			else
			return false;
			
		}
	
	public void enterText(WebElement element, String value) throws TimeoutException {
		Utilities.waitDriver.until(ExpectedConditions.visibilityOf(element)).clear();
		element.sendKeys(value);
	}
	
	public void clickSearchButton() throws TimeoutException {
		scrollToResults();
		WebElement ele =  Utilities.getDriver().findElement(By.cssSelector("#Search"));
		Utilities.waitDriver.until(ExpectedConditions.elementToBeClickable(ele)).click();
	}
	
	public static boolean searchResultDisplay(){
		try {
		scrollToResults();
		WebElement ele =  Utilities.getDriver().findElement(By.xpath("//strong[contains(text(),'Search Results')]"));
		
		if(isElementPresent(ele)) {
			return true;
		}
		else {
			return false;
		}
		}
		catch(Exception e) {
			System.out.println("No Search Results displayed on UI");
		}
		return false;
	}
	
	
	public void clickResetButton() throws TimeoutException {
		Utilities.getDriver().findElement(By.id("Reset")).click();  
	}
	
	
	public void clickAgeCheckBox() {
		Utilities.getDriver().findElement(By.xpath("//input[@class='ng-pristine ng-valid ng-empty ng-touched']")).click();
	}
	
	public static void scrollToResults() {
		JavascriptExecutor js = (JavascriptExecutor)Utilities.getDriver();
		js.executeScript("window.scrollBy(2000,2000)");
	}
	public static void scrollGrid() {
		JavascriptExecutor js = (JavascriptExecutor)Utilities.getDriver();
	//	js.executeScript("window.scrollBy(2000,2000)");
		WebElement webElement = Utilities.getDriver().findElement(By.xpath("//div[@class='ui-grid-viewport ng-isolate-scope']"));
		js.executeScript("arguments[0].scrollBy(700,2000)", webElement);
		Utilities.getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public static void scrollToHeight() {

		String jsCode1 = "window.scrollBy(0, document.body.scrollHeight)";
		JavascriptExecutor je = (JavascriptExecutor)Utilities.getDriver();
		je.executeScript(jsCode1); 
		}
}
