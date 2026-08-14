package com.fleet.authservice.service.impl;

import com.fleet.authservice.dto.request.LoginRequest;
import com.fleet.authservice.dto.request.RegisterRequest;
import com.fleet.authservice.dto.response.AuthResponse;
import com.fleet.authservice.entity.User;
import com.fleet.authservice.exception.EmailAlreadyExistsException;
import com.fleet.authservice.exception.InvalidCredentialsException;
import com.fleet.authservice.repository.UserRepository;
import com.fleet.authservice.security.JwtService;
import com.fleet.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.config.client.validation.InvalidApplicationNameException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse register (RegisterRequest request){
        if(userRepository.existsByEmail(request.getEmail())){
            throw new EmailAlreadyExistsException("Email already exists");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        userRepository.save(user);

        return AuthResponse.builder()
                .message("User Register Successfully")
                .token(null)
                .build();
    }

    @Override
    public AuthResponse login (LoginRequest request){
        User user =userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new InvalidCredentialsException("Invalid Email or Password"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new InvalidCredentialsException("Invalid Email or Password");
        }

        String token = jwtService.generateToken(user);
        return AuthResponse.builder()
                .success(true)
                .message("Login Successfully")
                .token(token)
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

}
