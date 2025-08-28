package com.example.demo.repository;

import com.example.demo.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShowRepository extends JpaRepository<Show, Long> {
    @Query("SELECT s FROM Show s " +
            "JOIN s.movie m " +
            "JOIN s.theatre t " +
            "WHERE LOWER(m.title) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "   OR LOWER(t.name) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "   OR LOWER(t.location) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Show> searchShows(@Param("query") String query);

}



