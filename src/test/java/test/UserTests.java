package test;

import endpoints.UserEndPoints;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import payload.User;
import utilities.DataProviders;
import com.github.javafaker.Faker;
import utilities.Helper;

import static org.testng.Assert.assertEquals;

public class UserTests {

    public Logger logger = LogManager.getLogger(this.getClass());
    Faker faker = new Faker();
    Helper helper = new Helper();

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

        logger.info("******** Creating user: " + username + " ********");
        Response response = UserEndPoints.createUser(userPayload);
        response.then().log().all();
        assertEquals(response.getStatusCode(), 200);
        logger.info("******** User created: " + username + " ********");
    }

    @Test(priority = 2, dataProvider = "Data", dataProviderClass = DataProviders.class)
    public void testGetUserByName(String id, String username, String firstName, String lastName,
                                  String email, String password, String phone) throws InterruptedException {
        logger.info("******** Reading user: " + username + " ********");
        Response response = helper.retryReadingUser(username);
        response.then().log().all();
        assertEquals(response.getStatusCode(), 200);

        User actualUser = response.as(User.class);
        assertEquals(actualUser.getUsername(), username);
        assertEquals(actualUser.getFirstName(), firstName);
        assertEquals(actualUser.getLastName(), lastName);
        assertEquals(actualUser.getEmail(), email);
        assertEquals(actualUser.getPassword(), password);
        assertEquals(actualUser.getPhone(), phone);
        logger.info("******** Finished reading user: " + username + " ********");
    }

    @Test(priority = 3, dataProvider = "Data", dataProviderClass = DataProviders.class)
    public void testUpdateUser(String id, String username, String firstName, String lastName,
                               String email, String password, String phone) throws InterruptedException {
        User updatedPayload = new User();
        updatedPayload.setId(Integer.parseInt(id));
        updatedPayload.setUsername(username);
        updatedPayload.setFirstName(faker.name().firstName());
        updatedPayload.setLastName(faker.name().lastName());
        updatedPayload.setEmail(faker.internet().safeEmailAddress());
        updatedPayload.setPassword(password);
        updatedPayload.setPhone(phone);

        logger.info("******** Updating user: " + username + " ********");
        Response updateResponse = helper.retryUpdatingUser(username, updatedPayload);
        updateResponse.then().log().body();
        assertEquals(updateResponse.getStatusCode(), 200);

        // unstable petstore API may not return the updated user immediately
        Thread.sleep(6000);

        Response response = helper.retryReadingUser(username);
        User actualUser = response.as(User.class);

        assertEquals(response.getStatusCode(), 200);
        assertEquals(actualUser.getUsername(), username);
        assertEquals(actualUser.getFirstName(), updatedPayload.getFirstName());
        assertEquals(actualUser.getLastName(), updatedPayload.getLastName());
        assertEquals(actualUser.getEmail(), updatedPayload.getEmail());
        assertEquals(actualUser.getPassword(), password);
        assertEquals(actualUser.getPhone(), phone);
        logger.info("******** User updated: " + username + " ********");
    }

    @Test(priority = 4, dataProvider = "Usernames", dataProviderClass = DataProviders.class)
    public void testDeleteUser(String username) throws InterruptedException {
        logger.info("******** Deleting user: " + username + " ********");
        Response response = helper.retryDeletingUser(username);
        response.then().log().all();
        assertEquals(response.getStatusCode(), 200);
        logger.info("******** User deleted: " + username + " ********");
    }
}
