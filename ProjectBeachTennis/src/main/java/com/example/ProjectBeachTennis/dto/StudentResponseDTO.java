package com.example.ProjectBeachTennis.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record StudentResponseDTO  (
        UUID id,
        String fullName,
        String email,
        String level,
        String status,
        LocalDateTime startAt
) {}
