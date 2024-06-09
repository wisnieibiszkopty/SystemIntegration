package com.project.steamtwitchintegration.controllers;

import com.project.steamtwitchintegration.dto.AuthenticationRequest;
import com.project.steamtwitchintegration.dto.AuthenticationResponse;
import com.project.steamtwitchintegration.dto.RegisterRequest;
import com.project.steamtwitchintegration.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authorization", description = "Endpoints for handling authorization")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Create user based on received data and return jwt token")
    @ApiResponse(responseCode = "200", content = {
        @Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationResponse.class))
    })
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid RegisterRequest request){
        return ResponseEntity.ok(authService.register(request));
    }

    @Operation(summary = "Receive jwt based on existing user data")
    @ApiResponse(responseCode = "200", content = {
        @Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationResponse.class))
    })
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid AuthenticationRequest request){
        return ResponseEntity.ok(authService.authenticate(request));
    }

}
