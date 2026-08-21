package com.fleet.tripservice.dto.response;

import com.fleet.tripservice.enums.DriverStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DriverResponse {

    private Long id;
    private String name;
    private String licenseNumber;
    private String phone;
    private DriverStatus status;
}
