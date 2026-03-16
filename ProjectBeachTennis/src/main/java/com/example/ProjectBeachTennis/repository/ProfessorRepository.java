package com.example.ProjectBeachTennis.repository;

import com.example.ProjectBeachTennis.model.Professor;
import com.example.ProjectBeachTennis.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfessorRepository extends JpaRepository<Professor, UUID> {
    Optional<Professor> findById(UUID id);

    List<Professor> findAll();

}
