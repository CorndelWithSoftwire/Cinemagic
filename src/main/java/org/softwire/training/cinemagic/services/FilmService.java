package org.softwire.training.cinemagic.services;

import org.softwire.training.cinemagic.models.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@Transactional
public class FilmService {
    private final EntityManager entityManager;

    @Autowired
    public FilmService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Film create(Film film) {
        entityManager.persist(film);
        return film;
    }

    public List<Film> list() {
        return entityManager.createQuery("SELECT f FROM Film f", Film.class).getResultList();
    }

    public void delete(Integer id) {
        Film film = entityManager.find(Film.class, id);
        if (film != null) {
            entityManager.remove(film);
        }
    }
}
