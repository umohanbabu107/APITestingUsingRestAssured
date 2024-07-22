package org.example;

import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;

public class JsonDataParsingUsingJsonPath {
    @Test
    public void test1() throws IOException {
        String jsonString = new String(Files.readAllBytes(Paths.get("src/resources/complexJsonData.json")));
        //System.out.println(jsonString);
        JsonPath jsonPath = new JsonPath(jsonString);

        // Getting list object from the json
        //List<Object> arrayList = jsonPath.getList("array");
       // for(Object object : arrayList){
           // System.out.println(object);
        //}

        // Getting Object details from json Object
        //Object object= jsonPath.getJsonObject("object");
        //JsonPath jsonPath1 = new JsonPath(object.toString());
        Map<String, Object> jsonSubObjectDetails = jsonPath.getMap("object");
        Set<String> objectKeys = jsonSubObjectDetails.keySet();
        for(String subObjectKey: objectKeys){
            System.out.println(jsonSubObjectDetails.get(subObjectKey));
        }


        //Map<String , Object> details = jsonPath.getMap("");
        //System.out.println(details.get("string"));
        //Set<String> allKeys = details.keySet();
        //for(String key : allKeys){
        //    System.out.println(key+" --> "+details.get(key));
        //}
    }
}
