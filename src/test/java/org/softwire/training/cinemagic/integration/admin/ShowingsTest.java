package org.softwire.training.cinemagic.integration.admin;

import org.junit.Test;
import org.softwire.training.cinemagic.builders.CinemaBuilder;
import org.softwire.training.cinemagic.builders.FilmBuilder;
import org.softwire.training.cinemagic.builders.ScreenBuilder;
import org.softwire.training.cinemagic.integration.IntegrationTestBase;
import org.softwire.training.cinemagic.models.Cinema;
import org.softwire.training.cinemagic.services.CinemaService;
import org.softwire.training.cinemagic.services.FilmService;
import org.softwire.training.cinemagic.services.ScreenService;
import org.springframework.beans.factory.annotation.Autowired;

public class ShowingsTest extends IntegrationTestBase {

    @Autowired private CinemaService cinemaService;
    @Autowired private FilmService filmService;
    @Autowired private ScreenService screenService;

    @Test
    public void addAndDeleteShowing() {
        filmService.create(FilmBuilder.film().withName("Film1").withLengthMinutes(123).build());
        filmService.create(FilmBuilder.film().withName("Film2").withLengthMinutes(321).build());

        Cinema cinema1 = cinemaService.create(CinemaBuilder.cinema().withName("Cinema1").build());
        screenService.create(ScreenBuilder.screen().withCinema(cinema1).withName("1").withRows(4).withRowWidth(4).build());
        screenService.create(ScreenBuilder.screen().withCinema(cinema1).withName("2").withRows(4).withRowWidth(4).build());

        Cinema cinema2 = cinemaService.create(CinemaBuilder.cinema().withName("Cinema2").build());
        screenService.create(ScreenBuilder.screen().withCinema(cinema2).withName("2").withRows(4).withRowWidth(4).build());
        screenService.create(ScreenBuilder.screen().withCinema(cinema2).withName("2").withRows(4).withRowWidth(4).build());

        this.adminPage.navigateToPage();
        this.adminPage.login();
        this.adminPage.clickShowingsLink();
        this.showingsPage.waitForPageLoad();

        this.showingsPage.selectScreen("Cinema1", "1");
        this.showingsPage.createShowing("Film2", "10/10/2010", "09:09");
        this.showingsPage.deleteShowing("Film2");
    }
}
