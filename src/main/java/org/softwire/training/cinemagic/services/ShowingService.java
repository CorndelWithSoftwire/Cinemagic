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

    private final EntityManager entityManager;

    @Autowired
    public ShowingService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Showing> listCinemaShowings(Integer cinemaID) {
        return entityManager
                .createQuery("SELECT s FROM Showing AS s WHERE s.screen.cinema.id = :id ORDER BY s.time", Showing.class)
                .setParameter("id", cinemaID)
                .getResultList();
    }

    public Showing create(Showing showing) {
        entityManager.persist(showing);
        entityManager.refresh(showing);
        return showing;
    }

    public List<Showing> list() {
        return entityManager
                .createQuery("SELECT s FROM Showing AS s", Showing.class)
                .getResultList();
    }

    public void delete(Integer id) {
        Showing showing = entityManager.find(Showing.class, id);
        if (showing != null) {
            entityManager.remove(showing);
        }
    }
}
