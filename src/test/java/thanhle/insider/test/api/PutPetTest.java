package thanhle.insider.test.api;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import thanhle.insider.dataobject.Pet;

public class PutPetTest extends BaseTest {
	
	@Test
	public void putPetTest_valid() {
		Pet pet = new Pet();
		pet.initData();
		petFunction.createPet(pet);
		
		pet.setName("Updated Pet Name");
		
		Response response = petFunction.updatePet(pet);
		assertEquals(response.getStatusCode(), 200, "Status code should be 200");
		
		Pet actualPet = Pet.fromResponse(response.getBody());
		assertPet(actualPet, pet);
	}
	
	@DataProvider(name = "invalidUpdatePetData")
    public Object[][] invalidUpdatePetData() {
        
        String invalidId = """
        {
          "id": -1,
          "category": {"id": 1, "name": "dogs"},
          "name": "BadID",
          "photoUrls": ["https://example.com/dog.jpg"],
          "tags": [{"id": 10, "name": "friendly"}],
          "status": "available"
        }""";

        String notFoundPet = """
        {
          "id": 99999999,
          "category": {"id": 1, "name": "dogs"},
          "name": "GhostPet",
          "photoUrls": ["https://example.com/dog.jpg"],
          "tags": [{"id": 10, "name": "friendly"}],
          "status": "available"
        }""";

        String validationError = """
        {
          "id": 1002,
          "category": {"id": 1, "name": "dogs"},
          "photoUrls": ["https://example.com/dog.jpg"],
          "tags": [{"id": 10, "name": "friendly"}],
          "status": "available"
        }""";

        return new Object[][]{
            {invalidId, 400, "Invalid ID supplied"},
            {notFoundPet, 404, "Pet not found"},
            {validationError, 405, "Validation exception"}
        };
    }
	
	@Test(dataProvider = "invalidUpdatePetData")
	public void putPetTest_invalid(String jsonBody, int expectedStatus, String expectedMessage) {
        Response response = petFunction.createPet(jsonBody);

        assertEquals(response.statusCode(), expectedStatus, "Unexpected status code for input");
        assertEquals(response.jsonPath().getInt("code"), expectedStatus, "Error code mismatch");
        assertEquals(response.jsonPath().getString("type"), "error", "Error type should be 'error'");
        assertEquals(response.jsonPath().getString("message"), expectedMessage, "Error message mismatch");
    }
	
}
