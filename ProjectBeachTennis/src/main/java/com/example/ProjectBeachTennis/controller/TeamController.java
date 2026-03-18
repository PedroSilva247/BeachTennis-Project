package com.example.ProjectBeachTennis.controller;

import com.example.ProjectBeachTennis.model.Lesson;
import com.example.ProjectBeachTennis.model.Professor;
import com.example.ProjectBeachTennis.model.Student;
import com.example.ProjectBeachTennis.model.Team;
import com.example.ProjectBeachTennis.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/team")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @GetMapping
    public List<Team> getAllTeams() {
        return teamService.getAllTeams();
    }

    @GetMapping("/{id}")
    public Optional<Team> getTeamById(@PathVariable UUID id) {
        return teamService.getTeamById(id);
    }

    @GetMapping("/{id}/student")
    public List<Student> getStudentsByTeamId(@PathVariable UUID id) {
        return teamService.getStudentsByTeamId(id);
    }

    @GetMapping("/{id}/lesson")
    public List<Lesson> getLessonsByTeamId(@PathVariable UUID id) {
        return teamService.getLessonsByTeamId(id);
    }

    @PreAuthorize("hasRole('ROLE_PROFESSOR')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveTeam(
            @RequestBody Team team,
            @AuthenticationPrincipal Professor professorLogged
            ) {
        System.out.println(professorLogged.getId());
        if (!professorLogged.getId().equals(team.getProfessor().getId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acesso negado");
        }

        return new ResponseEntity<>(teamService.saveTeam(team), HttpStatus.CREATED);
    }

}
