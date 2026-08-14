package com.fleet.vehicleservice.controller;

import com.fleet.vehicleservice.dto.request.VehicleFilterRequest;
import com.fleet.vehicleservice.dto.request.VehicleRequest;
import com.fleet.vehicleservice.dto.response.VehicleResponse;
import com.fleet.vehicleservice.enums.FuelType;
import com.fleet.vehicleservice.enums.VehicleStatus;
import com.fleet.vehicleservice.enums.VehicleType;
import com.fleet.vehicleservice.payload.ApiResponse;
import com.fleet.vehicleservice.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<ApiResponse<VehicleResponse>> registerVehicle(
            @Valid @RequestBody VehicleRequest request) {

        VehicleResponse response = vehicleService.registerVehicle(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<VehicleResponse>builder()
                        .success(true)
                        .message("Vehicle Registered Successfully")
                        .data(response)
                        .build());
    }

//    @GetMapping
//    public ResponseEntity<ApiResponse<List<VehicleResponse>>> getAllVehicles() {
//
//        List<VehicleResponse> response = vehicleService.getAllVehicles();
//
//        return ResponseEntity.ok(
//                ApiResponse.<List<VehicleResponse>>builder()
//                        .success(true)
//                        .message("Vehicles fetched successfully")
//                        .data(response)
//                        .build()
//        );
//    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<VehicleResponse>> getVehicleById(
            @PathVariable Long id) {

        VehicleResponse response = vehicleService.getVehicleById(id);

        return ResponseEntity.ok(
                ApiResponse.<VehicleResponse>builder()
                        .success(true)
                        .message("Vehicle fetched successfully")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<VehicleResponse>> getVehicleByVehicleNumber(
            @RequestParam String vehicleNumber){

        VehicleResponse response = vehicleService
                .getVehicleByVehicleNumber(vehicleNumber);

        return ResponseEntity.ok(
                ApiResponse.<VehicleResponse>builder()
                        .success(true)
                        .message("Vehicle fetched successfully")
                        .data(response)
                        .build());
    }

    @PutMapping("/update/{vehicleNumber}")
    public ResponseEntity<ApiResponse<VehicleResponse>> updateVehicle(
            @PathVariable String vehicleNumber,
            @Valid @RequestBody VehicleRequest request){

        VehicleResponse response = vehicleService.updateVehicle(vehicleNumber,request);

        return ResponseEntity.ok(
                ApiResponse.<VehicleResponse>builder()
                        .success(true)
                        .message("Vehicle Updated Successfully")
                        .data(response)
                        .build());
    }

    @DeleteMapping("/{vehicleNumber}")
    public ResponseEntity<ApiResponse<VehicleResponse>> deleteVehicle(
            @PathVariable String vehicleNumber){

        vehicleService.deleteVehicle(vehicleNumber);
        return ResponseEntity.ok(
                ApiResponse.<VehicleResponse>builder()
                        .success(true)
                        .message("Vehicle Delete Successfully " +vehicleNumber)
                        .data(null)
                        .build());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<VehicleResponse>>> getVehiclesByStatus(
            @PathVariable VehicleStatus status) {

        List<VehicleResponse> response =
                vehicleService.getVehiclesByStatus(status);

        return ResponseEntity.ok(
                ApiResponse.<List<VehicleResponse>>builder()
                        .success(true)
                        .message("Vehicles fetched successfully")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/type/{vehicleType}")
    public ResponseEntity<ApiResponse<List<VehicleResponse>>> getVehiclesByType(
            @PathVariable VehicleType vehicleType){

        List<VehicleResponse> response = vehicleService
                .getVehiclesByType(vehicleType);

        return ResponseEntity.ok(
                ApiResponse.<List<VehicleResponse>>builder()
                        .success(true)
                        .message("Vehicle fetched successfully")
                        .data(response)
                        .build());
    }

    @GetMapping("/fuel/{fuelType}")
    public ResponseEntity<ApiResponse<List<VehicleResponse>>> getVehiclesByFuelType(
            @PathVariable FuelType fuelType){

        List<VehicleResponse> response = vehicleService
                .getVehiclesByFuelType(fuelType);

        return ResponseEntity.ok(
                ApiResponse.<List<VehicleResponse>>builder()
                        .success(true)
                        .message("Vehicles fetched successfully")
                        .data(response)
                        .build());

    }

    @GetMapping("/brand/{brand}")
    public ResponseEntity<ApiResponse<List<VehicleResponse>>> getVehiclesByBrand(
            @PathVariable String brand) {

        List<VehicleResponse> response =
                vehicleService.getVehiclesByBrand(brand);

        return ResponseEntity.ok(
                ApiResponse.<List<VehicleResponse>>builder()
                        .success(true)
                        .message("Vehicles fetched successfully")
                        .data(response)
                        .build());
    }


    @GetMapping
    public ResponseEntity<ApiResponse<Page<VehicleResponse>>> getAllVehicles(

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction){

        Page<VehicleResponse> response = vehicleService.getAllVehiclesWithPagingAndSorting(page, size, sortBy, direction);

        return ResponseEntity.ok(
                ApiResponse.<Page<VehicleResponse>>builder()
                        .success(true)
                        .message("Vehicles fetched successfully")
                        .data(response)
                        .build());
    }

    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<List<VehicleResponse>>> filterVehicles(
            @ModelAttribute VehicleFilterRequest request){

        List<VehicleResponse> response = vehicleService.filterVehicles(request);

        return ResponseEntity.ok(
                ApiResponse.<List<VehicleResponse>>builder()
                        .success(true)
                        .message("Vehicles fetched successfully")
                        .data(response)
                        .build());
    }


}
