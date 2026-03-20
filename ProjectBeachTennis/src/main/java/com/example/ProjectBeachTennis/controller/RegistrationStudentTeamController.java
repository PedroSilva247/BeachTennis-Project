package com.example.ProjectBeachTennis.controller;

import com.example.ProjectBeachTennis.dto.ProfessorResponseDTO;
import com.example.ProjectBeachTennis.dto.RegistrationStudentTeamRequestDTO;
import com.example.ProjectBeachTennis.dto.RegistrationStudentTeamResponseDTO;
import com.example.ProjectBeachTennis.dto.StudentResponseDTO;
import com.example.ProjectBeachTennis.model.Professor;
import com.example.ProjectBeachTennis.model.RegistrationStudentTeam;
import com.example.ProjectBeachTennis.model.Student;
import com.example.ProjectBeachTennis.service.ProfessorService;
import com.example.ProjectBeachTennis.service.RegistrationStudentTeamService;
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
@RequestMapping("/api/registrationstudentteam")
public class RegistrationStudentTeamController {
    @Autowired
    private RegistrationStudentTeamService registrationStudentTeamService;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private StudentService studentService;

    @GetMapping
    public List<RegistrationStudentTeam> getAllRegistrationStudentTeam() {
        return registrationStudentTeamService.getAllRegistrationStudentTeam();
    }

    @PreAuthorize("hasRole('PROFESSOR')")
    @PostMapping
    public ResponseEntity<?> saveRegistrationStudentTeam(
            @RequestBody RegistrationStudentTeamRequestDTO dto,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String emailLogged = userDetails.getUsername();
        ProfessorResponseDTO professorLogged = professorService.getProfessorByEmail(emailLogged)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
        if(!professorLogged.id().equals(teamService.getTeamById(dto.teamId()).get().getProfessor().getId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acesso negado");
        }

        RegistrationStudentTeamResponseDTO saved = registrationStudentTeamService.saveRegistrationStudentTeam(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRegistrationStudentTeam(
            @PathVariable UUID id,
            @RequestBody RegistrationStudentTeamRequestDTO dto,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String emailLogged = userDetails.getUsername();
        // PROFESSOR
        Optional<ProfessorResponseDTO> professorOpt = professorService.getProfessorByEmail(emailLogged);
        if (professorOpt.isPresent()) {
            if (!professorOpt.get().id().equals(teamService.getTeamById(dto.teamId()).get().getProfessor().getId())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acesso negado");
            }
            registrationStudentTeamService.delete(id);
            return ResponseEntity.ok().body("Registro deletado");
        }

        // STUDENT
        Optional<StudentResponseDTO> studentOpt = studentService.getStudentByEmail(emailLogged);
        if (studentOpt.isPresent()) {
            if (!studentOpt.get().id().equals(dto.studentId())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acesso negado");
            }
            registrationStudentTeamService.delete(id);
            return ResponseEntity.ok().body("Registro deletado");
        }

        throw new RuntimeException("Usuário não encontrado");
    }

}
