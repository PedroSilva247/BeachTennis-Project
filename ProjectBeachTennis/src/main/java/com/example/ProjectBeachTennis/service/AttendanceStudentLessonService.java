package com.example.ProjectBeachTennis.service;

import com.example.ProjectBeachTennis.dto.AttendanceStudentLessonRequestDTO;
import com.example.ProjectBeachTennis.dto.AttendanceStudentLessonResponseDTO;
import com.example.ProjectBeachTennis.model.*;
import com.example.ProjectBeachTennis.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AttendanceStudentLessonService {
    @Autowired
    private AttendanceStudentLessonRepository attendanceStudentLessonRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private LessonRepository lessonRepository;

    public List<AttendanceStudentLesson> getAllAttendanceStudentLesson() {
        return attendanceStudentLessonRepository.findAll();
    }

    public List<AttendanceStudentLesson> getAttendancesStudentLessonByLessonId(UUID lessonId) {
        return attendanceStudentLessonRepository.findByLessonId(lessonId);
    }

    public AttendanceStudentLessonResponseDTO saveAttendanceStudentLesson(AttendanceStudentLessonRequestDTO dto) {
        Student student = studentRepository.findById(dto.studentId())
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        Lesson lesson = lessonRepository.findById(dto.lessonId())
                .orElseThrow(() -> new RuntimeException("Aula não encontrada"));

        AttendanceStudentLesson attendance = new AttendanceStudentLesson();
        attendance.setStudent(student);
        attendance.setLesson(lesson);
        attendance.setPresent(dto.isPresent());
        attendance.setAttendanceType(dto.attendanceType());

        attendanceStudentLessonRepository.save(attendance);

        return new AttendanceStudentLessonResponseDTO(
                attendance.getId(),
                attendance.getLesson().getId(),
                attendance.getStudent().getFullName(),
                attendance.getLesson().getTeam().getProfessor().getFullName(),
                attendance.getLesson().getDatetime(),
                attendance.getLesson().isLessonConducted(),
                attendance.isPresent(),
                attendance.getAttendanceType()

        );
    }
}
