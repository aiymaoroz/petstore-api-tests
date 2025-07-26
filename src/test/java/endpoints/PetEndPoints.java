package endpoints;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import payload.Pet;

import static io.restassured.RestAssured.given;
import static utilities.ConfigReader.getProperty;

public class PetEndPoints {

    public static Response createPet(Pet payload) {
        String postUrl = getProperty("pet_post_url");
        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
                .when()
                .post(postUrl);
    }

    public static Response readPet(int petId) {
        String getUrl = getProperty("pet_get_url");
        return given()
                .pathParam("petId", petId)
                .when()
                .get(getUrl);
    }

    public static Response updatePet(Pet payload) {
        String updateUrl = getProperty("pet_update_url");
        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
                .when()
                .put(updateUrl);
    }

    public static Response deletePet(int petId) {
        String deleteUrl = getProperty("pet_delete_url");
        return given()
                .pathParam("petId", petId)
                .when()
                .delete(deleteUrl);
    }
}
