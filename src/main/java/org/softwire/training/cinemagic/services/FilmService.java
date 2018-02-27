package org.softwire.training.cinemagic.services;

import org.softwire.training.cinemagic.models.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
@Transactional
public class FilmService {

    @Autowired
    private EntityManager entityManager;

    public Film create(Film film) {
        entityManager.persist(film);
        return film;
    }
}
