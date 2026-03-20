package com.example.ProjectBeachTennis.controller;

import com.example.ProjectBeachTennis.dto.AttendanceStudentLessonRequestDTO;
import com.example.ProjectBeachTennis.dto.ProfessorResponseDTO;
import com.example.ProjectBeachTennis.model.AttendanceStudentLesson;
import com.example.ProjectBeachTennis.model.Lesson;
import com.example.ProjectBeachTennis.model.RegistrationStudentTeam;
import com.example.ProjectBeachTennis.model.Student;
import com.example.ProjectBeachTennis.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendancestudentlesson")
public class AttendanceStudentLessonController {
    @Autowired
    private AttendanceStudentLessonService attendanceStudentLessonService;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private LessonService lessonService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private StudentService studentService;


    @GetMapping
    public List<AttendanceStudentLesson> getAllAttendanceStudentLesson() {
        return attendanceStudentLessonService.getAllAttendanceStudentLesson();
    }

    @PreAuthorize("hasRole('PROFESSOR')")
    @PostMapping
    public ResponseEntity<?> saveAttendanceStudentLesson(
            @RequestBody AttendanceStudentLessonRequestDTO dto,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String emailLogged = userDetails.getUsername();
        ProfessorResponseDTO professorLogged = professorService.getProfessorByEmail(emailLogged)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
        if (!professorLogged.id().equals(lessonService.getLessonById(dto.lessonId()).get().getTeam().getProfessor().getId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acesso negado");
        }


        return ResponseEntity.status(HttpStatus.CREATED).body(attendanceStudentLessonService.saveAttendanceStudentLesson(dto));
    }
}
