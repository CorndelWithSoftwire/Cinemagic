package org.softwire.training.cinemagic.integration.pages;

import org.openqa.selenium.WebDriver;
import org.softwire.training.cinemagic.integration.IntegrationTestSupport;

/**
 * Base class for pages.
 *
 * A page here does not mean a webpage, but rather a distinct component in the web app.  See
 * https://martinfowler.com/bliki/PageObject.html
 */
public abstract class AbstractPage {
    protected final WebDriver driver;
    protected final IntegrationTestSupport support;

    protected AbstractPage(WebDriver driver, IntegrationTestSupport support) {
        this.driver = driver;
        this.support = support;
    }

    abstract public void waitForPageLoad();
}
