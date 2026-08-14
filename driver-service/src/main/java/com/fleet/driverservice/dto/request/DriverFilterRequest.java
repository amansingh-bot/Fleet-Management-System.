package com.fleet.driverservice.dto.request;

import com.fleet.driverservice.enums.DriverStatus;
import lombok.Data;

@Data
public class DriverFilterRequest {

    private DriverStatus status;

    private Integer experience;
}
