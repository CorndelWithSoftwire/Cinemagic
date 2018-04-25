package org.softwire.training.cinemagic.integration.pages.admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.softwire.training.cinemagic.integration.IntegrationTestSupport;
import org.softwire.training.cinemagic.integration.pages.AbstractNavigablePage;

public class AdminPage extends AbstractNavigablePage {
    private final String adminUsername;
    private final String adminPassword;

    public AdminPage(WebDriver driver,
                     IntegrationTestSupport support,
                     String adminUsername,
                     String adminPassword) {
        super(driver, support);
        this.adminUsername = adminUsername;
        this.adminPassword = adminPassword;
    }

    @Override
    public String url() {
        return "/admin";
    }

    @Override
    public void waitForPageLoad() {
        support.shortWait().until(ExpectedConditions.presenceOfElementLocated(By.className("admin")));
    }

    public void login() {
        WebElement loginForm = support.waitForElement(By.id("login-form"));
        support.fillField(By.id("login-form-username-field"), adminUsername);
        support.fillField(By.id("login-form-password-field"), adminPassword);
        driver.findElement(By.id("login-form-submit-button")).click();

        support.shortWait().until(ExpectedConditions.stalenessOf(loginForm));
    }

    public void clickCinemasLink() {
        driver.findElement(By.id("admin-link-cinemas")).click();
    }

    public void clickFilmsLink() {
        driver.findElement(By.id("admin-link-films")).click();
    }

    public void clickShowingsLink() {
        driver.findElement(By.id("admin-link-showings")).click();
    }
}
