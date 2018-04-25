package org.softwire.training.cinemagic.integration.pages.booking;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.softwire.training.cinemagic.integration.IntegrationTestSupport;
import org.softwire.training.cinemagic.integration.pages.AbstractPage;

import java.text.MessageFormat;

public class ShowingSelectionPage extends AbstractPage {
    public ShowingSelectionPage(WebDriver driver, IntegrationTestSupport support) {
        super(driver, support);
    }

    @Override
    public void waitForPageLoad() {
        support.waitForElement(By.className("showing-select"));
    }

    public void selectShowing(String filmName, String time) {
        driver.findElements(By.xpath("//button[@class=\"showing-selection-button\"]"))
                .stream()
                .filter(webElement -> webElement.getText().contains(filmName) && webElement.getText().contains(time))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(MessageFormat.format(
                        "No showing with name {0} and time {1}{2}", filmName, time, " found")))
                .click();
    }
}
