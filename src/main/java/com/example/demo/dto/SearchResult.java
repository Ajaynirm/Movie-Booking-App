package com.example.demo.dto;

import java.time.LocalDateTime;

public class SearchResult {
    private long id;
    private String theatreName;
    private String movieName;
    private String location;
    private LocalDateTime showTime;
    public SearchResult(){}

    public SearchResult(long id, String theatreName, String movieName, String location, LocalDateTime showTime) {
        this.id = id;
        this.theatreName = theatreName;
        this.movieName = movieName;
        this.location = location;
        this.showTime = showTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTheatreName() {
        return theatreName;
    }

    public void setTheatreName(String theatreName) {
        this.theatreName = theatreName;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getShowTime() {
        return showTime;
    }

    public void setShowTime(LocalDateTime showTime) {
        this.showTime = showTime;
    }
}
