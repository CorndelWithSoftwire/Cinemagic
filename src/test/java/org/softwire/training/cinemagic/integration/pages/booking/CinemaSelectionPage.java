package org.softwire.training.cinemagic.integration.pages.booking;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.softwire.training.cinemagic.integration.IntegrationTestSupport;
import org.softwire.training.cinemagic.integration.pages.AbstractNavigablePage;

public class CinemaSelectionPage extends AbstractNavigablePage {
    public CinemaSelectionPage(WebDriver driver, IntegrationTestSupport support) {
        super(driver, support);
    }

    @Override
    public void waitForPageLoad() {
        support.waitForElement(By.className("cinema-select"));
    }

    @Override
    public String url() {
        return "/booking";
    }

    public void selectCinema(String cinemaName) {
        WebElement cinemaWidget = driver.findElement(By.className("cinema-select"));

        Select select = new Select(cinemaWidget.findElement(By.tagName("select")));
        select.selectByVisibleText(cinemaName);

        driver.findElement(By.xpath("//button[@type='submit']")).click();
    }
}
