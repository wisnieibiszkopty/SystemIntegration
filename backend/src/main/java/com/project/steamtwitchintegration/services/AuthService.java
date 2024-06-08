package com.project.steamtwitchintegration.services;

import com.project.steamtwitchintegration.dto.AuthResponse;
import com.project.steamtwitchintegration.dto.LoginDto;
import com.project.steamtwitchintegration.dto.RegisterDto;
import com.project.steamtwitchintegration.exceptions.UserDoesntExistException;
import com.project.steamtwitchintegration.exceptions.UserExistsException;
import com.project.steamtwitchintegration.models.User;
import com.project.steamtwitchintegration.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class AuthService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    public AuthResponse register(RegisterDto request) {
        Optional<User> existingUser = repository.findByEmail(request.email());
        if(existingUser.isPresent()){
            throw new UserExistsException("User with this email already exists");
        }

        existingUser = repository.findByFullName(request.fullName());
        if(existingUser.isPresent()){
            throw new UserExistsException("User with this name already exists");
        }

        User user = new User(
            request.fullName(),
            request.email(),
            passwordEncoder.encode(request.password()));

        repository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return new AuthResponse(jwtToken);
    }

    public AuthResponse authenticate(LoginDto request) {
        log.info("jazda jazda");

        Authentication auth = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.email(),
                request.password()
            )
        );

        System.out.println(auth);

        log.info("koniec jazdy");

        Optional<User> user = repository.findByEmail(request.email());
        if(user.isEmpty()){
            throw new UserDoesntExistException("Invalid user data");
        }

        String jwtToken = jwtService.generateToken(user.get());
        return new AuthResponse(jwtToken);
    }

}
