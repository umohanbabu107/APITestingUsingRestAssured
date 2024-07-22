package org.example;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class BookStoreJsonSchemaValidations {
    @Test
    public void studentJsonSchemaValidation(){
        given()
                .header("Content-Type", "application/json")
                .when()
                .get("http://localhost:3002/books?id=1")
                .then()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("jsonSchemas/bookStoreJsonSchema.json"));
    }

    @Test
    public void validateJsonSchemaFromFile() {
        File schema = new File("src/resources/jsonSchemas/bookStoreJsonSchema.json");

        given()
                .header("Content-Type", "application/json")
                .when()
                .get("http://localhost:3002/books?id=1")
                .then()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchema(schema));
    }
}

