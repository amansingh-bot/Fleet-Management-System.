package com.fleet.driverservice.dto.request;

import com.fleet.driverservice.enums.DriverStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverRequest {

    @NotBlank(message = "Driver name is required")
    private String name;

    @NotBlank(message = "License number is required")
    private String licenseNumber;

    @NotNull(message = "License expiry is required")
    private LocalDate licenseExpiry;

    @NotBlank(message = "Phone Number is required")
    @Pattern(regexp = "^[6-9]\\d{9}$",
            message = "Phone number must be a valid 10 digit Indian mobile number")
    private String phone;

    @NotBlank(message = "Address is required")
    private String address;

    @NotNull(message = "Experience is required")
    @Min(value = 0, message = "Experience cannot be negative")
    private Integer experience;

    private DriverStatus status;


}
