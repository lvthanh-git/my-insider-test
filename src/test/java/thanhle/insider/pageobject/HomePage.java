package thanhle.insider.pageobject;

import thanhle.insider.customazation.Driver;

public class HomePage extends GeneralPage{
	
	public HomePage(Driver driver) {
		super(driver);
		waitForPageDisplayed();
	}
	
	public void waitForPageDisplayed() {
		driver.waitUntilVisible(lnkLoginLoc);
	}	
	
	public boolean isDisplayed() {		
		boolean isLnkLoginDisplayed = getLnkLogin().isDisplayed();
		boolean isMenuCompanyDisplayed = getMenuCompany().isDisplayed();
		
		if(isLnkLoginDisplayed == true && isMenuCompanyDisplayed == true) {
			return true;
		}		
		
		return false;
	}
	
}
