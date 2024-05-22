package com.project.steamtwitchintegration.dto;

public record RegisterDto(
        String fullName,
        String email,
        String password
) {}
