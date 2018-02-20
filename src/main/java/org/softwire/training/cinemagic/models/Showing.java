package org.softwire.training.cinemagic.models;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "showings")
public class Showing {

    private Integer id;
    private Screen screen;
    private Film film;
    private Instant time;

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(optional = false)
    public Screen getScreen() {
        return screen;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    @ManyToOne(optional = false)
    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    @Column(nullable = false)
    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }
}
