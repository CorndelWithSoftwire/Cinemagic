package org.softwire.training.cinemagic.integration.pages.admin;

import com.google.common.base.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.softwire.training.cinemagic.integration.IntegrationTestSupport;
import org.softwire.training.cinemagic.integration.pages.AbstractNavigablePage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;

public class FilmPage extends AbstractNavigablePage {

    public FilmPage(WebDriver driver, IntegrationTestSupport support) {
        super(driver, support);
    }

    @Override
    public void waitForPageLoad() {
        support.waitForElement(By.className("admin-films"));
    }

    @Override
    public String url() {
        return "/admin/films";
    }

    @SuppressWarnings("ConstantConditions")
    public void addFilm(String filmName, String filmLengthMinutes) {
        // Add film
        support.fillField(By.id("films-form-name-field"), filmName);
        support.fillField(By.id("films-form-length-minutes-field"), filmLengthMinutes);
        driver.findElement(By.id("films-form-submit-button")).click();

        // Ensure film added
        WebElement nameElement = support.shortWait().until((Function<? super WebDriver, WebElement>) webDriver ->
                webDriver.findElement(filmDetailsXPath(filmName)));
        WebElement rowElement = nameElement.findElement(By.xpath("./.."));
        WebElement lengthMinutesElement = rowElement.findElement(By.className("films-details-length-minutes"));
        assertThat(lengthMinutesElement.getText(), equalTo(filmLengthMinutes));
    }

    private By filmDetailsXPath(String filmName) {
        return By.xpath("//td[@class=\"films-details-name\"][text()=\"" + filmName + "\"]");
    }

    public void deleteFilm(String filmName) {
        // Delete film
        WebElement rowElement = driver.findElement(filmDetailsXPath(filmName)).findElement(By.xpath("./.."));
        rowElement.findElement(By.className("films-details-delete-button")).click();

        // Ensure film deleted
        support.shortWait().until(stalenessOf(rowElement));
    }

    public void attemptToDeleteFilm(String filmName) {
        driver.findElement(filmDetailsXPath(filmName))
                .findElement(By.xpath("./.."))
                .findElement(By.className("films-details-delete-button"))
                .click();
    }

}
