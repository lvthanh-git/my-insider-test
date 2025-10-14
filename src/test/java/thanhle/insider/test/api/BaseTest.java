package thanhle.insider.test.api;

import io.restassured.RestAssured;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterMethod;

public class BaseTest {

	@BeforeSuite
	public void beforeSuite() {
		RestAssured.baseURI = "https://petstore.swagger.io/v2";
	}
	
	@BeforeMethod
	public void beforeMethod() {
	}

	@AfterMethod
	public void afterMethod() {
	}

}
