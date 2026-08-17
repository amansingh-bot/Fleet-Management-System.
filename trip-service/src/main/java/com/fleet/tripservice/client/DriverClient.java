package com.fleet.tripservice.client;

import com.fleet.tripservice.payload.DriverApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "driver-service")
public interface DriverClient {

    @GetMapping("/api/drivers/id/{id}")
    DriverApiResponse getDriverById(@PathVariable Long id);
}
