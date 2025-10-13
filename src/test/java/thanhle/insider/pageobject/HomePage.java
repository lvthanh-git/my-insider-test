package thanhle.insider.pageobject;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends GeneralPage{
	
	public HomePage(WebDriver driver) {
		super(driver);
		waitForPageDisplayed();
	}
	
	public void waitForPageDisplayed() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(getLnkLogin()));
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
