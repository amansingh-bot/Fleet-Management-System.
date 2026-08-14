package com.fleet.vehicleservice.specification;

import com.fleet.vehicleservice.dto.request.VehicleFilterRequest;
import com.fleet.vehicleservice.entity.Vehicle;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class VehicleSpecification {

    public static Specification<Vehicle> filterVehicles(VehicleFilterRequest request) {

        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (request.getStatus() != null) {
                predicates.add(
                        criteriaBuilder.equal(root.get("status"), request.getStatus())
                );
            }

            if (request.getVehicleType() != null) {
                predicates.add(
                        criteriaBuilder.equal(root.get("vehicleType"), request.getVehicleType())
                );
            }

            if (request.getFuelType() != null) {
                predicates.add(
                        criteriaBuilder.equal(root.get("fuelType"), request.getFuelType())
                );
            }

            if (request.getBrand() != null && !request.getBrand().isBlank()) {
                predicates.add(
                        criteriaBuilder.equal(root.get("brand"), request.getBrand())
                );
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
