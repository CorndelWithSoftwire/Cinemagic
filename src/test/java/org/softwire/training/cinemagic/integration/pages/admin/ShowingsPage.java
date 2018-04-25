package org.softwire.training.cinemagic.integration.pages.admin;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.softwire.training.cinemagic.integration.IntegrationTestSupport;
import org.softwire.training.cinemagic.integration.pages.AbstractNavigablePage;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ShowingsPage extends AbstractNavigablePage {

    public ShowingsPage(WebDriver driver, IntegrationTestSupport support) {
        super(driver, support);
    }

    @Override
    public void waitForPageLoad() {
        support.waitForElement(By.className("admin-showings"));
    }

    @Override
    public String url() {
        return "/admin/showings";
    }

    public void selectScreen(String cinemaName, String screenName) {
        driver.findElement(By.className("screen-selector"))
                .findElements(By.tagName("button"))
                .stream()
                .filter(webElement -> webElement.getText().contains(cinemaName + " - " + screenName))
                .findFirst()
                .get()
                .click();
        support.waitForElement(By.className("showings-details"));
    }

    @SuppressWarnings("ConstantConditions")
    public void createShowing(String filmName, String date, String time) {
        new Select(driver.findElement(By.id("showing-form-film-field"))).selectByVisibleText(filmName);
        driver.findElement(By.id("showing-form-date-field")).sendKeys(date);
        driver.findElement(By.id("showing-form-time-field")).sendKeys(time);
        driver.findElement(By.id("showing-form-submit-button")).click();

        WebElement showing = support
                .shortWait()
                .until(ExpectedConditions.presenceOfElementLocated(byShowingXPath(filmName)))
                .findElement(By.xpath("./.."));

        assertThat(showing.findElement(By.className("showing-detail-time")).getText(), equalTo(date + " " + time));
    }

    @SuppressWarnings("ConstantConditions")
    public void deleteShowing(String filmName) {
        WebElement showing = driver.findElement(byShowingXPath(filmName)).findElement(By.xpath("./.."));
        showing.findElement(By.className("showing-detail-delete-button")).click();

        support.shortWait().until(ExpectedConditions.stalenessOf(showing));
    }

    private By byShowingXPath(String filmName) {
        return By.xpath("//*[@class=\"showing-detail-film-name\"][text()=\"" + filmName + "\"]");
    }
}
