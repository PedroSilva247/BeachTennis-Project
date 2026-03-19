package com.example.ProjectBeachTennis.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public record TeamResponseDTO(
        UUID id,
        int dayOfWeek,
        LocalTime time,
        String level,
        String professorName,
        UUID professorId,
        LocalDateTime startAt
) {}
