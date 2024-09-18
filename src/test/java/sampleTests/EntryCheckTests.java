package sampleTests;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class EntryCheckTests {
    private String token;
    @BeforeMethod
    public void generateToken(){
        String username = System.getenv("BASIC_AUTH_USERNAME");
        String password = System.getenv("BASIC_AUTH_PASSWORD");
        Response response = given().auth()
                        .preemptive()
                .basic(username,password)
                .when().post("https://api.test.transporeon.com/oauth2/token?grant_type=client_credentials")
                .then()
                .log().all()
                .extract()
                .response();

        JsonPath data = new JsonPath(response.asString());
        this.token = data.getString("access_token");
    }

    @Test
    public void bookingAllowCheck(){
        Response response = given().contentType("application/json")
                .body(this.createPayload("TEST_ENTRY_CHECK_BARRIER_123","MOHANLPNO123","2024-09-04T09:30:00.000+00:00"))
                .header("Authorization","Bearer "+this.token)
                .when()
                .post("https://yard-api.dev.eu.trimble-transportation.com/yard-management/v1/yards/entry-check")
                .then()
                .statusCode(200)
                .log().all()
                .extract().response();

        final JsonPath jsonPath = new JsonPath(response.asString());
        Assert.assertEquals(jsonPath.get("status"),"ALLOWED");


    }
    @Test
    public void verifyNoBookingFoundWithWrongBarrierID(){
        Response response = given().contentType("application/json")
                .body(this.createPayload("TEST_ENTRY_CHECK_BARRIER_12345","MOHANLPNO123","2024-09-04T09:30:00.000+00:00"))
                .header("Authorization","Bearer "+this.token)
                .when()
                .post("https://yard-api.dev.eu.trimble-transportation.com/yard-management/v1/yards/entry-check")
                .then()
                .statusCode(200)
                .log().all()
                .extract().response();


        final JsonPath jsonPath = new JsonPath(response.asString());
        Assert.assertEquals(jsonPath.get("status"),"DENIED");
    }
    @Test
    public void verifyNoBookingFoundWithWrongLicensePlate() {
        Response response = given().contentType("application/json")
                .body(this.createPayload("TEST_ENTRY_CHECK_BARRIER_123", "MOHANLPNO12384545", "2024-09-04T09:30:00.000+00:00"))
                .header("Authorization", "Bearer " + this.token)
                .when()
                .post("https://yard-api.dev.eu.trimble-transportation.com/yard-management/v1/yards/entry-check")
                .then()
                .statusCode(200)
                .log().all()
                .extract().response();


        final JsonPath jsonPath = new JsonPath(response.asString());
        Assert.assertEquals(jsonPath.get("status"),"DENIED");
    }
    public String createPayload(final String barrierId, final String licensePlateNumber, final String date){
        final JSONObject data = new JSONObject();
        data.put("barrierId",barrierId);
        data.put("licensePlateNumber",licensePlateNumber);
        data.put("arrivalDateTime",date);
        return data.toString();
    }
}
