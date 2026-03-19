package com.example.ProjectBeachTennis.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record LessonRequestDTO(
        LocalDateTime dateTime,
        boolean isLessonConducted,
        UUID teamId
) {}
