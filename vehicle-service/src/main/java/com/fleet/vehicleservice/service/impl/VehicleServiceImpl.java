package com.fleet.vehicleservice.service.impl;

import com.fleet.vehicleservice.dto.request.VehicleFilterRequest;
import com.fleet.vehicleservice.dto.request.VehicleRequest;
import com.fleet.vehicleservice.dto.response.VehicleResponse;
import com.fleet.vehicleservice.entity.Vehicle;
import com.fleet.vehicleservice.enums.FuelType;
import com.fleet.vehicleservice.enums.VehicleStatus;
import com.fleet.vehicleservice.enums.VehicleType;
import com.fleet.vehicleservice.exception.ResourceNotFoundException;
import com.fleet.vehicleservice.exception.VehicleAlreadyExistsException;
import com.fleet.vehicleservice.mapper.VehicleMapper;
import com.fleet.vehicleservice.repository.VehicleRepository;
import com.fleet.vehicleservice.service.VehicleService;
import com.fleet.vehicleservice.specification.VehicleSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService{

    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;


    @Override
    public VehicleResponse registerVehicle(VehicleRequest request) {

        if (vehicleRepository.existsByVehicleNumber(request.getVehicleNumber())) {
            throw new VehicleAlreadyExistsException("Vehicle Number Already Exists");
        }

        Vehicle vehicle = vehicleMapper.toEntity(request);

        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        return vehicleMapper.mapToResponse(savedVehicle);

    }


//    @Override
//    public List<VehicleResponse> getAllVehicles() {
//
//        List<Vehicle> vehicles = vehicleRepository.findAll();
//
//        return vehicles.stream()
//                .map(vehicleMapper::mapToResponse)
//                .toList();
//    }


    @Override
    public VehicleResponse getVehicleById(Long id) {

        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Vehicle not found with id : " + id));

        return vehicleMapper.mapToResponse(vehicle);
    }


    @Override
    public VehicleResponse getVehicleByVehicleNumber(String vehicleNumber) {
        Vehicle vehicle = findVehicleByNumber(vehicleNumber);

        return vehicleMapper.mapToResponse(vehicle);
    }


    @Override
    public VehicleResponse updateVehicle(String vehicleNumber, VehicleRequest request) {
        Vehicle vehicle = findVehicleByNumber(vehicleNumber);

        if(!vehicle.getVehicleNumber().equals(request.getVehicleNumber())
                && vehicleRepository.existsByVehicleNumber(request.getVehicleNumber())){
            throw new VehicleAlreadyExistsException(
                    "Cannot update. Another vehicle is already registered with vehicle number: "
                            + request.getVehicleNumber());
        }

        vehicle.setVehicleNumber(request.getVehicleNumber());
        vehicle.setVehicleName(request.getVehicleName());
        vehicle.setVehicleType(request.getVehicleType());
        vehicle.setBrand(request.getBrand());
        vehicle.setModel(request.getModel());
        vehicle.setManufactureYear(request.getManufactureYear());
        vehicle.setColor(request.getColor());
        vehicle.setFuelType(request.getFuelType());
        vehicle.setCapacity(request.getCapacity());
        vehicle.setRegistrationNumber(request.getRegistrationNumber());
        vehicle.setInsuranceNumber(request.getInsuranceNumber());
        vehicle.setInsuranceExpiry(request.getInsuranceExpiry());
        vehicle.setPollutionExpiry(request.getPollutionExpiry());

        return vehicleMapper.mapToResponse(vehicleRepository.save(vehicle));


    }


    @Override
    public void deleteVehicle(String vehicleNumber) {
        Vehicle vehicle = findVehicleByNumber(vehicleNumber);

        vehicleRepository.delete(vehicle);
    }


    @Override
    public List<VehicleResponse> getVehiclesByStatus(VehicleStatus status) {

        List<Vehicle> vehicles = vehicleRepository.findByStatus(status);

        if (vehicles.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No vehicles found with status: " + status);
        }

        return vehicles.stream()
                .map(vehicleMapper::mapToResponse)
                .toList();
    }


    @Override
    public List<VehicleResponse> getVehiclesByType(VehicleType vehicleType) {
        List<Vehicle> vehicles = vehicleRepository.findByVehicleType(vehicleType);

        if (vehicles.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No vehicles found with type: " + vehicleType);
        }

        return vehicles.stream()
                .map(vehicleMapper::mapToResponse)
                .toList();
    }


    @Override
    public List<VehicleResponse> getVehiclesByFuelType(FuelType fuelType) {
        List<Vehicle> vehicles = vehicleRepository.findByFuelType(fuelType);

        if (vehicles.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No vehicles found with fuel type: " + fuelType);
        }

        return vehicles.stream()
                .map(vehicleMapper::mapToResponse)
                .toList();
    }


    @Override
    public List<VehicleResponse> getVehiclesByBrand(String brand) {
        List<Vehicle> vehicles = vehicleRepository.findByBrandIgnoreCase(brand);

        if(vehicles.isEmpty()){
            throw new ResourceNotFoundException("No vehicles found with brand: " + brand);
        }

        return vehicles.stream()
                .map(vehicleMapper::mapToResponse)
                .toList();
    }


    @Override
    public Page<VehicleResponse> getAllVehiclesWithPagingAndSorting(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Vehicle> vehicles = vehicleRepository.findAll(pageable);

        return vehicles.map(vehicleMapper::mapToResponse);
    }


    @Override
    public List<VehicleResponse> filterVehicles(VehicleFilterRequest request) {

        Specification<Vehicle> specification = VehicleSpecification
                .filterVehicles(request);

        List<Vehicle> vehicles = vehicleRepository.findAll(specification);
        if (vehicles.isEmpty()){
            throw new ResourceNotFoundException("No Vehicles found");
        }

        return vehicles.stream()
                .map(vehicleMapper::mapToResponse)
                .toList();
    }


    private Vehicle findVehicleByNumber(String vehicleNumber) {
        return vehicleRepository.findByVehicleNumber(vehicleNumber)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Vehicle not found with number : " + vehicleNumber));
    }



}

