package thanhle.insider.pageobject;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import thanhle.insider.customazation.DriverManager;
import thanhle.insider.dataobject.Job;

public class OpenPositionsPage extends GeneralPage {

	public OpenPositionsPage() {
		waitForPageDisplayed();
	}

	private String jobListItemXpath = "(//div[@id='jobs-list']/div)";
	private By lbResultCounterLoc = By.xpath("//p[@id='resultCounter' and contains(text(), 'Showing')]");
	private By cbbLocationLoc = By.xpath("//span[@id='select2-filter-by-location-container']");
	private By btnClearLocationLoc = By.xpath("//span[@id='select2-filter-by-location-container']/span[text()='×']");
	private By cbbDepartmentLoc = By.xpath("//span[@id='select2-filter-by-department-container']");

	protected WebElement getLbResultCounter() {
		return DriverManager.getDriver().findElement(lbResultCounterLoc);
	}

	protected WebElement getCbbLocation() {
		return DriverManager.getDriver().findElement(cbbLocationLoc);
	}
	
	protected WebElement getBtnClearLocation() {
		return DriverManager.getDriver().findElement(btnClearLocationLoc);
	}

	protected WebElement getCbbLocationItem(String value) {
		return DriverManager.getDriver().findElement(
				By.xpath(String.format("//ul[@id='select2-filter-by-location-results']/li[text()='%s']", value)));
	}

	protected WebElement getCbbDepartment() {
		return DriverManager.getDriver().findElement(cbbDepartmentLoc);
	}

	protected WebElement getCbbDepartmentItem(String value) {
		return DriverManager.getDriver().findElement(
				By.xpath(String.format("//ul[@id='select2-filter-by-department-results']/li[text()='%s']", value)));
	}
	
	protected WebElement getPanelJobByIndex(int index) {
		return DriverManager.getDriver().findElement(
				By.xpath(String.format("(//div[@id='jobs-list']/div)[%d]", index)));
	}
	
	protected WebElement getBtnViewJobByIndex(int index) {
		return DriverManager.getDriver().findElement(
				By.xpath(String.format("(//div[@id='jobs-list']/div)[%d]/div/a", index)));
	}	

	public void waitForPageDisplayed() {
		DriverManager.getDriver().waitUntilElementVisible(lbResultCounterLoc);
	}

	public OpenPositionsPage filterJobs(String location, String department) throws InterruptedException {
		int i = 10;
		
		do {
			getCbbLocation().click();
			List<WebElement> locationList = DriverManager.getDriver().waitUntilElementsVisible(By.xpath("//ul[@id='select2-filter-by-location-results']/li"));
			if(locationList.size() > 1) {
				break;
			}
			
			Thread.sleep(Duration.ofSeconds(1));
			getCbbLocation().click();
			i--;
		} while (i > 0);

		getCbbLocationItem(location).click();		
		getCbbDepartment().click();
		getCbbDepartmentItem(department).click();
		
		DriverManager.getDriver().waitUntilElementVisible(By.xpath("//span[@id='deneme' and string-length(text()) > 0] "));
		
		String jobListXpath = String.format("//div[@id='jobs-list']/div[@data-location='%s' and @data-team='%s']", 
				location.toLowerCase().replace(", ", "-"),
				department.toLowerCase().replace(" ", ""));
				
		DriverManager.getDriver().waitUntilElementVisible(By.xpath(jobListXpath));
		
		return this;
	}	
			
	public List<Job> getAllDisplayedJobs(){
		List<Job> jobs = new ArrayList<Job>();
		
		int jobSize = DriverManager.getDriver().findElements(By.xpath(jobListItemXpath)).size();
				
		for (int i = 1; i < jobSize + 1; i++) {
			String position = DriverManager.getDriver().findElement(By.xpath(String.format("%s[%d]/div/p", jobListItemXpath, i))).getText();
			String department = DriverManager.getDriver().findElement(By.xpath(String.format("%s[%d]/div/span", jobListItemXpath, i))).getText();
			String location = DriverManager.getDriver().findElement(By.xpath(String.format("%s[%d]/div/div", jobListItemXpath, i))).getText();
			
			jobs.add(new Job(position, department, location));
		}		
		
		return jobs;
	}
	
	public JobLeverPage viewJob(int jobIndex) throws InterruptedException {
		DriverManager.getDriver().scrollToElement(getPanelJobByIndex(jobIndex));
		DriverManager.getDriver().moveToElement(getPanelJobByIndex(jobIndex));
		getPanelJobByIndex(jobIndex).click();
		
		DriverManager.getDriver().moveToElement(DriverManager.getDriver().waitUntilElementVisible(getBtnViewJobByIndex(jobIndex)));
		getBtnViewJobByIndex(jobIndex).click();
		
		return new JobLeverPage();
	}
	
}
