package com.fleet.tripservice.repository;

import com.fleet.tripservice.entity.Trip;
import com.fleet.tripservice.enums.TripStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long>, JpaSpecificationExecutor<Trip> {

    List<Trip> findByDriverId(Long driverId);

    List<Trip> findByVehicleId(Long vehicleId);

    List<Trip> findByStatus(TripStatus status);

    boolean existsByDriverIdAndStatusIn(
            Long driverId, List<TripStatus> statuses);

    boolean existsByVehicleIdAndStatusIn(
            Long vehicleId, List<TripStatus> statuses);

    boolean existsByDriverIdAndStatusInAndIdNot(
            Long driverId, List<TripStatus> statuses, Long id);

    boolean existsByVehicleIdAndStatusInAndIdNot(
            Long vehicleId, List<TripStatus> statuses, Long id);
}
