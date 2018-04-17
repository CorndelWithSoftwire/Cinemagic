package org.softwire.training.cinemagic.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.MessageFormat;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    public NotFoundException(String entity, Object id) {
        super(MessageFormat.format("Unable to locate entity: {0} with id: {1}", entity, id));
    }
}
