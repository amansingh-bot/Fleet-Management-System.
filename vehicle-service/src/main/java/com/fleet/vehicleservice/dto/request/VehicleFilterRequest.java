package com.fleet.vehicleservice.dto.request;

import com.fleet.vehicleservice.enums.FuelType;
import com.fleet.vehicleservice.enums.VehicleStatus;
import com.fleet.vehicleservice.enums.VehicleType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleFilterRequest {

    private VehicleStatus status;

    private VehicleType vehicleType;

    private FuelType fuelType;

    private String brand;
}
