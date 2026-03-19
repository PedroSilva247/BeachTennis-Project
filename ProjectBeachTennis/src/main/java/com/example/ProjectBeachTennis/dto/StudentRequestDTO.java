package com.example.ProjectBeachTennis.dto;

public record StudentRequestDTO(
        String fullName,
        String email,
        String password,
        String level,
        String cpf,
        String status
) {
}
