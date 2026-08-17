package com.fleet.tripservice.payload;

import com.fleet.tripservice.dto.response.DriverResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DriverApiResponse {

    private boolean success;
    private String message;
    private DriverResponse data;
}
