package org.softwire.training.cinemagic.builders;

import org.softwire.training.cinemagic.models.Cinema;
import org.softwire.training.cinemagic.models.Screen;

import java.util.List;

public final class CinemaBuilder {
    private Integer id;
    private String name;
    private List<Screen> screens;

    private CinemaBuilder() {
    }

    public static CinemaBuilder cinema() {
        return new CinemaBuilder();
    }

    public CinemaBuilder withId(Integer id) {
        this.id = id;
        return this;
    }

    public CinemaBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public CinemaBuilder withScreens(List<Screen> screens) {
        this.screens = screens;
        return this;
    }

    public Cinema build() {
        Cinema cinema = new Cinema();
        cinema.setId(id);
        cinema.setName(name);
        cinema.setScreens(screens);
        return cinema;
    }
}
