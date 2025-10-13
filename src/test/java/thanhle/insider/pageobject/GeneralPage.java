package thanhle.insider.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import thanhle.insider.customazation.Driver;

public class GeneralPage extends BasePage{
	
	public GeneralPage(Driver driver) {
		super(driver);
	}

	protected By lnkLogo = By.xpath("//nav[@id='navigation']//a[@aria-label='Home']");
	protected By lnkLoginLoc = By.xpath("//a[text()='Login']");
	protected By menuCompanyLoc = By.xpath("//a[@id='navbarDropdownMenuLink' and contains(text(),'Company')]");
	protected By btnAcceptCookiesLoc = By.xpath("//a[@id='wt-cli-accept-all-btn']");
	
	protected WebElement getLogo() {
		return driver.findElement(lnkLogo);
	}
	
	protected WebElement getLnkLogin() {
		return driver.findElement(lnkLoginLoc);
	}
	
	protected WebElement getMenuCompany() {
		return driver.findElement(menuCompanyLoc);
	}
	
	protected WebElement getBtnAcceptCookies() {
		return driver.findElement(btnAcceptCookiesLoc);
	}
	
	public HomePage gotoHomePage() {
		getLogo().click();
		return new HomePage(driver);		
	}
	
	public void acceptCookies() {
		getBtnAcceptCookies().click();
	}
	
}
