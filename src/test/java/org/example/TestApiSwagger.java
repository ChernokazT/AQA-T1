package org.example;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;

@Tag("api")
public class TestApiSwagger extends ApiTestSupport {
    @Test
    void requestWithoutAuth_returns401() {
        given()
                .spec(requestSpec)
                .auth()
                .none()
                .body(productBody(uniqueName(), 10.00))
                .when()
                .post("/goods/add")
                .then()
                .statusCode(401);
    }

    @Test
    void addGoods_validBody_returns200() {
        createGoods(uniqueName(), 99.99);
    }

    @Test
    void addGoods_emptyName_returns400() {
        given()
                .spec(authorizedSpec)
                .body(productBody("", 10.00))
                .when()
                .post("/goods/add")
                .then()
                .statusCode(400);
    }

    @Test
    void getGoodsById_existingId_returns200() {
        String name = uniqueName();
        long id = createGoods(name, 50.00);
        given()
                .spec(authorizedSpec)
                .pathParam("id", id)
                .when()
                .get("/goods/{id}")
                .then()
                .statusCode(200)
                .body("name", equalTo(name));
    }

    @Test
    void getGoodsById_nonexistentId_returns404() {
        long id = createGoods(uniqueName(), 10.00);
        deleteGoods(id);
        given()
                .spec(authorizedSpec)
                .pathParam("id", id)
                .when()
                .get("/goods/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    void patchGoods_existingId_validBody_returns200() {
        long id = createGoods(uniqueName(), 10.00);
        String name = uniqueName();
        given()
                .spec(authorizedSpec)
                .pathParam("id", id)
                .body(productBody(name, 149.99))
                .when()
                .patch("/goods/{id}")
                .then()
                .statusCode(200);
        given()
                .spec(authorizedSpec)
                .pathParam("id", id)
                .when()
                .get("/goods/{id}")
                .then()
                .statusCode(200)
                .body("name", equalTo(name));
    }

    @Test
    void patchGoods_invalidBody_returns400() {
        long id = createGoods(uniqueName(), 10.00);
        given()
                .spec(authorizedSpec)
                .pathParam("id", id)
                .body(productBody("", -5.00))
                .when()
                .patch("/goods/{id}")
                .then()
                .statusCode(400);
    }

    @Test
    void patchGoods_nonexistentId_returns404() {
        long id = createGoods(uniqueName(), 10.00);
        deleteGoods(id);
        given()
                .spec(authorizedSpec)
                .pathParam("id", id)
                .body(productBody(uniqueName(), 1.00))
                .when()
                .patch("/goods/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    void getList_defaultPagination_returns200() {
        String name = uniqueName();
        createGoods(name, 10.00);
        given()
                .spec(requestSpec)
                .auth()
                .none()
                .when()
                .get("/goods/list")
                .then()
                .statusCode(200)
                .body("goods", notNullValue())
                .body("goods.name", hasItem(name));
    }

    @Test
    void getList_customPagination_returns200() {
        given()
                .spec(requestSpec)
                .queryParam("page", 0)
                .queryParam("size", 5)
                .when()
                .get("/goods/list")
                .then()
                .statusCode(200)
                .body("goods.size()", lessThanOrEqualTo(5));
    }

    @Test
    void deleteGoods_existingId_returns200() {
        long id = createGoods(uniqueName(), 20.00);
        deleteGoods(id);
        given()
                .spec(requestSpec)
                .when()
                .get("/goods/list")
                .then()
                .statusCode(200)
                .body("goods", org.hamcrest.Matchers.empty());
    }

    @Test
    void deleteGoods_nonexistentId_returns404() {
        long id = createGoods(uniqueName(), 10.00);
        deleteGoods(id);
        given()
                .spec(authorizedSpec)
                .pathParam("id", id)
                .when()
                .delete("/goods/{id}")
                .then()
                .statusCode(404);
    }
}
