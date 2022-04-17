package ru.hh.telda.regions.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends ServiceException {

    private static final long serialVersionUID = 498582879177323236L;

    public EntityNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
