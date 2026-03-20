package com.example.ProjectBeachTennis.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.ProjectBeachTennis.dto.*;
import com.example.ProjectBeachTennis.errors.EntityAlreadyExistsException;
import com.example.ProjectBeachTennis.errors.EntityNonExistsException;
import com.example.ProjectBeachTennis.model.Lesson;
import com.example.ProjectBeachTennis.model.Professor;
import com.example.ProjectBeachTennis.model.Student;
import com.example.ProjectBeachTennis.model.Team;
import com.example.ProjectBeachTennis.repository.LessonRepository;
import com.example.ProjectBeachTennis.repository.ProfessorRepository;
import com.example.ProjectBeachTennis.repository.StudentRepository;
import com.example.ProjectBeachTennis.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private LessonRepository lessonRepository;

    // for development
    public List<ProfessorResponseDTO> getAllProfessors() {
        List<Professor> professors = professorRepository.findAll();

        return professors.stream()
                .map(professor -> new ProfessorResponseDTO(
                        professor.getId(),
                        professor.getFullName(),
                        professor.getEmail(),
                        professor.getStatus(),
                        professor.getStartAt()
                )).toList();
    }

    public Optional<ProfessorResponseDTO> getProfessorById(UUID id) {
        return professorRepository.findById(id)
                .map(prof -> new ProfessorResponseDTO(
                        prof.getId(),
                        prof.getFullName(),
                        prof.getEmail(),
                        prof.getStatus(),
                        prof.getStartAt()
                ));

    }

    public List<TeamResponseDTO> getTeamsByProfessorId(UUID id) {
        List<Team> teams = teamRepository.findTeamsByProfessorId(id);
        return teams.stream()
                .map(team -> new TeamResponseDTO(
                        team.getId(),
                        team.getDayOfWeek(),
                        team.getTime(),
                        team.getLevel(),
                        team.getProfessor().getFullName(),
                        team.getProfessor().getId(),
                        team.getStartAt()
                )).toList();
    }

    public List<StudentResponseDTO> getStudentsByProfessorId(UUID id) {

        List<Student> students = studentRepository.findStudentsByProfessorId(id);

        return students.stream()
                .map(student -> new StudentResponseDTO(
                        student.getId(),
                        student.getFullName(),
                        student.getEmail(),
                        student.getLevel(),
                        student.getStatus(),
                        student.getStartAt()
                ))
                .toList();
    }

    public Optional<ProfessorResponseDTO> getProfessorByEmail(String email) {

        return professorRepository.findByEmail(email)
                .map(prof -> new ProfessorResponseDTO(
                        prof.getId(),
                        prof.getFullName(),
                        prof.getEmail(),
                        prof.getStatus(),
                        prof.getStartAt()
                ));
    }










    public ProfessorResponseDTO saveProfessor(ProfessorRequestDTO dto) {
        if(professorRepository.existsByCpf(dto.cpf())) {
            throw new EntityAlreadyExistsException("CPF já cadastrado");
        } else if (professorRepository.existsByEmail(dto.email())){
            throw new EntityAlreadyExistsException("Email já cadastrado");
        } else if (professorRepository.existsByFullName(dto.fullName())){
            throw new EntityAlreadyExistsException("Nome já cadastrado");
        }

        Professor professor = new Professor();
        professor.setFullName(dto.fullName());
        professor.setEmail(dto.email());
        professor.setCpf(dto.cpf());
        professor.setStatus(dto.status());

        String passwordHashred = passwordEncoder.encode(dto.password());
        professor.setPassword(passwordHashred);

        Professor savedProfessor = professorRepository.save(professor);

        return new ProfessorResponseDTO(
                savedProfessor.getId(),
                savedProfessor.getFullName(),
                savedProfessor.getEmail(),
                savedProfessor.getStatus(),
                savedProfessor.getStartAt()
        );
    }

    public void delete(UUID id) {
        var professor = this.professorRepository.findById(id).orElse(null);
        if(professor == null) {
            throw new EntityNonExistsException("Professor não encontrado");
        }
        professorRepository.delete(professor);
    }




    private String maskCPF(String cpf) {
        if(cpf == null || cpf.length() != 11) {
            return cpf;
        }
        return "***.***." + cpf.substring(6, 9) + "-" + cpf.substring(9, 11);
    }
}
