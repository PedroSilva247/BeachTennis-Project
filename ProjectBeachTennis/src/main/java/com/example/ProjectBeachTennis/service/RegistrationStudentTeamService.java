package com.example.ProjectBeachTennis.service;

import com.example.ProjectBeachTennis.dto.RegistrationStudentTeamDTO;
import com.example.ProjectBeachTennis.model.RegistrationStudentTeam;
import com.example.ProjectBeachTennis.model.Student;
import com.example.ProjectBeachTennis.model.Team;
import com.example.ProjectBeachTennis.repository.RegistrationStudentTeamRepository;
import com.example.ProjectBeachTennis.repository.StudentRepository;
import com.example.ProjectBeachTennis.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationStudentTeamService {
    @Autowired
    private RegistrationStudentTeamRepository registrationStudentTeamRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeamRepository teamRepository;

    public List<RegistrationStudentTeam> getAllRegistrationStudentTeam() {
        return registrationStudentTeamRepository.findAll();
    }

    public RegistrationStudentTeam saveRegistrationStudentTeam(RegistrationStudentTeamDTO dto) {
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        Team team = teamRepository.findById(dto.getTeamId())
                .orElseThrow(() -> new RuntimeException("Time não encontrado"));

        RegistrationStudentTeam registration = new RegistrationStudentTeam();
        registration.setStudent(student);
        registration.setTeam(team);

        return registrationStudentTeamRepository.save(registration);
    }
}
