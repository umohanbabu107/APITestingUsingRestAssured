package org.example;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.Test;
import java.util.Set;
import static io.restassured.RestAssured.*;

public class JsonDataParsingUsingJSONObjectTests {
    String url =  "http://localhost:3002/books?id=1";
    @Test
    public void getBookAuthor(){
        Response response = when()
                .get(url)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JSONObject jsonObject = new JSONObject(response.asString());
        Set<String> bookDetailsKeys = jsonObject.keySet();
        bookDetailsKeys.forEach((key) -> System.out.println(key+" --> "+jsonObject.get(key)));
    }
}
