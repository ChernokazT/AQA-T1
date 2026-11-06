package org.example;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

public class TestRestAssured {


    private RequestSpecification basicRQ = new RequestSpecBuilder()
            .setBaseUri("http://localhost:8080")
            .log(LogDetail.ALL)
            .setContentType("application/json")
            .build();

    @Test
    void testList_GivenWhenThen() {
        given()
                .baseUri("http://localhost:8080")
                .contentType("application/json")
                .when()
                .get("/goods/list")
                .then()
                .log().all()
                .statusCode(200)
                .body("", equalTo(""));
    }

    @Test
    void testList_RequestSpec() {
        given()
                .spec(basicRQ)
                .when()
                .get("/goods/list")
                .then()
                .log().all()
                .statusCode(200)
                .body("", equalTo(""));
    }

    @Test
    void RestAssuredAssertions() {
        String newProduct = """
                {
                    "name": "Laptop",
                    "price": 999
                }
                """;

        given()
                .spec(basicRQ)
                .body(newProduct)
                .when()
                .post("/goods/add")
                .then()
                .log().all()
                .statusCode(201);

        given()
                .spec(basicRQ)
                .when()
                .get("/goods/list")
                .then()
                .log().all()
                .statusCode(200)
                .body("name", hasItem("Laptop"));
    }

    @Test
    void AssertJ() {
        String newProduct = """
                {
                    "name": "Headphones",
                    "price": 150
                }
                """;

        given()
                .spec(basicRQ)
                .body(newProduct)
                .when()
                .post("/goods/add")
                .then()
                .log().all()
                .statusCode(201);

        JsonPath jsonPath = given()
                .spec(basicRQ)
                .when()
                .get("/goods/list")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .jsonPath();

        List<String> productNames = jsonPath.getList("name");
        assertThat(productNames).contains("Headphones");
    }
}
