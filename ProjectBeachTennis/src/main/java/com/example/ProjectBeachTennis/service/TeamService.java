package com.example.ProjectBeachTennis.service;

import com.example.ProjectBeachTennis.model.Lesson;
import com.example.ProjectBeachTennis.model.Student;
import com.example.ProjectBeachTennis.model.Team;
import com.example.ProjectBeachTennis.repository.LessonRepository;
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

    public Optional<Team> getTeamById(UUID id) {
        return teamRepository.findTeamById(id);
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public List<Student> getStudentsByTeamId(UUID id) {
        return studentRepository.findStudentsByTeamId(id);
    }

    public List<Lesson> getLessonsByTeamId(UUID id) {
        return lessonRepository.findLessonsByTeamId(id);
    }



    public Team saveTeam(Team team) {
        return teamRepository.save(team);
    }
}
