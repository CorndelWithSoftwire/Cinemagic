package org.softwire.training.cinemagic.services;

import org.softwire.training.cinemagic.exceptions.InvalidSeatingException;
import org.softwire.training.cinemagic.exceptions.NotFoundException;
import org.softwire.training.cinemagic.helpers.RandomHelper;
import org.softwire.training.cinemagic.models.Booking;
import org.softwire.training.cinemagic.models.Screen;
import org.softwire.training.cinemagic.models.Showing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookingService {

    private final EntityManager entityManager;

    @Autowired
    public BookingService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Booking> listBookings(Integer showingId) {
        return entityManager
                .createQuery("SELECT b FROM Booking AS b WHERE b.showing.id = :id", Booking.class)
                .setParameter("id", showingId)
                .getResultList();
    }

    public List<Booking> makeBookings(List<Booking> bookings) {
        validateBookings(bookings);

        String reference = RandomHelper.prettyUid(10);

        bookings.forEach(booking -> {
            booking.setReference(reference);
            entityManager.persist(booking);
        });

        return bookings;
    }

    private void validateBookings(List<Booking> bookings) {
        // Create a cache of showings
        Map<Integer, Showing> showings = bookings.stream()
                .map(booking -> booking.getShowing().getId())
                .distinct()
                .collect(Collectors.toMap(Function.identity(), id -> {
                    Showing showing = entityManager.find(Showing.class, id);
                    if (showing == null) {
                        throw new NotFoundException("Showing", id);
                    }
                    return showing;
                }));

        for (Booking booking : bookings) {
            Showing showing = showings.get(booking.getShowing().getId());
            validateBooking(booking, showing);
        }
    }

    private void validateBooking(Booking booking, Showing showing) {
        Screen screen = showing.getScreen();

        // Hibernate validator has already checked that the seat number and row are >= 0.
        if (booking.getSeat().getSeatNumber() >= screen.getRowWidth() ||
                booking.getSeat().getSeatRow() >= screen.getRows()) {
            throw InvalidSeatingException.seatOutsideOfBoundary();
        }

        Set<Booking.Seat> existingSeats = listBookings(showing.getId())
                .stream()
                .map(Booking::getSeat)
                .collect(Collectors.toSet());

        if (existingSeats.contains(booking.getSeat())) {
            throw InvalidSeatingException.seatAlreadyBooked();
        }
    }
}
