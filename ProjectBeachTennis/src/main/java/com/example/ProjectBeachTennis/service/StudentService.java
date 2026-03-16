package com.example.ProjectBeachTennis.service;

import com.example.ProjectBeachTennis.model.Professor;
import com.example.ProjectBeachTennis.model.Student;
import com.example.ProjectBeachTennis.model.Team;
import com.example.ProjectBeachTennis.repository.StudentRepository;
import com.example.ProjectBeachTennis.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeamRepository teamRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public List<Team> getTeamsByStudentId(Integer id) {
        return teamRepository.findTeamsByStudentId(id);
    }

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

}
