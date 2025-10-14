package thanhle.insider.api.function;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import thanhle.insider.dataobject.Pet;
import thanhle.insider.utility.JsonUtils;

public class PetFunction {

	public Response createPet(Pet pet) {
        String json = JsonUtils.toJsonString(pet);
		return createPet(json);
	}
	
	public Response createPet(String jsonString) {
		return RestAssured.given().contentType(ContentType.JSON).body(jsonString).post("/pet");
	}

	public Response getPet(long id) {
		return RestAssured.get(String.format("/pet/%d", id));
	}
	
	public Response getPet(String id) {
		return RestAssured.get(String.format("/pet/%s", id));
	}

	public Response updatePet(Pet pet) {
		return RestAssured.given().contentType(ContentType.JSON).body(pet).put("/pet");
	}

	public Response deletePet(long id) {
		return RestAssured.delete(String.format("/pet/%d", id));
	}

}
