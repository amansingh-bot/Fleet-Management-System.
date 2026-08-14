package com.fleet.vehicleservice.dto.response;

import com.fleet.vehicleservice.enums.FuelType;
import com.fleet.vehicleservice.enums.VehicleStatus;
import com.fleet.vehicleservice.enums.VehicleType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class VehicleResponse {

    private Long id;

    private String vehicleNumber;

    private String vehicleName;

    private VehicleType vehicleType;

    private String brand;

    private String model;

    private Integer manufactureYear;

    private String color;

    private FuelType fuelType;

    private Integer capacity;

    private VehicleStatus status;

    private String registrationNumber;

    private String insuranceNumber;

    private LocalDate insuranceExpiry;

    private LocalDate pollutionExpiry;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
