package org.example;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;


public class ExampleMain {
    ResponseSpecification responseSpecification = null;
    RequestSpecification requestSpecification = null;
    private final String apiKey = "93fe923ceafe4111980a825f50442934";
    private final String hashKey = "6f87c8cd02b36a14b8204e2aea68a951ec31689f";
    private final String url = "https://api.spoonacular.com/";


    @BeforeEach
    void beforeTest() {
        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectStatusLine("HTTP/1.1 200 OK")
                .expectContentType(ContentType.JSON)
                .expectResponseTime(Matchers.lessThan(5000L))
                //.expectHeader("Access-Control-Allow-Credentials", "true")
                .build();

        //RestAssured.responseSpecification = responseSpecification;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        requestSpecification = new RequestSpecBuilder()
                .addQueryParam("apiKey", apiKey)
                .addQueryParam("hash", hashKey)
                .addQueryParam("includeNutrition", "false")
                .log(LogDetail.ALL)
                .build();


    }

    @Test
    void getRecipePositiveTest() {
        given().spec(requestSpecification)
                .when()
                .get(url + "mealplanner/lally/shopping-list").prettyPeek()
                .then()
                .spec(responseSpecification);
//        assertThat(response.get("cost"), equalTo(0.0));
        ////        assertThat(response.getCuisine(), containsString("aisles"));
    }


    @Test
    void postResponseCuisineAsianTest() {

        ResponseCuisine response = (ResponseCuisine) given().spec(requestSpecification)
                .when()
                .formParam("title", "Thai Soup")
                .post(url + "recipes/cuisine").prettyPeek()
                .then()
                .extract()
                .response()
                .body()
                .as(ResponseCuisine.class);

        assertThat(response.getCuisine(), containsString("Asian"));
    }





//    @Test
//    void addMealPositiveTest() {
//        String id = given(requestSpecification)
//                .body("{\n"
//                        + " \"date\": 1644881179,\n"
//                        + " \"slot\": 1,\n"
//                        + " \"position\": 0,\n"
//                        + " \"type\": \"INGREDIENTS\",\n"
//                        + " \"value\": {\n"
//                        + " \"ingredients\": [\n"
//                        + " {\n"
//                        + " \"name\": \"1 avocado\"\n"
//                        + " }\n"
//                        + " ]\n"
//                        + " }\n"
//                        + "}")
//                .log()
//                .all()
//                .when()
//                .post("https://api.spoonacular.com/”  "
//                        + "+ mealplanner/lally/items")
//                .prettyPeek()
//                .then()
//                .spec(responseSpecification)
//                .extract()
//                .body()
//                .as(AddMealResponse.class)
//                .getId();
//
//

//    @Test
//    void deleteResponseTest() {
//                 given().spec(requestSpecification)
//                .delete(url + "mealplanner/lally/shopping-list/items/" + id)
//                .then()
//                .statusCode(200);
//    }
    }

