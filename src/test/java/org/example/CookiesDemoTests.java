package org.example;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.Set;

import static io.restassured.RestAssured.*;

public class CookiesDemoTests {
    @Test
    public void verifyCookies(){
        when()
                .get("https://www.google.com/")
                .then()
                .statusCode(200)
                .cookie("AEC")
                .cookie("NID");
    }
    @Test
    public void getCookies(){
        Response response = when().get("https://www.google.com/");
        Map<String,String> cookies = response.getCookies();
        Set<String> cookiesKeys = cookies.keySet();
        for(String cookiesKey : cookiesKeys){
            System.out.println(cookiesKey+ " --> "+cookies.get(cookiesKey));
        }
    }
}
