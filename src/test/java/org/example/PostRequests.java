package org.example;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class PostRequests {
    String url = "http://localhost:3001/students";
    // Send body using HashMap
    @Test
    public void sendDataUsingHashMap(){
        deleteStudentUsingId("2");
        HashMap student2 = new HashMap();
        student2.put("id", 2);
        student2.put("name", "Jane Smith");
        student2.put("age", 22);
        student2.put("gender", "Female");
        student2.put("course", "Data Science");
        student2.put("country", "USA");

        given()
                .contentType("application/json")
                .body(student2)
                .when()
                .post(url)
                .then()
                .statusCode(201)
                .body("id", equalTo(2))
                .body("name", equalTo("Jane Smith"))
                .body("age", equalTo(22))
                .body("gender", equalTo("Female"))
                .body("course", equalTo("Data Science"))
                .body("country", equalTo("USA"))
                .extract().response();

    }

    // Send body using JSONObject
    @Test
    public void sendDataUsingJsonObject(){
        deleteStudentUsingId("3");
        JSONObject student3 = new JSONObject();
        student3.put("id", 3);
        student3.put("name", "Alice Johnson");
        student3.put("age", 25);
        student3.put("gender", "Female");
        student3.put("course", "Machine Learning");
        student3.put("country", "UK");

        given()
                .contentType("application/json")
                .body(student3.toString())
                .when()
                .post(url)
                .then()
                .statusCode(201)
                .body("id", equalTo(3))
                .body("name", equalTo("Alice Johnson"))
                .body("age", equalTo(25))
                .body("gender", equalTo("Female"))
                .body("course", equalTo("Machine Learning"))
                .body("country", equalTo("UK"));
    }

    // Send data from external resources
    @Test
    public void sendDataUsingExternalFile() throws FileNotFoundException {
        deleteStudentUsingId("4");
        JSONObject data = createStudentDataUsingFIleName("sampleJsons/student4.json");

        given()
                .contentType("application/json")
                .body(data.toString())
                .when()
                .post(this.url)
                .then()
                .statusCode(201)
                .body("id", equalTo(4))
                .body("name", equalTo("Bob Williams"))
                .body("age", equalTo(28))
                .body("gender", equalTo("Male"))
                .body("course", equalTo("Artificial Intelligence"))
                .body("country", equalTo("Canada"));
    }
    @Test
    public void createStudent5() throws FileNotFoundException {
        final JSONObject data = this.createStudentDataUsingFIleName("sampleJsons/student5.json");

        given()
                .contentType("application/json")
                .body(data.toString())
                .when()
                .post(this.url);

    }
    //@Test
    public void deleteUser(){
        this.deleteStudentUsingId("5");
    }

    public void deleteStudentUsingId(final String id){
        when().delete(this.url+id);
    }
    public JSONObject createStudentDataUsingFIleName(final String fileName) throws FileNotFoundException {
        final File file = new File("src/resources/"+fileName);
        final FileReader fileReader = new FileReader(file);
        final JSONTokener jsonTokener = new JSONTokener(fileReader);
        return new JSONObject(jsonTokener);
    }
}
