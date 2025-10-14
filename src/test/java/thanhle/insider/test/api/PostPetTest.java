package thanhle.insider.test.api;

import static org.testng.Assert.assertEquals;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import thanhle.insider.dataobject.Pet;

public class PostPetTest extends BaseTest {
		
	@Test
	public void postPetTest_valid() {
		Pet pet = new Pet();
		pet.initData();
		Response response = petFunction.createPet(pet);
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
            {missingName, 405, "Invalid input"},
            {invalidStatus, 405, "Invalid input"},
            {emptyJson, 405, "Invalid input"}
        };
    }
	
	@Test(dataProvider = "invalidCreatePetData")
    public void postPetTest_invalid(String jsonBody, int expectedStatus, String expectedMessage) {
        Response response = petFunction.createPet(jsonBody);

        assertEquals(response.statusCode(), expectedStatus, "Unexpected status code for input");
        assertEquals(response.jsonPath().getInt("code"), 1, "Error code mismatch");
        assertEquals(response.jsonPath().getString("type"), "error", "Error type should be 'error'");
        assertEquals(response.jsonPath().getString("message"), expectedMessage, "Error message mismatch");
    }
	
}
