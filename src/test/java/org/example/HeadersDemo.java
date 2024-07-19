package org.example;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
public class HeadersDemo {
    @Test
    public void testHeaders(){
        when()
                .get("https://www.google.com/")
                .then()
                .statusCode(200)
                .header("Content-Type","text/html; charset=ISO-8859-1")
                .header("Cache-Control","private, max-age=0")
                .header("Content-Encoding","gzip")
                .header("Transfer-Encoding","chunked")
                .header("Server","gws");
    }
    @Test
    public void getAllHeaders(){
        Response response = when().get("https://www.google.com/");
        Headers headers = response.getHeaders();
        for(Header header: headers){
            System.out.println(header.getName()+ " --> "+ header.getValue());
        }
    }
}
