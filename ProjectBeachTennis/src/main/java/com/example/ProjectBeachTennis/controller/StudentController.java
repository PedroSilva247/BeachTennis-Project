package com.example.ProjectBeachTennis.controller;

import com.example.ProjectBeachTennis.model.Student;
import com.example.ProjectBeachTennis.model.Team;
import com.example.ProjectBeachTennis.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{id}/team")
    public ResponseEntity<List<Team>> getTeamsByStudentId(@PathVariable UUID id) {
        List<Team> teams = studentService.getTeamsByStudentId(id);
        if(!teams.isEmpty()) {
            return ResponseEntity.ok(teams);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteStudant(@PathVariable UUID id) {
        studentService.delete(id);
        return ResponseEntity.ok().body("Aluno deletado");
    }

}
