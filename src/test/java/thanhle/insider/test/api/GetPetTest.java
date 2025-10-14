package thanhle.insider.test.api;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import thanhle.insider.dataobject.Pet;

public class GetPetTest extends BaseTest {
	Pet pet;

	@BeforeClass
	public void beforeClass() {
		pet = new Pet();
		pet.initData();
		petFunction.createPet(pet);
		System.out.println(pet.toString());
	}
	
	@AfterClass
	public void afterClass() {
		petFunction.deletePet(apiKey, pet.getId());
	}
	
	@Test
	public void getPetTest_valid() {
		Response response = petFunction.getPet(pet.getId());
		assertEquals(response.getStatusCode(), 200, "Status code should be 200");

		Pet actualPet = Pet.fromResponse(response.getBody());
		assertPet(actualPet, pet);
	}

	@DataProvider(name = "invalidPetData")
    public Object[][] invalidPetData() {
        return new Object[][]{
                {null, 400, 1, "error", "Invalid ID supplied"},
                {" ", 400, 1, "error", "Invalid ID supplied"}, 
                {"invalid", 400, 1, "error", "Invalid ID supplied"}, 
                {2147483648L, 400, 1, "error", "Invalid ID supplied"},
                {-1, 400, 1, "error", "Invalid ID supplied"},
                {8889, 404, 1, "error", "Pet not found"}
        };
    }
	
	@Test(dataProvider = "invalidPetData")
	public void getPetTest_invalid(Object petID, int expectedStatusCode, int expectedBodyCode, String expectedErrorType, String expectedMessage) {
		Response response;
		
		if (petID instanceof Integer) {
			response = petFunction.getPet((int) petID);
		}else {
			response = petFunction.getPet((String) petID);
		}
		
		assertEquals(response.getStatusCode(), expectedStatusCode, "Status code is not as expected");
		assertEquals(response.jsonPath().getInt("code"), expectedBodyCode, "Body code is not as expected");
		assertEquals(response.jsonPath().getString("type"), expectedErrorType, "Body type is not as expected");
		assertEquals(response.jsonPath().getString("message"), expectedMessage, "Body message is not as expected");
	}
}
