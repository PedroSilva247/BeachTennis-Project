package com.example.ProjectBeachTennis.service;

import com.example.ProjectBeachTennis.dto.RegistrationStudentTeamRequestDTO;
import com.example.ProjectBeachTennis.dto.RegistrationStudentTeamResponseDTO;
import com.example.ProjectBeachTennis.errors.EntityNonExistsException;
import com.example.ProjectBeachTennis.model.RegistrationStudentTeam;
import com.example.ProjectBeachTennis.model.Student;
import com.example.ProjectBeachTennis.model.Team;
import com.example.ProjectBeachTennis.repository.RegistrationStudentTeamRepository;
import com.example.ProjectBeachTennis.repository.StudentRepository;
import com.example.ProjectBeachTennis.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

    public RegistrationStudentTeamResponseDTO saveRegistrationStudentTeam(RegistrationStudentTeamRequestDTO dto) {
        Student student = studentRepository.findById(dto.studentId())
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        Team team = teamRepository.findById(dto.teamId())
                .orElseThrow(() -> new RuntimeException("Time não encontrado"));

        RegistrationStudentTeam registration = new RegistrationStudentTeam();
        registration.setStudent(student);
        registration.setTeam(team);


        registrationStudentTeamRepository.save(registration);

        return new RegistrationStudentTeamResponseDTO(
                registration.getId(),
                registration.getStudent().getId(),
                registration.getTeam().getId(),
                registration.getStartAt()
        );
    }

    public void delete(UUID id) {
        var registration = this.registrationStudentTeamRepository.findById(id).orElse(null);
        if(registration == null) {
            throw new EntityNonExistsException("Registro não encontrado");
        }
        registrationStudentTeamRepository.delete(registration);
    }
}
