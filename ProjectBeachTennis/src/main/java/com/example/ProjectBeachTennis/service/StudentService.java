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
import com.example.ProjectBeachTennis.repository.StudentRepository;
import com.example.ProjectBeachTennis.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private LessonRepository lessonRepository;

    // for development
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public List<TeamResponseDTO> getTeamsByStudentId(UUID id) {
        List<Team> teams = teamRepository.findTeamsByStudentId(id);

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

    public Optional<StudentResponseDTO> getStudentByEmail(String email) {
        return studentRepository.findByEmail(email)
                .map(student -> new StudentResponseDTO(
                        student.getId(),
                        student.getFullName(),
                        student.getEmail(),
                        student.getLevel(),
                        student.getStatus(),
                        student.getStartAt()
                ));
    }

    public StudentResponseDTO saveStudent(StudentRequestDTO dto) {
        if(studentRepository.existsByCpf(dto.cpf())) {
            throw new EntityAlreadyExistsException("CPF já cadastrado");
        } else if (studentRepository.existsByEmail(dto.email())){
            throw new EntityAlreadyExistsException("Email já cadastrado");
        } else if (studentRepository.existsByFullName(dto.fullName())){
            throw new EntityAlreadyExistsException("Nome já cadastrado");
        }
        // student.setBalance(0);
        Student student = new Student();
        student.setFullName(dto.fullName());
        student.setEmail(dto.email());
        student.setLevel(dto.level());
        student.setCpf(dto.cpf());
        student.setStatus(dto.status());

        String passwordHashred = passwordEncoder.encode(dto.password());
        student.setPassword(passwordHashred);

        Student savedStudent = studentRepository.save(student);

        return new StudentResponseDTO(
                savedStudent.getId(),
                savedStudent.getFullName(),
                savedStudent.getEmail(),
                savedStudent.getLevel(),
                savedStudent.getStatus(),
                savedStudent.getStartAt()
        );
    }

    public List<LessonResponseDTO> getLessonsByStudentId(UUID id) {
        List<Lesson> lessons = lessonRepository.findLessonsByStudentId(id);

        return lessons.stream()
                .map(lesson -> new LessonResponseDTO(
                        lesson.getId(),
                        lesson.getDatetime(),
                        lesson.isLessonConducted(),
                        lesson.getTeam().getId(),
                        lesson.getTeam().getProfessor().getFullName()
                )).toList();
    }

    public void delete(UUID id) {
        var student = this.studentRepository.findById(id).orElse(null);
        if(student == null) {
            throw new EntityNonExistsException("Aluno não encontrado");
        }
        studentRepository.delete(student);
    }

}
