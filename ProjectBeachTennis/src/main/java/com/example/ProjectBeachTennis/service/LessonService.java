package com.example.ProjectBeachTennis.service;

import com.example.ProjectBeachTennis.dto.LessonRequestDTO;
import com.example.ProjectBeachTennis.dto.LessonResponseDTO;
import com.example.ProjectBeachTennis.dto.LessonWithStudentsDTO;
import com.example.ProjectBeachTennis.dto.StudentPresenceDTO;
import com.example.ProjectBeachTennis.model.AttendanceStudentLesson;
import com.example.ProjectBeachTennis.model.RegistrationStudentTeam;
import com.example.ProjectBeachTennis.model.Team;
import com.example.ProjectBeachTennis.repository.*;
import com.example.ProjectBeachTennis.model.Lesson;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LessonService {
    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AttendanceStudentLessonRepository attendanceStudentLessonRepository;

    @Autowired
    private RegistrationStudentTeamRepository registrationStudentTeamRepository;

    // for development
    public List<Lesson> getAllLessons() {
        return lessonRepository.findAll();
    }

    public Optional<Lesson> getLessonById(UUID id) {
        return lessonRepository.findById(id);
    }

    @Transactional
    public LessonResponseDTO saveLesson(LessonRequestDTO dto) {

        Team team = teamRepository.findTeamById(dto.teamId()).orElseThrow(() -> new RuntimeException("Time não encontrado"));

        Lesson lesson = new Lesson();

        lesson.setDatetime(dto.dateTime());
        lesson.setTeam(team);
        lesson.setLessonConducted(dto.isLessonConducted());


        Lesson savedLesson = lessonRepository.save(lesson);

        List<RegistrationStudentTeam> registrations = registrationStudentTeamRepository.findByTeamId(team.getId());

        List<AttendanceStudentLesson> attendanceList = new ArrayList<>();

        for (RegistrationStudentTeam registration : registrations) {
            AttendanceStudentLesson attendance = new AttendanceStudentLesson();
            attendance.setLesson(savedLesson);
            attendance.setStudent(registration.getStudent());
            attendance.setPresent(false);
            attendance.setAttendanceType("Expected");

            attendanceList.add(attendance);
        }

        List<AttendanceStudentLesson> savedAttendances = attendanceStudentLessonRepository.saveAll(attendanceList);


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
                    List<AttendanceStudentLesson> attendances = attendanceStudentLessonRepository.findByLessonId(lesson.getId());
                    System.out.println(attendances);
                    // 3. Pega a lista de presenças da aula, filtra e converte para StudentPresenceDTO
                    List<StudentPresenceDTO> attendants = attendances.stream()
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
