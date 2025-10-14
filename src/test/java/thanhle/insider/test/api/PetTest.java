package thanhle.insider.test.api;

import org.testng.annotations.Test;

import io.restassured.response.Response;
import thanhle.insider.api.function.PetFunction;
import thanhle.insider.dataobject.Pet;


public class PetTest extends BaseTest {
	private PetFunction petFunction = new PetFunction();
	
	
	@Test
	public void getPetTest() {
		Response response = petFunction.getPet(1);
		System.out.println(response.getStatusCode());

        Pet pet = Pet.fromResponse(response.getBody());

        System.out.println("Pet name: " + pet.getName());
        System.out.println("Status: " + pet.getStatus());
        System.out.println("Category: " + pet.getCategory().getName());
	}
	
	@Test
	public void createPetTest() {
		Pet pet = new Pet();
		pet.initData();
		
		petFunction.createPet(pet);
		
		System.out.println(pet.toString());
	}
	
}
