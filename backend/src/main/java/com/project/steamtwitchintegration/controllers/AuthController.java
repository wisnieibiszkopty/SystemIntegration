package com.project.steamtwitchintegration.controllers;

import com.project.steamtwitchintegration.dto.AuthResponse;
import com.project.steamtwitchintegration.dto.LoginDto;
import com.project.steamtwitchintegration.dto.RegisterDto;
import com.project.steamtwitchintegration.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterDto request){
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginDto request){
        return ResponseEntity.ok(authService.authenticate(request));
    }

}
