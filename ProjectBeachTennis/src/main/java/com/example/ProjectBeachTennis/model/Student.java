package com.example.ProjectBeachTennis.model;

//import com.example.ProjectBeachTennis.enums.Level;
//import com.example.ProjectBeachTennis.enums.Status;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_student", nullable = false)
    private UUID id;

    @Column(name = "full_name_student", nullable = false)
    private String fullName;

    @Column(unique = true, name = "email_student", nullable = false)
    @Email(message = "E-mail inválido.", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @Column(name = "password_student", nullable = false)
    private String password;

    @Column(name = "level_student", nullable = false)
//    @Enumerated(EnumType.STRING)
//    private Level level;
    private String level;

    @Column(unique = true, name = "cpf_student", nullable = false)
    private String cpf;

    @Column(name = "balance_student", nullable = false)
    private double balance;

    @Column(name = "startdate_student", nullable = false)
    private Date startDate;

    @Column(name = "status_student", nullable = false)
//    @Enumerated(EnumType.STRING)
//    private Status status;
    private String status;
}
