package com.example.ProjectBeachTennis.repository;

import com.example.ProjectBeachTennis.model.Professor;
import com.example.ProjectBeachTennis.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProfessorRepository extends JpaRepository<Professor, Integer> {
    Optional<Professor> findById(Integer id);

    List<Professor> findAll();

}
