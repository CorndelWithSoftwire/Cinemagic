package org.softwire.training.cinemagic.services;

import org.softwire.training.cinemagic.models.Showing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@Transactional
public class ShowingService {

    @Autowired
    private EntityManager entityManager;

    public List<Showing> listCinemaShowings(Integer cinemaID) {
        return entityManager
                .createQuery("SELECT s FROM Showing AS s WHERE s.screen.id.cinema.id = :id ORDER BY s.time", Showing.class)
                .setParameter("id", cinemaID)
                .getResultList();
    }

    public Showing create(Showing showing) {
        entityManager.persist(showing);
        return showing;
    }
}
