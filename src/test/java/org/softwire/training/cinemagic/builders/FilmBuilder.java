package org.softwire.training.cinemagic.builders;

import org.softwire.training.cinemagic.models.Film;

public final class FilmBuilder {
    private Integer id;
    private String name;
    private Integer lengthMinutes;

    private FilmBuilder() {
    }

    public static FilmBuilder film() {
        return new FilmBuilder();
    }

    public FilmBuilder withId(Integer id) {
        this.id = id;
        return this;
    }

    public FilmBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public FilmBuilder withLengthMinutes(Integer lengthMinutes) {
        this.lengthMinutes = lengthMinutes;
        return this;
    }

    public Film build() {
        Film film = new Film();
        film.setId(id);
        film.setName(name);
        film.setLengthMinutes(lengthMinutes);
        return film;
    }
}
