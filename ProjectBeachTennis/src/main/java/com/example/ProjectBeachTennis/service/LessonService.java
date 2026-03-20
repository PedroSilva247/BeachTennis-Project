package com.example.ProjectBeachTennis.service;

import com.example.ProjectBeachTennis.dto.LessonRequestDTO;
import com.example.ProjectBeachTennis.dto.LessonResponseDTO;
import com.example.ProjectBeachTennis.dto.LessonWithStudentsDTO;
import com.example.ProjectBeachTennis.dto.StudentPresenceDTO;
import com.example.ProjectBeachTennis.model.AttendanceStudentLesson;
import com.example.ProjectBeachTennis.model.Team;
import com.example.ProjectBeachTennis.repository.LessonRepository;
import com.example.ProjectBeachTennis.model.Lesson;
import com.example.ProjectBeachTennis.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public Optional<Lesson> getLessonById(UUID id) {
        return lessonRepository.findById(id);
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

    public List<LessonWithStudentsDTO> getLessonsByProfessorId(UUID professorId) {

        // 1. Busca todas as aulas deste professor
        List<Lesson> lessons = lessonRepository.findLessonsByProfessorId(professorId);

        // 2. Converte as aulas para o DTO aninhado
        return lessons.stream()
                .map(lesson -> {

                    // 3. Pega a lista de presenças da aula, filtra e converte para StudentPresenceDTO
                    List<StudentPresenceDTO> attendants = lesson.getAttendances().stream()
                            .filter(AttendanceStudentLesson::isPresent) // Pega SÓ quem estava presente
                            .map(att -> new StudentPresenceDTO(
                                    att.getStudent().getId(),
                                    att.getStudent().getFullName(),
                                    att.getAttendanceType()
                            ))
                            .toList();

                    // 4. Monta o DTO final da Aula colocando a lista de alunos dentro dele
                    return new LessonWithStudentsDTO(
                            lesson.getId(),
                            lesson.getDatetime(),
                            lesson.isLessonConducted(),
                            lesson.getTeam().getLevel(), // Pega o nível da turma
                            attendants // <-- A lista de alunos mastigada vai aqui!
                    );
                })
                .toList();
    }

    }
