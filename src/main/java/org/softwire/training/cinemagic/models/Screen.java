package org.softwire.training.cinemagic.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "screens")
public class Screen {

    private ScreenId screenId;
    private Integer rows;
    private Integer rowWidth;

    @EmbeddedId
    public ScreenId getScreenId() {
        return screenId;
    }

    public void setScreenId(ScreenId screenId) {
        this.screenId = screenId;
    }

    @Column(nullable = false)
    @Min(1)
    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    @Column(nullable = false)
    @Min(1)
    public Integer getRowWidth() {
        return rowWidth;
    }

    public void setRowWidth(Integer rowWidth) {
        this.rowWidth = rowWidth;
    }

    /**
     * Screens have a composite key consisting of a cinema and an id which is unique within that cinema
     *
     * TODO: having to specify an entire cinema is a bit awkward - cleaner way of doing this?
     * Maybe abandon the composite key!
     */
    @Embeddable
    public static class ScreenId implements Serializable {
        private Cinema cinema;
        private Integer id;

        @ManyToOne(optional = false)
        public Cinema getCinema() {
            return cinema;
        }

        public void setCinema(Cinema cinema) {
            this.cinema = cinema;
        }

        @Column(nullable = false)
        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ScreenId screenId = (ScreenId) o;
            return Objects.equals(cinema, screenId.cinema) &&
                    Objects.equals(id, screenId.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(cinema, id);
        }
    }
}
