package org.softwire.training.cinemagic.models;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import java.util.List;

/**
 * Hibernate will only validate Java Beans, so if we want a list of bookings to be validated we need to wrap it in a
 * bean.
 */
public class Bookings {
    private List<Booking> bookings;

    public Bookings() {
    }

    public Bookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    @Valid
    @NotEmpty
    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}
