package org.softwire.training.cinemagic.integration;

import com.google.common.base.Function;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.softwire.training.cinemagic.models.Cinema;
import org.softwire.training.cinemagic.services.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
public class BookingTest {
    @Rule
    public final WebDriverLoggingRule webDriverLoggingRule = new WebDriverLoggingRule();

    @LocalServerPort
    protected Integer port;

    @Autowired
    protected WebDriver driver;

    @Autowired
    private CinemaService cinemaService;

    @Test
    public void testTitle() {
        navigateToBookingPage();
        assertThat("Cinemagic", equalTo(driver.getTitle()));
    }

    @Test
    public void testMainlineBookingFlow() {
        String cinemaName = "Test Cinema";
        createTestData(cinemaName);

        navigateToBookingPage();
        waitForElement(By.className("cinema-select"));

        // Select the cinema
        WebElement cinemaWidget = driver.findElement(By.className("cinema-select"));
        Select select = new Select(cinemaWidget.findElement(By.tagName("select")));
        select.selectByVisibleText(cinemaName);
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        waitForElement(By.className("film-time-select"));

        // TODO: Finish this!
    }

    private void createTestData(String cinemaName) {
        Cinema cinema = new Cinema();
        cinema.setName(cinemaName);
        cinemaService.create(cinema);
    }

    private void navigateToBookingPage() {
        driver.get("http://localhost:" + port + "/booking");
    }

    @SuppressWarnings("ConstantConditions")
    private WebElement waitForElement(By locator) {
        return shortWait().until((Function<? super WebDriver, WebElement>) webDriver -> webDriver.findElement(locator));
    }

    private WebDriverWait shortWait() {
        return new WebDriverWait(driver, 10, 100);
    }
}
