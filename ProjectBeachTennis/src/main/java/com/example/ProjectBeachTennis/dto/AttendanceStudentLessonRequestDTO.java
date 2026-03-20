package com.example.ProjectBeachTennis.dto;

import java.util.UUID;

public record AttendanceStudentLessonRequestDTO (
        UUID lessonId,
        UUID studentId,
        boolean isPresent,
        String attendanceType
) {}
