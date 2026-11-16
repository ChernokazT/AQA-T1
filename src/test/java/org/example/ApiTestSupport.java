package org.example;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterEach;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.preemptive;
import static org.hamcrest.Matchers.notNullValue;

public class ApiTestSupport {
    protected final String baseUri = "http://localhost:8081";
    protected final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri(baseUri)
            .setContentType(ContentType.JSON)
            .build();
    protected final RequestSpecification authorizedSpec = new RequestSpecBuilder()
            .addRequestSpecification(requestSpec)
            .setAuth(preemptive().basic("admin", "secret123"))
            .build();
    private final List<Long> createdIds = new ArrayList<>();

    protected String uniqueName() {
        return "Product-" + UUID.randomUUID();
    }

    protected String productBody(String name, double price) {
        return String.format(Locale.US, "{\"name\":\"%s\",\"price\":%.2f}", name, price);
    }

    protected long createGoods(String name, double price) {
        long id = given()
                .spec(authorizedSpec)
                .body(productBody(name, price))
                .when()
                .post("/goods/add")
                .then()
                .statusCode(200)
                .body("data.id", notNullValue())
                .extract()
                .jsonPath()
                .getLong("data.id");
        createdIds.add(id);
        return id;
    }

    protected void deleteGoods(long id) {
        given()
                .spec(authorizedSpec)
                .pathParam("id", id)
                .when()
                .delete("/goods/{id}")
                .then()
                .statusCode(200);
        createdIds.remove(id);
    }

    @AfterEach
    void cleanUp() {
        for (long id : new ArrayList<>(createdIds)) {
            deleteGoods(id);
        }
    }
}
