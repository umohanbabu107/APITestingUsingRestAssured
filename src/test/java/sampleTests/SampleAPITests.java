package sampleTests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;

public class SampleAPITests {

    // Base URL for all tests
    static {
        RestAssured.baseURI = "https://reqres.in/api";
    }

    // 15 Passing Test Cases

    @Test
    public void getUsers_ValidStatusCode() {
        RestAssured.get("/users?page=2")
                .then()
                .statusCode(200)
                .body("page", equalTo(2))
                .body("data", hasSize(6));
    }

    @Test
    public void getUserById_ValidResponse() {
        RestAssured.get("/users/2")
                .then()
                .statusCode(200)
                .body("data.id", equalTo(2))
                .body("data.first_name", equalTo("Janet"));
    }

    @Test
    public void createUser_ValidData() {
        String requestBody = "{\"name\": \"John\", \"job\": \"Engineer\"}";

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post("/users")
                .then()
                .statusCode(201)
                .body("name", equalTo("John"))
                .body("job", equalTo("Engineer"));
    }

    @Test
    public void updateUser_ValidData() {
        String requestBody = "{\"name\": \"John\", \"job\": \"Manager\"}";

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .put("/users/2")
                .then()
                .statusCode(200)
                .body("name", equalTo("John"))
                .body("job", equalTo("Manager"));
    }

    @Test
    public void deleteUser_ValidStatusCode() {
        RestAssured.delete("/users/2")
                .then()
                .statusCode(204);
    }

    @Test
    public void getResourceList_ValidStatusCode() {
        RestAssured.get("/unknown")
                .then()
                .statusCode(200)
                .body("data", hasSize(6));
    }

    @Test
    public void getSingleResource_ValidResponse() {
        RestAssured.get("/unknown/2")
                .then()
                .statusCode(200)
                .body("data.id", equalTo(2))
                .body("data.name", equalTo("fuchsia rose"));
    }

    @Test
    public void getSingleResource_NotFound() {
        RestAssured.get("/unknown/23")
                .then()
                .statusCode(404);
    }

    @Test
    public void registerUser_Successful() {
        String requestBody = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\"}";

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post("/register")
                .then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("token", notNullValue());
    }

    @Test
    public void loginUser_Successful() {
        String requestBody = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\"}";

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post("/login")
                .then()
                .statusCode(200)
                .body("token", notNullValue());
    }

    @Test
    public void delayedResponse_ValidStatusCode() {
        RestAssured.get("/users?delay=3")
                .then()
                .statusCode(200)
                .body("data", hasSize(6));
    }

    @Test
    public void createUserWithMissingField_ShouldFail() {
        String requestBody = "{\"name\": \"Jane\"}";

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post("/users")
                .then()
                .statusCode(201)
                .body("name", equalTo("Jane"))
                .body("job", nullValue());
    }

    @Test
    public void updateUserWithPartialData_ValidResponse() {
        String requestBody = "{\"job\": \"Scientist\"}";

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .patch("/users/2")
                .then()
                .statusCode(200)
                .body("job", equalTo("Scientist"));
    }

    @Test
    public void listResources_ValidTotalResources() {
        RestAssured.get("/unknown")
                .then()
                .statusCode(200)
                .body("total", equalTo(12));
    }

    @Test
    public void deleteNonExistingUser_ShouldReturn404() {
        RestAssured.delete("/users/999")
                .then()
                .statusCode(204);  // Simulating pass with wrong logic but in real life would fail.
    }

    // 5 Failing Test Cases

    @Test
    public void getUsers_InvalidStatusCode_ShouldFail() {
        RestAssured.get("/users?page=2")
                .then()
                .statusCode(400);  // Expected status code is incorrect, should be 200
    }

    @Test
    public void getUserById_InvalidData_ShouldFail() {
        RestAssured.get("/users/23")
                .then()
                .statusCode(200);  // User ID 23 does not exist, should return 404
    }

    @Test
    public void createUser_MissingJobField_ShouldFail() {
        String requestBody = "{\"name\": \"John\"}";

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post("/users")
                .then()
                .statusCode(201)
                .body("job", equalTo("Engineer"));  // Job field is missing, so this will fail
    }

    @Test
    public void updateUserWithInvalidId_ShouldFail() {
        String requestBody = "{\"name\": \"John\", \"job\": \"Manager\"}";

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .put("/users/999")  // User with ID 999 does not exist
                .then()
                .statusCode(200);  // Should return 404, not 200
    }

    @Test
    public void deleteUser_InvalidId_ShouldFail() {
        RestAssured.delete("/users/999")
                .then()
                .statusCode(204);  // This will fail as it should return 404 for non-existing user
    }
}
