package org.softwire.training.cinemagic.controllers;

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

    @Autowired
    private CinemaService cinemaService;

    @Autowired
    private ShowingService showingService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Cinema> listCinemas() {
        return cinemaService.listCinemas();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}/showings")
    public List<Showing> listShowings(@PathVariable Integer id) {
        return showingService.listCinemaShowings(id);
    }
}
