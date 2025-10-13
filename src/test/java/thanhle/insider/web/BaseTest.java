package thanhle.insider.web;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import thanhle.insider.customazation.Driver;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.annotations.AfterMethod;

public class BaseTest {

	protected static ThreadLocal<Driver> drivers = new ThreadLocal<Driver>();
	
	@Parameters({ "browser", "webURL", "implicitWaitTimeout", "explicitWaitTimeout", "pageLoadTimeout"})
	@BeforeMethod
	protected void beforeMethod(String browser, String webURL, int implicitWaitTimeout, int explicitWaitTimeout, int pageLoadTimeout) throws Exception {
		Driver driver = new Driver(browser, explicitWaitTimeout);	

		drivers.set(driver);
		drivers.get().getWebDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadTimeout));
		drivers.get().getWebDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWaitTimeout));
		drivers.get().getWebDriver().manage().window().maximize();
		
		drivers.get().getWebDriver().get(webURL);
	}

	@AfterMethod
	protected void afterMethod() {
		drivers.get().getWebDriver().quit();
		drivers.remove();
	}

}
