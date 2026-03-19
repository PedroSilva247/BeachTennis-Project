package com.example.ProjectBeachTennis.service;

import com.example.ProjectBeachTennis.dto.StudentResponseDTO;
import com.example.ProjectBeachTennis.dto.TeamRequestDTO;
import com.example.ProjectBeachTennis.dto.TeamResponseDTO;
import com.example.ProjectBeachTennis.model.Lesson;
import com.example.ProjectBeachTennis.model.Professor;
import com.example.ProjectBeachTennis.model.Student;
import com.example.ProjectBeachTennis.model.Team;
import com.example.ProjectBeachTennis.repository.LessonRepository;
import com.example.ProjectBeachTennis.repository.ProfessorRepository;
import com.example.ProjectBeachTennis.repository.StudentRepository;
import com.example.ProjectBeachTennis.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    public Optional<Team> getTeamById(UUID id) {
        return teamRepository.findTeamById(id);
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public List<StudentResponseDTO> getStudentsByTeamId(UUID id) {
        List<Student> students = studentRepository.findStudentsByTeamId(id);
        return students.stream()
                .map(student -> new StudentResponseDTO(
                        student.getId(),
                        student.getFullName(),
                        student.getEmail(),
                        student.getLevel(),
                        student.getStatus(),
                        student.getStartAt()
                )).toList();
    }

    public List<Lesson> getLessonsByTeamId(UUID id) {
        return lessonRepository.findLessonsByTeamId(id);
    }





    public TeamResponseDTO saveTeam(TeamRequestDTO dto) {
        Professor professor = professorRepository.findById(dto.professorId()).orElseThrow(() -> new RuntimeException("Professor não encontrado"));

        Team team = new Team();

        team.setTime(dto.time());
        team.setLevel(dto.level());
        team.setDayOfWeek(dto.dayOfWeek());
        team.setProfessor(professor);

        teamRepository.save(team);


        return new TeamResponseDTO(
                team.getId(),
                team.getDayOfWeek(),
                team.getTime(),
                team.getLevel(),
                team.getProfessor().getFullName(),
                team.getProfessor().getId(),
                team.getStartAt()
        );
    }
}
