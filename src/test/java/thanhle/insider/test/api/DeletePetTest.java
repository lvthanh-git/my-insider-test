package thanhle.insider.test.api;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import thanhle.insider.dataobject.Pet;
import thanhle.insider.utility.ExtentReportListener;

public class DeletePetTest extends BaseTest {
		
	@Test
	public void TC10020() {
		ExtentReportListener.logInfo("DELETE Pet with valid request");
		
		Pet pet = new Pet();
		pet.initData();
		petFunction.createPet(pet);
		
		Response response = petFunction.deletePet(apiKey, pet.getId());
		ExtentReportListener.logResponse("Response Body", response.asPrettyString());
		
		assertEquals(response.getStatusCode(), 200, "Status code should be 200");		
		assertEquals(response.jsonPath().getInt("code"), 200, "Body code is not as expected");
		assertEquals(response.jsonPath().getString("type"), "unknown", "Body type is not as expected");
		assertEquals(response.jsonPath().getString("message"), "1", "Body message is not as expected");
	}
	
	
	@DataProvider(name = "invalidDeletePetData")
	public Object[][] deletePetData() {
	    return new Object[][]{
	        {"DELETE Pet with unexisting Pet ID", 951632, "special-key", 404, "Pet not found"},
	        {"DELETE Pet with negative Pet ID", -1, "special-key", 400, "Invalid ID supplied"},
	        {"DELETE Pet without api_key", 1, "", 403, "Invalid API key"},
	        {"DELETE Pet with wrong api_key", 1, "wrong-key", 403, "Invalid API key"},
	    };
	}
	
	@Test(dataProvider = "invalidDeletePetData")
	public void TC10021_TC10022_TC10023_TC10024(String testName, int petID, String apiKey, int expectedStatusCode, String expectedMessage) {
		ExtentReportListener.logInfo(testName);
		
		Response response = petFunction.deletePet(apiKey, petID);
		ExtentReportListener.logResponse("Response Body", response.asPrettyString());
		
		assertEquals(response.getStatusCode(), expectedStatusCode, "Status code is not as expected");
		assertEquals(response.jsonPath().getInt("code"), expectedStatusCode, "Body code is not as expected");
		assertEquals(response.jsonPath().getString("message"), expectedMessage, "Body message is not as expected");
	}
}
