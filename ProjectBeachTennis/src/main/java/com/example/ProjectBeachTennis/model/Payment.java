package com.example.ProjectBeachTennis.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_payment", nullable = false)
    private int id;

    @Column(name = "value_payment", nullable = false)
    private double value;

    @Column(name = "datetime_payment", nullable = false)
    private LocalDateTime datetime;

    @Column(name = "referring_month_payment", nullable = false)
    private int referringmonth;

    @ManyToOne
    @JoinColumn(name = "student_id_student", nullable = false, referencedColumnName = "id_student")
    @JsonBackReference
    private Student student;

}
