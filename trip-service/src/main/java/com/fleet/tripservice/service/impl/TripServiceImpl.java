package com.fleet.tripservice.service.impl;

import com.fleet.tripservice.client.DriverClient;
import com.fleet.tripservice.client.VehicleClient;
import com.fleet.tripservice.dto.request.TripFilterRequest;
import com.fleet.tripservice.dto.request.TripRequest;
import com.fleet.tripservice.dto.response.DriverResponse;
import com.fleet.tripservice.dto.response.TripDetailResponse;
import com.fleet.tripservice.dto.response.TripResponse;
import com.fleet.tripservice.dto.response.VehicleResponse;
import com.fleet.tripservice.entity.Trip;
import com.fleet.tripservice.enums.TripStatus;
import com.fleet.tripservice.exception.ResourceNotFoundException;
import com.fleet.tripservice.exception.TripValidationException;
import com.fleet.tripservice.mapper.TripMapper;
import com.fleet.tripservice.payload.DriverApiResponse;
import com.fleet.tripservice.payload.VehicleApiResponse;
import com.fleet.tripservice.repository.TripRepository;
import com.fleet.tripservice.service.TripService;
import com.fleet.tripservice.specification.TripSpecification;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {

    private final TripRepository tripRepository;
    private final TripMapper tripMapper;
    private final DriverClient driverClient;
    private final VehicleClient vehicleClient;

    @Override
    public TripResponse createTrip(TripRequest request) {

        Map<String, String> errors = new HashMap<>();

        DriverResponse driverResponse = getAndValidateDriver(
                        request.getDriverId(),
                        errors
                );

        VehicleResponse vehicleResponse = getAndValidateVehicle(
                        request.getVehicleId(),
                        errors
                );

        List<TripStatus> activeStatues = List.of(TripStatus.CREATED , TripStatus.STARTED);

        boolean driverAlreadyAssigned = tripRepository
                .existsByDriverIdAndStatusIn(request.getDriverId(), activeStatues);

        if(driverAlreadyAssigned){
            errors.put("driver",
                       "Driver is already assigned to an active trip");
        }

        boolean vehicleAlreadyAssigned = tripRepository
                .existsByVehicleIdAndStatusIn(request.getVehicleId(), activeStatues);

        if (vehicleAlreadyAssigned){
            errors.put("vehicle",
                       "Vehicle is already assigned to an active trip");
        }

        if (!errors.isEmpty()) {
            throw new TripValidationException(errors);
        }

        Trip trip = tripMapper.mapToEntity(request);

        Trip savedTrip = tripRepository.save(trip);

        return tripMapper.mapToResponse(
                savedTrip,
                driverResponse,
                vehicleResponse
        );
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
    public TripDetailResponse getTripById(Long id) {

        Trip trip = getTripOrThrow(id);

        return buildTripDetailResponse(trip);
    }

    @Override
    public TripResponse updatedTrip(Long id, TripRequest request) {

        Trip trip = getTripOrThrow(id);

        Map<String, String> errors = new HashMap<>();

        validateStatusTransition(
                trip.getStatus(),
                request.getStatus(),
                errors
        );

        DriverResponse driverResponse = getAndValidateDriver(
                        request.getDriverId(),
                        errors
                );

        VehicleResponse vehicleResponse = getAndValidateVehicle(
                        request.getVehicleId(),
                        errors
                );

        List<TripStatus> activeStatuses = List.of(TripStatus.CREATED, TripStatus.STARTED);

        boolean driverAlreadyAssigned = tripRepository
                .existsByDriverIdAndStatusInAndIdNot(request.getDriverId(), activeStatuses, id);

        if (driverAlreadyAssigned){
            errors.put("driver",
                    "Driver is already assigned to another active trip");
        }

        boolean vehicleAlreadyAssigned = tripRepository
                .existsByVehicleIdAndStatusInAndIdNot(request.getVehicleId(), activeStatuses, id);

        if (vehicleAlreadyAssigned){
            errors.put("vehicle",
                    "Vehicle is already assigned to another active trip");
        }

        if (!errors.isEmpty()){
             throw new TripValidationException(errors);
        }

        trip.setDriverId(request.getDriverId());
        trip.setVehicleId(request.getVehicleId());
        trip.setStartLocation(request.getStartLocation());
        trip.setEndLocation(request.getEndLocation());
        trip.setStartTime(request.getStartTime());
        trip.setStatus(request.getStatus());
        trip.setDistance(request.getDistance());

        Trip updateTrip = tripRepository.save(trip);

        return tripMapper.mapToResponse(
                updateTrip,
                driverResponse,
                vehicleResponse
        );
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

    private TripResponse buildTripResponse(Trip trip) {

        DriverResponse driverResponse;
        VehicleResponse vehicleResponse;

        try {
            DriverApiResponse driverApiResponse = driverClient
                    .getDriverById(trip.getDriverId());

             driverResponse = driverApiResponse.getData();
        } catch (FeignException.NotFound ex){

            throw new ResourceNotFoundException(
                    "Driver not found with id: " + trip.getDriverId());
        }

        try {
            VehicleApiResponse vehicleApiResponse = vehicleClient
                    .getVehicleById(trip.getVehicleId());

            vehicleResponse = vehicleApiResponse.getData();
        } catch (FeignException.NotFound ex){

            throw new ResourceNotFoundException(
                    "Vehicle not found with id: " + trip.getVehicleId());
        }

        if (driverResponse == null) {
            throw new ResourceNotFoundException(
                    "Driver not found with id: " + trip.getDriverId());
        }

        if(vehicleResponse == null){
            throw new ResourceNotFoundException(
                    "Vehicle not found with id: " + trip.getVehicleId());
        }

        return tripMapper.mapToResponse(trip, driverResponse, vehicleResponse);
    }

    private TripDetailResponse buildTripDetailResponse(Trip trip) {

        DriverResponse driverResponse = null;
        VehicleResponse vehicleResponse = null;

        Map<String, String> errors = new HashMap<>();

        // Driver
        try {

            DriverApiResponse driverApiResponse =
                    driverClient.getDriverById(trip.getDriverId());

            driverResponse = driverApiResponse.getData();

            if (driverResponse == null) {
                errors.put(
                        "driver",
                        "Driver not found with id: " + trip.getDriverId()
                );
            }

        } catch (FeignException.NotFound ex) {

            errors.put(
                    "driver",
                    "Driver not found with id: " + trip.getDriverId()
            );
        }

        // Vehicle
        try {

            VehicleApiResponse vehicleApiResponse =
                    vehicleClient.getVehicleById(trip.getVehicleId());

            vehicleResponse = vehicleApiResponse.getData();

            if (vehicleResponse == null) {
                errors.put(
                        "vehicle",
                        "Vehicle not found with id: " + trip.getVehicleId()
                );
            }

        } catch (FeignException.NotFound ex) {

            errors.put(
                    "vehicle",
                    "Vehicle not found with id: " + trip.getVehicleId()
            );
        }

        return TripDetailResponse.builder()
                .tripId(trip.getId())
                .driverId(trip.getDriverId())
                .driver(driverResponse)
                .vehicleId(trip.getVehicleId())
                .vehicle(vehicleResponse)
                .errors(errors.isEmpty() ? null : errors)
                .build();
    }

    private DriverResponse getAndValidateDriver(
            Long driverId,
            Map<String, String> errors) {

        try {
            DriverApiResponse driverApiResponse =
                    driverClient.getDriverById(driverId);

            DriverResponse driverResponse =
                    driverApiResponse.getData();

            switch (driverResponse.getStatus()) {

                case ON_TRIP:
                    errors.put(
                            "driver",
                            "Driver is already on a trip"
                    );
                    break;

                case INACTIVE:
                    errors.put(
                            "driver",
                            "Driver is inactive"
                    );
                    break;

                case ON_LEAVE:
                    errors.put(
                            "driver",
                            "Driver is on leave"
                    );
                    break;

                case AVAILABLE:
                    break;
            }

            return driverResponse;

        } catch (FeignException.NotFound ex) {

            errors.put(
                    "driver",
                    "Driver not found with id: " + driverId
            );

            return null;
        }
    }

    private VehicleResponse getAndValidateVehicle(
            Long vehicleId,
            Map<String, String> errors) {

        try {
            VehicleApiResponse vehicleApiResponse =
                    vehicleClient.getVehicleById(vehicleId);

            VehicleResponse vehicleResponse =
                    vehicleApiResponse.getData();

            switch (vehicleResponse.getStatus()) {

                case ON_TRIP:
                    errors.put(
                            "vehicle",
                            "Vehicle is already on a trip"
                    );
                    break;

                case MAINTENANCE:
                    errors.put(
                            "vehicle",
                            "Vehicle is under maintenance"
                    );
                    break;

                case INACTIVE:
                    errors.put(
                            "vehicle",
                            "Vehicle is inactive"
                    );
                    break;

                case AVAILABLE:
                    break;
            }

            return vehicleResponse;

        } catch (FeignException.NotFound ex) {

            errors.put(
                    "vehicle",
                    "Vehicle not found with id: " + vehicleId
            );

            return null;
        }
    }

    private void validateStatusTransition(
            TripStatus currentStatus,
            TripStatus newStatus,
            Map<String, String> errors) {

        boolean valid = switch (currentStatus) {

            case CREATED ->
                    newStatus == TripStatus.STARTED
                            || newStatus == TripStatus.CANCELLED;

            case STARTED ->
                    newStatus == TripStatus.COMPLETED;

            case COMPLETED, CANCELLED ->
                    false;
        };

        if (!valid) {
            errors.put(
                    "status",
                    "Invalid trip status transition from "
                            + currentStatus
                            + " to "
                            + newStatus
            );
        }
    }

}
