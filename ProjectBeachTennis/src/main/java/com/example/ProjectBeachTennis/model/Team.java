package com.example.ProjectBeachTennis.model;

//import com.example.ProjectBeachTennis.enums.Level;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;


@Data
@Entity
@Table(name = "team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_team", nullable = false)
    private UUID id;

    @Column(name = "day_of_week_team", nullable = false)
    private int dayOfWeek;

    @Column(name = "time_team", nullable = false)
    private LocalTime time;

    @Column(name = "level_team", nullable = false)
//    private Level level;
    private String level;

    @ManyToOne
    @JoinColumn(name = "professor_id_professor", nullable = false, referencedColumnName = "id_professor")
    @JsonBackReference
    private Professor professor;

//    @OneToMany(mappedBy = "team")
//    private List<RegistrationStudentTeam> registrations;

    @Column(name = "start_at_team", nullable = false)
    @CreationTimestamp
    private LocalDateTime startAt;
}
