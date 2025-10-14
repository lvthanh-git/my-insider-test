package thanhle.insider.pageobject;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import thanhle.insider.customazation.Driver;
import thanhle.insider.dataobject.Job;

public class OpenPositionsPage extends GeneralPage {

	public OpenPositionsPage(Driver driver) {
		super(driver);
		waitForPageDisplayed();
	}

	private String jobListItemXpath = "(//div[@id='jobs-list']/div)";
	private By lbResultCounterLoc = By.xpath("//p[@id='resultCounter' and contains(text(), 'Showing')]");
	private By cbbLocationLoc = By.xpath("//span[@id='select2-filter-by-location-container']");
	private By btnClearLocationLoc = By.xpath("//span[@id='select2-filter-by-location-container']/span[text()='×']");
	private By cbbDepartmentLoc = By.xpath("//span[@id='select2-filter-by-department-container']");

	protected WebElement getLbResultCounter() {
		return driver.findElement(lbResultCounterLoc);
	}

	protected WebElement getCbbLocation() {
		return driver.findElement(cbbLocationLoc);
	}
	
	protected WebElement getBtnClearLocation() {
		return driver.findElement(btnClearLocationLoc);
	}

	protected WebElement getCbbLocationItem(String value) {
		return driver.findElement(
				By.xpath(String.format("//ul[@id='select2-filter-by-location-results']/li[text()='%s']", value)));
	}

	protected WebElement getCbbDepartment() {
		return driver.findElement(cbbDepartmentLoc);
	}

	protected WebElement getCbbDepartmentItem(String value) {
		return driver.findElement(
				By.xpath(String.format("//ul[@id='select2-filter-by-department-results']/li[text()='%s']", value)));
	}

	public void waitForPageDisplayed() {
		driver.waitUntilElementVisible(lbResultCounterLoc);
	}

	public OpenPositionsPage filterJobs(String location, String department) throws InterruptedException {
		int i = 10;
		
		do {
			getCbbLocation().click();
			List<WebElement> locationList = driver.waitUntilElementsVisible(By.xpath("//ul[@id='select2-filter-by-location-results']/li"));
			if(locationList.size() > 1) {
				break;
			}
			
			Thread.sleep(Duration.ofSeconds(1));
			getCbbLocation().click();
			i--;
		} while (i > 0);

		WebElement locationItem = getCbbLocationItem(location);
		Actions actions = new Actions(driver.getWebDriver());
		actions.moveToElement(locationItem);
		actions.perform();
		locationItem.click();
		
		getCbbDepartment().click();
		WebElement departmentItem = getCbbDepartmentItem(department);
		actions.moveToElement(departmentItem);
		actions.perform();
		departmentItem.click();
		
		String jobListXpath = String.format("//div[@id='jobs-list']/div[@data-location='%s' and @data-team='%s']", 
				location.toLowerCase().replace(", ", "-"),
				department.toLowerCase().replace(" ", ""));
				
		driver.waitUntilElementVisible(By.xpath(jobListXpath));
		
		return this;
	}	
			
	public List<Job> getAllDisplayedJobs(){
		List<Job> jobs = new ArrayList<Job>();
		
		int jobSize = driver.findElements(By.xpath(jobListItemXpath)).size();
				
		for (int i = 1; i < jobSize + 1; i++) {
			String position = driver.findElement(By.xpath(String.format("%s[%d]/div/p", jobListItemXpath, i))).getText();
			String department = driver.findElement(By.xpath(String.format("%s[%d]/div/span", jobListItemXpath, i))).getText();
			String location = driver.findElement(By.xpath(String.format("%s[%d]/div/div", jobListItemXpath, i))).getText();
			
			jobs.add(new Job(position, department, location));
		}		
		
		return jobs;
	}
	
	public JobLeverPage viewJob(int jobIndex) {
		String jobXpath = String.format("(//div[@id='jobs-list']/div)[%d]", jobIndex);
		
		Actions actions = new Actions(driver.getWebDriver());
		actions.moveToElement(driver.findElement(By.xpath(jobXpath)));
		actions.perform();	
		
		driver.findElement(By.xpath(String.format("%s/div/a", jobXpath))).click();
		
		return new JobLeverPage(driver);
	}
	
}
