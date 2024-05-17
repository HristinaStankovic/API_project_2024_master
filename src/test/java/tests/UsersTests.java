package tests;

import endpoints.UserEndpoints;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static java.lang.Math.log;

public class UsersTests {




    @Test
    public void getUsersTest() {
        Map<String, Integer> map = new HashMap<>();
        map.put("page", 1);
        map.put("limit", 6);

        Response response = given()
                .baseUri("https://dummyapi.io/data")
                .basePath("/v1/")
                .header("app-id", "6647410f159a98c25209a76b")
                .queryParams(map)
                .log().all()
                .when().get(Constants.GET_ALL_USERS);



        Assert.assertEquals(response.getStatusCode(), 200, "Expected 200 but got: " + response.getStatusCode());
        String actualFirstName = response.jsonPath().get("data[0].firstName");
        System.out.println(actualFirstName);

        Assert.assertEquals(actualFirstName, "Emre");

    }

    @Test
    public void getUsersUsingJsonPathTest() {
        Map<String, Integer> map = new HashMap<>();
        map.put("page", 1);
        map.put("limit", 5);

        JsonPath jsonPath = given()
                .baseUri("https://dummyapi.io/data")
                .basePath("/v1/")
                .header("app-id", "6647410f159a98c25209a76b")
                .queryParams(map)
                .log().all()
                .when().get(Constants.GET_ALL_USERS).getBody().jsonPath();

        String actualFirstName = jsonPath.getString("data[0].firstName");
        boolean result = actualFirstName.equals("Carolina");

        Assert.assertTrue(result, "Expected first name is not correct.");
    }

        @Test
        public void getUserByIdTest() {

            RestAssured.baseURI = "https://dummyapi.io/data";
            RestAssured.basePath = "/v1";

            String userId = "60d0fe4f5311236168a109ca";
            Response response = given()
                    .header("app-id", "664537d0b23dada292df3e28")
                    .log().all()
                    .when()
                    .get("/user/" + userId);
            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(), 200, "Expected 200 but got: " + response.getStatusCode());
            String actualFirstName = response.jsonPath().getString("firstName");
            System.out.println("First Name: " + actualFirstName);

            Assert.assertEquals(actualFirstName, "Sara", "First name doesn't match");
        }
    }
