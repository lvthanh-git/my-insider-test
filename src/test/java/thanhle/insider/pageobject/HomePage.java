package thanhle.insider.pageobject;

import thanhle.insider.customazation.DriverManager;

public class HomePage extends GeneralPage{
	
	public HomePage() {
		waitForPageDisplayed();
	}
	
	public void waitForPageDisplayed() {
		DriverManager.getDriver().waitUntilElementVisible(lnkLoginLoc);
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
