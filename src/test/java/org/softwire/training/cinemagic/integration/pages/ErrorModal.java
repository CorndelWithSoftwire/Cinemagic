package org.softwire.training.cinemagic.integration.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.softwire.training.cinemagic.integration.IntegrationTestSupport;

public class ErrorModal extends AbstractPage {
    public ErrorModal(WebDriver driver, IntegrationTestSupport support) {
        super(driver, support);
    }

    @Override
    public void waitForPageLoad() {
        support.waitForElement(By.id("errorModal"));
    }

    public String getErrorMessage() {
        return driver.findElement(By.className("errorMessage")).getText();
    }
}
