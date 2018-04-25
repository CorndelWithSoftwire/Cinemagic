package org.softwire.training.cinemagic.integration.admin;

import org.junit.Test;
import org.softwire.training.cinemagic.builders.CinemaBuilder;
import org.softwire.training.cinemagic.builders.FilmBuilder;
import org.softwire.training.cinemagic.builders.ScreenBuilder;
import org.softwire.training.cinemagic.builders.ShowingBuilder;
import org.softwire.training.cinemagic.integration.IntegrationTestBase;
import org.softwire.training.cinemagic.models.Cinema;
import org.softwire.training.cinemagic.models.Film;
import org.softwire.training.cinemagic.models.Screen;
import org.softwire.training.cinemagic.services.CinemaService;
import org.softwire.training.cinemagic.services.FilmService;
import org.softwire.training.cinemagic.services.ScreenService;
import org.softwire.training.cinemagic.services.ShowingService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class FilmTest extends IntegrationTestBase {

    @Autowired private CinemaService cinemaService;
    @Autowired private FilmService filmService;
    @Autowired private ShowingService showingService;
    @Autowired private ScreenService screenService;

    @Test
    public void filmsPageLoadsByDefault() {
        adminPage.navigateToPage();
        adminPage.login();

        filmPage.waitForPageLoad();
    }

    @Test
    public void addAndDeleteFilm() {
        String filmName = "One flew over the cuckoo's nest";
        String filmLengthMinutes = "120";

        adminPage.navigateToPage();
        adminPage.login();
        adminPage.clickFilmsLink();
        filmPage.waitForPageLoad();

        filmPage.addFilm(filmName, filmLengthMinutes);
        filmPage.deleteFilm(filmName);
    }

    @Test
    public void displaysErrorWhenDeletingFilmInUse() {
        String filmName = "The Shawshank Redemption";
        Film film = filmService.create(FilmBuilder.film().withName(filmName).withLengthMinutes(160).build());
        Cinema cinema = cinemaService.create(CinemaBuilder.cinema().withName("Portswood").build());
        Screen screen = screenService.create(
                ScreenBuilder.screen().withName("Screen1").withCinema(cinema).withRows(10).withRowWidth(10).build()
        );
        showingService.create(
                ShowingBuilder.showing().withFilm(film).withScreen(screen).withTime(Instant.EPOCH).build()
        );

        adminPage.navigateToPage();
        adminPage.login();
        adminPage.clickFilmsLink();
        filmPage.waitForPageLoad();

        filmPage.attemptToDeleteFilm(filmName);
        errorModal.waitForPageLoad();
        assertThat(errorModal.getErrorMessage(), containsString("Unable to delete film"));
    }

}

