package com.fleet.driverservice.specification;

import com.fleet.driverservice.dto.request.DriverFilterRequest;
import com.fleet.driverservice.entity.Driver;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class DriverSpecification {

    public static Specification<Driver> filterDriver(DriverFilterRequest request){

        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (request.getStatus() != null){
                predicates.add(
                        criteriaBuilder.equal(root.get("status"), request.getStatus()));
            }

            if (request.getExperience() != null){
                predicates.add(
                        criteriaBuilder.equal(root.get("experience"), request.getExperience()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
