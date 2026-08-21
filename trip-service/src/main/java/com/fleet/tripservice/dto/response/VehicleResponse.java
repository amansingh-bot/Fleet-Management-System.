package com.fleet.tripservice.dto.response;

import com.fleet.tripservice.enums.VehicleStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleResponse {

    private Long id;
    private String vehicleNumber;
    private String vehicleType;
    private VehicleStatus status;
}
