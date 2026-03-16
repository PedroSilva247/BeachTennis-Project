package com.example.ProjectBeachTennis.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.ProjectBeachTennis.errors.EntityAlreadyExistsException;
import com.example.ProjectBeachTennis.errors.EntityNonExistsException;
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
        if(professorRepository.existsByCpf(professor.getCpf())) {
            throw new EntityAlreadyExistsException("CPF já cadastrado");
        } else if (professorRepository.existsByEmail(professor.getEmail())){
            throw new EntityAlreadyExistsException("Email já cadastrado");
        } else if (professorRepository.existsByFullName(professor.getFullName())){
            throw new EntityAlreadyExistsException("Nome já cadastrado");
        }

        var passwordHashred = BCrypt.withDefaults()
                .hashToString(12,professor.getPassword().toCharArray());
        professor.setPassword(passwordHashred);
        return professorRepository.save(professor);
    }

    public void delete(UUID id) {
        var professor = this.professorRepository.findById(id).orElse(null);
        if(professor == null) {
            throw new EntityNonExistsException("Professor não encontrado");
        }
        professorRepository.delete(professor);
    }
}
