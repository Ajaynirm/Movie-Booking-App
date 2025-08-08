package com.example.demo.repository;

import com.example.demo.model.TheatreSeat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheatreSeatRepository extends JpaRepository<TheatreSeat, Long> {
}
