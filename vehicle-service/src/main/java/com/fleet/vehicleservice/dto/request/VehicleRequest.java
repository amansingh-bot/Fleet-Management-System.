package com.fleet.vehicleservice.dto.request;

import com.fleet.vehicleservice.enums.FuelType;
import com.fleet.vehicleservice.enums.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class VehicleRequest {

    @NotBlank(message = "Vehicle Number is required")
    private String vehicleNumber;

    @NotBlank(message = "Vehicle Name is required")
    private String vehicleName;

    @NotNull(message = "Vehicle Type is required")
    private VehicleType vehicleType;

    @NotBlank(message = "Brand is required")
    private String brand;

    private String model;

    private Integer manufactureYear;

    private String color;

    @NotNull(message = "Fuel Type is required")
    private FuelType fuelType;

    private Integer capacity;

    @NotBlank(message = "Registration Number is required")
    private String registrationNumber;

    private String insuranceNumber;

    private LocalDate insuranceExpiry;

    private LocalDate pollutionExpiry;

}
