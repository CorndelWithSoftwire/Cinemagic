package org.softwire.training.cinemagic.integration.pages.booking;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.softwire.training.cinemagic.integration.IntegrationTestSupport;
import org.softwire.training.cinemagic.integration.pages.AbstractPage;

public class BookingSuccessPage extends AbstractPage {
    public BookingSuccessPage(WebDriver driver, IntegrationTestSupport support) {
        super(driver, support);
    }

    @Override
    public void waitForPageLoad() {
        support.waitForElement(By.className("booking-success"));
    }
}
