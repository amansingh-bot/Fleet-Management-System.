package com.fleet.driverservice.controller;

import com.fleet.driverservice.dto.request.DriverFilterRequest;
import com.fleet.driverservice.dto.request.DriverRequest;
import com.fleet.driverservice.dto.response.DriverResponse;
import com.fleet.driverservice.entity.Driver;
import com.fleet.driverservice.enums.DriverStatus;
import com.fleet.driverservice.payload.ApiResponse;
import com.fleet.driverservice.service.DriverService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
@RequiredArgsConstructor
public class DriverController {

    private final DriverService driverService;

    @PostMapping
    public ResponseEntity<ApiResponse<DriverResponse>> registerDriver(
            @Valid @RequestBody DriverRequest request) {

        DriverResponse response = driverService.registerDriver(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<DriverResponse>builder()
                        .success(true)
                        .message("Driver Registered Successfully")
                        .data(response)
                        .build());
    }

//    @GetMapping
//    public ResponseEntity<ApiResponse<List<DriverResponse>>> getAllDrivers(){
//
//        List<DriverResponse> responses = driverService.getAllDrivers();
//
//        return ResponseEntity.ok(
//                ApiResponse.<List<DriverResponse>>builder()
//                        .success(true)
//                        .message("Driver fetched successfully")
//                        .data(responses)
//                        .build());
//    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ApiResponse<DriverResponse>> getDriverById(
            @PathVariable Long id){

        DriverResponse response = driverService.getDriverById(id);

        return ResponseEntity.ok(
                ApiResponse.<DriverResponse>builder()
                        .success(true)
                        .message("Driver fetched successfully")
                        .data(response)
                        .build());
    }

    @GetMapping("/{licenseNumber}")
    public ResponseEntity<ApiResponse<DriverResponse>> getDriverByLicenseNumber(
            @PathVariable String licenseNumber){

        DriverResponse response = driverService.getDriverByLicenseNumber(licenseNumber);

        return ResponseEntity.ok(
                ApiResponse.<DriverResponse>builder()
                        .success(true)
                        .message("Driver fetched successfully License number :" + licenseNumber)
                        .data(response)
                        .build());
    }

    @PutMapping("/update/{licenseNumber}")
    public ResponseEntity<ApiResponse<DriverResponse>> updateDriver(
            @PathVariable String licenseNumber,
            @Valid @RequestBody DriverRequest request){

        DriverResponse response = driverService.updateDriver(licenseNumber, request);

        return ResponseEntity.ok(
                ApiResponse.<DriverResponse>builder()
                        .success(true)
                        .message("Driver update successfully : " + licenseNumber)
                        .data(response)
                        .build());
    }

    @DeleteMapping("/{licenseNumber}")
    public ResponseEntity<ApiResponse<DriverResponse>> deleteDriver(
            @PathVariable String licenseNumber){

        driverService.deleteDriver(licenseNumber);

        return ResponseEntity.ok(
                ApiResponse.<DriverResponse>builder()
                        .success(true)
                        .message("Driver delete successfully : " + licenseNumber)
                        .data(null)
                        .build());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<DriverResponse>>> getDriversByStatus(
            @PathVariable DriverStatus status){

        List<DriverResponse> response = driverService.getDriversByStatus(status);

        return ResponseEntity.ok(
                ApiResponse.<List<DriverResponse>>builder()
                        .success(true)
                        .message("Driver fetched successfully")
                        .data(response)
                        .build());
     }

     @GetMapping("/experience/{experience}")
     public ResponseEntity<ApiResponse<List<DriverResponse>>> getDriversByExperience(
             @PathVariable Integer experience){

        List<DriverResponse> response = driverService.getDriversByExperience(experience);

        return ResponseEntity.ok(
                ApiResponse.<List<DriverResponse>>builder()
                        .success(true)
                        .message("Driver fetched successfully")
                        .data(response)
                        .build());
     }

     @GetMapping
     public ResponseEntity<ApiResponse<Page<DriverResponse>>> getAllDrivers(
             @RequestParam(defaultValue = "0") int page,
             @RequestParam(defaultValue = "10") int size,
             @RequestParam(defaultValue = "id") String sortBy,
             @RequestParam(defaultValue = "asc") String direction){

        Page<DriverResponse> response = driverService.getAllDriversWithPaginationAndSorting(
                page, size, sortBy, direction);

        return ResponseEntity.ok(
                ApiResponse.<Page<DriverResponse>>builder()
                        .success(true)
                        .message("Drivers fetched successfully")
                        .data(response)
                        .build());
     }

     @GetMapping("/filter")
     public ResponseEntity<ApiResponse<List<DriverResponse>>> filterDriver (
             @ModelAttribute DriverFilterRequest request){

        List<DriverResponse> response = driverService.filterDriver(request);

        return ResponseEntity.ok(
                ApiResponse.<List<DriverResponse>>builder()
                        .success(true)
                        .message("Drivers fetched successfully")
                        .data(response)
                        .build());
     }
}
