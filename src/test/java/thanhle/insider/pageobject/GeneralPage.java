package thanhle.insider.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import thanhle.insider.customazation.Driver;

public class GeneralPage extends BasePage{
	
	public GeneralPage(Driver driver) {
		super(driver);
	}

	protected By lnkLogoLoc = By.xpath("//nav[@id='navigation']//a[@aria-label='Home']");
	protected By lnkLoginLoc = By.xpath("//a[text()='Login']");
	protected By menuCompanyLoc = By.xpath("//a[@id='navbarDropdownMenuLink' and contains(text(),'Company')]");
	protected By btnAcceptCookiesLoc = By.xpath("//a[@id='wt-cli-accept-all-btn']");
		
	protected WebElement getLogo() {
		return driver.findElement(lnkLogoLoc);
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
	
	private void selectMenu(String path) {
		String[] items = path.split("/");
		
		String menu_lvl1_xpath = String.format("//div[@id='navbarNavDropdown']//a[contains(text(), '%s')]", items[0]);
		String menu_lvl2_xpath = String.format("//div[contains(@class, 'dropdown-menu')]//a[text()='%s']", items[1]);
		
		driver.findElement(By.xpath(menu_lvl1_xpath)).click();
		driver.waitUntilElementVisible(By.xpath(menu_lvl2_xpath));
		driver.findElement(By.xpath(menu_lvl2_xpath)).click();	
	}
		
	public CareersPage gotoCompanyCareersPage() {
		selectMenu("Company/Careers");
		return new CareersPage(driver);
	}
	
}
