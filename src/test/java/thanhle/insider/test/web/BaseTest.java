package thanhle.insider.test.web;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import thanhle.insider.customazation.DriverManager;

import java.time.Duration;
import org.testng.annotations.AfterMethod;

public class BaseTest {

	@Parameters({ "browser", "webURL", "implicitWaitTimeout", "explicitWaitTimeout", "pageLoadTimeout"})
	@BeforeMethod
	protected void beforeMethod(String browser, String webURL, int implicitWaitTimeout, int explicitWaitTimeout, int pageLoadTimeout) throws Exception {
		DriverManager.createDriver(browser, explicitWaitTimeout);
		
		DriverManager.getDriver().getWebDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadTimeout));
		DriverManager.getDriver().getWebDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWaitTimeout));
		DriverManager.getDriver().getWebDriver().manage().window().maximize();		
		DriverManager.getDriver().getWebDriver().get(webURL);
	}

	@AfterMethod
	protected void afterMethod() {
		DriverManager.getDriver().getWebDriver().quit();
		DriverManager.removeDriver();
	}

}
