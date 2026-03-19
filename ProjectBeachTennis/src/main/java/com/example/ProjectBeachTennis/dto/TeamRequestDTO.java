package com.example.ProjectBeachTennis.dto;

import com.example.ProjectBeachTennis.model.Professor;

import java.time.LocalTime;
import java.util.UUID;

public record TeamRequestDTO(
        int dayOfWeek,
        LocalTime time,
        String level,
        UUID professorId
){}
