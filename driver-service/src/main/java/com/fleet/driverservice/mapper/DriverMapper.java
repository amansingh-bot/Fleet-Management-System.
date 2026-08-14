package com.fleet.driverservice.mapper;

import com.fleet.driverservice.dto.request.DriverRequest;
import com.fleet.driverservice.dto.response.DriverResponse;
import com.fleet.driverservice.entity.Driver;
import org.springframework.stereotype.Component;

@Component
public class DriverMapper {


    public Driver toEntity(DriverRequest request) {

        return Driver.builder()
                .name(request.getName())
                .licenseNumber(request.getLicenseNumber())
                .licenseExpiry(request.getLicenseExpiry())
                .phone(request.getPhone())
                .address(request.getAddress())
                .experience(request.getExperience())
                .status(request.getStatus())
                .build();
    }

    public DriverResponse mapToResponse(Driver driver) {

        return DriverResponse.builder()
                .id(driver.getId())
                .name(driver.getName())
                .licenseNumber(driver.getLicenseNumber())
                .licenseExpiry(driver.getLicenseExpiry())
                .phone(driver.getPhone())
                .address(driver.getAddress())
                .experience(driver.getExperience())
                .status(driver.getStatus())
                .createdAt(driver.getCreatedAt())
                .updatedAt(driver.getUpdatedAt())
                .build();
    }
}
