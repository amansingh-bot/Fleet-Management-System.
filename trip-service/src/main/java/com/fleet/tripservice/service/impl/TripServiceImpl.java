package com.fleet.tripservice.service.impl;

import com.fleet.tripservice.dto.request.TripRequest;
import com.fleet.tripservice.dto.response.TripResponse;
import com.fleet.tripservice.entity.Trip;
import com.fleet.tripservice.exception.ResourceNotFoundException;
import com.fleet.tripservice.mapper.TripMapper;
import com.fleet.tripservice.repository.TripRepository;
import com.fleet.tripservice.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {

    private final TripRepository tripRepository;
    private final TripMapper tripMapper;

    @Override
    public TripResponse createTrip(TripRequest request) {

        Trip trip = tripMapper.mapToEntity(request);
        Trip savedTrip = tripRepository.save(trip);

        return tripMapper.mapToResponse(savedTrip);
    }

    @Override
    public Page<TripResponse> getAllTripsWithPaginationAndSorting(int page, int size, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Trip> tripPage = tripRepository.findAll(pageable);

        if(tripPage.isEmpty()){
            throw new ResourceNotFoundException("No Trips Found");
        }

        return tripPage.map(tripMapper::mapToResponse);
    }

    @Override
    public TripResponse getTripById(Long id) {

        Trip trip = tripRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Trip Not Found with Id : " + id));

        return tripMapper.mapToResponse(trip);
    }
}
