package org.example;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class PathAndQueryParamsTests {
    String url = "http://localhost:3001/";
    @Test
    public void pathAndQueryParamsTest(){
        given()
                .pathParam("path","students")
                .queryParam("id",5)
                .when()
                .get(url+"{path}")
                .then()
                .statusCode(200)
                .body("[0].id", equalTo(5))
                .body("[0].name", equalTo("Charlie Brown"))
                .body("[0].age", equalTo(23))
                .body("[0].gender", equalTo("Male"))
                .body("[0].course", equalTo("Computer Networks"))
                .body("[0].country", equalTo("Australia"));
    }
}
