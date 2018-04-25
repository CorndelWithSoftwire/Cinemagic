package org.softwire.training.cinemagic.integration;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.softwire.training.cinemagic.integration.pages.ErrorModal;
import org.softwire.training.cinemagic.integration.pages.admin.AdminPage;
import org.softwire.training.cinemagic.integration.pages.admin.CinemaPage;
import org.softwire.training.cinemagic.integration.pages.admin.FilmPage;
import org.softwire.training.cinemagic.integration.pages.admin.ShowingsPage;
import org.softwire.training.cinemagic.integration.pages.booking.BookingSuccessPage;
import org.softwire.training.cinemagic.integration.pages.booking.ShowingSelectionPage;
import org.softwire.training.cinemagic.integration.pages.booking.CinemaSelectionPage;
import org.softwire.training.cinemagic.integration.pages.booking.SeatSelectionPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * Abstract base class which all integration tests will inherit from.  Looks after the {@link WebDriver} and
 * page objects.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
public abstract class IntegrationTestBase {

    @Rule
    public final WebDriverLoggingRule webDriverLoggingRule = new WebDriverLoggingRule();

    @LocalServerPort
    protected Integer port;

    @Autowired
    protected WebDriver driver;

    @Value("${cinemagic.admin.username}")
    protected String adminUsername;

    @Value("${cinemagic.admin.password}")
    protected String adminPassword;

    protected IntegrationTestSupport support;

    protected AdminPage adminPage;
    protected CinemaPage cinemaPage;
    protected FilmPage filmPage;
    protected ShowingsPage showingsPage;
    protected ShowingSelectionPage showingSelectionPage;
    protected CinemaSelectionPage cinemaSelectionPage;
    protected SeatSelectionPage seatSelectionPage;
    protected BookingSuccessPage bookingSuccessPage;
    protected ErrorModal errorModal;

    @Before
    public void before() {
        support = new IntegrationTestSupport(driver, port);

        adminPage = new AdminPage(driver, support, adminUsername, adminPassword);
        cinemaPage = new CinemaPage(driver, support);
        filmPage = new FilmPage(driver, support);
        showingsPage = new ShowingsPage(driver, support);
        showingSelectionPage = new ShowingSelectionPage(driver, support);
        cinemaSelectionPage = new CinemaSelectionPage(driver, support);
        seatSelectionPage = new SeatSelectionPage(driver, support);
        bookingSuccessPage = new BookingSuccessPage(driver, support);
        errorModal = new ErrorModal(driver, support);
    }
}
