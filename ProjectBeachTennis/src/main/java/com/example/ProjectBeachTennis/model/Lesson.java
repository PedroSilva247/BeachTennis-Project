package com.example.ProjectBeachTennis.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "lesson")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_lesson", nullable = false)
    private UUID id;

    @Column(name = "datetime_lesson", nullable = false)
    private LocalDateTime datetime;

    @Column(name = "is_lesson_conducted", nullable = false)
    private boolean isLessonConducted;

    @ManyToOne
    @JoinColumn(name = "team_id_team", nullable = false, referencedColumnName = "id_team")
    @JsonBackReference
    private Team team;

//    @OneToMany(mappedBy = "lesson")
//    private List<AttendanceStudentLesson> attendances;

}
