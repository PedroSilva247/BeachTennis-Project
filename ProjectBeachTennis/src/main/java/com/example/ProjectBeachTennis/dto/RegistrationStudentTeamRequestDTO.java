package com.example.ProjectBeachTennis.dto;

import java.util.UUID;

public record RegistrationStudentTeamRequestDTO (
        UUID studentId,
        UUID teamId
) {}
