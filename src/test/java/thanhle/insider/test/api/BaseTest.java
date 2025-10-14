package thanhle.insider.test.api;

import io.restassured.RestAssured;
import thanhle.insider.api.function.PetFunction;
import thanhle.insider.dataobject.Pet;
import thanhle.insider.dataobject.Tag;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;


public class BaseTest {

	protected final String apiKey = "special-key";
	protected PetFunction petFunction = new PetFunction();
	
	@BeforeSuite
	public void beforeSuite() {
		RestAssured.baseURI = "https://petstore.swagger.io/v2";
	}
	
	protected void assertPet(Pet actual, Pet expected) {
		
		System.out.println(actual.toString());
		System.out.println(expected.toString());
		
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
			assertEquals(actualTag.getName(), expectedTag.getName(), "\"Pet's tag name is not as expected");
		}
	}

}
