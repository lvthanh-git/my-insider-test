package thanhle.insider.pageobject;

import org.openqa.selenium.By;

import thanhle.insider.customazation.Driver;

public class CareersTeamPage extends GeneralPage{
	
	public CareersTeamPage(Driver driver) {
		super(driver);
	}
	
	public CareersTeamPage(Driver driver, String url) {
		this(driver);
		driver.getWebDriver().navigate().to(url);
	}
	
	public OpenPositionsPage clickSeeAllJobs(String buttonText) {
		driver.findElement(By.xpath(String.format("//a[text()='%s']", buttonText))).click();
		return new OpenPositionsPage(driver);
	}
	
}
