package com.example.ProjectBeachTennis.model;

//import com.example.ProjectBeachTennis.enums.Status;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.Email;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "professor")
public class Professor implements UserDetails {

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




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_PROFESSOR"));
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
