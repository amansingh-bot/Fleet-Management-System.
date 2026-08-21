package com.fleet.tripservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TripDetailResponse {

    private Long tripId;

    private Long driverId;
    private DriverResponse driver;

    private Long vehicleId;
    private VehicleResponse vehicle;

    private Map<String, String> errors;
}
