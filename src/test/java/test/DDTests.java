package test;

import endpoints.UserEndPoints;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import payload.User;
import utilities.DataProviders;

import static org.testng.Assert.assertEquals;

public class DDTests {

    @Test(priority = 1, dataProvider = "Data", dataProviderClass = DataProviders.class)
    public void testPostUser(String id, String username, String firstName, String lastName,
                             String email, String password, String phone) {
        User userPayload = new User();
        userPayload.setId(Integer.parseInt(id));
        userPayload.setUsername(username);
        userPayload.setFirstName(firstName);
        userPayload.setLastName(lastName);
        userPayload.setEmail(email);
        userPayload.setPassword(password);
        userPayload.setPhone(phone);
        Response response = UserEndPoints.createUser(userPayload);
        assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 2, dataProvider = "Usernames", dataProviderClass = DataProviders.class)
    public void testDeleteUserByName(String username) {
        Response response = UserEndPoints.deleteUser(username);
        assertEquals(response.getStatusCode(), 200);
    }

}
