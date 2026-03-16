package com.example.ProjectBeachTennis.model;

//import com.example.ProjectBeachTennis.enums.Status;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.Email;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "professor")
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_professor", nullable = false)
    private UUID id;

    @Column(name = "full_name_professor", nullable = false)
    private String fullName;

    @Column(unique = true, name = "email_professor", nullable = false)
    @Email(message = "E-mail inválido.", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @Column(name = "password_professor", nullable = false)
    private String password;


    @Column(unique = true, name = "cpf_professor", nullable = false)
    private String cpf;

    @Column(name = "status_professor", nullable = false)
//    @Enumerated(EnumType.STRING)
//    private Status status;
    private String status;

    @Column(name = "start_at_professor", nullable = false)
    @CreationTimestamp
    private LocalDateTime startAt;


}
