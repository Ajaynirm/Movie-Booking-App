package com.example.demo.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "movie-show")
public class Movie {
    @Id
    @GeneratedValue
    private Long id;


    private String title;

    private String genre;

    private int duration; // in minutes

    // Constructors, Getters, Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle( String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre( String genre) {
        this.genre = genre;
    }


    public int getDuration() {
        return duration;
    }

    public void setDuration( int duration) {
        this.duration = duration;
    }
}

