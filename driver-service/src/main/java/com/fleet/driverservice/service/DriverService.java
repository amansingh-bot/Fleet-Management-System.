package com.fleet.driverservice.service;

import com.fleet.driverservice.dto.request.DriverFilterRequest;
import com.fleet.driverservice.dto.request.DriverRequest;
import com.fleet.driverservice.dto.response.DriverResponse;
import com.fleet.driverservice.entity.Driver;
import com.fleet.driverservice.enums.DriverStatus;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DriverService {

    DriverResponse registerDriver(DriverRequest request);

//    List<DriverResponse> getAllDrivers();

    DriverResponse getDriverById(Long id);

    DriverResponse getDriverByLicenseNumber(String licenseNumber);

    DriverResponse updateDriver(String licenseNumber, DriverRequest request);

    void deleteDriver(String licenseNumber);

    List<DriverResponse> getDriversByStatus(DriverStatus status);

    List<DriverResponse> getDriversByExperience(Integer experience);

    Page<DriverResponse> getAllDriversWithPaginationAndSorting(
            int page, int size, String sortBy, String direction);

    List<DriverResponse> filterDriver (DriverFilterRequest request);
}
