package org.softwire.training.cinemagic.builders;

import org.softwire.training.cinemagic.models.Film;
import org.softwire.training.cinemagic.models.Screen;
import org.softwire.training.cinemagic.models.Showing;

import java.time.Instant;

public final class ShowingBuilder {
    private Integer id;
    private Screen screen;
    private Film film;
    private Instant time;

    private ShowingBuilder() {
    }

    public static ShowingBuilder showing() {
        return new ShowingBuilder();
    }

    public ShowingBuilder withId(Integer id) {
        this.id = id;
        return this;
    }

    public ShowingBuilder withScreen(Screen screen) {
        this.screen = screen;
        return this;
    }

    public ShowingBuilder withFilm(Film film) {
        this.film = film;
        return this;
    }

    public ShowingBuilder withTime(Instant time) {
        this.time = time;
        return this;
    }

    public Showing build() {
        Showing showing = new Showing();
        showing.setId(id);
        showing.setScreen(screen);
        showing.setFilm(film);
        showing.setTime(time);
        return showing;
    }
}
