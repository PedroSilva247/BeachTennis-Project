package com.example.ProjectBeachTennis.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@Table(name = "registration_student_team")
public class RegistrationStudentTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_registration_student_team")
    private int idResgistrationStudentTeam;

    @ManyToOne
    @JoinColumn(name = "team_id_team", nullable = false, referencedColumnName = "id_team")
    @JsonBackReference
    private Team team;

    @ManyToOne
    @JoinColumn(name = "student_id_student", nullable = false, referencedColumnName = "id_student")
    @JsonBackReference
    private Student student;

    @Column(name = "startdate_registration_student_team", nullable = false)
    private Date startdateRegistrationStudentTeam;
}
