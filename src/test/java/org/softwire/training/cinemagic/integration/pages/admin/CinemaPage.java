package org.softwire.training.cinemagic.integration.pages.admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.softwire.training.cinemagic.integration.IntegrationTestSupport;
import org.softwire.training.cinemagic.integration.pages.AbstractNavigablePage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CinemaPage extends AbstractNavigablePage {

    public CinemaPage(WebDriver driver, IntegrationTestSupport support) {
        super(driver, support);
    }

    @Override
    public String url() {
        return "/admin/cinemas";
    }

    @Override
    public void waitForPageLoad() {
        support.waitForElement(By.className("admin-cinemas"));
    }

    @SuppressWarnings("ConstantConditions")
    public void addCinema(String cinemaName) {
        support.fillField(By.id("cinemas-form-name-field"), cinemaName);
        driver.findElement(By.id("cinemas-form-submit-button")).click();

        // Wait for cinema to be present
        support.shortWait().until(ExpectedConditions.presenceOfElementLocated(byCinemaNameXPath(cinemaName)));
    }

    public void deleteCinema(String cinemaName) {
        WebElement rowElement = getCinemaRow(cinemaName);
        rowElement.findElement(By.className("cinema-details-delete-button")).click();

        // Ensure cinema deleted
        support.shortWait().until(ExpectedConditions.stalenessOf(rowElement));
    }

    public void addScreen(String cinemaName, String screenName, String rows, String rowWidth) {
        WebElement rowElement = getCinemaRow(cinemaName);

        // Add screen
        support.fillField(rowElement.findElement(By.className("screen-form-name-field")), screenName);
        support.fillField(rowElement.findElement(By.className("screen-form-rows-field")), rows);
        support.fillField(rowElement.findElement(By.className("screen-form-row-width-field")), rowWidth);
        rowElement.findElement(By.className("screen-form-submit-button")).click();

        // Ensure screen added
        WebElement rowsElement = support
                .shortWait()
                .until(ExpectedConditions.presenceOfElementLocated(byScreenXPath(screenName)));
        WebElement screenElement = rowsElement.findElement(By.xpath("./.."));
        assertThat(screenElement.findElement(By.className("screen-detail-row-width")).getText(), equalTo(rowWidth));
        assertThat(screenElement.findElement(By.className("screen-detail-rows")).getText(), equalTo(rows));
    }

    public void deleteScreen(String cinemaName, String screenName) {
        WebElement screenElement = findScreenElement(cinemaName, screenName);

        // Delete screen
        screenElement.findElement(By.className("screen-detail-delete-button")).click();

        // Ensure screen deleted
        support.shortWait().until(ExpectedConditions.stalenessOf(screenElement));
    }

    private WebElement getCinemaRow(String cinemaName) {
        return driver.findElement(byCinemaNameXPath(cinemaName)).findElement(By.xpath("./../.."));
    }

    private By byCinemaNameXPath(String cinemaName) {
        return By.xpath("//*[@class=\"cinema-details-name\"][text()=\"" + cinemaName + "\"]");
    }

    private By byScreenXPath(String screenName) {
        return By.xpath("//*[@class=\"screen-detail-name\"][text()=\"" + screenName + "\"]");
    }

    private WebElement findScreenElement(String cinemaName, String screenName) {
        return getCinemaRow(cinemaName).findElement(byScreenXPath(screenName)).findElement(By.xpath("./.."));
    }
}
