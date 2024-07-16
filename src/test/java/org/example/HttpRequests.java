package org.example;

import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;


public class HttpRequests {
    int id;
    @Test(priority = 0)
    void createUser(){
        HashMap data = new HashMap();
        data.put("name","Mohan");
        data.put("job","Sdet");

       id = given()
                .contentType("application/json")
                .body(data)
                .when()
                .post("https://reqres.in/api/users")
                .jsonPath().getInt("id");
    }
    @Test(priority = 1, dependsOnMethods = "createUser")
    void getUser(){
        System.out.println(id);
        given()

                .when()
                .get("https://reqres.in/api/users/"+id)
                .then()
                .statusCode(404)
                .log().all();
   }
   @Test(priority = 2, dependsOnMethods = "createUser")
   void updateUser(){
       HashMap data = new HashMap();
       data.put("name","Mohan_updated");
       data.put("job","Sdet_updated");
        given()
                .contentType("application/json")
                .body(data)
                .when()
                .put("https://reqres.in/api/users/"+id)
                .then()
                .statusCode(200);

   }
   @Test(priority = 3)
    void getUsers(){
       when()
               .get("https://reqres.in/api/users")
               .then()
               .statusCode(200)
               .log().all();
   }
   @Test(priority = 4)
    void deleteUser(){
        when()
                .delete("https://reqres.in/api/users/"+id)
                .then()
                .statusCode(204);
   }

}
