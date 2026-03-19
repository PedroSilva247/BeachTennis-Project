package com.example.ProjectBeachTennis.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record LessonResponseDTO(
        UUID id,
        LocalDateTime dateTime,
        boolean isLessonConducted,
        UUID teamId,
        String professorFullName
) {}
