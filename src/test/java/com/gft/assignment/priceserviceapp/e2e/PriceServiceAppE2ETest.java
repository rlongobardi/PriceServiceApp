package com.gft.assignment.priceserviceapp.e2e;

import io.restassured.RestAssured;
import org.flywaydb.core.Flyway;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

@SpringBootTest
@Tag("e2e")
@ActiveProfiles("test") // Ensure the test profile is active
public class PriceServiceAppE2ETest {

    private static final String API_V_1_PRICES = "/api/v1/prices";
    @Autowired
    private Flyway flyway; // Inject Flyway

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080";
        flyway.migrate(); // This will run the migrations
    }

    @Test
    public void testGetPriceById() throws JSONException {
        String expectedJson = "{"
                + "\"id\": 1,"
                + "\"brandId\": 1,"
                + "\"startDate\": \"2024-09-10T00:00:00\","
                + "\"endDate\": \"2024-09-10T23:59:59\","
                + "\"priceList\": 1,"
                + "\"productId\": 35455,"
                + "\"priority\": 1,"
                + "\"price\": \"35.50\","
                + "\"currency\": \"EUR\""
                + "}";

        JSONObject expected = new JSONObject(expectedJson);

        given()
                .queryParam("applicationDate", "2024-09-10T16:00:00")
                .queryParam("productId", 35455)
                .queryParam("brandId", 1)
                .when()
                .get(API_V_1_PRICES)
                .then()
                .log().all()
                .statusCode(200)
                .body("id", equalTo(expected.getInt("id")))
                .body("productId", equalTo(expected.getInt("productId")))
                .body("price", equalTo(expected.getString("price")));
    }

    @Test
    public void testGetPrice_InvalidProductId() {
        given()
                .queryParam("applicationDate", "2024-09-10T16:00:00")
                .queryParam("productId", 99999) // Invalid product ID
                .queryParam("brandId", 1)
                .when()
                .get(API_V_1_PRICES)
                .then()
                .statusCode(404);
    }

    @Test
    public void testGetPrice_InvalidBrandId() {
        given()
                .queryParam("applicationDate", "2024-09-10T16:00:00")
                .queryParam("productId", 35455)
                .queryParam("brandId", 99999) // Invalid brand ID
                .when()
                .get(API_V_1_PRICES)
                .then()
                .statusCode(404);
    }

    @Test
    public void testGetPrice_MissingParameters() {
        given()
                .queryParam("applicationDate", "2024-09-10T16:00:00")
                .queryParam("brandId", 1) // Missing productId
                .when()
                .get(API_V_1_PRICES)
                .then()
                .statusCode(400); // Assuming the API returns a 400 Bad Request
    }

    @Test
    public void testGetPrice_InvalidDateFormat() {
        given()
                .queryParam("applicationDate", "invalid-date-format") // Invalid date
                .queryParam("productId", 35455)
                .queryParam("brandId", 1)
                .when()
                .get(API_V_1_PRICES)
                .then()
                .statusCode(400); // Assuming the API returns a 400 Bad Request
    }
}