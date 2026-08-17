package com.fleet.tripservice.service.impl;

import com.fleet.tripservice.client.DriverClient;
import com.fleet.tripservice.client.VehicleClient;
import com.fleet.tripservice.dto.request.TripFilterRequest;
import com.fleet.tripservice.dto.request.TripRequest;
import com.fleet.tripservice.dto.response.DriverResponse;
import com.fleet.tripservice.dto.response.TripResponse;
import com.fleet.tripservice.dto.response.VehicleResponse;
import com.fleet.tripservice.entity.Trip;
import com.fleet.tripservice.exception.ResourceNotFoundException;
import com.fleet.tripservice.mapper.TripMapper;
import com.fleet.tripservice.payload.DriverApiResponse;
import com.fleet.tripservice.payload.VehicleApiResponse;
import com.fleet.tripservice.repository.TripRepository;
import com.fleet.tripservice.service.TripService;
import com.fleet.tripservice.specification.TripSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {

    private final TripRepository tripRepository;
    private final TripMapper tripMapper;
    private final DriverClient driverClient;
    private final VehicleClient vehicleClient;

    @Override
    public TripResponse createTrip(TripRequest request) {


        Trip trip = tripMapper.mapToEntity(request);
        Trip savedTrip = tripRepository.save(trip);

        return buildTripResponse(savedTrip);
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

        return tripPage.map(this::buildTripResponse);
    }

    @Override
    public TripResponse getTripById(Long id) {

        Trip trip = getTripOrThrow(id);


        return buildTripResponse(trip);
    }

    @Override
    public TripResponse updatedTrip(Long id, TripRequest request) {
        Trip trip = getTripOrThrow(id);

        trip.setDriverId(request.getDriverId());
        trip.setVehicleId(request.getVehicleId());
        trip.setStartLocation(request.getStartLocation());
        trip.setEndLocation(request.getEndLocation());
        trip.setStartTime(request.getStartTime());
        trip.setStatus(request.getStatus());
        trip.setDistance(request.getDistance());

        Trip updateTrip = tripRepository.save(trip);

        return buildTripResponse(updateTrip);
    }

    @Override
    public void deleteTrip(Long id) {

        Trip trip = getTripOrThrow(id);
        tripRepository.delete(trip);
    }

    @Override
    public List<TripResponse> filterTrip(TripFilterRequest request) {
        Specification<Trip> specification = TripSpecification
                .filterTrip(request);

        List<Trip> trip = tripRepository.findAll(specification);

        if (trip.isEmpty()){
            throw new ResourceNotFoundException("No Trip Found");
        }

        return trip.stream()
                .map(this::buildTripResponse)
                .toList();

    }

    private Trip getTripOrThrow(Long id){

        return tripRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Trip not found with id :" + id));

    }

    private TripResponse buildTripResponse(Trip trip){
        DriverApiResponse driverApiResponse = driverClient
                .getDriverById(trip.getDriverId());

        DriverResponse driverResponse = driverApiResponse.getData();

        VehicleApiResponse vehicleApiResponse = vehicleClient
                .getVehicleById(trip.getVehicleId());

        VehicleResponse vehicleResponse = vehicleApiResponse.getData();

        System.out.println(
                "Trip ID: " + trip.getId()
                        + " | Driver ID: " + trip.getDriverId()
                        + " | Vehicle ID: " + trip.getVehicleId()
        );

        if (driverResponse == null) {
            throw new ResourceNotFoundException("Driver not found");
        }

        if(vehicleResponse == null){
            throw new ResourceNotFoundException("Vehicle not found");
        }

        return tripMapper.mapToResponse(trip, driverResponse, vehicleResponse);
    }
}
