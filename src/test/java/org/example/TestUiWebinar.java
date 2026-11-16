package org.example;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;

@Tag("ui")
public class TestUiWebinar {
    private final List<Long> ids = new ArrayList<>();
    private String name;

    @BeforeEach
    void setUp() {
        Configuration.baseUrl = "http://localhost:8080";
        Configuration.headless = true;
        Configuration.timeout = 5000;
        Configuration.reportsFolder = "build/reports/tests/selenide";
        name = "Webinar-" + UUID.randomUUID();
    }

    private long createViaApi(String productName, double price) {
        long id = given()
                .baseUri("http://localhost:8080")
                .auth().preemptive().basic("admin", "secret123")
                .contentType("application/json")
                .body(String.format(Locale.US, "{\"name\":\"%s\",\"price\":%.2f}", productName, price))
                .when()
                .post("/goods/add")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getLong("data.id");
        ids.add(id);
        return id;
    }

    private void login() {
        open("/admin");
        $("#username").shouldBe(visible).setValue("admin");
        $("#password").setValue("secret123");
        $("button[type='submit']").click();
        $("#add-btn").shouldBe(visible);
    }

    @Test
    void threeUnitsCanBeOrdered() {
        createViaApi(name, 50);
        open("/");
        SelenideElement card = $(".product-card[data-name='" + name + "']").shouldBe(visible);
        card.$("input.qty-input").setValue("3");
        card.$("button[data-action='add-to-cart']").click();
        $("#cart-count").shouldBe(exactText("3"));
        $(".btn-cart").click();
        $("#total-price").shouldBe(exactText("150"));
        $("#makeOrder").click();
        $$(".toast").findBy(text("\u0417\u0430\u043a\u0430\u0437 \u043f\u0440\u0438\u043d\u044f\u0442 \u0432 \u043e\u0431\u0440\u0430\u0431\u043e\u0442\u043a\u0443!")).shouldBe(visible);
        $("#main-title").shouldBe(text("\u0417\u0430\u043a\u0430\u0437 \u0443\u0441\u043f\u0435\u0448\u043d\u043e \u043e\u0444\u043e\u0440\u043c\u043b\u0435\u043d!"));
        $("#cart-count").shouldBe(exactText("0"));
    }

    @Test
    void differentProductsHaveCorrectTotal() {
        createViaApi(name + "-A", 25.50);
        createViaApi(name + "-B", 40.25);
        open("/");
        $(".product-card[data-name='" + name + "-A'] button[data-action='add-to-cart']").click();
        $(".product-card[data-name='" + name + "-B'] button[data-action='add-to-cart']").click();
        $("#cart-count").shouldBe(exactText("2"));
        $(".btn-cart").click();
        $$(".cart-item b").findBy(exactText(name + "-A")).shouldBe(visible);
        $$(".cart-item b").findBy(exactText(name + "-B")).shouldBe(visible);
        $("#total-price").shouldBe(exactText("65.75"));
    }

    @Test
    void addingProductShowsNotification() {
        login();
        $("#n-name").setValue(name);
        $("#n-price").setValue("25");
        $("#add-btn").click();
        try {
            $$(".toast").findBy(exactText("\u0422\u043e\u0432\u0430\u0440 \u0443\u0441\u043f\u0435\u0448\u043d\u043e \u0434\u043e\u0431\u0430\u0432\u043b\u0435\u043d!")).shouldBe(visible);
        } finally {
            ids.add(given()
                    .baseUri("http://localhost:8080")
                    .queryParam("size", 100)
                    .when()
                    .get("/goods/list")
                    .then()
                    .statusCode(200)
                    .extract()
                    .jsonPath()
                    .param("productName", name)
                    .getLong("goods.find { it.name == productName }.id"));
        }
    }

    @Test
    void editedProductAppearsInCatalog() {
        long id = createViaApi(name, 25);
        login();
        $("#nm-" + id).shouldBe(visible).setValue(name + "-edited");
        $("#pr-" + id).setValue("45");
        $("button[data-action='update'][data-id='" + id + "']").click();
        $$(".toast").findBy(text("#" + id)).shouldBe(visible);
        $("a[href='/']").click();
        SelenideElement card = $(".product-card[data-name='" + name + "-edited']").shouldBe(visible);
        card.$("h4").shouldBe(exactText(name + "-edited"));
        card.$("div:first-of-type").shouldBe(exactText("45 \u20bd"));
    }

    @AfterEach
    void cleanUp() {
        try {
            for (long id : ids) {
                given()
                        .baseUri("http://localhost:8080")
                        .auth().preemptive().basic("admin", "secret123")
                        .when()
                        .delete("/goods/" + id)
                        .then()
                        .statusCode(200);
            }
        } finally {
            closeWebDriver();
        }
    }
}
