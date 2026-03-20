package com.example.ProjectBeachTennis.dto;

import java.util.UUID;

public record StudentPresenceDTO (
    UUID studentId,
    String studentFullName,
    String attendanceType
) {}
