package com.example.demo.dto;


public class ShowSeatDTO {
    private Long id;
    private String seatLabel;
    private boolean booked;
    public ShowSeatDTO(){}
    public ShowSeatDTO(Long id, String seatLabel, boolean booked) {
        this.id = id;
        this.seatLabel = seatLabel;
        this.booked = booked;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSeatLabel() {
        return seatLabel;
    }

    public void setSeatLabel(String seatLabel) {
        this.seatLabel = seatLabel;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }
}