package com.example.ProjectBeachTennis.service;

import com.example.ProjectBeachTennis.model.RegistrationStudentTeam;
import com.example.ProjectBeachTennis.model.Student;
import com.example.ProjectBeachTennis.repository.RegistrationStudentTeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationStudentTeamService {
    @Autowired
    private RegistrationStudentTeamRepository registrationStudentTeamRepository;

    public List<RegistrationStudentTeam> getAllRegistrationStudentTeam() {
        return registrationStudentTeamRepository.findAll();
    }

    public RegistrationStudentTeam saveRegistrationStudentTeam(RegistrationStudentTeam registrationStudentTeam) {
        return registrationStudentTeamRepository.save(registrationStudentTeam);
    }
}
