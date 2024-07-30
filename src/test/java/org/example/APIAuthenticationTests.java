package org.example;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class APIAuthenticationTests {
    //@Test
    public void basicAuthTest(){
        given()
                //.auth().basic()
                .when()
                .get("https://postman-echo.com/basic-auth")
                .then()
                .statusCode(200)
                .body("authenticated", equalTo(true));
    }
    //@Test
    public void bearerTokenAuth(){
        //final String bearerToken; provide token from github
        given()
               // .header()
                .when()
                .get("https://api.github.com/user/repos")
                .then()
                .statusCode(200)
                .log().all();
    }
    //@Test
    public void oAuth1Test(){
        given()
                //.auth()
                //.oauth()
                .when()
                .get("https://api.example.com/protected-resource")
                .then()
                .statusCode(200)
                .log().all();
    }
    //@Test
    public void oAuth2Test(){
        given()
               // .auth().oauth2()
                .when()
                .get("https://api.example.com/protected-resource")
                .then()
                .statusCode(200)
                .log().all();
    }
    //@Test
    public void apiKeyAuthTest(){
        // Go to https://home.openweathermap.org/ and login with umohanbabu107@gmail.com and Mohan/990
        // Then you will get the Api key. Send that API key through the query parameters
        given()
                //.queryParam()
                .when()
                .get("https://api.openweathermap.org/data/2.5/forecast/daily?q=hyderabad&units=metric&cnt=7")
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .log().all();
//        String apiKey ; // Replace with your actual API key
//        String lat = "17.3850";
//        String lon = "78.4867";
//        int cnt = 7;
//
//        given()
//                .queryParam("lat", lat)
//                .queryParam("lon", lon)
//                .queryParam("cnt", cnt)
//                .queryParam("appid", apiKey)
//                .when()
//                .get("https://api.openweathermap.org/data/2.5/forecast/daily")
//                .then()
//                .statusCode(200)
//                .log().all();

    }
}
