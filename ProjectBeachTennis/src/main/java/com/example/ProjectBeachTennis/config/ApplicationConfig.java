package com.example.ProjectBeachTennis.config;

import com.example.ProjectBeachTennis.repository.ProfessorRepository;
import com.example.ProjectBeachTennis.repository.StudentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfig {
    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;

    public ApplicationConfig(StudentRepository studentRepository, ProfessorRepository professorRepository) {
        this.studentRepository = studentRepository;
        this.professorRepository = professorRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            var professor = professorRepository.findByEmail(username);
            if (professor.isPresent()) {
                return professor.get();
            }

            var student = studentRepository.findByEmail(username);
            if(student.isPresent()) {
                return student.get();
            }

            throw new UsernameNotFoundException("Usuário não encontrado: " + username);
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());

        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
}
