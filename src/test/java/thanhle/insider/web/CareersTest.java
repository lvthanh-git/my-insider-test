package thanhle.insider.web;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import thanhle.insider.pageobject.HomePage;

public class CareersTest extends BaseTest{
		
	@Test
	public void tc001() {
		HomePage homePage = new HomePage(drivers.get());
		homePage.acceptCookies();
		
		boolean isHomePageDisplayed = homePage.isDisplayed();		
		assertEquals(isHomePageDisplayed, true, "Home page is not displayed.");
		
		
	}

	

}
