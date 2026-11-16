package org.example;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItem;

@Tag("api")
public class TestRestAssured extends ApiTestSupport {
    @Test
    void testList_GivenWhenThen() {
        given()
                .baseUri(baseUri)
                .when()
                .get("/goods/list")
                .then()
                .statusCode(200)
                .body("goods", empty());
    }

    @Test
    void testList_RequestSpec() {
        given()
                .spec(requestSpec)
                .when()
                .get("/goods/list")
                .then()
                .statusCode(200)
                .body("goods", empty());
    }

    @Test
    void restAssuredAssertions() {
        String name = uniqueName();
        createGoods(name, 999.00);
        given()
                .spec(requestSpec)
                .when()
                .get("/goods/list")
                .then()
                .statusCode(200)
                .body("goods.name", hasItem(name));
    }

    @Test
    void assertJAssertions() {
        String name = uniqueName();
        createGoods(name, 150.00);
        List<String> names = given()
                .spec(requestSpec)
                .when()
                .get("/goods/list")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("goods.name", String.class);
        assertThat(names).contains(name);
    }
}
