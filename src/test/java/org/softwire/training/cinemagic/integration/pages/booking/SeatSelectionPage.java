package org.softwire.training.cinemagic.integration.pages.booking;

import com.google.common.base.Predicate;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.softwire.training.cinemagic.integration.IntegrationTestSupport;
import org.softwire.training.cinemagic.integration.pages.AbstractPage;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

public class SeatSelectionPage extends AbstractPage {

    public SeatSelectionPage(WebDriver driver, IntegrationTestSupport support) {
        super(driver, support);
    }

    @Override
    public void waitForPageLoad() {
        support.waitForElement(By.className("seat-select"));
    }

    public void selectSeat(int column, int row) {
        WebElement seat = getSeat(column, row);

        assertThat(seat.isEnabled(), equalTo(true));
        assertThat(seat.getAttribute("title"), equalTo("Available"));

        seat.click();
        support.shortWait().until((Predicate<WebDriver>) webDriver -> seat.getAttribute("title").equals("Selected"));
    }

    public String getSeatStatus(int column, int row) {
        return getSeat(column, row).getAttribute("title");
    }

    private WebElement getSeat(int column, int row) {
        WebElement seats = driver.findElement(By.className("seats"));
        List<WebElement> rows = seats.findElements(By.className("row"));

        return rows.get(row).findElements(By.className("seat")).get(column);
    }

    public void book() {
        driver.findElement(By.id("seat-select-book-button")).click();
    }
}
