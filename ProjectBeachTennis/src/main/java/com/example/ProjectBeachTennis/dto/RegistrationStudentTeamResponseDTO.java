package com.example.ProjectBeachTennis.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record RegistrationStudentTeamResponseDTO (
        UUID id,
        UUID studentId,
        UUID teamId,
        LocalDateTime startAt
) {}
