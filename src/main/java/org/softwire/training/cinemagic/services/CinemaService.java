package org.softwire.training.cinemagic.services;

import org.softwire.training.cinemagic.models.Cinema;
import org.softwire.training.cinemagic.models.Showing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@Transactional
public class CinemaService {

    @Autowired
    private EntityManager entityManager;

    public List<Cinema> listCinemas() {
        return entityManager
                .createQuery("SELECT c FROM Cinema AS c ORDER BY c.name", Cinema.class)
                .getResultList();
    }

    public List<Showing> listShowings(Integer id) {
        return entityManager
                .createQuery("SELECT s FROM Showing AS s WHERE s.screen.id.cinema.id = :id ORDER BY s.time", Showing.class)
                .setParameter("id", id)
                .getResultList();
    }

    public Cinema create(Cinema cinema) {
        entityManager.persist(cinema);
        return cinema;
    }
}
