package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class ShowSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isBooked=false;


    @ManyToOne
    @JsonBackReference
    private Show show;

    @ManyToOne
    private TheatreSeat theatreSeat;

    public ShowSeat(){}

    public ShowSeat( boolean isBooked, Show show, TheatreSeat theatreSeat) {
        this.show = show;
        this.theatreSeat = theatreSeat;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public TheatreSeat getTheatreSeat() {
        return theatreSeat;
    }

    public void setTheatreSeat(TheatreSeat theatreSeat) {
        this.theatreSeat = theatreSeat;
    }
}
