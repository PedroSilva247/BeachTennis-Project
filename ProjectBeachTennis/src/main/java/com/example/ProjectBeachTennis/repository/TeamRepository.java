package com.example.ProjectBeachTennis.repository;

import com.example.ProjectBeachTennis.model.Lesson;
import com.example.ProjectBeachTennis.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TeamRepository extends JpaRepository<Team, UUID> {
    @Query("SELECT t FROM Team t WHERE t.id = :id")
    Optional<Team> findTeamById(@Param("id") UUID id);

    @Query("SELECT t FROM Team t WHERE t.professor.id = :id")
    List<Team> findTeamsByProfessorId(@Param("id") UUID professorId);

    @Query(value = "SELECT t.* FROM team t " +
            "JOIN registration_student_team rst ON t.id_team = rst.team_id_team " +
            "WHERE rst.student_id_student = :id",
            nativeQuery = true)
    List<Team> findTeamsByStudentId(@Param("id") UUID studentId);
}
