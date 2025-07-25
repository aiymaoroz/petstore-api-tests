package test;

import com.github.javafaker.Faker;
import endpoints.UserEndPoints;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import payload.User;

import static org.testng.Assert.assertEquals;

public class UserTests {

    Faker faker;
    User userPayload;
    public Logger logger;

    @BeforeClass
    public void setup() {
        faker = new Faker();

        userPayload = new User();

        userPayload.setId(faker.idNumber().hashCode());
        userPayload.setUsername(faker.name().username());
        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setEmail(faker.internet().safeEmailAddress());
        userPayload.setPassword(faker.internet().password(5, 10));
        userPayload.setPhone(faker.phoneNumber().cellPhone());

        //logs
        logger= LogManager.getLogger(this.getClass());
    }

    @Test(priority = 1)
    public void testPostUser() {
        logger.info("**************** Creating user ****************");
        Response response = UserEndPoints.createUser(userPayload);
        response.then().log().all();
        assertEquals(response.getStatusCode(), 200);
        logger.info("**************** User is created ****************");
    }

    @Test(priority = 2)
    public void testGetUserByName() throws InterruptedException {
        Response response = null;
        int retries = 5;
        int waitTimeMs = 1000; // 1 second between retries

        logger.info("**************** Reading User info ****************");
        for (int i = 0; i < retries; i++) {
            response = UserEndPoints.readUser(this.userPayload.getUsername());
            if (response.getStatusCode() == 200) {
                break; // success!
            }
            Thread.sleep(waitTimeMs);
        }
        response.then().log().all();
        assertEquals(response.getStatusCode(), 200, "User was not found after retries");
        logger.info("**************** User info is read ****************");
    }

    @Test(priority = 3)
    public void testUpdateUser() {
        logger.info("**************** Updating user ****************");
        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setEmail(faker.internet().safeEmailAddress());

        Response response = UserEndPoints.updateUser(this.userPayload.getUsername(), userPayload);
        response.then().log().body();
        assertEquals(response.getStatusCode(), 200);
        logger.info("**************** User is updated ****************");

        //checking data after update
        Response getResponse = UserEndPoints.readUser(this.userPayload.getUsername());
        getResponse.then().log().body();
        assertEquals(getResponse.getStatusCode(), 200);
    }

    @Test(priority = 4)
    public void testDeleteUserByName() {
        logger.info("**************** Deleting user ****************");
        Response response = UserEndPoints.deleteUser(this.userPayload.getUsername());
        response.then().log().all();
        assertEquals(response.getStatusCode(), 200);
        logger.info("**************** User is deleted ****************");
    }
}
