package com.example.ProjectBeachTennis.controller;

import com.example.ProjectBeachTennis.dto.RegistrationStudentTeamDTO;
import com.example.ProjectBeachTennis.model.Professor;
import com.example.ProjectBeachTennis.model.RegistrationStudentTeam;
import com.example.ProjectBeachTennis.model.Student;
import com.example.ProjectBeachTennis.service.ProfessorService;
import com.example.ProjectBeachTennis.service.RegistrationStudentTeamService;
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

@RestController
@RequestMapping("/api/registrationstudentteam")
public class RegistrationStudentTeamController {
    @Autowired
    private RegistrationStudentTeamService registrationStudentTeamService;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private TeamService teamService;

    @GetMapping
    public List<RegistrationStudentTeam> getAllRegistrationStudentTeam() {
        return registrationStudentTeamService.getAllRegistrationStudentTeam();
    }

    @PreAuthorize("hasRole('PROFESSOR')")
    @PostMapping
    public ResponseEntity<?> saveRegistrationStudentTeam(
            @RequestBody RegistrationStudentTeamDTO dto,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String emailLogged = userDetails.getUsername();
        Professor professorLogged = professorService.getProfessorByEmail(emailLogged)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
        if(!professorLogged.getId().equals(teamService.getTeamById(dto.getTeamId()).get().getProfessor().getId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acesso negado");
        }

        RegistrationStudentTeam saved = registrationStudentTeamService.saveRegistrationStudentTeam(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

}
