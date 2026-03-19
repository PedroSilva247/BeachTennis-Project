package com.example.ProjectBeachTennis.controller;

import com.example.ProjectBeachTennis.dto.LessonResponseDTO;
import com.example.ProjectBeachTennis.dto.StudentResponseDTO;
import com.example.ProjectBeachTennis.dto.TeamResponseDTO;
import com.example.ProjectBeachTennis.model.Lesson;
import com.example.ProjectBeachTennis.model.Professor;
import com.example.ProjectBeachTennis.model.Student;
import com.example.ProjectBeachTennis.model.Team;
import com.example.ProjectBeachTennis.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping
    public List<Student> getAllStudents() {
        return  studentService.getAllStudents();
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/{id}/teams")
    public ResponseEntity<?> getTeamsByStudentId(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetails userDetails
            ) {
        String emailLogged = userDetails.getUsername();
        StudentResponseDTO studentLogged = studentService.getStudentByEmail(emailLogged)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        if (!studentLogged.id().equals(id)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acesso negado");
        }

        List<TeamResponseDTO> teams = studentService.getTeamsByStudentId(id);
        if(!teams.isEmpty()) {
            return ResponseEntity.ok(teams);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/{id}/lessons")
    public ResponseEntity<?> getLessonsByStudentId(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String emailLogged = userDetails.getUsername();
        StudentResponseDTO studentLogged = studentService.getStudentByEmail(emailLogged)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        if (!studentLogged.id().equals(id)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acesso negado");
        }

        List<LessonResponseDTO> lessons = studentService.getLessonsByStudentId(id);
        if(!lessons.isEmpty()) {
            return ResponseEntity.ok(lessons);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("#id = authentication.principal.id")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudant(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String emailLogged = userDetails.getUsername();
        StudentResponseDTO studentLogged = studentService.getStudentByEmail(emailLogged)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        if (!studentLogged.id().equals(id)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acesso negado");
        }

        studentService.delete(id);
        return ResponseEntity.ok().body("Aluno deletado");
    }


}
