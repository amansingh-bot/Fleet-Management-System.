package com.fleet.tripservice.specification;

import com.fleet.tripservice.dto.request.TripFilterRequest;
import com.fleet.tripservice.entity.Trip;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TripSpecification {

    public static Specification<Trip> filterTrip(TripFilterRequest request) {

        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (request.getDriverId() != null) {
                predicates.add(
                        criteriaBuilder.equal(root.get("driverId"), request.getDriverId())
                );
            }

            if (request.getVehicleId() != null) {
                predicates.add(
                        criteriaBuilder.equal(root.get("vehicleId"), request.getVehicleId())
                );
            }

            if (request.getStatus() != null) {
                predicates.add(
                        criteriaBuilder.equal(root.get("status"), request.getStatus())
                );
            }

            if (request.getStartLocation() != null &&
                    !request.getStartLocation().isBlank()) {

                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("startLocation")),
                                "%" + request.getStartLocation().toLowerCase() + "%"
                        )
                );
            }

            if (request.getEndLocation() != null &&
                    !request.getEndLocation().isBlank()) {

                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("endLocation")),
                                "%" + request.getEndLocation().toLowerCase() + "%"
                        )
                );
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
