package com.example.ProjectBeachTennis.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record LessonWithStudentsDTO (
    UUID lessonId,
    LocalDateTime dateTimeLesson,
    boolean isConducted,
    String teamLevel,
    List<StudentPresenceDTO> presentStudents
) {}
