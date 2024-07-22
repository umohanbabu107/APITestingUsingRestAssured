package org.example;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.Set;

import static io.restassured.RestAssured.*;

public class JSONDataParsing {
    @Test
    public void validateJsonBody(){
        Response response = when().get("http://localhost:3000/books");
        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertEquals(response.getHeader("Content-Type"),"application/json; charset=utf-8");
        Assert.assertEquals(response.jsonPath().get("[4].title").toString(),"The Great Gatsby");
        Assert.assertEquals(response.jsonPath().get("[0].author").toString(),"Harper Lee");
    }

    @Test
    public void getJsonBodyDataByID() {
        int bookID = 10;
        Response response = when().get("http://localhost:3000/books");
        JsonPath jsonPath = new JsonPath(response.asString());
        Map<String, Object> bookDetails = jsonPath.getMap("find { it.id == "+bookID+" }");
        if(bookDetails != null) {
            Set<String> bookKeys = bookDetails.keySet();
            for(String key : bookKeys) {
                System.out.println(key + " --> " + bookDetails.get(key));
            }
        }
        else {
            System.out.println("Book details with given ID is not found!");
        }
    }
    @Test
    public void validateJsonBodyUsingJsonPath(){
        String bookTitle = "The Great Gatsby";
        Response response = when().get("http://localhost:3000/books");

        JsonPath jsonPath = new JsonPath(response.asString());
        Map<String, Object> bookDetails = jsonPath.getMap("find {it.title =='"+bookTitle+"'}");

        Assert.assertEquals(bookDetails.get("title"),"The Great Gatsby");
        Assert.assertEquals(bookDetails.get("author"),"F. Scott Fitzgerald");
        Assert.assertEquals(bookDetails.get("genre"),"Tragedy");
        Assert.assertEquals(bookDetails.get("price").toString(),"10.99");
        Assert.assertEquals(bookDetails.get("published_date"),"1925-04-10");
    }
    @Test
    public void jsonPathExploring(){
        Response response = when().get("http://localhost:3000/books");
        //System.out.println(response.toString());
        JsonPath jsonPath = new JsonPath(response.asString());
        System.out.println(jsonPath.get().toString());
        Map<String, Object> bookDetails = jsonPath.getMap("find {it.id==1}");
        System.out.println(bookDetails);
    }
    @Test
    public void getRequest(){
        when().get("http://localhost:3000/books").then().log().all();
    }
}
