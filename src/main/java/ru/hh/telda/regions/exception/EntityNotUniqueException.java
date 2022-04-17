package ru.hh.telda.regions.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EntityNotUniqueException extends ServiceException {

    private static final long serialVersionUID = -682774621895081494L;

    public EntityNotUniqueException(String errorMessage) {
        super(errorMessage);
    }
}
