package thanhle.insider.pageobject;

import org.openqa.selenium.By;

import thanhle.insider.customazation.DriverManager;

public class CareersTeamPage extends GeneralPage{
	

	public CareersTeamPage(String url) {
		DriverManager.getDriver().getWebDriver().navigate().to(url);
	}
	
	public OpenPositionsPage clickSeeAllJobs(String buttonText) {
		DriverManager.getDriver().findElement(By.xpath(String.format("//a[text()='%s']", buttonText))).click();
		return new OpenPositionsPage();
	}
	
}
