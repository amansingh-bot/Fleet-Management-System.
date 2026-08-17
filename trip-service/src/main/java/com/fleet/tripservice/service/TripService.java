package com.fleet.tripservice.service;

import com.fleet.tripservice.dto.request.TripFilterRequest;
import com.fleet.tripservice.dto.request.TripRequest;
import com.fleet.tripservice.dto.response.TripResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TripService {

    TripResponse createTrip(TripRequest request);

    Page<TripResponse> getAllTripsWithPaginationAndSorting(
            int page,
            int size,
            String sortBy,
            String sortDir);

    TripResponse getTripById(Long id);

    TripResponse updatedTrip(Long id, TripRequest request);

     void deleteTrip(Long id);

     List<TripResponse> filterTrip(TripFilterRequest request);
}
