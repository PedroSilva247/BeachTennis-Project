package com.example.ProjectBeachTennis.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "attendance_student_lesson")
public class AttendanceStudentLesson {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_attendance_student_lesson")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "lesson_id_lesson", nullable = false, referencedColumnName = "id_lesson")
    @JsonBackReference
    private Lesson lesson;

    @ManyToOne
    @JoinColumn(name = "student_id_student", nullable = false, referencedColumnName = "id_student")
    @JsonBackReference
    private Student student;

    @Column(name = "is_present")
    private boolean isPresent;

    @Column(name = "attendance_type")
    private String attendanceType;

    @Column(name = "start_at_attendance_student_lesson", nullable = false)
    @CreationTimestamp
    private LocalDateTime startAt;



}
