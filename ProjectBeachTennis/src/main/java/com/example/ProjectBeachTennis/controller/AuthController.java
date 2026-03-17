package com.example.ProjectBeachTennis.controller;

import com.example.ProjectBeachTennis.dto.LoginRequest;
import com.example.ProjectBeachTennis.model.Professor;
import com.example.ProjectBeachTennis.model.Student;
import com.example.ProjectBeachTennis.service.AuthService;
import com.example.ProjectBeachTennis.service.ProfessorService;
import com.example.ProjectBeachTennis.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final ProfessorService professorService;
    private final StudentService studentService;
    private final AuthService authService;

    public AuthController(ProfessorService professorService, StudentService studentService, AuthService authService) {
        this.professorService = professorService;
        this.studentService = studentService;
        this.authService = authService;
    }


    @PostMapping("/professor/login")
    public ResponseEntity<String> professorLogin(@RequestBody LoginRequest loginRequest) {
        String token = authService.loginProfessor(loginRequest.email(), loginRequest.password());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/student/login")
    public ResponseEntity<String> studentLogin(@RequestBody LoginRequest loginRequest) {
        String token = authService.loginStudent(loginRequest.email(), loginRequest.password());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/professor/register")
    public ResponseEntity<Professor> saveProfessor(@RequestBody Professor professor) {
        return new ResponseEntity<>(professorService.saveProfessor(professor), HttpStatus.CREATED);
    }

    @PostMapping("/student/register")
    public ResponseEntity<Student> saveStudent(@RequestBody Student student) {
        return new ResponseEntity<>(studentService.saveStudent(student), HttpStatus.CREATED);
    }


}
