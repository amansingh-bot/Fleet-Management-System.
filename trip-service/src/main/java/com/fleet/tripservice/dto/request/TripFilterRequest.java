package com.fleet.tripservice.dto.request;


import com.fleet.tripservice.enums.TripStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TripFilterRequest {

    private Long driverId;

    private Long vehicleId;

    private TripStatus status;

    private String startLocation;

    private String endLocation;
}