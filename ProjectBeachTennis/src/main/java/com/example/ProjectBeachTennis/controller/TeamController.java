package com.example.ProjectBeachTennis.controller;

import com.example.ProjectBeachTennis.model.Lesson;
import com.example.ProjectBeachTennis.model.Professor;
import com.example.ProjectBeachTennis.model.Student;
import com.example.ProjectBeachTennis.model.Team;
import com.example.ProjectBeachTennis.service.ProfessorService;
import com.example.ProjectBeachTennis.service.StudentService;
import com.example.ProjectBeachTennis.service.TeamService;
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
@RequestMapping("/api/team")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private StudentService studentService;

    // For development
    @GetMapping
    public List<Team> getAllTeams() {
        return teamService.getAllTeams();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTeamById(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String emailLogged = userDetails.getUsername();

        // PROFESSOR
        Optional<Professor> professorOpt = professorService.getProfessorByEmail(emailLogged);
        if (professorOpt.isPresent()) {
            if (!professorOpt.get().getId().equals(teamService.getTeamById(id).get().getProfessor().getId())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acesso negado");
            }
            return ResponseEntity.ok().body(teamService.getTeamById(id));
        }

        // STUDENT
        Optional<Student> studentOpt = studentService.getStudentByEmail(emailLogged);
        if (studentOpt.isPresent()) {
            if (!studentService.getTeamsByStudentId(studentOpt.get().getId()).contains(teamService.getTeamById(id).get())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acesso negado");
            }
            return ResponseEntity.ok().body(teamService.getTeamById(id));
        }

        throw new RuntimeException("Usuário não encontrado");
    }

    @GetMapping("/{id}/student")
    public ResponseEntity<?> getStudentsByTeamId(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String emailLogged = userDetails.getUsername();

        // PROFESSOR
        Optional<Professor> professorOpt = professorService.getProfessorByEmail(emailLogged);
        if (professorOpt.isPresent()) {
            if (!professorOpt.get().getId().equals(teamService.getTeamById(id).get().getProfessor().getId())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acesso negado");
            }
            return ResponseEntity.ok().body(teamService.getStudentsByTeamId(id));
        }

        // STUDENT
        Optional<Student> studentOpt = studentService.getStudentByEmail(emailLogged);
        if (studentOpt.isPresent()) {
            if (!studentService.getTeamsByStudentId(studentOpt.get().getId()).contains(teamService.getTeamById(id).get())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acesso negado");
            }
            return ResponseEntity.ok().body(teamService.getStudentsByTeamId(id));
        }

        throw new RuntimeException("Usuário não encontrado");
    }

    @GetMapping("/{id}/lesson")
    public ResponseEntity<?> getLessonsByTeamId(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String emailLogged = userDetails.getUsername();
        // PROFESSOR
        Optional<Professor> professorOpt = professorService.getProfessorByEmail(emailLogged);
        if (professorOpt.isPresent()) {
            if (!professorOpt.get().getId().equals(teamService.getTeamById(id).get().getProfessor().getId())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acesso negado");
            }
            return ResponseEntity.ok().body(teamService.getLessonsByTeamId(id));
        }

        // STUDENT
        Optional<Student> studentOpt = studentService.getStudentByEmail(emailLogged);
        if (studentOpt.isPresent()) {
            if (!studentService.getTeamsByStudentId(studentOpt.get().getId()).contains(teamService.getTeamById(id).get())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acesso negado");
            }
            return ResponseEntity.ok().body(teamService.getLessonsByTeamId(id));
        }

        throw new RuntimeException("Usuário não encontrado");
    }

    @PreAuthorize("hasRole('ROLE_PROFESSOR')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveTeam(
            @RequestBody Team team,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String emailLogged = userDetails.getUsername();
        Professor professorLogged = professorService.getProfessorByEmail(emailLogged)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

        if (!professorLogged.getId().equals(team.getProfessor().getId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acesso negado");
        }

        return new ResponseEntity<>(teamService.saveTeam(team), HttpStatus.CREATED);
    }

}
