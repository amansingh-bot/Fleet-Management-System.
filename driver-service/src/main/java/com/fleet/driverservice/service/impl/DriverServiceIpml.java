package com.fleet.driverservice.service.impl;

import com.fleet.driverservice.dto.request.DriverFilterRequest;
import com.fleet.driverservice.dto.request.DriverRequest;
import com.fleet.driverservice.dto.response.DriverResponse;
import com.fleet.driverservice.entity.Driver;
import com.fleet.driverservice.enums.DriverStatus;
import com.fleet.driverservice.exception.DriverAlreadyExistsException;
import com.fleet.driverservice.exception.ResourceNotFoundException;
import com.fleet.driverservice.mapper.DriverMapper;
import com.fleet.driverservice.repository.DriverRepository;
import com.fleet.driverservice.service.DriverService;
import com.fleet.driverservice.specification.DriverSpecification;
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
public class DriverServiceIpml implements DriverService {

    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;

    @Override
    public DriverResponse registerDriver(DriverRequest request) {

        if (driverRepository.existsByLicenseNumber(request.getLicenseNumber())){
            throw new DriverAlreadyExistsException("License Number Already Exists");
        }

        if (driverRepository.existsByPhone((request.getPhone()))){
            throw new DriverAlreadyExistsException("Phone Number Already Exists");
        }

        Driver driver = driverMapper.toEntity(request);

        Driver savedDriver = driverRepository.save(driver);

        return driverMapper.mapToResponse(savedDriver);
    }

//    @Override
//    public List<DriverResponse> getAllDrivers() {
//        List<Driver> drivers = driverRepository.findAll();
//        return drivers.stream()
//                .map(driverMapper::mapToResponse)
//                .toList();
//    }

    @Override
    public DriverResponse getDriverByLicenseNumber(String licenseNumber) {
        Driver driver = getDriverOrThrow(licenseNumber);
        return driverMapper.mapToResponse(driver);
    }

    @Override
    public DriverResponse updateDriver(String licenseNumber, DriverRequest request) {
        Driver driver = getDriverOrThrow(licenseNumber);

        if (!licenseNumber.equals(request.getLicenseNumber())) {
            throw new IllegalArgumentException(
                    "You are updating another driver's license number.");
        }

        if (!driver.getPhone().equals(request.getPhone())
                && driverRepository.existsByPhone(request.getPhone())) {

            throw new DriverAlreadyExistsException("Phone Number Already Exists");
        }

        driver.setName(request.getName());
        driver.setLicenseExpiry(request.getLicenseExpiry());
        driver.setPhone(request.getPhone());
        driver.setAddress(request.getAddress());
        driver.setExperience(request.getExperience());
        driver.setStatus(request.getStatus());

        Driver updatedDriver = driverRepository.save(driver);

        return driverMapper.mapToResponse(updatedDriver);
    }

    @Override
    public void deleteDriver(String licenseNumber) {
        Driver driver = getDriverOrThrow(licenseNumber);
        driverRepository.delete(driver);

    }

    @Override
    public List<DriverResponse> getDriversByStatus(DriverStatus status) {
        List<Driver> drivers = driverRepository.findByStatus(status);

        if(drivers.isEmpty()){
            throw new ResourceNotFoundException(
                    "No drivers found with status: " + status);
        }

        return drivers.stream()
                .map(driverMapper::mapToResponse)
                .toList();
    }

    @Override
    public List<DriverResponse> getDriversByExperience(Integer experience) {

        List<Driver> drivers = driverRepository.findByExperience(experience);

        if (drivers.isEmpty()){
            throw new ResourceNotFoundException(
                    "No drivers found with experience: " + experience);
        }

        return drivers.stream()
                .map(driverMapper::mapToResponse)
                .toList();
    }

    @Override
    public Page<DriverResponse> getAllDriversWithPaginationAndSorting(int page, int size, String sortBy, String direction) {

        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Driver> drivers = driverRepository.findAll(pageable);

        if (drivers.isEmpty()){
            throw new ResourceNotFoundException("No drivers found");
        }

        return drivers.map(driverMapper::mapToResponse);
    }

    @Override
    public List<DriverResponse> filterDriver(DriverFilterRequest request) {

        Specification<Driver> specification = DriverSpecification
                .filterDriver(request);

        List<Driver> drivers = driverRepository.findAll(specification);

        if (drivers.isEmpty()){
            throw new ResourceNotFoundException("No Driver found");
        }

        return drivers.stream()
                .map(driverMapper::mapToResponse)
                .toList();
    }

    private Driver getDriverOrThrow(String licenseNumber){
        return driverRepository.findByLicenseNumber(licenseNumber)
                .orElseThrow(()->
                        new ResourceNotFoundException(
                                "Driver not found with license number : " + licenseNumber));
    }

}
