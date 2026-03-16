package com.example.ProjectBeachTennis.service;

import com.example.ProjectBeachTennis.errors.EntityAlreadyExistsException;
import com.example.ProjectBeachTennis.errors.EntityNonExistsException;
import com.example.ProjectBeachTennis.model.Professor;
import com.example.ProjectBeachTennis.model.Student;
import com.example.ProjectBeachTennis.model.Team;
import com.example.ProjectBeachTennis.repository.StudentRepository;
import com.example.ProjectBeachTennis.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeamRepository teamRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public List<Team> getTeamsByStudentId(UUID id) {
        return teamRepository.findTeamsByStudentId(id);
    }

    public Student saveStudent(Student student) {
        if(studentRepository.existsByCpf(student.getCpf())) {
            throw new EntityAlreadyExistsException("CPF já cadastrado");
        } else if (studentRepository.existsByEmail(student.getEmail())){
            throw new EntityAlreadyExistsException("Email já cadastrado");
        } else if (studentRepository.existsByFullName(student.getFullName())){
            throw new EntityAlreadyExistsException("Nome já cadastrado");
        }
        return studentRepository.save(student);
    }

    public void delete(UUID id) {
        var student = this.studentRepository.findById(id).orElse(null);
        if(student == null) {
            throw new EntityNonExistsException("Aluno não encontrado");
        }
        studentRepository.delete(student);
    }

}
