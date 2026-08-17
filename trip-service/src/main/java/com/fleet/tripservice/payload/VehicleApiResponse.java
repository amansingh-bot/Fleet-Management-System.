package com.fleet.tripservice.payload;

import com.fleet.tripservice.dto.response.VehicleResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleApiResponse {

    private boolean success;
    private String message;
    private VehicleResponse data;
}
