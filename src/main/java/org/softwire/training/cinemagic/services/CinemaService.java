package org.softwire.training.cinemagic.services;

import org.softwire.training.cinemagic.models.Cinema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@Transactional
public class CinemaService {

    private final EntityManager entityManager;

    @Autowired
    public CinemaService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Cinema> list() {
        return entityManager
                .createQuery("SELECT c FROM Cinema AS c ORDER BY c.name", Cinema.class)
                .getResultList();
    }

    public Cinema get(Integer cinemaId) {
        return entityManager.find(Cinema.class, cinemaId);
    }

    public Cinema create(Cinema cinema) {
        entityManager.persist(cinema);
        entityManager.refresh(cinema);
        return cinema;
    }

    public void delete(Integer cinemaId) {
        Cinema cinema = entityManager.find(Cinema.class, cinemaId);
        if (cinema != null) {
            entityManager.remove(cinema);
        }
    }
}
