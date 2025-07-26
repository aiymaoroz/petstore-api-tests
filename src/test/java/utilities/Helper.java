package utilities;

import endpoints.PetEndPoints;
import endpoints.UserEndPoints;
import io.restassured.response.Response;

public class Helper {

    public Response retryReadingUser(String username) throws InterruptedException {
        Response response = null;
        int retries = 10;
        int waitTimeMs = 1500;
        for (int i = 0; i < retries; i++) {
            response = UserEndPoints.readUser(username);
            if (response.getStatusCode() == 200) break;
            Thread.sleep(waitTimeMs);
        }
        return response;
    }

    public Response retryDeletingUser(String username) throws InterruptedException {
        Response response = null;
        int retries = 7;
        int waitTimeMs = 1500;
        for (int i = 0; i < retries; i++) {
            response = UserEndPoints.deleteUser(username);
            if (response.getStatusCode() == 200) break;
            Thread.sleep(waitTimeMs);
        }
        return response;
    }

    public Response retryReadingPet(int id) throws InterruptedException {
        Response response = null;
        int retries = 10;
        int waitTimeMs = 1500;
        for (int i = 0; i < retries; i++) {
            response = PetEndPoints.readPet(id);
            if (response.getStatusCode() == 200) break;
            Thread.sleep(waitTimeMs);
        }
        return response;
    }
    public Response retryDeletingPet(int id) throws InterruptedException {
        Response response = null;
        int retries = 8;
        int waitTimeMs = 1000;
        for (int i = 0; i < retries; i++) {
            response = PetEndPoints.deletePet(id);
            if (response.getStatusCode() == 200) break;
            Thread.sleep(waitTimeMs);
        }
        return response;
    }
    public Response retryUpdatingPet(payload.Pet pet) throws InterruptedException {
        Response response = null;
        int retries = 7;
        int waitTimeMs = 1500;
        for (int i = 0; i < retries; i++) {
            response = PetEndPoints.updatePet(pet);
            if (response.getStatusCode() == 200) break;
            Thread.sleep(waitTimeMs);
        }
        return response;
    }
    public Response retryUpdatingUser(String username, payload.User user) throws InterruptedException {
        Response response = null;
        int retries = 7;
        int waitTimeMs = 1500;

        for (int i = 0; i < retries; i++) {
            response = UserEndPoints.updateUser(username, user);
            if (response.getStatusCode() == 200) break;
            Thread.sleep(waitTimeMs);
        }
        return response;
    }


}
