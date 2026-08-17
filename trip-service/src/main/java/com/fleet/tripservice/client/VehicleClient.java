package com.fleet.tripservice.client;

import com.fleet.tripservice.payload.VehicleApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "vehicle-service")
public interface VehicleClient {

    @GetMapping("/api/vehicles/id/{id}")
    VehicleApiResponse getVehicleById(@PathVariable Long id);
}
