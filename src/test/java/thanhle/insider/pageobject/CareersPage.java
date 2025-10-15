package thanhle.insider.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import thanhle.insider.customazation.DriverManager;

public class CareersPage extends GeneralPage{
	
	public CareersPage() {
		waitForPageDisplayed();
	}
	
	private By pageHeaderLoc = By.xpath("//h1[text()='Ready to disrupt? ']");
	private By blockLocationsLoc = By.xpath("//section[@id='career-our-location']//h3[contains(text(), 'Our Locations')]");
	private By blockLifeLoc = By.xpath("//section//h2[text()='Life at Insider']");
	private By blockTeamsLoc = By.xpath("//section[@id='career-find-our-calling']//h3[contains(text(), 'Find your calling')]");
	private By btnSeeAllTeamsLoc = By.xpath("//a[text()='See all teams']");
	
	protected WebElement getBlockLocations() {
		return DriverManager.getDriver().findElement(blockLocationsLoc);
	}
	
	protected WebElement getBlockLife() {
		return DriverManager.getDriver().findElement(blockLifeLoc);
	}
	
	protected WebElement getBlockTeams() {
		return DriverManager.getDriver().findElement(blockTeamsLoc);
	}
	
	
	public void waitForPageDisplayed() {
		DriverManager.getDriver().waitUntilElementVisible(pageHeaderLoc);
	}
	
	public boolean isLocationsBlockDisplayed() {
		return getBlockLocations().isDisplayed();
	}
	
	public boolean isTeamsBlockDisplayed() {
		return getBlockTeams().isDisplayed();
	}
	
	public boolean isLifeAtInsiderBlockDisplayed() {
		return getBlockLife().isDisplayed();
	}
	
	protected WebElement getBtnSeeAllTeams() {
		return DriverManager.getDriver().findElement(btnSeeAllTeamsLoc);
	}
	
//	public CareersTeamPage gotoCareersTeamPage(String teamName) {
//		getBtnSeeAllTeams().click();		
//		driver.findElement(By.xpath(String.format("//a/h3[text()='%s']", teamName))).click();
//		
//		return new CareersTeamPage(driver);
//	}
	
}
