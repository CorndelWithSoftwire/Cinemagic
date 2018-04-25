package org.softwire.training.cinemagic.integration.booking;

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
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class BookingTest extends IntegrationTestBase {

    @Autowired
    private CinemaService cinemaService;

    @Autowired
    private FilmService filmService;

    @Autowired
    private ScreenService screenService;

    @Autowired
    private ShowingService showingService;

    @Test
    public void testTitle() {
        cinemaSelectionPage.navigateToPage();
        assertThat("Cinemagic", equalTo(driver.getTitle()));
    }

    @Test
    public void mainlineBookingFlow() {
        String cinemaName = "Test Cinema";
        String filmName = "Test Film";
        Instant filmTime = Instant.parse("2010-05-30T10:15:30.00Z");
        String formattedFilmTime = DateTimeFormatter
                .ofPattern("MMM d'th' h:mm a")  // DateTimeFormatter doesn't handle st, th, and so on, so hard code it.
                .withZone(ZoneId.systemDefault())
                .format(filmTime);
        String screenName = "Test Screen";

        createTestData(cinemaName, screenName, filmName, filmTime);

        cinemaSelectionPage.navigateToPage();
        cinemaSelectionPage.selectCinema(cinemaName);

        showingSelectionPage.waitForPageLoad();
        showingSelectionPage.selectShowing(filmName, formattedFilmTime);

        seatSelectionPage.waitForPageLoad();
        seatSelectionPage.selectSeat(0,  0);
        seatSelectionPage.selectSeat(2,  3);
        seatSelectionPage.book();

        bookingSuccessPage.waitForPageLoad();

        // Verify seats are booked
        cinemaSelectionPage.navigateToPage();
        cinemaSelectionPage.selectCinema(cinemaName);

        showingSelectionPage.waitForPageLoad();
        showingSelectionPage.selectShowing(filmName, formattedFilmTime);

        seatSelectionPage.waitForPageLoad();
        assertThat(seatSelectionPage.getSeatStatus(0,0), equalTo("Unavailable"));
        assertThat(seatSelectionPage.getSeatStatus(2,3), equalTo("Unavailable"));
    }

    private void createTestData(String cinemaName, String screenName, String filmName, Instant filmTime) {
        Cinema cinema = cinemaService.create(CinemaBuilder.cinema().withName(cinemaName).build());
        Screen screen = screenService.create(
                ScreenBuilder.screen().withName(screenName).withRows(10).withRowWidth(10).withCinema(cinema).build()
        );
        Film film = filmService.create(FilmBuilder.film().withName(filmName).withLengthMinutes(102).build());
        showingService.create(
                ShowingBuilder.showing().withFilm(film).withScreen(screen).withTime(filmTime).build()
        );
    }
}
