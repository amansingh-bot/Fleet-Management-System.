package com.fleet.authservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
public class ErrorResponse {

    private boolean success;

    private int status;

    private String message;

    private LocalDateTime timestamp;
}
