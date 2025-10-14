package thanhle.insider.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import thanhle.insider.customazation.DriverManager;

public class JobLeverPage extends GeneralPage{
	
	public JobLeverPage() {
		Object[] windowHandles=DriverManager.getDriver().getWebDriver().getWindowHandles().toArray();
		DriverManager.getDriver().getWebDriver().switchTo().window((String) windowHandles[1]);
		DriverManager.getDriver().waitUntilUrlContains("https://jobs.lever.co/");
	}
	
	protected By btnApply = By.xpath("//a[text()='Apply for this job']");
	
	protected WebElement getBtnApply() {
		return DriverManager.getDriver().findElement(btnApply);
	}
	
	public boolean isDisplayed() {
		boolean urlCheck = getURL().contains("https://jobs.lever.co/");
		boolean btnApplyCheck = getBtnApply().isDisplayed();
		return (urlCheck && btnApplyCheck);
	}		
}
