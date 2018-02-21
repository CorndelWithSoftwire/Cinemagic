package org.softwire.training.cinemagic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class BookingTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    private WebDriver driver;

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
    public void testBooking() {
        driver.get(getBaseUrl());

        // TODO: Populate test data in a more robust way
        waitForElement(By.className("cinema-select"));
        selectCinemaWithName("Camden");
        waitForElement(By.className("film-time-select"));

        WebElement filmTimeSelect = driver.findElement(By.className("film-time-select"));
        assertEquals("Camden", filmTimeSelect.findElement(By.tagName("h3")).getText());

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

    private void selectCinemaWithName(String name) {
        WebElement cinemaWidget = driver.findElement(By.className("cinema-select"));

        Select select = new Select(cinemaWidget.findElement(By.tagName("select")));
        select.selectByVisibleText(name);

        WebElement submit = driver.findElement(By.xpath("//button[@type='submit']"));
        submit.click();
    }
}
