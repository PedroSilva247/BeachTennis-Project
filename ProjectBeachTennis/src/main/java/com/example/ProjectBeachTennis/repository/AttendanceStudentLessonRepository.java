package com.example.ProjectBeachTennis.repository;

import com.example.ProjectBeachTennis.model.AttendanceStudentLesson;
import com.example.ProjectBeachTennis.model.RegistrationStudentTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface AttendanceStudentLessonRepository extends JpaRepository<AttendanceStudentLesson, UUID> {
    List<AttendanceStudentLesson> findByStudentId(UUID studentId);

    List<AttendanceStudentLesson> findByLessonId(UUID lessonId);
    // List<AttendanceStudentLesson> findBy
}
