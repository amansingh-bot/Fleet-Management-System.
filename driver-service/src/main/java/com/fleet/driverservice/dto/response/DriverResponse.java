package com.fleet.driverservice.dto.response;

import com.fleet.driverservice.enums.DriverStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverResponse {

    private Long id;

    private  String name;

    private String licenseNumber;

    private LocalDate licenseExpiry;

    private String phone;

    private String address;

    private Integer experience;

    private DriverStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
