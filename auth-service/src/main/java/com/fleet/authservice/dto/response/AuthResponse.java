package com.fleet.authservice.dto.response;

import com.fleet.authservice.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AuthResponse {

    private boolean success;
    private String message;
    private String token;
    private String email;
    private String name;
    private Role role;


}
