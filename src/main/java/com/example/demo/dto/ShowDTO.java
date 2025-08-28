package com.example.demo.dto;



import java.time.LocalDateTime;
import java.util.List;


public class ShowDTO {
    private Long id;
    private String movieTitle;
    private String theatreName;
    private String location;
    private LocalDateTime showTime;
    private List<ShowSeatDTO> showSeats;
    public ShowDTO(){}

    public ShowDTO(Long id, String movieTitle, String theatreName, String location, LocalDateTime showTime, List<ShowSeatDTO> showSeats) {
        this.id = id;
        this.movieTitle = movieTitle;
        this.theatreName = theatreName;
        this.location = location;
        this.showTime = showTime;
        this.showSeats = showSeats;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getTheatreName() {
        return theatreName;
    }

    public void setTheatreName(String theatreName) {
        this.theatreName = theatreName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<ShowSeatDTO> getShowSeats() {
        return showSeats;
    }

    public void setShowSeats(List<ShowSeatDTO> showSeats) {
        this.showSeats = showSeats;
    }
}
