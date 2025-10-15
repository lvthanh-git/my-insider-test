package thanhle.insider.pageobject;

import thanhle.insider.customazation.DriverManager;

public class BasePage {
	
	public String getURL() {
		return DriverManager.getDriver().getWebDriver().getCurrentUrl();
	}	
	
}
