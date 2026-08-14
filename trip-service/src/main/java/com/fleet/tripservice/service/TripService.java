package com.fleet.tripservice.service;

import com.fleet.tripservice.dto.request.TripRequest;
import com.fleet.tripservice.dto.response.TripResponse;
import org.springframework.data.domain.Page;

public interface TripService {

    TripResponse createTrip(TripRequest request);

    Page<TripResponse> getAllTripsWithPaginationAndSorting(
            int page,
            int size,
            String sortBy,
            String sortDir);

    TripResponse getTripById(Long id);
}
