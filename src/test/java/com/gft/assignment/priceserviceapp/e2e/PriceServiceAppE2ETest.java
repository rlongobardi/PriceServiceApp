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
                + "\"price\": \"35.50\"," // Ensure this is a string with trailing zero
                + "\"currency\": \"EUR\""
                + "}";

        JSONObject expected = new JSONObject(expectedJson);

        given()
                .queryParam("applicationDate", "2024-09-10T16:00:00")
                .queryParam("productId", 35455)
                .queryParam("brandId", 1)
                .when()
                .get("/api/v1/prices")
                .then()
                .log().all()
                .statusCode(200)
                .body("id", equalTo(expected.getInt("id")))
                .body("productId", equalTo(expected.getInt("productId")))
                .body("price", equalTo("35.50"));
    }
}