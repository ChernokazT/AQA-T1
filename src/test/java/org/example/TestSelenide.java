package org.example;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.UUID;


import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exactValue;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.confirm;
import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.refresh;


@Tag("ui")
public class TestSelenide {
    private String productName;
    private boolean productCreated;

    @BeforeEach
    void setUp() {
        Configuration.baseUrl = "http://localhost:8080";
        Configuration.browser = "chrome";
        Configuration.headless = true;
        Configuration.timeout = 5000;
        Configuration.reportsFolder = "build/reports/tests/selenide";
        productName = "UI-" + UUID.randomUUID();
    }

    private void createProduct(String price) {
        open("/admin");
        $("#username").shouldBe(visible).setValue("admin");
        $("#password").setValue("secret123");
        $("button[type='submit']").click();
        $("#n-name").shouldBe(visible).setValue(productName);
        $("#n-price").setValue(price);
        $("#add-btn").click();
        productCreated = true;
        $("#n-name").shouldBe(exactValue(""));
        $$("#tbody input[type='text']").findBy(value(productName)).shouldBe(visible);
        $("a[href='/']").click();
        $(".product-card[data-name='" + productName + "']").shouldBe(visible);
    }

    private void addProductToCart() {
        $(".product-card[data-name='" + productName + "'] button[data-action='add-to-cart']").click();
        $("#cart-count").shouldBe(exactText("1"));
        $(".btn-cart").click();
        $(".cart-item").shouldBe(visible);
        $$(".cart-item").get(1).shouldBe(not(exist));
        $(".cart-item b").shouldBe(exactText(productName));
    }

    @Test
    void productCreatedInAdminIsDisplayedInCatalog() {
        createProduct("25");
        SelenideElement card = $(".product-card[data-name='" + productName + "']");
        card.$("h4").shouldBe(exactText(productName));
        card.$("div:first-of-type").shouldBe(exactText("25 \u20bd"));
    }

    @Test
    void addedProductIsDisplayedInCart() {
        createProduct("25");
        addProductToCart();
        $("#total-price").shouldBe(exactText("25"));
    }

    @Test
    void invalidCredentialsDoNotAllowAdminAccess() {
        open("/admin");
        $("#username").shouldBe(visible).setValue("wrong-user");
        $("#password").setValue("wrong-password");
        $("button[type='submit']").click();
        $(".alert-danger").shouldBe(visible).shouldBe(text("\u041d\u0435\u0432\u0435\u0440\u043d\u044b\u0435 \u0443\u0447\u0435\u0442\u043d\u044b\u0435 \u0434\u0430\u043d\u043d\u044b\u0435 \u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u044f"));
        $("#username").shouldBe(visible);
        $("#add-btn").shouldBe(not(visible));
    }

    @Test
    void cartPreservesProductAfterPageRefresh() {
        createProduct("25");
        addProductToCart();
        refresh();
        $(".btn-cart").shouldBe(visible).click();
        $(".cart-item").shouldBe(visible);
        $$(".cart-item").get(1).shouldBe(not(exist));
        $(".cart-item b").shouldBe(exactText(productName));
        $("#cart-count").shouldBe(exactText("1"));
        $("#total-price").shouldBe(exactText("25"));
    }

    @Test
    void orderOver300DisplaysAlert() {
        createProduct("301");
        addProductToCart();
        $("#total-price").shouldBe(exactText("301"));
        $("#makeOrder").click();
        confirm("[SmartShop]: \u0414\u0435\u043d\u0435\u0433 \u043d\u0435 \u0445\u0432\u0430\u0442\u0430\u0435\u0442! \u0421\u0443\u043c\u043c\u0430 301 \u20bd \u043f\u0440\u0435\u0432\u044b\u0448\u0430\u0435\u0442 \u043b\u0438\u043c\u0438\u0442 300 \u20bd.");
        $(".cart-item b").shouldBe(exactText(productName));
    }

    @AfterEach
    void cleanUp() {
        try {
            if (productCreated) {
                closeWebDriver();
                open("/admin");
                $("#username").shouldBe(visible).setValue("admin");
        $("#password").setValue("secret123");
        $("button[type='submit']").click();
                SelenideElement nameInput = $$("#tbody input[type='text']").findBy(value(productName));
                nameInput.shouldBe(visible).closest("tr").$("button[data-action='delete']").click();
                confirm();
                $$("#tbody input[type='text']").filterBy(value(productName)).first().shouldBe(not(exist));
            }
        } finally {
            closeWebDriver();
        }
    }
}
