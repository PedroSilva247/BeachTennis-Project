package com.example.ProjectBeachTennis.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ProjectBeachTennis.model.Lesson;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Integer> {

    @Query(value = "SELECT * FROM lesson l WHERE l.team_id_team = :teamId", nativeQuery = true)
    List<Lesson> findLessonsByTeamId(@Param("teamId") int teamId);
}
