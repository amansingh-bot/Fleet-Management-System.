package com.fleet.tripservice.dto.response;

import com.fleet.tripservice.enums.TripStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TripResponse {

    private Long id;

    private Long driverId;

    private Long vehicleId;

    private String startLocation;

    private String endLocation;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private TripStatus status;

    private Double distance;
}
