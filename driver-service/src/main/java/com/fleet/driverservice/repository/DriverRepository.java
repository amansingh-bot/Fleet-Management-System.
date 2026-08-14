package com.fleet.driverservice.repository;

import com.fleet.driverservice.entity.Driver;
import com.fleet.driverservice.enums.DriverStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface DriverRepository extends JpaRepository <Driver, Long>, JpaSpecificationExecutor<Driver> {

    boolean existsByLicenseNumber(String licenseNumber);

    boolean existsByPhone(String phone);

    Optional<Driver> findByLicenseNumber(String licenseNumber);

    List<Driver> findByStatus(DriverStatus status);

    void deleteByLicenseNumber(String licenseNumber);

    List<Driver> findByExperience(Integer experience);
}
