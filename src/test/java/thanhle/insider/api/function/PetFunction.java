package thanhle.insider.api.function;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import thanhle.insider.dataobject.Pet;
import thanhle.insider.utility.JsonUtils;

public class PetFunction {

	/*
	 * CREATE
	 * */
	public Response createPet(Pet pet) {
		return createPet(JsonUtils.toJsonString(pet));
	}

	public Response createPet(String jsonString) {
		return RestAssured.given().contentType(ContentType.JSON).body(jsonString).post("/pet");
	}

	/*
	 * GET
	 * */
	public Response getPet(int id) {
		return RestAssured.get(String.format("/pet/%d", id));
	}
	
	public Response getPet(long id) {
		return RestAssured.get(String.format("/pet/%d", id));
	}

	public Response getPet(String id) {
		return RestAssured.get(String.format("/pet/%s", id));
	}

	/*
	 * UPDATE
	 * */
	public Response updatePet(Pet pet) {
		return updatePet(JsonUtils.toJsonString(pet));
	}	
	
	public Response updatePet(String jsonString) {
		return RestAssured.given().contentType(ContentType.JSON).body(jsonString).post("/pet");
	}

	/*
	 * DELETE
	 * */	
	public Response deletePet(String apiKey, int id) {
		return RestAssured.given().header("api_key", apiKey).delete(String.format("/pet/%d", id));
	}
	
}
