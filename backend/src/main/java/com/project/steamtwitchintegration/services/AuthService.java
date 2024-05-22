package com.project.steamtwitchintegration.services;

import com.project.steamtwitchintegration.dto.AuthResponse;
import com.project.steamtwitchintegration.dto.LoginDto;
import com.project.steamtwitchintegration.dto.RegisterDto;
import com.project.steamtwitchintegration.models.User;
import com.project.steamtwitchintegration.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    public AuthResponse register(RegisterDto request) {
        User user = new User(
            request.fullName(),
            request.email(),
            passwordEncoder.encode(request.password()));

        repository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return new AuthResponse(jwtToken);
    }

    public AuthResponse authenticate(LoginDto request) {
        authManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.email(),
                request.password()
            )
        );

        User user = repository.findByEmail(request.email()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        return new AuthResponse(jwtToken);
    }

}
