package thanhle.insider.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import thanhle.insider.customazation.Driver;

public class JobLeverPage extends GeneralPage{
	
	public JobLeverPage(Driver driver) {
		super(driver);
	}

	
	protected By btnApply = By.xpath("//a[text()='Apply for this job']");
	
	protected WebElement getBtnApply() {
		return driver.findElement(btnApply);
	}
	
	public boolean isDisplayed() {
		return (getURL().contains("https://jobs.lever.co/") && getBtnApply().isDisplayed());
	}	
	
		
}
