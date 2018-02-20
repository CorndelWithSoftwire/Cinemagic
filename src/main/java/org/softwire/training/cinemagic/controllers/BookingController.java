package org.softwire.training.cinemagic.controllers;

import org.softwire.training.cinemagic.models.Booking;
import org.softwire.training.cinemagic.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Booking> listBookings(@RequestParam Integer showingId) {
        return bookingService.listBookings(showingId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public List<Booking> makeBookings(@RequestBody List<Booking> bookings) {
        return bookingService.makeBookings(bookings);
    }
}
