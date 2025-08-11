package com.example.demo.dto;


import java.time.LocalDateTime;

public class ShowDetailDTO {
    private Long id;
    private String movieTitle;
    private String theatreName;
    private String location;
    private LocalDateTime showTime;
    public ShowDetailDTO(Long id, String movieTitle, String theatreName, String location, LocalDateTime showTime) {
        this.id = id;
        this.movieTitle = movieTitle;
        this.theatreName = theatreName;
        this.location = location;
        this.showTime = showTime;
    }
}



