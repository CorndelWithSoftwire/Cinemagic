package org.softwire.training.cinemagic.controllers;

import org.softwire.training.cinemagic.exceptions.NotFoundException;
import org.softwire.training.cinemagic.models.Cinema;
import org.softwire.training.cinemagic.models.Showing;
import org.softwire.training.cinemagic.services.CinemaService;
import org.softwire.training.cinemagic.services.ShowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/cinemas")
public class CinemaController {

    private final CinemaService cinemaService;
    private final ShowingService showingService;

    @Autowired
    public CinemaController(CinemaService cinemaService,
                            ShowingService showingService) {
        this.cinemaService = cinemaService;
        this.showingService = showingService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Cinema> listCinemas() {
        return cinemaService.list();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Cinema getCinema(@PathVariable Integer id) throws NotFoundException {
        Cinema cinema = cinemaService.get(id);
        if (cinema == null) {
            throw new NotFoundException("Cinema", id);
        }
        return cinema;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}/showings")
    public List<Showing> listShowings(@PathVariable Integer id) {
        return showingService.listCinemaShowings(id);
    }
}
