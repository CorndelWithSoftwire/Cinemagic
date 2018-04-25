package org.softwire.training.cinemagic.integration;

import com.google.common.base.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Support functions which don't below to any particular page.
 */
public class IntegrationTestSupport {
    private final WebDriver driver;
    private final Integer port;

    IntegrationTestSupport(WebDriver driver,
                           Integer port) {
        this.driver = driver;
        this.port = port;
    }

    public void navigateToUrl(String path) {
        driver.get("http://localhost:" + port + path);
    }

    public WebDriverWait shortWait() {
        return new WebDriverWait(driver, 10, 100);
    }

    @SuppressWarnings("ConstantConditions")
    public WebElement waitForElement(By locator) {
        return shortWait().until((Function<? super WebDriver, WebElement>) webDriver -> webDriver.findElement(locator));
    }

    public void fillField(By by, String keys) {
        fillField(driver.findElement(by), keys);
    }

    public void fillField(WebElement field, String keys) {
        field.clear();
        field.sendKeys(keys);
    }
}
