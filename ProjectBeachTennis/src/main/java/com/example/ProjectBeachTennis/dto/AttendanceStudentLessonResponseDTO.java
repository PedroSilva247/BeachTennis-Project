package com.example.ProjectBeachTennis.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record AttendanceStudentLessonResponseDTO (
        UUID id,
        UUID lessonId,
        String studentName,
        String professorName,
        LocalDateTime lessonDate,
        boolean isConducted,
        boolean isPresent,
        String attendanceType
) {}
