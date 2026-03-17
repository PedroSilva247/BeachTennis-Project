package com.example.ProjectBeachTennis.model;

//import com.example.ProjectBeachTennis.enums.Level;
//import com.example.ProjectBeachTennis.enums.Status;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "student")
public class Student implements UserDetails {
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

    @Column(name = "start_at_student", nullable = false)
    @CreationTimestamp
    private LocalDateTime startAt;

    @Column(name = "status_student", nullable = false)
//    @Enumerated(EnumType.STRING)
//    private Status status;
    private String status;






    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_STUDENT"));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
