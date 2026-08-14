package com.fleet.vehicleservice.service;

import com.fleet.vehicleservice.dto.request.VehicleFilterRequest;
import com.fleet.vehicleservice.dto.request.VehicleRequest;
import com.fleet.vehicleservice.dto.response.VehicleResponse;
import com.fleet.vehicleservice.entity.Vehicle;
import com.fleet.vehicleservice.enums.FuelType;
import com.fleet.vehicleservice.enums.VehicleStatus;
import com.fleet.vehicleservice.enums.VehicleType;
import org.springframework.data.domain.Page;
import java.util.List;


public interface VehicleService {

    VehicleResponse registerVehicle(VehicleRequest request);

//    List<VehicleResponse> getAllVehicles();

    VehicleResponse getVehicleById(Long id);

    VehicleResponse getVehicleByVehicleNumber(String vehicleNumber);

    VehicleResponse updateVehicle(String vehicleNumber, VehicleRequest request);

    void deleteVehicle(String vehicleNumber);

    List<VehicleResponse> getVehiclesByStatus(VehicleStatus status);

    List<VehicleResponse> getVehiclesByType(VehicleType vehicleType);

    List<VehicleResponse> getVehiclesByFuelType(FuelType fuelType);

    List<VehicleResponse> getVehiclesByBrand(String brand);

    Page<VehicleResponse> getAllVehiclesWithPagingAndSorting(int page, int size, String sortBy, String direction);

    List<VehicleResponse> filterVehicles(VehicleFilterRequest request);
}
