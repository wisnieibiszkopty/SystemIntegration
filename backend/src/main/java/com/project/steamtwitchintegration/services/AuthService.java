package com.project.steamtwitchintegration.services;

import com.project.steamtwitchintegration.dto.AuthenticationRequest;
import com.project.steamtwitchintegration.dto.AuthenticationResponse;
import com.project.steamtwitchintegration.dto.RegisterRequest;
import com.project.steamtwitchintegration.dto.Role;
import com.project.steamtwitchintegration.models.User;
import com.project.steamtwitchintegration.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    // jak daje z tokenem żądania to sie wysypuje
    // o co ci chodzi
    public AuthenticationResponse register(RegisterRequest request) {
        User user = User
            .builder()
            //.firstname(request.getFirstname())
            //.lastname(request.getLastname())
            .fullname(request.getFullname())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(Role.USER)
            .build();

        repository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );

        User user = repository.findByEmail(request.getEmail()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }
}
