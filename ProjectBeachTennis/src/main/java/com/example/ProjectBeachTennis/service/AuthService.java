package com.example.ProjectBeachTennis.service;

import com.example.ProjectBeachTennis.model.Professor;
import com.example.ProjectBeachTennis.model.Student;
import com.example.ProjectBeachTennis.repository.ProfessorRepository;
import com.example.ProjectBeachTennis.repository.StudentRepository;
import com.example.ProjectBeachTennis.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {
    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            StudentRepository studentRepository,
            ProfessorRepository professorRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.studentRepository = studentRepository;
        this.professorRepository = professorRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String loginStudent(String email, String passwordEntered) {
        var student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado"));
        if (!passwordEncoder.matches(passwordEntered, student.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Senha incorreta");
        }
        return jwtService.generateToken(student);
    }

    public String loginProfessor(String email, String passwordEntered) {
        System.out.println("EMAIL" + email);
        System.out.println("passwordEntered" + passwordEntered);
        var professor = professorRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não encontrado"));
        if (!passwordEncoder.matches(passwordEntered, professor.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Senha incorreta");
        }
        return jwtService.generateToken(professor);
    }
}
