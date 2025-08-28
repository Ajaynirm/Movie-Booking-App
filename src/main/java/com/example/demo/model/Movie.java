package com.example.demo.model;


import jakarta.persistence.*;

@Entity
@Table(name = "movie-show")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String title;

    private String genre;

    private int duration; // in minutes

    // Constructors, Getters, Setters


    public Movie() {
    }

    public Movie(String title, String genre, int duration) {
        this.title = title;
        this.genre = genre;
        this.duration = duration;
    }

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
