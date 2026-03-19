package com.example.ProjectBeachTennis.dto;

public record ProfessorRequestDTO(
    String fullName,
    String email,
    String password,
    String cpf,
    String status
) {}

