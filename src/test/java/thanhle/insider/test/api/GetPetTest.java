package thanhle.insider.test.api;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import thanhle.insider.dataobject.Pet;
import thanhle.insider.utility.ExtentReportListener;

public class GetPetTest extends BaseTest {
	Pet pet;

	@BeforeClass
	public void beforeClass() {
		pet = new Pet();
		pet.initData();
		petFunction.createPet(pet);
	}

	@AfterClass
	public void afterClass() {
		petFunction.deletePet(apiKey, pet.getId());
	}

	@Test
	public void TC10001() {
		ExtentReportListener.logInfo("GET Pet with valid request");
		Response response = petFunction.getPet(pet.getId());
		ExtentReportListener.logResponse("Response Body", response.asPrettyString());

		assertEquals(response.getStatusCode(), 200, "Status code should be 200");
		Pet actualPet = Pet.fromResponse(response.getBody());
		assertPet(actualPet, pet);
	}

	@DataProvider(name = "invalidPetData")
	public Object[][] invalidPetData() {
		return new Object[][] { { "GET Pet with null ID", null, 400, 1, "error", "Invalid ID supplied" },
				{ "GET Pet with empty ID", " ", 400, 1, "error", "Invalid ID supplied" },
				{ "GET Pet with 'invalid' ID", "invalid", 400, 1, "error", "Invalid ID supplied" },
				{ "GET Pet with ID is a long", 2147483648L, 400, 1, "error", "Invalid ID supplied" },
				{ "GET Pet with negative ID", -1, 400, 1, "error", "Invalid ID supplied" },
				{ "GET Pet with unexisting ID", 8889, 404, 1, "error", "Pet not found" } };
	}

	@Test(dataProvider = "invalidPetData")
	public void TC10002_TC10003_TC10004_TC10005_TC10006_TC10007(String testName, Object petID, int expectedStatusCode,
			int expectedBodyCode, String expectedErrorType, String expectedMessage) {
		ExtentReportListener.logInfo(testName);
		Response response;

		if (petID instanceof Integer) {
			response = petFunction.getPet((int) petID);
		} else if (petID instanceof Long) {
			response = petFunction.getPet((long) petID);
		}
		else {
			response = petFunction.getPet((String) petID);
		}

		ExtentReportListener.logResponse("Response Body", response.asPrettyString());
		assertEquals(response.getStatusCode(), expectedStatusCode, "Status code is not as expected");
		assertEquals(response.jsonPath().getInt("code"), expectedBodyCode, "Body code is not as expected");
		assertEquals(response.jsonPath().getString("type"), expectedErrorType, "Body type is not as expected");
		assertEquals(response.jsonPath().getString("message"), expectedMessage, "Body message is not as expected");
	}
}
