package com.example.ProjectBeachTennis.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "lesson")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lesson", nullable = false)
    private int id;

    @Column(name = "datetime_lesson", nullable = false)
    private LocalDateTime datetime;

    @Column(name = "is_lesson_conducted")
    private boolean isLessonConducted;

    @ManyToOne
    @JoinColumn(name = "team_id_team", nullable = false, referencedColumnName = "id_team")
    @JsonBackReference
    private Team team;
}
