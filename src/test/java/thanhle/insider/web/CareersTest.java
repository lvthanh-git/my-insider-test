package thanhle.insider.web;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import thanhle.insider.dataobject.Job;
import thanhle.insider.pageobject.CareersPage;
import thanhle.insider.pageobject.CareersTeamPage;
import thanhle.insider.pageobject.HomePage;
import thanhle.insider.pageobject.JobLeverPage;
import thanhle.insider.pageobject.OpenPositionsPage;

public class CareersTest extends BaseTest{
		
	@Test
	public void tc001() throws InterruptedException {
		HomePage homePage = new HomePage(drivers.get());
		homePage.acceptCookies();
		
		assertTrue(homePage.isDisplayed(), "Home page is not displayed.");
		
		CareersPage careersPage = homePage.gotoCompanyCareersPage();
		assertTrue(careersPage.isLocationsBlockDisplayed(), "Locations block in the Company > Careers page is not displayed");
		assertTrue(careersPage.isTeamsBlockDisplayed(), "Teams block in the Company > Careers page is not displayed");
		assertTrue(careersPage.isLifeAtInsiderBlockDisplayed(), "Life at Insider block in the Company > Careers page is not displayed");
		
		CareersTeamPage qualityAssurancePage = new CareersTeamPage(drivers.get(), "https://useinsider.com/careers/quality-assurance/");
		
		String location = "Istanbul, Turkiye";
		String department = "Quality Assurance";
		OpenPositionsPage openPositionsPage = qualityAssurancePage.clickSeeAllJobs("See all QA jobs");
		List<Job> jobs = openPositionsPage.filterJobs(location, department)
				.getAllDisplayedJobs();
		
		for (Job job : jobs) {
			assertTrue(job.getPosition().contains(department), String.format("%s is not displayed in job's position", department));
			assertTrue(job.getDepartment().contains(department), String.format("%s is not displayed in job's department", department));
			assertTrue(job.getLocation().contains(location), String.format("%s is not displayed in job's location", location));
		}
		
		JobLeverPage jobLeverPage = openPositionsPage.viewJob(1);
		assertTrue(jobLeverPage.isDisplayed(), "Job Lever Page is not displayed");
		
		Thread.sleep(Duration.ofSeconds(5));
		
		
		
	}

	

}
