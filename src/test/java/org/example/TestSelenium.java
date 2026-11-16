package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("ui")
public class TestSelenium {
    private WebDriver driver;
    private WebDriverWait wait;
    private String name;
    private boolean created;

    @BeforeEach
    void startBrowser() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new", "--window-size=1440,1000");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        name = "Selenium-" + UUID.randomUUID();
    }

    private WebElement visible(String css) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(css)));
    }

    private void login(String username, String password) {
        driver.get("http://localhost:8080/admin");
        visible("#username").sendKeys(username);
        visible("#password").sendKeys(password);
        visible("button[type='submit']").click();
    }

    private void createProduct() {
        login("admin", "secret123");
        visible("#n-name").sendKeys(name);
        visible("#n-price").sendKeys("25");
        visible("#add-btn").click();
        created = true;
        wait.until(browser -> "".equals(browser.findElement(By.id("n-name")).getDomProperty("value")));
        visible("a[href='/']").click();
        visible(".product-card[data-name='" + name + "']");
    }

    private void addToCart() {
        visible(".product-card[data-name='" + name + "'] button[data-action='add-to-cart']").click();
        wait.until(ExpectedConditions.textToBe(By.id("cart-count"), "1"));
        visible(".btn-cart").click();
        assertEquals(name, visible(".cart-item b").getText());
    }

    @Test
    void createdProductAppearsInCatalog() {
        createProduct();
        assertEquals(name, visible(".product-card[data-name='" + name + "'] h4").getText());
    }

    @Test
    void addedProductAppearsInCart() {
        createProduct();
        addToCart();
        assertEquals("25", visible("#total-price").getText());
    }

    @Test
    void invalidCredentialsAreRejected() {
        login("wrong-user", "wrong-password");
        assertTrue(visible(".alert-danger").isDisplayed());
        assertTrue(driver.findElements(By.id("add-btn")).isEmpty());
    }

    @Test
    void cartPreservesProductAfterRefresh() {
        createProduct();
        addToCart();
        driver.navigate().refresh();
        visible(".btn-cart").click();
        assertEquals(name, visible(".cart-item b").getText());
        assertEquals("1", visible("#cart-count").getText());
    }

    @AfterEach
    void closeBrowser() {
        try {
            if (created) {
                driver.get("http://localhost:8080/admin");
                By input = By.cssSelector("#tbody input[value='" + name + "']");
                WebElement row = wait.until(ExpectedConditions.visibilityOfElementLocated(input))
                        .findElement(By.xpath("ancestor::tr"));
                row.findElement(By.cssSelector("button[data-action='delete']")).click();
                wait.until(ExpectedConditions.alertIsPresent()).accept();
                wait.until(ExpectedConditions.invisibilityOfElementLocated(input));
            }
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
