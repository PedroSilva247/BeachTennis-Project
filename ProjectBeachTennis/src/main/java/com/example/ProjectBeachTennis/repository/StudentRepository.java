package com.example.ProjectBeachTennis.repository;

import com.example.ProjectBeachTennis.model.Student;
import com.example.ProjectBeachTennis.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    @Query("SELECT s FROM Student s " +
            "JOIN RegistrationStudentTeam rst ON s.id = rst.student.id " +
            "JOIN Team t ON rst.team.id = t.id " +
            "WHERE t.professor.id = :professorId")
    List<Student> findStudentsByProfessorId(@Param("id") Integer id);

    @Query("SELECT s FROM Student s " +
            "JOIN RegistrationStudentTeam rst ON s.id = rst.student.id " +
            "WHERE rst.team.id = :teamId")
    List<Student> findStudentsByTeamId(@Param("id") Integer teamId);
}
