package com.example.ProjectBeachTennis.service;

import com.example.ProjectBeachTennis.model.Professor;
import com.example.ProjectBeachTennis.model.Student;
import com.example.ProjectBeachTennis.model.Team;
import com.example.ProjectBeachTennis.repository.ProfessorRepository;
import com.example.ProjectBeachTennis.repository.StudentRepository;
import com.example.ProjectBeachTennis.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProfessorService {
    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private StudentRepository studentRepository;

    public List<Professor> getAllProfessors() {
        return professorRepository.findAll();
    }

    public Optional<Professor> getProfessorById(UUID id) {
        return professorRepository.findById(id);
    }

    public List<Team> getTeamsByProfessorId(UUID id) {
        return teamRepository.findTeamsByProfessorId(id);
    }

    public List<Student> getStudentsByProfessorId(UUID id) {
        return studentRepository.findStudentsByProfessorId(id);
    }

    public Professor saveProfessor(Professor professor) {
        return professorRepository.save(professor);
    }
}
