package org.example;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.*;
public class BookStoreAPITests {
    String url = " http://localhost:3000/books";

    @Test
    public void getAllBooks(){
        when()
                .get(url)
                .then()
                .statusCode(200)
                .log().all();
    }

    @Test
    public void testBookTitles(){
        Response booksAPIResponse = when()
                .get(url)
                .then()
                .statusCode(200)
                .extract()
                .response();
        JsonPath jsonPath = new JsonPath(booksAPIResponse.asString());
        final List<Map<String, Object>> booksData = jsonPath.getList("");
        for(int i=0; i<booksData.size();i++){
            System.out.println(booksData.get(i).get("title"));
        }
        final List<String> booksTitles = booksData.stream()
                .map(book -> book.get("title").toString())
                .collect(Collectors.toList());
        Assert.assertTrue(booksTitles.contains("To Kill a Mockingbird"));
        Assert.assertTrue(booksTitles.contains("1984"));
        Assert.assertTrue(booksTitles.contains("Moby Dick"));
        Assert.assertTrue(booksTitles.contains("Pride and Prejudice"));
        Assert.assertTrue(booksTitles.contains("The Great Gatsby"));
    }

    @Test
    public void testPriceOfBook(){
        final Response response = when()
                .get(this.url)
                .then()
                .statusCode(200)
                .extract().response();
        final JsonPath jsonPath = new JsonPath(response.asString());
        final List<Map<String, Object>> booksData = jsonPath.getList("");
        final Map<String, String > booksPrices = booksData.stream().collect(Collectors.toMap(
                book -> book.get("title").toString(),
                book -> book.get("price").toString()));
        Assert.assertEquals(booksPrices.get("1984"),"8.99");
    }
    @Test
    public void findTotalPriceOfAllBooks(){
        Response response = when()
                .get(url)
                .then()
                .statusCode(200)
                .extract().response();

        JsonPath jsonPath = new JsonPath(response.asString());
        List<Map<String, Object>> booksData = jsonPath.getList("");
        Double price = booksData.stream().map(book -> Double.valueOf(book.get("price").toString())).reduce(0.0,Double::sum);
        System.out.println(price);
    }
    @Test
    public void getBookDetails(){
        Response response = when()
                .get(url+"?id=1")
                .then()
                .statusCode(200)
                .extract()
                .response();
        JsonPath jsonPath = new JsonPath(response.asString());
        Map<String,Object> bookDetails = jsonPath.getMap("[0]");
        System.out.println(bookDetails.get("title"));
    }
}
