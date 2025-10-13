package thanhle.insider.web;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.annotations.AfterMethod;

public class BaseTest {

	protected static ThreadLocal<WebDriver> drivers = new ThreadLocal<WebDriver>();
	protected int explicitWaitTimeout = 0;
	
	@Parameters({ "browser", "webURL", "implicitWaitTimeout", "explicitWaitTimeout", "pageLoadTimeout"})
	@BeforeMethod
	protected void beforeMethod(String browser, String webURL, int implicitWaitTimeout, int explicitWaitTimeout, int pageLoadTimeout) throws Exception {
		WebDriver driver = null;
		this.explicitWaitTimeout = explicitWaitTimeout;
		
		if (browser.toLowerCase().equals("chrome")) {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-notifications");
			
			driver = new ChromeDriver(options);
		} else if (browser.toLowerCase().equals("firefox")) {		
			driver = new FirefoxDriver();
		} else {
			throw new Exception(String.format("%s is unsupported!", browser));
		}

		drivers.set(driver);
		drivers.get().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadTimeout));
		drivers.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWaitTimeout));
		drivers.get().manage().window().maximize();
		
		drivers.get().get(webURL);

	}

	@AfterMethod
	protected void afterMethod() {
		drivers.get().quit();
		drivers.remove();
	}

}
