package org.softwire.training.cinemagic.builders;

import org.softwire.training.cinemagic.models.Cinema;
import org.softwire.training.cinemagic.models.Screen;

public final class ScreenBuilder {
    private Integer id;
    private String name;
    private Cinema cinema;
    private Integer rows;
    private Integer rowWidth;

    private ScreenBuilder() {
    }

    public static ScreenBuilder screen() {
        return new ScreenBuilder();
    }

    public ScreenBuilder withId(Integer id) {
        this.id = id;
        return this;
    }

    public ScreenBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ScreenBuilder withCinema(Cinema cinema) {
        this.cinema = cinema;
        return this;
    }

    public ScreenBuilder withRows(Integer rows) {
        this.rows = rows;
        return this;
    }

    public ScreenBuilder withRowWidth(Integer rowWidth) {
        this.rowWidth = rowWidth;
        return this;
    }

    public Screen build() {
        Screen screen = new Screen();
        screen.setId(id);
        screen.setName(name);
        screen.setCinema(cinema);
        screen.setRows(rows);
        screen.setRowWidth(rowWidth);
        return screen;
    }
}
