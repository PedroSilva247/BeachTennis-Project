package com.example.ProjectBeachTennis.repository;

import com.example.ProjectBeachTennis.model.RegistrationStudentTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface RegistrationStudentTeamRepository extends JpaRepository<RegistrationStudentTeam, UUID> {
    @Query("SELECT rst FROM RegistrationStudentTeam rst WHERE rst.student.id = :studentId")
    List<RegistrationStudentTeam> findByStudentId(@Param("studentId") UUID studentId);

    @Query("SELECT rst FROM RegistrationStudentTeam rst WHERE rst.team.id = :teamId")
    List<RegistrationStudentTeam> findByTeamId(@Param("teamId") UUID teamId);
}
