package endpoints;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import payload.User;

import static io.restassured.RestAssured.given;
import static utilities.ConfigReader.getProperty;

public class UserEndPoints {
    public static Response createUser(User payload) {
        String postUrl = getProperty("user_post_url");
        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
                .when()
                .post(postUrl);
    }

    public static Response readUser(String username) {
        String getUrl = getProperty("user_get_url");
        return given()
                .pathParam("username", username)
                .when()
                .get(getUrl);
    }

    public static Response updateUser(String username, User payload) {
        String updateUrl = getProperty("user_update_url");
        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .pathParam("username", username)
                .body(payload)
                .when()
                .put(updateUrl);
    }

    public static Response deleteUser(String username) {
        String deleteUrl = getProperty("user_delete_url");
        return given()
                .pathParam("username", username)
                .when()
                .delete(deleteUrl);
    }
}
