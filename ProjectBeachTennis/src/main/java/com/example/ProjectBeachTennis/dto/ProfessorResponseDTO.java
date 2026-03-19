package com.example.ProjectBeachTennis.dto;


import java.time.LocalDateTime;
import java.util.UUID;


public record ProfessorResponseDTO (
    UUID id,
    String fullName,
    String email,
    String status,
    LocalDateTime startAt
) {}
