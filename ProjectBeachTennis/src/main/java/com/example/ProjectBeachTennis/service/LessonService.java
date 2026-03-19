package com.example.ProjectBeachTennis.service;

import com.example.ProjectBeachTennis.dto.LessonRequestDTO;
import com.example.ProjectBeachTennis.dto.LessonResponseDTO;
import com.example.ProjectBeachTennis.model.Team;
import com.example.ProjectBeachTennis.repository.LessonRepository;
import com.example.ProjectBeachTennis.model.Lesson;
import com.example.ProjectBeachTennis.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class LessonService {
    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private TeamRepository teamRepository;

    // for development
    public List<Lesson> getAllLessons() {
        return lessonRepository.findAll();
    }

    public LessonResponseDTO saveLesson(LessonRequestDTO dto) {

        Team team = teamRepository.findTeamById(dto.teamId()).orElseThrow(() -> new RuntimeException("Time não encontrado"));

        Lesson lesson = new Lesson();

        lesson.setDatetime(dto.dateTime());
        lesson.setTeam(team);
        lesson.setLessonConducted(dto.isLessonConducted());


        lessonRepository.save(lesson);

        return new LessonResponseDTO(
                lesson.getId(),
                lesson.getDatetime(),
                lesson.isLessonConducted(),
                lesson.getTeam().getId(),
                lesson.getTeam().getProfessor().getFullName()
        );
    }
}
