package com.fleet.vehicleservice.mapper;

import com.fleet.vehicleservice.dto.request.VehicleRequest;
import com.fleet.vehicleservice.dto.response.VehicleResponse;
import com.fleet.vehicleservice.entity.Vehicle;
import org.springframework.stereotype.Component;

@Component
public class VehicleMapper {


    public Vehicle toEntity(VehicleRequest request){
        return Vehicle.builder()
                .vehicleName(request.getVehicleName())
                .vehicleNumber(request.getVehicleNumber())
                .vehicleType(request.getVehicleType())
                .brand(request.getBrand())
                .model(request.getModel())
                .manufactureYear(request.getManufactureYear())
                .color(request.getColor())
                .fuelType(request.getFuelType())
                .capacity(request.getCapacity())
                .registrationNumber(request.getRegistrationNumber())
                .insuranceNumber(request.getInsuranceNumber())
                .insuranceExpiry((request.getInsuranceExpiry()))
                .pollutionExpiry(request.getPollutionExpiry())
                .build();
    }

    public VehicleResponse mapToResponse(Vehicle vehicle) {

        return VehicleResponse.builder()
                .id(vehicle.getId())
                .vehicleNumber(vehicle.getVehicleNumber())
                .vehicleName(vehicle.getVehicleName())
                .vehicleType(vehicle.getVehicleType())
                .brand(vehicle.getBrand())
                .model(vehicle.getModel())
                .manufactureYear(vehicle.getManufactureYear())
                .color(vehicle.getColor())
                .fuelType(vehicle.getFuelType())
                .capacity(vehicle.getCapacity())
                .status(vehicle.getStatus())
                .registrationNumber(vehicle.getRegistrationNumber())
                .insuranceNumber(vehicle.getInsuranceNumber())
                .insuranceExpiry(vehicle.getInsuranceExpiry())
                .pollutionExpiry(vehicle.getPollutionExpiry())
                .createdAt(vehicle.getCreatedAt())
                .updatedAt(vehicle.getUpdatedAt())
                .build();
    }
}
