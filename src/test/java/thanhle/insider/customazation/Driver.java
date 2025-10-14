package thanhle.insider.customazation;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Driver {
	private WebDriver driver;
	private int explicitWaitTimeout;
	
	public Driver(String browser) throws Exception {
		if (browser.toLowerCase().equals("chrome")) {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-notifications");
			
			driver = new ChromeDriver(options);
		} else if (browser.toLowerCase().equals("firefox")) {		
			driver = new FirefoxDriver();
		} else {
			throw new Exception(String.format("%s is unsupported!", browser));
		}
	}
	
	public Driver(String browser, int explicitWaitTimeout) throws Exception {
		this(browser);
		this.explicitWaitTimeout = explicitWaitTimeout;
	}
	
	public WebDriverWait getWait() {
		return new WebDriverWait(driver, Duration.ofSeconds(explicitWaitTimeout));
	}
	
	public WebDriver getWebDriver() {
		return driver;
	}
	
	public WebElement waitUntilElementVisible(By by) {
		WebDriverWait wait = getWait();
		return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}
	
	public List<WebElement> waitUntilElementsVisible(By by) {
		WebDriverWait wait = getWait();
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
	}
	
	
	public WebElement findElement(By by) {
		try {
			return driver.findElement(by);
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	public List<WebElement> findElements(By by) {
		try {
			return driver.findElements(by);
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	
	
	
}
