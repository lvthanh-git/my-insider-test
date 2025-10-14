package thanhle.insider.test.web;

import static org.testng.Assert.assertTrue;

import java.util.List;

import org.testng.annotations.Test;

import thanhle.insider.dataobject.Job;
import thanhle.insider.pageobject.CareersPage;
import thanhle.insider.pageobject.CareersTeamPage;
import thanhle.insider.pageobject.HomePage;
import thanhle.insider.pageobject.JobLeverPage;
import thanhle.insider.pageobject.OpenPositionsPage;

public class CareersTest extends BaseTest {

	@Test
	public void tc001() throws InterruptedException {
		//1. Visit https://useinsider.com/ and check Insider home page is opened or not	
		HomePage homePage = new HomePage();
		homePage.acceptCookies();		
		assertTrue(homePage.isDisplayed(), "Home page is not displayed.");
		
		//2. Select the “Company” menu in the navigation bar, select “Careers” and check Career page, its Locations, Teams, and Life at Insider blocks are open or not
		CareersPage careersPage = homePage.gotoCompanyCareersPage();
		assertTrue(careersPage.isLocationsBlockDisplayed(), "Locations block in the Company > Careers page is not displayed");
		assertTrue(careersPage.isTeamsBlockDisplayed(), "Teams block in the Company > Careers page is not displayed");
		assertTrue(careersPage.isLifeAtInsiderBlockDisplayed(), "Life at Insider block in the Company > Careers page is not displayed");
		
		//3. Go to https://useinsider.com/careers/quality-assurance/, click “See all QA jobs”, filter jobs by Location: “Istanbul, Turkey”, and Department: “Quality Assurance”, check the presence of the job list
		CareersTeamPage qualityAssurancePage = new CareersTeamPage("https://useinsider.com/careers/quality-assurance/");
		
		String location = "Istanbul, Turkiye";
		String department = "Quality Assurance";
		OpenPositionsPage openPositionsPage = qualityAssurancePage.clickSeeAllJobs("See all QA jobs");
		List<Job> jobs = openPositionsPage.filterJobs(location, department)
				.getAllDisplayedJobs();
		
		//4. Check that all jobs’ Position contains “Quality Assurance”, Department contains “Quality Assurance”, and Location contains “Istanbul, Turkey”
		for (Job job : jobs) {
			assertTrue(job.getPosition().contains(department), String.format("%s is not displayed in job's position", department));
			assertTrue(job.getDepartment().contains(department), String.format("%s is not displayed in job's department", department));
			assertTrue(job.getLocation().contains(location), String.format("%s is not displayed in job's location", location));
		}
		
		//5. Click the “View Role” button and check that this action redirects us to the Lever Application form page
		JobLeverPage jobLeverPage = openPositionsPage.viewJob(1);
		assertTrue(jobLeverPage.isDisplayed(), "Job Lever Page is not displayed");
	}
}
