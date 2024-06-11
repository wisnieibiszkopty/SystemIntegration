package com.project.steamtwitchintegration.services;

import com.project.steamtwitchintegration.dto.AuthenticationRequest;
import com.project.steamtwitchintegration.dto.AuthenticationResponse;
import com.project.steamtwitchintegration.dto.RegisterRequest;
import com.project.steamtwitchintegration.dto.Role;
import com.project.steamtwitchintegration.exceptions.UserAlreadyExistsException;
import com.project.steamtwitchintegration.models.User;
import com.project.steamtwitchintegration.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    public AuthenticationResponse register(RegisterRequest request) {
        Optional<User> existingUser = repository.findByEmail(request.getEmail());
        if(existingUser.isPresent()){
            throw new UserAlreadyExistsException("User with this email already exists");
        }

        User user = User
            .builder()
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

