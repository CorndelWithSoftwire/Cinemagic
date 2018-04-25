package org.softwire.training.cinemagic.integration.admin;

import org.junit.Test;
import org.softwire.training.cinemagic.integration.IntegrationTestBase;

public class CinemaTest extends IntegrationTestBase {

    @Test
    @SuppressWarnings("ConstantConditions")
    public void testAddAndDeleteCinema() {
        String cinemaName = "Camden";

        adminPage.navigateToPage();
        adminPage.login();

        adminPage.clickCinemasLink();
        cinemaPage.waitForPageLoad();

        cinemaPage.addCinema(cinemaName);
        cinemaPage.deleteCinema(cinemaName);
    }

    @Test
    public void testAddAndDeleteScreens() {
        String cinemaName = "Kentish Town";
        String screenName = "1";
        String rows = "10";
        String rowWidth = "19";

        adminPage.navigateToPage();
        adminPage.login();
        adminPage.clickCinemasLink();
        cinemaPage.waitForPageLoad();

        cinemaPage.addCinema(cinemaName);

        cinemaPage.addScreen(cinemaName, screenName, rows, rowWidth);
        cinemaPage.deleteScreen(cinemaName, screenName);
    }
}
