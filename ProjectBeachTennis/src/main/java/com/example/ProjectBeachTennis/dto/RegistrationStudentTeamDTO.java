package com.example.ProjectBeachTennis.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class RegistrationStudentTeamDTO {
    private UUID studentId;
    private UUID teamId;
}
