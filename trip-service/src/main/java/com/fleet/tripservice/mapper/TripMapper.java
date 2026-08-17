package com.fleet.tripservice.mapper;

import com.fleet.tripservice.dto.request.TripRequest;
import com.fleet.tripservice.dto.response.DriverResponse;
import com.fleet.tripservice.dto.response.TripResponse;
import com.fleet.tripservice.dto.response.VehicleResponse;
import com.fleet.tripservice.entity.Trip;
import org.springframework.stereotype.Component;

@Component
public class TripMapper {

    public Trip mapToEntity(TripRequest request){

        return Trip.builder()
                .driverId(request.getDriverId())
                .vehicleId(request.getVehicleId())
                .startLocation(request.getStartLocation())
                .endLocation(request.getEndLocation())
                .startTime(request.getStartTime())
                .status(request.getStatus())
                .distance(request.getDistance())
                .build();
    }

    public TripResponse mapToResponse(Trip trip){


        return TripResponse.builder()
                .id(trip.getId())
                .driverId(trip.getDriverId())
                .vehicleId(trip.getVehicleId())
                .startLocation(trip.getStartLocation())
                .endLocation(trip.getEndLocation())
                .startTime(trip.getStartTime())
                .endTime(trip.getEndTime())
                .status(trip.getStatus())
                .distance(trip.getDistance())
                .build();
    }

    public TripResponse mapToResponse(
            Trip trip,
            DriverResponse driver,
            VehicleResponse vehicle) {

        TripResponse response = mapToResponse(trip);

        response.setDriver(driver);
        response.setVehicle(vehicle);

        return response;
    }
}
