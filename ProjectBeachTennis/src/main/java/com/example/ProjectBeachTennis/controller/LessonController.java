package com.example.ProjectBeachTennis.controller;

import com.example.ProjectBeachTennis.dto.LessonRequestDTO;
import com.example.ProjectBeachTennis.dto.LessonResponseDTO;
import com.example.ProjectBeachTennis.service.LessonService;
import com.example.ProjectBeachTennis.model.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lesson")
public class LessonController {
    @Autowired
    private LessonService lessonService;

    @GetMapping
    public List<Lesson> getAllLessons() {
        return lessonService.getAllLessons();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LessonResponseDTO> saveLesson(@RequestBody LessonRequestDTO dto) {
        return new ResponseEntity<>(lessonService.saveLesson(dto), HttpStatus.CREATED);
    }
}
