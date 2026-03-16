package com.example.ProjectBeachTennis.controller;

import com.example.ProjectBeachTennis.model.Professor;
import com.example.ProjectBeachTennis.model.Student;
import com.example.ProjectBeachTennis.model.Team;
import com.example.ProjectBeachTennis.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Professor> getProfessorById(@PathVariable UUID id) {
        Optional<Professor> professor = professorService.getProfessorById(id);
        if(professor.isPresent()) {
            return ResponseEntity.ok(professor.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/team")
    public ResponseEntity<List<Team>> getTeamsByProfessorId(@PathVariable UUID id) {
        List<Team> teams = professorService.getTeamsByProfessorId(id);
        if(!teams.isEmpty()) {
            return ResponseEntity.ok(teams);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/student")
    public ResponseEntity<List<Student>> getStudentsByProfessorId(@PathVariable UUID id) {
        List<Student> students = professorService.getStudentsByProfessorId(id);
        if(!students.isEmpty()) {
            return ResponseEntity.ok(students);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping //(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Professor> saveProfessor(@RequestBody Professor professor) {
        return new ResponseEntity<>(professorService.saveProfessor(professor), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProfessor(@PathVariable UUID id) {
        professorService.delete(id);
        return ResponseEntity.ok().body("Professor deletado");
    }

}
