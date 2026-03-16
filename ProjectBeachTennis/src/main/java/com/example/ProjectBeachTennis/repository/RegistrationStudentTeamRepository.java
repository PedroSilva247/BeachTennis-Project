package com.example.ProjectBeachTennis.repository;

import com.example.ProjectBeachTennis.model.RegistrationStudentTeam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RegistrationStudentTeamRepository extends JpaRepository<RegistrationStudentTeam, UUID> {

}
