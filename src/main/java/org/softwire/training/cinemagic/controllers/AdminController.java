package org.softwire.training.cinemagic.controllers;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.softwire.training.cinemagic.models.Cinema;
import org.softwire.training.cinemagic.models.Film;
import org.softwire.training.cinemagic.models.Screen;
import org.softwire.training.cinemagic.models.Showing;
import org.softwire.training.cinemagic.services.CinemaService;
import org.softwire.training.cinemagic.services.FilmService;
import org.softwire.training.cinemagic.services.ScreenService;
import org.softwire.training.cinemagic.services.ShowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@Secured("ROLE_ADMIN")
public class AdminController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    private final CinemaService cinemaService;
    private final FilmService filmService;
    private final ShowingService showingService;
    private final ScreenService screenService;

    @Autowired
    public AdminController(CinemaService cinemaService,
                           FilmService filmService,
                           ShowingService showingService,
                           ScreenService screenService) {
        this.cinemaService = cinemaService;
        this.filmService = filmService;
        this.showingService = showingService;
        this.screenService = screenService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/cinemas")
    public Cinema createCinema(@Valid @RequestBody Cinema cinema) {
        return cinemaService.create(cinema);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/cinemas/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteCinema(@NotNull @PathVariable Integer id) {
        cinemaService.delete(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/films")
    public Film createFilm(@Valid @RequestBody Film film) {
        return filmService.create(film);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/films")
    public List<Film> listFilms() {
        return filmService.list();
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/films/{id}")
    public void deleteFilm(@NotNull @PathVariable("id") Integer id) {
        filmService.delete(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/showings")
    public Showing createShowing(@Valid @RequestBody Showing showing) {
        return showingService.create(showing);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/showings")
    public List<Showing> listShowings() {
        return showingService.list();
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/showings/{id}")
    public void deleteShowing(@NotNull @PathVariable Integer id) {
        showingService.delete(id);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/screens/{id}")
    public void deleteScreen(@NotNull @PathVariable Integer id) {
        screenService.delete(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/screens")
    public Screen createScreen(@NotNull @Valid @RequestBody Screen screen) {
        return screenService.create(screen);
    }

    @ExceptionHandler
    void handleConstraintViolationException(ConstraintViolationException e,
                                            HttpServletResponse response) throws IOException {
        LOGGER.info("Constraint violation exception: ", e);
        response.sendError(HttpStatus.CONFLICT.value());
    }
}
