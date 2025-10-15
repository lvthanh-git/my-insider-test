package thanhle.insider.customazation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Driver {
	private WebDriver driver;
	private int explicitWaitTimeout;
    private final String screenshot_path = "target/screenshots/";
    
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

	public WebElement waitUntilElementVisible(WebElement elemment) {
		WebDriverWait wait = getWait();
		return wait.until(ExpectedConditions.visibilityOf(elemment));
	}

	public WebElement waitUntilElementClickable(By by) {
		WebDriverWait wait = getWait();
		return wait.until(ExpectedConditions.elementToBeClickable(by));
	}

	public WebElement waitUntilElementClickable(WebElement elemment) {
		WebDriverWait wait = getWait();
		return wait.until(ExpectedConditions.elementToBeClickable(elemment));
	}

	public List<WebElement> waitUntilElementsVisible(By by) {
		WebDriverWait wait = getWait();
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
	}

	public void waitUntilUrlContains(String text) {
		WebDriverWait wait = getWait();
		wait.until(ExpectedConditions.urlContains(text));
	}

	public WebElement findElement(By by) {
		try {
			return driver.findElement(by);
		} catch (Exception e) {
			throw e;
		}
	}

	public List<WebElement> findElements(By by) {
		try {
			return driver.findElements(by);
		} catch (Exception e) {
			throw e;
		}
	}

	public void scrollToElement(WebElement element) {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		} catch (Exception e) {
			throw e;
		}
	}

	public void moveToElement(WebElement element) {
		try {
			Actions actions = new Actions(driver);
			actions.moveToElement(element);
			actions.perform();
		} catch (Exception e) {
			throw e;
		}
	}
	
	public String captureScreenshot(String name) {
        try {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            File dir = new File(screenshot_path);
            if (!dir.exists()) dir.mkdirs();

            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String destPath = screenshot_path + name + "_" + timestamp + ".png";

            File destFile = new File(destPath);
            Files.copy(srcFile.toPath(), destFile.toPath());

            return destPath;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
