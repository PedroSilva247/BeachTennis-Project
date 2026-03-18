package com.example.ProjectBeachTennis.controller;

import com.example.ProjectBeachTennis.model.Professor;
import com.example.ProjectBeachTennis.model.Student;
import com.example.ProjectBeachTennis.model.Team;
import com.example.ProjectBeachTennis.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/professor")
public class ProfessorController {
    @Autowired
    private ProfessorService professorService;

    @GetMapping
    public List<Professor> getAllProfessors() {
        return professorService.getAllProfessors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProfessorById(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetails userDetails
            ) {
        String emailLogged = userDetails.getUsername();
        Professor professorLogged = professorService.getProfessorByEmail(emailLogged)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

        if (!professorLogged.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acesso negado");
        }

        Optional<Professor> professor = professorService.getProfessorById(id);
        if(professor.isPresent()) {
            return ResponseEntity.ok(professor.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('PROFESSOR')")
    @GetMapping("/{id}/team")
    public ResponseEntity<?> getTeamsByProfessorId(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String emailLogged = userDetails.getUsername();
        Professor professorLogged = professorService.getProfessorByEmail(emailLogged)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

        if (!professorLogged.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acesso negado");
        }
        List<Team> teams = professorService.getTeamsByProfessorId(id);
        if(!teams.isEmpty()) {
            return ResponseEntity.ok(teams);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('PROFESSOR')")
    @GetMapping("/{id}/student")
    public ResponseEntity<?> getStudentsByProfessorId(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String emailLogged = userDetails.getUsername();
        Professor professorLogged = professorService.getProfessorByEmail(emailLogged)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

        if (!professorLogged.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acesso negado");
        }

        List<Student> students = professorService.getStudentsByProfessorId(id);
        if(!students.isEmpty()) {
            return ResponseEntity.ok(students);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("#id = authentication.principal.id")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProfessor(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String emailLogged = userDetails.getUsername();
        Professor professorLogged = professorService.getProfessorByEmail(emailLogged)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

        if (!professorLogged.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acesso negado");
        }

        professorService.delete(id);
        return ResponseEntity.ok().body("Professor deletado");
    }

}
