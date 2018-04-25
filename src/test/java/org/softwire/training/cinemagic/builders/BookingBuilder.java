package org.softwire.training.cinemagic.builders;

import org.softwire.training.cinemagic.models.Booking;
import org.softwire.training.cinemagic.models.Showing;

public final class BookingBuilder {
    private Integer id;
    private Showing showing;
    private String reference;
    private Integer seatRow;
    private Integer seatNumber;

    private BookingBuilder() {
    }

    public static BookingBuilder booking() {
        return new BookingBuilder();
    }

    public BookingBuilder withId(Integer id) {
        this.id = id;
        return this;
    }

    public BookingBuilder withShowing(Showing showing) {
        this.showing = showing;
        return this;
    }

    public BookingBuilder withReference(String reference) {
        this.reference = reference;
        return this;
    }

    public BookingBuilder withSeatRow(Integer seatRow) {
        this.seatRow = seatRow;
        return this;
    }

    public BookingBuilder withSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
        return this;
    }

    public Booking build() {
        Booking booking = new Booking();
        booking.setId(id);
        booking.setShowing(showing);
        booking.setReference(reference);

        Booking.Seat seat = new Booking.Seat();
        seat.setSeatRow(seatRow);
        seat.setSeatNumber(seatNumber);

        booking.setSeat(seat);
        return booking;
    }
}
