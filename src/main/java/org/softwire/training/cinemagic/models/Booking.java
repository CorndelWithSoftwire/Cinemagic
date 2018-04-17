package org.softwire.training.cinemagic.models;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.google.common.base.Objects;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Entity
@Table(name = "bookings")
public class Booking {

    private Integer id;
    private Showing showing;
    private String reference;
    private Seat seat;

    @Null
    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NotNull
    @ManyToOne(optional = false)
    public Showing getShowing() {
        return showing;
    }

    public void setShowing(Showing showing) {
        this.showing = showing;
    }

    @Null
    @Column(nullable = false)
    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    @Embedded
    @JsonUnwrapped
    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    @Embeddable
    public static class Seat {
        private Integer seatRow;
        private Integer seatNumber;

        public Seat() {
        }

        @Min(0)
        @NotNull
        @Column(nullable = false)
        public Integer getSeatRow() {
            return seatRow;
        }

        public void setSeatRow(Integer seatRow) {
            this.seatRow = seatRow;
        }

        @Min(0)
        @NotNull
        @Column(nullable = false)
        public Integer getSeatNumber() {
            return seatNumber;
        }

        public void setSeatNumber(Integer seatNumber) {
            this.seatNumber = seatNumber;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Seat seat = (Seat) o;
            return Objects.equal(seatRow, seat.seatRow) &&
                    Objects.equal(seatNumber, seat.seatNumber);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(seatRow, seatNumber);
        }
    }
}
