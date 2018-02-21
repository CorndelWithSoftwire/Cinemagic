package org.softwire.training.cinemagic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.softwire.training.cinemagic.models.Cinema;
import org.softwire.training.cinemagic.services.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

// TODO Logging output here is really noisy - want to redirect the app, and possibly the webdriver to a file.
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class BookingTest {

    private static final String TEST_CINEMA_NAME = "Test Cinema";

    @LocalServerPort
    private Integer port;

    @Autowired
    private WebDriver driver;

    @Autowired
    private CinemaService cinemaService;

    @Test
    public void testTitle() {
        driver.get(getBaseUrl());
        assertEquals("Cinemagic", driver.getTitle());
    }

    @Test
    public void testBanner() {
        driver.get(getBaseUrl());
        WebElement banner = driver.findElement(By.className("banner"));
        assertEquals("Cinemagic!", banner.getText());
    }

    @Test
    public void testCinemaSelection() {
        // Arrange
        createTestCinema();
        driver.get(getBaseUrl());

        // Act
        waitForElement(By.className("cinema-select-form"));
        selectTestCinema();
        waitForElement(By.className("film-time-select"));

        // Assert
        WebElement filmTimeSelect = driver.findElement(By.className("film-time-select"));
        assertEquals(TEST_CINEMA_NAME, filmTimeSelect.findElement(By.tagName("h3")).getText());

        // TODO: Complete booking
    }

    private String getBaseUrl() {
        return "http://localhost:" + port;
    }

    private WebDriverWait shortWait() {
        return  new WebDriverWait(driver, 2, 100);
    }

    private void waitForElement(By locator) {
        shortWait().until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    private void selectTestCinema() {
        WebElement cinemaWidget = driver.findElement(By.className("cinema-select"));

        Select select = new Select(cinemaWidget.findElement(By.tagName("select")));
        select.selectByVisibleText(TEST_CINEMA_NAME);

        WebElement submit = driver.findElement(By.xpath("//button[@type='submit']"));
        submit.click();
    }

    private void createTestCinema() {
        Cinema cinema = new Cinema();
        cinema.setName(TEST_CINEMA_NAME);
        cinemaService.create(cinema);
    }
}
