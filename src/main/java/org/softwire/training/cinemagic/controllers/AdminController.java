package org.softwire.training.cinemagic.controllers;

import org.softwire.training.cinemagic.models.Cinema;
import org.softwire.training.cinemagic.models.Film;
import org.softwire.training.cinemagic.models.Showing;
import org.softwire.training.cinemagic.services.CinemaService;
import org.softwire.training.cinemagic.services.FilmService;
import org.softwire.training.cinemagic.services.ShowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

// TODO: Wire this up to a simple admin page
// Could be one of:
// a) static HTML page with simple JS
// b) react page, either part of the main app or separate bundle
// c) MVC page using thymeleaf or similar
// N.B. Auth needs to match approach
@RestController
@RequestMapping("/api/admin")
@Secured("ROLE_ADMIN")
public class AdminController {

    @Autowired
    private CinemaService cinemaService;

    @Autowired
    private FilmService filmService;

    @Autowired
    private ShowingService showingService;

    @RequestMapping(method = RequestMethod.POST, value = "/cinemas")
    public Cinema createCinema(@RequestBody Cinema cinema) {
        return cinemaService.create(cinema);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/films")
    public Film createFilm(@RequestBody Film film) {
        return filmService.create(film);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/showings")
    public Showing createShowing(@RequestBody Showing showing) {
        return showingService.create(showing);
    }
}
