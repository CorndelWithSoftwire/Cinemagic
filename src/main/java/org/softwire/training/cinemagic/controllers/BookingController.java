package org.softwire.training.cinemagic.controllers;

import org.softwire.training.cinemagic.models.Booking;
import org.softwire.training.cinemagic.models.Bookings;
import org.softwire.training.cinemagic.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(value = "/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Booking> listBookings(@NotNull @RequestParam Integer showingId) {
        return bookingService.listBookings(showingId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Bookings makeBookings(@Valid @RequestBody Bookings bookings) {
        return new Bookings(bookingService.makeBookings(bookings.getBookings()));
    }
}
