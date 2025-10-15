package thanhle.insider.test.api;

import static org.testng.Assert.assertEquals;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import thanhle.insider.dataobject.Pet;
import thanhle.insider.utility.ExtentReportListener;

public class PostPetTest extends BaseTest {
		
	@Test
	public void TC10010() {
		ExtentReportListener.logInfo("POST Pet with valid request");
		Pet pet = new Pet();
		pet.initData();
		Response response = petFunction.createPet(pet);
		ExtentReportListener.logResponse("Response Body", response.asPrettyString());
		
		assertEquals(response.getStatusCode(), 200, "Status code should be 200");		
		Pet actualPet = Pet.fromResponse(response.getBody());
		assertPet(actualPet, pet);
	}
	
	@DataProvider(name = "invalidCreatePetData")
    public Object[][] invalidCreatePetData() {
        String missingName = """
        {
          "id": 102,
          "category": {"id": 1, "name": "dogs"},
          "photoUrls": ["https://example.com/dog.jpg"],
          "tags": [{"id": 10, "name": "friendly"}],
          "status": "available"
        }""";

        String invalidStatus = """
        {
          "id": 103,
          "category": {"id": 1, "name": "dogs"},
          "name": "Rex",
          "photoUrls": ["https://example.com/dog.jpg"],
          "tags": [{"id": 10, "name": "friendly"}],
          "status": "!@#$^invalid_status"
        }""";

        String emptyJson = "{}";

        return new Object[][]{         
            {"POST Pet without name", missingName, 405, "Invalid input"},
            {"POST Pet with invalid status", invalidStatus, 405, "Invalid input"},
            {"POST Pet empty data", emptyJson, 405, "Invalid input"}
        };
    }
	
	@Test(dataProvider = "invalidCreatePetData")
    public void TC10011_TC10012_TC10013(String testName, String jsonBody, int expectedStatus, String expectedMessage) {
		ExtentReportListener.logInfo(testName);
		Response response = petFunction.createPet(jsonBody);
		
		ExtentReportListener.logResponse("Response Body", response.asPrettyString());
        assertEquals(response.statusCode(), expectedStatus, "Unexpected status code for input");
        assertEquals(response.jsonPath().getInt("code"), 1, "Error code mismatch");
        assertEquals(response.jsonPath().getString("type"), "error", "Error type should be 'error'");
        assertEquals(response.jsonPath().getString("message"), expectedMessage, "Error message mismatch");
    }
	
}
