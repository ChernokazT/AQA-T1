package org.example;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.basic;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@Tag("api")

public class TestApiSwagger {

    private static RequestSpecification basicRQ;

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "http://localhost:8080";
        RestAssured.authentication = basic("admin", "secret123");
        basicRQ = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }

    private static long createGoods(String name, double price) {
        return given()
                .spec(basicRQ)
                .body(String.format("{\"name\": \"%s\", \"price\": %.2f}", name, price))
                .when()
                .post("/goods/add")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getLong("id");
    }

    @Test
    void requestWithoutAuth_returns401() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/goods/list")
                .then()
                .statusCode(401);
    }

    @Test
    void addGoods_validBody_returns200() {
        given()
                .spec(basicRQ)
                .body(String.format("{\"name\": \"Товар %d\", \"price\": 99.99}", System.currentTimeMillis()))
                .when()
                .post("/goods/add")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }

    @Test
    void addGoods_emptyName_returns400() {
        given()
                .spec(basicRQ)
                .body("{\"name\": \"\", \"price\": 10.0}")
                .when()
                .post("/goods/add")
                .then()
                .statusCode(400);
    }

    @Test
    void getGoodsById_existingId_returns200() {
        long id = createGoods("Для чтения " + System.currentTimeMillis(), 50.0);

        given()
                .spec(basicRQ)
                .pathParam("id", id)
                .when()
                .get("/goods/{id}")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }

    @Test
    void getGoodsById_nonexistentId_returns404() {
        given()
                .spec(basicRQ)
                .pathParam("id", 999_999_999L)
                .when()
                .get("/goods/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    void patchGoods_existingId_validBody_returns200() {
        long id = createGoods("До патча " + System.currentTimeMillis(), 10.0);

        given()
                .spec(basicRQ)
                .pathParam("id", id)
                .body(String.format("{\"name\": \"После патча %d\", \"price\": 149.99}", System.currentTimeMillis()))
                .when()
                .patch("/goods/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    void patchGoods_invalidBody_returns400() {
        long id = createGoods("Для патча с ошибкой " + System.currentTimeMillis(), 10.0);

        given()
                .spec(basicRQ)
                .pathParam("id", id)
                .body("{\"name\": \"\", \"price\": -5.0}")
                .when()
                .patch("/goods/{id}")
                .then()
                .statusCode(400);
    }

    @Test
    void patchGoods_nonexistentId_returns404() {
        given()
                .spec(basicRQ)
                .pathParam("id", 999_999_999L)
                .body("{\"name\": \"Нет такого\", \"price\": 1.0}")
                .when()
                .patch("/goods/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    void getList_defaultPagination_returns200() {
        given()
                .spec(basicRQ)
                .when()
                .get("/goods/list")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }

    @Test
    void getList_customPagination_returns200() {
        given()
                .spec(basicRQ)
                .queryParam("page", 0)
                .queryParam("size", 5)
                .when()
                .get("/goods/list")
                .then()
                .statusCode(200);
    }

    @Test
    void deleteGoods_existingId_returns200() {
        long id = createGoods("На удаление " + System.currentTimeMillis(), 20.0);

        given()
                .spec(basicRQ)
                .pathParam("id", id)
                .when()
                .delete("/goods/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    void deleteGoods_nonexistentId_returns404() {
        given()
                .spec(basicRQ)
                .pathParam("id", 999_999_999L)
                .when()
                .delete("/goods/{id}")
                .then()
                .statusCode(404);
    }
}
