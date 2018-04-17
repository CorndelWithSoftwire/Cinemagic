package org.softwire.training.cinemagic.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidSeatingException extends RuntimeException {
    private InvalidSeatingException(String message) {
        super(message);
    }

    public static InvalidSeatingException seatOutsideOfBoundary() {
        throw new InvalidSeatingException("One of the requested seats was invalid");
    }

    public static InvalidSeatingException seatAlreadyBooked() {
        throw new InvalidSeatingException("One of the requested seats was already booked");
    }
}
