package org.softwire.training.cinemagic.services;

import org.softwire.training.cinemagic.models.Cinema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class CinemaService {

    @Autowired
    private EntityManager entityManager;

    public List<Cinema> listCinemas() {
        return entityManager
                .createQuery("SELECT c FROM Cinema AS c ORDER BY c.name", Cinema.class)
                .getResultList();
    }
}
