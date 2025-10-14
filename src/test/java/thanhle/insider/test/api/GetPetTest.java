package thanhle.insider.test.api;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import thanhle.insider.api.function.PetFunction;
import thanhle.insider.dataobject.Pet;
import thanhle.insider.dataobject.Tag;

public class GetPetTest extends BaseTest {
	private PetFunction petFunction = new PetFunction();
	Pet pet = new Pet();

	@BeforeClass
	public void beforeClass() {
		pet.initData();
		petFunction.createPet(pet);
		System.out.println(pet.toString());
	}
	
	@AfterClass
	public void afterClass() {
		petFunction.deletePet(pet.getId());
	}

	@DataProvider(name = "invalidPetData")
    public Object[][] invalidPetData() {
        return new Object[][]{
                {null, 400, 1, "error", "Invalid ID supplied"},
                {" ", 400, 1, "error", "Invalid ID supplied"}, 
                {"invalid", 400, 1, "error", "Invalid ID supplied"}, 
                {2147483648L, 400, 1, "error", "Invalid ID supplied"},
                {-1L, 404, 1, "error", "Pet not found"}
        };
    }
	
	@Test(dataProvider = "invalidPetData")
	public void getPetTest_invalid(Object petID, int expectedStatusCode, int expectedBodyCode, String expectedErrorType, String expectedMessage) {
		Response response;
		
		if (petID instanceof Long) {
			response = petFunction.getPet((long) petID);
		}else {
			response = petFunction.getPet((String) petID);
		}
		
		assertEquals(response.getStatusCode(), expectedStatusCode, "Status code is not as expected");
		assertEquals(response.jsonPath().getInt("code"), expectedBodyCode, "Body code is not as expected");
		assertEquals(response.jsonPath().getString("type"), expectedErrorType, "Body type is not as expected");
		assertEquals(response.jsonPath().getString("message"), expectedMessage, "Body message is not as expected");
	}
	
	@Test
	public void getPetTest_valid() {
		Response response = petFunction.getPet(pet.getId());
		assertEquals(response.getStatusCode(), 200, "Status code should be 200");

		Pet actualPet = Pet.fromResponse(response.getBody());
		assertPet(actualPet, pet);
	}

	private void assertPet(Pet actual, Pet expected) {
		assertEquals(actual.getName(), expected.getName(), "Pet's name is not as expected");
		assertEquals(actual.getStatus(), expected.getStatus(), "Pet's status is not as expected");
		assertEquals(actual.getCategory().getId(), expected.getCategory().getId(), "Pet's name is not as expected");
		assertEquals(actual.getCategory().getName(), expected.getCategory().getName(), "Pet's name is not as expected");

		List<String> actualPhotoUrls = actual.getPhotoUrls();
		List<String> expectedPhotoUrls = expected.getPhotoUrls();

		for (String expectedPhotoUrl : expectedPhotoUrls) {
			assertTrue(actualPhotoUrls.contains(expectedPhotoUrl), "Pet's photoUrl is not as expected");
		}

		List<Tag> actualTags = actual.getTags();
		List<Tag> expectedTags = expected.getTags();

		for (Tag expectedTag : expectedTags) {
			Tag actualTag = null;
			
			for (Tag tag : actualTags) {
		        if (tag.getId() == expectedTag.getId()) {
		        	actualTag = tag;
		        	break;
		        }
		    }
			
			assertTrue(actualTag!=null, "Pet's tag not found");
			assertEquals(actual.getName(), expectedTag.getName(), "\"Pet's tag name is not as expected");
		}
	}
}
