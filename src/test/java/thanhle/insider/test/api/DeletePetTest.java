package thanhle.insider.test.api;

import static org.testng.Assert.assertEquals;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import thanhle.insider.dataobject.Pet;

public class DeletePetTest extends BaseTest {
		
	@Test
	public void deletePetTest_valid() {
		Pet pet = new Pet();
		pet.initData();
		petFunction.createPet(pet);
		
		Response response = petFunction.deletePet(apiKey, pet.getId());
		assertEquals(response.getStatusCode(), 200, "Status code should be 200");
		
		assertEquals(response.jsonPath().getInt("code"), 200, "Body code is not as expected");
		assertEquals(response.jsonPath().getString("type"), "unknown", "Body type is not as expected");
		assertEquals(response.jsonPath().getString("message"), "1", "Body message is not as expected");
	}
	
	
	@DataProvider(name = "invalidDeletePetData")
	public Object[][] deletePetData() {
	    return new Object[][]{
	        {654321, "special-key", 404, "Pet not found"},
	        {-1, "special-key", 400, "Invalid ID supplied"},
	        {1, "", 403, "Invalid API key"},
	        {1, "wrong-key", 403, "Invalid API key"},
	    };
	}
	
	@Test(dataProvider = "invalidDeletePetData")
	public void deletePetTest_invalid(Object petID, String apiKey, int expectedStatusCode, String expectedMessage) {
		Response response;
		
		if (petID instanceof Integer) {
			response = petFunction.getPet((int) petID);
		}else {
			response = petFunction.getPet((String) petID);
		}
		
		assertEquals(response.getStatusCode(), expectedStatusCode, "Status code is not as expected");
		assertEquals(response.jsonPath().getInt("code"), expectedStatusCode, "Body code is not as expected");
		assertEquals(response.jsonPath().getString("message"), expectedMessage, "Body message is not as expected");
	}
}
