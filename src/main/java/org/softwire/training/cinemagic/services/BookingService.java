package org.softwire.training.cinemagic.services;

import org.softwire.training.cinemagic.helpers.RandomHelper;
import org.softwire.training.cinemagic.models.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@Transactional
public class BookingService {

    @Autowired
    private EntityManager entityManager;

    public List<Booking> listBookings(Integer showingId) {
        return entityManager
                .createQuery("SELECT b FROM Booking AS b WHERE b.showing.id = :id", Booking.class)
                .setParameter("id", showingId)
                .getResultList();
    }

    public List<Booking> makeBookings(List<Booking> bookings) {
        String reference = RandomHelper.prettyUid(10);

        bookings.forEach(booking -> {
            booking.setReference(reference);
            entityManager.persist(booking);
        });

        return bookings;
    }
}
