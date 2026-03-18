package com.example.ProjectBeachTennis.repository;

import com.example.ProjectBeachTennis.model.Student;
import com.example.ProjectBeachTennis.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
    @Query("SELECT rst.student FROM RegistrationStudentTeam rst WHERE rst.team.professor.id = :professorId")
    List<Student> findStudentsByProfessorId(@Param("professorId") UUID professorId);

    @Query("SELECT rst.student FROM RegistrationStudentTeam rst WHERE rst.team.id = :teamId")
    List<Student> findStudentsByTeamId(@Param("teamId") UUID teamId);

    Optional<Student> findByEmail(String email);

    boolean existsByFullName(String fullName);
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
}
