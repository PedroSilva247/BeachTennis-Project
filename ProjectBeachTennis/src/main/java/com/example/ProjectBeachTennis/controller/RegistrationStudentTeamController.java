package com.example.ProjectBeachTennis.controller;

import com.example.ProjectBeachTennis.model.RegistrationStudentTeam;
import com.example.ProjectBeachTennis.model.Student;
import com.example.ProjectBeachTennis.service.RegistrationStudentTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registrationstudentteam")
public class RegistrationStudentTeamController {
    @Autowired
    private RegistrationStudentTeamService registrationStudentTeamService;

    @GetMapping
    public List<RegistrationStudentTeam> getAllRegistrationStudentTeam() {
        return registrationStudentTeamService.getAllRegistrationStudentTeam();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegistrationStudentTeam> saveRegistrationStudentTeam(@RequestBody RegistrationStudentTeam registrationStudentTeam) {
        return new ResponseEntity<>(registrationStudentTeamService.saveRegistrationStudentTeam(registrationStudentTeam), HttpStatus.CREATED);
    }

}
