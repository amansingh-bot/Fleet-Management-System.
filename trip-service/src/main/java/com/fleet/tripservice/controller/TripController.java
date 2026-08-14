package com.fleet.tripservice.controller;


import com.fleet.tripservice.dto.request.TripRequest;
import com.fleet.tripservice.dto.response.TripResponse;
import com.fleet.tripservice.payload.ApiResponse;
import com.fleet.tripservice.service.TripService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;

@RestController
@RequestMapping("/api/trips")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    @PostMapping
    public ResponseEntity<ApiResponse<TripResponse>> createTrip(
            @Valid @RequestBody TripRequest request){

        TripResponse response = tripService.createTrip(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<TripResponse>builder()
                        .success(true)
                        .message("Trip Created Successfully")
                        .data(response)
                        .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<TripResponse>>> getAllTrips(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir){

        Page<TripResponse> response = tripService
                .getAllTripsWithPaginationAndSorting(page, size, sortBy, sortDir);

        return ResponseEntity.ok(
                ApiResponse.<Page<TripResponse>>builder()
                        .success(true)
                        .message("Trips fetched Successfully")
                        .data(response)
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TripResponse>> getTripById(
            @PathVariable Long id){

        TripResponse response = tripService.getTripById(id);

        return ResponseEntity.ok(
                ApiResponse.<TripResponse>builder()
                        .success(true)
                        .message("Trip Retrieved Successfully")
                        .data(response)
                        .build());
    }
}
