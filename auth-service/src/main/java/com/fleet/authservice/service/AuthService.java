package com.fleet.authservice.service;

import com.fleet.authservice.dto.request.LoginRequest;
import com.fleet.authservice.dto.request.RegisterRequest;
import com.fleet.authservice.dto.response.AuthResponse;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}
