package com.fleet.tripservice.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
public class TripValidationException extends RuntimeException{

    private final Map<String, String> errors;

    public TripValidationException(Map<String, String> errors){
        super("Trip Validation false");
        this.errors = errors;
    }
}
