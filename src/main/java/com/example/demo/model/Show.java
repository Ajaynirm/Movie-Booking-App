package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table (name = "shows")
public class Show {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Movie movie;

    @ManyToOne
    private Theatre theatre;

    private LocalDateTime showTime;

    @OneToMany(mappedBy = "show", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ShowSeat> showSeats = new ArrayList<>();

    public Show(){}

    public Show(Movie movie, Theatre theatre, LocalDateTime showTime) {
        this.movie = movie;
        this.theatre = theatre;
        this.showTime = showTime;
    }

    public ShowSeat findSeat(String label){
        for(ShowSeat seat:showSeats){
            if(seat.getTheatreSeat().getSeatLabel().equals(label)){
                if(seat.isBooked()){
                    throw new RuntimeException("Seat already booked");
                }
                return seat;
            }
        }
        throw new RuntimeException("Invalid seat label");
    }
    public List<ShowSeat> getShowSeats() {
        return showSeats;
    }

    public void setShowSeats(List<ShowSeat> showSeats) {
        this.showSeats = showSeats;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Theatre getTheatre() {
        return theatre;
    }

    public void setTheatre(Theatre theatre) {
        this.theatre = theatre;
    }

    public LocalDateTime getShowTime() {
        return showTime;
    }

    public void setShowTime(LocalDateTime showTime) {
        this.showTime = showTime;
    }

}

