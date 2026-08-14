package com.fleet.tripservice.dto.request;

import com.fleet.tripservice.enums.TripStatus;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TripRequest {

    @NotNull(message = "Driver Id is required")
    private Long driverId;

    @NotNull(message = "Vehicle Id is required")
    private Long vehicleId;

    @NotNull(message = "Start Location is required")
    private String startLocation;

    @NotNull(message = "End Location is required")
    private String endLocation;

    @NotNull(message = "Start Time is required")
    private LocalDateTime startTime;

    @NotNull(message = "Status is required")
    private TripStatus status;

    @NotNull(message = "Distance is required")
    private Double distance;
}
