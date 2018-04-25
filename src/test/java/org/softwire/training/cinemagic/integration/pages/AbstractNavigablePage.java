package org.softwire.training.cinemagic.integration.pages;

import org.openqa.selenium.WebDriver;
import org.softwire.training.cinemagic.integration.IntegrationTestSupport;

/**
 * An {@link AbstractPage} which can be navigated to via it's {@link #url()}.
 */
public abstract class AbstractNavigablePage extends AbstractPage {
    protected AbstractNavigablePage(WebDriver driver, IntegrationTestSupport support) {
        super(driver, support);
    }

    public void navigateToPage() {
        support.navigateToUrl(url());
        waitForPageLoad();
    }

    abstract public String url();
}
