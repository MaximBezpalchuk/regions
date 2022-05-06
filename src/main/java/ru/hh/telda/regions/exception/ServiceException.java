package ru.hh.telda.regions.exception;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.List;

public class ServiceException extends RuntimeException implements GraphQLError {

    private static final long serialVersionUID = 1061524633610399643L;

    public ServiceException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public ErrorClassification getErrorType() {
        return null;
    }

}
