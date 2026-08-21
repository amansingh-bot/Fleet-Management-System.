package com.fleet.tripservice.exception;

import com.fleet.tripservice.payload.ApiResponse;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TripNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleTripNotFoundException(
            TripNotFoundException ex) {

        ApiResponse<Object> response = ApiResponse.builder()
                .success(false)
                .message(ex.getMessage())
                .data(null)
                .build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleResourceNotFoundException(
            ResourceNotFoundException ex) {

        ApiResponse<Object> response = ApiResponse.builder()
                .success(false)
                .message(ex.getMessage())
                .data(null)
                .build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleException(
            Exception ex) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.builder()
                        .success(false)
                        .message(ex.getMessage())
                        .data(null)
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationException(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        ApiResponse<Map<String, String>> response = ApiResponse.<Map<String, String>>builder()
                .success(false)
                .message("Validation Failed")
                .data(errors)
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<ApiResponse<Object>> handleFeignNotFound(
            FeignException.NotFound ex) {

        String url = ex.request().url();

        String message;

        if (url.contains("/api/drivers/")) {
            message = "Driver not found";
        } else if (url.contains("/api/vehicles/")) {
            message = "Vehicle not found";
        } else {
            message = "Requested resource not found";
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.builder()
                        .success(false)
                        .message(message)
                        .data(null)
                        .build());
    }

    @ExceptionHandler(TripValidationException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleTripValidationException(
            TripValidationException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.<Map<String, String>>builder()
                        .success(false)
                        .message("Trip validation failed")
                        .data(ex.getErrors())
                        .build());
    }
}
