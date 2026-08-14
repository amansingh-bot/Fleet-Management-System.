package com.fleet.vehicleservice.repository;

import com.fleet.vehicleservice.entity.Vehicle;
import com.fleet.vehicleservice.enums.FuelType;
import com.fleet.vehicleservice.enums.VehicleStatus;
import com.fleet.vehicleservice.enums.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle,Long>, JpaSpecificationExecutor<Vehicle> {

    Optional<Vehicle> findByVehicleNumber(String vehiclenumber);

    List<Vehicle> findByStatus(VehicleStatus status);

    List<Vehicle> findByVehicleType(VehicleType vehicleType);

    List<Vehicle> findByFuelType(FuelType fuelType);

    boolean existsByVehicleNumber(String vehicleNumber);

    List<Vehicle> findByBrandIgnoreCase(String brand);
}
