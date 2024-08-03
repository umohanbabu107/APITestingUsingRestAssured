package org.example;

import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonDataParsingUsingJsonPath {
    @Test
    public void test1() throws IOException {
        final String jsonString = new String(Files.readAllBytes(Paths.get("src/resources/sampleJsons/complexJsonData.json")));
        //System.out.println(jsonString);
        final JsonPath jsonPath = new JsonPath(jsonString);

        // Getting list object from the json
        //List<Object> arrayList = jsonPath.getList("array");
       // for(Object object : arrayList){
           // System.out.println(object);
        //}

        // Getting Object details from json Object
        //Object object= jsonPath.getJsonObject("object");
        //JsonPath jsonPath1 = new JsonPath(object.toString());
//        final Map<String, Object> jsonSubObjectDetails = jsonPath.getMap("object");
//        final Set<String> objectKeys = jsonSubObjectDetails.keySet();
//        for(final String subObjectKey: objectKeys){
//            System.out.println(jsonSubObjectDetails.get(subObjectKey));
//        }


        //Map<String , Object> details = jsonPath.getMap("");
        //System.out.println(details.get("string"));
        //Set<String> allKeys = details.keySet();
        //for(String key : allKeys){
        //    System.out.println(key+" --> "+details.get(key));
        //}

        final List<Map<String, Object>> jsonData = jsonPath.getList("");
        System.out.println(jsonData.size());
        System.out.println(jsonData.get(0).get("string").toString());
    }

    @Test
    public void test2(){
        String jsonDataStringFormat = null;
        try {
            jsonDataStringFormat = new String(Files.readAllBytes(Paths.get("src/resources/sampleJsons/jsonSet.json")));
        }
        catch(final Exception exception){
            System.out.println("This json not found!");
        }
        final JsonPath jsonPath = new JsonPath(jsonDataStringFormat);
        final List<HashMap<String, Object>> setData = jsonPath.getList("data_sets");
        System.out.println(setData.get(2).get(KEYS.STRING.getKey()).toString());
        System.out.println(setData.get(0).get(KEYS.OBJECT.getKey()));
        final HashMap<String, Object> objectData = (HashMap<String, Object>) setData.get(0).get("object");
        System.out.println(objectData.get("nested_string"));
    }
    public String getObjectData(final Object object, final String subString){
        final JsonPath jsonPath = new JsonPath(object.toString());
        return jsonPath.getString(subString);
    }
    public enum KEYS{
        STRING("string"),
        NUMBER("number"),
        FLOAT("float"),
        BOOLEAN("boolean"),
        NULL_VALUE("null_value"),
        ARRAY("array"),
        OBJECT("object"),
        NESTED_STRING("nested_string"),
        NESTED_NUMBER("nested_number"),
        NESTED_BOOLEAN("nested_boolean"),
        SPECIAL_CHARACTERS("special_characters"),
        UNICODE_CHARACTERS("unicode_characters"),
        EMPTY_STRING("empty_string"),
        EMPTY_ARRAY("empty_array"),
        EMPTY_OBJECT("empty_object"),
        COMPLEX_OBJECT("complex_object"),
        LIST_OF_OBJECTS("list_of_objects"),
        LIST_OF_ARRAYS("list_of_arrays"),
        BOOLEAN_STRING("boolean_string"),
        NUMBER_STRING("number_string"),
        DATETIME("datetime");

        private final String key;
        KEYS(final String key){
            this.key = key;
        }
        public String getKey(){
            return this.key;
        }
    }
}
