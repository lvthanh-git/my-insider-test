package thanhle.insider.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import thanhle.insider.customazation.Driver;

public class CareersPage extends GeneralPage{
	
	public CareersPage(Driver driver) {
		super(driver);
		waitForPageDisplayed();
	}
	
	private By pageHeaderLoc = By.xpath("//h1[text()='Ready to disrupt? ']");
	private By blockLocationsLoc = By.xpath("//section[@id='career-our-location']//h3[contains(text(), 'Our Locations')]");
	private By blockLifeLoc = By.xpath("//section//h2[text()='Life at Insider']");
	private By blockTeamsLoc = By.xpath("//section[@id='career-find-our-calling']//h3[contains(text(), 'Find your calling')]");
	private By btnSeeAllTeamsLoc = By.xpath("//a[text()='See all teams']");
	
	protected WebElement getBlockLocations() {
		return driver.findElement(blockLocationsLoc);
	}
	
	protected WebElement getBlockLife() {
		return driver.findElement(blockLifeLoc);
	}
	
	protected WebElement getBlockTeams() {
		return driver.findElement(blockTeamsLoc);
	}
	
	
	public void waitForPageDisplayed() {
		driver.waitUntilElementVisible(pageHeaderLoc);
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
		return driver.findElement(btnSeeAllTeamsLoc);
	}
	
	public CareersTeamPage gotoCareersTeamPage(String teamName) {
		getBtnSeeAllTeams().click();		
		driver.findElement(By.xpath(String.format("//a/h3[text()='%s']", teamName))).click();
		
		return new CareersTeamPage(driver);
	}
	
}
