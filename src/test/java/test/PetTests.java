package test;

import endpoints.PetEndPoints;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import payload.Pet;
import utilities.DataProviders;
import utilities.Helper;

import java.util.Collections;

import static org.testng.Assert.assertEquals;

public class PetTests {

    public Logger logger = LogManager.getLogger(this.getClass());
    Helper helper = new Helper();

    @Test(priority = 1, dataProvider = "PetData", dataProviderClass = DataProviders.class)
    public void testPostPet(String id, String categoryId, String categoryName, String name,
                            String photoUrl, String tagId, String tagName, String status) {

        Pet.Category category = new Pet.Category(Integer.parseInt(categoryId), categoryName);
        Pet.Tag tag = new Pet.Tag(Integer.parseInt(tagId), tagName);

        Pet petPayload = new Pet(
                Integer.parseInt(id),
                category,
                name,
                Collections.singletonList(photoUrl),
                Collections.singletonList(tag),
                status
        );

        logger.info("******** Creating pet: " + name + " ********");
        Response response = PetEndPoints.createPet(petPayload);
        response.then().log().all();
        assertEquals(response.getStatusCode(), 200);
        logger.info("******** Pet created: " + name + " ********");
    }

    @Test(priority = 2, dataProvider = "PetData", dataProviderClass = DataProviders.class)
    public void testGetPetById(String id, String categoryId, String categoryName, String name,
                               String photoUrl, String tagId, String tagName, String status) throws InterruptedException {

        logger.info("******** Reading pet ID: " + id + " ********");

        Response response = helper.retryReadingPet(Integer.parseInt(id));
        response.then().log().all();
        assertEquals(response.getStatusCode(), 200);

        Pet actualPet = response.as(Pet.class);
        assertEquals(actualPet.getId(), Integer.parseInt(id));
        assertEquals(actualPet.getName(), name);
        assertEquals(actualPet.getStatus(), status);

        assertEquals(actualPet.getCategory().getId(), Integer.parseInt(categoryId));
        assertEquals(actualPet.getCategory().getName(), categoryName);

        assertEquals(actualPet.getPhotoUrls().getFirst(), photoUrl);

        assertEquals(actualPet.getTags().getFirst().getId(), Integer.parseInt(tagId));
        assertEquals(actualPet.getTags().getFirst().getName(), tagName);

        logger.info("******** Finished reading pet: " + name + " ********");
    }

    @Test(priority = 3, dataProvider = "PetData", dataProviderClass = DataProviders.class)
    public void testUpdatePet(String id, String categoryId, String categoryName, String name,
                              String photoUrl, String tagId, String tagName, String status) throws InterruptedException {

        Pet.Category category = new Pet.Category(Integer.parseInt(categoryId), categoryName + "_Updated");
        Pet.Tag tag = new Pet.Tag(Integer.parseInt(tagId), tagName + "_Updated");

        Pet petPayload = new Pet(
                Integer.parseInt(id),
                category,
                name + "_Updated",
                Collections.singletonList(photoUrl + "/updated"),
                Collections.singletonList(tag),
                "pending"
        );

        logger.info("******** Updating pet ID: " + id + " ********");
        Response updateResponse = helper.retryUpdatingPet(petPayload);
        updateResponse.then().log().all();
        assertEquals(updateResponse.getStatusCode(), 200);

        // unstable petstore API may not return the updated user immediately
        Thread.sleep(5000);

        Response getResponse = helper.retryReadingPet(Integer.parseInt(id));
        Pet actualPet = getResponse.as(Pet.class);

        assertEquals(actualPet.getName(), petPayload.getName());
        assertEquals(actualPet.getStatus(), "pending");
        assertEquals(actualPet.getCategory().getName(), categoryName + "_Updated");
        assertEquals(actualPet.getTags().getFirst().getName(), tagName + "_Updated");

        logger.info("******** Pet updated: " + id + " ********");
    }

    @Test(priority = 4, dataProvider = "PetData", dataProviderClass = DataProviders.class)
    public void testDeletePet(String id, String categoryId, String categoryName, String name,
                              String photoUrl, String tagId, String tagName, String status) throws InterruptedException {

        logger.info("******** Deleting pet ID: " + id + " ********");
        Response response = helper.retryDeletingPet(Integer.parseInt(id));
        response.then().log().all();
        assertEquals(response.getStatusCode(), 200);
        logger.info("******** Pet deleted: " + id + " ********");
    }
}
